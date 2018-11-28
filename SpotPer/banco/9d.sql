select f.descricao
from faixa f, tipo_composicao tc, compositor_faixa cf, compositor c, periodo_musical
where 
		tc.descricao like 'Concerto' and
		pm.descricao like 'Barroco' and

		tc.id_composicao = f.id_composicao and
		f.num_faixa = cf.num_faixa and 
		f.id_album = cf.id_album and
		cf.id_compositor = c.id_compositor and
		c.id_periodo = pm.id_periodo
