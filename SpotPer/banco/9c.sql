select top 1 c.nome, count(*) 
from compositor c, faixa_compositor cf, faixa_playlist fp
where 
	c.id_compositor = cf.id_compositor and
	cf.num_faixa = fp.num_faixa and
	cf.id_album = fp.id_album
group by c.id_compositor, c.nome