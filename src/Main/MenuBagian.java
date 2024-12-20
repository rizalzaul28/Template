/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Main;

import Kelas.Bagian;
import PopUp.PopUpBagian;
import static PopUp.PopUpBagian.lb_Id;
import static PopUp.PopUpBagian.tf_Kode;
import static PopUp.PopUpBagian.tf_Nama;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rizan
 */
public class MenuBagian extends javax.swing.JPanel implements Bagian.PerubahanData {

    /**
     * Creates new form MenuDashboard
     */
    private Bagian bgn;

    public MenuBagian() throws SQLException {
        initComponents();

        bgn = new Bagian();
        bgn.TambahPerubahanData(this);

        loadTabel();
    }

    public void loadTabel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Kode Bagian Surat");
        model.addColumn("Nama Bagian Surat");

        try {
            Bagian k = new Bagian();
            ResultSet data = k.KodeTampilTabel();

            while (data.next()) {
                model.addRow(new Object[]{
                    data.getString("id_bagian"),
                    data.getString("kode_bagian"),
                    data.getString("nama_bagian"),});
            }

            data.close();
        } catch (SQLException sQLException) {
        }

        tb_Bagian.setModel(model);

        tb_Bagian.getColumnModel().getColumn(0).setMinWidth(0);
        tb_Bagian.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_Bagian.getColumnModel().getColumn(0).setWidth(0);

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
        bt_Tambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Bagian = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        pn_Main.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Mongolian Baiti", 1, 24)); // NOI18N
        jLabel1.setText("Menu Bagian");

        bt_Tambah.setText("Tambah Bagian");
        bt_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahActionPerformed(evt);
            }
        });

        tb_Bagian.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_Bagian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_BagianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_Bagian);

        javax.swing.GroupLayout pn_MainLayout = new javax.swing.GroupLayout(pn_Main);
        pn_Main.setLayout(pn_MainLayout);
        pn_MainLayout.setHorizontalGroup(
            pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1096, Short.MAX_VALUE)
                    .addGroup(pn_MainLayout.createSequentialGroup()
                        .addGroup(pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(bt_Tambah))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pn_MainLayout.setVerticalGroup(
            pn_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_MainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(bt_Tambah)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(pn_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void bt_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahActionPerformed
        try {
            PopUpBagian popUp = new PopUpBagian(null, true, bgn);
            popUp.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MenuBagian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_TambahActionPerformed

    private void tb_BagianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_BagianMouseClicked
        try {
            int baris = tb_Bagian.rowAtPoint(evt.getPoint());
            String id = tb_Bagian.getModel().getValueAt(baris, 0).toString();
            String kode = tb_Bagian.getValueAt(baris, 1).toString();
            String nama = tb_Bagian.getValueAt(baris, 2).toString();

            PopUpBagian popUpKategori = new PopUpBagian(null, true, bgn);

            lb_Id.setText(id);
            tf_Kode.setText(kode);
            tf_Nama.setText(nama);
            popUpKategori.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MenuBagian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_BagianMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pn_Main;
    private javax.swing.JTable tb_Bagian;
    // End of variables declaration//GEN-END:variables
    @Override
    public void AktifPerubahanData() {
        loadTabel();
    }

}
