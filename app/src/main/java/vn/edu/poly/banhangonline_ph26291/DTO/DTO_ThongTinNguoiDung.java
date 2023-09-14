package vn.edu.poly.banhangonline_ph26291.DTO;

public class DTO_ThongTinNguoiDung {
    int id_nguoidung;
    String email;
    String nbphonel;

    public DTO_ThongTinNguoiDung() {
    }

    public DTO_ThongTinNguoiDung(int id_nguoidung, String email, String nbphonel) {
        this.id_nguoidung = id_nguoidung;
        this.email = email;
        this.nbphonel = nbphonel;
    }

    public int getId_nguoidung() {
        return id_nguoidung;
    }

    public void setId_nguoidung(int id_nguoidung) {
        this.id_nguoidung = id_nguoidung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNbphonel() {
        return nbphonel;
    }

    public void setNbphonel(String nbphonel) {
        this.nbphonel = nbphonel;
    }
}
