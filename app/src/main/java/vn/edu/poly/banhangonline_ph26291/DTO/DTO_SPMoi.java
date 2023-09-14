package vn.edu.poly.banhangonline_ph26291.DTO;

import java.io.Serializable;

public class DTO_SPMoi implements Serializable {
    int id_spmoi;
    String ten_spmoi;
    String hinh_spmoi;
    String gia_spmoi;
    String mota;
    int id_loaisp;

    public DTO_SPMoi(int id_spmoi, String ten_spmoi, String hinh_spmoi, String gia_spmoi, String mota, int id_loaisp) {
        this.id_spmoi = id_spmoi;
        this.ten_spmoi = ten_spmoi;
        this.hinh_spmoi = hinh_spmoi;
        this.gia_spmoi = gia_spmoi;
        this.mota = mota;
        this.id_loaisp = id_loaisp;
    }

    public int getId_spmoi() {
        return id_spmoi;
    }

    public void setId_spmoi(int id_spmoi) {
        this.id_spmoi = id_spmoi;
    }

    public String getTen_spmoi() {
        return ten_spmoi;
    }

    public void setTen_spmoi(String ten_spmoi) {
        this.ten_spmoi = ten_spmoi;
    }

    public String getHinh_spmoi() {
        return hinh_spmoi;
    }

    public void setHinh_spmoi(String hinh_spmoi) {
        this.hinh_spmoi = hinh_spmoi;
    }

    public String getGia_spmoi() {
        return gia_spmoi;
    }

    public void setGia_spmoi(String gia_spmoi) {
        this.gia_spmoi = gia_spmoi;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getId_loaisp() {
        return id_loaisp;
    }

    public void setId_loaisp(int id_loaisp) {
        this.id_loaisp = id_loaisp;
    }

    public DTO_SPMoi() {
    }
}
