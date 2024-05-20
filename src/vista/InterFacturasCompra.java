package vista;

import controlador.Ctrl_FacturaCompra;
import modelo.FacturaCompra;
import servicios.ServicioFacturaCompra;
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

/**
 * Esta clase representa un JInternalFrame para administrar facturas de compra.
 * Proporciona funcionalidades para crear, editar y mostrar información sobre
 * las facturas de compra.
 *
 * @author Alfonso Lanzarot
 */
public class InterFacturasCompra extends javax.swing.JInternalFrame {

    /**
     * Variables de instancia de la clase.
     */
    private final Map<Integer, Integer> idFacturaCompraPorFila = new HashMap<>();
    private int idFacturaCompra;
    List<FacturaCompra> listaFacturasCompra = new ArrayList<>();

    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();

    int ancho = (int) d.getWidth();
    int alto = (int) d.getHeight();

    /**
     * Constructor de la clase InterFacturasCompra. Inicializa el
     * JInternalFrame, configura su tamaño y título, carga y configura la tabla
     * de facturas de compra, y añade un WindowListener para cargar la tabla
     * cuando se abre el frame interno.
     */
    public InterFacturasCompra() {
        initComponents();
        this.setSize(ancho, alto);
        this.setTitle("FACTURAS DE COMPRA RECIBIDAS");
        CargarTablaFacturasCompra();
        configurarTablaFacturasCompra();

        // Añadimos WindowListener para detectar cuándo se abre el frame interno.
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                CargarTablaFacturasCompra(); // Llama al método para cargar la tabla cuando se abre el frame.
            }
        });

    } // Cierre del constructor.

    /**
     * Clase interna que define un renderizador de celdas personalizado para
     * cambiar el color del texto basado en el estado.
     */
    class CustomTableCellRenderer extends DefaultTableCellRenderer {

        /**
         * Renderiza las celdas de la tabla con un color de texto diferente
         * dependiendo del estado de la factura de compra
         *
         * @param table La tabla donde se renderizará la celda.
         * @param value El valor de la celda.
         * @param isSelected Indica si la celda está seleccionada.
         * @param hasFocus Indica si la celda tiene el foco.
         * @param row El índice de la fila de la celda.
         * @param column El índice de la columna de la celda.
         * @return La componente de la celda renderizada.
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Obtener el valor del estado en la columna "Estado".
            String estado = (String) table.getValueAt(row, 7);

            // Cambiar el color del texto basado en el estado.
            switch (estado) {
                case "PENDIENTE":
                    cellComponent.setForeground(Color.RED);
                    break;
                case "PARCIALMENTE PAGADA":
                    cellComponent.setForeground(new Color(243, 147, 41));
                    break;
                case "PAGADA":
                    cellComponent.setForeground(new Color(0, 176, 80));
                    break;
                default:
                    // Si no se encuentra el estado, mantener el color por defecto.
                    cellComponent.setForeground(table.getForeground());
                    break;
            }

            // Establecer la fuente en negrita.
            Font font = cellComponent.getFont();
            cellComponent.setFont(font.deriveFont(font.getStyle() | Font.BOLD));

            return cellComponent;
        }
    } // Cierre de la clase.

    /**
     * Este método configura la tabla de facturas de compra recibidas,
     * estableciendo el modelo de la tabla, personalizando el encabezado, el
     * tamaño de las filas, el tipo de letra y tamaño del contenido de la tabla,
     * así como el color de fondo del JScrollPane. También alinea el contenido
     * de ciertas columnas y personaliza el renderizado del encabezado de la
     * tabla.
     */
    private void configurarTablaFacturasCompra() {
        // Crear un modelo de tabla.
        DefaultTableModel model = new DefaultTableModel();

        // Agregar columnas al modelo de tabla.
        model.addColumn("Fecha de factura");
        model.addColumn("Número de factura");
        model.addColumn("Proveedor");
        model.addColumn("Descripción");
        model.addColumn("Total euros");
        model.addColumn("Asociada a");
        model.addColumn("Domiciliado");
        model.addColumn("Estado");

        // Establecer el modelo de tabla en la tabla.
        tblFacturasCompra.setModel(model);

        // Personalizar el encabezado de la tabla.
        JTableHeader header = tblFacturasCompra.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());

        // Aumentar la altura del encabezado de la tabla.
        int alturaEncabezado = 42;
        header.setPreferredSize(new Dimension(header.getWidth(), alturaEncabezado));

        // Obtener el renderizador predeterminado del encabezado.
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tblFacturasCompra.getTableHeader().getDefaultRenderer();

        // Establecer alineación centrada para el renderizador del encabezado.
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Personalizar el tamaño de las filas.
        tblFacturasCompra.setRowHeight(60); // Cambiar el tamaño de las filas.

        // Personalizar el tipo de letra y tamaño de la letra del contenido de la tabla.
        tblFacturasCompra.setFont(new Font("Roboto", Font.PLAIN, 12)); // Cambiar el tipo de letra y tamaño.

        //mCambiar el color de fondo del jScrollPane.
        jScrollPane1.getViewport().setBackground(new Color(247, 247, 252));

        // Renderizador para alinear al centro las celdas de las columnas de fecha, número, total euros, asociada a, domiciliado y estado.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblFacturasCompra.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Fecha
        tblFacturasCompra.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Número
        tblFacturasCompra.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Total euros
        tblFacturasCompra.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Asociada a
        tblFacturasCompra.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Domiciliado
        tblFacturasCompra.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Estado

        // Crear un renderizador personalizado para las celdas de las columnas con decimales.
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        DefaultTableCellRenderer decimalRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    value = decimalFormat.format(value) + " €"; // Añadir el símbolo del euro después del valor.
                }
                super.setValue(value);

            }

            // Asegurar que la alineación se mantenga centrada.
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
            }
        };

        // Aplicar el renderizador personalizado a las columnas de los importes del total euros.
        tblFacturasCompra.getColumnModel().getColumn(4).setCellRenderer(decimalRenderer); // Total euros.

        // Personalizar el renderizado de las celdas de la columna "Domiciliado"
        tblFacturasCompra.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof Boolean) {
                    boolean domiciliado = (boolean) value;
                    if (domiciliado) {
                        setText("<html><b>SÍ</b></html>");
                        setForeground(new Color(0, 176, 80));
                    } else {
                        setText("<html><b>NO</b></html>");
                        setForeground(new Color(15, 158, 213));
                    }
                }
            }
        });
        // Establecer el renderizador personalizado para la columna "Estado".
        tblFacturasCompra.getColumnModel().getColumn(7).setCellRenderer(new InterFacturasCompra.CustomTableCellRenderer());

    } // Cierre del método.

    /**
     * Clase que personaliza el renderizado del encabezado de la tabla.
     */
    public class CustomHeaderRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

        /**
         * Construye un renderizador de encabezado personalizado.
         */
        public CustomHeaderRenderer() {
            setOpaque(true); // Asegura que el componente es opaco.

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
            // Establece el color de fondo y la fuente del encabezado de la tabla.
            component.setBackground(new Color(106, 141, 162));
            component.setFont(new Font("Roboto", Font.BOLD, 12));
            component.setForeground(Color.WHITE);
            // Establecer bordes al componente del encabezado.
            Border border = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE); // Borde blanco en la parte inferior y derecha.
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
        tblFacturasCompra = new javax.swing.JTable();
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
        lblTitulo.setText("Facturas recibidas");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 35, -1, -1));

        btnAnadir.setBackground(new java.awt.Color(106, 141, 162));
        btnAnadir.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        btnAnadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAnadir.setText("Nueva factura");
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

        tblFacturasCompra = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblFacturasCompra.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblFacturasCompra.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFacturasCompra.setFocusable(false);
        tblFacturasCompra.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblFacturasCompra);
        if (tblFacturasCompra.getColumnModel().getColumnCount() > 0) {
            tblFacturasCompra.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            tblFacturasCompra.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            tblFacturasCompra.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            tblFacturasCompra.getColumnModel().getColumn(3).setHeaderValue("Title 4");
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
     * Acción del botón Añadir Factura.
     *
     * @param evt Evento que se produce al hacer clic en el botón Añadir
     * Usuario.
     */
    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        Frame f = JOptionPane.getFrameForComponent(this);
        DlgFacturasCompra dlgFacturasCompra = new DlgFacturasCompra(f, true);
        dlgFacturasCompra.setIfFacturaCompra(this);
        dlgFacturasCompra.setVisible(true);


    }//GEN-LAST:event_btnAnadirActionPerformed
    /**
     * Acción del botón Eliminar Factura.
     *
     * @param evt Evento que se produce al hacer clic en el botón Eliminar
     * Factura.
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarFacturaCompra();
    }//GEN-LAST:event_btnEliminarActionPerformed
    /**
     * Acción del botón Editar Factura.
     *
     * @param evt Evento que se produce al hacer clic en el botón Editar
     * Factura.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editarFacturaCompra();
    }//GEN-LAST:event_btnEditarActionPerformed
    /**
     * Acción del botón Buscar.
     *
     * @param evt Evento que se produce al hacer clic en el botón Buscar.
     */
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed
    /**
     * Acción de buscar presionando Enter.
     *
     * @param evt Evento que se produce al presionar Enter en el campo de
     * búsqueda.
     */
    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.buscar();
        }
    }//GEN-LAST:event_txtBuscarKeyPressed
    /**
     * Método para cambiar el color de fondo del botón "Editar" cuando se pasa
     * el ratón por encima.
     *
     * @param evt Evento que se produce cuando el ratón entra en el área del
     * botón "Editar".
     */
    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnEditarMouseEntered
    /**
     * Método para restaurar el color de fondo del botón "Editar" cuando el
     * ratón sale del área del botón.
     *
     * @param evt Evento que se produce cuando el ratón sale del área del botón
     * "Editar".
     */
    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnEditarMouseExited
    /**
     * Método para cambiar el color de fondo del botón "Buscar" cuando se pasa
     * el ratón por encima.
     *
     * @param evt Evento que se produce cuando el ratón entra en el área del
     * botón "Buscar".
     */
    private void btnBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseEntered
        btnBuscar.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnBuscarMouseEntered
    /**
     * Método para restaurar el color de fondo del botón "Buscar" cuando el
     * ratón sale del área del botón.
     *
     * @param evt Evento que se produce cuando el ratón sale del área del botón
     * "Buscar".
     */
    private void btnBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseExited
        btnBuscar.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnBuscarMouseExited
    /**
     * Método para cambiar el color de fondo del botón "Añadir" cuando se pasa
     * el ratón por encima.
     *
     * @param evt Evento que se produce cuando el ratón entra en el área del
     * botón "Añadir".
     */
    private void btnAnadirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirMouseEntered
        btnAnadir.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnAnadirMouseEntered
    /**
     * Método para restaurar el color de fondo del botón "Añadir" cuando el
     * ratón sale del área del botón.
     *
     * @param evt Evento que se produce cuando el ratón sale del área del botón
     * "Añadir".
     */
    private void btnAnadirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnadirMouseExited
        btnAnadir.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnAnadirMouseExited
    /**
     * Método para cambiar el color de fondo del botón "Eliminar" cuando se pasa
     * el ratón por encima.
     *
     * @param evt Evento que se produce cuando el ratón entra en el área del
     * botón "Eliminar".
     */
    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        btnEliminar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnEliminarMouseEntered
    /**
     * Método para restaurar el color de fondo del botón "Eliminar" cuando el
     * ratón sale del área del botón.
     *
     * @param evt Evento que se produce cuando el ratón sale del área del botón
     * "Eliminar".
     */
    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        btnEliminar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnEliminarMouseExited

    /**
     * JScrollPane utilizado para mostrar una lista de usuarios. JTable
     * utilizado para mostrar información sobre los usuarios.
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
    public static javax.swing.JTable tblFacturasCompra;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    /**
     * Método para cargar la tabla con todas las facturas de compra registradas.
     */
    private void CargarTablaFacturasCompra() {
        ConexionBD conexion = new ConexionBD();

        try {
            try {
                Class.forName(conexion.driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterFacturasCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.con = DriverManager.getConnection(conexion.url, conexion.usuario, conexion.clave);

            DefaultTableModel model = (DefaultTableModel) tblFacturasCompra.getModel();

            String sql = "select idFacturaCompra, idProveedor, fecha, numero, nombre_proveedor, descripcion, base_imponible, tipo_iva, iva, base_exenta, retencion, "
                    + "total, domiciliado, observaciones, asociada, estado from tb_factura_compra";

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                listaFacturasCompra = new ArrayList<>();

                while (rs.next()) {

                    FacturaCompra facturaCompra = ServicioFacturaCompra.asignarDatosFacturaCompra(rs);
                    listaFacturasCompra.add(facturaCompra);

                    Object[] datosFila = this.asignarDatosModelo(facturaCompra);
                    model.addRow(datosFila);

                    // Obtener el ID del usuario de la fila actual.
                    idFacturaCompra = rs.getInt("idFacturaCompra");

                    // Obtener el índice de la fila recién agregada.
                    int fila = model.getRowCount() - 1;

                    // Asociar el ID de la factura de compra con el índice de fila en el HashMap.
                    idFacturaCompraPorFila.put(fila, idFacturaCompra);

                }

            } catch (SQLException e) {

                System.out.println("Error al llenar la tabla de facturas de compra: " + e);
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
    } // Cierre del método.

    /**
     * Método para eliminar facturas de compra.
     */
    private void eliminarFacturaCompra() {
        Ctrl_FacturaCompra controlFacturaCompra = new Ctrl_FacturaCompra();
        //Obtener la fila seleccionada.
        int filaSeleccionada = tblFacturasCompra.getSelectedRow();
        if (filaSeleccionada != -1) {

            idFacturaCompra = idFacturaCompraPorFila.get(filaSeleccionada);
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar la factura seleccionada?", "ATENCIÓN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icono("/img/pregunta.png", 40, 40));
            if (respuesta == JOptionPane.YES_OPTION) {
                if (controlFacturaCompra.eliminar(idFacturaCompra)) {

                    this.recargarTabla();
                    JOptionPane.showMessageDialog(null, "Factura eliminada correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
                } else {

                    JOptionPane.showMessageDialog(null, """
                                                        Error al eliminar la factura. No es posible eliminar 
                                                        una factura que tiene asignados vencimientos.
                                                        
                                                        Antes debe editarla y eliminar los vencimientos.
                                                        """,
                            "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una factura para eliminarla.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }
    } // Cierre del método.

    /**
     * Método para editar facturas de compra.
     */
    private void editarFacturaCompra() {
        // Obtener la fila seleccionada.
        int filaSeleccionada = tblFacturasCompra.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtener el ID de la factura de compra de la fila seleccionada utilizando el HashMap.
            idFacturaCompra = idFacturaCompraPorFila.get(filaSeleccionada);

            // Obtener los datos de la fila seleccionada.
            DefaultTableModel modelo = (DefaultTableModel) tblFacturasCompra.getModel();
            Object[] datosFila = new Object[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                datosFila[i] = modelo.getValueAt(filaSeleccionada, i);
            }

            // Pasar el ID de la factura de compra y los datos de la fila al diálogo de edición.
            Frame f = JOptionPane.getFrameForComponent(this);
            DlgFacturasCompra dlgFacturasCompra = new DlgFacturasCompra(f, true);
            dlgFacturasCompra.setIdFacturaCompra(idFacturaCompra);
            dlgFacturasCompra.mostrarDatos(idFacturaCompra, datosFila); // Pasa el ID de la factura de compra y los datos de la fila al diálogo.
            dlgFacturasCompra.setIfFacturaCompra(this);
            dlgFacturasCompra.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una factura para editarla.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }
    } // Cierre del método.

    /**
     * Método para buscar facturas en la tabla. Busca por número, por nombre,
     * por estado y por domiciliado.
     */
    private void buscar() {
        String criterioBusqueda = txtBuscar.getText().toLowerCase();
        List<FacturaCompra> listaFiltrada = new ArrayList<>();

        for (FacturaCompra fc : this.listaFacturasCompra) {
            // Búsqueda por número de factura
            if (fc.getNumero().toLowerCase().contains(criterioBusqueda)) {
                listaFiltrada.add(fc);
            } // Búsqueda por nombre de proveedor
            else if (fc.getNombre_proveedor().toLowerCase().contains(criterioBusqueda)) {
                listaFiltrada.add(fc);
            } // Búsqueda por estado
            else if (fc.getEstado().equalsIgnoreCase(criterioBusqueda)) {
                listaFiltrada.add(fc);
            } // Búsqueda por estado domiciliado
            else if (criterioBusqueda.equals("si") && fc.isDomiciliado()) {
                listaFiltrada.add(fc);
            } else if (criterioBusqueda.equals("no") && !fc.isDomiciliado()) {
                listaFiltrada.add(fc);
            }
        }

        // Mostrar los resultados en la tabla
        DefaultTableModel model = (DefaultTableModel) tblFacturasCompra.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos
        for (FacturaCompra factura : listaFiltrada) {
            Object[] fila = asignarDatosModelo(factura);
            model.addRow(fila);

        }

    } // Cierre del método.

    /**
     * Método para asignar los datos de las facturas de compra registradas al
     * modelo de tabla.
     */
    private Object[] asignarDatosModelo(FacturaCompra facturaCompra) {

        Object fila[] = new Object[8];

        fila[0] = facturaCompra.getFecha();
        fila[1] = facturaCompra.getNumero();
        fila[2] = facturaCompra.getNombre_proveedor();
        fila[3] = facturaCompra.getDescripcion();
        fila[4] = facturaCompra.getTotal();
        fila[5] = facturaCompra.getAsociada();
        fila[6] = facturaCompra.isDomiciliado();
        fila[7] = facturaCompra.getEstado();

        return fila;
    } // Cierre del método.

    /**
     * Método para recargar la tabla con todas las facturas registradas cuando
     * se añade o se elimina una.
     */
    public void recargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblFacturasCompra.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos.

        CargarTablaFacturasCompra();
    } // Cierre del método.

    /**
     * Método para establecer el ID de la factura de compra.
     *
     * @param idFacturaCompra El ID de la factura de compra a establecer.
     */
    public void setIdFacturaCompra(int idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
    } // Cierre del método.

    /**
     * Devuelve el botón utilizado para editar.
     *
     * @return El botón de editar.
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Devuelve el botón utilizado para añadir.
     *
     * @return El botón de añadir.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Devuelve el botón utilizado para eliminar.
     *
     * @return El botón de eliminar.
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
