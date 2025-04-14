package dev.nathan.web.controllers;

import dev.nathan.web.dtos.ComentarioDTO;
import dev.nathan.web.dtos.PostDTO;
import dev.nathan.web.models.Post;
import dev.nathan.web.services.PostService;
import dev.nathan.web.converters.impls.PostConverter;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class PostController {
    private final PostService postService;
    private final PostConverter postConverter;

    public PostController(PostService postService, PostConverter postConverter) {
        this.postService = postService;
        this.postConverter = postConverter;
    }

    @GetMapping("/")
    public String redirectToPosts() {
        return "redirect:/posts";
    }

    @GetMapping("/newpost")
    public ModelAndView exibirFormularioNovoPost() {
        ModelAndView modelAndView = new ModelAndView("newpost");
        modelAndView.addObject("post", new PostDTO(null, "", null, "", "", null));
        return modelAndView;
    }

    @PostMapping("/newpost")
    public String criarPost(@Valid @ModelAttribute PostDTO postDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "newpost";
        }
        Post post = postConverter.toEntity(postDTO);
        postService.criarPost(post);
        redirectAttributes.addFlashAttribute("message", "Post criado com sucesso!");
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public ModelAndView listarTodosPosts() {
        List<Post> posts = postService.listarTodosPosts();
        List<PostDTO> postsDTO = posts.stream().map(postConverter::toDTO).collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView("posts");
        modelAndView.addObject("posts", postsDTO);
        return modelAndView;
    }

    @GetMapping("/posts/{id}")
    public ModelAndView buscarPostPorId(@PathVariable UUID id) {
        try {
            Post post = postService.buscarPostPorId(id);
            PostDTO postDTO = postConverter.toDTO(post);
            ModelAndView modelAndView = new ModelAndView("post");
            modelAndView.addObject("post", postDTO);
            modelAndView.addObject("comentario", new ComentarioDTO(null, null, "", id));
            return modelAndView;
        } catch (RuntimeException e) {
            ModelAndView modelAndView = new ModelAndView("posts");
            modelAndView.addObject("message", "Post não encontrado");
            return modelAndView;
        }
    }

    @GetMapping("/posts/edit/{id}")
    public ModelAndView exibirFormularioEditarPost(@PathVariable UUID id) {
        try {
            Post post = postService.buscarPostPorId(id);
            PostDTO postDTO = postConverter.toDTO(post);
            ModelAndView modelAndView = new ModelAndView("editpost");
            modelAndView.addObject("post", postDTO);
            return modelAndView;
        } catch (RuntimeException e) {
            ModelAndView modelAndView = new ModelAndView("posts");
            modelAndView.addObject("message", "Post não encontrado");
            return modelAndView;
        }
    }

    @PostMapping("/posts/edit/{id}")
    public String atualizarPost(@PathVariable UUID id, @Valid @ModelAttribute PostDTO postDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "editpost";
        }
        Post post = postConverter.toEntity(postDTO);
        post.setId(id);
        postService.atualizarPost(id, post);
        redirectAttributes.addFlashAttribute("message", "Post atualizado com sucesso!");
        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/delete/{id}")
    public String deletarPost(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            postService.deletarPost(id);
            redirectAttributes.addFlashAttribute("message", "Post deletado com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao deletar post: " + e.getMessage());
        }
        return "redirect:/posts";
    }
}