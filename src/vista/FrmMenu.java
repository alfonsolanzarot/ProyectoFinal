package vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author Alfonso Lanzarot
 */
public class FrmMenu extends javax.swing.JFrame {

    public static EscritorioPersonalizado Escritorio;

    public FrmMenu() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/img/bullet_BOMS.png")).getImage());
        this.setSize(new Dimension(1350, 750));
        this.setExtendedState(FrmMenu.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setTitle("Back Office Management System – BOMS");

        this.setLayout(null);
        Escritorio = new EscritorioPersonalizado();

        int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        FrmMenu.Escritorio.setBounds(0, 0, ancho, (alto - 102));
        this.add(Escritorio);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuGeneral = new javax.swing.JMenuBar();
        menuLogo = new javax.swing.JMenu();
        mnContactos = new javax.swing.JMenu();
        miClientes = new javax.swing.JMenuItem();
        miProveedores = new javax.swing.JMenuItem();
        mnVentas = new javax.swing.JMenu();
        miPrespuestos = new javax.swing.JMenuItem();
        miFacturasProforma = new javax.swing.JMenuItem();
        miPedidosVenta = new javax.swing.JMenuItem();
        miFacturasEmitidas = new javax.swing.JMenuItem();
        mnGastos = new javax.swing.JMenu();
        miFacturasRecibidas = new javax.swing.JMenuItem();
        mnProductos = new javax.swing.JMenu();
        miProductosServicios = new javax.swing.JMenuItem();
        mnInformes = new javax.swing.JMenu();
        mnConfiguracion = new javax.swing.JMenu();
        miGestionUsuarios = new javax.swing.JMenuItem();
        miOtrasConfiguraciones = new javax.swing.JMenuItem();
        mnAyuda = new javax.swing.JMenu();
        mnSalir = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuGeneral.setBackground(new java.awt.Color(31, 78, 121));
        menuGeneral.setMinimumSize(new java.awt.Dimension(1250, 50));
        menuGeneral.setPreferredSize(new java.awt.Dimension(1250, 50));

        menuLogo.setBackground(new java.awt.Color(255, 255, 255));
        menuLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/favicon_BOMS.png"))); // NOI18N
        menuLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        menuLogo.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/favicon_BOMS.png"))); // NOI18N
        menuLogo.setEnabled(false);
        menuLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuLogo.setMargin(new java.awt.Insets(0, 12, 0, 0));
        menuLogo.setOpaque(true);
        menuLogo.setPreferredSize(new java.awt.Dimension(70, 50));
        menuGeneral.add(menuLogo);

        mnContactos.setBackground(new java.awt.Color(186, 213, 238));
        mnContactos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contactos.png"))); // NOI18N
        mnContactos.setText("Contactos");
        mnContactos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnContactos.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        mnContactos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnContactos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnContactos.setMargin(new java.awt.Insets(5, 10, 3, 10));
        mnContactos.setMinimumSize(new java.awt.Dimension(160, 50));
        mnContactos.setName(""); // NOI18N
        mnContactos.setPreferredSize(new java.awt.Dimension(147, 50));

        miClientes.setBackground(new java.awt.Color(186, 213, 238));
        miClientes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clientes.png"))); // NOI18N
        miClientes.setText("Clientes");
        miClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miClientes.setPreferredSize(new java.awt.Dimension(200, 33));
        miClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miClientesActionPerformed(evt);
            }
        });
        mnContactos.add(miClientes);

        miProveedores.setBackground(new java.awt.Color(186, 213, 238));
        miProveedores.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/proveedores.png"))); // NOI18N
        miProveedores.setText("Proveedores");
        miProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miProveedores.setPreferredSize(new java.awt.Dimension(200, 33));
        mnContactos.add(miProveedores);

        menuGeneral.add(mnContactos);

        mnVentas.setBackground(new java.awt.Color(186, 213, 238));
        mnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ventas.png"))); // NOI18N
        mnVentas.setText("Ventas");
        mnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnVentas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        mnVentas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnVentas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnVentas.setMargin(new java.awt.Insets(5, 10, 3, 10));
        mnVentas.setMinimumSize(new java.awt.Dimension(160, 50));
        mnVentas.setPreferredSize(new java.awt.Dimension(147, 50));

        miPrespuestos.setBackground(new java.awt.Color(186, 213, 238));
        miPrespuestos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miPrespuestos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/presupuesto.png"))); // NOI18N
        miPrespuestos.setText("Presupuestos");
        miPrespuestos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miPrespuestos.setPreferredSize(new java.awt.Dimension(200, 33));
        mnVentas.add(miPrespuestos);

        miFacturasProforma.setBackground(new java.awt.Color(186, 213, 238));
        miFacturasProforma.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miFacturasProforma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/factura_proforma.png"))); // NOI18N
        miFacturasProforma.setText("Facturas proforma");
        miFacturasProforma.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miFacturasProforma.setPreferredSize(new java.awt.Dimension(200, 33));
        mnVentas.add(miFacturasProforma);

        miPedidosVenta.setBackground(new java.awt.Color(186, 213, 238));
        miPedidosVenta.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miPedidosVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pedido.png"))); // NOI18N
        miPedidosVenta.setText("Pedidos de venta");
        miPedidosVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miPedidosVenta.setPreferredSize(new java.awt.Dimension(200, 33));
        mnVentas.add(miPedidosVenta);

        miFacturasEmitidas.setBackground(new java.awt.Color(186, 213, 238));
        miFacturasEmitidas.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miFacturasEmitidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/factura_emitida.png"))); // NOI18N
        miFacturasEmitidas.setText("Facturas emitidas");
        miFacturasEmitidas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miFacturasEmitidas.setPreferredSize(new java.awt.Dimension(200, 33));
        mnVentas.add(miFacturasEmitidas);

        menuGeneral.add(mnVentas);

        mnGastos.setBackground(new java.awt.Color(186, 213, 238));
        mnGastos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/gastos.png"))); // NOI18N
        mnGastos.setText("Gastos");
        mnGastos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnGastos.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        mnGastos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnGastos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnGastos.setMargin(new java.awt.Insets(5, 10, 3, 10));
        mnGastos.setMinimumSize(new java.awt.Dimension(160, 50));
        mnGastos.setPreferredSize(new java.awt.Dimension(147, 50));

        miFacturasRecibidas.setBackground(new java.awt.Color(186, 213, 238));
        miFacturasRecibidas.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miFacturasRecibidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/factura_recibida.png"))); // NOI18N
        miFacturasRecibidas.setText("Facturas recibidas");
        miFacturasRecibidas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miFacturasRecibidas.setPreferredSize(new java.awt.Dimension(200, 33));
        mnGastos.add(miFacturasRecibidas);

        menuGeneral.add(mnGastos);

        mnProductos.setBackground(new java.awt.Color(186, 213, 238));
        mnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/productos.png"))); // NOI18N
        mnProductos.setText("Productos");
        mnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnProductos.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        mnProductos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnProductos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnProductos.setMargin(new java.awt.Insets(5, 10, 3, 10));
        mnProductos.setMinimumSize(new java.awt.Dimension(160, 50));
        mnProductos.setPreferredSize(new java.awt.Dimension(147, 50));

        miProductosServicios.setBackground(new java.awt.Color(186, 213, 238));
        miProductosServicios.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miProductosServicios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/caja.png"))); // NOI18N
        miProductosServicios.setText("Productos/Servicios");
        miProductosServicios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miProductosServicios.setPreferredSize(new java.awt.Dimension(200, 33));
        mnProductos.add(miProductosServicios);

        menuGeneral.add(mnProductos);

        mnInformes.setBackground(new java.awt.Color(186, 213, 238));
        mnInformes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/presentacion.png"))); // NOI18N
        mnInformes.setText("Informes");
        mnInformes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnInformes.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        mnInformes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnInformes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnInformes.setMargin(new java.awt.Insets(5, 10, 3, 10));
        mnInformes.setMinimumSize(new java.awt.Dimension(160, 50));
        mnInformes.setPreferredSize(new java.awt.Dimension(147, 50));
        menuGeneral.add(mnInformes);

        mnConfiguracion.setBackground(new java.awt.Color(186, 213, 238));
        mnConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/configuraciones.png"))); // NOI18N
        mnConfiguracion.setText("Configuración");
        mnConfiguracion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnConfiguracion.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        mnConfiguracion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnConfiguracion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnConfiguracion.setMargin(new java.awt.Insets(5, 10, 3, 10));
        mnConfiguracion.setMinimumSize(new java.awt.Dimension(170, 50));
        mnConfiguracion.setPreferredSize(new java.awt.Dimension(180, 50));

        miGestionUsuarios.setBackground(new java.awt.Color(186, 213, 238));
        miGestionUsuarios.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miGestionUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuarios.png"))); // NOI18N
        miGestionUsuarios.setText("Gestión de usuarios");
        miGestionUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miGestionUsuarios.setPreferredSize(new java.awt.Dimension(210, 33));
        mnConfiguracion.add(miGestionUsuarios);

        miOtrasConfiguraciones.setBackground(new java.awt.Color(186, 213, 238));
        miOtrasConfiguraciones.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miOtrasConfiguraciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/llave.png"))); // NOI18N
        miOtrasConfiguraciones.setText("Otras configuraciones");
        miOtrasConfiguraciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        miOtrasConfiguraciones.setPreferredSize(new java.awt.Dimension(210, 33));
        mnConfiguracion.add(miOtrasConfiguraciones);

        menuGeneral.add(mnConfiguracion);

        mnAyuda.setBackground(new java.awt.Color(186, 213, 238));
        mnAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ayuda.png"))); // NOI18N
        mnAyuda.setText("Ayuda");
        mnAyuda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnAyuda.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        mnAyuda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnAyuda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnAyuda.setMargin(new java.awt.Insets(5, 10, 3, 10));
        mnAyuda.setMinimumSize(new java.awt.Dimension(160, 50));
        mnAyuda.setPreferredSize(new java.awt.Dimension(142, 50));
        menuGeneral.add(mnAyuda);

        mnSalir.setBackground(new java.awt.Color(186, 213, 238));
        mnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/salida.png"))); // NOI18N
        mnSalir.setText("Salir");
        mnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mnSalir.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        mnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        mnSalir.setMargin(new java.awt.Insets(5, 10, 3, 10));
        mnSalir.setMinimumSize(new java.awt.Dimension(160, 50));
        mnSalir.setPreferredSize(new java.awt.Dimension(142, 50));
        mnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mnSalirMouseClicked(evt);
            }
        });
        menuGeneral.add(mnSalir);

        setJMenuBar(menuGeneral);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miClientesActionPerformed
        InterClientes interClientes = new InterClientes();
        Escritorio.add(interClientes);
        interClientes.setVisible(true);
    }//GEN-LAST:event_miClientesActionPerformed

    private void mnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_mnSalirMouseClicked

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
            java.util.logging.Logger.getLogger(FrmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar menuGeneral;
    private javax.swing.JMenu menuLogo;
    private javax.swing.JMenuItem miClientes;
    private javax.swing.JMenuItem miFacturasEmitidas;
    private javax.swing.JMenuItem miFacturasProforma;
    private javax.swing.JMenuItem miFacturasRecibidas;
    private javax.swing.JMenuItem miGestionUsuarios;
    private javax.swing.JMenuItem miOtrasConfiguraciones;
    private javax.swing.JMenuItem miPedidosVenta;
    private javax.swing.JMenuItem miPrespuestos;
    private javax.swing.JMenuItem miProductosServicios;
    private javax.swing.JMenuItem miProveedores;
    private javax.swing.JMenu mnAyuda;
    private javax.swing.JMenu mnConfiguracion;
    private javax.swing.JMenu mnContactos;
    private javax.swing.JMenu mnGastos;
    private javax.swing.JMenu mnInformes;
    private javax.swing.JMenu mnProductos;
    private javax.swing.JMenu mnSalir;
    private javax.swing.JMenu mnVentas;
    // End of variables declaration//GEN-END:variables

    /**
     * **********************************************
     * CLASE PARA DIBUJAR IMAGEN EN EL JDesktopPane.
     *
     * **********************************************
     */
    public class EscritorioPersonalizado extends JDesktopPane {

        private BufferedImage img;

        public EscritorioPersonalizado() {
            try {
                img = ImageIO.read(getClass().getResourceAsStream("/img/bullet_BOMS.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        /**
         *
         * @param g
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 800, 350, null);
        }

    }

}
