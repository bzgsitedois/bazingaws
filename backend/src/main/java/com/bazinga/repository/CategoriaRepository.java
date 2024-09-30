package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.CategoriaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends BaseRepository<CategoriaEntity> {

    @Override
    Optional<CategoriaEntity> findById(Long id);
}
