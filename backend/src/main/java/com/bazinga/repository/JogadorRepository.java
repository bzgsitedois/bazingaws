package com.bazinga.repository;


import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends BaseRepository<Jogador> {
    @Query("SELECT j FROM Jogador j WHERE j.time.id = :timeId")
    List<Jogador> findByTimeId(@Param("timeId") Long timeId);

    @Modifying
    @Transactional
    @Query("UPDATE Jogador j SET j.time.id = :timeId WHERE j.id IN :usuarioIds")
    void adicionarUsuariosAoTime(@Param("timeId") Long timeId, @Param("usuarioIds") List<Long> usuarioIds);

    @Modifying
    @Transactional
    @Query("UPDATE Jogador j SET j.time.id = null WHERE j.id IN :usuarioIds")
    void removerUsuariosDoTime(@Param("usuarioIds") List<Long> usuarioIds);

    @Query("SELECT j FROM Jogador j WHERE j.liderTime = true AND j.time.id = :timeId")
    List<Jogador> findLideresDeTimeByTimeId(@Param("timeId") Long timeId);

    @Query("SELECT j.id, j.nome FROM Jogador j WHERE j.time.id IS NULL")
    List<Object[]> findJogadoresSemTime();

    @Query("SELECT j FROM Jogador j LEFT JOIN FETCH j.classes c WHERE j.id = :id")
    Optional<Jogador> findJogadoresWithClassesById(@Param("id") Long id);

    @Query("SELECT COUNT(j) FROM Jogador j WHERE j.time.id = :id")
    int findNumeroJogadoresByTimeId(@Param("id")Long id);
}
