ALTER TABLE venda
ADD COLUMN valor_bruto NUMERIC(14,2);

ALTER TABLE venda
ADD COLUMN valor_desconto NUMERIC(14,2);

ALTER TABLE venda
ADD COLUMN valor_acrescimo NUMERIC(14,2);

ALTER TABLE venda
ADD COLUMN valor_liquido NUMERIC(14,2);

UPDATE venda
SET
    valor_bruto = valor_total,
    valor_desconto = 0,
    valor_acrescimo = 0,
    valor_liquido = valor_total;

ALTER TABLE venda
ALTER COLUMN valor_bruto SET NOT NULL;

ALTER TABLE venda
ALTER COLUMN valor_desconto SET NOT NULL;

ALTER TABLE venda
ALTER COLUMN valor_acrescimo SET NOT NULL;

ALTER TABLE venda
ALTER COLUMN valor_liquido SET NOT NULL;