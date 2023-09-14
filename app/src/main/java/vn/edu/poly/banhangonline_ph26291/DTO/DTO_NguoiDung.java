package vn.edu.poly.banhangonline_ph26291.DTO;

public class DTO_NguoiDung {
    int id_nguoidung;
    String email;
    String user;
    String pass;
    String numberphone;

    public DTO_NguoiDung() {
    }

    public DTO_NguoiDung(int id_nguoidung, String email, String numberphone,String user, String pass ) {
        this.id_nguoidung = id_nguoidung;
        this.email = email;
        this.user = user;
        this.pass = pass;
        this.numberphone = numberphone;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }
}
