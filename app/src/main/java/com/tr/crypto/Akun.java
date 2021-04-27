package com.tr.crypto;

public class Akun {
    private String Nama_panjang;
    private String Nama_akun;
    private String email;
    private String password;

    public Akun() {
    }

    public Akun(String Nama_panjang, String Nama_akun, String email, String password) {
        this.Nama_panjang = Nama_panjang;
        this.Nama_akun = Nama_akun;
        this.email = email;
        this.password = password;
    }

    public String getNama_panjang() {
        return Nama_panjang;
    }

    public void setNama_panjang(String Nama_panjang) {
        this.Nama_panjang = Nama_panjang;
    }

    public String getNama_akun() {
        return Nama_akun;
    }

    public void setNama_akun(String Nama_akun) {
        this.Nama_akun = Nama_akun;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
