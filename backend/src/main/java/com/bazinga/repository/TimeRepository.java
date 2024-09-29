package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.dto.TimeProjectionDTO;
import com.bazinga.entity.Time;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface TimeRepository extends BaseRepository<Time>{

    @Query("SELECT t FROM Time t LEFT JOIN FETCH t.jogadores j LEFT JOIN FETCH t.categorias c WHERE t.id = :id")
    Optional<Time> findTimeWithJogadoresAndCategoriasById(@Param("id") Long id);


}



