DROP TABLE IF EXISTS `simpleague`.`league`;

CREATE TABLE `simpleague`.`league` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `user_id` int(11) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  CONSTRAINT `league_user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DELIMITER $$

/*DROP TRIGGER `simpleague`.`bu_league`$$*/

CREATE
    TRIGGER `simpleague`.`bu_league` BEFORE UPDATE ON `simpleague`.`league` 
    FOR EACH ROW BEGIN
      SET NEW.modified_date = CURRENT_TIMESTAMP;
END;
$$
