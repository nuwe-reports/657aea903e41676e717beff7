-- MySQL Script generated by MySQL Workbench
-- Sun Dec 17 18:08:38 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema accwe-hospital
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `accwe-hospital` ;

-- -----------------------------------------------------
-- Schema accwe-hospital
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `accwe-hospital` DEFAULT CHARACTER SET utf8 ;
USE `accwe-hospital` ;

-- -----------------------------------------------------
-- Table `accwe-hospital`.`appointment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `accwe-hospital`.`appointment` ;

CREATE TABLE IF NOT EXISTS `accwe-hospital`.`appointment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `finishes_at` DATE NOT NULL,
  `starts_at` DATE NOT NULL,
  `patient_id` INT NOT NULL,
  `doctors_id` INT NOT NULL,
  `room_roomName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_appointment_patient_idx` (`patient_id` ASC) VISIBLE,
  INDEX `fk_appointment_doctors1_idx` (`doctors_id` ASC) VISIBLE,
  INDEX `fk_appointment_room1_idx` (`room_roomName` ASC) VISIBLE,
  CONSTRAINT `fk_appointment_patient`
    FOREIGN KEY (`patient_id`)
    REFERENCES `accwe-hospital`.`patient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_doctors1`
    FOREIGN KEY (`doctors_id`)
    REFERENCES `accwe-hospital`.`doctors` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_room1`
    FOREIGN KEY (`room_roomName`)
    REFERENCES `accwe-hospital`.`room` (`roomName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `accwe-hospital`.`doctors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `accwe-hospital`.`doctors` ;

CREATE TABLE IF NOT EXISTS `accwe-hospital`.`doctors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `age` INT NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `accwe-hospital`.`patient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `accwe-hospital`.`patient` ;

CREATE TABLE IF NOT EXISTS `accwe-hospital`.`patient` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `age` INT NOT NULL,
  `email` VARCHAR(95) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `accwe-hospital`.`room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `accwe-hospital`.`room` ;

CREATE TABLE IF NOT EXISTS `accwe-hospital`.`room` (
  `roomName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`roomName`));


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;