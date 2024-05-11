package vista;

import controlador.Ctrl_Usuario;
import java.awt.Color;
import java.awt.Component;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import modelo.Usuario;
import servicios.ServicioUsuario;

/**
 *
 * @author Alfonso Lanzarot
 */
public class InterUsuarios extends javax.swing.JInternalFrame {

    private final Map<Integer, Integer> idUsuarioPorFila = new HashMap<>();
    private int idUsuario;
    List<Usuario> listaUsuarios = new ArrayList<>();

    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();

    int ancho = (int) d.getWidth();
    int alto = (int) d.getHeight();

    //CONSTRUCTOR
    public InterUsuarios() {
        initComponents();
        this.setSize(ancho, alto);
        this.setTitle("USUARIOS");
        CargarTablaUsuarios();
        configurarTablaUsuarios();

        // Añadimos WindowListener para detectar cuándo se abre el frame interno
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                CargarTablaUsuarios(); // Llama al método para cargar la tabla cuando se abre el frame
            }
        });

    }

    /**
     * ******************************
     * MÉTODO QUE CONFIGURA LA TABLA.
     *
     * ******************************
     */
    private void configurarTablaUsuarios() {
        // Crear un modelo de tabla
        DefaultTableModel model = new DefaultTableModel();

        // Agregar columnas al modelo de tabla
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellidos");
        model.addColumn("Usuario (Email)");
        model.addColumn("Clave");
        model.addColumn("Permisos");
        model.addColumn("Estado");

        // Establecer el modelo de tabla en la tabla
        tblUsuarios.setModel(model);

        // Personalizar el encabezado de la tabla
        JTableHeader header = tblUsuarios.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());

        // Aumentar la altura del encabezado de la tabla
        int alturaEncabezado = 42; // Puedes ajustar este valor según tus preferencias
        header.setPreferredSize(new Dimension(header.getWidth(), alturaEncabezado));

        // Obtener el renderizador predeterminado del encabezado
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tblUsuarios.getTableHeader().getDefaultRenderer();

        // Establecer alineación centrada para el renderizador del encabezado
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Personalizar el tamaño de las filas
        tblUsuarios.setRowHeight(60); // Cambiar el tamaño de las filas

        // Personalizar el tipo de letra y tamaño de la letra del contenido de la tabla
        tblUsuarios.setFont(new Font("Roboto", Font.PLAIN, 12)); // Cambiar el tipo de letra y tamaño

        //Cambiar el color de fondo del jScrollPane.
        jScrollPane1.getViewport().setBackground(new Color(247, 247, 252));

        // Renderizador para alinear al centro las celdas de las columnas de ID, nombre y apellidos, email y clave.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblUsuarios.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        tblUsuarios.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Nombre
        tblUsuarios.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Apellidos
        tblUsuarios.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Email
        tblUsuarios.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Clave

        // Personalizar el renderizado de las celdas de la columna "Permisos"
        tblUsuarios.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof Integer) {
                    int idRol = (int) value;
                    switch (idRol) {
                        case 1:
                            value = "<html><b>Administrador</b></html>";
                            break;
                        case 2:
                            value = "<html><b>Asistente de ventas</b></html>";
                            break;
                        case 3:
                            value = "<html><b>Asistente de compras</b></html>";
                            break;
                        default:
                            value = "<html><b>Desconocido</b></html>";
                    }
                }
                super.setValue(value);
            }
        });

        // Personalizar el renderizado de las celdas de la columna "Estado"
        tblUsuarios.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof Boolean) {
                    boolean estado = (boolean) value;
                    if (estado) {
                        setText("<html><b>ACTIVO</b></html>");
                        setForeground(new Color(0,176,80));
                    } else {
                        setText("<html><b>INACTIVO</b></html>");
                        setForeground(Color.RED);
                    }
                }
            }
        });

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
        tblUsuarios = new javax.swing.JTable();
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
        lblTitulo.setText("Gestión de usuarios");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 35, -1, -1));

        btnAnadir.setBackground(new java.awt.Color(106, 141, 162));
        btnAnadir.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        btnAnadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAnadir.setText("Nuevo usuario");
        btnAnadir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 98, 137), 3));
        btnAnadir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnadir.setFocusPainted(false);
        btnAnadir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAnadir.setOpaque(true);
        btnAnadir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAnadirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAnadirMouseExited(evt);
            }
        });
        btnAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirActionPerformed(evt);
            }
        });
        getContentPane().add(btnAnadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1728, 80, 130, 55));

        btnEditar.setBackground(new java.awt.Color(106, 141, 162));
        btnEditar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 98, 137), 3));
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setFocusPainted(false);
        btnEditar.setOpaque(true);
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 162, 130, 55));

        btnEliminar.setBackground(new java.awt.Color(255, 124, 128));
        btnEliminar.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 0, 102), 3));
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEliminarMouseExited(evt);
            }
        });
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 240, 130, 55));

        pnlTabla.setBackground(new java.awt.Color(247, 247, 252));
        pnlTabla.setPreferredSize(new java.awt.Dimension(1000, 300));
        pnlTabla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblUsuarios = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblUsuarios.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuarios.setFocusable(false);
        tblUsuarios.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblUsuarios);
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            tblUsuarios.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            tblUsuarios.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            tblUsuarios.getColumnModel().getColumn(3).setHeaderValue("Title 4");
        }

        pnlTabla.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1600, 750));

        getContentPane().add(pnlTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 1620, 770));

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });
        getContentPane().add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 105, 210, 30));

        btnBuscar.setBackground(new java.awt.Color(106, 141, 162));
        btnBuscar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 98, 137), 2));
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setPreferredSize(new java.awt.Dimension(48, 30));
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarMouseExited(evt);
            }
        });
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 102, 80, 35));

        lblFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo6.png"))); // NOI18N
        lblFondo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 950));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        Frame f = JOptionPane.getFrameForComponent(this);
        DlgUsuarios dlgUsuarios = new DlgUsuarios(f, true);
        dlgUsuarios.setIfUsuario(this);
        dlgUsuarios.setVisible(true);


    }//GEN-LAST:event_btnAnadirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Ctrl_Usuario controlUsuario = new Ctrl_Usuario();
        //Obtener la fila seleccionada.
        int filaSeleccionada = tblUsuarios.getSelectedRow();
        if (filaSeleccionada != -1) {

            idUsuario = idUsuarioPorFila.get(filaSeleccionada);
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar el usuario seleccionado?", "ATENCIÓN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icono("/img/pregunta.png", 40, 40));
            if (respuesta == JOptionPane.YES_OPTION) {
                if (controlUsuario.eliminar(idUsuario)) {

                    this.recargarTabla();
                    JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
                } else {

                    JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.",
                            "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario para eliminarlo.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // Obtener la fila seleccionada.
        int filaSeleccionada = tblUsuarios.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtener el ID del usuario de la fila seleccionada utilizando el HashMap
            idUsuario = idUsuarioPorFila.get(filaSeleccionada);

            // Obtener los datos de la fila seleccionada.
            DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();
            Object[] datosFila = new Object[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                datosFila[i] = modelo.getValueAt(filaSeleccionada, i);
            }

            // Pasar el ID del usuario y los datos de la fila al diálogo de edición.
            Frame f = JOptionPane.getFrameForComponent(this);
            DlgUsuarios dlgUsuarios = new DlgUsuarios(f, true);
            dlgUsuarios.setIdUsuario(idUsuario);
            dlgUsuarios.mostrarDatos(idUsuario, datosFila); // Pasa el ID del usuario y los datos de la fila al diálogo
            dlgUsuarios.setIfUsuario(this);
            dlgUsuarios.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario para editarlo.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.buscar();
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseEntered
        btnBuscar.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnBuscarMouseEntered

    private void btnBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseExited
        btnBuscar.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnBuscarMouseExited

    private void btnAnadirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirMouseEntered
        btnAnadir.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnAnadirMouseEntered

    private void btnAnadirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirMouseExited
        btnAnadir.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnAnadirMouseExited

    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        btnEliminar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnEliminarMouseEntered

    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        btnEliminar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnEliminarMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadir;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    public static javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlTabla;
    public static javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    /**
     * ***************************************************************
     * MÉTODO PARA CARGAR LA TABLA CON TODOS LOS USUARIOS REGISTRADOS.
     * ***************************************************************
     */
    private void CargarTablaUsuarios() {
        ConexionBD conexion = new ConexionBD();

        try {
            try {
                Class.forName(conexion.driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.con = DriverManager.getConnection(conexion.url, conexion.usuario, conexion.clave);

            DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();

            String sql = "select idUsuario, idRoles, nombre, apellidos, email, clave, estado from tb_usuarios";

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                listaUsuarios = new ArrayList<>();

                while (rs.next()) {

                    Usuario usuario = ServicioUsuario.asignarDatosUsuario(rs);
                    listaUsuarios.add(usuario);

                    Object[] datosFila = this.asignarDatosModelo(usuario);
                    model.addRow(datosFila);

                    // Obtener el ID del usuario de la fila actual
                    idUsuario = rs.getInt("idUsuario");

                    // Obtener el índice de la fila recién agregada
                    int fila = model.getRowCount() - 1;

                    // Asociar el ID del usuario con el índice de fila en el HashMap
                    idUsuarioPorFila.put(fila, idUsuario);

                }

            } catch (SQLException e) {

                System.out.println("Error al llenar la tabla usuarios: " + e);
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
     * ****************************************
     * MÉTODO PARA BUSCAR USUARIOS EN LA TABLA.
     * ****************************************
     */
    public void buscar() {
        List<Usuario> listaFiltrada = new ArrayList<>();
        for (Usuario u : this.listaUsuarios) {
            if (u.getNombre().toLowerCase().contains(txtBuscar.getText().toLowerCase()) || u.getApellidos().toLowerCase().contains(txtBuscar.getText().toLowerCase())) {
                listaFiltrada.add(u);
            }
        }
        Object[] arrayObjetos = new Object[listaFiltrada.size()];
        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos
        for (int i = 0; i < listaFiltrada.size(); i++) {
            arrayObjetos[i] = this.asignarDatosModelo(listaFiltrada.get(i));
            model.addRow((Object[]) arrayObjetos[i]);
        }
    }

    /**
     * **********************************************************************
     * MÉTODO PARA ASIGNAR LOS DATOS DE LOS USUARIOS REGISTRADOS AL MODELO DE
     * TABLA.
     * **********************************************************************
     */
    private Object[] asignarDatosModelo(Usuario usuario) {

        Object fila[] = new Object[7];

        fila[0] = usuario.getIdUsuario();
        fila[1] = usuario.getNombre();
        fila[2] = usuario.getApellidos();
        fila[3] = usuario.getEmail();
        fila[4] = usuario.getClave();
        fila[5] = usuario.getIdRoles();
        fila[6] = usuario.isEstado();

        return fila;
    }

    /**
     * ****************************************************************
     * MÉTODO PARA RECARGAR LA TABLA CON TODOS LOS USUARIOS REGISTRADOS CUANDO
     * SE AÑADE O SE ELIMINA UNO.
     * ****************************************************************
     */
    public void recargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos

        CargarTablaUsuarios();
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

    /**
     * Método para establecer el ID del usuario.
     *
     * @param idUsuario El ID del usuario a establecer.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
