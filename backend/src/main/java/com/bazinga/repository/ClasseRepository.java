package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.ClasseTFEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClasseRepository extends BaseRepository<ClasseTFEntity>{
    @Override
    Optional<ClasseTFEntity> findById(Long id);

    @Query("SELECT c.id FROM Jogador j JOIN j.classes c WHERE j.id = :jogadorId")
    List<Long> findClassesIdsByJogadorId(@Param("jogadorId") Long jogadorId);
}
