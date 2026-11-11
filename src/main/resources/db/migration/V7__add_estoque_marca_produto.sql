ALTER TABLE produto
ADD COLUMN marca VARCHAR(100);

ALTER TABLE produto
ADD COLUMN unidade_medida VARCHAR(50);

ALTER TABLE produto
ADD COLUMN estoque_atual INTEGER;

ALTER TABLE produto
ADD COLUMN estoque_minimo INTEGER;

UPDATE produto
SET estoque_atual = estoque
WHERE estoque IS NOT NULL;

UPDATE produto
SET estoque_minimo = 0
WHERE estoque_minimo IS NULL;