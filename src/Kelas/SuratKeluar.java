/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rizan
 */
public class SuratKeluar {

    int id_suratkeluar, tahun, jumlah = 0;
    String bagian, kategori, no_surat, perihal, tujuan, nama_file;
    java.sql.Date tgl_dibuat;
    FileInputStream file;

    private Connection conn;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    private List<PerubahanData> listeners = new ArrayList<>();

    public interface PerubahanData {

        void AktifPerubahanData();
    }

    public void TambahPerubahanData(PerubahanData listener) {
        listeners.add(listener);
    }

    public void HapusPerubahanData(PerubahanData listener) {
        listeners.remove(listener);
    }

    public void NotifPerubahanData() {
        for (PerubahanData listener : listeners) {
            listener.AktifPerubahanData();
        }
    }

    public SuratKeluar() throws SQLException {
        Koneksi koneksi = new Koneksi();
        conn = koneksi.koneksiDB();
    }

    public int getId_suratkeluar() {
        return id_suratkeluar;
    }

    public void setId_suratkeluar(int id_suratkeluar) {
        this.id_suratkeluar = id_suratkeluar;
    }

    public String getBagian() {
        return bagian;
    }

    public void setBagian(String bagian) {
        this.bagian = bagian;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNo_surat() {
        return no_surat;
    }

    public void setNo_surat(String no_surat) {
        this.no_surat = no_surat;
    }

    public String getPerihal() {
        return perihal;
    }

    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }

    public Date getTgl_dibuat() {
        return tgl_dibuat;
    }

    public void setTgl_dibuat(Date tgl_dibuat) {
        this.tgl_dibuat = tgl_dibuat;
    }

    public FileInputStream getFile() {
        return file;
    }

    public void setFile(FileInputStream file) {
        this.file = file;
    }

