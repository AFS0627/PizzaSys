CREATE DATABASE IF NOT EXISTS pizzasys;
USE pizzasys;

CREATE TABLE funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    funcao INT NOT NULL,
    salario DECIMAL(10,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE pizza (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    descricao VARCHAR(500),
    imagem LONGBLOB
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATETIME NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'Pendente',
    funcionario_id INT NOT NULL,
    forma_pagamento INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_pedido_funcionario
        FOREIGN KEY (funcionario_id)
        REFERENCES funcionario(id)
);

CREATE TABLE item_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    pizza_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(500),
    tamanho INT NOT NULL,

    CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedido(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_item_pizza
        FOREIGN KEY (pizza_id)
        REFERENCES pizza(id)
);
CREATE TABLE notificacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    funcionario_id INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    mensagem VARCHAR(500) NOT NULL,
    lida BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_notificacao_funcionario
        FOREIGN KEY (funcionario_id)
        REFERENCES funcionario(id)
        ON DELETE CASCADE
);
CREATE TABLE movimentacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    funcionario_id INT,
    acao VARCHAR(100) NOT NULL,
    pedido_id INT,
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_movimentacao_funcionario
        FOREIGN KEY (funcionario_id)
        REFERENCES funcionario(id),

    CONSTRAINT fk_movimentacao_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedido(id)
);
INSERT INTO funcionario
(nome, login, senha, funcao, salario)
VALUES
('Administrador', 'admin', '1234', 3, 5000.00);
INSERT INTO funcionario
(nome, login, senha, funcao, salario)
VALUES
('Administrador', 'admin', '3', 3, 5000.00);