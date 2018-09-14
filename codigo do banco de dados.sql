
CREATE TABLE gravadora (
                cod_gravadora INTEGER NOT NULL,
                nome VARCHAR NOT NULL,
                home_page VARCHAR NOT NULL,
                logradouro SMALLINT NOT NULL,
                cep SMALLINT NOT NULL,
                CONSTRAINT gravadora_pk PRIMARY KEY (cod_gravadora)
);


CREATE TABLE telefone_gravadora (
                telefone VARCHAR NOT NULL,
                cod_gravadora INTEGER NOT NULL,
                CONSTRAINT telefone_gravadora_pk PRIMARY KEY (telefone, cod_gravadora)
);


CREATE TABLE album (
                id_album INTEGER NOT NULL,
                tipo_compra VARCHAR NOT NULL,
                gravacao VARCHAR NOT NULL,
                descricao VARCHAR NOT NULL,
                data_compra DATE NOT NULL,
                preco FLOAT NOT NULL,
                cod_gravadora INTEGER NOT NULL,
                CONSTRAINT album_pk PRIMARY KEY (id_album)
);


CREATE TABLE composicao (
                id_composicao INTEGER NOT NULL,
                descricao VARCHAR NOT NULL,
                tipo VARCHAR NOT NULL,
                CONSTRAINT composicao_pk PRIMARY KEY (id_composicao)
);


CREATE TABLE playlist (
                id_playlist INTEGER NOT NULL,
                data_criacao DATE NOT NULL,
                data_ultima_vez_tocada TIMESTAMP NOT NULL,
                numero_vezes_tocada SMALLINT NOT NULL,
                tempo_total_execucao FLOAT NOT NULL,
                nome VARCHAR NOT NULL,
                CONSTRAINT playlist_pk PRIMARY KEY (id_playlist)
);


CREATE TABLE faixa (
                cod_faixa INTEGER NOT NULL,
                tempo_de_duracao FLOAT NOT NULL,
                posicao INTEGER NOT NULL,
                descricao VARCHAR NOT NULL,
                tipo_gravacao VARCHAR NOT NULL,
                id_composicao INTEGER NOT NULL,
                CONSTRAINT faixa_pk PRIMARY KEY (cod_faixa)
);


CREATE TABLE album_faixa (
                cod_faixa INTEGER NOT NULL,
                id_album INTEGER NOT NULL,
                CONSTRAINT album_faixa_pk PRIMARY KEY (cod_faixa, id_album)
);


CREATE TABLE playlist_faixa (
                id_playlist INTEGER NOT NULL,
                cod_faixa INTEGER NOT NULL,
                CONSTRAINT playlist_faixa_pk PRIMARY KEY (id_playlist, cod_faixa)
);


CREATE TABLE interprete (
                id_interprete INTEGER NOT NULL,
                nome VARCHAR NOT NULL,
                tipo VARCHAR NOT NULL,
                CONSTRAINT interprete_pk PRIMARY KEY (id_interprete)
);


CREATE TABLE interprete_faixa (
                id_interprete INTEGER NOT NULL,
                cod_faixa INTEGER NOT NULL,
                CONSTRAINT interprete_faixa_pk PRIMARY KEY (id_interprete, cod_faixa)
);


CREATE TABLE compositor (
                id_compositor INTEGER NOT NULL,
                data_morte DATE NOT NULL,
                cidade VARCHAR NOT NULL,
                pais VARCHAR NOT NULL,
                nome VARCHAR NOT NULL,
                data_nascimento DATE NOT NULL,
                CONSTRAINT compositor_pk PRIMARY KEY (id_compositor)
);


CREATE TABLE compositor_faixa (
                id_compositor INTEGER NOT NULL,
                cod_faixa INTEGER NOT NULL,
                CONSTRAINT compositor_faixa_pk PRIMARY KEY (id_compositor, cod_faixa)
);


CREATE TABLE Periodo_musical (
                id_periodo INTEGER NOT NULL,
                intervalo_ativo DATE NOT NULL,
                periodo_descrição VARCHAR NOT NULL,
                CONSTRAINT Periodo_musical_pk PRIMARY KEY (id_periodo)
);


CREATE TABLE periodo_compositor (
                id_periodo INTEGER NOT NULL,
                id_compositor INTEGER NOT NULL,
                CONSTRAINT periodo_compositor_pk PRIMARY KEY (id_periodo, id_compositor)
);


ALTER TABLE telefone_gravadora ADD CONSTRAINT gravadora_telefone_gravadora_fk
FOREIGN KEY (cod_gravadora)
REFERENCES gravadora (cod_gravadora)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE album ADD CONSTRAINT gravadora_album_fk
FOREIGN KEY (cod_gravadora)
REFERENCES gravadora (cod_gravadora)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE album_faixa ADD CONSTRAINT album_album_faixa_fk
FOREIGN KEY (id_album)
REFERENCES album (id_album)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE faixa ADD CONSTRAINT composicao_faixa_fk
FOREIGN KEY (id_composicao)
REFERENCES composicao (id_composicao)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE playlist_faixa ADD CONSTRAINT playlist_playlist_faixa_fk
FOREIGN KEY (id_playlist)
REFERENCES playlist (id_playlist)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE interprete_faixa ADD CONSTRAINT faixa_interprete_faixa_fk
FOREIGN KEY (cod_faixa)
REFERENCES faixa (cod_faixa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE compositor_faixa ADD CONSTRAINT faixa_compositor_faixa_fk
FOREIGN KEY (cod_faixa)
REFERENCES faixa (cod_faixa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE playlist_faixa ADD CONSTRAINT faixa_playlist_faixa_fk
FOREIGN KEY (cod_faixa)
REFERENCES faixa (cod_faixa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE album_faixa ADD CONSTRAINT faixa_album_faixa_fk
FOREIGN KEY (cod_faixa)
REFERENCES faixa (cod_faixa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE interprete_faixa ADD CONSTRAINT interprete_interprete_faixa_fk
FOREIGN KEY (id_interprete)
REFERENCES interprete (id_interprete)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

 

ALTER TABLE compositor_faixa ADD CONSTRAINT compositor_compositor_faixa_fk
FOREIGN KEY (id_compositor)
REFERENCES compositor (id_compositor)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE periodo_compositor ADD CONSTRAINT Periodo_musical_periodo_compositor_fk
FOREIGN KEY (id_periodo)
REFERENCES Periodo_musical (id_periodo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;