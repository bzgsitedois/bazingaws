CREATE TABLE IF NOT EXISTS controle.jogo_time (
    jogo_id BIGINT,
    time_id BIGINT,
    PRIMARY KEY (jogo_id, time_id)
    );

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.table_constraints
        WHERE table_schema = 'controle'
        AND table_name = 'jogo_time'
        AND constraint_name = 'fk_jogo'
    ) THEN
ALTER TABLE controle.jogo_time
    ADD CONSTRAINT fk_jogo FOREIGN KEY (jogo_id) REFERENCES controle.jogo(id);
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.table_constraints
        WHERE table_schema = 'controle'
        AND table_name = 'jogo_time'
        AND constraint_name = 'fk_time'
    ) THEN
ALTER TABLE controle.jogo_time
    ADD CONSTRAINT fk_time FOREIGN KEY (time_id) REFERENCES controle.time(id);
END IF;
END $$;
