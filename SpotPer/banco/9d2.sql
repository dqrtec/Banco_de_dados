SELECT play
from
	(select pl.play,
		(select play.faixa from play where play.play = pl.play
		except
		SELECT faixa.faixa FROM faixa WHERE tipo = 'a')
		is NULL as 'barroco'
	from pl)
where barroco = 1