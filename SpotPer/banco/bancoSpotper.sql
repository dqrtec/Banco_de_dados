CREATE DATABASE spotper
on PRIMARY(
		NAME = 'spotper',
		FILENAME = 'C:\BDspotPer\spotper.mdf',
		SIZE = 5120KB,
		FILEGROWTH = 1024KB
	),

	FILEGROUP spot_fg01(
			NAME = 'arquivo01',
			FILENAME = 'C:\BDspotPer\arquivo01.ndf',
			SIZE = 1024KB,
			FILEGROWTH = 30%
		),
		(
			NAME = 'arquivo02',
			FILENAME = 'C:\BDspotPer\arquivo02.ndf',
			SIZE = 1024KB,
			FILEGROWTH = 30%
		),

	FILEGROUP spot_fg02(
			NAME = 'arquivo03',
			FILENAME = 'C:\BDspotPer\arquivo03.ndf',
			SIZE = 1024KB,
			FILEGROWTH = 30%
		)

	LOG ON(
		NAME = 'spotper_log',
		FILENAME = 'C:\BDspotPer\spotper_log.ldf',
		SIZE = 1024KB,
		FILEGROWTH = 10%
	)
GO

USE spotper
GO

------------------| TABELAS |------------------

CREATE TABLE periodo_musical(
	id_periodo SMALLINT NOT NULL,
	inicio_periodo DATE NOT NULL,
	fim_periodo DATE,
	descricao VARCHAR(50) NOT NULL
)on spot_fg01;

CREATE TABLE compositor(
	id_compositor INT NOT NULL,
	nome VARCHAR(50) NOT NULL,
	data_nascimento DATE NOT NULL,
	data_morte DATE,
	pais VARCHAR(50) NOT NULL,
	cidade VARCHAR(50) NOT NULL,
	id_periodo SMALLINT NOT NULL
)on spot_fg01;

CREATE TABLE faixa_compositor(
	id_compositor INT NOT NULL,
	num_faixa SMALLINT NOT NULL,
	id_album INT NOT NULL
)on spot_fg01;

CREATE TABLE tipo_interprete(
	id_tipo_interprete SMALLINT NOT NULL,
	tipo VARCHAR(50) NOT NULL
)on spot_fg01;

CREATE TABLE interprete(
	id_interprete INT NOT NULL,
	id_tipo_interprete SMALLINT NOT NULL,
	nome VARCHAR(50)
)on spot_fg01;

CREATE TABLE faixa_interprete(
	id_interprete INT NOT NULL,
	num_faixa SMALLINT NOT NULL,
	id_album INT NOT NULL
)on spot_fg01;

CREATE TABLE tipo_composicao(
	id_tipo_composicao SMALLINT NOT NULL,
	descricao VARCHAR(50) NOT NULL
)on spot_fg01;

CREATE TABLE telefone_gravadora(
	cod_gravadora INT NOT NULL,
	telefone VARCHAR(20) NOT NULL
)on spot_fg01;

CREATE TABLE gravadora(
	cod_gravadora INT NOT NULL,
	nome VARCHAR(50) NOT NULL,
	home_page VARCHAR(50),
	pais VARCHAR(50) NOT NULL,
	cidade VARCHAR(50) NOT NULL,
	rua VARCHAR(50) NOT NULL
)on spot_fg01;

CREATE TABLE album(
	id_album INT NOT NULL,
	descricao VARCHAR(50) NOT NULL,
	tipo_compra VARCHAR(50) NOT NULL,
	data_gravacao DATE NOT NULL,
	data_compra DATE,
	preco_compra DECIMAL(10,2),
	cod_gravadora INT NOT NULL
)on spot_fg01;

CREATE TABLE faixa(
	num_faixa SMALLINT NOT NULL,
	id_album INT NOT NULL,
	descricao VARCHAR(50) NOT NULL,
	tempo_duracao DECIMAL(10,2) NOT NULL,
	tipo_gravacao VARCHAR (3) NOT NULL,
	id_tipo_composicao SMALLINT NOT NULL
)on spot_fg02;

CREATE TABLE faixa_playlist(
	id_playlist INT NOT NULL,
	id_album INT NOT NULL,
	num_faixa SMALLINT NOT NULL,
	quantidade_tocada INT,
	data_ultima_vez_tocada DATE
)on spot_fg02;

CREATE TABLE playlist(
	id_playlist INT NOT NULL,
	nome VARCHAR(50) NOT NULL,
	data_criacao DATE NOT NULL,
	tempo_total_execucao DECIMAL(10,2)
)on spot_fg02;

------------------| INDICES |------------------

CREATE clustered INDEX indice_faixa_by_album_id 
ON dbo.faixa(
		id_album ASC
) WITH 
(PAD_INDEX = On,
 FILLFACTOR = 100)
GO

CREATE NONCLUSTERED INDEX indice_sec_tipo_composi ON dbo.faixa
	(
		id_tipo_composicao ASC
	)WITH 
(PAD_INDEX = On,
 FILLFACTOR = 100)
GO

------------------| CHAVES PRIMÁRIAS |------------------

ALTER TABLE periodo_musical
	ADD CONSTRAINT periodo_musical_PK PRIMARY KEY (id_periodo);

ALTER TABLE compositor
	ADD CONSTRAINT compositor_PK PRIMARY KEY (id_compositor);

ALTER TABLE faixa_compositor
	ADD CONSTRAINT faixa_compositor_PK PRIMARY KEY (id_compositor, num_faixa, id_album);

ALTER TABLE tipo_interprete
	ADD CONSTRAINT tipo_interprete_PK PRIMARY KEY (id_tipo_interprete);

