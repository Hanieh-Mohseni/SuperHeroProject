DROP DATABASE IF EXISTS superheroDBTest;
CREATE DATABASE superheroDBTest;

USE `superheroDBTest` ;

-- -----------------------------------------------------
-- Table `superheroDBTest`.`superHero`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroDBTest`.`superHero` ;

CREATE TABLE IF NOT EXISTS `superheroDBTest`.`superHero` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `superheroDBTest`.`organization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroDBTest`.`organization` ;

CREATE TABLE IF NOT EXISTS `superheroDBTest`.`organization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(100) NULL,
  `address` VARCHAR(100) NULL,
  `contact` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `superheroDBTest`.`superOrganization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroDBTest`.`superOrganization` ;

CREATE TABLE IF NOT EXISTS `superheroDBTest`.`superOrganization` (
  `heroId` INT NOT NULL,
  `orgId` INT NOT NULL,
  PRIMARY KEY (`heroId`, `orgId`),
  INDEX `fk_memberOrgnization_organization_idx` (`orgId` ASC) VISIBLE,
  CONSTRAINT `fk_memberOrgnization_members`
    FOREIGN KEY (`heroId`)
    REFERENCES `superheroDBTest`.`superHero` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_memberOrgnization_organization`
    FOREIGN KEY (`orgId`)
    REFERENCES `superheroDBTest`.`organization` (`id`)
);


-- -----------------------------------------------------
-- Table `superheroDBTest`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroDBTest`.`location` ;

CREATE TABLE IF NOT EXISTS `superheroDBTest`.`location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NULL,
  `address` VARCHAR(100) NOT NULL,
  `latitude` DECIMAL(10,7) NOT NULL,
  `longitude` DECIMAL(10,7) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `superheroDBTest`.`sighting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroDBTest`.`sighting` ;

CREATE TABLE IF NOT EXISTS `superheroDBTest`.`sighting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `occurDate` DATE NOT NULL,
  `picture` VARCHAR(100) NULL,
  `heroId` INT NOT NULL,
  `locationId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sights_location_idx` (`locationId` ASC) VISIBLE,
  CONSTRAINT `fk_sights_location`
    FOREIGN KEY (`locationId`)
    REFERENCES `superheroDBTest`.`location` (`id`)
);


-- -----------------------------------------------------
-- Table `superheroDBTest`.`power`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroDBTest`.`power` ;

CREATE TABLE IF NOT EXISTS `superheroDBTest`.`power` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `superheroDBTest`.`SuperPower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superheroDBTest`.`SuperPower` ;

CREATE TABLE IF NOT EXISTS `superheroDBTest`.`SuperPower` (
  `heroId` INT NOT NULL,
  `powerId` INT NOT NULL,
  PRIMARY KEY (`heroId`),
  INDEX `fk_hero_power_idx` (`powerId` ASC) VISIBLE,
  CONSTRAINT `fk_superpower_power`
    FOREIGN KEY (`powerId`)
    REFERENCES `superheroDBTest`.`power` (`id`),
  CONSTRAINT `fk_superpower_hero`
    FOREIGN KEY (`heroId`)
    REFERENCES `superheroDBTest`.`superHero` (`id`)
);