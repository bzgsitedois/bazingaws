package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.dto.ProdutoDTOs.ProdutoProjectionDTO;
import com.bazinga.entity.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends BaseRepository<Produto> {

    @Override
    Optional<Produto> findById(Long id);

    @Query("select p from Produto p left join fetch p.tamanho t where p.id= :id")
    Optional<Produto> findProdutoWithTamanhoById(@Param("id") Long id);
}