package com.bazinga.mapper;

import com.bazinga.dto.ProdutoDTOs.ProdutoProjectionDTO;
import com.bazinga.entity.Produto;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-04T22:01:52-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public ProdutoProjectionDTO toProdutoProjectionDTO(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        List<Enum> tamanho = null;
        Long id = null;
        String nome = null;
        String logo = null;
        double preco = 0.0d;
        double desconto = 0.0d;
        double frete = 0.0d;
        String materiais = null;
        String fotoPath = null;
        int quantidade = 0;

        tamanho = mapClasses( produto.getTamanho() );
        id = produto.getId();
        nome = produto.getNome();
        logo = produto.getLogo();
        preco = produto.getPreco();
        desconto = produto.getDesconto();
        frete = produto.getFrete();
        materiais = produto.getMateriais();
        fotoPath = produto.getFotoPath();
        quantidade = produto.getQuantidade();

        ProdutoProjectionDTO produtoProjectionDTO = new ProdutoProjectionDTO( id, nome, logo, preco, desconto, frete, materiais, tamanho, fotoPath, quantidade );

        return produtoProjectionDTO;
    }
}
