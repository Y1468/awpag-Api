package com.projetospring.awpag.api.model;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//Modelo de representação
@Getter
@Setter
public class ParcelamentoModel {

    private Long id;
    //private String nomeCliente;
    private ClienteResumoModel cliente;
    private String descrisao;
    private BigDecimal valorTotal;
    private Integer parcelas;
    private OffsetDateTime dataCriacao;
}
