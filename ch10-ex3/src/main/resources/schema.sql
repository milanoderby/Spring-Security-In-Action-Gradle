CREATE TABLE IF NOT EXISTS `_token` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `identifier` VARCHAR(45) NULL,
    `token` TEXT NULL,
    PRIMARY KEY (`id`)
);