INSERT INTO `_user` (`username`, `password`, `algorithm`) VALUES ('john', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');

INSERT INTO `_authority` (`name`, `user_id`) VALUES ('READ', '1');
INSERT INTO `_authority` (`name`, `user_id`) VALUES ('WRITE', '1');

INSERT INTO `_product` (`name`, `price`, `currency`) VALUES ('Chocolate', '10', 'USD');