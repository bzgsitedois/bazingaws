package com.bazinga.services;

import com.bazinga.dto.JogadorDTOs.JogadorCreateDTO;
import com.bazinga.dto.JogadorDTOs.JogadorProjectionDTO;
import com.bazinga.dto.JogadorDTOs.JogadorSemTimeDTO;
import com.bazinga.dto.JogadorDTOs.JogadorUpdateDTO;
import com.bazinga.dto.TimeDTOs.TimeCreateDTO;
import com.bazinga.entity.CategoriaEntity;
import com.bazinga.entity.ClasseTFEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.mapper.JogadorMapper;
import com.bazinga.repository.ClasseRepository;
import com.bazinga.repository.JogadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final JogadorMapper jogadorMapper;
    private final ClasseRepository classeRepository;

    public JogadorService(JogadorRepository jogadorRepository, JogadorMapper jogadorMapper, ClasseRepository classeRepository) {
        this.jogadorRepository = jogadorRepository;
        this.jogadorMapper = jogadorMapper;
        this.classeRepository = classeRepository;
    }

    public void atualizarJogadoresDoTime(Time time, List<Long> novosUsuariosIds) {
        List<Jogador> usuariosAtuais = jogadorRepository.findByTimeId(time.getId());

        List<Long> idsUsuariosAtuais = usuariosAtuais.stream()
                .map(Jogador::getId)
                .toList();

        List<Long> idsParaRemover = idsUsuariosAtuais.stream()
                .filter(id -> !novosUsuariosIds.contains(id))
                .toList();

        if (!idsParaRemover.isEmpty()) {
            jogadorRepository.removerUsuariosDoTime(idsParaRemover);
        }

        List<Long> idsParaAdicionar = novosUsuariosIds.stream()
                .filter(id -> !idsUsuariosAtuais.contains(id))
                .toList();

        if (!idsParaAdicionar.isEmpty()) {
            jogadorRepository.adicionarUsuariosAoTime(time.getId(), idsParaAdicionar);
        }
    }

    public List<JogadorSemTimeDTO> buscarJogadoresSemTime() {
        List<Object[]> resultados = jogadorRepository.findJogadoresSemTime();
        List<JogadorSemTimeDTO> jogadores = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Long id = (Long) resultado[0];
            String nome = (String) resultado[1];
            jogadores.add(new JogadorSemTimeDTO(id, nome));
        }
        return jogadores;
    }

    public Optional<JogadorProjectionDTO> findById(long id) {
        Optional<Jogador> jogador = jogadorRepository.findJogadoresWithClassesById(id);
        return jogador.map(jogadorMapper::toJogadorProjectionDTO);
    }

    public Jogador newEntity(JogadorCreateDTO dto) {
        Jogador entity = jogadorMapper.toEntity(dto);

        for (Long id : dto.classesId()) {
            ClasseTFEntity classe = classeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o id: " + id));
            entity.getClasses().add(classe);
        }

        return jogadorRepository.save(entity);
    }

    public Jogador updateEntity(JogadorUpdateDTO dto , Long id){
        Jogador entity = jogadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jogador não encontrado com o id: " + id));

        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setFotoPath(dto.fotoPath());

        Set<ClasseTFEntity> novasClasses = new HashSet<>();
        for (Long classeId : dto.classesId()) {
            ClasseTFEntity classeTF = classeRepository.findById(classeId)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o id: " + classeId));
            novasClasses.add(classeTF);
        }
        entity.setClasses(novasClasses);

        return jogadorRepository.save(entity);

    }

    public void deleteEntity(Long id) {
        jogadorRepository.deleteById(id);
    }


}
