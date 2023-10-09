CREATE SCHEMA local_names_db;

CREATE TABLE local_names_db.regiao (
    id_regiao INT NOT NULL,
    sigla_regiao VARCHAR(2) NOT NULL,
    nome_regiao VARCHAR(255) NOT NULL,
    CONSTRAINT pk_regiao PRIMARY KEY(id_regiao,sigla_estado,nome_regiao),
    CONSTRAINT ck_id CHECK (id_regiao > 0)
);

CREATE TABLE local_names_db.estado (
    id_estado INT,
    sigla_estado VARCHAR(2) NOT NULL,
    nome_estado VARCHAR(255) NOT NULL,
    sigla_regiao VARCHAR(2) NOT NULL,
    CONSTRAINT pk_estado PRIMARY KEY(id_estado,sigla_estado,nome_estado,sigla_regiao),
    CONSTRAINT fk_sigla_regiao FOREIGN KEY (sigla_regiao) REFERENCES local_names_db.regiao(sigla_regiao),
    CONSTRAINT ck_id CHECK (id_estado > 0)

);

CREATE TABLE local_names_db.cidade (
    id_cidade INT,
    nome_cidade VARCHAR(255) NOT NULL,
    sigla_estado VARCHAR(2) NOT NULL,
    CONSTRAINT pk_cidade PRIMARY KEY(id_cidade,sigla_estado,nome_cidade),
    CONSTRAINT fk_sigla_estado FOREIGN KEY (sigla_estado) REFERENCES local_names_db.regiao(sigla_estado),
    CONSTRAINT ck_id CHECK (id_cidade > 0)
);

CREATE TABLE local_names_db.nome (
    id int,
    localidade int NOT NULL,
    sexo VARCHAR(1),
    nome VARCHAR(255) NOT NULL,
    frequencia int,
    CONSTRAINT pk_id PRIMARY KEY (id),
    CONSTRAINT fk_nome FOREIGN KEY (localidade) REFERENCES local_names_db.nome(id_cidade),
)