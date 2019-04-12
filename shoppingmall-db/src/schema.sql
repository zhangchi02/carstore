
-- 创建数据库
create database `ShoppingMallDB` default character set utf8 collate utf8_general_ci;

use ShoppingMallDB;

-- 建表
CREATE TABLE `product_table` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`name` TEXT NULL,
	`price` DOUBLE NULL DEFAULT NULL,
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=5
;

CREATE TABLE `user_table` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`name` TEXT NOT NULL,
	`password` TEXT NOT NULL,
	`created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

CREATE TABLE `payment_table` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`productid` TEXT NOT NULL,
	`userid` TEXT NOT NULL,
	`created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

SET GLOBAL event_scheduler = OFF;
SET GLOBAL event_scheduler = ON;


CREATE EVENT AutoDeleteOlProduct

ON SCHEDULE EVERY '1' HOUR
  STARTS CURRENT_TIMESTAMP
  ON COMPLETION PRESERVE
  DO 
DELETE  FROM ShoppingMallDB.payment_table WHERE created < DATE_SUB(NOW(), INTERVAL 1 DAY);

-- 插入数据
INSERT INTO `user_table` (`id`, `name`, `password`, `created`)
VALUES
    (0,'apm','123456',Now());
INSERT INTO `user_table` (`id`, `name`, `password`, `created`)
VALUES
    (1,'abc','123456',Now());
INSERT INTO `user_table` (`id`, `name`, `password`, `created`)
VALUES
    (2,'paas','123456',Now());
INSERT INTO `user_table` (`id`, `name`, `password`, `created`)
VALUES
    (3,'user','123456',Now());

INSERT INTO `product_table` (`id`, `name`, `price`, `created`)
VALUES
    (34211223411,'Aston Martin Zagato',10000000,Now());
INSERT INTO `product_table` (`id`, `name`, `price`, `created`)
VALUES
    (34211223412,'Ferrari GTC4Lusso',4888888,Now());
INSERT INTO `product_table` (`id`, `name`, `price`, `created`)
VALUES
    (34211223413,'Bugatti Chiron',20000000,Now());
INSERT INTO `product_table` (`id`, `name`, `price`, `created`)
VALUES
    (34211223414,'Ferrari FF 2018',5000000,Now());