CREATE TRIGGER toda_musica_DDD
ON faixa_compositor
FOR INSERT
AS
begin
	declare @id_compositor smallint

	if( (select i.id_compositor from inserted i)
		in
		(select c.id_compositor
		from 
			compositor c
			inner join periodo_musical pm on(c.id_periodo = pm.id_periodo)
		where pm.descricao like '_arroco'
		)
	)
	begin
		if( (select ie.id_album,ie.num_faixa from inserted ie)
			not in
			(select f.id_album,f.num_faixa
			from faixa f
			where f.tipo_gravacao like 'DDD'
			)
		)
		begin
			RAISERROR ('Existe uma Faixa Barroca que nao foi gravada como DDD', 10, 6)
			ROLLBACK TRANSACTION
		end
	end
end

