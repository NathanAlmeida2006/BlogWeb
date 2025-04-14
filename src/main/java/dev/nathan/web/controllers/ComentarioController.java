package dev.nathan.web.controllers;

import dev.nathan.web.dtos.ComentarioDTO;
import dev.nathan.web.models.Comentario;
import dev.nathan.web.models.Post;
import dev.nathan.web.services.ComentarioService;
import dev.nathan.web.services.PostService;
import dev.nathan.web.converters.impls.ComentarioConverter;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class ComentarioController {
    private final ComentarioService comentarioService;
    private final PostService postService;
    private final ComentarioConverter comentarioConverter;

    public ComentarioController(ComentarioService comentarioService, PostService postService, ComentarioConverter comentarioConverter) {
        this.comentarioService = comentarioService;
        this.postService = postService;
        this.comentarioConverter = comentarioConverter;
    }

    @PostMapping("/posts/{id}/comments")
    public String criarComentario(@PathVariable UUID id, @Valid @ModelAttribute ComentarioDTO comentarioDTO, RedirectAttributes redirectAttributes) {
        try {
            Post post = postService.buscarPostPorId(id);
            Comentario comentario = comentarioConverter.toEntity(comentarioDTO, post);
            comentarioService.criarComentario(comentario);
            redirectAttributes.addFlashAttribute("message", "Comentário adicionado com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao adicionar comentário: " + e.getMessage());
        }
        return "redirect:/posts/" + id;
    }

    @GetMapping("/comments/edit/{id}")
    public ModelAndView exibirFormularioEditarComentario(@PathVariable UUID id) {
        try {
            Comentario comentario = comentarioService.buscarComentarioPorId(id);
            ComentarioDTO comentarioDTO = comentarioConverter.toDTO(comentario);
            ModelAndView modelAndView = new ModelAndView("editcomment");
            modelAndView.addObject("comentario", comentarioDTO);
            return modelAndView;
        } catch (RuntimeException e) {
            ModelAndView modelAndView = new ModelAndView("posts");
            modelAndView.addObject("message", "Comentário não encontrado");
            return modelAndView;
        }
    }

    @PostMapping("/comments/edit/{id}")
    public String atualizarComentario(@PathVariable UUID id, @Valid @ModelAttribute ComentarioDTO comentarioDTO, RedirectAttributes redirectAttributes) {
        try {
            Comentario comentario = comentarioConverter.toEntity(comentarioDTO);
            comentario.setId(id);
            Comentario existingComentario = comentarioService.buscarComentarioPorId(id);
            comentario.setPost(existingComentario.getPost());
            comentarioService.atualizarComentario(id, comentario);
            redirectAttributes.addFlashAttribute("message", "Comentário atualizado com sucesso!");
            return "redirect:/posts/" + existingComentario.getPost().getId();
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao atualizar comentário: " + e.getMessage());
            return "redirect:/posts";
        }
    }

    @GetMapping("/comments/delete/{id}")
    public String deletarComentario(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            Comentario comentario = comentarioService.buscarComentarioPorId(id);
            UUID postId = comentario.getPost().getId();
            comentarioService.deletarComentario(id);
            redirectAttributes.addFlashAttribute("message", "Comentário deletado com sucesso!");
            return "redirect:/posts/" + postId;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao deletar comentário: " + e.getMessage());
            return "redirect:/posts";
        }
    }
}