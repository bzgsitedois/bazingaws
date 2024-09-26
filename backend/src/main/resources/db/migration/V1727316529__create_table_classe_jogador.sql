CREATE TABLE IF NOT EXISTS controle.classe_jogador (
         classe_id BIGINT,
         jogador_id BIGINT,
         PRIMARY KEY (classe_id, jogador_id)
    );

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.table_constraints
        WHERE table_schema = 'controle'
        AND table_name = 'classe_jogador'
        AND constraint_name = 'fk_classe'
    ) THEN
ALTER TABLE controle.classe_jogador
    ADD CONSTRAINT fk_classe FOREIGN KEY (classe_id) REFERENCES controle.classe(id);
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.table_constraints
        WHERE table_schema = 'controle'
        AND table_name = 'classe_jogador'
        AND constraint_name = 'fk_jogador'
    ) THEN
ALTER TABLE controle.classe_jogador
    ADD CONSTRAINT fk_jogador FOREIGN KEY (jogador_id) REFERENCES seguranca.jogador(id);
END IF;
END $$;
