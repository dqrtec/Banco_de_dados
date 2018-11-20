drop function busca
go

create function busca(@arg varchar(50))
returns @saida table(
	id_album INT,
	descricao VARCHAR(50),
	tipo_compra VARCHAR(50),
	data_gravacao DATE,
	data_compra DATE,
	preco_compra DECIMAL(10,2),
	cod_gravadora INT
)
as
begin
	insert into @saida 
		select a.id_album, a.descricao, a.tipo_compra, a.data_gravacao, a.data_compra, a.preco_compra, a.cod_gravadora
		from compositor c , faixa_compositor fc , faixa f , album a
		where
			c.nome like '%'+@arg+'%' and
			c.id_compositor = fc.id_compositor and
			fc.id_album = a.id_album
		group by a.id_album, a.descricao, a.tipo_compra, a.data_gravacao, a.data_compra, a.preco_compra, a.cod_gravadora
	return
end
go

select * from dbo.busca('b')
