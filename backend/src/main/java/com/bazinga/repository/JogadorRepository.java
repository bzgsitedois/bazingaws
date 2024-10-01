package com.bazinga.repository;


import com.bazinga.bases.BaseRepository;
import com.bazinga.entity.Jogador;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends BaseRepository<Jogador> {
    @Query("SELECT u FROM Jogador u WHERE u.time.id = :timeId")
    List<Jogador> findByTimeId(@Param("timeId") Long timeId);

    @Modifying
    @Transactional
    @Query("UPDATE Jogador u SET u.time.id = :timeId WHERE u.id IN :usuarioIds")
    void adicionarUsuariosAoTime(@Param("timeId") Long timeId, @Param("usuarioIds") List<Long> usuarioIds);

    @Modifying
    @Transactional
    @Query("UPDATE Jogador u SET u.time.id = null WHERE u.id IN :usuarioIds")
    void removerUsuariosDoTime(@Param("usuarioIds") List<Long> usuarioIds);

    @Query("SELECT j FROM Jogador j WHERE j.liderTime = true AND j.time.id = :timeId")
    List<Jogador> findLideresDeTimeByTimeId(@Param("timeId") Long timeId);

}
