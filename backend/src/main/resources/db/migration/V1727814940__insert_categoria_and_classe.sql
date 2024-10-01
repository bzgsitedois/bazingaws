insert into controle.categoria (id, categoria) values (1,'SIXES') , (2,'HL')
ON CONFLICT (id) DO NOTHING;
insert into controle.classe(id, classe) VALUES (1, 'SCOUT') , (2, 'SOLDIER') ,(3, 'PYRO') ,(4, 'DEMOMAN') ,(5, 'HEAVY') ,(6, 'ENGINEER') ,(7, 'MEDIC') ,(8, 'SNIPER') ,(9, 'SPY')
ON CONFLICT (id) DO NOTHING;