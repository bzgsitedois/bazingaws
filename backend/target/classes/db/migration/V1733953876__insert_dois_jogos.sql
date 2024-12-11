insert into controle.jogo (id, jogo) values (7,'LOL') , (8,'MARVELRIVALS')
    ON CONFLICT (id) DO NOTHING;