/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PopUp;

import Kelas.Bagian;
import Kelas.Kategori;
import Kelas.SuratKeluar;
import Kelas.TimedJOptionPane;
import Main.MenuSuratKeluar;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import AutoClose.AutoCloseJFrame;

/**
 *
 * @author rizan
 */
public class PopUpSuratKeluar extends javax.swing.JDialog {

    /**
     * Creates new form PopUpBagian
     */
    private SuratKeluar sk;

    public PopUpSuratKeluar(java.awt.Frame parent, boolean b, SuratKeluar srtkel) throws SQLException {
        super(parent, b);
        initComponents();

        this.sk = srtkel;

        AutoCloseJFrame.autoCloseIfIdle(this, 3000);

        tf_Tgl.setDate(new Date());
        cbBagianSurat();
        cbKategoriSurat();

        autoId();

        cb_Kategori.addItemListener(e -> {
            try {
                updateNoSurat();
            } catch (SQLException ex) {
                Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cb_Bagian.addItemListener(e -> {
            try {
                updateNoSurat();
            } catch (SQLException ex) {
                Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tf_Tgl.addPropertyChangeListener("date", evt -> {
            try {
                updateNoSurat();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    // Method untuk update nomor surat berdasarkan kategori dan bagian
    private void updateNoSurat() throws SQLException {
        if (cb_Kategori.getSelectedItem() == null || cb_Bagian.getSelectedItem() == null) {
            tf_NoSurat.setText("");
            return;
        }

        String kategori = cb_Kategori.getSelectedItem().toString();
        String bagian = cb_Bagian.getSelectedItem().toString();

        if (kategori.equals("--Pilih Kategori Surat--") || bagian.equals("--Pilih Bagian Surat--")) {
            tf_NoSurat.setText("");
            return;
        }

        String selectedKategori = cb_Kategori.getSelectedItem().toString().split(" - ")[0];
        String selectedBagian = cb_Bagian.getSelectedItem().toString().split(" - ")[0];

        // Ambil tanggal dari JDateChooser
        Date tanggal = tf_Tgl.getDate();  // tf_Tgl adalah JDateChooser
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tanggal);
        int bulan = calendar.get(Calendar.MONTH) + 1;  // Ambil bulan
        int tahun = calendar.get(Calendar.YEAR);       // Ambil tahun

        // Ambil nomor surat terbaru
        SuratKeluar surat = new SuratKeluar();
        String noSurat = surat.GetNoSurat(kategori, bagian, tahun);

        // Konversi bulan ke format Romawi
        String bulanRomawi = surat.KonversiRomawi(bulan);

        // Format nomor surat
        String formattedNoSurut = String.format("%s.%s/%s/%s/%d",
                selectedKategori,
                noSurat,
                selectedBagian,
                bulanRomawi,
                tahun
        );

        // Set nomor surat ke text field
        tf_NoSurat.setText(formattedNoSurut);
    }

    void cbKategoriSurat() {
        try {
            cb_Kategori.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_Kategori.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_Kategori.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            cb_Bagian.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_Bagian.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_Bagian.setSelectedItem("--Pilih Bagian Surat--"); // Pilih default option
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lb_Id = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cb_Kategori = new javax.swing.JComboBox<>();
        cb_Bagian = new javax.swing.JComboBox<>();
        tf_NoSurat = new javax.swing.JTextField();
        tf_Tgl = new com.toedter.calendar.JDateChooser();
        tf_Perihal = new javax.swing.JTextField();
        tf_Tujuan = new javax.swing.JTextField();
        tf_File = new javax.swing.JTextField();
        bt_Salin = new javax.swing.JButton();
        bt_Upload = new javax.swing.JButton();
        bt_Tambah = new javax.swing.JButton();
        bt_Ubah = new javax.swing.JButton();
        bt_Hapus = new javax.swing.JButton();
        bt_Close = new javax.swing.JButton();
        bt_Lihat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 24)); // NOI18N
        jLabel1.setText("Form Tambah Surat Keluar");

        lb_Id.setBackground(new java.awt.Color(255, 255, 255));
        lb_Id.setForeground(new java.awt.Color(153, 153, 153));
        lb_Id.setText("0");

        jLabel2.setText("Kategori");

        jLabel3.setText("Bagian");

        jLabel4.setText("Nomor Surat");

        jLabel5.setText("Tanggal Dibuat");

        jLabel6.setText("Perihal");

        jLabel7.setText("Tujuan");

        tf_Tgl.setDateFormatString("d, MMM, y");

        bt_Salin.setText("Salin");
        bt_Salin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SalinActionPerformed(evt);
            }
        });

        bt_Upload.setText("Upload File");
        bt_Upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_UploadActionPerformed(evt);
            }
        });

        bt_Tambah.setText("Tambah");
        bt_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahActionPerformed(evt);
            }
        });

