SELECT play
from
	(select playlist.play,
		(select faixa_play.faixa from faixa_play where faixa_play.play = playlist.play --todas as faixas de uma certa play
		except
		SELECT faixa.faixa FROM faixa WHERE tipo = 'a')        --tudas as faixas do tipo barroco
		is NULL as 'barroco'
	from playlist)
where barroco = 1  --lista das playlists e se é composta apenas de barrocas
