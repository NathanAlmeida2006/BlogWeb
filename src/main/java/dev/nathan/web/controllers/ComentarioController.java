package dev.nathan.web.controllers;

import dev.nathan.web.dtos.ComentarioDTO;
import dev.nathan.web.models.Comentario;
import dev.nathan.web.services.ComentarioService;
import dev.nathan.web.converters.impls.ComentarioConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final ComentarioConverter comentarioConverter;

    public ComentarioController(ComentarioService comentarioService, ComentarioConverter comentarioConverter) {
        this.comentarioService = comentarioService;
        this.comentarioConverter = comentarioConverter;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView criarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        Comentario comentario = comentarioConverter.toEntity(comentarioDTO);
        Comentario comentarioSalvo = comentarioService.criarComentario(comentario);
        ComentarioDTO comentarioSalvoDTO = comentarioConverter.toDTO(comentarioSalvo);

        ModelAndView modelAndView = new ModelAndView("comentario-detalhes"); // Nome da view
        modelAndView.addObject("comentario", comentarioSalvoDTO);
        modelAndView.setStatus(HttpStatus.CREATED);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView buscarComentarioPorId(@PathVariable UUID id) {
        Comentario comentario = comentarioService.buscarComentarioPorId(id);
        ComentarioDTO comentarioDTO = comentarioConverter.toDTO(comentario);

        ModelAndView modelAndView = new ModelAndView("comentario-detalhes"); // Nome da view
        modelAndView.addObject("comentario", comentarioDTO);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTodosComentarios() {
        List<Comentario> comentarios = comentarioService.listarTodosComentarios();
        List<ComentarioDTO> comentariosDTO = comentarios.stream()
                .map(comentarioConverter::toDTO)
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("lista-comentarios"); // Nome da view
        modelAndView.addObject("comentarios", comentariosDTO);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ModelAndView atualizarComentario(@PathVariable UUID id, @RequestBody ComentarioDTO comentarioDTO) {
        Comentario comentario = comentarioConverter.toEntity(comentarioDTO);
        Comentario comentarioAtualizado = comentarioService.atualizarComentario(id, comentario);
        ComentarioDTO comentarioAtualizadoDTO = comentarioConverter.toDTO(comentarioAtualizado);

        ModelAndView modelAndView = new ModelAndView("comentario-detalhes"); // Nome da view
        modelAndView.addObject("comentario", comentarioAtualizadoDTO);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView deletarComentario(@PathVariable UUID id) {
        comentarioService.deletarComentario(id);

        return new ModelAndView("redirect:/api/comentarios");
    }
}