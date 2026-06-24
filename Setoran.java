/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankSampah;

import java.text.DecimalFormat;

/**
 *
 * @author ditia
 */
public class Setoran {

    // atribut dibuat private supaya aman (encapsulation)
    private String no;
    private String anggota;
    private String jenisSampah;
    private double berat;
    private double saldo;

    // constructor
    public Setoran(String no, String anggota, String jenisSampah, double berat, double saldo) {
        this.no = no;
        this.anggota = anggota;
        this.jenisSampah = jenisSampah;
        this.berat = berat;
        this.saldo = saldo;
    }

    // getter setter
    public String getNo() {
        return no;
    }

    public String getAnggota() {
        return anggota;
    }

    public void setAnggota(String anggota) {
        this.anggota = anggota;
    }

    public String getJenisSampah() {
        return jenisSampah;
    }

    public void setJenisSampah(String jenisSampah) {
        this.jenisSampah = jenisSampah;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        // validasi biar ga bisa input berat minus
        if (berat > 0) {
            this.berat = berat;
        } else {
            System.out.println("berat tidak boleh minus atau nol!");
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // buat format simpan ke file, dipisah titik koma
    public String toFile() {
        return no + ";" + anggota + ";" + jenisSampah + ";" + berat + ";" + saldo;
    }

    // buat nampilin data
    public void tampil() {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("No         : " + no);
        System.out.println("Anggota    : " + anggota);
        System.out.println("Jenis      : " + jenisSampah);
        System.out.println("Berat      : " + berat + " kg");
        System.out.println("Saldo      : Rp " + df.format(saldo));
        System.out.println("-----------------------------");
    }
}
