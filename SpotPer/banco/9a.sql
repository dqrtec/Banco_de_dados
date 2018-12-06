select album.id_album
from album
where preco_compra > 
	(
		select avg(preco_compra) from album
	)