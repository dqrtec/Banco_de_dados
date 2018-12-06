CREATE TRIGGER toda_musica_DD
ON faixa_compositor
FOR INSERT
AS
declare @id_compositor

if( select i.id_compositor from inserted i
	in
	(select c.id_compositor
	from 
		compositor c
		inner join periodo_musical pm on(c.id_periodo = pm.id_periodo)
	where pm.descricao like 'barroco'
	)
)
begin
	if( select i.id_album,i.num_faixa from inserted i
		not in
		(select f.id_album,f.num_faixa
		from faixa f
		where f.tipo_gravacao
		)
	)
	begin
		RAISERROR ('Existe uma Faixa Barroca que nao foi gravada como DDD', 10, 6)
		ROLLBACK TRANSACTION
	end
end
