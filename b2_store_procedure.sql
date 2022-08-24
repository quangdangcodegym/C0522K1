delimiter //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insert_class`(
	IN size int,
    OUT message varchar(200)
)
begin
	declare v_counter int unsigned default 0;

	if(size < 10) then
		set message = 'size kich thuoc qua nho, khong thich nhap';
    end if;
    
    while v_counter < size do
		INSERT INTO `quanlysinhvien`.`class` (`ClassName`, `StartDate`, `Status`) VALUES (concat('D',v_counter), now(), b'1');
		set v_counter=v_counter+1;
	end while;
    
end;//
# Kiem tra so dien thoai co ton tai hay chua
select exists (select phone from student where phone = '0399578134');

## viet 1 store procdue
-- nhap vao cac thong tin: name, address, classid,, phone age. Kem 1 tham so out hien thi message
--  + Kiem tra xem so dien thoai chua ton tai moi duoc them
--  + Kiem tra classid da ton tai moi duoc them
--  + Sau khi kiem tra thanh cong thi moi duoc them
 
delimiter //
create procedure sp_insert_student_c5(
	IN sName varchar(200),
    IN sAddress varchar(200),
    IN sPhone varchar(11),
    IN sStatus boolean, 
    IN sClassId int,
    IN sAge int,
    OUT sMessage varchar(100)
)
begin
	if(exists (select phone from student where phone = sPhone)) then
		set sMessage = 'So dien thoai da ton tai';
        end if;
	if(not exists (select ClassID from class where ClassID = sClassId)) then
		set sMessage = 'Class id chua ton tai';
        end if;
end;//


# store procedure nạp tiền
delimiter //
create procedure sp_deposit(
	IN sCustomerId int, 
    IN sMoney decimal(12,0),
    OUT sMessage varchar(200)
)
begin
	declare flag boolean;
    declare total decimal(12,0);
    set flag = true;
    
	if(not exists (select id from customers where id = sCustomerId)) then 
		set sMessage = 'Nguoi dung khong ton tai';
        set flag = false;
	end if;
    if(sMoney <= 0) then
		set sMessage = 'So tien nap phai lon hon khong';
        set flag = false;
    end if;
    
    if(flag = true) then
		INSERT INTO `c5_banking`.`deposits` (`created_at`, `deleted`, `customer_id`, `transaction_amount`) 
        VALUES (now(), 0, sCustomerId, sMoney);
        
        set total = (SELECT balance FROM c5_banking.customers where id = sCustomerId);
		set total = total + sMoney;
        UPDATE `c5_banking`.`customers` SET `balance` = total WHERE (`id` = sCustomerId);
	end if;
end;