SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `tedbase` DEFAULT CHARACTER SET utf8 ;
USE `tedbase` ;

-- -----------------------------------------------------
-- Table `tedbase`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tedbase`.`user` (
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `surname` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(85) NULL DEFAULT NULL,
  `phoneNumber` BIGINT(30) NULL DEFAULT NULL,
  `afm` BIGINT(30) NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(45) NULL DEFAULT NULL,
  `checkUser` INT(11) NULL DEFAULT NULL,
  `latitude_user` DOUBLE NULL DEFAULT NULL,
  `longitude_user` DOUBLE NULL DEFAULT NULL,
  `user_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `location` VARCHAR(45) NULL DEFAULT NULL,
  `rating` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 26468
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tedbase`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tedbase`.`item` (
  `item_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `currently` VARCHAR(10) NULL DEFAULT NULL,
  `first_Bid` VARCHAR(10) NULL DEFAULT NULL,
  `number_of_Bids` INT(11) NULL DEFAULT NULL,
  `location` VARCHAR(200) NULL DEFAULT NULL,
  `started` BIGINT(20) NULL DEFAULT NULL,
  `ends` BIGINT(20) NULL DEFAULT NULL,
  `buy_price` BIGINT(20) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `latitude` DOUBLE NULL DEFAULT NULL,
  `longitude` DOUBLE NULL DEFAULT NULL,
  `seller_id` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  INDEX `fk_item_user1_idx` (`seller_id` ASC),
  CONSTRAINT `fk_item_user1`
    FOREIGN KEY (`seller_id`)
    REFERENCES `tedbase`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 100244
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tedbase`.`bids`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tedbase`.`bids` (
  `bid_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `amount` VARCHAR(10) NOT NULL,
  `time` BIGINT(20) NOT NULL,
  `bidder_id` BIGINT(20) UNSIGNED NOT NULL,
  `item_item_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`bid_id`),
  INDEX `fk_bids_user1_idx` (`bidder_id` ASC),
  INDEX `fk_bids_item1_idx` (`item_item_id` ASC),
  CONSTRAINT `fk_bids_item1`
    FOREIGN KEY (`item_item_id`)
    REFERENCES `tedbase`.`item` (`item_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_bids_user1`
    FOREIGN KEY (`bidder_id`)
    REFERENCES `tedbase`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 19004
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tedbase`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tedbase`.`category` (
  `category_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1035
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tedbase`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tedbase`.`image` (
  `image_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `item_image_id` BIGINT(20) UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`image_id`),
  INDEX `fk_image_item1_idx` (`item_image_id` ASC),
  CONSTRAINT `fk_image_item1`
    FOREIGN KEY (`item_image_id`)
    REFERENCES `tedbase`.`item` (`item_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 89
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tedbase`.`items_has_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tedbase`.`items_has_category` (
  `items_has_category_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `item_item_id` BIGINT(20) UNSIGNED NOT NULL,
  `category_category_id` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`items_has_category_id`, `item_item_id`, `category_category_id`),
  INDEX `fk_items_has_category_item1_idx` (`item_item_id` ASC),
  INDEX `fk_items_has_category_category1_idx` (`category_category_id` ASC),
  CONSTRAINT `fk_items_has_category_category1`
    FOREIGN KEY (`category_category_id`)
    REFERENCES `tedbase`.`category` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_items_has_category_item1`
    FOREIGN KEY (`item_item_id`)
    REFERENCES `tedbase`.`item` (`item_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 167376
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tedbase`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tedbase`.`message` (
  `message_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `message` TEXT NULL DEFAULT NULL,
  `from_id` BIGINT(20) UNSIGNED NOT NULL,
  `to_id` BIGINT(20) UNSIGNED NOT NULL,
  `hasReadMessage` INT(11) NOT NULL,
  PRIMARY KEY (`message_id`),
  INDEX `fk_message_user1_idx` (`from_id` ASC),
  INDEX `fk_message_user2_idx` (`to_id` ASC),
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`from_id`)
    REFERENCES `tedbase`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_message_user2`
    FOREIGN KEY (`to_id`)
    REFERENCES `tedbase`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tedbase`.`recommendedItems`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tedbase`.`recommendedItems` (
  `userId` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  `itemId` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  `recId` BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`recId`),
  INDEX `itemId` (`itemId` ASC),
  INDEX `userId` (`userId` ASC),
  CONSTRAINT `recommendedItems_ibfk_1`
    FOREIGN KEY (`userId`)
    REFERENCES `tedbase`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `recommendedItems_ibfk_2`
    FOREIGN KEY (`itemId`)
    REFERENCES `tedbase`.`item` (`item_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 37173
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
