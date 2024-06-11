package com.projetospring.awpag.api.controller;

import com.projetospring.awpag.domain.exception.NegocioException;
import com.projetospring.awpag.domain.model.Cliente;
import com.projetospring.awpag.domain.repositorio.ClienteRepositorio;
import com.projetospring.awpag.domain.services.CadastroClienteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private  final CadastroClienteService cadastroClienteService;
    private ClienteRepositorio clienteRepositorio;

    //Listando todos clientes
    @GetMapping
    public List <Cliente> listar(){
        return  clienteRepositorio.findAll();
    }

    //Listando um cliente em especifico
    @GetMapping("{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId){
        Optional<Cliente>cliente=clienteRepositorio.findById(clienteId);

        if (cliente.isPresent()) {
              return ResponseEntity.ok(cliente.get());
           }
              return ResponseEntity.notFound().build();
        }

     //Cadastrando cliente
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cliente adcionar(@Valid @RequestBody Cliente cliente){

        return cadastroClienteService.salvar(cliente);
    }

    //Altera cliente
    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId,@Valid @RequestBody Cliente cliente){

        if (!clienteRepositorio.existsById(clienteId)){
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteId);
        cliente=cadastroClienteService.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }
    //Deletar cliente
    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> delete(@PathVariable Long clienteId){

        if (!clienteRepositorio.existsById(clienteId)){
             return ResponseEntity.notFound().build();
        }

        cadastroClienteService.excluir(clienteId);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String>capturar(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
