CREATE TABLE IF NOT EXISTS `estudo`.`CIDADES` (
  `id_Cidades` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(60) NOT NULL,
  `id_estados` INT NULL,
  PRIMARY KEY (`id_Cidades`),
  INDEX `id_estados_idx` (`id_estados` ASC) VISIBLE,
  CONSTRAINT `id_estados`
    FOREIGN KEY (`id_estados`)
    REFERENCES `estudo`.`ESTADOS` (`id_estados`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB