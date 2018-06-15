CREATE TABLE .`user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `password_text` varchar(10) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
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
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1;

DELIMITER $$

/*DROP TRIGGER `simpleague`.`bu_user`$$*/

CREATE
    TRIGGER `simpleague`.`bu_user` BEFORE UPDATE ON `simpleague`.`user` 
    FOR EACH ROW BEGIN
      SET NEW.modified_date = CURRENT_TIMESTAMP;
END;
$$
