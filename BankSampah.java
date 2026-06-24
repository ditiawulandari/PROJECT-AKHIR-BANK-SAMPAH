/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankSampah;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ditia
 */
public class BankSampah {
// program bank sampah - tugas akhir pemrograman
// nama  : Ditia Wulandari
// nim   : 241011401219

    // arraylist buat nyimpen semua data setoran
    static ArrayList<Setoran> listSetoran = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static String namaFile = "data_banksampah_241011401219.txt";
    static int nomorUrut = 1;

    public static void main(String[] args) {

        // load data dari file dulu waktu program dibuka
        muatDariFile();

        int pilih = -1;

        do {
            System.out.println("\n===== APLIKASI BANK SAMPAH =====");
            System.out.println("1. Tambah Setoran");
            System.out.println("2. Tampil Semua Setoran");
            System.out.println("3. Ubah Setoran");
            System.out.println("4. Hapus Setoran");
            System.out.println("5. Cari Setoran");
            System.out.println("6. Simpan Data");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            try {
                pilih = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("input harus angka!");
                continue;
            }

            switch (pilih) {
                case 1:
                    tambahSetoran();
                    break;
                case 2:
                    tampilSemua();
                    break;
                case 3:
                    ubahSetoran();
                    break;
                case 4:
                    hapusSetoran();
                    break;
                case 5:
                    cariSetoran();
                    break;
                case 6:
                    simpanKeFile();
                    break;
                case 0:
                    simpanKeFile();
                    System.out.println("data tersimpan. sampai jumpa!");
                    break;
                default:
                    System.out.println("pilihan tidak ada, coba lagi.");
            }

        } while (pilih != 0);
    }

    // method tambah setoran
    static void tambahSetoran() {
        System.out.println("\n-- Tambah Setoran Baru --");

        String no = "S" + String.format("%03d", nomorUrut);
        nomorUrut++;

        System.out.print("Nama anggota : ");
        String anggota = sc.nextLine();

        if (anggota.trim().isEmpty()) {
            System.out.println("nama tidak boleh kosong!");
            nomorUrut--;
            return;
        }

        System.out.println("Jenis sampah :");
        System.out.println("1. Plastik  (Rp 2000/kg)");
        System.out.println("2. Kertas   (Rp 1500/kg)");
        System.out.println("3. Logam    (Rp 5000/kg)");
        System.out.println("4. Kaca     (Rp 1000/kg)");
        System.out.println("5. Kardus   (Rp 1200/kg)");
        System.out.println("6. Kaleng   (Rp 3000/kg)");
        System.out.print("Pilih jenis  : ");

        int pilihanJenis;
        try {
            pilihanJenis = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("input jenis harus angka!");
            nomorUrut--;
            return;
        }

        String jenis;
        double harga;

        switch (pilihanJenis) {
            case 1: jenis = "Plastik";  harga = 2000; break;
            case 2: jenis = "Kertas";   harga = 1500; break;
            case 3: jenis = "Logam";    harga = 5000; break;
            case 4: jenis = "Kaca";     harga = 1000; break;
            case 5: jenis = "Kardus";   harga = 1200; break;
            case 6: jenis = "Kaleng";   harga = 3000; break;
            default:
                System.out.println("pilihan jenis tidak valid!");
                nomorUrut--;
                return;
        }

        System.out.print("Berat (kg)   : ");
        double berat;
        try {
            String inputBerat = sc.nextLine().replace(',', '.');
            berat = Double.parseDouble(inputBerat);
        } catch (NumberFormatException e) {
            System.out.println("berat harus angka!");
            nomorUrut--;
            return;
        }

        if (berat <= 0) {
            System.out.println("berat harus lebih dari 0!");
            nomorUrut--;
            return;
        }

        double saldo = berat * harga;

        Setoran s = new Setoran(no, anggota, jenis, berat, saldo);
        listSetoran.add(s);

        java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
        System.out.println("\nsetoran berhasil ditambahkan!");
        System.out.println("No: " + no + " | Saldo: Rp " + df.format(saldo));
    }

    // method tampil semua data
    static void tampilSemua() {
        System.out.println("\n-- Daftar Semua Setoran --");

        if (listSetoran.isEmpty()) {
            System.out.println("belum ada data setoran.");
            return;
        }

        double totalSaldo = 0;
        for (Setoran s : listSetoran) {
            s.tampil();
            totalSaldo += s.getSaldo();
        }

        java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
        System.out.println("Total setoran : " + listSetoran.size());
        System.out.println("Total saldo   : Rp " + df.format(totalSaldo));
    }

