drop view playlist_qtd_album
go

CREATE VIEW playlist_qtd_album(id_playlist,nome,qtd)
With SchemaBinding
AS
	SELECT p.id_playlist, p.nome , count_big(*) as qtd
	FROM dbo.playlist p, dbo.faixa_playlist fp,dbo.faixa f
	WHERE 
		p.id_playlist = fp.id_playlist AND
		fp.id_album = f.id_album AND
		fp.num_faixa = f.num_faixa
	GROUP BY p.id_playlist , p.nome 
GO

CREATE UNIQUE CLUSTERED INDEX indx_play_qtd_faixa
  ON playlist_qtd_album(id_playlist,nome)
GO