    // Method untuk menambah data (KodeTambah)
    public void KodeTambah() {
        query = "INSERT INTO surat_keluar (id_suratkeluar, kategori, bagian, no_surat, tgl_dibuat, perihal, tujuan, nama_file, file)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id_suratkeluar);
            ps.setString(2, kategori);
            ps.setString(3, bagian);
            ps.setString(4, no_surat);
            ps.setDate(5, tgl_dibuat);
            ps.setString(6, perihal);
            ps.setString(7, tujuan);
            ps.setString(8, nama_file);
            ps.setBlob(9, file);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Surat berhasil ditambahkan!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal menambahkan surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk mengubah data (KodeUbah)
    public void KodeUbah() {
        query = "UPDATE surat_keluar SET bagian = ?, kategori = ?, no_surat = ?, perihal = ?, tujuan = ?, tgl_dibuat = ?, nama_file = ?, file = ? "
                + "WHERE id_suratkeluar = ?";
        try {
            ps = conn.prepareStatement(query);

            ps.setString(1, kategori);
            ps.setString(2, bagian);
            ps.setDate(3, tgl_dibuat);
            ps.setString(4, perihal);
            ps.setString(5, tujuan);
            ps.setString(6, no_surat);
            ps.setString(7, nama_file);
            ps.setBlob(8, file);
            ps.setInt(9, id_suratkeluar);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Surat berhasil diubah!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal mengubah surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menghapus data (KodeHapus)
    public void KodeHapus() {
        query = "DELETE FROM surat_keluar WHERE id_suratkeluar = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id_suratkeluar);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Data surat keluar berhasil dihapus!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal menghapus surat keluar!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk membuat nomor id otomatis (autoId)
    public int autoId() {
        int newID = 1;

        try {
            String query = "SELECT MAX(id_suratkeluar) AS max_id FROM surat_keluar";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                String lastNoUrut = rs.getString("max_id");

                if (lastNoUrut != null && !lastNoUrut.isEmpty()) {
                    String numericPart = lastNoUrut.split("\\.")[0];
                    newID = Integer.parseInt(numericPart) + 1;
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghasilkan nomor urut baru!");
            e.printStackTrace();
        }

        return newID;
    }

    // Method untuk menampilkan jumlah Surat Keluar
    public int TampilJumlahBagian() {
        query = "SELECT COUNT(*) AS jumlah FROM surat_keluar";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            if (rs.next()) {
                jumlah = rs.getInt("jumlah");
            }

            rs.close();
            st.close();
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data gagal ditampilkan: " + sQLException.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return jumlah;
    }

    // Untuk mengkonversi ke angka romawi
    public String KonversiRomawi(int bulan) {
        String[] bulanRomawi = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
        return bulanRomawi[bulan - 1];
    }

    // Untuk mengambil ekstensi file
    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }

    // Method untuk melihat nomor surat
    public String GetNoSurat(String kategori, String bagian, int tahun) {
        String noSurat = "001";
        try {
            String query = "SELECT no_surat FROM surat_keluar WHERE kategori = ? AND bagian = ? AND YEAR(tgl_dibuat) = ? ORDER BY id_suratkeluar DESC LIMIT 1";
            ps = conn.prepareStatement(query);
            ps.setString(1, kategori);
            ps.setString(2, bagian);
            ps.setInt(3, tahun);
            rs = ps.executeQuery();

            if (rs.next()) {
                String lastNoSurat = rs.getString("no_srt");
                String[] parts = lastNoSurat.split("/");
                String nomorUrut = parts[0].split("\\.")[1];
                int lastNo = Integer.parseInt(nomorUrut);
                noSurat = String.format("%03d", lastNo + 1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noSurat;
    }

    // Method untuk membuka File
    public byte[] BukaFile() throws SQLException {
        byte[] IsiFile = null;
        query = "SELECT file FROM surat_keluar WHERE nama_file = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, nama_file);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            IsiFile = rs.getBytes("file");
        }

        rs.close();
        ps.close();

        return IsiFile;

    }

    // Method untuk menggabungkan filter
    public ResultSet KodeTampilByFilters(String bagian, String kategori, Date tanggalAwal, Date tanggalAkhir) throws SQLException {
        String query = "SELECT * FROM surat_keluar WHERE 1=1";

        // Membuat query dinamis berdasarkan filter
        if (bagian != null && !bagian.isEmpty()) {
            query += " AND bagian = ?";
        }
        if (kategori != null && !kategori.isEmpty()) {
            query += " AND kategori = ?";
        }
        if (tanggalAwal != null && tanggalAkhir != null) {
            query += " AND tgl_dibuat BETWEEN ? AND ?";
        }

        PreparedStatement ps = conn.prepareStatement(query);

        int paramIndex = 1;

        // Mengisi parameter query
        if (bagian != null && !bagian.isEmpty()) {
            ps.setString(paramIndex++, bagian);
        }
        if (kategori != null && !kategori.isEmpty()) {
            ps.setString(paramIndex++, kategori);
        }
        if (tanggalAwal != null && tanggalAkhir != null) {
            ps.setDate(paramIndex++, new java.sql.Date(tanggalAwal.getTime()));
            ps.setDate(paramIndex++, new java.sql.Date(tanggalAkhir.getTime()));
        }

        return ps.executeQuery();
    }

    // Method untuk memfilter data berdasarkan Kategori
    public ResultSet KodeTampilByKategori(String filterKategori) {
        try {
            if (filterKategori == null || filterKategori.isEmpty()) {
                query = "SELECT id_suratkeluar, kategori, bagian, no_surat, tgl_dibuat, perihal, tujuan, nama_file FROM suratkeluar";
                PreparedStatement ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
            } else {
                query = "SELECT id_suratkeluar, kategori, bagian, no_surat, tgl_dibuat, perihal, tujuan, nama_file FROM suratkeluar WHERE kategori = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, filterKategori);
                rs = ps.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Method untuk memfilter data berdasarkan Bagian
    public ResultSet KodeTampilByBagian(String filterBagian) {
        try {
            if (filterBagian == null || filterBagian.isEmpty()) {
                // Query tanpa filter
                query = "SELECT id_suratkeluar, kategori, bagian, no_surat, tgl_dibuat, perihal, tujuan, nama_file FROM suratkeluar";
                PreparedStatement ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
            } else {
                // Query dengan filter bagian
                query = "SELECT id_suratkeluar, kategori, bagian, no_surat, tgl_dibuat, perihal, tujuan, nama_file FROM suratkeluar WHERE bagian = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, filterBagian);
                rs = ps.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Method untuk memfilter data berdasarkan Tanggal
    public ResultSet KodeTampilByTanggal(java.sql.Date tanggal) {
        try {
            query = "SELECT id_suratkeluar, kategori, bagian, no_surat, tgl_dibuat, perihal, tujuan, nama_file FROM suratkeluar WHERE tanggal_dibuat = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1, tanggal);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

}
