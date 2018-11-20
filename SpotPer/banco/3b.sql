drop trigger limite64FaixaPorAlbum
go

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

select count(*) from faixa where id_album=111

insert into faixa values (99, 111, 'Faixa 1', 1.50, 'cdd', 33)
