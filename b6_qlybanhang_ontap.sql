
# Cau 7:  Đơn vị tính của sản phẩm chỉ có thể là (“cây”,”hộp”,”cái”,”quyển”,”chục”)
ALTER TABLE sanpham
ADD CHECK (DVT IN ('cay', 'hop', 'quyen', 'chuc', 'cai'));


ALTER TABLE sanpham
ADD CHECK (DVT = 'cay' or DVT = 'hop' or DVT = 'quyen' or DVT = 'chuc' or DVT = 'cai');

# 10. Ngày gia nhập của khách hàng thành viên phải lớn hơn ngày sinh của người đó.
ALTER TABLE khachhang
ADD CHECK (NgaySinh < NgayDangKi);

delimiter //
create trigger insert_hoadon
after insert
on hoadon for each row
begin 
	update hoadon
    set TriGia = 0
    where SoHoaDon = NEW.SoHoaDon;
end//

-- CAU 10) In ra danh sách các sản phẩm (MASP,TENSP) được khách hàng có tên “Nguyễn Văn A” mua trong háng 10/2006.
SELECT sanpham.MaSP,sanpham.TenSP,hoadon.MaKH
FROM hoadon,khachhang,cthd,sanpham
WHERE hoadon.MaKH = KHACHHANG.MaKH AND hoadon.SoHoaDon = cthd.SoHD AND cthd.MaSP = sanpham.MaSP AND
MONTH(NgayMuaHang) = 10 AND YEAR(NgayMuaHang) = 2006 AND HOTEN = 'NGUYEN VAN A' ;
