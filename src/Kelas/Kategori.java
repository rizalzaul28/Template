/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rizan
 */
public class Kategori {

    int id_kategori, jumlah = 0;
    String kode_kategori, nama_kategori;

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

    public Kategori() throws SQLException {
        Koneksi koneksi = new Koneksi();
        conn = koneksi.koneksiDB();
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getKode_kategori() {
        return kode_kategori;
    }

    public void setKode_kategori(String kode_kategori) {
        this.kode_kategori = kode_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    // Method untuk menambah data (KodeTambah)
    public void KodeTambah() {
        query = "INSERT INTO kategori (id_kategori, kode_kategori, nama_kategori) VALUES (?,?,?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id_kategori);
            ps.setString(2, kode_kategori);
            ps.setString(3, nama_kategori);
            ps.executeUpdate();
            ps.close();
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat berhasil ditambahkan!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat gagal ditampilkan!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk mengubah data (KodeUbah)
    public void KodeUbah() {
        query = "UPDATE kategori SET kode_kategori = ?, nama_kategori = ? WHERE id_kategori = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, kode_kategori);
            ps.setString(2, nama_kategori);
            ps.setInt(3, id_kategori);

            ps.executeUpdate();
            ps.close();

            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat berhasil diubah!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat gagal diubah!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menghapus data (KodeHapus)
    public void KodeHapus() {
        query = "DELETE FROM kategori WHERE id_kategori = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id_kategori);
            ps.executeUpdate();
            ps.close();
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat berhasil dihapus!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (Exception e) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Kategori Surat gagal dihapus!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menampilkan data di tabel(KodeTampilTabel)
    public ResultSet KodeTampilTabel() {
        query = "SELECT * FROM kategori";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Data gagal ditampilkan", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;
    }

    // Method untuk menampilkan jumlah Kategori
    public int TampilJumlahKategori() {
        query = "SELECT COUNT(*) AS jumlah FROM kategori";

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

    // Method untuk menampilkan data (cb_Kategori)
    public ResultSet Tampil_CbKategoriSurat() {

        try {
            query = "SELECT kode_kategori,nama_kategori FROM kategori";
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Data Gagal Ditampilkan!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;

    }

    // Method untuk membuat nomor id otomatis (autoIdKategori)
    public int autoIdKategori() {
        int newID = 1;

        try {

            String query = "SELECT MAX(id_kategori) AS max_id FROM kategori";
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
            TimedJOptionPane timedPane = new TimedJOptionPane();
            timedPane.showTimedMessage("Gagal menghasilkan nomor urut baru!", null, JOptionPane.ERROR_MESSAGE, 3000);
            e.printStackTrace();
        }

        return newID;
    }

}
