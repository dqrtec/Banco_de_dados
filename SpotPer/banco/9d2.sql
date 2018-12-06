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
	)
