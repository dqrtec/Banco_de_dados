CREATE DATABASE spotper;

CREATE TABLE peri_musi(
	peri_musi_id SMALLINT NOT NULL,
	inicio_periodo DATE NOT NULL,
	fim_periodo DATE NOT NULL,
	descricao VARCHAR(50)
);

CREATE TABLE compositor(
	compositor_id SMALLINT NOT NULL,
	peri_musi_id SMALLINT NOT NULL,
	datamorte DATE,
	cidade_compositor VARCHAR(50) NOT NULL,
	pais_compositor VARCHAR(50) NOT NULL,
	nome_compositor VARCHAR(50) NOT NULL,
	data_nascimento DATE NOT NULL
);

CREATE TABLE compositor_faixa(
	compositor_id SMALLINT NOT NULL,
	num_faixa SMALLINT NOT NULL,
	album_id SMALLINT NOT NULL
);

CREATE TABLE tipo_interprete(
	tipo_interprete_id SMALLINT NOT NULL,
	tipo VARCHAR(50) NOT NULL
);

CREATE TABLE interprete(
	interprete_id SMALLINT NOT NULL,
	tipo_interprete_id SMALLINT NOT NULL,
	nome_interprete VARCHAR(50)
);

CREATE TABLE faixa_interprete(
	interprete_id SMALLINT NOT NULL,
	num_faixa SMALLINT NOT NULL,
	album_id SMALLINT NOT NULL
);

CREATE TABLE tipo_composicao(
	tipo_composicao_id SMALLINT NOT NULL,
	tipo_compo_descricao VARCHAR(50)
);

CREATE TABLE telefone_gravadora(
	telefone VARCHAR(20) NOT NULL,
	gravadora_id SMALLINT NOT NULL
);

CREATE TABLE gravadora(
	gravadora_id SMALLINT NOT NULL,
	nome_gravadora VARCHAR(50) NOT NULL,
	home_page VARCHAR(50),
	rua VARCHAR(50),
	cidade_gravadora VARCHAR(50) NOT NULL,
	pais_gravadora VARCHAR(50) NOT NULL
);

CREATE TABLE album(
	album_id SMALLINT NOT NULL,
	tipo_compra VARCHAR(50) NOT NULL,
	data_gravacao DATE NOT NULL,
	album_descricao VARCHAR(50) NOT NULL,
	data_compra DATE,
	preco_compra DECIMAL(10,2),
	gravadora_id SMALLINT NOT NULL
);

CREATE TABLE album_faixa(
	num_faixa SMALLINT NOT NULL,
	album_id SMALLINT NOT NULL
);

CREATE TABLE faixa(
	num_faixa SMALLINT NOT NULL,
	album_id SMALLINT NOT NULL,
	tempo_duracao_min DECIMAL(10,2),
	faixa_descricao VARCHAR(50),
	tipo_composicao_id SMALLINT NOT NULL
);

CREATE TABLE album_faixa_playlist(
	playlist_id SMALLINT NOT NULL,
	num_faixa SMALLINT NOT NULL,
	album_id SMALLINT NOT NULL,
	quantidade_tocada SMALLINT NOT NULL,
	data_ultima_vez_tocada DATE
);

CREATE TABLE playlist(
	playlist_id SMALLINT NOT NULL,
	data_criacao DATE NOT NULL,
	data_ultima_vez_tocada TIMESTAMP,
	tempo_playlist DECIMAL(10,2),
	nome_playlist VARCHAR(50)
);
######################################################
######################################################

alter table peri_musi
	add constraint peri_musi_PK primary key(peri_musi_id);

alter table compositor
	add constraint compositor_PK primary key(compositor_id);

alter table tipo_interprete
	add constraint tipo_interprete_PK primary key(tipo_interprete_id);

alter table interprete
	add constraint interprete_PK primary key(interprete_id);

alter table tipo_composicao
	add constraint tipo_composicao_PK primary key(tipo_composicao_id);

alter table playlist
	add constraint playlist_PK primary key(playlist_id);

alter table gravadora
	add constraint gravadora_PK primary key(gravadora_id);

alter table album
	add constraint album_PK primary key(album_id);

######################################################
######################################################

alter table compositor
	add constraint comp_peri_musi_FK 
		foreign key(peri_musi_id) 
		references peri_musi;

alter table telefone_gravadora
	add constraint tel_gra_gra_FK 
		foreign key(gravadora_id)
		references gravadora;

alter table album
	add constraint alb_gra_FK 
		foreign key(gravadora_id)
		references gravadora;

alter table interprete
	add constraint inter_tipo_inter_FK 
		foreign key(tipo_interprete_id)
		references tipo_interprete;

alter table faixa
	add constraint faixa_tipo_comp_FK 
		foreign key(tipo_composicao_id)
		references tipo_composicao;

alter table faixa
	add constraint faixa_album_FK 
		foreign key(album_id)
		references album;

alter table faixa
	add constraint faixa_PK
		primary key(num_faixa,album_id);

alter table compositor_faixa
	add constraint comp_faixa_FK 
		foreign key(compositor_id)
		references compositor,

		foreign key(num_faixa,album_id)
		references faixa;

alter table faixa_interprete
	add constraint faixa_interprete_FK 
		foreign key(interprete_id)
		references interprete,

		foreign key(num_faixa,album_id)
		references faixa;

alter table album_faixa
	add constraint album_faixa_FK 
		foreign key(num_faixa,album_id)
		references faixa;

alter table album_faixa_playlist
	add constraint album_faixa_playlist_FK 
		foreign key(playlist_id)
		references playlist,
		foreign key(num_faixa,album_id)
		references faixa;