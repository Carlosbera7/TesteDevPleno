/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.demo.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author -
 */
@Repository
public interface CidadesRepository extends JpaRepository<Cidade, Long> {

    List<Cidade> findByNome(String nome);

    List<Cidade> findByNomeContains(String nome);
    
}
