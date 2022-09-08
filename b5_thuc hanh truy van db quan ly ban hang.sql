SELECT * FROM sanpham
where not exists (select MaSP from cthd where MaSP = sanpham.MaSP) ;

SELECT * FROM sanpham
where MaSP not in  (SELECT MaSP FROM c5_qlybanhang.cthd);

# 33. Tính tổng số lượng của từng sản phẩm bán ra trong ngày 28/10/2006.
#cau lenh khi chua co group by
select *
from hoadon hd join cthd ct on hd.SoHoaDon = ct.SoHD
where NgayMuaHang = '2006-10-28';

SELECT MASP,SUM(SoLuong)
FROM  HOADON A, CTHD B
WHERE A.SoHoaDon = B.SoHD AND DAY(NgayMuaHang) = 28 AND MONTH(NgayMuaHang)=10 AND YEAR(NgayMuaHang)=2006 
GROUP BY MASP;

# 29. Tính tổng số sản phẩm do “Trung Quốc” sản xuất.
select count(MaSP)
from sanpham
where NuocSX = 'Trung Quoc';

-- 26. In ra danh sách các sản phẩm (MASP, TENSP) do “Trung Quốc” sản xuất có giá 
-- bằng 1 trong 3 mức giá thấp nhất (của tất cả các sản phẩm).

select *
from sanpham
order by Gia asc;

-- select TOP 3 *
-- from sanpham
-- order by Gia asc;

select *
from sanpham
order by Gia asc
limit 3;


select sp.MaSP, sp.TenSP
from sanpham sp join (select * from sanpham order by Gia asc limit 3) as temp on sp.Gia = temp.gia
where sp.NuocSX = 'Trung Quoc';


# 36. Tìm sản phẩm (MASP, TENSP) có tổng số lượng bán ra thấp nhất trong năm 2006.
select ct.MaSP, sp.TenSP, ct.SoLuong, sum(ct.SoLuong) as tong
from cthd ct join hoadon hd on ct.SoHD = hd.SoHoaDon join sanpham sp on sp.MaSP = ct.MaSP
where year(NgayMuaHang) = '2006'
group by MaSP
order by tong
limit 3;

select ct.MaSP, sp.TenSP, sum(ct.SoLuong) as tong
from cthd ct join hoadon hd on ct.SoHD = hd.SoHoaDon join sanpham sp on sp.MaSP = ct.MaSP
where year(NgayMuaHang) = '2006'
group by MaSP
order by tong asc;


SELECT B.MaSP, TenSP, SUM(SoLuong)
FROM  sanpham A, cthd B, hoadon C
WHERE A.MaSP=B.MaSP AND B.SoHD=C.SoHoaDon AND YEAR(NgayMuaHang)=2006
GROUP BY B.MASP,TENSP
HAVING SUM(SoLuong)<=ALL(SELECT  SUM(SoLuong)
     FROM  cthd A, hoadon B
     WHERE A.SoHD=B.SoHoaDon AND YEAR(NgayMuaHang)=2006
     GROUP BY MaSP)
limit 1;











