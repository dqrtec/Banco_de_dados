select album_id
from album
where preco_compra > 
	(
		select avg(preco_compra) from album
	)
