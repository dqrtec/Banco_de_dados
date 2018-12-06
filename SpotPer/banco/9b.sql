-- musicas feitas por Dvorack
create view musicasDvorack as
select cf.num_faixa as 'num_faixa', cf.id_album as 'id_album'
from compositor c, faixa_compositor cf
where
		c.id_compositor = cf.id_compositor and
		c.nome = 'Compo barroco'

-- Consulta
select id_playlist 
from faixa_playlist fp
inner join musicasDvorack md ON (md.id_album = fp.id_album and md.num_faixa = fp.num_faixa)


select top(1) g.nome, count(fp.id_album)
from gravadora g, album a,faixa_playlist fp, musicasDvorack md
where
		g.cod_gravadora = a.cod_gravadora and
		a.id_album = md.id_album and
		md.id_album = fp.id_album and
		md.num_faixa = fp.num_faixa

group by a.cod_gravadora, g.nome
order by count(fp.id_album) desc