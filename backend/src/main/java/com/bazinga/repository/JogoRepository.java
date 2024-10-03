package com.bazinga.repository;
import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.JogoEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JogoRepository extends BaseRepository<JogoEntity> {

    @Override
    Optional<JogoEntity> findById(Long id);
}
