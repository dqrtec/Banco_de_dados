SELECT p.nome
FROM
	playlist p
where
	0 =
	(SELECT count(*)
	from 
		((SELECT fp.num_faixa,fp.id_album FROM faixa_playlist fp
			inner join faixa_playlist fp on p.cod_playlist = fp.cod_playlist)
		except
		(SELECT f.num_faixa,f.id_album
			FROM faixa f, tipo_composicao tc, faixa_compositor fc,compositor c, periodo_musical pm
			WHERE
				tc.descricao like 'concerto' and
				pm.descricao like 'barroco' and
				tc.id_tipo_composicao = f.id_tipo_composicao and
				f.num_faixa = fc.num_faixa and
				f.id_album = fc.id_album and
				fc.id_compositor = c.id_compositor and
				c.id_periodo = pm.id_periodo
		group by f.num_faixa,f.id_album)
		)
	)
/*
	(SELECT count(*) FROM faixa_playlist fp
		inner join faixa_playlist fp on p.cod_playlist = fp.cod_playlist)
*/



/////////////// LIXO
SELECT play
from
	(select playlist.play,
		(select faixa_play.faixa from faixa_play where faixa_play.play = playlist.play --todas as faixas de uma certa play
		except
		SELECT faixa.faixa FROM faixa WHERE tipo = 'a')        --tudas as faixas do tipo barroco
		is NULL as 'barroco'
	from playlist)
where barroco = 1  --lista das playlists e se é composta apenas de barrocas

select playlist.nome
from playlist
where
	(select fp.num_faixa,fp.id_album from faixa_playlist fp where fp.id_playlist = playlist.id_playlist
	except
	SELECT f.num_faixa,f.id_album
		FROM faixa f, tipo_composicao tc, faixa_compositor fc,compositor c, periodo_musical pm
		WHERE
			tc.descricao like 'concerto' and
			pm.descricao like 'barroco' and
			tc.id_tipo_composicao = f.id_tipo_composicao and
			f.num_faixa = fc.num_faixa and
			f.id_album = fc.id_album and
			fc.id_compositor = c.id_compositor and
			c.id_periodo = pm.id_periodo
		group by f.num_faixa,f.id_album
	)is NULL
