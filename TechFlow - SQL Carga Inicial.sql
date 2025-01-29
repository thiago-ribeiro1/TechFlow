INSERT INTO produto (nome, descricao, valor) VALUES
('Smartphone XYZ', 'Smartphone com 128GB de armazenamento e câmera tripla de 50MP', 1999.99),
('Notebook ABC', 'Notebook com processador Intel i7, 16GB RAM e SSD de 512GB', 5499.90),
('Smart TV Ultra HD', 'Televisão 4K de 55 polegadas com suporte a HDR e Wi-Fi integrado', 2999.99),
('Fone de Ouvido Bluetooth', 'Fone de ouvido com cancelamento de ruído e bateria de 30 horas', 499.90),
('Caixa de Som Portátil', 'Caixa de som com conexão Bluetooth, resistente à água e 20W de potência', 249.90);

INSERT INTO estoque (quantidade, produto_id) VALUES
(1000, 1),
(1000, 2),
(1000, 3),
(1000, 4),
(1000, 5);

INSERT INTO cliente (nome, cpf) VALUES
('João Silva', '12345678900'),
('Maria Oliveira', '98765432100'),
('Pedro Santos', '11122233344'),
('Ana Costa', '55566677788'),
('Lucas Lima', '99988877766');