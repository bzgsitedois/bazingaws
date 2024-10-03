package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VendaRepository extends BaseRepository<Venda> {
    @Override
    Optional <Venda> findById(Long id);
}
