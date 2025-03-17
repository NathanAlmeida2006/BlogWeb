package dev.nathan.web.controllers;

import dev.nathan.web.dtos.PostDTO;
import dev.nathan.web.models.Post;
import dev.nathan.web.services.PostService;
import dev.nathan.web.converters.impls.PostConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostConverter postConverter;

    public PostController(PostService postService, PostConverter postConverter) {
        this.postService = postService;
        this.postConverter = postConverter;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView criarPost(@RequestBody PostDTO postDTO) {
        Post post = postConverter.toEntity(postDTO);
        Post postSalvo = postService.criarPost(post);
        PostDTO postSalvoDTO = postConverter.toDTO(postSalvo);

        ModelAndView modelAndView = new ModelAndView("post-detalhes"); // Nome da view
        modelAndView.addObject("post", postSalvoDTO);
        modelAndView.setStatus(HttpStatus.CREATED);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView buscarPostPorId(@PathVariable UUID id) {
        Post post = postService.buscarPostPorId(id);
        PostDTO postDTO = postConverter.toDTO(post);

        ModelAndView modelAndView = new ModelAndView("post-detalhes"); // Nome da view
        modelAndView.addObject("post", postDTO);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTodosPosts() {
        List<Post> posts = postService.listarTodosPosts();
        List<PostDTO> postsDTO = posts.stream()
                .map(postConverter::toDTO)
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("lista-posts"); // Nome da view
        modelAndView.addObject("posts", postsDTO);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ModelAndView atualizarPost(@PathVariable UUID id, @RequestBody PostDTO postDTO) {
        Post post = postConverter.toEntity(postDTO);
        Post postAtualizado = postService.atualizarPost(id, post);
        PostDTO postAtualizadoDTO = postConverter.toDTO(postAtualizado);

        ModelAndView modelAndView = new ModelAndView("post-detalhes"); // Nome da view
        modelAndView.addObject("post", postAtualizadoDTO);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView deletarPost(@PathVariable UUID id) {
        postService.deletarPost(id);

        return new ModelAndView("redirect:/api/posts");
    }
}