create table if not exists controle.produto(
    id bigserial primary key ,
    nome varchar(255),
    quantidade int,
    preco float,
    desconto float,
    frete float,
    logo varchar(255),
    materiais varchar(255),
    fotopath varchar(255)
)