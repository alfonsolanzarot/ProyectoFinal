package vista;

import controlador.Ctrl_Usuario;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Usuario;

/**
 * Esta clase representa un diálogo utilizado para crear y editar usuarios en el
 * sistema. Permite al usuario ingresar información relacionada con la creación
 * o edición de un usuario.
 *
 * @author Alfonso Lanzarot
 */
public class DlgUsuarios extends javax.swing.JDialog {

    /**
     * Variables de instancia de la clase.
     */
    private int xMouse, yMouse;
    private int idUsuario;
    private InterUsuarios ifUsuario;

    /**
     * Constructor que inicializa un nuevo formulario DlgUsuarios.
     *
     * @param parent Frame interUsuarios
     * @param modal Ventana modal que no permite hacer clic en el Frame padre.
     */
    public DlgUsuarios(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setSize(new Dimension(700, 372));
        this.setLocationRelativeTo(null);
        this.txtClave.setVisible(true);
        this.txtClaveVisible.setVisible(false);

    } // Cierre del constructor.

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlUsuarios = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApellidos = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblClave = new javax.swing.JLabel();
        txtClave = new javax.swing.JPasswordField();
        txtClaveVisible = new javax.swing.JTextField();
        lblPermisos = new javax.swing.JLabel();
        cbPermisos = new javax.swing.JComboBox<>();
        lblEstado = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        btnCrear = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        ckbxVerClave = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 372));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnlUsuarios.setBackground(new java.awt.Color(247, 247, 252));
        PnlUsuarios.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(52, 98, 139), 1, true));
        PnlUsuarios.setMinimumSize(new java.awt.Dimension(700, 372));
        PnlUsuarios.setPreferredSize(new java.awt.Dimension(700, 372));
        PnlUsuarios.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                PnlUsuariosMouseDragged(evt);
            }
        });
        PnlUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PnlUsuariosMousePressed(evt);
            }
        });
        PnlUsuarios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(52, 98, 139));
        lblTitulo.setText("Nuevo usuario");
        PnlUsuarios.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 26, -1, -1));
        lblTitulo.getAccessibleContext().setAccessibleName("");

        lblNombre.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(102, 102, 102));
        lblNombre.setText("Nombre");
        PnlUsuarios.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 73, -1, -1));

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtNombre.setPreferredSize(new java.awt.Dimension(64, 27));
        PnlUsuarios.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 96, 310, -1));

        lblApellidos.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblApellidos.setForeground(new java.awt.Color(102, 102, 102));
        lblApellidos.setText("Apellidos");
        PnlUsuarios.add(lblApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 73, -1, -1));

        txtApellidos.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtApellidos.setForeground(new java.awt.Color(0, 0, 0));
        txtApellidos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtApellidos.setPreferredSize(new java.awt.Dimension(64, 27));
        PnlUsuarios.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 96, 325, -1));

        lblEmail.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(102, 102, 102));
        lblEmail.setText("Correo electrónico");
        PnlUsuarios.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 139, -1, -1));

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtEmail.setPreferredSize(new java.awt.Dimension(64, 27));
        PnlUsuarios.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 162, 413, -1));

        lblClave.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblClave.setForeground(new java.awt.Color(102, 102, 102));
        lblClave.setText("Clave");
        PnlUsuarios.add(lblClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(448, 139, -1, -1));

        txtClave.setBackground(new java.awt.Color(255, 255, 255));
        txtClave.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtClave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtClave.setPreferredSize(new java.awt.Dimension(64, 27));
        PnlUsuarios.add(txtClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 162, 223, -1));

        txtClaveVisible.setBackground(new java.awt.Color(255, 255, 255));
        txtClaveVisible.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtClaveVisible.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtClaveVisible.setPreferredSize(new java.awt.Dimension(64, 27));
        PnlUsuarios.add(txtClaveVisible, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 162, 223, -1));

        lblPermisos.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblPermisos.setForeground(new java.awt.Color(102, 102, 102));
        lblPermisos.setText("Permisos");
        PnlUsuarios.add(lblPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 207, -1, -1));

        cbPermisos.setBackground(new java.awt.Color(255, 255, 255));
        cbPermisos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cbPermisos.setForeground(new java.awt.Color(0, 0, 0));
        cbPermisos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar permiso:", "Administrador", "Asistente de ventas", "Asistente de compras" }));
        cbPermisos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbPermisos.setPreferredSize(new java.awt.Dimension(72, 27));
        PnlUsuarios.add(cbPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 230, 192, -1));

        lblEstado.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(102, 102, 102));
        lblEstado.setText("Estado");
        PnlUsuarios.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 207, -1, -1));

        cbEstado.setBackground(new java.awt.Color(255, 255, 255));
        cbEstado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cbEstado.setForeground(new java.awt.Color(0, 0, 0));
        cbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar estado:", "Activo", "Inactivo" }));
        cbEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbEstado.setPreferredSize(new java.awt.Dimension(72, 27));
        PnlUsuarios.add(cbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 230, 192, -1));

        btnCrear.setBackground(new java.awt.Color(106, 141, 162));
        btnCrear.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(255, 255, 255));
        btnCrear.setText("Crear");
        btnCrear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 98, 139), 3));
        btnCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCrearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCrearMouseExited(evt);
            }
        });
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        btnCrear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCrearKeyPressed(evt);
            }
        });
        PnlUsuarios.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 305, 109, 42));

        btnCancelar.setBackground(new java.awt.Color(255, 124, 128));
        btnCancelar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 102), 3));
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setMaximumSize(new java.awt.Dimension(40, 23));
        btnCancelar.setMinimumSize(new java.awt.Dimension(40, 23));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        PnlUsuarios.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(564, 305, 109, 42));

        ckbxVerClave.setBackground(new java.awt.Color(255, 255, 255));
        ckbxVerClave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckbxVerClaveMouseClicked(evt);
            }
        });
        PnlUsuarios.add(ckbxVerClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(448, 195, -1, -1));

        getContentPane().add(PnlUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 372));

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
     * Este método establece el color de fondo del botón "Crear" cuando el mouse
     * entra en él.
     *
     * @param evt Evento de mouse que desencadena la acción.
     */
    private void btnCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseEntered
        btnCrear.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnCrearMouseEntered
    /**
     * Este método restablece el color de fondo del botón "Crear" cuando el
     * mouse sale de él.
     *
     * @param evt Evento de mouse que desencadena la acción.
     */
    private void btnCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseExited
        btnCrear.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnCrearMouseExited
    /**
     * Acción del botón "Crear" que realiza dos acciones dependiendo del texto
     * del botón: crear un nuevo usuario o actualizar un usuario existente.
     *
     * @param evt Evento de acción que desencadena la acción.
     */
    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        String textoBoton = btnCrear.getText();

        if (textoBoton.equals("Crear")) {
            crearUsuario();
        } else if (textoBoton.equals("Actualizar")) {
            actualizarUsuario();
        }

    }//GEN-LAST:event_btnCrearActionPerformed
    /**
     * Este método establece el color de fondo del botón "Cancelar" cuando el
     * mouse entra en él.
     *
     * @param evt Evento de mouse que desencadena la acción.
     */
    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnCancelarMouseEntered
    /**
     * Este método restablece el color de fondo del botón "Cancelar" cuando el
     * mouse sale de él.
     *
     * @param evt Evento de mouse que desencadena la acción.
     */
    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnCancelarMouseExited
    /**
     * Acción del botón "Cancelar" que cierra el diálogo actual.
     *
     * @param evt Evento de acción que desencadena la acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * Método que registra la posición del cursor del mouse en el panel de
     * encabezado.
     *
     * @param evt Evento del mouse.
     */
    private void PnlUsuariosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlUsuariosMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_PnlUsuariosMousePressed
    /**
     * Método que arrastra la ventana a la nueva posición según el movimiento
     * del mouse.
     *
     * @param evt Evento del mouse.
     */
    private void PnlUsuariosMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnlUsuariosMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_PnlUsuariosMouseDragged
    /**
     * Acción del botón "Ver Clave" que muestra u oculta la clave del usuario.
     *
     * @param evt Evento de mouse que desencadena la acción.
     */
    private void ckbxVerClaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckbxVerClaveMouseClicked
        mostrarClave();
    }//GEN-LAST:event_ckbxVerClaveMouseClicked
    /**
     * Acción del botón "Crear" cuando se presiona la tecla "Enter", que realiza
     * dos acciones dependiendo del texto del botón: crear un nuevo usuario o
     * actualizar un usuario existente.
     *
     * @param evt Evento de teclado que desencadena la acción.
     */
    private void btnCrearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCrearKeyPressed
        String textoBoton = btnCrear.getText();

        if (textoBoton.equals("Crear")) {
            crearUsuario();
        } else if (textoBoton.equals("Actualizar")) {
            actualizarUsuario();
        }
    }//GEN-LAST:event_btnCrearKeyPressed

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
            java.util.logging.Logger.getLogger(DlgUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgUsuarios dialog = new DlgUsuarios(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlUsuarios;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JComboBox<String> cbPermisos;
    private javax.swing.JCheckBox ckbxVerClave;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPermisos;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtClaveVisible;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

    /**
     * Método que muestra los datos seleccionados en tabla de usuarios para
     * editar la información. Recibe dos parámetros.
     *
     * @param idUsuario ID del usuario seleccionado en la tabla.
     * @param datosFila los datos del usuario de la fila seleccionada.
     */
    public void mostrarDatos(int idUsuario, Object[] datosFila) {
        // Cambiamos los textos del botón y del título del diálogo.
        lblTitulo.setText("Editar usuario");
        btnCrear.setText("Actualizar");

        txtNombre.setText((String) datosFila[1]);
        txtApellidos.setText((String) datosFila[2]);
        txtEmail.setText((String) datosFila[3]);
        txtClave.setText((String) datosFila[4]);

        // Crear un modelo de ComboBox con los valores "Seleccionar permiso:", "Administrador", "Asistente de ventas" y "Asistente de compras"
        DefaultComboBoxModel<String> modeloPermiso = new DefaultComboBoxModel<>();
        modeloPermiso.addElement("Seleccionar permiso:");
        modeloPermiso.addElement("Administrador");
        modeloPermiso.addElement("Asistente de ventas");
        modeloPermiso.addElement("Asistente de compras");

        // Establecer el modelo en el JComboBox
        cbPermisos.setModel(modeloPermiso);

        // Seleccionar el valor correspondiente al cliente seleccionado
        int idPermiso = (int) datosFila[5]; // Aquí asumimos que la columna 4 contiene el ID del permiso
        switch (idPermiso) {
            case 1:
                cbPermisos.setSelectedItem("Administrador");
                break;
            case 2:
                cbPermisos.setSelectedItem("Asistente de ventas");
                break;
            case 3:
                cbPermisos.setSelectedItem("Asistente de compras");
                break;
            default:
                cbPermisos.setSelectedItem("Seleccionar permiso:");
        }

        // Crear un modelo de ComboBox con los valores "Seleccionar estado:", "Activo" e "Inactivo"
        DefaultComboBoxModel<String> modeloEstado = new DefaultComboBoxModel<>();
        modeloEstado.addElement("Seleccionar estado:");
        modeloEstado.addElement("Activo");
        modeloEstado.addElement("Inactivo");

        // Establecer el modelo en el JComboBox
        cbEstado.setModel(modeloEstado);

        // Seleccionar el valor correspondiente al cliente seleccionado
        Boolean estado = (Boolean) datosFila[6]; // Aquí asumimos que la columna 5 contiene el tipo de estado
        if (estado) {
            cbEstado.setSelectedItem("Activo");
        } else {
            cbEstado.setSelectedItem("Inactivo");
        }
    } // Cierre del método.

    /**
     * Método que valida los campos vacíos y crea un usuario.
     */
    public void crearUsuario() {
        Usuario usuario = new Usuario();
        Ctrl_Usuario controlUsuario = new Ctrl_Usuario();

        if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar al menos el nombre, los apellidos y el correo.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));

        } else {

            // Comprobar si el usuario ya existe por correo electrónico
            String correoUsuario = txtEmail.getText().trim();

            if (!controlUsuario.existeUsuario(correoUsuario)) {

                usuario.setNombre(txtNombre.getText().trim());
                usuario.setApellidos(txtApellidos.getText().trim());
                usuario.setEmail(txtEmail.getText().trim());
                usuario.setClave(txtClave.getText().trim());

                // Obtener el ID del usuario recién creado (si es generado automáticamente)
                idUsuario = usuario.getIdUsuario(); // Por ejemplo, si getIdUsuario devuelve el ID generado automáticamente

                // Obtener el índice seleccionado en el JComboBox cbPermisos y asignar el valor correspondiente a usuario.setIdRoles()
                int selectedRolIndex = cbPermisos.getSelectedIndex();
                if (selectedRolIndex != 0 && selectedRolIndex != -1) {
                    int idRoles;
                    switch (selectedRolIndex) {
                        case 1: // Administrador
                            idRoles = 1;
                            break;
                        case 2: // Asistente de ventas
                            idRoles = 2;
                            break;
                        case 3: // Asistente de compras
                            idRoles = 3;
                            break;
                        default:
                            // Manejar el caso en que no se seleccione ninguna opción o alguna excepción
                            JOptionPane.showMessageDialog(null, "Debe seleccionar una opción válida en el campo de permisos.",
                                    "ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);
                            return; // Salir del método para evitar operaciones incorrectas
                        }
                    usuario.setIdRoles(idRoles);
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una opción válida en el campo permisos.",
                            "ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }

                // Obtener el índice seleccionado en el JComboBox cbEstado y asignar el valor correspondiente a usuario.setEstado()
                int selectedEstadoIndex = cbEstado.getSelectedIndex();
                if (selectedEstadoIndex != 0 && selectedEstadoIndex != -1) {
                    Object[] estadoValues = {null, true, false};
                    usuario.setEstado((Boolean) estadoValues[selectedEstadoIndex]);
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una opción válida en el campo estado.",
                            "ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }

                if (controlUsuario.crear(usuario)) {
                    this.ifUsuario.recargarTabla();
                    JOptionPane.showMessageDialog(null, "Usuario creado correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
                    this.Limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear el usuario.",
                            "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                    this.Limpiar();
                }

            } else {
                JOptionPane.showMessageDialog(null, "El usuario ya está registrado en la base de datos.",
                        "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                this.Limpiar();
            }
        }
    } // Cierre del método.

    /**
     * Método que valida los campos vacíos y actualiza un usuario.
     */
    public void actualizarUsuario() {
        Usuario usuario = new Usuario();
        Ctrl_Usuario controlUsuario = new Ctrl_Usuario();

        if (txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar al menos el nombre, los apellidos y el correo.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));

        } else {
            // Utiliza el ID del usuario seleccionado
            usuario.setIdUsuario(idUsuario);

            usuario.setNombre(txtNombre.getText().trim());
            usuario.setApellidos(txtApellidos.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setClave(txtClave.getText().trim());

            // Obtener el índice seleccionado en el JComboBox cbPermisos y asignar el valor correspondiente a usuario.setIdRoles()
            int selectedRolIndex = cbPermisos.getSelectedIndex();
            if (selectedRolIndex != 0 && selectedRolIndex != -1) {
                int idRoles;
                switch (selectedRolIndex) {
                    case 1: // Administrador
                        idRoles = 1;
                        break;
                    case 2: // Asistente de ventas
                        idRoles = 2;
                        break;
                    case 3: // Asistente de compras
                        idRoles = 3;
                        break;
                    default:
                        // Manejar el caso en que no se seleccione ninguna opción o alguna excepción
                        JOptionPane.showMessageDialog(null, "Debe seleccionar una opción válida en el campo de permisos.",
                                "ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE);
                        return; // Salir del método para evitar operaciones incorrectas
                }
                usuario.setIdRoles(idRoles);
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una opción válida en el campo permisos.",
                        "ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }

            // Obtener el índice seleccionado en el JComboBox cbEstado y asignar el valor correspondiente a usuario.setEstado()
            int selectedEstadoIndex = cbEstado.getSelectedIndex();
            if (selectedEstadoIndex != 0 && selectedEstadoIndex != -1) {
                Object[] estadoValues = {null, true, false};
                usuario.setEstado((Boolean) estadoValues[selectedEstadoIndex]);
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una opción válida en el campo estado.",
                        "ERROR DE SELECCIÓN", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }

            if (controlUsuario.actualizar(usuario)) {
                JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
                dispose(); // Cerrar el diálogo después de actualizar.

                this.ifUsuario.recargarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el usuario.", "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                Limpiar(); // Limpiar los campos en caso de error
            }

        }
    } // Cierre del método.

    /**
     * Método para mostrar la clave oculta del campo password.
     */
    public void mostrarClave() {
        if (ckbxVerClave.isSelected() == true) {
            String pass = "";
            char[] passIngresado = txtClave.getPassword();
            for (int i = 0; i < passIngresado.length; i++) {
                pass += passIngresado[i];
            }
            txtClaveVisible.setText(pass);
            txtClave.setVisible(false);
            txtClaveVisible.setVisible(true);
        } else {
            String claveIngresada = txtClaveVisible.getText().trim();
            txtClave.setText(claveIngresada);
            txtClave.setVisible(true);
            txtClaveVisible.setVisible(false);
        }
    } // Cierre del método.

    /**
     * Método para establecer el ID del usuario.
     *
     * @param idUsuario El ID del usuario a establecer.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Método que devuelve el internal frame Usuarios.
     *
     * @return Internal Frame Usuarios.
     */
    public InterUsuarios getIfUsuario() {
        return ifUsuario;
    } // Cierre del método.

    /**
     * Método que establece el internal frame Usuarios.
     *
     * @param ifUsuario Internal Frame Usuarios.
     */
    public void setIfUsuario(InterUsuarios ifUsuario) {
        this.ifUsuario = ifUsuario;
    } // Cierre del método.

    /**
     * Método para limpiar los campos.
     */
    private void Limpiar() {

        txtNombre.setText("");
        txtApellidos.setText("");
        txtEmail.setText("");
        txtClave.setText("");
        txtClaveVisible.setText("");
        cbPermisos.setSelectedItem("Seleccionar permiso:");
        cbEstado.setSelectedItem("Seleccionar estado:");

    } // Cierre del método.

    /**
     * Retorna un icono escalado de acuerdo a la ruta y las dimensiones
     * especificadas.
     *
     * @param path La ruta del icono.
     * @param width La anchura del icono.
     * @param heigth La altura del icono.
     * @return La imagen del icono.
     */
    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    } // Cierre del método.
} // Cierre de la clase.
