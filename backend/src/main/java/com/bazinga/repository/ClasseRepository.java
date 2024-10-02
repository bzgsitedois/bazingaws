package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.CategoriaEntity;
import com.bazinga.entity.ClasseTFEntity;
import com.bazinga.entity.enums.ClasseTF;

import java.util.Optional;

public interface ClasseRepository extends BaseRepository<ClasseTFEntity>{
    @Override
    Optional<ClasseTFEntity> findById(Long id);
}
