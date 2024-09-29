CREATE TABLE IF NOT EXISTS controle.categoria_time (
    categoria_id BIGINT,
    time_id BIGINT,
    PRIMARY KEY (categoria_id, time_id)
    );

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.table_constraints
        WHERE table_schema = 'controle'
        AND table_name = 'categoria_time'
        AND constraint_name = 'fk_categoria'
    ) THEN
ALTER TABLE controle.categoria_time
    ADD CONSTRAINT fk_categoria FOREIGN KEY (categoria_id) REFERENCES controle.categoria(id);
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT *
        FROM information_schema.table_constraints
        WHERE table_schema = 'controle'
        AND table_name = 'categoria_time'
        AND constraint_name = 'fk_time'
    ) THEN
ALTER TABLE controle.categoria_time
    ADD CONSTRAINT fk_time FOREIGN KEY (time_id) REFERENCES controle.time(id);
END IF;
END $$;
