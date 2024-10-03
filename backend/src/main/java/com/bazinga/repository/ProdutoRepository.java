package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.Produto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends BaseRepository<Produto> {

    @Override
    Optional<Produto> findById(Long id);
}