CREATE TABLE IF NOT EXISTS controle.produto_tamanho (
         produto_id BIGINT,
         tamanho_id BIGINT,
         PRIMARY KEY (produto_id, tamanho_id)
    );

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.table_constraints
        WHERE table_schema = 'controle'
        AND table_name = 'produto_tamanho'
        AND constraint_name = 'fk_tamanho'
    ) THEN
ALTER TABLE controle.produto_tamanho
    ADD CONSTRAINT fk_tamanho FOREIGN KEY (tamanho_id) REFERENCES controle.tamanho(id);
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.table_constraints
        WHERE table_schema = 'controle'
        AND table_name = 'produto_tamanho'
        AND constraint_name = 'fk_produto'
    ) THEN
ALTER TABLE controle.produto_tamanho
    ADD CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES controle.produto(id);
END IF;
END $$;
