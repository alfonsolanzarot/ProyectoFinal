package vista;

import controlador.Ctrl_Usuario;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.Usuario;

/**
 * Esta clase representa el formulario de inicio de sesión del sistema BOMS
 * (Business Operations Management System). Permite a los usuarios acceder al
 * sistema.
 *
 * @author Alfonso Lanzarot
 */
public class FrmLogin extends javax.swing.JFrame {

    /**
     * Variables de instancia para el manejo del movimiento del formulario
     * mediante el mouse.
     */
    private int xMouse, yMouse;

    /**
     * Constructor de la clase FrmLogin. Inicializa los componentes del
     * formulario, establece la imagen del icono, configura el título, el tamaño
     * y la posición del formulario, y establece el foco en el botón de entrar.
     */
    public FrmLogin() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/bullet_BOMS.png")).getImage());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Login – BOMS");
        this.setSize(new Dimension(1000, 668));
        this.btnEntrar.requestFocus();
    } // Cierre del constructor.

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panLogin = new javax.swing.JPanel();
        btnEntrar = new javax.swing.JButton();
        lblLogo = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtClave = new javax.swing.JPasswordField();
        lblClave = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblCierre = new javax.swing.JLabel();
        lblHeader = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        panLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEntrar.setBackground(new java.awt.Color(180, 209, 236));
        btnEntrar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(140, 118, 68));
        btnEntrar.setText("ENTRAR");
        btnEntrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(140, 118, 68), new java.awt.Color(140, 118, 68)));
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrarMouseExited(evt);
            }
        });
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });
        btnEntrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEntrarKeyPressed(evt);
            }
        });
        panLogin.add(btnEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 432, 90, 30));

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_BOMS.png"))); // NOI18N
        lblLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panLogin.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, 160, -1));

        txtUsuario.setBackground(new java.awt.Color(255, 255, 255));
        txtUsuario.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txtUsuario.setText("Ingrese su correo electrónico");
        txtUsuario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtUsuarioMousePressed(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });
        panLogin.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 220, 30));

        txtClave.setBackground(new java.awt.Color(255, 255, 255));
        txtClave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtClave.setForeground(new java.awt.Color(204, 204, 204));
        txtClave.setText("Contraseña");
        txtClave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtClave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtClaveFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtClaveFocusLost(evt);
            }
        });
        txtClave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtClaveMousePressed(evt);
            }
        });
        txtClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClaveKeyPressed(evt);
            }
        });
        panLogin.add(txtClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 372, 220, 30));

        lblClave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/candado.png"))); // NOI18N
        lblClave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panLogin.add(lblClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 370, -1, -1));

        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuario.png"))); // NOI18N
        lblUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panLogin.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, -1, -1));

        lblCierre.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblCierre.setForeground(new java.awt.Color(137, 115, 65));
        lblCierre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCierre.setText("X");
        lblCierre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCierre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCierre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCierreMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCierreMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCierreMouseExited(evt);
            }
        });
        panLogin.add(lblCierre, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        lblHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblHeaderMouseDragged(evt);
            }
        });
        lblHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHeaderMousePressed(evt);
            }
        });
        panLogin.add(lblHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 30));

        lblLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login_BOMS.png"))); // NOI18N
        lblLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panLogin.add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 668));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Método que registra la posición del cursor del mouse en el panel de
     * encabezado.
     *
     * @param evt Evento del mouse.
     */
    private void lblHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_lblHeaderMousePressed
    /**
     * Método que arrastra la ventana a la nueva posición según el movimiento
     * del mouse.
     *
     * @param evt Evento del mouse.
     */
    private void lblHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHeaderMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_lblHeaderMouseDragged
    /**
     * Este método cierra el diálogo cuando se presiona la X.
     *
     * @param evt El evento de acción del botón.
     */
    private void lblCierreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCierreMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblCierreMouseClicked
    /**
     * Cambia el color del texto del botón de cierre cuando el mouse entra en
     * él.
     *
     * @param evt Evento del mouse.
     */
    private void lblCierreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCierreMouseEntered
        lblCierre.setForeground(Color.yellow);
    }//GEN-LAST:event_lblCierreMouseEntered
    /**
     * Restaura el color del texto del botón de cierre cuando el mouse sale de
     * él.
     *
     * @param evt Evento del mouse.
     */
    private void lblCierreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCierreMouseExited
        lblCierre.setForeground(new Color(137, 115, 65));
    }//GEN-LAST:event_lblCierreMouseExited
    /**
     * Cambia el color de fondo del botón de entrar cuando el mouse entra en él.
     *
     * @param evt Evento del mouse.
     */
    private void btnEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseEntered
        btnEntrar.setBackground(new Color(119, 167, 212));
    }//GEN-LAST:event_btnEntrarMouseEntered
    /**
     * Restaura el color de fondo del botón de entrar cuando el mouse sale de
     * él.
     *
     * @param evt Evento del mouse.
     */
    private void btnEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseExited
        btnEntrar.setBackground(new Color(180, 209, 236));
    }//GEN-LAST:event_btnEntrarMouseExited
    /**
     * Establece el foco en el campo de usuario cuando se hace clic en él.
     *
     * @param evt Evento del mouse.
     */
    private void txtUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMousePressed
        focoUsuario();
    }//GEN-LAST:event_txtUsuarioMousePressed
    /**
     * Establece el foco en el campo de contraseña cuando se hace clic en él.
     *
     * @param evt Evento del mouse.
     */
    private void txtClaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtClaveMousePressed
        focoClave();
    }//GEN-LAST:event_txtClaveMousePressed
    /**
     * Realiza el inicio de sesión cuando se hace clic en el botón de entrar.
     *
     * @param evt Evento del botón.
     */
    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        this.login();
    }//GEN-LAST:event_btnEntrarActionPerformed
    /**
     * Pasa al campo de contraseña cuando se presiona la tecla Enter en el campo
     * de usuario.
     *
     * @param evt Evento del teclado.
     */
    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtClave.requestFocus();
            txtClave.removeAll();
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed
    /**
     * Realiza el inicio de sesión cuando se presiona la tecla Enter en el campo
     * de contraseña.
     *
     * @param evt Evento del teclado.
     */
    private void txtClaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.login();
        }
    }//GEN-LAST:event_txtClaveKeyPressed
    /**
     * Establece el foco en el campo de usuario cuando este gana el foco.
     *
     * @param evt Evento del foco.
     */
    private void txtUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusGained
        focoUsuario();
    }//GEN-LAST:event_txtUsuarioFocusGained
    /**
     * Establece el foco en el campo de usuario cuando este pierde el foco.
     *
     * @param evt Evento del foco.
     */
    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        focoClave();
    }//GEN-LAST:event_txtUsuarioFocusLost
    /**
     * Cambia el foco al campo de contraseña cuando este gana el foco.
     *
     * @param evt Evento del foco.
     */
    private void txtClaveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClaveFocusGained
        focoClave();
    }//GEN-LAST:event_txtClaveFocusGained
    /**
     * Cambia el foco al campo de usuario cuando el campo de contraseña pierde
     * el foco.
     *
     * @param evt Evento del foco.
     */
    private void txtClaveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClaveFocusLost
        focoUsuario();
    }//GEN-LAST:event_txtClaveFocusLost
    /**
     * Método que realiza el inicio de sesión al sistema al presionar la tecla
     * Enter.
     *
     * @param evt Evento del teclado.
     */
    private void btnEntrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEntrarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.login();
        }
    }//GEN-LAST:event_btnEntrarKeyPressed

    /**
     * El método principal que se ejecuta al iniciar la aplicación.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        Color color = new Color(151, 192, 229);
        UIManager.put("nimbusBase", color);
        UIManager.put("Table[Enabled+Selected].textBackground", new Color(157, 195, 230));

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrar;
    private javax.swing.JLabel lblCierre;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panLogin;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    /**
     * Realiza el proceso de inicio de sesión en el sistema.
     */
    private void login() {
        if (!txtUsuario.getText().isEmpty() && !txtClave.getText().isEmpty()) {
            Ctrl_Usuario controlUsuario = new Ctrl_Usuario();
            Usuario usuario = new Usuario();
            usuario.setEmail(txtUsuario.getText().trim());
            usuario.setClave(txtClave.getText().trim());

            try {
                if (controlUsuario.loginUser(usuario)) {
                    String nombre = usuario.getNombre();
                    String apellidos = usuario.getApellidos();
                    int idRol = usuario.getIdRoles();

                    FrmMenu menu = new FrmMenu();
                    menu.setNombreUsuario(nombre);
                    menu.setApellidosUsuario(apellidos);
                    menu.setIdRol(idRol);
                    menu.setVisible(true);
                    this.dispose();
                } else {
                    txtUsuario.setText("");
                    txtClave.setText("");
                    txtUsuario.requestFocus();
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + e.getMessage(),
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese sus credenciales.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
            txtUsuario.requestFocus();
        }
    } // Cierre del método.

    /**
     * Cambia el foco al campo de usuario cuando este gana el foco.
     */
    private void focoUsuario() {
        if (txtUsuario.getText().equals("Ingrese su correo electrónico")) {
            txtUsuario.setText("");
            txtUsuario.setForeground(Color.black);
        }
        if (String.valueOf(txtClave.getPassword()).isEmpty()) {
            txtClave.setText("Contraseña");
            txtClave.setForeground(new Color(204, 204, 204));
        }
    } // Cierre del método.

    /**
     * Cambia el foco al campo de clave cuando este gana el foco.
     */
    private void focoClave() {
        if (String.valueOf(txtClave.getPassword()).equals("Contraseña")) {
            txtClave.setText("");
            txtClave.setForeground(Color.black);
        }
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.setText("Ingrese su correo electrónico");
            txtUsuario.setForeground(new Color(204, 204, 204));
        }
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
