package vn.edu.poly.banhangonline_ph26291.DTO;

import java.util.List;

public class DTO_DonHang {
   int id_donhang;
   String ten_sp;
   String hinh_sp;
   String gia_sp;
   int soluongct;
   String tongtien;



    public DTO_DonHang(int id_donhang, String ten_sp, String hinh_sp, String gia_sp, int soluongct, String tongtien) {
        this.id_donhang = id_donhang;
        this.ten_sp = ten_sp;
        this.hinh_sp = hinh_sp;
        this.gia_sp = gia_sp;
        this.soluongct = soluongct;
        this.tongtien = tongtien;
    }

    public int getId_donhang() {
        return id_donhang;
    }

    public void setId_donhang(int id_donhang) {
        this.id_donhang = id_donhang;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public String getHinh_sp() {
        return hinh_sp;
    }

    public void setHinh_sp(String hinh_sp) {
        this.hinh_sp = hinh_sp;
    }

    public String getGia_sp() {
        return gia_sp;
    }

    public void setGia_sp(String gia_sp) {
        this.gia_sp = gia_sp;
    }

    public int getSoluongct() {
        return soluongct;
    }

    public void setSoluongct(int soluongct) {
        this.soluongct = soluongct;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }


}
