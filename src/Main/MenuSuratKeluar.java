/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Main;

import Kelas.Bagian;
import Kelas.Kategori;
import Kelas.SuratKeluar;
import PopUp.PopUpSuratKeluar;
import static PopUp.PopUpSuratKeluar.cb_Bagian;
import static PopUp.PopUpSuratKeluar.cb_Kategori;
import static PopUp.PopUpSuratKeluar.lb_Id;
import static PopUp.PopUpSuratKeluar.tf_File;
import static PopUp.PopUpSuratKeluar.tf_NoSurat;
import static PopUp.PopUpSuratKeluar.tf_Perihal;
import static PopUp.PopUpSuratKeluar.tf_Tgl;
import static PopUp.PopUpSuratKeluar.tf_Tujuan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rizan
 */
public class MenuSuratKeluar extends javax.swing.JPanel implements SuratKeluar.PerubahanData {

    /**
     * Creates new form MenuDashboard
     */
    private SuratKeluar sk;

    public MenuSuratKeluar() throws SQLException {
        initComponents();

        sk = new SuratKeluar();
        sk.TambahPerubahanData(this);

        loadTabel();
        cbBagianSurat();
        cbKategoriSurat();

        cb_KategoriMenu.addActionListener(evt -> applyFilters());
        cb_BagianMenu.addActionListener(evt -> applyFilters());
        tf_TglAwal.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                applyFilters();
            }
        });

        tf_TglAkhir.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                applyFilters();
            }
        });

    }

    void cbKategoriSurat() {
        try {
            cb_KategoriMenu.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_KategoriMenu.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_KategoriMenu.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            cb_BagianMenu.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_BagianMenu.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_BagianMenu.setSelectedItem("--Pilih Bagian Surat--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTabel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null); // Kolom tersembunyi untuk ID
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            SuratKeluar k = new SuratKeluar();

            java.util.Date now = new java.util.Date();
            java.sql.Date startOfMonth = new java.sql.Date(now.getYear(), now.getMonth(), 1);
            java.sql.Date endOfMonth = new java.sql.Date(now.getYear(), now.getMonth() + 1, 0);

            ResultSet data = k.KodeTampilByFilters(null, null, null, null);

            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {
                // Format tanggal
                String formattedDate = "";
                if (data.getString("tgl_dibuat") != null) {
                    java.util.Date date = java.sql.Date.valueOf(data.getString("tgl_dibuat"));
                    formattedDate = dateFormat.format(date);
                }

                // Tambahkan data ke tabel
                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"), // ID
                    data.getString("kategori"), // Kategori
                    data.getString("bagian"), // Bagian
                    data.getString("no_surat"), // Nomor
                    formattedDate, // Tanggal dalam format Hari Bulan Tahun
                    data.getString("perihal"), // Perihal
                    data.getString("tujuan"), // Tujuan
                    data.getString("nama_file") // Nama File
                });
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        // Sembunyikan kolom ID
        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    }

    private void applyFilters() {
        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();
        java.util.Date tanggalAwal = tf_TglAwal.getDate(); // Tanggal awal
        java.util.Date tanggalAkhir = tf_TglAkhir.getDate(); // Tanggal akhir

        String filterKategori = null;
        String filterBagian = null;
        java.sql.Date sqlTanggalAwal = null;
        java.sql.Date sqlTanggalAkhir = null;

        if (!selectedKategori.equals("--Pilih Kategori Surat--")) {
            filterKategori = selectedKategori.split(" - ")[0];
        }

        if (!selectedBagian.equals("--Pilih Bagian Surat--")) {
            filterBagian = selectedBagian.split(" - ")[0];
        }

        if (tanggalAwal != null) {
            sqlTanggalAwal = new java.sql.Date(tanggalAwal.getTime());
        }

        if (tanggalAkhir != null) {
            sqlTanggalAkhir = new java.sql.Date(tanggalAkhir.getTime());
        }

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            SuratKeluar bg = new SuratKeluar();
            ResultSet data = bg.KodeTampilByFilters(filterBagian, filterKategori, sqlTanggalAwal, sqlTanggalAkhir);

            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {
                String formattedDate = "";
                if (data.getString("tgl_dibuat") != null) {
                    java.util.Date date = java.sql.Date.valueOf(data.getString("tgl_dibuat"));
                    formattedDate = dateFormat.format(date);
                }

                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"),
                    data.getString("kategori"),
                    data.getString("bagian"),
                    data.getString("no_surat"),
                    formattedDate,
                    data.getString("perihal"),
                    data.getString("tujuan"),
                    data.getString("nama_file"),});
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    }

    private void filterTabelByKategori() {
        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
        if (selectedKategori.equals("--Pilih Kategori Surat--")) {
            loadTabel();
            return;
        }

        String kodeKategori = selectedKategori.split(" - ")[0]; // Ambil kode kategori
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            SuratKeluar bg = new SuratKeluar();
            ResultSet data = bg.KodeTampilByKategori(kodeKategori);

            while (data.next()) {
                if (data.getString("kategori").equals(kodeKategori)) {
                    model.addRow(new Object[]{
                        data.getString("id_suratkeluar"),
                        data.getString("kategori"),
                        data.getString("bagian"),
                        data.getString("no_surat"),
                        data.getString("tgl_dibuat"),
                        data.getString("perihal"),
                        data.getString("tujuan"),
                        data.getString("nama_file"),});
                }
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    }

    private void filterTabelByBagian() {
        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();

        if (selectedBagian.equals("--Pilih Bagian Surat--")) {
            loadTabel();
        } else {
            String filterBagian = selectedBagian.split(" - ")[0];
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn(null);
            model.addColumn("Kategori");
            model.addColumn("Bagian");
            model.addColumn("Nomor");
            model.addColumn("Tanggal");
            model.addColumn("Perihal");
            model.addColumn("Tujuan");
            model.addColumn("Nama File");

            try {
                SuratKeluar bg = new SuratKeluar();
                ResultSet data = bg.KodeTampilByBagian(filterBagian);

                while (data.next()) {
                    model.addRow(new Object[]{
                        data.getString("id_suratkeluar"),
                        data.getString("kategori"),
                        data.getString("bagian"),
                        data.getString("no_surat"),
                        data.getString("tgl_dibuat"),
                        data.getString("perihal"),
                        data.getString("tujuan"),
                        data.getString("nama_file"),});
                }

                data.close();
            } catch (SQLException sQLException) {
                sQLException.printStackTrace();
            }

            tb_SuratKeluar.setModel(model);

            tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
            tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
            tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
        }
    }

    private void filterTabelByTanggal(java.util.Date tanggal) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kategori");
        model.addColumn("Bagian");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");

        try {
            java.sql.Date sqlDate = new java.sql.Date(tanggal.getTime());
            SuratKeluar bg = new SuratKeluar();
            ResultSet data = bg.KodeTampilByTanggal(sqlDate);

            while (data.next()) {
                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"),
                    data.getString("kategori"),
                    data.getString("bagian"),
                    data.getString("no_surat"),
                    data.getString("tgl_dibuat"),
                    data.getString("perihal"),
                    data.getString("tujuan"),
                    data.getString("nama_file"),});
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    }

    void resetTgl() {
        tf_TglAwal.setDate(null);
        tf_TglAkhir.setDate(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_Main = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bt_Tambah = new javax.swing.JButton();
        cb_KategoriMenu = new javax.swing.JComboBox<>();
        cb_BagianMenu = new javax.swing.JComboBox<>();
        tf_TglAwal = new com.toedter.calendar.JDateChooser();
        tf_TglAkhir = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_SuratKeluar = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        pn_Main.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 24)); // NOI18N
        jLabel1.setText("Menu Surat Keluar");

        jLabel4.setText("Tanggal Awal");

        jLabel2.setText("Kategori");

        jLabel3.setText("Bagian");

        jLabel5.setText("Tanggal Akhir");

        bt_Tambah.setText("Tambah Surat Keluar");
        bt_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahActionPerformed(evt);
            }
        });

        tb_SuratKeluar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_SuratKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_SuratKeluarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_SuratKeluar);

        jButton1.setText("Reset Tanggal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_MainLayout = new javax.swing.GroupLayout(pn_Main);
        pn_Main.setLayout(pn_MainLayout);
        pn_MainLayout.setHorizontalGroup(
            pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pn_MainLayout.createSequentialGroup()
                        .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(bt_Tambah)
                            .addGroup(pn_MainLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tf_TglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tf_TglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)))
                        .addGap(0, 45, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pn_MainLayout.setVerticalGroup(
            pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_MainLayout.createSequentialGroup()
                .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_MainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(bt_Tambah)
                        .addGap(19, 19, 19)
                        .addComponent(tf_TglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pn_MainLayout.createSequentialGroup()
                        .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pn_MainLayout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                    .addComponent(tf_TglAwal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_MainLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(pn_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void bt_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahActionPerformed
        try {
            PopUpSuratKeluar popUp = new PopUpSuratKeluar(null, true, sk);
            popUp.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MenuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_TambahActionPerformed

    private void tb_SuratKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_SuratKeluarMouseClicked
        try {
            int baris = tb_SuratKeluar.getSelectedRow();
            if (baris != -1 && tb_SuratKeluar.getValueAt(baris, 0) != null) {

                // Ambil nilai dari tabel
                String Id = tb_SuratKeluar.getValueAt(baris, 0).toString();
                String Kategori = tb_SuratKeluar.getValueAt(baris, 1).toString();
                String Bagian = tb_SuratKeluar.getValueAt(baris, 2).toString();
                String Nomor = tb_SuratKeluar.getValueAt(baris, 3).toString();
                String Tanggal = tb_SuratKeluar.getValueAt(baris, 4).toString(); // Tanggal dalam format "dd MMMM yyyy"
                String Perihal = tb_SuratKeluar.getValueAt(baris, 5).toString();
                String Tujuan = tb_SuratKeluar.getValueAt(baris, 6).toString();
                String File = tb_SuratKeluar.getValueAt(baris, 7).toString();

                // Tampilkan pop-up form untuk ubah data
                PopUpSuratKeluar pusk = new PopUpSuratKeluar(null, true, sk);

                // Set nilai ID
                lb_Id.setText(Id);

                // Set nilai kategori
                for (int i = 0; i < cb_Kategori.getItemCount(); i++) {
                    String item = cb_Kategori.getItemAt(i);
                    if (item.startsWith(Kategori + " -")) {
                        cb_Kategori.setSelectedIndex(i);
                        break;
                    }
                }

                // Set nilai bagian
                for (int i = 0; i < cb_Bagian.getItemCount(); i++) {
                    String item = cb_Bagian.getItemAt(i);
                    if (item.startsWith(Bagian + " -")) {
                        cb_Bagian.setSelectedIndex(i);
                        break;
                    }
                }

                // Set nomor surat
                tf_NoSurat.setText(Nomor);

                // Konversi tanggal dari format "dd MMMM yyyy" ke "java.util.Date"
                if (Tanggal != null && !Tanggal.isEmpty()) {
                    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));
                    java.util.Date date = formatter.parse(Tanggal);
                    tf_Tgl.setDate(date);
                }

                // Set nilai perihal, tujuan, dan file
                tf_Perihal.setText(Perihal);
                tf_Tujuan.setText(Tujuan);
                tf_File.setText(File);

                // Tampilkan pop-up
                pusk.setVisible(true);
            }
        } catch (SQLException | java.text.ParseException ex) {
            Logger.getLogger(MenuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_SuratKeluarMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        resetTgl();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Tambah;
    private javax.swing.JComboBox<String> cb_BagianMenu;
    private javax.swing.JComboBox<String> cb_KategoriMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pn_Main;
    private javax.swing.JTable tb_SuratKeluar;
    private com.toedter.calendar.JDateChooser tf_TglAkhir;
    private com.toedter.calendar.JDateChooser tf_TglAwal;
    // End of variables declaration//GEN-END:variables
    @Override
    public void AktifPerubahanData() {
          loadTabel();
    }

}
