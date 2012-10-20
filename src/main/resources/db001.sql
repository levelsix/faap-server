CREATE DATABASE gameaday;

CREATE TABLE `user_id` (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `macAddress` varchar(100) NOT NULL,
  `odin1` varchar(100) NOT NULL,
  `openUdid` varchar(100) NOT NULL,
   PRIMARY KEY (`user_id`)
) ENGINE=MyISam DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX `id_uid_mac` ON `user_id` (macAddress);
CREATE UNIQUE INDEX `id_uid_odin` ON `user_id` (odin1);
CREATE UNIQUE INDEX `id_uid_udid` ON `user_id` (openUdid);



