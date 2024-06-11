package com.projetospring.awpag.api.controller;

import com.projetospring.awpag.api.asembler.ParcelamentoAsembler;
import com.projetospring.awpag.api.model.ParcelamentoModel;
import com.projetospring.awpag.api.model.input.ParcelamentoInput;
import com.projetospring.awpag.domain.exception.NegocioException;
import com.projetospring.awpag.domain.model.Parcelamento;
import com.projetospring.awpag.domain.repositorio.ParcelamentoRepositorio;
import com.projetospring.awpag.domain.services.ParcelamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/parcelamento")
public class ParcelamentoController {

    private final ParcelamentoRepositorio parcelamentoRepositorio;
    private  final ParcelamentoService parcelamentoService;
    private  final ParcelamentoAsembler parcelamentoAsembler;

    //Listar parcelamento
    @GetMapping
    public List <ParcelamentoModel> listar(){

        return parcelamentoAsembler.toCollectionModel(parcelamentoRepositorio.findAll());
    }

    //Listando um unico parcelamento
    @GetMapping("/{parcelamentoId}")
    public ResponseEntity<ParcelamentoModel> buscar(@PathVariable Long parcelamentoId){
        return parcelamentoRepositorio.findById(parcelamentoId)

                .map( parcelamentoAsembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelamentoModel cadastrar(@Valid @RequestBody ParcelamentoInput parcelamentoInput){
        Parcelamento novoParcelamento=parcelamentoAsembler.toEntity(parcelamentoInput);
        Parcelamento parcelamentoCadastro=parcelamentoService.cadastrar(novoParcelamento);
        return parcelamentoAsembler.toModel(parcelamentoCadastro);
    }

}
