package com.bazinga.services;

import com.bazinga.dto.ProdutoDTOs.ProdutoProjectionDTO;
import com.bazinga.entity.Produto;
import com.bazinga.mapper.ProdutoMapper;
import com.bazinga.repository.ProdutoRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;


    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }
    public Optional<ProdutoProjectionDTO> findById(Long id) {
            Optional<Produto> produto = produtoRepository.findProdutoWithTamanhoById(id);
            return produto.map(produtoMapper::toProdutoProjectionDTO);
        }
    }