ALTER TABLE interprete
	ADD CONSTRAINT interprete_PK PRIMARY KEY (id_interprete);

ALTER TABLE faixa_interprete
	ADD CONSTRAINT faixa_interprete_PK PRIMARY KEY (id_interprete, num_faixa, id_album);

ALTER TABLE tipo_composicao
	ADD CONSTRAINT tipo_composicao_PK PRIMARY KEY (id_tipo_composicao);

ALTER TABLE telefone_gravadora
	ADD CONSTRAINT telefone_gravadora_PK PRIMARY KEY (cod_gravadora, telefone);

ALTER TABLE gravadora
	ADD CONSTRAINT gravadora_PK PRIMARY KEY (cod_gravadora);

ALTER TABLE album
	ADD CONSTRAINT album_PK PRIMARY KEY (id_album);

ALTER TABLE faixa
	ADD CONSTRAINT faixa_PK PRIMARY KEY (num_faixa, id_album);

ALTER TABLE faixa_playlist
	ADD CONSTRAINT faixa_playlist_PK PRIMARY KEY (id_playlist, num_faixa, id_album);

ALTER TABLE playlist
	ADD CONSTRAINT playlist_PK PRIMARY KEY (id_playlist);


------------------| CHAVES ESTRANGEIRAS |------------------

ALTER TABLE compositor
	ADD CONSTRAINT compositor_periodo_musical_FK 
		FOREIGN KEY (id_periodo) 
		REFERENCES periodo_musical
		ON DELETE NO ACTION
		ON UPDATE CASCADE;

ALTER TABLE faixa_compositor
	ADD CONSTRAINT faixa_compositor_comp_FK 
		FOREIGN KEY (id_compositor)
		REFERENCES compositor
		ON DELETE CASCADE
		ON UPDATE CASCADE;

ALTER TABLE faixa_compositor
	ADD CONSTRAINT faixa_compositor_faixa_FK 
		FOREIGN KEY (num_faixa, id_album)
		REFERENCES faixa (num_faixa, id_album)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

ALTER TABLE interprete
	ADD CONSTRAINT interprete_tipo_inter_FK 
		FOREIGN KEY (id_tipo_interprete)
		REFERENCES tipo_interprete
		ON DELETE NO ACTION
		ON UPDATE CASCADE;

ALTER TABLE faixa_interprete
	ADD CONSTRAINT faixa_interprete_inter_FK 
		FOREIGN KEY (id_interprete)
		REFERENCES interprete
		ON DELETE CASCADE
		ON UPDATE CASCADE;

ALTER TABLE faixa_interprete	
	ADD CONSTRAINT faixa_interprete_faixa_FK 
		FOREIGN KEY (num_faixa, id_album)
		REFERENCES faixa (num_faixa, id_album)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

ALTER TABLE telefone_gravadora
	ADD CONSTRAINT tel_gravadora_FK 
		FOREIGN KEY (cod_gravadora)
		REFERENCES gravadora
		ON DELETE CASCADE
		ON UPDATE CASCADE;

ALTER TABLE album
	ADD CONSTRAINT album_gravadora_FK 
		FOREIGN KEY (cod_gravadora)
		REFERENCES gravadora
		ON DELETE CASCADE
		ON UPDATE CASCADE;

ALTER TABLE faixa
	ADD CONSTRAINT faixa_tipo_composicao_FK 
		FOREIGN KEY (id_tipo_composicao)
		REFERENCES tipo_composicao
		ON DELETE NO ACTION
		ON UPDATE CASCADE;

ALTER TABLE faixa
	ADD CONSTRAINT faixa_album_FK 
		FOREIGN KEY (id_album)
		REFERENCES album
		ON DELETE CASCADE
		ON UPDATE CASCADE;

ALTER TABLE faixa_playlist
	ADD CONSTRAINT faixa_playlist_play_FK 
		FOREIGN KEY (id_playlist)
		REFERENCES playlist
		ON DELETE CASCADE
		ON UPDATE CASCADE;

ALTER TABLE faixa_playlist
	ADD CONSTRAINT faixa_playlist_faixa_FK 	
		FOREIGN KEY (num_faixa, id_album)
		REFERENCES faixa (num_faixa, id_album)
		ON DELETE CASCADE
		ON UPDATE CASCADE;
		
------------------| CONSULTAS |------------------

------------------| TRIGGERS |------------------

create trigger limite64FaixaPorAlbum
	on faixa
	for insert
	as
	declare @aux int
	(select @aux = id_album from inserted)
if(
	(select top 1 count(*) from
		(select  id_album as id from inserted
			union all
		 select id_album as id from faixa)d
		group by id)
	> 17)
begin
	RAISERROR ('ALGUM ALBUM TERA MAIS DE 64 FAIXAS', 10, 6)
	ROLLBACK TRANSACTION
end
GO

------------------| MATERIALIZED VIEW |------------------

CREATE VIEW playlist_qtd_album(id_playlist,nome,qtd)
With SchemaBinding
AS
	SELECT p.id_playlist, p.nome , count_big(*) as qtd
	FROM dbo.playlist p, dbo.faixa_playlist fp,dbo.faixa f
	WHERE 
		p.id_playlist = fp.id_playlist AND
		fp.id_album = f.id_album AND
		fp.num_faixa = f.num_faixa
	GROUP BY p.id_playlist , p.nome 
GO

CREATE UNIQUE CLUSTERED INDEX indx_play_qtd_faixa
  ON playlist_qtd_album(id_playlist,nome)
GO
