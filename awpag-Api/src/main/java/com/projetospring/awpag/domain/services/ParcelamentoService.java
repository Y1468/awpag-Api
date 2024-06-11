package com.projetospring.awpag.domain.services;

import com.projetospring.awpag.domain.exception.NegocioException;
import com.projetospring.awpag.domain.model.Cliente;
import com.projetospring.awpag.domain.model.Parcelamento;
import com.projetospring.awpag.domain.repositorio.ClienteRepositorio;
import com.projetospring.awpag.domain.repositorio.ParcelamentoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class ParcelamentoService {

    private final ParcelamentoRepositorio parcelamentoRepositorio;
    private  final ClienteRepositorio clienteRepositorio;

    @Transactional
    public Parcelamento cadastrar(Parcelamento novoParcelamento){

        if (novoParcelamento.getId()!=null){
            throw new NegocioException("Parcelamento a ser criado não deve possuir um codigo");
        }

        //Verificando se esiste cliente com determinado id
        Cliente cliente=clienteRepositorio.findById(novoParcelamento.getCliente().getId())
                        .orElseThrow(()->new NegocioException("Cliente não emcontrado"));

        novoParcelamento.setCliente(cliente);

        novoParcelamento.setDataCriacao(OffsetDateTime.now());

        return parcelamentoRepositorio.save(novoParcelamento);
    }
}
