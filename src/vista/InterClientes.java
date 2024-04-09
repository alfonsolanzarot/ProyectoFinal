package vista;

import controlador.Ctrl_Cliente;
import java.awt.Color;
import java.awt.Component;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import modelo.Cliente;

/**
 *
 * @author Alfonso Lanzarot
 */
public class InterClientes extends javax.swing.JInternalFrame {

    private final Map<Integer, Integer> idClientePorFila = new HashMap<>();
    private int idCliente;
    List<Cliente> listaClientes = new ArrayList<>();

    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();

    int ancho = (int) d.getWidth();
    int alto = (int) d.getHeight();

    //CONSTRUCTOR
    public InterClientes() {
        initComponents();
        this.setSize(ancho, alto);
        this.setTitle("CLIENTES");
        CargarTablaClientes();
        configurarTablaClientes();

        // Añadimos WindowListener para detectar cuándo se abre el frame interno
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                CargarTablaClientes(); // Llama al método para cargar la tabla cuando se abre el frame
            }
        });

    }

    /**
     * ******************************
     * MÉTODO QUE CONFIGURA LA TABLA.
     *
     * ******************************
     */
    private void configurarTablaClientes() {
        // Crear un modelo de tabla
        DefaultTableModel model = new DefaultTableModel();

        // Agregar columnas al modelo de tabla
        model.addColumn("Nombre");
        model.addColumn("NIF");
        model.addColumn("E-mail");
        model.addColumn("Teléfono");
        model.addColumn("Móvil");
        model.addColumn("Dirección");
        model.addColumn("Población");
        model.addColumn("C.P.");
        model.addColumn("Provincia");
        model.addColumn("País");
        model.addColumn("Nombre comercial");
        model.addColumn("Condiciones pago");
        model.addColumn("Website");
        model.addColumn("Tipo de precio");

        // Establecer el modelo de tabla en la tabla
        tblClientes.setModel(model);

        // Personalizar el encabezado de la tabla
        JTableHeader header = tblClientes.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());

        // Aumentar la altura del encabezado de la tabla
        int alturaEncabezado = 55; // Puedes ajustar este valor según tus preferencias
        header.setPreferredSize(new Dimension(header.getWidth(), alturaEncabezado));

        // Obtener el renderizador predeterminado del encabezado
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tblClientes.getTableHeader().getDefaultRenderer();

        // Establecer alineación centrada para el renderizador del encabezado
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Personalizar el tamaño de las filas
        tblClientes.setRowHeight(42); // Cambiar el tamaño de las filas

        // Personalizar el tipo de letra y tamaño de la letra del contenido de la tabla
        tblClientes.setFont(new Font("Roboto", Font.PLAIN, 12)); // Cambiar el tipo de letra y tamaño

    }

    /**
     * ****************************************************************
     * CLASE QUE PERSONALIZA EL RENDERIZADO DEL ENCABEZADO DE LA TABLA.
     * ****************************************************************
     */
    public class CustomHeaderRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

        public CustomHeaderRenderer() {
            setOpaque(true); // Asegura que el componente es opaco

        }

        /**
         *
         * @param table
         * @param value
         * @param isSelected
         * @param hasFocus
         * @param row
         * @param column
         * @return
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            // Establece el color de fondo y la fuente del encabezado de la tabla
            component.setBackground(new Color(106, 141, 162));
            component.setFont(new Font("Roboto", Font.BOLD, 12));
            component.setForeground(Color.WHITE);
            // Establecer bordes al componente del encabezado
            Border border = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE); // Borde blanco en la parte inferior y derecha
            ((JLabel) component).setBorder(border);

            return component;
        }
    }

    /**
     * ****************************
     * CONEXIÓN A LA BASE DE DATOS.
     *
     * ****************************
     */
    public class ConexionBD {

        Connection con;
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbName = "bd_boms";
        String url = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&serverTimezone=UTC";
        String usuario = "root";
        String clave = "dugu&7Photh&";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnAnadir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(52, 98, 139));
        lblTitulo.setText("Clientes");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 35, -1, -1));

        btnAnadir.setBackground(new java.awt.Color(157, 195, 230));
        btnAnadir.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        btnAnadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAnadir.setText("+");
        btnAnadir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(52, 98, 139), 4, true));
        btnAnadir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnadir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1760, 28, 100, 60));

        btnEditar.setBackground(new java.awt.Color(157, 195, 230));
        btnEditar.setFont(new java.awt.Font("Roboto", 1, 22)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(52, 98, 139), 4, true));
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 140, 60));

        btnEliminar.setBackground(new java.awt.Color(255, 124, 128));
        btnEliminar.setFont(new java.awt.Font("Roboto", 1, 22)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 102), 4, true));
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 140, 60));

        pnlTabla.setBackground(new java.awt.Color(247, 247, 252));
        pnlTabla.setPreferredSize(new java.awt.Dimension(1000, 300));
        pnlTabla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblClientes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblClientes);
        if (tblClientes.getColumnModel().getColumnCount() > 0) {
            tblClientes.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            tblClientes.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            tblClientes.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            tblClientes.getColumnModel().getColumn(3).setHeaderValue("Title 4");
        }

        pnlTabla.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1600, 750));

        getContentPane().add(pnlTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 1620, 770));

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 210, 30));

        btnBuscar.setBackground(new java.awt.Color(106, 141, 162));
        btnBuscar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 80, 30));

        lblFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo6.png"))); // NOI18N
        lblFondo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 950));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        Frame f = JOptionPane.getFrameForComponent(this);
        DlgClientes dlgClientes = new DlgClientes(f, true);
        dlgClientes.setIfCliente(this);
        dlgClientes.setVisible(true);


    }//GEN-LAST:event_btnAnadirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Ctrl_Cliente controlCliente = new Ctrl_Cliente();
        //Obtener la fila seleccionada.
        int filaSeleccionada = tblClientes.getSelectedRow();
        if (filaSeleccionada != -1) {

            idCliente = idClientePorFila.get(filaSeleccionada);
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar el cliente seleccionado?", "ATENCIÓN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icono("/img/pregunta.png", 40, 40));
            if (respuesta == JOptionPane.YES_OPTION) {
                if (controlCliente.eliminar(idCliente)) {

                    this.recargarTabla();
                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
                } else {

                    JOptionPane.showMessageDialog(null, "Error al eliminar el cliente.",
                            "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para eliminarlo.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // Obtener la fila seleccionada.
        int filaSeleccionada = tblClientes.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtener el ID del cliente de la fila seleccionada utilizando el HashMap
            idCliente = idClientePorFila.get(filaSeleccionada);

            // Obtener los datos de la fila seleccionada.
            DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
            Object[] datosFila = new Object[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                datosFila[i] = modelo.getValueAt(filaSeleccionada, i);
            }

            // Pasar el ID del cliente y los datos de la fila al diálogo de edición.
            Frame f = JOptionPane.getFrameForComponent(this);
            DlgEdicionCliente dlgEdicionCliente = new DlgEdicionCliente(f, true);
            dlgEdicionCliente.setIdCliente(idCliente);
            dlgEdicionCliente.mostrarDatos(idCliente, datosFila); // Pasa el ID del cliente y los datos de la fila al diálogo
            dlgEdicionCliente.setIfCliente(this);
            dlgEdicionCliente.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para editarlo.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        List<Cliente> listaFiltrada = new ArrayList<>();
        for (Cliente c : this.listaClientes) {
            if (c.getNombre().toLowerCase().contains(txtBuscar.getText().toLowerCase())) {
                listaFiltrada.add(c);
            }
        }
        Object[] arrayObjetos = new Object[listaFiltrada.size()];
        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos
        for (int i = 0; i < listaFiltrada.size(); i++) {
            arrayObjetos[i] = this.asignarDatosModelo(listaFiltrada.get(i));
            model.addRow((Object[]) arrayObjetos[i]);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadir;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    public static javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlTabla;
    public static javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    /**
     * ***************************************************************
     * MÉTODO PARA CARGAR LA TABLA CON TODOS LOS CLIENTES REGISTRADOS.
     * ***************************************************************
     */
    private void CargarTablaClientes() {
        ConexionBD conexion = new ConexionBD();

        try {
            try {
                Class.forName(conexion.driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.con = DriverManager.getConnection(conexion.url, conexion.usuario, conexion.clave);

            DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();

            String sql = "select idCliente, nombre, nif, email, telefono, movil, direccion, poblacion, c_postal, provincia, pais, n_comercial, condiciones_pago, website, tipo_precio from tb_clientes";

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                listaClientes = new ArrayList<>();

                while (rs.next()) {

                    Cliente cliente = this.asignarDatosCliente(rs);
                    listaClientes.add(cliente);

                    Object[] datosFila = this.asignarDatosModelo(cliente);
                    model.addRow(datosFila);

                    // Obtener el ID del cliente de la fila actual
                    idCliente = rs.getInt("idCliente");

                    // Obtener el índice de la fila recién agregada
                    int fila = model.getRowCount() - 1;

                    // Asociar el ID del cliente con el índice de fila en el HashMap
                    idClientePorFila.put(fila, idCliente);
                 
                }

            } catch (SQLException e) {

                System.out.println("Error al llenar la tabla clientes: " + e);
            }

        } catch (SQLException e) {

            System.out.println("Error al conectar con la base de datos: " + e);

        } finally {

            if (conexion.con != null) {

                try {

                    conexion.con.close();

                } catch (SQLException ex) {

                    System.out.println("Error al cerrar la conexión: " + ex);

                }
            }
        }
    }

    /**
     * ******************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE UN CLIENTE REGISTRADO A LA TABLA
     * CLIENTES.
     * ******************************************************************
     */
    private Cliente asignarDatosCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setIdCliente(rs.getInt(1));
        cliente.setNombre(rs.getString(2));
        cliente.setNif(rs.getString(3));
        cliente.setEmail(rs.getString(4));
        cliente.setDireccion(rs.getString(5));
        cliente.setTelefono(rs.getString(6));
        cliente.setMovil(rs.getString(7));
        cliente.setPoblacion(rs.getString(8));
        cliente.setC_postal(rs.getString(9));
        cliente.setWebsite(rs.getString(10));
        cliente.setProvincia(rs.getString(11));
        cliente.setPais(rs.getString(12));
        cliente.setN_comercial(rs.getString(13));
        cliente.setCondiciones_pago(rs.getString(14));
        cliente.setTipo_precio(rs.getString(15));

        return cliente;
    }

    /**
     * ***********************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE LOS CLIENTES REGISTRADOS AL MODELO DE
     * TABLA.
     * ***********************************************************************
     */
    private Object[] asignarDatosModelo(Cliente cliente) {

        Object fila[] = new Object[14];

        fila[0] = cliente.getNombre();
        fila[1] = cliente.getNif();
        fila[2] = cliente.getEmail();
        fila[3] = cliente.getDireccion();
        fila[4] = cliente.getTelefono();
        fila[5] = cliente.getMovil();
        fila[6] = cliente.getPoblacion();
        fila[7] = cliente.getC_postal();
        fila[8] = cliente.getWebsite();
        fila[9] = cliente.getProvincia();
        fila[10] = cliente.getPais();
        fila[11] = cliente.getN_comercial();
        fila[12] = cliente.getCondiciones_pago();
        fila[13] = cliente.getTipo_precio();

        return fila;
    }

    /**
     * ************************************************************************
     * MÉTODO PARA RECARGAR LA TABLA CON TODOS LOS CLIENTES REGISTRADOS CUANDO
     * SE AÑADE UNO NUEVO Y SE ELIMINA UN CLIENTE.
     * ************************************************************************
     */
    public void recargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos

        CargarTablaClientes();
    }

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

}
