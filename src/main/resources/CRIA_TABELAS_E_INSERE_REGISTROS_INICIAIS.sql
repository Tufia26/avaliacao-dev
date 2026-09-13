CREATE TABLE funcionario (rowid bigint auto_increment, nm_funcionario VARCHAR(255));
INSERT INTO funcionario (nm_funcionario) VALUES ('João'), ('Maria'), ('José'), ('Joana');

CREATE TABLE agenda (rowid bigint auto_increment, nm_agenda VARCHAR(255), cd_disponibilidade VARCHAR(10));
INSERT INTO agenda (nm_agenda, cd_disponibilidade) VALUES ('Exames Admissionais', '1'), ('Consultas Clínicas', '2'), ('Perícia Médica Geral', '3');

CREATE TABLE compromisso (rowid bigint auto_increment, cd_funcionario bigint, cd_agenda bigint, dt_compromisso VARCHAR(10), hr_compromisso VARCHAR(5));
INSERT INTO compromisso (cd_funcionario, cd_agenda, dt_compromisso, hr_compromisso) VALUES (1, 1, '2026-09-15', '09:00'), (2, 2, '2026-09-15', '14:30'), (3, 3, '2026-09-16', '10:00');