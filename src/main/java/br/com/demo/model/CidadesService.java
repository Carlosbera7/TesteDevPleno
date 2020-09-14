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
public class CidadesService {

    @Autowired
    private CidadesRepository cidadesRepository;

    public Cidade salvar(Cidade cidades) {
        List<Cidade> cidadesExiste = cidadesRepository.findByNome(cidades.getNome());
        if (!cidadesExiste.isEmpty()) {
            throw new NegocioException("Já existe uma cidade cadastrada com este nome");
        }

        return cidadesRepository.save(cidades);
    }

    public void excluir(Long clienteId) {
        cidadesRepository.deleteById(clienteId);
    }
}
