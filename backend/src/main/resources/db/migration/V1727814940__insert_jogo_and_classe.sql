insert into controle.jogo (id, jogo) values (1,'TF2') , (2,'VALORANT'),(3,'BRAWLHALLA'),(4,'ROCKETLEAGUE'),(5,'FORTNITE'),(6,'CS2')
ON CONFLICT (id) DO NOTHING;
insert into controle.classe(id, classe) VALUES (1, 'SCOUT') , (2, 'SOLDIER') ,(3, 'PYRO') ,(4, 'DEMOMAN') ,(5, 'HEAVY') ,(6, 'ENGINEER') ,(7, 'MEDIC') ,(8, 'SNIPER') ,(9, 'SPY')
ON CONFLICT (id) DO NOTHING;