--   -------------------------------------------------- 
--   Generated by Enterprise Architect Version 10.0.1006
--   Created On : sexta-feira, 03 junho, 2016 
--   DBMS       : PostgreSQL 
--   -------------------------------------------------- 

CREATE DATABASE bau
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       CONNECTION LIMIT = -1;


DROP SCHEMA IF EXISTS CAIXEIRO CASCADE;
DROP SCHEMA IF EXISTS PRODUTO CASCADE;

CREATE SCHEMA PRODUTO;

CREATE TABLE PRODUTO.USUARIO (
    ID SERIAL NOT NULL,
    LOGIN VARCHAR(50) NOT NULL,
    SENHA VARCHAR(32) NOT NULL,
    TIPO SMALLINT NOT NULL,
    CONSTRAINT CHECK_TIPO CHECK (TIPO = ANY( ARRAY[1,2,3] )),
    CONSTRAINT PK_USUARIO PRIMARY KEY (ID),
    CONSTRAINT UN_LOGIN UNIQUE (LOGIN)
);

CREATE TABLE PRODUTO.ENDERECO ( 
	ID SERIAL NOT NULL,
	DESCRICAO TEXT,
	LATITUDE NUMERIC(17, 14),
	LONGITUDE NUMERIC(17, 14),
	CONSTRAINT PK_ENDERECO PRIMARY KEY (ID)
);

CREATE TABLE PRODUTO.CLIENTE (
	ID SERIAL NOT NULL,
	NOME VARCHAR(50) NOT NULL,
	ENDERECO_ID INTEGER NOT NULL,
	USUARIO_ID INTEGER NOT NULL,
	CONSTRAINT PK_CLIENTE PRIMARY KEY (ID),
	CONSTRAINT FK_CLIENTE_ENDERECO FOREIGN KEY (ENDERECO_ID) REFERENCES PRODUTO.ENDERECO (ID),
	CONSTRAINT FK_CLIENTE_USUARIO FOREIGN KEY (USUARIO_ID) REFERENCES PRODUTO.USUARIO (ID)
);

CREATE TABLE PRODUTO.CARNE (
	ID SERIAL NOT NULL,
	DIAVENCIMENTO INT,
	CLIENTE_ID INTEGER NOT NULL,
	CONSTRAINT PK_CARNE PRIMARY KEY (ID),
	CONSTRAINT FK_CARNE_CLIENTE FOREIGN KEY (CLIENTE_ID) REFERENCES PRODUTO.CLIENTE (ID)
);

CREATE TABLE PRODUTO.PAGAMENTO (
	ID SERIAL NOT NULL,
	DATA DATE NOT NULL,
	CARNE_ID INTEGER NOT NULL,
	CONSTRAINT PK_PAGAMENTO PRIMARY KEY (ID),
	CONSTRAINT FK_PAGAMENTO_CARNE FOREIGN KEY (CARNE_ID) REFERENCES PRODUTO.CARNE (ID)
);

CREATE TABLE PRODUTO.CESTA ( 
	ID SERIAL NOT NULL,
	DATA DATE,
	TEMA VARCHAR,
	VALOR_MAX DOUBLE PRECISION,
	PESO INTEGER,
	CONSTRAINT PK_CESTA PRIMARY KEY (ID)
);

CREATE TABLE PRODUTO.FORNECEDOR (
	ID SERIAL NOT NULL,
	NOME VARCHAR(50) NOT NULL,
	USUARIO_ID INTEGER NOT NULL,
	CONSTRAINT PK_FORNECEDOR PRIMARY KEY (ID),
	CONSTRAINT FK_FORNECEDOR_USUARIO FOREIGN KEY (USUARIO_ID) REFERENCES PRODUTO.USUARIO (ID)
);

CREATE TABLE PRODUTO.CATEGORIA (
	ID SERIAL NOT NULL,
	DESCRICAO VARCHAR,
	CONSTRAINT PK_CATEGORIA PRIMARY KEY (ID)
);

