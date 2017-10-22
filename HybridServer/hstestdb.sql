DROP DATABASE IF EXISTS `hstestdb`;
CREATE DATABASE IF NOT EXISTS `hstestdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
USE `hstestdb`;

SET SQL_MODE = '';
GRANT USAGE ON *.* TO hsdb@localhost;
 DROP USER hsdb@localhost;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'hsdb'@'localhost' IDENTIFIED BY 'hsdbpass';


GRANT ALL ON `hstestdb`.* TO 'hsdb'@'localhost';


DROP TABLE IF EXISTS ` hstestdb`.`html` ;

CREATE TABLE IF NOT EXISTS `HTML` (
  `uuid` CHAR(36) NOT NULL,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`uuid`))ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;;

;
