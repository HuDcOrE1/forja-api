ALTER TABLE venda
ALTER COLUMN status TYPE VARCHAR(50);

ALTER TABLE venda
ADD COLUMN cancelada_em TIMESTAMP;

ALTER TABLE venda
ADD COLUMN cancelada_por BIGINT;

ALTER TABLE venda
ADD COLUMN motivo_cancelamento VARCHAR(500);

ALTER TABLE venda
ADD CONSTRAINT fk_venda_cancelada_por
FOREIGN KEY (cancelada_por)
REFERENCES usuario(id);