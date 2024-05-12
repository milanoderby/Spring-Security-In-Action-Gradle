CREATE TABLE IF NOT EXISTS `_user` (
    `username` VARCHAR(45) NULL,
    `password` TEXT NULL,
    PRIMARY KEY (`username`)
);

CREATE TABLE IF NOT EXISTS `_otp` (
    `username` VARCHAR(45) NOT NULL,
    `code` VARCHAR(45) NULL,
    PRIMARY KEY (`username`)
);