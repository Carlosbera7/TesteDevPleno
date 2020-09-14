/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.demo.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author -
 */
@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estados) {

        List<Estado> estadosExiste = estadoRepository.findByNome(estados.getNome());
        if (!estadosExiste.isEmpty()) {
            throw new NegocioException("Já existe um estado cadastrado com este nome");
        }

        return estadoRepository.save(estados);
    }
    
    public void excluir(Long clienteId) {
		estadoRepository.deleteById(clienteId);
	}

}
