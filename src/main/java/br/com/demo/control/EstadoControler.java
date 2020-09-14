/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.demo.control;

import br.com.demo.model.Cidade;
import br.com.demo.model.CidadeView;
import br.com.demo.model.EstadoRepository;
import br.com.demo.model.EstadoService;
import br.com.demo.model.Estado;
import br.com.demo.model.EstadoView;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author -
 */
@RestController
@RequestMapping("/estados")
public class EstadoControler {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private ModelMapper modelMapper;

    private EstadoView toModel(Estado estado) {
        return modelMapper.map(estado, EstadoView.class);
    }

    private List<EstadoView> toCollectionModel(List<Estado> comentarios) {
        return comentarios.stream().map(comentario -> toModel(comentario))
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<EstadoView> listar(@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
        return toCollectionModel(estadoRepository.findAll(pageable).toList());
    }

    @GetMapping("/quantRegistros")
    public int quantidadeRegistros() {
        return estadoRepository.findAll().size();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adcionar(@Valid @RequestBody Estado estados) {
        return estadoService.salvar(estados);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> atualizar(@Valid @PathVariable Long estadoId,
            @RequestBody Estado estados) {
        if (!estadoRepository.existsById(estadoId)) {
            return ResponseEntity.notFound().build();
        }
        estados.setId(estadoId);
        estadoService.salvar(estados);
        return null;
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Void> remover(@PathVariable Long estadoId) {
        if (!estadoRepository.existsById(estadoId)) {
            return ResponseEntity.notFound().build();
        }
        estadoService.excluir(estadoId);
        return ResponseEntity.noContent().build();
    }
}
