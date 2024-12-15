
package examenparcial3;

/**
 *
 * @author chave
 */
public class FormularioInicio extends javax.swing.JFrame {

    
    public FormularioInicio() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnMedicos = new javax.swing.JButton();
        btnPacientes = new javax.swing.JButton();
        lblBackgriund = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMedicos.setText("MEDICOS");
        btnMedicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMedicosActionPerformed(evt);
            }
        });
        jPanel1.add(btnMedicos, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 100, 60));

        btnPacientes.setText("PACIENTES");
        btnPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPacientesActionPerformed(evt);
            }
        });
        jPanel1.add(btnPacientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 260, 100, 60));

        lblBackgriund.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\Documents\\CURSO GRATIS EN JAVA TODO CODE\\ExamenParcial3\\src\\Imagenes\\800x600_Wallpaper_Blue_Sky.png")); // NOI18N
        jPanel1.add(lblBackgriund, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMedicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMedicosActionPerformed
        FormularioMedicosEC open = new FormularioMedicosEC();
        open.setVisible(true);
        this.setVisible(false);
             
    }//GEN-LAST:event_btnMedicosActionPerformed

    private void btnPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPacientesActionPerformed
        FormularioPacientesEC open = new FormularioPacientesEC();
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnPacientesActionPerformed

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
            java.util.logging.Logger.getLogger(FormularioInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMedicos;
    private javax.swing.JButton btnPacientes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBackgriund;
    // End of variables declaration//GEN-END:variables
}