CREATE TABLE PRODUTO.PRODUTO (
	ID SERIAL NOT NULL,
	DESCRICAO VARCHAR,
	VALOR DOUBLE PRECISION,
	PESO DOUBLE PRECISION,
	CATEGORIA_ID INTEGER NOT NULL,
	CONSTRAINT PK_PRODUTO PRIMARY KEY (ID),
	CONSTRAINT FK_PRODUTO_CATEGORIA FOREIGN KEY (CATEGORIA_ID) REFERENCES PRODUTO.CATEGORIA (ID)
);

CREATE TABLE PRODUTO.CESTA_PRODUTO ( 
	ID SERIAL NOT NULL,
	CESTA_ID INTEGER NOT NULL,
	PRODUTO_ID INTEGER NOT NULL,
	DATA DATE,
	CONSTRAINT PK_CESTA_PRODUTO PRIMARY KEY (CESTA_ID, PRODUTO_ID, DATA),
	CONSTRAINT FK_CESTA_PRODUTO_CESTA FOREIGN KEY (CESTA_ID) REFERENCES PRODUTO.CESTA (ID),
	CONSTRAINT FK_CESTA_PRODUTO_PRODUTO FOREIGN KEY (PRODUTO_ID) REFERENCES PRODUTO.PRODUTO (ID)
);

CREATE TABLE PRODUTO.CESTA_CATEGORIA ( 
	ID SERIAL NOT NULL,
	CESTA_ID INTEGER NOT NULL,
	CATEGORIA_ID INTEGER NOT NULL,
	DATA DATE NOT NULL,
	CONSTRAINT PK_CESTA_CATEGORIA PRIMARY KEY (CESTA_ID, CATEGORIA_ID),
	CONSTRAINT FK_CESTA_CATEGORIA_CESTA FOREIGN KEY (CESTA_ID) REFERENCES PRODUTO.CESTA (ID),
	CONSTRAINT FK_CESTA_CATEGORIA_CATEGORIA FOREIGN KEY (CATEGORIA_ID) REFERENCES PRODUTO.CATEGORIA (ID)
);

CREATE TABLE PRODUTO.FORNECEDOR_PRODUTO ( 
	ID SERIAL NOT NULL,
	FORNECEDOR_ID INTEGER NOT NULL,
	PRODUTO_ID INTEGER NOT NULL,
	VALOR INTEGER PRECISION,
	CONSTRAINT PK_FORNECEDOR_PRODUTO PRIMARY KEY (FORNECEDOR_ID, PRODUTO_ID, ID),
	CONSTRAINT FK_FORNECEDOR_PRODUTO_PRODUTO FOREIGN KEY (PRODUTO_ID) REFERENCES PRODUTO.PRODUTO (ID),
	CONSTRAINT FK_FORNECEDOR_PRODUTO_USUARIO FOREIGN KEY (FORNECEDOR_ID) REFERENCES PRODUTO.USUARIO (ID)
);

CREATE TABLE PRODUTO.CLIENTE_PRODUTO (
	ID SERIAL NOT NULL,
	CLIENTE_ID INTEGER NOT NULL,
	PRODUTO_ID INTEGER NOT NULL,
	SATISFACAO INTEGER,
	CONSTRAINT PK_CLIENTE_PRODUTO PRIMARY KEY (CLIENTE_ID, PRODUTO_ID, ID),
	CONSTRAINT FK_CLIENTE_PRODUTO_CLIENTE FOREIGN KEY (CLIENTE_ID) REFERENCES PRODUTO.CLIENTE (ID),
	CONSTRAINT FK_CLIENTE_PRODUTO_PRODUTO FOREIGN KEY (PRODUTO_ID) REFERENCES PRODUTO.PRODUTO (ID)
);

CREATE SCHEMA CAIXEIRO;

