package com.bazinga.controllers;


import com.bazinga.bases.BasePagination;
import com.bazinga.dto.JogadorDTOs.*;
import com.bazinga.dto.TimeDTOs.TimeFilter;
import com.bazinga.dto.TimeDTOs.TimeListAllDTO;
import com.bazinga.dto.TimeDTOs.TimeProjectionDTO;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.exception.JogadorNaoEncontradoException;
import com.bazinga.repository.JogadorRepository;
import com.bazinga.services.JogadorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/jogador")
public class JogadorController {

    private final JogadorRepository jogadorRepository;
    private final JogadorService jogadorService;

    public JogadorController(JogadorRepository jogadorRepository, JogadorService jogadorService) {
        this.jogadorRepository = jogadorRepository;
        this.jogadorService = jogadorService;
    }

    @GetMapping("/semTime")
    private ResponseEntity<?> semTime() {
        List<JogadorSemTimeDTO> jogadores = jogadorService.buscarJogadoresSemTime();
        if (jogadores.isEmpty()) {
            return ResponseEntity.ok("Todos os jogadores já têm time");
        }

        return ResponseEntity.ok(jogadores);
    }

    @GetMapping("/{id}")
    private ResponseEntity<JogadorProjectionDTO> findById(@PathVariable Long id) {
        JogadorProjectionDTO jogadorDto = jogadorService.findById(id)
                .orElseThrow(JogadorNaoEncontradoException::new);
        return ResponseEntity.ok(jogadorDto);
    }

    @PostMapping
    private ResponseEntity<?> newEntity(@RequestBody @Valid JogadorCreateDTO dto) {
        jogadorService.newEntity(dto);
        return  ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateEntity(@PathVariable Long id, @RequestBody @Valid JogadorUpdateDTO dto) {
        Jogador jogadorAtualizado = jogadorService.updateEntity(dto,id);
        if (jogadorAtualizado != null) {
            return new ResponseEntity<>("Jogador Atualizado", HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<JogadorProjectionDTO> entity = jogadorService.findById(id);
        entity.ifPresentOrElse(
                e -> jogadorService.deleteEntity(id),
                () -> {
                    throw new JogadorNaoEncontradoException();
                }
        );
        return new ResponseEntity<>("Objeto deletado", HttpStatus.OK);
    }

    @PostMapping(value = "/listAll" , params = {"page", "size"})
    private ResponseEntity<BasePagination<JogadorListAllDTO>> listAll(@RequestParam(defaultValue = "0", required = false) Integer page,
  @RequestParam(defaultValue = "10", required = false) Integer size,
  @RequestBody JogadorFilter jogadorFilter,
  HttpServletRequest request){
        if (size <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return jogadorService.listAll(page, size, jogadorFilter, request);
    }

    @PutMapping("/novoLider/{id}")
    private ResponseEntity<?> novoLider(@PathVariable Long id) {
        if (id != null) {
            jogadorService.definirNovoLider(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/exLider/{id}")
    private ResponseEntity<?> exLider(@PathVariable Long id) {
        if (id != null) {
            jogadorService.tirarCargoLider(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/foto/upload")
    public ResponseEntity<Object> uploadFoto(@RequestParam("file") MultipartFile foto) {
        jogadorService.uploadFoto(foto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/foto/{id}")
    public ResponseEntity<Object> retornaPathFoto(@PathVariable Long id) {
        String path = jogadorService.retornaPathFoto(id);
        return ResponseEntity.ok().body(path);
    }

    @PutMapping("/sairTime")
    public ResponseEntity<?> sairTime(){
        jogadorService.sairTime();
        return ResponseEntity.ok().build();
    }
}
