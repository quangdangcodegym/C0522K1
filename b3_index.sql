SET SQL_SAFE_UPDATES = 0;
truncate student;
ALTER TABLE student AUTO_INCREMENT=1;

SET @@local.net_read_timeout=360;
SET GLOBAL connect_timeout=28800;

# Dem so dong cua bang student hien tai
SELECT count(*) FROM c5_index.student;

# su dung explain de hieu ve  index cho primary key
EXPLAIN select * from c5_index.student where id = 90001;
EXPLAIN select * from c5_index.student where email = 'emailcodegym90000';

# su dung explain de hieu ve  index cho primary key
select * from c5_index.student where id = 90001;
select * from c5_index.student where email = 'emailcodegym90000';
delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_inIt_Table_Student`(
	IN sSize int,
    OUT sMessage varchar(200)
)
BEGIN
	declare v_counter int unsigned default 0;
    /**
    DECLARE exit handler for sqlexception
	  BEGIN
		-- ERROR
	  ROLLBACK;
	END;
    **/
    
	if(sSize <100) then
		set sMessage = 'Size qua nho, khong them them';
    end if;
  start transaction;
  while v_counter < sSize do
    insert into student (email) values (concat('emailcodegym', v_counter) );
    /**
    if(v_counter=10) then 
		insert into student (id, email) values (10, concat('emailcodegym', 10) );
    end if;
    **/
    set v_counter=v_counter+1;
  end while;
  commit;
  set sMessage = 'Them thanh cong';
END //