--apagar indice anterior antes de rodar esse codigo
drop index faixa.faixa_PK
go
--indice primario

CREATE clustered INDEX indice_faixa_by_album_id 
ON dbo.faixa(
		id_album ASC
) WITH 
(PAD_INDEX = On,
 FILLFACTOR = 100)
GO
--indice secundario

CREATE NONCLUSTERED INDEX indice_sec_tipo_composi ON dbo.faixa
	(
		id_tipo_composicao ASC
	)WITH 
(PAD_INDEX = On,
 FILLFACTOR = 100)
GO
