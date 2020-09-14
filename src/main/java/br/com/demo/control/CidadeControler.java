/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.demo.control;

import br.com.demo.model.Cidade;
import br.com.demo.model.CidadeView;
import br.com.demo.model.CidadesRepository;
import br.com.demo.model.CidadesService;
import br.com.demo.model.EstadoRepository;
import br.com.demo.model.Estado;
import java.util.ArrayList;
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
@RequestMapping("/cidades")
public class CidadeControler {

    @Autowired
    private CidadesRepository cidadesRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadesService cidadesService;

    @Autowired
    private ModelMapper modelMapper;

    private CidadeView toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeView.class);
    }

    private List<CidadeView> toCollectionModel(List<Cidade> comentarios) {
        return comentarios.stream().map(comentario -> toModel(comentario))
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<CidadeView> listar(@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page, size == null ? 10 : size);
        return toCollectionModel(cidadesRepository.findAll(pageable).toList());
    }

    @GetMapping("/quantRegistros")
    public int quantidadeRegistros() {
        return cidadesRepository.findAll().size();
    }

    @GetMapping("cidadeNome/{nome}")
    public List<CidadeView> buscar(@PathVariable String nome) {
        if (nome.length() > 2) {           
            return toCollectionModel(cidadesRepository.findByNomeContains(nome));
        }
        return new ArrayList<>();
    }

    @GetMapping("estado/{estadoId}")
    public List<CidadeView> buscar(@PathVariable Long estadoId) {
        Estado estado = estadoRepository.findById(estadoId).orElseThrow(() -> new RuntimeException("Cidade não encontrada!"));
        return toCollectionModel(estado.getCidades());
    }

    /*@GetMapping("estado/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        Optional<Estado> estados = estadoRepository.findById(estadoId);

        if (estados.isPresent()) {
            return ResponseEntity.ok(estados.get());
        }
        return ResponseEntity.notFound().build();
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeView adcionar(@Valid @RequestBody Cidade cidades) {
        return toModel(cidadesService.salvar(cidades));
    }

    @PutMapping("/{cidadesId}")
    public ResponseEntity<Cidade> atualizar(@Valid @PathVariable Long cidadesId,
            @RequestBody Cidade cidades) {
        if (!cidadesRepository.existsById(cidadesId)) {
            return ResponseEntity.notFound().build();
        }
        cidades.setId(cidadesId);
        cidadesService.salvar(cidades);
        return null;
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Void> remover(@PathVariable Long cidadeId) {
        if (!cidadesRepository.existsById(cidadeId)) {
            return ResponseEntity.notFound().build();
        }
        cidadesService.excluir(cidadeId);
        return ResponseEntity.noContent().build();
    }

}
