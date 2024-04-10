package vista;

import controlador.Ctrl_Cliente;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Cliente;

/**
 *
 * @author Alfonso Lanzarot
 */
public class DlgClientes extends javax.swing.JDialog {

    private int xMouse, yMouse;
    private InterClientes ifCliente;

    /**
     * Creates new form DlgClientes
     *
     * @param parent
     * @param modal
     */
    public DlgClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setSize(new Dimension(800, 480));
        this.setLocationRelativeTo(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlClientes = new javax.swing.JPanel();
        lblHeader = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNif = new javax.swing.JLabel();
        txtNif = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblMovil = new javax.swing.JLabel();
        txtMovil = new javax.swing.JTextField();
        lblPoblacion = new javax.swing.JLabel();
        txtPoblacion = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblWeb = new javax.swing.JLabel();
        txtWeb = new javax.swing.JTextField();
        lblProvincia = new javax.swing.JLabel();
        txtProvincia = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        txtPais = new javax.swing.JTextField();
        lblNComercial = new javax.swing.JLabel();
        txtNComercial = new javax.swing.JTextField();
        lblCondicionesPago = new javax.swing.JLabel();
        txtCondicionesPago = new javax.swing.JTextField();
        cbTipo = new javax.swing.JComboBox<>();
        lblTipo = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PnlClientes.setBackground(new java.awt.Color(247, 247, 252));
        PnlClientes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(52, 98, 139), 1, true));
        PnlClientes.setPreferredSize(new java.awt.Dimension(800, 480));

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

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(52, 98, 139));
        lblTitulo.setText("Nuevo cliente");

        lblNombre.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(102, 102, 102));
        lblNombre.setText("Nombre");

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtNombre.setPreferredSize(new java.awt.Dimension(64, 27));

        lblNif.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblNif.setForeground(new java.awt.Color(102, 102, 102));
        lblNif.setText("NIF del contacto");

        txtNif.setBackground(new java.awt.Color(255, 255, 255));
        txtNif.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNif.setForeground(new java.awt.Color(0, 0, 0));
        txtNif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtNif.setPreferredSize(new java.awt.Dimension(64, 27));

        lblCorreo.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblCorreo.setForeground(new java.awt.Color(102, 102, 102));
        lblCorreo.setText("Correo electrónico");

        txtCorreo.setBackground(new java.awt.Color(255, 255, 255));
        txtCorreo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(0, 0, 0));
        txtCorreo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtCorreo.setPreferredSize(new java.awt.Dimension(67, 27));

        lblDireccion.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblDireccion.setForeground(new java.awt.Color(102, 102, 102));
        lblDireccion.setText("Dirección");

        txtDireccion.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(0, 0, 0));
        txtDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtDireccion.setPreferredSize(new java.awt.Dimension(64, 27));

        lblTelefono.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblTelefono.setForeground(new java.awt.Color(102, 102, 102));
        lblTelefono.setText("Teléfono");

        txtTelefono.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefono.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(0, 0, 0));
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtTelefono.setPreferredSize(new java.awt.Dimension(64, 27));
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        lblMovil.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblMovil.setForeground(new java.awt.Color(102, 102, 102));
        lblMovil.setText("Móvil");

        txtMovil.setBackground(new java.awt.Color(255, 255, 255));
        txtMovil.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMovil.setForeground(new java.awt.Color(0, 0, 0));
        txtMovil.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMovil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtMovil.setPreferredSize(new java.awt.Dimension(64, 27));
        txtMovil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMovilKeyTyped(evt);
            }
        });

        lblPoblacion.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblPoblacion.setForeground(new java.awt.Color(102, 102, 102));
        lblPoblacion.setText("Población");

        txtPoblacion.setBackground(new java.awt.Color(255, 255, 255));
        txtPoblacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPoblacion.setForeground(new java.awt.Color(0, 0, 0));
        txtPoblacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtPoblacion.setPreferredSize(new java.awt.Dimension(64, 27));

        lblCodigo.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(102, 102, 102));
        lblCodigo.setText("Código postal");

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(0, 0, 0));
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtCodigo.setPreferredSize(new java.awt.Dimension(64, 27));

        lblWeb.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblWeb.setForeground(new java.awt.Color(102, 102, 102));
        lblWeb.setText("Sitio web");

        txtWeb.setBackground(new java.awt.Color(255, 255, 255));
        txtWeb.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtWeb.setForeground(new java.awt.Color(0, 0, 0));
        txtWeb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtWeb.setPreferredSize(new java.awt.Dimension(67, 27));

        lblProvincia.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblProvincia.setForeground(new java.awt.Color(102, 102, 102));
        lblProvincia.setText("Provincia");

        txtProvincia.setBackground(new java.awt.Color(255, 255, 255));
        txtProvincia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProvincia.setForeground(new java.awt.Color(0, 0, 0));
        txtProvincia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtProvincia.setPreferredSize(new java.awt.Dimension(64, 27));

        lblPais.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblPais.setForeground(new java.awt.Color(102, 102, 102));
        lblPais.setText("País");

        txtPais.setBackground(new java.awt.Color(255, 255, 255));
        txtPais.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPais.setForeground(new java.awt.Color(0, 0, 0));
        txtPais.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtPais.setPreferredSize(new java.awt.Dimension(64, 27));

        lblNComercial.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblNComercial.setForeground(new java.awt.Color(102, 102, 102));
        lblNComercial.setText("Nombre comercial");

        txtNComercial.setBackground(new java.awt.Color(255, 255, 255));
        txtNComercial.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNComercial.setForeground(new java.awt.Color(0, 0, 0));
        txtNComercial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtNComercial.setPreferredSize(new java.awt.Dimension(64, 27));

        lblCondicionesPago.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblCondicionesPago.setForeground(new java.awt.Color(102, 102, 102));
        lblCondicionesPago.setText("Condiciones de pago");

        txtCondicionesPago.setBackground(new java.awt.Color(255, 255, 255));
        txtCondicionesPago.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCondicionesPago.setForeground(new java.awt.Color(0, 0, 0));
        txtCondicionesPago.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtCondicionesPago.setPreferredSize(new java.awt.Dimension(64, 27));

        cbTipo.setBackground(new java.awt.Color(255, 255, 255));
        cbTipo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cbTipo.setForeground(new java.awt.Color(0, 0, 0));
        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Alto", "Bajo" }));
        cbTipo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbTipo.setPreferredSize(new java.awt.Dimension(72, 27));

        lblTipo.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(102, 102, 102));
        lblTipo.setText("Tipo de precio");

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

        btnCancelar.setBackground(new java.awt.Color(255, 124, 128));
        btnCancelar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 102), 3));
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        javax.swing.GroupLayout PnlClientesLayout = new javax.swing.GroupLayout(PnlClientes);
        PnlClientes.setLayout(PnlClientesLayout);
        PnlClientesLayout.setHorizontalGroup(
            PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlClientesLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlClientesLayout.createSequentialGroup()
                        .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(lblTitulo)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDireccion))
                        .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PnlClientesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNif)
                                .addGap(60, 60, 60))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlClientesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PnlClientesLayout.createSequentialGroup()
                        .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlClientesLayout.createSequentialGroup()
                                .addComponent(lblPoblacion)
                                .addGap(235, 235, 235)
                                .addComponent(lblCodigo))
                            .addComponent(lblCondicionesPago))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PnlClientesLayout.createSequentialGroup()
                        .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(PnlClientesLayout.createSequentialGroup()
                                .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(PnlClientesLayout.createSequentialGroup()
                                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProvincia)
                                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(PnlClientesLayout.createSequentialGroup()
                                        .addComponent(lblPais)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(txtCondicionesPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlClientesLayout.createSequentialGroup()
                        .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(PnlClientesLayout.createSequentialGroup()
                                    .addGap(118, 118, 118)
                                    .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(txtNComercial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(PnlClientesLayout.createSequentialGroup()
                                    .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTelefono)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblMovil)
                                        .addComponent(txtMovil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(PnlClientesLayout.createSequentialGroup()
                                    .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblWeb, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblCorreo, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblNComercial, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addComponent(lblTipo)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))
                    .addGroup(PnlClientesLayout.createSequentialGroup()
                        .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(PnlClientesLayout.createSequentialGroup()
                .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PnlClientesLayout.setVerticalGroup(
            PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlClientesLayout.createSequentialGroup()
                .addComponent(lblHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblNif)
                    .addComponent(lblCorreo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDireccion)
                    .addComponent(lblTelefono)
                    .addComponent(lblMovil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPoblacion)
                    .addComponent(lblCodigo)
                    .addComponent(lblWeb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProvincia)
                    .addComponent(lblPais)
                    .addComponent(lblNComercial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNComercial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCondicionesPago)
                    .addComponent(lblTipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCondicionesPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(PnlClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(PnlClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        int key = evt.getKeyChar();
        boolean numero = key >= 32 && key <= 57;
        if (!numero) {
            evt.consume();
        }
        if (txtTelefono.getText().trim().length() == 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtMovilKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMovilKeyTyped
        int key = evt.getKeyChar();
        boolean numero = key >= 32 && key <= 57;
        if (!numero) {
            evt.consume();
        }
        if (txtMovil.getText().trim().length() == 16) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMovilKeyTyped

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        Cliente cliente = new Cliente();
        Ctrl_Cliente controlCliente = new Ctrl_Cliente();

        /**
         * ***************************************************
         * VALIDACIÓN DE CAMPOS VACÍOS Y CREACIÓN DE CLIENTES.
         *
         * ***************************************************
         */
        if (txtNombre.getText().isEmpty() || txtCondicionesPago.getText().isEmpty() || cbTipo.getSelectedItem().equals("Seleccionar")) {
            JOptionPane.showMessageDialog(null, "Debe completar al menos el nombre, las condiciones de pago y el tipo de precio.",
                    "Información", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));

        } else {

            if (!controlCliente.existeCliente(txtNombre.getText().trim())) {

                cliente.setNombre(txtNombre.getText().trim());
                cliente.setNif(txtNif.getText().trim());
                cliente.setEmail(txtCorreo.getText().trim());
                cliente.setDireccion(txtDireccion.getText().trim());
                cliente.setTelefono(txtTelefono.getText().trim());
                cliente.setMovil(txtMovil.getText().trim());
                cliente.setPoblacion(txtPoblacion.getText().trim());
                cliente.setC_postal(txtCodigo.getText().trim());
                cliente.setWebsite(txtWeb.getText().trim());
                cliente.setProvincia(txtProvincia.getText().trim());
                cliente.setPais(txtPais.getText().trim());
                cliente.setN_comercial(txtNComercial.getText().trim());
                cliente.setCondiciones_pago(txtCondicionesPago.getText().trim());
                cliente.setTipo_precio(cbTipo.getSelectedItem().toString());

                if (controlCliente.crear(cliente)) {
                    this.ifCliente.recargarTabla();
                    JOptionPane.showMessageDialog(null, "Cliente creado correctamente.", "Información", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
                    this.Limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear el cliente.",
                            "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                    this.Limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "El cliente ya está registrado en la base de datos.",
                        "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                this.Limpiar();
            }

        }


    }//GEN-LAST:event_btnCrearActionPerformed

    /**
     * *****************************
     * MÉTODO PARA MOVER LA VENTANA.
     *
     * *****************************
     * @param evt
     */

    private void lblHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_lblHeaderMousePressed

    private void lblHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHeaderMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_lblHeaderMouseDragged

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseEntered
        btnCrear.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnCrearMouseEntered

    private void btnCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseExited
        btnCrear.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnCrearMouseExited

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
            java.util.logging.Logger.getLogger(DlgClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DlgClientes dialog = new DlgClientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel PnlClientes;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCondicionesPago;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblMovil;
    private javax.swing.JLabel lblNComercial;
    private javax.swing.JLabel lblNif;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblPoblacion;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblWeb;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCondicionesPago;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtMovil;
    private javax.swing.JTextField txtNComercial;
    private javax.swing.JTextField txtNif;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtPoblacion;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtWeb;
    // End of variables declaration//GEN-END:variables

    /**
     * *********************************************
     * MÉTODO DE ICONOS DE ATENCIÓN Y/O ADVERTENCIA.
     *
     * *********************************************
     *
     * @param path
     * @param width
     * @param heigth
     * @return
     */
    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    }

    /**
     * *******************************
     * MÉTODO PARA LIMPIAR LOS CAMPOS.
     *
     * *******************************
     */
    private void Limpiar() {
        txtNombre.setText("");
        txtNif.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtMovil.setText("");
        txtPoblacion.setText("");
        txtCodigo.setText("");
        txtWeb.setText("");
        txtProvincia.setText("");
        txtPais.setText("");
        txtNComercial.setText("");
        txtCondicionesPago.setText("");
        cbTipo.setSelectedItem("Seleccionar");
    }

    public InterClientes getIfCliente() {
        return ifCliente;
    }

    public void setIfCliente(InterClientes ifCliente) {
        this.ifCliente = ifCliente;
    }

}
