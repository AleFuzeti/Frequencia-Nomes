create schema local_names_db;

CREATE TABLE local_names_db.regiao (
    id PRIMARY KEY,
    sigla VARCHAR(2) UNIQUE,
    nome VARCHAR(255) UNIQUE,
    CONSTRAINT ck_id CHECK (id > 0)
);

CREATE TABLE local_names_db.estado (
    id PRIMARY KEY,
    sigla VARCHAR(2) UNIQUE NOT NULL,
    nome VARCHAR(255),
    sigla_reg VARCHAR(2) NOT NULL,
    CONSTRAINT fk_sigla_reg FOREIGN KEY (sigla_reg) REFERENCES local_names_db.regiao(sigla),
    CONSTRAINT ck_id_estado CHECK (id > 0)
);

CREATE TABLE local_names_db.cidade (
    id PRIMARY KEY,
    nome VARCHAR(255) UNIQUE NOT NULL,
    sigla_estado VARCHAR(2) NOT NULL,
    CONSTRAINT fk_sigla_estado FOREIGN KEY (sigla_estado) REFERENCES local_names_db.estado(sigla),
    CONSTRAINT ck_id_cidade CHECK (id > 0)
);

CREATE TABLE local_names_db.nome (
    localidade VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    CONSTRAINT pk_names PRIMARY KEY (nome, localidade),
    frequencia INT DEFAULT 0,
    ranking INT DEFAULT 0,
    CONSTRAINT fk_localidade_nome FOREIGN KEY (cidade) REFERENCES local_names_db.cidade(nome)
);

-- Create table local_names_db.local_nome (
-- 	id int,
--     nome VARCHAR(255),
-- 	localidade int ,
-- 	frequencia INT default 1,
--     CONSTRAINT pk_id_ln PRIMARY KEY (nome, localidade),
--     CONSTRAINT fk_nome FOREIGN KEY (nome) REFERENCES local_names_db.nome(id_nome),
--     CONSTRAINT fk_localidade FOREIGN KEY (localidade) REFERENCES local_names_db.cidade(id_cidade)
-- );
