create table if not exists seguranca.jogador(
    id bigserial primary key ,
    nome varchar(45),
    email varchar(255) unique ,
    foto_path varchar(255),
    perfil varchar(45),
    lider_time boolean,
    time_id bigint

);

DO $$
    BEGIN
        IF NOT EXISTS (select *
                       from information_schema.table_constraints
                       where table_schema = 'controle'
                         and table_name = 'time'
                         and constraint_name = 'fk_time')
        THEN
            alter table if exists seguranca.jogador
                add constraint fk_time foreign key (time_id) references controle.time ON DELETE SET NULL;
        END IF;
END $$;