CREATE DATABASE gameaday;

DROP TABLE `user_id`;

CREATE TABLE `user_id` (
  `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `macAddress` varchar(100) NOT NULL,
  `odin1` varchar(100) NOT NULL,
  `open_udid` varchar(100) NOT NULL,
  `email` varchar(100),
  `device_token` varchar(100),
   PRIMARY KEY (`user_id`)
) ENGINE=MyISam DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX `id_uid_mac` ON `user_id` (macAddress);
CREATE UNIQUE INDEX `id_uid_odin` ON `user_id` (odin1);
CREATE UNIQUE INDEX `id_uid_udid` ON `user_id` (open_udid);


DROP TABLE `games`;

CREATE TABLE `games` (
  `game_id` varchar(100) NOT NULL,
  `game_title` varchar(100) NOT NULL,
  `user_count` int NOT NULL,
  `game_description` varchar(1000) NOT NULL,
  `review_title` varchar(100) NOT NULL,
  `review_description` varchar(1000) NOT NULL,
  `price` varchar(100) NOT NULL,
  `rating_artwork` decimal(3,1) NOT NULL,
  `rating_fun` decimal(3,1) NOT NULL,
  `rating_gameplay` decimal(3,1) NOT NULL,
  `rating_sound` decimal(3,1) NOT NULL,
  `rating_story` decimal(3,1) NOT NULL,
   PRIMARY KEY (`game_id`)
) ENGINE=MyISam DEFAULT CHARSET=utf8;


DROP TABLE `calendar`;

CREATE TABLE `calendar` (
  `game_id` varchar(100) NOT NULL,
  `date_free` date NOT NULL,
   PRIMARY KEY (`date_free`)
) ENGINE=MyISam DEFAULT CHARSET=utf8;

CREATE INDEX `idx_cal_games` ON `calendar` (game_id);

DROP TABLE `ratings`;

CREATE TABLE `ratings` (
  `game_id` varchar(100) NOT NULL,
  `user_id` int NOT NULL,
  `rating` int NOT NULL,
   PRIMARY KEY (`game_id`,`user_id`)
) ENGINE=MyISam DEFAULT CHARSET=utf8;

CREATE INDEX `idx_rating_games` ON `ratings` (game_id);

DROP TABLE `tracking`;

CREATE TABLE `tracking` (
  `user_id` int NOT NULL,
  `game_id` varchar(100) NOT NULL,
  `date_free` date NOT NULL,
  `impression` int NOT NULL,
  `download` int NOT NULL,
   PRIMARY KEY (`game_id`,`user_id`, `date_free`)
) ENGINE=MyISam DEFAULT CHARSET=utf8;

CREATE INDEX `idx_tracking_games` ON `ratings` (game_id);
CREATE INDEX `idx_tracking_users` ON `ratings` (user_id);
CREATE INDEX `idx_tracking_gamesusers` ON `ratings` (game_id, user_id);


## start data;

INSERT INTO `games` VALUES ('ageofchaos','Age of Chaos',12342,'Greatest game ever','You should all play this game!','Stellar Fun','$1.99',9.5,8.5,9.0,9.5,9.5);

INSERT INTO `calendar` VALUES ('ageofchaos','2012-10-20'),('ageofchaos','2012-10-21'),('ageofchaos','2012-10-19'),('ageofchaos','2012-10-22'),('ageofchaos','2012-10-23'),('ageofchaos','2012-10-24'),('ageofchaos','2012-10-25'),('ageofchaos','2012-10-26'),('ageofchaos','2012-10-27'),('ageofchaos','2012-10-28'),('ageofchaos','2012-10-29'),('ageofchaos','2012-10-30'),('ageofchaos','2012-10-31');
