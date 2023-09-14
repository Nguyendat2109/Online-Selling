package vn.edu.poly.banhangonline_ph26291.Model;

import java.util.ArrayList;
import java.util.List;

public class GioHang {
    int id_sp;
    String ten_sp;
    long gia_sp;
    String hinh_sp;
    int soluong;

    public GioHang() {
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public long getGia_sp() {
        return gia_sp;
    }

    public void setGia_sp(long gia_sp) {
        this.gia_sp = gia_sp;
    }

    public String getHinh_sp() {
        return hinh_sp;
    }

    public void setHinh_sp(String hinh_sp) {
        this.hinh_sp = hinh_sp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public static List<GioHang>list_giohang;
    public static List<GioHang>list_muahang=new ArrayList<>();
}
