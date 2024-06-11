package com.projetospring.awpag.domain.repositorio;

import com.projetospring.awpag.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente,Long> {

    List<Cliente>findByNome(String nome);
    List<Cliente>findByNomeContaining(String nome);
    Optional<Cliente>findByEmail(String email);
}
