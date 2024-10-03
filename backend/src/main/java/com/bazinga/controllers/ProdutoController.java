package com.bazinga.controllers;

import com.bazinga.dto.ProdutoDTOs.ProdutoProjectionDTO;
import com.bazinga.dto.TimeDTOs.TimeProjectionDTO;
import com.bazinga.entity.Produto;
import com.bazinga.repository.ProdutoRepository;
import com.bazinga.services.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoService produtoService, ProdutoRepository produtoRepository) {
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
    }
    @GetMapping("/{id}")
    private ResponseEntity<ProdutoProjectionDTO> buscarPeloId(@PathVariable Long id) {
        ProdutoProjectionDTO produtoDTO = produtoService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        return ResponseEntity.ok(produtoDTO);
    }

}
