package com.bazinga.repository;

import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    @Query("SELECT CASE WHEN COUNT(j) >= 2 THEN TRUE ELSE FALSE END " +
            "FROM Jogador j WHERE j.time.id = :timeId AND j.liderTime = TRUE")
    boolean doisLideres(@Param("timeId") Long timeId);


    @Query(value = "SELECT id FROM seguranca.jogador " +
            "WHERE time_id = :timeId AND lider_time = FALSE " +
            "ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Long findRandomJogadorIdByTimeID(@Param("timeId") Long timeId);

}



