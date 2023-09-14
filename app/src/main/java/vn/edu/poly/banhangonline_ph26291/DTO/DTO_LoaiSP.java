package vn.edu.poly.banhangonline_ph26291.DTO;

public class DTO_LoaiSP {
    public int id_loaisp;
    public String tenloaisp;
    public String hinhloaisp;



    public DTO_LoaiSP(int id_loaisp, String tenloaisp, String hinhloaisp) {
        this.id_loaisp = id_loaisp;
        this.tenloaisp = tenloaisp;
        this.hinhloaisp = hinhloaisp;
    }

    public int getId_loaisp() {
        return id_loaisp;
    }

    public void setId_loaisp(int id_loaisp) {
        this.id_loaisp = id_loaisp;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhloaisp() {
        return hinhloaisp;
    }

    public void setHinhloaisp(String hinhloaisp) {
        this.hinhloaisp = hinhloaisp;
    }
}