CREATE TABLE CAIXEIRO.RELACIONAMENTO_ENDERECO (
	endereco_saida_id integer not null,
	endereco_chegada_id integer not null,
	distancia integer not null,
	constraint pk_relacionamento_endereco primary key (endereco_saida_id, endereco_chegada_id),
	constraint fk_relacionamento_endereco_saida foreign key (endereco_saida_id) references produto.endereco (id),
	constraint fk_relacionamento_endereco_chegada foreign key (endereco_chegada_id) references produto.endereco (id)
);

CREATE TABLE CAIXEIRO.CENTRODISTRIBUICAO (
	ID SERIAL NOT NULL,
	NOME VARCHAR(50) NOT NULL,
	ENDERECO_ID INTEGER,
	CONSTRAINT PK_CENTRODISTRIBUICAO PRIMARY KEY (ID),
	CONSTRAINT FK_CENTRODISTRIBUICAO_ENDERECO FOREIGN KEY (ENDERECO_ID) REFERENCES PRODUTO.ENDERECO (ID)
);

CREATE TABLE CAIXEIRO.VEICULO (
	ID SERIAL NOT NULL,
	DESCRICAO VARCHAR(50) NOT NULL,
	CAPACIDADE INT NOT NULL,
	CONSTRAINT CHECK_CAPACIDADE CHECK (CAPACIDADE > 0),
	CONSTRAINT PK_VEICULO PRIMARY KEY (ID)
);

CREATE TABLE CAIXEIRO.ENTREGA (
	ID SERIAL NOT NULL,
	DATA DATE NOT NULL,
	VEICULO_ID INTEGER NOT NULL,
	CESTA_ID INTEGER NOT NULL,
	CENTRODISTRIBUICAO_ID INTEGER NOT NULL,
	CONSTRAINT PK_ENTREGA PRIMARY KEY (ID),
	CONSTRAINT FK_ENTREGA_VEICULO FOREIGN KEY (VEICULO_ID) REFERENCES CAIXEIRO.VEICULO (ID),
	CONSTRAINT FK_ENTREGA_CESTA FOREIGN KEY (CESTA_ID) REFERENCES PRODUTO.CESTA (ID),
	CONSTRAINT FK_ENTREGA_CENTRODISTRIBUICAO FOREIGN KEY (CENTRODISTRIBUICAO_ID) REFERENCES CAIXEIRO.CENTRODISTRIBUICAO (ID)
);

CREATE TABLE CAIXEIRO.ENTREGA_PAGAMENTO ( 
	ID SERIAL NOT NULL,
	PAGAMENTO_ID INTEGER NOT NULL,
	ENTREGA_ID INTEGER NOT NULL,
	CONSTRAINT PK_ENTREGA_PAGAMENTO PRIMARY KEY (ID),
	CONSTRAINT FK_ENTREGA_PAGAMENTO_PAGAMENTO FOREIGN KEY (PAGAMENTO_ID) REFERENCES PRODUTO.PAGAMENTO (ID),
	CONSTRAINT FK_ENTREGA_PAGAMENTO_ENTREGA FOREIGN KEY (ENTREGA_ID) REFERENCES CAIXEIRO.ENTREGA (ID) on delete cascade
);

CREATE TABLE CAIXEIRO.ENTREGA_ITERACAO (
	ID BIGSERIAL NOT NULL,
	ENTREGA_ID INTEGER NOT NULL,
	DISTANCIA NUMERIC(17,2) NOT NULL,
	MEDIA NUMERIC(17,2) NOT NULL,
	TRAJETO TEXT NOT NULL,
	CONSTRAINT PK_ENTREGA_ITERACAO PRIMARY KEY (ID),
	CONSTRAINT FK_ENTREGA_ITERACAO_ENTREGA FOREIGN KEY (ENTREGA_ID) REFERENCES CAIXEIRO.ENTREGA (ID) on delete cascade
);
\ No newline at end of file