        bt_Ubah.setText("Ubah");
        bt_Ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_UbahActionPerformed(evt);
            }
        });

        bt_Hapus.setText("Hapus");
        bt_Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_HapusActionPerformed(evt);
            }
        });

        bt_Close.setText("Close");
        bt_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_CloseActionPerformed(evt);
            }
        });

        bt_Lihat.setText("Lihat Surat");
        bt_Lihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_LihatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb_Bagian, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tf_NoSurat)
                            .addComponent(cb_Kategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(bt_Upload))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_Tgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tf_Perihal)
                            .addComponent(tf_Tujuan)
                            .addComponent(tf_File)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bt_Tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_Ubah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_Hapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_Lihat)
                                .addGap(0, 1, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bt_Close)
                            .addComponent(jLabel1)
                            .addComponent(lb_Id))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_Salin)
                .addGap(51, 51, 51))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_Id)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cb_Kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cb_Bagian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tf_NoSurat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_Salin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tf_Tgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tf_Perihal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tf_Tujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_File, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_Upload))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Tambah)
                    .addComponent(bt_Ubah)
                    .addComponent(bt_Hapus)
                    .addComponent(bt_Lihat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 199, Short.MAX_VALUE)
                .addComponent(bt_Close)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(586, 573));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_CloseActionPerformed
        dispose();
    }//GEN-LAST:event_bt_CloseActionPerformed

    private void bt_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahActionPerformed
        try {
            if (cb_Bagian.getSelectedIndex() == 0 || cb_Kategori.getSelectedIndex() == 0
                    || tf_NoSurat.getText().isEmpty() || tf_Tgl.getDate() == null
                    || tf_Perihal.getText().isEmpty() || tf_Tujuan.getText().isEmpty()
                    || tf_File.getText().isEmpty()) {
                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Pastikan semua field ter isi", null, JOptionPane.ERROR_MESSAGE, 3000);
                return;
            }

            SuratKeluar kodeTambah = new SuratKeluar();

            kodeTambah.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

            String bagian = (cb_Bagian.getSelectedItem().toString());
            if (!bagian.equals("--Pilih Bagian Surat--")) {
                kodeTambah.setBagian(bagian.split(" - ")[0]);
            } else {

            }

            String kategori = (cb_Kategori.getSelectedItem().toString());
            if (!kategori.equals("--Pilih Kategori Surat--")) {
                kodeTambah.setKategori(kategori.split(" - ")[0]);
            } else {

            }

            kodeTambah.setNo_surat(tf_NoSurat.getText());
            java.util.Date tanggalDibuatUtil = tf_Tgl.getDate();
            if (tanggalDibuatUtil != null) {
                java.sql.Date tanggalDibuatSQL = new java.sql.Date(tanggalDibuatUtil.getTime());
                kodeTambah.setTgl_dibuat(tanggalDibuatSQL);
            } else {
                return;
            }
            kodeTambah.setPerihal(tf_Perihal.getText());
            kodeTambah.setTujuan(tf_Tujuan.getText());

            String nomorSurat = tf_NoSurat.getText();
            File file = new File(tf_File.getText());
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                String fileExtension = kodeTambah.getFileExtension(file.getName());
                String formattedNamaFile = nomorSurat.replace(".", "_").replace("/", "_") + fileExtension;
                kodeTambah.setFile(fis);
                kodeTambah.setNama_file(formattedNamaFile);
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            kodeTambah.KodeTambah();
            reset();
            autoId();

            MenuSuratKeluar kt = new MenuSuratKeluar();
            kt.loadTabel();
            sk.NotifPerubahanData();
            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_TambahActionPerformed

    private void bt_UbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_UbahActionPerformed
        try {
            SuratKeluar kodeUbah = new SuratKeluar();
            kodeUbah.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));
            String bagian = (cb_Bagian.getSelectedItem().toString());
            if (!bagian.equals("--Pilih Bagian Surat--")) {
                kodeUbah.setBagian(bagian.split(" - ")[0]);
            } else {

            }

            String kategori = (cb_Kategori.getSelectedItem().toString());
            if (!kategori.equals("--Pilih Kategori Surat--")) {
                kodeUbah.setKategori(kategori.split(" - ")[0]);
            } else {

            }

            kodeUbah.setNo_surat(tf_NoSurat.getText());
            java.util.Date tanggalDibuatUtil = tf_Tgl.getDate();
            if (tanggalDibuatUtil != null) {
                java.sql.Date tanggalDibuatSQL = new java.sql.Date(tanggalDibuatUtil.getTime());
                kodeUbah.setTgl_dibuat(tanggalDibuatSQL);
            } else {
                return;
            }
            kodeUbah.setPerihal(tf_Perihal.getText());
            kodeUbah.setTujuan(tf_Tujuan.getText());

            String filePath = tf_File.getText();
            if (filePath != null && !filePath.trim().isEmpty()) {
                File file = new File(filePath);
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    String fileExtension = kodeUbah.getFileExtension(file.getName());
                    String formattedNamaFile = tf_NoSurat.getText().replace(".", "_").replace("/", "_") + fileExtension;
                    kodeUbah.setFile(fis);
                    kodeUbah.setNama_file(formattedNamaFile);
                } else {
                    JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "File tidak diubah. Menggunakan file sebelumnya.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }

            kodeUbah.KodeUbah();
            reset();
            autoId();

            MenuSuratKeluar kt = new MenuSuratKeluar();
            kt.loadTabel();
            sk.NotifPerubahanData();
            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_UbahActionPerformed

    private void bt_HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_HapusActionPerformed
        try {
            if (lb_Id.getText().isEmpty()) {
                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Pilih data yang ingin dihapus!", null, JOptionPane.WARNING_MESSAGE, 1000);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                SuratKeluar kodeHapus = new SuratKeluar();
                kodeHapus.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

                kodeHapus.KodeHapus();
                dispose();

                MenuSuratKeluar kt = new MenuSuratKeluar();
                kt.loadTabel();
                sk.NotifPerubahanData();

            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_bt_HapusActionPerformed

    private void bt_UploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_UploadActionPerformed
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = chooser.getSelectedFile();

                String targetFolderPath = "File/FileSuratKeluar";
                File targetFolder = new File(targetFolderPath);

                if (!targetFolder.exists()) {
                    if (!targetFolder.mkdirs()) {
                        System.out.println("Gagal membuat folder FileSurat!");
                        return;
                    }
                }

                String fileBaseName = tf_NoSurat.getText().replace(".", "_").replace("/", "_");

                SuratKeluar kodeUpload = new SuratKeluar();
                String fileExtension = kodeUpload.getFileExtension(selectedFile.getName());

                File targetFile = new File(targetFolderPath, fileBaseName + fileExtension);
                int count = 1;
                while (targetFile.exists()) {
                    targetFile = new File(targetFolderPath, fileBaseName + "(" + count + ")" + fileExtension);
                    count++;
                }

                try {
                    Files.copy(selectedFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    tf_File.setText(targetFile.getAbsolutePath());
                } catch (IOException e) {
                    TimedJOptionPane timedPane = new TimedJOptionPane();
                    timedPane.showTimedMessage("Gagal mengunggah file", null, JOptionPane.ERROR_MESSAGE, 1000);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_UploadActionPerformed

    private void bt_SalinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SalinActionPerformed
        try {
            String textToCopy = tf_NoSurat.getText();

            if (textToCopy != null && !textToCopy.isEmpty()) {
                java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(textToCopy);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Nomor urut telah disalin ke Clipboard.", "Berhasil", JOptionPane.INFORMATION_MESSAGE, 1000);
            } else {
                TimedJOptionPane timedPane = new TimedJOptionPane();
                timedPane.showTimedMessage("Nomor urut kosong, tidak ada yang disalin.", "Kesalahan", JOptionPane.ERROR_MESSAGE, 2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyalin teks.", "Kesalahan", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_SalinActionPerformed

    private void bt_LihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_LihatActionPerformed
        try {
            String namaFile = tf_File.getText();
            if (namaFile == null || namaFile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama file tidak tersedia.", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SuratKeluar suratKeluar = new SuratKeluar();
            suratKeluar.setNama_file(namaFile);
            byte[] fileData = suratKeluar.BukaFile();

            if (fileData != null) {
                File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + namaFile);
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(fileData);
                fos.close();

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(tempFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Desktop tidak didukung pada sistem ini.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan untuk surat ini.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuka file: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_LihatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SuratKeluar srtkel = new SuratKeluar();
                    new PopUpSuratKeluar(null, true, srtkel).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Close;
    private javax.swing.JButton bt_Hapus;
    private javax.swing.JButton bt_Lihat;
    private javax.swing.JButton bt_Salin;
    private javax.swing.JButton bt_Tambah;
    private javax.swing.JButton bt_Ubah;
    private javax.swing.JButton bt_Upload;
    public static javax.swing.JComboBox<String> cb_Bagian;
    public static javax.swing.JComboBox<String> cb_Kategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lb_Id;
    public static javax.swing.JTextField tf_File;
    public static javax.swing.JTextField tf_NoSurat;
    public static javax.swing.JTextField tf_Perihal;
    public static com.toedter.calendar.JDateChooser tf_Tgl;
    public static javax.swing.JTextField tf_Tujuan;
    // End of variables declaration//GEN-END:variables
    private void autoId() throws SQLException {
        SuratKeluar auto = new SuratKeluar();
        int newID = auto.autoId();
        lb_Id.setText(String.valueOf(newID));
    }

    void reset() {

    }

}
