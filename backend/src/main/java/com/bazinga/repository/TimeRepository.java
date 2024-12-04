package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.Time;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface TimeRepository extends BaseRepository<Time>{

    @Query("SELECT t FROM Time t LEFT JOIN FETCH t.jogadores j LEFT JOIN FETCH t.jogos c WHERE t.id = :id")
    Optional<Time> findTimeWithJogadoresAndJogosById(@Param("id") Long id);


    @Modifying
    @Query(value = "DELETE FROM controle.jogo_time WHERE jogo_id = :id", nativeQuery = true)
    void desassociarJogos(@Param("id") Long id);

    @Query("SELECT t.nome FROM Time t JOIN Jogador j ON t.id = j.time.id WHERE j.id = :id")
    String findNomeDoTimeByJogadorId(@Param("id") Long id);

}



