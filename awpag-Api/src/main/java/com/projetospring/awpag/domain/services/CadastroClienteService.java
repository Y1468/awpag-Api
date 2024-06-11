package com.projetospring.awpag.domain.services;

import com.projetospring.awpag.domain.exception.NegocioException;
import com.projetospring.awpag.domain.model.Cliente;
import com.projetospring.awpag.domain.repositorio.ClienteRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CadastroClienteService {

    private final ClienteRepositorio clienteRepositorio;

    @Transactional
    public Cliente salvar(Cliente cliente){

        boolean emailEmUso=clienteRepositorio.findByEmail(cliente.getEmail())
                        .filter(c->!c.equals(cliente))
                       .isPresent();

        if (emailEmUso){
            throw new NegocioException("Ja existe um cliente cadastrado com esse e-mail");
        }
        return clienteRepositorio.save(cliente);
    }

    @Transactional
    public void excluir(Long clienteId){
        clienteRepositorio.deleteById(clienteId);
    }
}