    // method ubah setoran
    static void ubahSetoran() {
        System.out.println("\n-- Ubah Data Setoran --");
        System.out.print("Masukkan nomor setoran (contoh: S001) : ");
        String cariNo = sc.nextLine().trim();

        Setoran target = null;
        for (Setoran s : listSetoran) {
            if (s.getNo().equalsIgnoreCase(cariNo)) {
                target = s;
                break;
            }
        }

        if (target == null) {
            System.out.println("nomor setoran tidak ditemukan.");
            return;
        }

        System.out.println("data ditemukan:");
        target.tampil();

        // ubah nama anggota
        System.out.print("nama anggota baru (enter = skip): ");
        String anggotaBaru = sc.nextLine();
        if (!anggotaBaru.trim().isEmpty()) {
            target.setAnggota(anggotaBaru);
        }

        // ubah jenis sampah
        System.out.print("ganti jenis sampah? (y/n): ");
        String gantiJenis = sc.nextLine().trim();
        if (gantiJenis.equalsIgnoreCase("y")) {
            System.out.println("Jenis sampah :");
            System.out.println("1. Plastik  (Rp 2000/kg)");
            System.out.println("2. Kertas   (Rp 1500/kg)");
            System.out.println("3. Logam    (Rp 5000/kg)");
            System.out.println("4. Kaca     (Rp 1000/kg)");
            System.out.println("5. Kardus   (Rp 1200/kg)");
            System.out.println("6. Kaleng   (Rp 3000/kg)");
            System.out.print("Pilih jenis baru : ");
            try {
                int pilihanJenisBaru = Integer.parseInt(sc.nextLine());
                String jenisBaru;
                switch (pilihanJenisBaru) {
                    case 1: jenisBaru = "Plastik"; break;
                    case 2: jenisBaru = "Kertas";  break;
                    case 3: jenisBaru = "Logam";   break;
                    case 4: jenisBaru = "Kaca";    break;
                    case 5: jenisBaru = "Kardus";  break;
                    case 6: jenisBaru = "Kaleng";  break;
                    default:
                        System.out.println("pilihan tidak valid, jenis tidak diubah.");
                        jenisBaru = target.getJenisSampah();
                }
                target.setJenisSampah(jenisBaru);
                // hitung ulang saldo dengan jenis baru dan berat yang ada
                target.setSaldo(target.getBerat() * getHargaJenis(jenisBaru));
                System.out.println("jenis sampah diubah ke " + jenisBaru);
            } catch (NumberFormatException e) {
                System.out.println("input tidak valid, jenis tidak diubah.");
            }
        }

        // ubah berat   
        System.out.print("berat baru kg (0 = skip): ");
        try {
            String inputBerat = sc.nextLine().replace(',', '.');
            double beratBaru = Double.parseDouble(inputBerat);
            if (beratBaru > 0) {
                target.setBerat(beratBaru);
                // hitung ulang saldo dengan berat baru
                target.setSaldo(beratBaru * getHargaJenis(target.getJenisSampah()));
            }
        } catch (NumberFormatException e) {
            System.out.println("berat tidak diubah.");
        }

        System.out.println("data berhasil diubah!");
        target.tampil();
    }

    // method hapus setoran
    static void hapusSetoran() {
        System.out.println("\n-- Hapus Setoran --");
        System.out.print("Masukkan nomor setoran yang dihapus : ");
        String cariNo = sc.nextLine().trim();

        Setoran target = null;
        for (Setoran s : listSetoran) {
            if (s.getNo().equalsIgnoreCase(cariNo)) {
                target = s;
                break;
            }
        }

        if (target == null) {
            System.out.println("nomor setoran tidak ditemukan.");
            return;
        }

        System.out.println("data yang akan dihapus:");
        target.tampil();

        System.out.print("yakin hapus? (y/n): ");
        String konfirm = sc.nextLine();

        if (konfirm.equalsIgnoreCase("y")) {
            listSetoran.remove(target);
            System.out.println("data berhasil dihapus.");
        } else {
            System.out.println("hapus dibatalkan.");
        }
    }

    // method cari setoran
    static void cariSetoran() {
        System.out.println("\n-- Cari Setoran --");
        System.out.println("Cari berdasarkan:");
        System.out.println("1. Nomor setoran");
        System.out.println("2. Nama anggota");
        System.out.println("3. Jenis sampah");
        System.out.print("Pilih: ");

        int pilihanCari;
        try {
            pilihanCari = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("input harus angka!");
            return;
        }

        System.out.print("Kata kunci: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean ketemu = false;
        for (Setoran s : listSetoran) {
            boolean cocok = false;
            switch (pilihanCari) {
                case 1: cocok = s.getNo().toLowerCase().contains(keyword); break;
                case 2: cocok = s.getAnggota().toLowerCase().contains(keyword); break;
                case 3: cocok = s.getJenisSampah().toLowerCase().contains(keyword); break;
            }
            if (cocok) {
                s.tampil();
                ketemu = true;
            }
        }

        if (!ketemu) {
            System.out.println("data tidak ditemukan.");
        }
    }

    // method simpan ke file teks
    static void simpanKeFile() {
        try (FileWriter fw = new FileWriter(namaFile)) {
            fw.write(nomorUrut + "\n");
            for (Setoran s : listSetoran) {
                fw.write(s.toFile() + "\n");
            }
            System.out.println("data berhasil disimpan ke " + namaFile);
        } catch (IOException e) {
            System.out.println("gagal simpan: " + e.getMessage());
        }
    }

    // method muat dari file
    static void muatDariFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(namaFile))) {
            String barisPertama = br.readLine();
            if (barisPertama != null) {
                nomorUrut = Integer.parseInt(barisPertama.trim());
            }

            String baris;
            while ((baris = br.readLine()) != null) {
                if (baris.trim().isEmpty()) continue;

                String[] bagian = baris.split(";");
                if (bagian.length == 5) {
                    String no      = bagian[0];
                    String anggota = bagian[1];
                    String jenis   = bagian[2];
                    double berat   = Double.parseDouble(bagian[3]);
                    double saldo   = Double.parseDouble(bagian[4]);
                    listSetoran.add(new Setoran(no, anggota, jenis, berat, saldo));
                }
            }

        } catch (IOException e) {
        } catch (NumberFormatException e) {
            System.out.println("ada data yang rusak di file, sebagian dilewati.");
        }
    }

    // method bantu (ambil harga berdasarkan jenis)
    static double getHargaJenis(String jenis) {
        switch (jenis.toLowerCase()) {
            case "plastik": return 2000;
            case "kertas":  return 1500;
            case "logam":   return 5000;
            case "kaca":    return 1000;
            case "kardus":  return 1200;
            case "kaleng":  return 3000;
            default:        return 1000;
        }
    }
}
