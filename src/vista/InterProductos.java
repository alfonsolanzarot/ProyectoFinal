package vista;

import controlador.Ctrl_Producto;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import modelo.Producto;
import servicios.ServicioProducto;

/**
 * Esta clase proporciona funcionalidades para crear, editar, eliminar y mostrar
 * información de productos. Permite gestionar la información relacionada con
 * los productos.
 *
 * @author Alfonso Lanzarot
 */
public class InterProductos extends javax.swing.JInternalFrame {

    /**
     * Variables de instancia de la clase.
     */
    private final Map<Integer, Integer> idProductoPorFila = new HashMap<>();
    private int idProducto;
    List<Producto> listaProductos = new ArrayList<>();

    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();

    int ancho = (int) d.getWidth();
    int alto = (int) d.getHeight();

    /**
     * Constructor de la clase InterProductos. Inicializa el JInternalFrame,
     * configura su tamaño y título, carga y configura la tabla de productos, y
     * añade un WindowListener para cargar la tabla cuando se abre el frame
     * interno.
     */
    public InterProductos() {
        initComponents();
        this.setSize(ancho, alto);
        this.setTitle("PRODUCTOS");
        CargarTablaProductos();
        configurarTablaProductos();

        // Añadimos WindowListener para detectar cuándo se abre el frame interno
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                CargarTablaProductos(); // Llama al método para cargar la tabla cuando se abre el frame
            }
        });

    } // Cierre del constructor.

    /**
     * Este método configura la tabla de productos, estableciendo el modelo de
     * la tabla, personalizando el encabezado, el tamaño de las filas, el tipo
     * de letra y tamaño del contenido de la tabla, así como el color de fondo
     * del JScrollPane. También alinea el contenido de ciertas columnas y
     * personaliza el renderizado del encabezado de la tabla.
     */
    private void configurarTablaProductos() {
        // Crear un modelo de tabla
        DefaultTableModel model = new DefaultTableModel();

        // Agregar columnas al modelo de tabla
        model.addColumn("Código del producto o servicio");
        model.addColumn("Descripción");
        model.addColumn("Formato");
        model.addColumn("Peso unitario (en kg)");
        model.addColumn("Precio alto (en €)");
        model.addColumn("Precio bajo (en €)");
        model.addColumn("Precio del servicio (en €)");

        // Establecer el modelo de tabla en la tabla
        tblProductos.setModel(model);

        // Personalizar el encabezado de la tabla
        JTableHeader header = tblProductos.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());

        // Aumentar la altura del encabezado de la tabla
        int alturaEncabezado = 42; // Puedes ajustar este valor según tus preferencias
        header.setPreferredSize(new Dimension(header.getWidth(), alturaEncabezado));

        // Obtener el renderizador predeterminado del encabezado
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tblProductos.getTableHeader().getDefaultRenderer();

        // Establecer alineación centrada para el renderizador del encabezado
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Personalizar el tamaño de las filas
        tblProductos.setRowHeight(60); // Cambiar el tamaño de las filas

        // Personalizar el tipo de letra y tamaño de la letra del contenido de la tabla
        tblProductos.setFont(new Font("Roboto", Font.PLAIN, 12)); // Cambiar el tipo de letra y tamaño

        // Cambiar el color de fondo del jScrollPane.
        jScrollPane1.getViewport().setBackground(new Color(247, 247, 252));

        // Renderizador para alinear al centro las celdas de las columnas de código, formato, peso y los precios
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblProductos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Código
        tblProductos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Formato

        // Crear un renderizador personalizado para las celdas de las columnas con decimales
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        DefaultTableCellRenderer decimalRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    value = decimalFormat.format(value) + " €"; // Añadir el símbolo del euro después del valor
                }
                super.setValue(value);

            }

            // Asegurar que la alineación se mantenga centrada
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
            }
        };

        // Aplicar el renderizador personalizado a las columnas de los precios
        tblProductos.getColumnModel().getColumn(4).setCellRenderer(decimalRenderer);
        tblProductos.getColumnModel().getColumn(5).setCellRenderer(decimalRenderer);
        tblProductos.getColumnModel().getColumn(6).setCellRenderer(decimalRenderer);

        // Renderizador para las unidades de peso
        DefaultTableCellRenderer unitRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    value = decimalFormat.format(value) + " kg"; // Añadir la unidad kg después del valor
                }
                super.setValue(value);
            }

            // Asegurar que la alineación se mantenga centrada
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
            }
        };
        tblProductos.getColumnModel().getColumn(3).setCellRenderer(unitRenderer); // Peso unitario
    } //Cierre del método.

    /**
     * Clase que personaliza el renderizado del encabezado de la tabla.
     */
    public class CustomHeaderRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

        /**
         * Construye un renderizador de encabezado personalizado.
         */
        public CustomHeaderRenderer() {
            setOpaque(true); // Asegura que el componente es opaco

        }

        /**
         * Obtiene el componente del encabezado de la tabla y aplica un estilo
         * personalizado.
         *
         * @param table Tabla.
         * @param value Valor en la tabla.
         * @param isSelected Si se selecciona una fila de la tabla.
         * @param hasFocus Foco.
         * @param row Fila.
         * @param column Columna.
         * @return El componente del encabezado de la tabla con el estilo
         * personalizado aplicado.
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
    } // Cierre de la clase.

    /**
     * Clase de conexión a la base de datos.
     */
    public class ConexionBD {

        Connection con;
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbName = "bd_boms";
        String url = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&serverTimezone=UTC";
        String usuario = "root";
        String clave = "dugu&7Photh&";

        /**
         * Constructor por defecto de la clase ConexionBD.
         *
         * Este constructor inicializa la conexión a la base de datos con los
         * valores predeterminados.
         */
        public ConexionBD() {
            // Inicialización de la conexión a la base de datos
        }
    } // Cierre de la clase.

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
        tblProductos = new javax.swing.JTable();
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
        lblTitulo.setText("Productos y servicios");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 35, -1, -1));

        btnAnadir.setBackground(new java.awt.Color(106, 141, 162));
        btnAnadir.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        btnAnadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAnadir.setText("Nuevo producto");
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

        tblProductos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblProductos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProductos.setFocusable(false);
        tblProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProductos);
        if (tblProductos.getColumnModel().getColumnCount() > 0) {
            tblProductos.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            tblProductos.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            tblProductos.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            tblProductos.getColumnModel().getColumn(3).setHeaderValue("Title 4");
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
    /**
     * Acción del botón Añadir Producto. Abre el diálogo para crear un producto
     * o un servicio nuevo.
     *
     * @param evt Evento de acción del botón.
     */
    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        Frame f = JOptionPane.getFrameForComponent(this);
        DlgProductos dlgProductos = new DlgProductos(f, true);
        dlgProductos.setIfProducto(this);
        dlgProductos.setVisible(true);


    }//GEN-LAST:event_btnAnadirActionPerformed
    /**
     * Acción del botón Eliminar Producto. Elimina el producto o servicio de la
     * fila seleccionada en la tabla de productos.
     *
     * @param evt Evento de acción del botón.
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_btnEliminarActionPerformed
    /**
     * Acción del botón Editar Producto. Abre el diálogo para editar el producto
     * o servicio seleccionado en la tabla.
     *
     * @param evt Evento de acción del botón.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editarProducto();
    }//GEN-LAST:event_btnEditarActionPerformed
    /**
     * Acción del botón Buscar. Busca los productos o servicios de la tabla.
     *
     * @param evt Evento de acción del botón.
     */
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed
    /**
     * Acción de buscar presionando Enter. Realiza la búsqueda de productos o
     * servicios en la tabla al presionar la tecla Enter.
     *
     * @param evt Evento de teclado.
     */
    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.buscar();
        }
    }//GEN-LAST:event_txtBuscarKeyPressed
    /**
     * Cambia el color de fondo del botón Editar cuando el mouse entra en el
     * área del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnEditarMouseEntered
    /**
     * Restaura el color de fondo del botón Editar cuando el mouse sale del área
     * del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnEditarMouseExited
    /**
     * Cambia el color de fondo del botón Buscar cuando el mouse entra en el
     * área del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseEntered
        btnBuscar.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnBuscarMouseEntered
    /**
     * Restaura el color de fondo del botón Buscar cuando el mouse sale del área
     * del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseExited
        btnBuscar.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnBuscarMouseExited
    /**
     * Cambia el color de fondo del botón Añadir cuando el mouse entra en el
     * área del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnAnadirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirMouseEntered
        btnAnadir.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnAnadirMouseEntered
    /**
     * Restaura el color de fondo del botón Añadir cuando el mouse sale del área
     * del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnAnadirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirMouseExited
        btnAnadir.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnAnadirMouseExited
    /**
     * Cambia el color de fondo del botón Eliminar cuando el mouse entra en el
     * área del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        btnEliminar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnEliminarMouseEntered
    /**
     * Restaura el color de fondo del botón Eliminar cuando el mouse entra en el
     * área del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        btnEliminar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnEliminarMouseExited

    /**
     * JScrollPane utilizado para mostrar una lista de productos. JTable
     * utilizado para mostrar información sobre los productos.
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadir;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    public static javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlTabla;
    public static javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    /**
     * Método para cargar la tabla con todos los productos o servicios
     * registrados.
     */
    private void CargarTablaProductos() {
        ConexionBD conexion = new ConexionBD();

        try {
            try {
                Class.forName(conexion.driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.con = DriverManager.getConnection(conexion.url, conexion.usuario, conexion.clave);

            DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();

            String sql = "SELECT idProducto, codigo_producto, descripcion, formato, peso_unitario, precio_alto, precio_bajo, precio_servicio FROM tb_productos";

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                listaProductos = new ArrayList<>();

                while (rs.next()) {

                    Producto producto = ServicioProducto.asignarDatosProducto(rs);
                    listaProductos.add(producto);

                    Object[] datosFila = this.asignarDatosModelo(producto);
                    model.addRow(datosFila);

                    // Obtener el ID del producto de la fila actual
                    idProducto = rs.getInt("idProducto");

                    // Obtener el índice de la fila recién agregada
                    int fila = model.getRowCount() - 1;

                    // Asociar el ID del producto con el índice de fila en el HashMap
                    idProductoPorFila.put(fila, idProducto);

                }

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(null, "Error al llenar la tabla productos: " + e,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));

        } finally {

            if (conexion.con != null) {

                try {

                    conexion.con.close();

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex,
                            "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));

                }
            }
        }
    } // Cierre del método.

    /**
     * Método para editar los productos o servicios seleccionados.
     */
    private void editarProducto() {
        // Obtener la fila seleccionada.
        int filaSeleccionada = tblProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtener el ID del producto de la fila seleccionada utilizando el HashMap
            idProducto = idProductoPorFila.get(filaSeleccionada);

            // Obtener los datos de la fila seleccionada.
            DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
            Object[] datosFila = new Object[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                datosFila[i] = modelo.getValueAt(filaSeleccionada, i);
            }

            // Pasar el ID del producto y los datos de la fila al diálogo de edición.
            Frame f = JOptionPane.getFrameForComponent(this);
            DlgProductos dlgEdicionProducto = new DlgProductos(f, true);
            dlgEdicionProducto.setIdProducto(idProducto);
            dlgEdicionProducto.mostrarDatos(idProducto, datosFila); // Pasa el ID del producto y los datos de la fila al diálogo
            dlgEdicionProducto.setIfProducto(this);
            dlgEdicionProducto.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto para editarlo.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }
    } // Cierre del método.

    /**
     * Método para eliminar los productos o servicios seleccionados.
     */
    private void eliminarProducto() {
        Ctrl_Producto controlProducto = new Ctrl_Producto();
        //Obtener la fila seleccionada.
        int filaSeleccionada = tblProductos.getSelectedRow();
        if (filaSeleccionada != -1) {

            idProducto = idProductoPorFila.get(filaSeleccionada);
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar el producto seleccionado?", "ATENCIÓN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icono("/img/pregunta.png", 40, 40));
            if (respuesta == JOptionPane.YES_OPTION) {
                if (controlProducto.eliminar(idProducto)) {

                    this.recargarTabla();
                    JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
                } else {

                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto.",
                            "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un producto para eliminarlo.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }
    } // Cierre del método.

    /**
     * Método para buscar productos o servicios en la tabla. Busca por código y
     * por descripción.
     */
    private void buscar() {
        List<Producto> listaFiltrada = new ArrayList<>();
        for (Producto pr : this.listaProductos) {
            if (pr.getCodigo().toLowerCase().contains(txtBuscar.getText().toLowerCase()) || pr.getDescripcion().toLowerCase().contains(txtBuscar.getText().toLowerCase())) {
                listaFiltrada.add(pr);
            }
        }
        Object[] arrayObjetos = new Object[listaFiltrada.size()];
        DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos
        for (int i = 0; i < listaFiltrada.size(); i++) {
            arrayObjetos[i] = this.asignarDatosModelo(listaFiltrada.get(i));
            model.addRow((Object[]) arrayObjetos[i]);
        }
    } // Cierre del método.

    /**
     * Método para asignar los datos de los productos registrados al modelo de
     * tabla.
     */
    private Object[] asignarDatosModelo(Producto producto) {

        Object fila[] = new Object[7];

        fila[0] = producto.getCodigo();
        fila[1] = producto.getDescripcion();
        fila[2] = producto.getFormato();
        fila[3] = producto.getPesoUnitario();
        fila[4] = producto.getPrecioAlto();
        fila[5] = producto.getPrecioBajo();
        fila[6] = producto.getPrecioServicio();

        return fila;
    } // Cierre del método.

    /**
     * Método para recargar la tabla con todos los productos registrados cuando
     * se añade o se elimina uno.
     */
    public void recargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos

        CargarTablaProductos();
    } // Cierre del método.

    /**
     * Devuelve el botón utilizado para editar productos.
     *
     * @return El botón para editar productos.
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Devuelve el botón utilizado para añadir nuevos productos.
     *
     * @return El botón para añadir nuevos productos.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Devuelve el botón utilizado para eliminar productos.
     *
     * @return El botón para eliminar productos.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

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
