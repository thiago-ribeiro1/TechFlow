-- MySQL Workbench Synchronization
-- Generated: 2025-01-29 14:51
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: vanin

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `techflow` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `techflow`.`cliente` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `techflow`.`produto` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(500) NOT NULL,
  `valor` DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `techflow`.`pedido` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `data_hora` DATETIME NOT NULL,
  `observacao` VARCHAR(300) NULL DEFAULT NULL,
  `metodo_pagamento` ENUM("PIX", "BOLETO", "CARTAO_CREDITO", "CARTAO_DEBITO") NOT NULL,
  `cliente_id` INT(11) NOT NULL,
  `endereco` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pedido_cliente1_idx` (`cliente_id` ASC) VISIBLE,
  CONSTRAINT `fk_pedido_cliente1`
    FOREIGN KEY (`cliente_id`)
    REFERENCES `techflow`.`cliente` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `techflow`.`estoque` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `quantidade` INT(11) NOT NULL,
  `produto_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_estoque_produto_idx` (`produto_id` ASC) VISIBLE,
  UNIQUE INDEX `produto_id_UNIQUE` (`produto_id` ASC) VISIBLE,
  CONSTRAINT `fk_estoque_produto`
    FOREIGN KEY (`produto_id`)
    REFERENCES `techflow`.`produto` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `techflow`.`status_pedido` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `status` ENUM("EM_ANDAMENTO", "SEM_ESTOQUE", "PAGAMENTO_REALIZADO", "FALHA_PAGAMENTO", "RETIRADA_APROVADA", "ENVIADO", "FALHA_ENVIO") NOT NULL,
  `pedido_id` INT(11) NOT NULL,
  `data_hora` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_status_pedido_pedido1_idx` (`pedido_id` ASC) VISIBLE,
  CONSTRAINT `fk_status_pedido_pedido1`
    FOREIGN KEY (`pedido_id`)
    REFERENCES `techflow`.`pedido` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `techflow`.`item_pedido` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `produto_id` INT(11) NOT NULL,
  `quantidade_produto` INT(11) NOT NULL,
  `pedido_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_produto_quantidade_pedido1_idx` (`pedido_id` ASC) VISIBLE,
  INDEX `fk_produto_quantidade_produto1_idx` (`produto_id` ASC) VISIBLE,
  CONSTRAINT `fk_produto_quantidade_pedido1`
    FOREIGN KEY (`pedido_id`)
    REFERENCES `techflow`.`pedido` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_produto_quantidade_produto1`
    FOREIGN KEY (`produto_id`)
    REFERENCES `techflow`.`produto` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
