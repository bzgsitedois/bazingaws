package com.bazinga.mapper;


import com.bazinga.dto.JogadorDTOs.JogadorProjectionDTO;
import com.bazinga.dto.ProdutoDTOs.ProdutoProjectionDTO;
import com.bazinga.entity.ClasseTFEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Produto;
import com.bazinga.entity.TamanhoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(source = "tamanho", target = "tamanho")
    ProdutoProjectionDTO toProdutoProjectionDTO(Produto produto);

    default List<Enum> mapClasses(Set<TamanhoEntity> tamanho) {
        return tamanho.stream()
                .map(TamanhoEntity::getTamanho)
                .collect(Collectors.toList());
    }
}
