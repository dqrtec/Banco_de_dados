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