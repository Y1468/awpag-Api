package com.projetospring.awpag.domain.repositorio;

import com.projetospring.awpag.domain.model.Parcelamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParcelamentoRepositorio extends JpaRepository<Parcelamento,Long> {


}
