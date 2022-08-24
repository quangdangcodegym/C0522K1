# Tao bang
CREATE TABLE `c5_demo`.`class` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  /**
  **/
-- Them du lieu
INSERT INTO `c5_demo`.`class` (`id`, `name`) VALUES ('101', 'C07');
INSERT INTO `c5_demo`.`class` (`id`, `name`) VALUES ('102', 'C07');
INSERT INTO `c5_demo`.`class` (`id`, `name`) VALUES ('103', 'C07');

-- Sua du lieu 
UPDATE `c5_demo`.`class` SET `name` = 'C08' WHERE (`id` = '101');

-- Khi thay doi ten bang, ten cot, kieu du lieu cot, rang buoc thi dung alter
ALTER TABLE `c5_demo`.`class` 
CHANGE COLUMN `name` `nameClass` VARCHAR(200) NULL DEFAULT NULL ;

-- Muon xoa 1 bang, hay 1 cot thi dung drop
ALTER TABLE `c5_demo`.`student` 
DROP COLUMN `name1`;

-- Cau lenh exist: kiem tra su ton tai
select exists (select * from student where StudentName like 'PhuLe');
select 'Luong';

-- Toan tu like
-- like %: dai dien cho 0, 1 hoac nhieu ki tu
-- like _: dai dien 1 ki tu, (Vi du ki tu ___

## and, in, betwwen, or




