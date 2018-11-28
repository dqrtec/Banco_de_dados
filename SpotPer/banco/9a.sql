select 
from faixa
where 
		


--musicas feitas por Dvorack
create view musicasDvorack as
select cf.num_faixa as 'num_faixa', cf.id_album as 'id_album'
from compositor c, compositor_faixa cf
where
		c.id_compositor = cf.id_compositor and
		c.nome = 'Dvorack'





select 
from gravadora g, album a,faixa_playlist fp, musicasDvorack md
where
		g.cod_gravadora = a.cod_gravadora and
		a.id_album = md.id_album and
		md.id_album = fp.id_album and
		md.num_faixa = fp.num_faixa

group by