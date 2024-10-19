insert into controle.tamanho(id,tamanho) values(1, 'PP') , (2, 'P'), (3, 'M') , (4, 'G'),(5, 'GG')
    ON CONFLICT (id) DO NOTHING;