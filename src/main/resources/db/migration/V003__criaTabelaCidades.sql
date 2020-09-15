CREATE TABLE IF NOT EXISTS `estudo`.`CIDADES` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NOT NULL,
  `id_estados` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_estados_idx` (`id_estados` ASC) VISIBLE,
  CONSTRAINT `id_estados`
    FOREIGN KEY (`id_estados`)
    REFERENCES `estudo`.`ESTADOS` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
