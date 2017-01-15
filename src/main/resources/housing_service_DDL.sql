-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema housing_service
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema housing_service
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `housing_service` DEFAULT CHARACTER SET utf8 ;
USE `housing_service` ;

-- -----------------------------------------------------
-- Table `housing_service`.`work_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `housing_service`.`work_type` (
  `WORK_TYPE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `TYPE_NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`WORK_TYPE_ID`),
  UNIQUE INDEX `TYPE_NAME_UNIQUE` (`TYPE_NAME` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `housing_service`.`brigade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `housing_service`.`brigade` (
  `BRIGADE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `BRIGADE_NAME` VARCHAR(45) NOT NULL,
  `WORK_TYPE_ID` INT(11) NOT NULL,
  PRIMARY KEY (`BRIGADE_ID`),
  INDEX `BRIGADE_WORK_TYPE_ID_idx` (`WORK_TYPE_ID` ASC),
  CONSTRAINT `BRIGADE_WORK_TYPE_ID`
    FOREIGN KEY (`WORK_TYPE_ID`)
    REFERENCES `housing_service`.`work_type` (`WORK_TYPE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `housing_service`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `housing_service`.`role` (
  `ROLE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `ROLE_TYPE` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE INDEX `ROLE_TYPE_UNIQUE` (`ROLE_TYPE` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `housing_service`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `housing_service`.`user` (
  `USER_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `LOGIN` VARCHAR(45) NOT NULL,
  `PASSWD` VARCHAR(45) NOT NULL,
  `ROLE_ID` INT(11) NOT NULL,
  `FIRST_NAME` VARCHAR(45) NOT NULL,
  `LAST_NAME` VARCHAR(45) NOT NULL,
  `POSITION` VARCHAR(45) NULL DEFAULT NULL,
  `BRIGADE_ID` INT(11) NULL DEFAULT NULL,
  `STREET` VARCHAR(45) NOT NULL,
  `HOUSE_NUMBER` VARCHAR(45) NOT NULL,
  `APARTMENT` INT(11) NOT NULL,
  `CITY` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE INDEX `LOGIN_UNIQUE` (`LOGIN` ASC),
  INDEX `USER_ROLE_ID_idx` (`ROLE_ID` ASC),
  INDEX `USER_BRIGADE_ID_idx` (`BRIGADE_ID` ASC),
  CONSTRAINT `USER_BRIGADE_ID`
    FOREIGN KEY (`BRIGADE_ID`)
    REFERENCES `housing_service`.`brigade` (`BRIGADE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `USER_ROLE_ID`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `housing_service`.`role` (`ROLE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `housing_service`.`bid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `housing_service`.`bid` (
  `BID_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `WORK_TYPE_ID` INT(11) NOT NULL,
  `WORK_SCOPE` INT(1) NOT NULL,
  `LEAD_TIME` DATETIME NULL DEFAULT NULL,
  `USER_TENANT_ID` INT(11) NOT NULL,
  `STATUS` VARCHAR(45) NOT NULL,
  `DESCRIPTION` VARCHAR(45) NULL DEFAULT NULL,
  `BID_TIME` DATETIME NOT NULL,
  PRIMARY KEY (`BID_ID`),
  INDEX `BID_USER_TENANT_ID_idx` (`USER_TENANT_ID` ASC),
  INDEX `BID_WORK_TYPE_ID_idx` (`WORK_TYPE_ID` ASC),
  CONSTRAINT `BID_USER_TENANT_ID`
    FOREIGN KEY (`USER_TENANT_ID`)
    REFERENCES `housing_service`.`user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `BID_WORK_TYPE_ID`
    FOREIGN KEY (`WORK_TYPE_ID`)
    REFERENCES `housing_service`.`work_type` (`WORK_TYPE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `housing_service`.`work_plane`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `housing_service`.`work_plane` (
  `WORK_PLANE_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `USER_ADVISOR_ID` INT(11) NOT NULL,
  `BRIGADE_ID` INT(11) NOT NULL,
  `BID_ID` INT(11) NOT NULL,
  `STATUS` VARCHAR(45) NOT NULL,
  `WORK_TIME` DATETIME NULL DEFAULT NULL,
  `COMPLETE_TIME` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`WORK_PLANE_ID`),
  INDEX `BRIGADE_ID_idx` (`BRIGADE_ID` ASC),
  INDEX `BID_ID_idx` (`BID_ID` ASC),
  INDEX `PLANE_USER_ADVISOR_ID_idx` (`USER_ADVISOR_ID` ASC),
  CONSTRAINT `BID_ID`
    FOREIGN KEY (`BID_ID`)
    REFERENCES `housing_service`.`bid` (`BID_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `BRIGADE_ID`
    FOREIGN KEY (`BRIGADE_ID`)
    REFERENCES `housing_service`.`brigade` (`BRIGADE_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PLANE_USER_ADVISOR_ID`
    FOREIGN KEY (`USER_ADVISOR_ID`)
    REFERENCES `housing_service`.`user` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;