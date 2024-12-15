
package examenparcial3;

import DAO.Conexion;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.stream.Collectors.toList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author chave
 */
public class FormularioMedicosEC extends javax.swing.JFrame {

    public FormularioMedicosEC() {
        initComponents();
        jPanel2.setVisible(false);
        this.setLocationRelativeTo(null);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(false);
                
    }
    public void mostrarInicio(){
        FormularioInicio open = new FormularioInicio();
        open.setVisible(true);
        this.setVisible(false);
    }
    DefaultTableModel modelo;
    
    Conexion conn = new Conexion("gestionmedicaec");
    String mensaje = "";
            
    public void InsertNewMedico() {
                Connection con = null;
                PreparedStatement ps = null;

                try {
                    
                    String name = txtNombre.getText().trim();
                    Integer age = Integer.parseInt(txtEdad.getText().trim());
                    String identity = txtIdentidad.getText().trim();
                    Integer experiency = Integer.parseInt(txtAniosExperiencia.getText().trim());
                    String experiencia = String.valueOf(cboEspecialidad.getSelectedItem()).trim();
                    

                    
                    con = conn.getConexion();
                    String sql = "INSERT INTO medicos (name, age, identity, experiencyYears, especialidad) VALUES (?, ?, ?, ?,?)";
                    ps = con.prepareStatement(sql);

                    // Asignar los valores a los parámetros de la consulta
                    ps.setString(1, name);
                    ps.setInt(2, age);
                    ps.setString(3, identity);
                    ps.setInt(4, experiency);
                    ps.setString(5, experiencia);

                    // Ejecutar la inserción y verificar el resultado
                    int rowsInserted = ps.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "¡Datos insertados exitosamente!");
                        toList(); // Refrescar la lista si es necesario
                        mostrarInicio();
                        
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo insertar la información.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al insertar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if (ps != null) ps.close();
                        if (con != null) con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void buscarMedico(){

            Object dataClient[] = new Object[4];
            modelo = (DefaultTableModel) tblMedicos.getModel();
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Statement st = null;
            modelo.setRowCount(0);

            try {
                con = conn.getConexion();
                st = con.createStatement();
                rs = st.executeQuery("select * from medicos where name like '%" + txtBuscarNombre.getText() + "%'");

                while(rs.next()){
                    dataClient[0] = rs.getString("id");
                    dataClient[1] = rs.getString("name");
                    dataClient[2] = rs.getString("identity");
                    dataClient[3] = rs.getString("especialidad");

                    modelo.addRow(dataClient);

                    tblMedicos.setModel(modelo);

                }
            } catch (Exception e) {
                System.out.println("Error en la consulta. problema en BuscarCliente");
            }

        }
            
        public void mostrarMedico(String Id){


                 Connection con = null;

                 ResultSet rs = null;
                 Statement st = null;


                 try {
                     con = conn.getConexion();
                     st = con.createStatement();
                     rs = st.executeQuery("select * from medicos where id = '"+ Id +"'");

                     while(rs.next()){
                         txtId.setText(rs.getString("id"));
                         txtNombre.setText(rs.getString("name"));
                         txtEdad.setText(rs.getString("age"));
                         txtIdentidad.setText(rs.getString("identity"));
                         txtAniosExperiencia.setText(rs.getString("experiencyYears"));
                         cboEspecialidad.setSelectedItem(rs.getString("especialidad"));
                     }
                 } catch (Exception e) {
                     System.out.println("Error en la consulta.");
                 }

             }
       
    public void modificarMedicoEC() {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            String nameEC = txtNombre.getText();
            int ageEC = Integer.parseInt(txtEdad.getText());
            String identityEC = txtIdentidad.getText();
            int experienciaEC = Integer.parseInt(txtAniosExperiencia.getText());
            String especialidadEC = String.valueOf(cboEspecialidad.getSelectedItem());
            String idTextEC = txtId.getText();

            if (idTextEC == null || idTextEC.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El ID no puede estar vacío.");
                return;
            }

            int idMedicoEC;
            try {
                idMedicoEC = Integer.parseInt(idTextEC);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
                return;
            }

            con = conn.getConexion();
            String sqlEC = "UPDATE medicos SET name = ?, age = ?, identity = ?, experiencyYears = ?, especialidad = ? WHERE id = ?";
            ps = con.prepareStatement(sqlEC);
            ps.setString(1, nameEC);
            ps.setInt(2, ageEC);
            ps.setString(3, identityEC);
            ps.setInt(4, experienciaEC);
            ps.setString(5, especialidadEC);
            ps.setInt(6, idMedicoEC);

            int rowsUpdatedEC = ps.executeUpdate();

            if (rowsUpdatedEC > 0) {
                JOptionPane.showMessageDialog(this, "¡Datos actualizados exitosamente!");
                toList();
                mostrarInicio();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el registro a actualizar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar los datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Uno de los campos numéricos no tiene un valor válido.");
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

        public void deleteMedicoEC (){
    
            Connection con = null;
            PreparedStatement ps = null;

            try {

                int selectedRow = tblMedicos.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return; 
                }


                int idClient = Integer.parseInt(tblMedicos.getValueAt(selectedRow, 0).toString());


                int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return; 
                }

                con = conn.getConexion();

                String sql = "DELETE FROM medicos WHERE id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, idClient);

                int rowsDeleted = ps.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "¡Producto eliminado exitosamente!");

                    toList();
                    mostrarInicio();
                    
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el cliente a eliminar.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar el cliente: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID del cliente debe ser un número válido.");
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtBuscarNombre = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtIdentidad = new javax.swing.JTextField();
        txtEdad = new javax.swing.JTextField();
        txtAniosExperiencia = new javax.swing.JTextField();
        cboEspecialidad = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtRegresar = new javax.swing.JButton();
        btnPacientes = new javax.swing.JButton();
        lblBackgriund = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblMedicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "IDENTIDAD", "ESPECIALIDAD"
            }
        ));
        tblMedicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMedicosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMedicos);
        if (tblMedicos.getColumnModel().getColumnCount() > 0) {
            tblMedicos.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblMedicos.getColumnModel().getColumn(0).setMaxWidth(30);
            tblMedicos.getColumnModel().getColumn(1).setPreferredWidth(350);
            tblMedicos.getColumnModel().getColumn(1).setMaxWidth(350);
            tblMedicos.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblMedicos.getColumnModel().getColumn(2).setMaxWidth(200);
            tblMedicos.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblMedicos.getColumnModel().getColumn(3).setMaxWidth(200);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 690, 80));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("INFORMACION MEDICO");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("INFORMACION MEDICO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("ID");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 190, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("BUSCAR MEDICO");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 210, 30));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("EDAD");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 200, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("AÑOS EXPERIENCIA");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 240, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("ESPECIALIDAD");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, 190, -1));

        txtBuscarNombre.setText("BUSCAR POR NOMBRE");
        txtBuscarNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarNombreKeyPressed(evt);
            }
        });
        jPanel1.add(txtBuscarNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 300, 30));

        txtId.setEditable(false);
        jPanel1.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 90, 30));
        jPanel1.add(txtIdentidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 300, 30));
        jPanel1.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, 90, 30));
        jPanel1.add(txtAniosExperiencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, 90, 30));

        cboEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "          ", "NUTRIOLOGO", "DERMATOLOGO", "MEDICO GENERAL", "GASTROENTEROLOGO", "MEDICO FORENCE", "ENFERMERO", " " }));
        jPanel1.add(cboEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 150, 30));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("IDENTIDAD");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 210, 30));

        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, 100, 60));

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 510, 100, 60));

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 510, 110, 60));

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 510, 100, 60));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("NOMBRE");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 210, 30));

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreKeyPressed(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 210, 300, 30));

        txtRegresar.setText("REGRESAR");
        txtRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(txtRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 510, 100, 60));

        btnPacientes.setText("VER PACIENTES");
        jPanel1.add(btnPacientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 350, 120, 70));

        lblBackgriund.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\Documents\\CURSO GRATIS EN JAVA TODO CODE\\ExamenParcial3\\src\\Imagenes\\800x600_Wallpaper_Blue_Sky.png")); // NOI18N
        jPanel1.add(lblBackgriund, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        InsertNewMedico();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyPressed
        btnGuardar.setEnabled(true);
    }//GEN-LAST:event_txtNombreKeyPressed

    private void txtBuscarNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarNombreKeyPressed
        jPanel2.setVisible(true);
        buscarMedico();
    }//GEN-LAST:event_txtBuscarNombreKeyPressed

    private void tblMedicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMedicosMouseClicked
        int fila =  tblMedicos.getSelectedRow();
        String Id = tblMedicos.getValueAt(fila, 0).toString();
        mostrarMedico(Id);
        jPanel2.setVisible(false);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tblMedicosMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
         modificarMedicoEC();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        deleteMedicoEC();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRegresarActionPerformed
        FormularioInicio open = new FormularioInicio();
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_txtRegresarActionPerformed

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioMedicosEC().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnPacientes;
    private javax.swing.JComboBox<String> cboEspecialidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBackgriund;
    private javax.swing.JTable tblMedicos;
    private javax.swing.JTextField txtAniosExperiencia;
    private javax.swing.JTextField txtBuscarNombre;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdentidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JButton txtRegresar;
    // End of variables declaration//GEN-END:variables
}
