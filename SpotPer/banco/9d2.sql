SELECT p.nome
FROM
	playlist p
where
	
	(
	SELECT * --count(*)
	from 
		(
			(SELECT fp.num_faixa,fp.id_album 
			 FROM faixa_playlist fp
			 where p.id_playlist = fp.id_playlist)
			except
			(SELECT f.num_faixa,f.id_album
			FROM faixa f
			INNER JOIN tipo_composicao tc ON (tc.id_tipo_composicao = f.id_tipo_composicao)
			INNER JOIN faixa_compositor fc ON (f.num_faixa = fc.num_faixa and f.id_album = fc.id_album)
			INNER JOIN compositor c ON (c.id_compositor = fc.id_compositor)
			INNER JOIN periodo_musical pm ON (c.id_periodo = pm.id_periodo)
			WHERE
				tc.descricao like '_oncerto' and
				pm.descricao like '_arroco'
			--group by f.id_album, f.num_faixa
			)
		)
	) = 0