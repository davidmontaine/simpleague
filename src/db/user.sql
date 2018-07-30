/*DROP DATABASE `simpleague`;*/

/*CREATE DATABASE `simpleague`*/ /*!40100 DEFAULT CHARACTER SET latin1 */;

DROP TABLE IF EXISTS `simpleague`.`user`;

CREATE TABLE `simpleague`.`user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `group_name` varchar(10) NOT NULL DEFAULT 'USER',
  `uuid` char(36) NOT NULL,
  `verified` enum('Y','N') NOT NULL DEFAULT 'N',
  `email_count` int(11) NOT NULL DEFAULT '0',
  `password_count` int(11) NOT NULL DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '0',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `uuid_UNIQUE` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DELIMITER $$

/*DROP TRIGGER `simpleague`.`bu_user`$$*/

CREATE
    TRIGGER `simpleague`.`bu_user` BEFORE UPDATE ON `simpleague`.`user` 
    FOR EACH ROW BEGIN
      SET NEW.modified_date = CURRENT_TIMESTAMP;
END;
$$
