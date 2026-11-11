ALTER TABLE categoria_produto
ADD COLUMN descricao VARCHAR(255);

ALTER TABLE categoria_produto
ADD COLUMN ativo BOOLEAN;

UPDATE categoria_produto
SET ativo = TRUE
WHERE ativo IS NULL;