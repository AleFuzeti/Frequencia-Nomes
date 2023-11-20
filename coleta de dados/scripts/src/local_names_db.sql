create schema local_names_db;

CREATE TABLE local_names_db.regiao (
    id_regiao INT unique,
    sigla_regiao VARCHAR(2) PRIMARY KEY,
    nome_regiao VARCHAR(255) unique,
    CONSTRAINT ck_id CHECK (id_regiao > 0)
);

CREATE TABLE local_names_db.estado (
    id_estado INT unique not null,
    sigla_estado VARCHAR(2) unique NOT NULL,
    nome_estado VARCHAR(255) PRIMARY KEY,
    sigla_regiao VARCHAR(2) NOT NULL,
    CONSTRAINT fk_sigla_regiao FOREIGN KEY (sigla_regiao) REFERENCES local_names_db.regiao(sigla_regiao),
    CONSTRAINT ck_id_estado CHECK (id_estado > 0)
);


CREATE TABLE local_names_db.cidade (
    id_cidade INT unique not null,
    nome_cidade VARCHAR(255) unique NOT NULL,
    sigla_estado VARCHAR(2) unique NOT NULL,
    CONSTRAINT pk_cidade PRIMARY KEY(id_cidade,sigla_estado,nome_cidade),
    CONSTRAINT fk_sigla_estado FOREIGN KEY (sigla_estado) REFERENCES local_names_db.estado(sigla_estado), -- Correção aqui
    CONSTRAINT ck_id_cidade CHECK (id_cidade > 0)
);


CREATE TABLE local_names_db.nome (
    id_nome VARCHAR(255) PRIMARY KEY,
    sexo VARCHAR(1) default 'n',
    nome VARCHAR(255) NOT NULL
);

Create table local_names_db.local_nome (
	id int,
    nome VARCHAR(255),
	localidade int ,
	frequencia INT default 1,
    CONSTRAINT pk_id_ln PRIMARY KEY (nome, localidade),
    CONSTRAINT fk_nome FOREIGN KEY (nome) REFERENCES local_names_db.nome(id_nome),
    CONSTRAINT fk_localidade FOREIGN KEY (localidade) REFERENCES local_names_db.cidade(id_cidade)
);
