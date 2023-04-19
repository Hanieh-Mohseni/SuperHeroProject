DROP DATABASE IF EXISTS superheroDB;
CREATE DATABASE superheroDB;

USE `superHeroDB` ;

-- -----------------------------------------------------
-- Table `superHeroDB`.`superHero`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superHeroDB`.`superHero` ;

CREATE TABLE IF NOT EXISTS `superHeroDB`.`superHero` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `superHeroDB`.`organization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superHeroDB`.`organization` ;

CREATE TABLE IF NOT EXISTS `superHeroDB`.`organization` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NULL,
  `address` VARCHAR(100) NOT NULL,
  `contact` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `superHeroDB`.`superOrganization`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superHeroDB`.`superOrganization` ;

CREATE TABLE IF NOT EXISTS `superHeroDB`.`superOrganization` (
  `heroId` INT NOT NULL,
  `orgId` INT NOT NULL,
  PRIMARY KEY (`heroId`, `orgId`),
  INDEX `fk_memberOrgnization_organization_idx` (`orgId` ASC) VISIBLE,
  CONSTRAINT `fk_memberOrgnization_members`
    FOREIGN KEY (`heroId`)
    REFERENCES `superHeroDB`.`superHero` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_memberOrgnization_organization`
    FOREIGN KEY (`orgId`)
    REFERENCES `superHeroDB`.`organization` (`id`)
);


-- -----------------------------------------------------
-- Table `superHeroDB`.`location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superHeroDB`.`location` ;

CREATE TABLE IF NOT EXISTS `superHeroDB`.`location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NULL,
  `address` VARCHAR(100) NOT NULL,
  `latitude` DECIMAL(10,7) NOT NULL,
  `longitude` DECIMAL(10,7) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `superHeroDB`.`sighting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superHeroDB`.`sighting` ;

CREATE TABLE IF NOT EXISTS `superHeroDB`.`sighting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `occurDate` DATE NOT NULL,
  `picture` VARCHAR(100) NULL,
  `heroId` INT NOT NULL,
  `locationId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sights_location_idx` (`locationId` ASC) VISIBLE,
  CONSTRAINT `fk_sights_location`
    FOREIGN KEY (`locationId`)
    REFERENCES `superHeroDB`.`location` (`id`)
);


-- -----------------------------------------------------
-- Table `superHeroDB`.`power`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superHeroDB`.`power` ;

CREATE TABLE IF NOT EXISTS `superHeroDB`.`power` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `superHeroDB`.`SuperPower`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `superHeroDB`.`SuperPower` ;

CREATE TABLE IF NOT EXISTS `superHeroDB`.`SuperPower` (
  `heroId` INT NOT NULL,
  `powerId` INT NOT NULL,
  PRIMARY KEY (`heroId`),
  INDEX `fk_hero_power_idx` (`powerId` ASC) VISIBLE,
  CONSTRAINT `fk_superpower_power`
    FOREIGN KEY (`powerId`)
    REFERENCES `superHeroDB`.`power` (`id`),
  CONSTRAINT `fk_superpower_hero`
    FOREIGN KEY (`heroId`)
    REFERENCES `superHeroDB`.`superHero` (`id`)
);