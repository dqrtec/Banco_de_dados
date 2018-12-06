-------|9 - a|--------

select album.id_album
from album
where preco_compra > 
	(
		select avg(preco_compra) from album
	)

-------|9 - b|--------

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


-------|9 - c|--------

select top 1 c.nome, count(*) 
from compositor c, faixa_compositor cf, faixa_playlist fp
where 
	c.id_compositor = cf.id_compositor and
	cf.num_faixa = fp.num_faixa and
	cf.id_album = fp.id_album
group by c.id_compositor, c.nome

-------|9 - d|--------

SELECT distinct(p.nome)
FROM faixa_playlist fp
INNER JOIN playlist p ON (p.id_playlist = fp.id_playlist)
where ( 
-- Todas as playlists
(SELECT count(*)
FROM faixa_playlist fpp
where fpp.id_playlist = p.id_playlist 
group by fpp.id_playlist) 
-
-- playlists que possuem faixas (todas) do tipo Concerto e compositor do tipo Barroco
(SELECT count(*)
FROM faixa f
INNER JOIN tipo_composicao tc ON (tc.id_tipo_composicao = f.id_tipo_composicao)
INNER JOIN faixa_compositor fc ON (f.num_faixa = fc.num_faixa and f.id_album = fc.id_album)
INNER JOIN compositor c ON (c.id_compositor = fc.id_compositor)
INNER JOIN periodo_musical pm ON (c.id_periodo = pm.id_periodo)
INNER JOIN faixa_playlist fppp ON (f.num_faixa = fppp.num_faixa and f.id_album = fppp.id_album)
where fppp.id_playlist = p.id_playlist  and

	tc.descricao like '_oncerto' and
	pm.descricao like '_arroco'
group by fppp.id_playlist)) = 0