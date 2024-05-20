package vista;

import java.sql.Statement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;
import conexion.Conexion;
import controlador.Ctrl_ProductosProforma;
import controlador.Ctrl_Proforma;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import modelo.ProductoProforma;
import modelo.Proforma;
import servicios.ServicioProducto;
import servicios.ServicioProductoProforma;

/**
 * Esta clase representa un diálogo para la creación y edición de facturas
 * proforma. Permite gestionar y visualizar información relacionada con las
 * proformas.
 *
 * @author Alfonso Lanzarot
 */
public final class DlgProformas extends javax.swing.JDialog {

    /**
     * Variables de instancia de la clase.
     */
    private int xMouse, yMouse;
    private InterProformas ifProforma;
    private static int numeroProforma = 1;
    private static int ultimoAnio = 0;
    private static int idProforma;
    private Ctrl_Proforma ctrlProforma;
    private int idProducto;
    private int idCliente;
    private boolean esServicio;
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private double totalSubtotal = 0.0;
    private double subtotalTotal = 0.0;
    private double totalIva2 = 0.0;
    private DlgProductosProforma dlgProductosProforma;
    private final Map<Integer, Integer> idProductoPorFila = new HashMap<>();
    private List<ProductoProforma> listaProductosProforma = new ArrayList<>();

    /**
     * Constructor que crea un nuevo formulario DlgProformas.
     *
     * @param parent Frame interProformas
     * @param modal Ventana modal que no nos permite hacer clic en el Frame
     * padre.
     */
    public DlgProformas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setSize(new Dimension(1250, 800));
        this.setLocationRelativeTo(null);
        this.setearFechaPorDefecto();
        this.CargarComboClientes();
        this.camposNoEditables();
        this.datosClienteCombo();
        this.focoFechas();
        this.configurarTablaProductos();
        this.txtSubtotal2.setText("0,00 €");
        this.txtTotalIva2.setText("0,00 €");
        this.txtTotal.setText("0,00 €");
        this.txtKilos.setText("0,00 kg");
        this.txtSubtotal.setText("0,00 €");
        this.txtTotalIva.setText("0,00 €");
        this.listaProductosProforma = new ArrayList<>();

        // Obtener el próximo idProforma disponible
        ctrlProforma = new Ctrl_Proforma();
        idProforma = ctrlProforma.obtenerProximoIdProforma();

        // Obtener y establecer el número de proforma al cargar el formulario
        txtNumero.setText(generarNumeroProforma());

    } // Cierre del constructor.

    /**
     * Método que establece el diálogo para insertar productos o servicios a la
     * factura proforma.
     *
     * @param dlgProductosProforma Diálogo productos proforma.
     */
    public void setDlgProductosProforma(DlgProductosProforma dlgProductosProforma) {
        this.dlgProductosProforma = dlgProductosProforma;
    } // Cierre del método.

    /**
     * Método para actualizar el JTable con los datos de la lista temporal.
     *
     * @param listaProductos Lista de los productos.
     */
    public void actualizarTablaProductos(List<ProductoProforma> listaProductos) {
        configurarTablaProductos();

        DefaultTableModel model = new DefaultTableModel();

        // Verificar si la lista de productos no está vacía
        if (listaProductos != null && !listaProductos.isEmpty()) {
            // La lista de productos no está vacía, se pueden recorrer los elementos
            for (ProductoProforma producto : listaProductos) {
                Object[] row = {producto.getCodigo_producto(), producto.getDescripcion(), producto.getCantidad(),
                    producto.getPrecio_unitario(), producto.getSubtotal(), producto.getTipo_iva(), producto.getImporte_iva()};
                model.addRow(row);
            }
            // Después de haber agregado los datos a la tabla mediante el método actualizarTablaProductos
            // Llama al método revalidate() del JScrollPane para actualizar su contenido y su estado interno
            jScrollPane1.setViewportView(tblProductos);
            jScrollPane1.revalidate();

            listenersCalculos();

            calcularTotales();

        } else {
            // La lista de productos está vacía o es nula
            JOptionPane.showMessageDialog(null, "La lista de productos está vacía o es nula.", "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));

        }

    } // Cierre del método.

    /**
     * Método que llama a la lista de productos actualizada.
     */
    public void onListaProductosActualizada() {
        actualizarTablaProductos(this.listaProductosProforma);

    } // Cierre del método.

    /**
     * Método que define la interfaz ServicioListener
     */
    public interface ServicioListener {

        /**
         * Método invocado cuando se actualiza el estado del servicio.
         *
         * Este método se llama cuando el estado del servicio ha sido
         * actualizado, indicando si el servicio está disponible o no.
         *
         * @param esServicio true si el servicio está disponible, false si no lo
         * está
         */
        void estadoServicioActualizado(boolean esServicio);
    } // Cierre del método.

    /**
     * Método que actualiza la variable esServicio con el valor proporcionado.
     *
     * @param esServicio Parámetro que establece si es un servicio.
     */
    public void estadoServicioActualizado(boolean esServicio) {
        this.esServicio = esServicio;
    } // Cierre del método.

    /**
     * Este método configura la tabla de productos o servicios a incorporar en
     * la factura proforma, estableciendo el modelo de la tabla, personalizando
     * el encabezado, el tamaño de las filas, el tipo de letra y tamaño del
     * contenido de la tabla, así como el color de fondo del JScrollPane.
     * También alinea el contenido de ciertas columnas y personaliza el
     * renderizado del encabezado de la tabla.
     */
    private void configurarTablaProductos() {
        // Crear un modelo de tabla
        DefaultTableModel model = new DefaultTableModel();

        // Agregar columnas al modelo de tabla
        model.addColumn("Código");
        model.addColumn("Descripción");
        model.addColumn("Kilos/Uds.");
        model.addColumn("Precio unitario");
        model.addColumn("Subtotal");
        model.addColumn("Tipo IVA/VAT");
        model.addColumn("IVA/VAT");

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
        tblProductos.setRowHeight(42); // Cambiar el tamaño de las filas

        // Personalizar el tipo de letra y tamaño de la letra del contenido de la tabla
        tblProductos.setFont(new Font("Roboto", Font.PLAIN, 12)); // Cambiar el tipo de letra y tamaño

        // Cambiar el color de fondo del jScrollPane.
        jScrollPane1.getViewport().setBackground(new Color(247, 247, 252));

        tblProductos.setFillsViewportHeight(true);

        // Establecer el color de fondo del JTable
        tblProductos.setBackground(new Color(247, 247, 252));

        // Renderizador para alinear al centro las celdas de las columnas de código, descripción, kilos, precio unitario, subtotal, tipo IVA e IVA.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblProductos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Código
        tblProductos.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Descripción
        tblProductos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Kilos
        tblProductos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Precio unitario
        tblProductos.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Subtotal
        tblProductos.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Tipo IVA
        tblProductos.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // IVA

        // Crear un renderizador personalizado para las celdas de las columnas con decimales
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        decimalFormat = new DecimalFormat("#,##0.00", symbols);

        // Renderizador para las unidades de moneda
        DefaultTableCellRenderer decimalRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    value = decimalFormat.format(value) + " €"; // A�adir el símbolo del euro después del valor
                }
                super.setValue(value);

            }

            // Asegurar que la alineación se mantenga centrada
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
            }
        };

        // Aplicar el renderizador personalizado a las columnas de los importes del precio unitario, el subtotal, y el IVA
        tblProductos.getColumnModel().getColumn(3).setCellRenderer(decimalRenderer);
        tblProductos.getColumnModel().getColumn(4).setCellRenderer(decimalRenderer);
        tblProductos.getColumnModel().getColumn(6).setCellRenderer(decimalRenderer);

        // Renderizador para las unidades de peso
        DefaultTableCellRenderer unitRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    if (esServicio) {
                        value = decimalFormat.format(value) + " ud"; // Para servicio, mostrar unidades
                    } else {
                        value = decimalFormat.format(value) + " kg"; // Para producto, mostrar kilos
                    }
                }
                super.setValue(value);
            }

            // Asegurar que la alineación se mantenga centrada
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
            }
        };
        tblProductos.getColumnModel().getColumn(2).setCellRenderer(unitRenderer);

        // Renderizador para el porcentaje de IVA
        DefaultTableCellRenderer percentageRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    value = decimalFormat.format(value) + " %"; // Añadir el símbolo de % después del valor
                }
                super.setValue(value);
            }

            // Asegurar que la alineación se mantenga centrada
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
            }
        };
        tblProductos.getColumnModel().getColumn(5).setCellRenderer(percentageRenderer); // Porcentaje iva

    } // Cierre del método.

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
     * Método que establece si el producto es un servicio.
     *
     * @param esServicio Parámetro que establece si es servicio.
     */
    public void setEsServicio(boolean esServicio) {
        this.esServicio = esServicio;
    } // Cierre del método.

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlProfoma = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblNumero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblCliente = new javax.swing.JLabel();
        cbCliente = new javax.swing.JComboBox<>();
        lblNif = new javax.swing.JLabel();
        txtNif = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnCrear = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblKilos = new javax.swing.JLabel();
        txtKilos = new javax.swing.JTextField();
        lblSubtotal = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        lblTotalIva = new javax.swing.JLabel();
        txtTotalIva = new javax.swing.JTextField();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        lblCondiciones = new javax.swing.JLabel();
        txtCondiciones = new javax.swing.JTextField();
        lblValidez = new javax.swing.JLabel();
        txtValidez = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblCpostal = new javax.swing.JLabel();
        txtCpostal = new javax.swing.JTextField();
        lblProvincia = new javax.swing.JLabel();
        txtProvincia = new javax.swing.JTextField();
        lblPoblacion = new javax.swing.JLabel();
        txtPoblacion = new javax.swing.JTextField();
        lblIncoterm = new javax.swing.JLabel();
        txtIncoterm = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        txtPais = new javax.swing.JTextField();
        lblTransporte = new javax.swing.JLabel();
        txtTransporte = new javax.swing.JTextField();
        lblSeguro = new javax.swing.JLabel();
        txtSeguro = new javax.swing.JTextField();
        lblSubtotal2 = new javax.swing.JLabel();
        txtSubtotal2 = new javax.swing.JTextField();
        lblTotalIva2 = new javax.swing.JLabel();
        txtTotalIva2 = new javax.swing.JTextField();
        lblDescuento = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnInsertar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblTipo = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlProfoma.setBackground(new java.awt.Color(247, 247, 252));
        pnlProfoma.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(52, 98, 139), 1, true));
        pnlProfoma.setPreferredSize(new java.awt.Dimension(1250, 800));
        pnlProfoma.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlProfomaMouseDragged(evt);
            }
        });
        pnlProfoma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlProfomaMousePressed(evt);
            }
        });
        pnlProfoma.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(52, 98, 139));
        lblTitulo.setText("Nueva factura proforma");
        pnlProfoma.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        lblFecha.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(102, 102, 102));
        lblFecha.setText("Fecha");
        pnlProfoma.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFecha.setForeground(new java.awt.Color(0, 0, 0));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtFecha.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 120, -1));

        lblNumero.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblNumero.setForeground(new java.awt.Color(102, 102, 102));
        lblNumero.setText("Número");
        pnlProfoma.add(lblNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 80, -1, -1));

        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        txtNumero.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNumero.setForeground(new java.awt.Color(0, 0, 0));
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtNumero.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 100, 120, -1));

        lblCliente.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblCliente.setForeground(new java.awt.Color(102, 102, 102));
        lblCliente.setText("Cliente");
        pnlProfoma.add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 80, -1, -1));

        cbCliente.setBackground(new java.awt.Color(255, 255, 255));
        cbCliente.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cbCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar cliente:", "Item 2", "Item 3", "Item 4" }));
        cbCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbCliente.setPreferredSize(new java.awt.Dimension(400, 27));
        cbCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbClienteKeyReleased(evt);
            }
        });
        pnlProfoma.add(cbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 100, -1, -1));

        lblNif.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblNif.setForeground(new java.awt.Color(102, 102, 102));
        lblNif.setText("NIF del cliente");
        pnlProfoma.add(lblNif, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, -1, -1));

        txtNif.setBackground(new java.awt.Color(255, 255, 255));
        txtNif.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNif.setForeground(new java.awt.Color(0, 0, 0));
        txtNif.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtNif.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtNif, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, 147, -1));

        lblEstado.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(102, 102, 102));
        lblEstado.setText("Estado");
        pnlProfoma.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(886, 80, -1, -1));

        cbEstado.setBackground(new java.awt.Color(255, 255, 255));
        cbEstado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar estado:", "Pedido en curso", "Pedido realizado", "Pedido cancelado" }));
        cbEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbEstado.setPreferredSize(new java.awt.Dimension(200, 27));
        pnlProfoma.add(cbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(886, 100, -1, -1));

        pnlTabla.setBackground(new java.awt.Color(247, 247, 252));
        pnlTabla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(247, 247, 252));
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1055, 170));

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
        tblProductos.setPreferredSize(new java.awt.Dimension(1055, 170));
        tblProductos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProductos);

        pnlTabla.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1055, 170));

        pnlProfoma.add(pnlTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 1075, 190));

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
        pnlProfoma.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(983, 720, 109, 42));

        btnCancelar.setBackground(new java.awt.Color(255, 124, 128));
        btnCancelar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Salir");
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
        pnlProfoma.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1112, 720, 109, 42));

        lblKilos.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblKilos.setForeground(new java.awt.Color(102, 102, 102));
        lblKilos.setText("Total kilos/Uds.");
        pnlProfoma.add(lblKilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, -1, -1));

        txtKilos.setBackground(new java.awt.Color(255, 255, 255));
        txtKilos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtKilos.setForeground(new java.awt.Color(0, 0, 0));
        txtKilos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtKilos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtKilos.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtKilos, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 335, 120, -1));

        lblSubtotal.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblSubtotal.setForeground(new java.awt.Color(102, 102, 102));
        lblSubtotal.setText("Subtotal");
        pnlProfoma.add(lblSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(626, 340, -1, -1));

        txtSubtotal.setBackground(new java.awt.Color(255, 255, 255));
        txtSubtotal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSubtotal.setForeground(new java.awt.Color(0, 0, 0));
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubtotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtSubtotal.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(685, 335, 120, -1));

        lblTotalIva.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblTotalIva.setForeground(new java.awt.Color(102, 102, 102));
        lblTotalIva.setText("Total IVA");
        pnlProfoma.add(lblTotalIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 340, -1, -1));

        txtTotalIva.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalIva.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTotalIva.setForeground(new java.awt.Color(0, 0, 0));
        txtTotalIva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtTotalIva.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtTotalIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(968, 335, 120, -1));

        lblObservaciones.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblObservaciones.setForeground(new java.awt.Color(102, 102, 102));
        lblObservaciones.setText("Observaciones");
        pnlProfoma.add(lblObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        txaObservaciones.setBackground(new java.awt.Color(255, 255, 255));
        txaObservaciones.setColumns(20);
        txaObservaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txaObservaciones.setLineWrap(true);
        txaObservaciones.setRows(5);
        txaObservaciones.setWrapStyleWord(true);
        txaObservaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        jScrollPane2.setViewportView(txaObservaciones);

        pnlProfoma.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 399, 370, 152));

        lblCondiciones.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblCondiciones.setForeground(new java.awt.Color(102, 102, 102));
        lblCondiciones.setText("Condiciones de pago");
        pnlProfoma.add(lblCondiciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 380, -1, -1));

        txtCondiciones.setBackground(new java.awt.Color(255, 255, 255));
        txtCondiciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCondiciones.setForeground(new java.awt.Color(0, 0, 0));
        txtCondiciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtCondiciones.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtCondiciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 400, 440, -1));

        lblValidez.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblValidez.setForeground(new java.awt.Color(102, 102, 102));
        lblValidez.setText("Validez");
        pnlProfoma.add(lblValidez, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 380, -1, -1));

        txtValidez.setBackground(new java.awt.Color(255, 255, 255));
        txtValidez.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtValidez.setForeground(new java.awt.Color(0, 0, 0));
        txtValidez.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValidez.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtValidez.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtValidez, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 400, 186, -1));

        lblDireccion.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblDireccion.setForeground(new java.awt.Color(102, 102, 102));
        lblDireccion.setText("Dirección");
        pnlProfoma.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 440, -1, -1));

        txtDireccion.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(0, 0, 0));
        txtDireccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtDireccion.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 460, 656, -1));

        lblCpostal.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblCpostal.setForeground(new java.awt.Color(102, 102, 102));
        lblCpostal.setText("Código postal");
        pnlProfoma.add(lblCpostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 504, -1, -1));

        txtCpostal.setBackground(new java.awt.Color(255, 255, 255));
        txtCpostal.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCpostal.setForeground(new java.awt.Color(0, 0, 0));
        txtCpostal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCpostal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtCpostal.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtCpostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 524, 100, -1));

        lblProvincia.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblProvincia.setForeground(new java.awt.Color(102, 102, 102));
        lblProvincia.setText("Provincia");
        pnlProfoma.add(lblProvincia, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 504, -1, -1));

        txtProvincia.setBackground(new java.awt.Color(255, 255, 255));
        txtProvincia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProvincia.setForeground(new java.awt.Color(0, 0, 0));
        txtProvincia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtProvincia.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtProvincia, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 524, 220, -1));

        lblPoblacion.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblPoblacion.setForeground(new java.awt.Color(102, 102, 102));
        lblPoblacion.setText("Población");
        pnlProfoma.add(lblPoblacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 504, -1, -1));

        txtPoblacion.setBackground(new java.awt.Color(255, 255, 255));
        txtPoblacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPoblacion.setForeground(new java.awt.Color(0, 0, 0));
        txtPoblacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtPoblacion.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtPoblacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 524, 275, -1));

        lblIncoterm.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblIncoterm.setForeground(new java.awt.Color(102, 102, 102));
        lblIncoterm.setText("Incoterm");
        pnlProfoma.add(lblIncoterm, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 567, -1, -1));

        txtIncoterm.setBackground(new java.awt.Color(255, 255, 255));
        txtIncoterm.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtIncoterm.setForeground(new java.awt.Color(0, 0, 0));
        txtIncoterm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtIncoterm.setPreferredSize(new java.awt.Dimension(64, 27));
        txtIncoterm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIncotermKeyPressed(evt);
            }
        });
        pnlProfoma.add(txtIncoterm, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 585, 220, -1));

        lblPais.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblPais.setForeground(new java.awt.Color(102, 102, 102));
        lblPais.setText("País");
        pnlProfoma.add(lblPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 567, -1, -1));

        txtPais.setBackground(new java.awt.Color(255, 255, 255));
        txtPais.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPais.setForeground(new java.awt.Color(0, 0, 0));
        txtPais.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtPais.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 585, 220, -1));

        lblTransporte.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblTransporte.setForeground(new java.awt.Color(102, 102, 102));
        lblTransporte.setText("Precio del transporte");
        pnlProfoma.add(lblTransporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 567, -1, -1));

        txtTransporte.setBackground(new java.awt.Color(255, 255, 255));
        txtTransporte.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTransporte.setForeground(new java.awt.Color(204, 204, 204));
        txtTransporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTransporte.setText("0,00 €");
        txtTransporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtTransporte.setPreferredSize(new java.awt.Dimension(64, 27));
        txtTransporte.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTransporteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTransporteFocusLost(evt);
            }
        });
        txtTransporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTransporteKeyPressed(evt);
            }
        });
        pnlProfoma.add(txtTransporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 585, 125, -1));

        lblSeguro.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblSeguro.setForeground(new java.awt.Color(102, 102, 102));
        lblSeguro.setText("Precio del seguro");
        pnlProfoma.add(lblSeguro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 567, -1, -1));

        txtSeguro.setBackground(new java.awt.Color(255, 255, 255));
        txtSeguro.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSeguro.setForeground(new java.awt.Color(204, 204, 204));
        txtSeguro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSeguro.setText("0,00 €");
        txtSeguro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtSeguro.setPreferredSize(new java.awt.Dimension(64, 27));
        txtSeguro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSeguroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSeguroFocusLost(evt);
            }
        });
        txtSeguro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSeguroKeyPressed(evt);
            }
        });
        pnlProfoma.add(txtSeguro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 585, 125, -1));

        lblSubtotal2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblSubtotal2.setForeground(new java.awt.Color(102, 102, 102));
        lblSubtotal2.setText("Subtotal");
        pnlProfoma.add(lblSubtotal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 650, -1, -1));

        txtSubtotal2.setBackground(new java.awt.Color(255, 255, 255));
        txtSubtotal2.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        txtSubtotal2.setForeground(new java.awt.Color(0, 0, 0));
        txtSubtotal2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtSubtotal2.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtSubtotal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 673, 170, -1));

        lblTotalIva2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblTotalIva2.setForeground(new java.awt.Color(102, 102, 102));
        lblTotalIva2.setText("Total IVA");
        pnlProfoma.add(lblTotalIva2, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 650, -1, -1));

        txtTotalIva2.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalIva2.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        txtTotalIva2.setForeground(new java.awt.Color(0, 0, 0));
        txtTotalIva2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalIva2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtTotalIva2.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtTotalIva2, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 673, 170, -1));

        lblDescuento.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblDescuento.setForeground(new java.awt.Color(102, 102, 102));
        lblDescuento.setText("Descuento");
        pnlProfoma.add(lblDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 650, -1, -1));

        txtDescuento.setBackground(new java.awt.Color(255, 255, 255));
        txtDescuento.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        txtDescuento.setForeground(new java.awt.Color(204, 204, 204));
        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.setText("0,00 €");
        txtDescuento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtDescuento.setPreferredSize(new java.awt.Dimension(64, 27));
        txtDescuento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescuentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescuentoFocusLost(evt);
            }
        });
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyPressed(evt);
            }
        });
        pnlProfoma.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 673, 170, -1));

        lblTotal.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(102, 102, 102));
        lblTotal.setText("TOTAL");
        pnlProfoma.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 650, -1, -1));

        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(0, 0, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtTotal.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 673, 170, -1));

        btnInsertar.setBackground(new java.awt.Color(106, 141, 162));
        btnInsertar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnInsertar.setForeground(new java.awt.Color(255, 255, 255));
        btnInsertar.setText("Insertar");
        btnInsertar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 98, 139), 3));
        btnInsertar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInsertar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInsertarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInsertarMouseExited(evt);
            }
        });
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });
        pnlProfoma.add(btnInsertar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 140, 109, 42));

        btnEliminar.setBackground(new java.awt.Color(255, 124, 128));
        btnEliminar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 102), 3));
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        pnlProfoma.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 200, 109, 42));

        lblTipo.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(102, 102, 102));
        lblTipo.setText("Tipo de precio");
        pnlProfoma.add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 567, -1, -1));

        txtTipo.setBackground(new java.awt.Color(255, 255, 255));
        txtTipo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTipo.setForeground(new java.awt.Color(0, 0, 0));
        txtTipo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTipo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtTipo.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlProfoma.add(txtTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 585, 100, -1));

        getContentPane().add(pnlProfoma, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Método que cambia el color de fondo del botón "Crear" cuando el cursor
     * entra en él.
     *
     * @param evt Evento del mouse
     */
    private void btnCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseEntered
        btnCrear.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnCrearMouseEntered
    /**
     * Método que restaura el color de fondo del botón "Crear" cuando el cursor
     * sale de él.
     *
     * @param evt Evento del mouse
     */
    private void btnCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseExited
        btnCrear.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnCrearMouseExited
    /**
     * Método que se activa al hacer clic en el botón "Crear" o "Actualizar"
     * dependiendo de su estado.
     *
     * @param evt Evento de acción
     */
    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        if (btnCrear.getText().equals("Crear")) {
            crearProforma();
        } else if (btnCrear.getText().equals("Actualizar")) {
            actualizarProforma();
        }
    }//GEN-LAST:event_btnCrearActionPerformed

    /**
     * Método para limpiar la tabla de productos.
     */
    private void limpiarTablaProductos() {
        DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
        model.setRowCount(0); // Eliminar todas las filas de la tabla
    } // Cierre del método.

    /**
     * Validación de campos vacíos y creación provisional de la proforma antes
     * de insertar los productos o servicios.
     *
     * @throws HeadlessException Excepción lanzada si se realiza una operación
     * gráfica en un entorno sin pantalla
     * @throws NumberFormatException Excepción lanzada si se produce un error al
     * convertir una cadena en un número
     */
    private void crearProvisionalEnBD() throws HeadlessException, NumberFormatException {
        Proforma proforma = new Proforma();
        Ctrl_Proforma controlProforma = new Ctrl_Proforma();

        if (txtFecha.getText().isEmpty() || cbCliente.getSelectedItem().equals("Seleccionar cliente:") || cbEstado.getSelectedItem().equals("Seleccionar estado:")) {
            JOptionPane.showMessageDialog(null, "Debe completar la fecha, el nombre del cliente y el estado de la factura proforma.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));

        } else {

            // Comprobar si la proforma ya existe por número
            String numProforma = txtNumero.getText().trim();
            Date fechaDate = null;
            Date validezDate = null;

            String fechaString = (txtFecha.getText());
            String[] fechaSplit = ServicioProducto.convertirFechaArrayInt(fechaString);
            String validezString = (txtValidez.getText());
            String[] fechaValidezSplit = ServicioProducto.convertirFechaArrayInt(validezString);

            try {
                fechaDate = Date.valueOf(LocalDate.of(Integer.parseInt(fechaSplit[0]), Integer.parseInt(fechaSplit[1]), Integer.parseInt(fechaSplit[2])));
                validezDate = Date.valueOf(LocalDate.of(Integer.parseInt(fechaValidezSplit[0]), Integer.parseInt(fechaValidezSplit[1]), Integer.parseInt(fechaValidezSplit[2])));

            } catch (NullPointerException e) {
                fechaDate = new Date(System.currentTimeMillis());
                validezDate = new Date(System.currentTimeMillis());
                JOptionPane.showMessageDialog(null, "Error en la fecha: " + e,
                        "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }

            double kilosDouble = Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtKilos.getText()));

            if (!controlProforma.existeProforma(numProforma)) {

                proforma.setFecha(fechaDate);
                proforma.setNumero(txtNumero.getText().trim());
                proforma.setNombre_cliente(cbCliente.getSelectedItem().toString());
                proforma.setNif(txtNif.getText().trim());
                proforma.setKilos(kilosDouble);
                proforma.setSuma_subtotal(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSubtotal.getText())));
                proforma.setSuma_iva(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTotalIva.getText())));
                proforma.setCondiciones_pago(txtCondiciones.getText().trim());
                proforma.setValidez(validezDate);
                proforma.setDireccion(txtDireccion.getText().trim());
                proforma.setPoblacion(txtPoblacion.getText().trim());
                proforma.setC_postal(txtCpostal.getText().trim());
                proforma.setProvincia(txtProvincia.getText().trim());
                proforma.setIncoterm(txtIncoterm.getText().trim());
                proforma.setPais(txtPais.getText().trim());
                proforma.setTipo_precio(txtTipo.getText());
                if (null != txtTransporte.getText() && !txtTransporte.getText().isBlank()) {
                    proforma.setTransporte(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTransporte.getText())));
                }
                if (null != txtSeguro.getText() && !txtSeguro.getText().isBlank()) {
                    proforma.setSeguro(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSeguro.getText())));
                }
                if (null != txtDescuento.getText() && !txtDescuento.getText().isBlank()) {
                    proforma.setDescuento(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtDescuento.getText())));
                }
                proforma.setTotal(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTotal.getText())));
                proforma.setObservaciones(txaObservaciones.getText());
                proforma.setEstado(cbEstado.getSelectedItem().toString());

                // Actualizar el idCliente en el objeto Proforma
                proforma.setIdCliente(idCliente);

                if (controlProforma.crear(proforma)) {

                    this.ifProforma.recargarTabla();

                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear la proforma.",
                            "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                    this.Limpiar();
                }

            }
        }
    } // Cierre del método.

    /**
     * Método que crea la proforma definitiva después de insertar los productos
     * o servicios.
     *
     */
    private void crearProforma() {
        Proforma proforma = new Proforma();

        Ctrl_Proforma controlProforma = new Ctrl_Proforma();

        proforma.setIdProforma(idProforma);
        if (null != txtTransporte.getText() && !txtTransporte.getText().isBlank()) {
            proforma.setTransporte(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTransporte.getText())));
        }
        if (null != txtSeguro.getText() && !txtSeguro.getText().isBlank()) {
            proforma.setSeguro(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSeguro.getText())));
        }
        if (null != txtDescuento.getText() && !txtDescuento.getText().isBlank()) {
            proforma.setDescuento(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtDescuento.getText())));
        }
        proforma.setIncoterm(txtIncoterm.getText());
        proforma.setObservaciones(txaObservaciones.getText());
        proforma.setKilos(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtKilos.getText())));
        proforma.setSuma_subtotal(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSubtotal2.getText())));
        proforma.setSuma_iva(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTotalIva2.getText())));
        proforma.setTotal(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTotal.getText())));
        proforma.setEstado(cbEstado.getSelectedItem().toString());

        // Actualizar la información de la proforma en la base de datos
        if (controlProforma.actualizarProforma(proforma)) {
            // Incrementar el contador de número de proforma
            numeroProforma++;

            // Actualizar la interfaz de usuario con el nuevo número de proforma
            txtNumero.setText(generarNumeroProforma());

            // Obtener el próximo idProforma disponible
            idProforma = ctrlProforma.obtenerProximoIdProforma();
            proforma.setIdProforma(ctrlProforma.obtenerProximoIdProforma());

            JOptionPane.showMessageDialog(null, "Proforma creada correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
            Limpiar(); // Limpiar los campos después de actualizar la proforma

            // Limpiar la tabla de productos
            limpiarTablaProductos();

            btnInsertar.setText("Insertar");

            // Recargar tabla
            this.ifProforma.recargarTabla();

        } else {
            JOptionPane.showMessageDialog(null, "Error al crear la proforma.", "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
            Limpiar(); // Limpiar los campos en caso de error
        }
    } // Cierre del método.

    /**
     *
     * Método que actualiza la proforma después de insertar más productos o
     * servicios o de modificarla.
     */
    private void actualizarProforma() {
        Proforma proforma = new Proforma();

        Ctrl_Proforma controlProforma = new Ctrl_Proforma();

        proforma.setIdProforma(idProforma);
        if (null != txtTransporte.getText() && !txtTransporte.getText().isBlank()) {
            proforma.setTransporte(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTransporte.getText())));
        }
        if (null != txtSeguro.getText() && !txtSeguro.getText().isBlank()) {
            proforma.setSeguro(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSeguro.getText())));
        }
        if (null != txtDescuento.getText() && !txtDescuento.getText().isBlank()) {
            proforma.setDescuento(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtDescuento.getText())));
        }
        proforma.setIncoterm(txtIncoterm.getText());
        proforma.setObservaciones(txaObservaciones.getText());
        proforma.setKilos(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtKilos.getText())));
        proforma.setSuma_subtotal(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSubtotal2.getText())));
        proforma.setSuma_iva(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTotalIva2.getText())));
        proforma.setTotal(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTotal.getText())));
        proforma.setEstado(cbEstado.getSelectedItem().toString());

        // Actualizar la información de la proforma en la base de datos
        if (controlProforma.actualizarProforma(proforma)) {

            JOptionPane.showMessageDialog(null, "Proforma actualizada correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
            dispose(); // Cerrar el diálogo después de actualizar.

            // Recargar tabla
            this.ifProforma.recargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la proforma.", "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
            Limpiar(); // Limpiar los campos en caso de error
        }
    } // Cierre del método.

    /**
     * Cambia el color de fondo del botón Cancelar cuando el cursor entra en él.
     *
     * @param evt Evento del mouse.
     */
    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnCancelarMouseEntered
    /**
     * Restaura el color de fondo del botón Cancelar cuando el cursor sale de
     * él.
     *
     * @param evt Evento del mouse.
     */
    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnCancelarMouseExited
    /**
     * Cierra la ventana actual y recarga la tabla de proformas.
     *
     * @param evt Evento del botón.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
        this.ifProforma.recargarTabla();
    }//GEN-LAST:event_btnCancelarActionPerformed
    /**
     * Cambia el color de fondo del botón Insertar cuando el cursor entra en él.
     *
     * @param evt Evento del mouse.
     */
    private void btnInsertarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInsertarMouseEntered
        btnInsertar.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnInsertarMouseEntered
    /**
     * Restaura el color de fondo del botón Insertar cuando el cursor sale de
     * él.
     *
     * @param evt Evento del mouse.
     */
    private void btnInsertarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInsertarMouseExited
        btnInsertar.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnInsertarMouseExited
    /**
     * Cambia el color de fondo del botón Eliminar cuando el cursor entra en él.
     *
     * @param evt Evento del mouse.
     */
    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        btnEliminar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnEliminarMouseEntered
    /**
     * Restaura el color de fondo del botón Eliminar cuando el cursor sale de
     * él.
     *
     * @param evt Evento del mouse.
     */
    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        btnEliminar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnEliminarMouseExited

    /**
     * Método que registra la posición del cursor del mouse en el panel de
     * encabezado.
     *
     * @param evt Evento del mouse.
     */
    private void pnlProfomaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlProfomaMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_pnlProfomaMousePressed
    /**
     * Método que arrastra la ventana a la nueva posición según el movimiento
     * del mouse.
     *
     * @param evt Evento del mouse.
     */
    private void pnlProfomaMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlProfomaMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_pnlProfomaMouseDragged
    /**
     * Acción al hacer clic en el botón Insertar.
     *
     * @param evt Evento del botón.
     */
    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        insertarProductos();
    }//GEN-LAST:event_btnInsertarActionPerformed
    /**
     * Acción al soltar una tecla mientras el foco está en el campo de texto de
     * Cliente.
     *
     * @param evt Evento del teclado.
     */
    private void cbClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbClienteKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSeguro.requestFocus();
        }
    }//GEN-LAST:event_cbClienteKeyReleased
    /**
     * Acción al hacer clic en el botón Eliminar.
     *
     * @param evt Evento del botón.
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarProductos();
    }//GEN-LAST:event_btnEliminarActionPerformed
    /**
     * Acción al presionar una tecla mientras el foco está en el campo de texto
     * de Transporte.
     *
     * @param evt Evento del teclado.
     */
    private void txtTransporteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTransporteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSeguro.requestFocus();
        }
    }//GEN-LAST:event_txtTransporteKeyPressed
    /**
     * Acción al presionar una tecla mientras el foco está en el campo de texto
     * de Seguro.
     *
     * @param evt Evento del teclado.
     */
    private void txtSeguroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSeguroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDescuento.requestFocus();
        }
    }//GEN-LAST:event_txtSeguroKeyPressed
    /**
     * Acción al presionar una tecla mientras el foco está en el campo de texto
     * de Descuento.
     *
     * @param evt Evento del teclado.
     */
    private void txtDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtIncoterm.requestFocus();
        }
    }//GEN-LAST:event_txtDescuentoKeyPressed
    /**
     * Acción al presionar una tecla mientras el foco está en el campo de texto
     * de Incoterm.
     *
     * @param evt Evento del teclado.
     */
    private void txtIncotermKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIncotermKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnCrear.requestFocus();
        }
    }//GEN-LAST:event_txtIncotermKeyPressed
    /**
     * Acción al presionar una tecla mientras el foco está en el botón Crear.
     *
     * @param evt Evento del teclado.
     */
    private void btnCrearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCrearKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnCrear.getText().equals("Crear")) {
                crearProforma();
            } else if (btnCrear.getText().equals("Actualizar")) {
                actualizarProforma();
            }
        }
    }//GEN-LAST:event_btnCrearKeyPressed
    /**
     * Acción al obtener el foco en el campo de texto de Transporte.
     *
     * @param evt Evento del foco.
     */
    private void txtTransporteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTransporteFocusGained
        focoTransporte();
    }//GEN-LAST:event_txtTransporteFocusGained
    /**
     * Acción al perder el foco en el campo de texto de Transporte.
     *
     * @param evt Evento del foco.
     */
    private void txtTransporteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTransporteFocusLost
        focoSeguro();
    }//GEN-LAST:event_txtTransporteFocusLost
    /**
     * Acción al obtener el foco en el campo de texto de Seguro.
     *
     * @param evt Evento del foco.
     */
    private void txtSeguroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSeguroFocusGained
        focoSeguro();
    }//GEN-LAST:event_txtSeguroFocusGained
    /**
     * Acción al perder el foco en el campo de texto de Seguro.
     *
     * @param evt Evento del foco.
     */
    private void txtSeguroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSeguroFocusLost
        focoDescuento();
    }//GEN-LAST:event_txtSeguroFocusLost
    /**
     * Acción al obtener el foco en el campo de texto de Descuento.
     *
     * @param evt Evento del foco.
     */
    private void txtDescuentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescuentoFocusGained
        focoDescuento();
    }//GEN-LAST:event_txtDescuentoFocusGained
    /**
     * Acción al perder el foco en el campo de texto de Descuento.
     *
     * @param evt Evento del foco.
     */
    private void txtDescuentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescuentoFocusLost
        focoIncoterm();
    }//GEN-LAST:event_txtDescuentoFocusLost

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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgProformas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgProformas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgProformas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgProformas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgProformas dialog = new DlgProformas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JComboBox<String> cbCliente;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCondiciones;
    private javax.swing.JLabel lblCpostal;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblIncoterm;
    private javax.swing.JLabel lblKilos;
    private javax.swing.JLabel lblNif;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblPoblacion;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JLabel lblSeguro;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblSubtotal2;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalIva;
    private javax.swing.JLabel lblTotalIva2;
    private javax.swing.JLabel lblTransporte;
    private javax.swing.JLabel lblValidez;
    private javax.swing.JPanel pnlProfoma;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JTextField txtCondiciones;
    private javax.swing.JTextField txtCpostal;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIncoterm;
    private javax.swing.JTextField txtKilos;
    private javax.swing.JTextField txtNif;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtPoblacion;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JTextField txtSeguro;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtSubtotal2;
    private javax.swing.JTextField txtTipo;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalIva;
    private javax.swing.JTextField txtTotalIva2;
    private javax.swing.JTextField txtTransporte;
    private javax.swing.JTextField txtValidez;
    // End of variables declaration//GEN-END:variables

    /**
     * Método que valida los campos requeridos y abre el diálogo para la
     * inserción de productos o servicios.
     */
    private void insertarProductos() {
        if (txtFecha.getText().isEmpty() || cbCliente.getSelectedItem().equals("Seleccionar cliente:") || cbEstado.getSelectedItem().equals("Seleccionar estado:")) {
            JOptionPane.showMessageDialog(null, "Antes de insertar valores debe completar la fecha, el nombre \ndel cliente y el estado de la factura proforma.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        } else {

            crearProvisionalEnBD();

            String tipo = txtTipo != null ? txtTipo.getText() : "";

            Frame f = JOptionPane.getFrameForComponent(this);
            dlgProductosProforma = new DlgProductosProforma(f, true);
            dlgProductosProforma.setIdProforma(idProforma);
            dlgProductosProforma.setTipoPrecio(tipo);
            dlgProductosProforma.setDlgProformas(this);
            dlgProductosProforma.setVisible(true);
            esServicio = dlgProductosProforma.isEsServicioSeleccionado();

            // Agregar el WindowListener después de inicializar dlgProductosProforma
            dlgProductosProforma.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Cuando se cierra el diálogo DlgProductosProforma, actualiza el texto del botón btnInsertar
                    btnInsertar.setText("Añadir más");
                }
            });

            if (btnInsertar.getText().equals("Añadir más")) {

                calcularTotales();

            }
        }
    } // Cierre del método.

    /**
     * Método que elimina los productos o servicios seleccionados de la tabla.
     */
    private void eliminarProductos() {
        Ctrl_ProductosProforma controlProforma = new Ctrl_ProductosProforma();
        //Obtener la fila seleccionada.
        int filaSeleccionada = tblProductos.getSelectedRow();
        try {
            if (filaSeleccionada != -1) {
                idProducto = idProductoPorFila.get(filaSeleccionada);
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar el producto seleccionado?", "ATENCIÓN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icono("/img/pregunta.png", 40, 40));
                if (respuesta == JOptionPane.YES_OPTION) {
                    if (controlProforma.eliminar(idProducto)) {

                        this.recargarTabla();

                        calcularTotales();

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
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
    } // Cierre del método.

    /**
     * Método para cargar los clientes en el combo box de clientes.
     */
    private void CargarComboClientes() {
        Connection cn = Conexion.conectar();
        String sql = "SELECT * FROM tb_clientes";
        Statement st;

        try {
            st = (Statement) cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cbCliente.removeAllItems();
            cbCliente.addItem("Seleccionar cliente: ");

            while (rs.next()) {
                cbCliente.addItem(rs.getString("nombre"));

            }

        } catch (SQLException ex) {
            System.out.println("Error al cargar los clientes." + ex);
        } finally {

            if (cn != null) {

                try {

                    cn.close();

                } catch (SQLException ex) {

                    System.out.println("Error al cerrar la conexión: " + ex);

                }
            }
        }
    } // Cierre del método.

    /**
     * Método para cargar los datos del cliente seleccionado en el combo box.
     */
    private void datosClienteCombo() {
        // Agrega un ActionListener al JComboBox cbCliente
        cbCliente.addActionListener((ActionEvent e) -> {
            // Verifica si se ha seleccionado un cliente válido
            if (cbCliente.getSelectedIndex() > 0) {
                // Obtengo el nombre del cliente seleccionado del JComboBox
                String nombreCliente = (String) cbCliente.getSelectedItem();

                // Realiza una consulta a la base de datos para obtener la información del cliente
                Connection cn = Conexion.conectar();
                String sql = "SELECT * FROM tb_clientes WHERE nombre = '" + nombreCliente + "'";
                Statement st = null;
                ResultSet rs = null;

                try {
                    st = cn.createStatement();

                    rs = st.executeQuery(sql);

                    // Si se encuentra el cliente, actualiza los campos de texto en el JDialog
                    if (rs.next()) {

                        idCliente = rs.getInt("idCliente"); // Obtiene el idCliente

                        txtNif.setText(rs.getString("nif"));
                        txtCondiciones.setText(rs.getString("condiciones_pago"));
                        txtDireccion.setText(rs.getString("direccion"));
                        txtCpostal.setText(rs.getString("c_postal"));
                        txtProvincia.setText(rs.getString("provincia"));
                        txtPoblacion.setText(rs.getString("poblacion"));
                        txtPais.setText(rs.getString("pais"));
                        txtTipo.setText(rs.getString("tipo_precio"));

                    }
                } catch (SQLException ex) {
                    System.out.println("Error al obtener la información del cliente: " + ex);
                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                        if (st != null) {
                            st.close();
                        }
                        if (cn != null) {
                            cn.close();
                        }
                    } catch (SQLException ex) {
                        System.out.println("Error al cerrar la conexión: " + ex);
                    }
                }
            }
        });
    } // Cierre del método.

    /**
     * Método para crear automáticamente el número de la proforma.
     *
     * @return El número de la proforma.
     */
    public static String generarNumeroProforma() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int anioActual = calendar.get(Calendar.YEAR);

        Ctrl_Proforma controlProforma = new Ctrl_Proforma();

        // Reiniciar el contador si es un nuevo año
        if (anioActual != ultimoAnio) {
            numeroProforma = 1;
            ultimoAnio = anioActual;
        }

        // Construir el número de proforma
        String numero;
        boolean existeProforma;

        // Bucle para generar un número de proforma único
        do {
            numero = String.format("PF%02d-%d", numeroProforma, anioActual);
            existeProforma = controlProforma.existeProforma(numero);
            if (existeProforma) {
                numeroProforma++;
            }
        } while (existeProforma);

        return numero;
    }  // Cierre del método.

    /**
     * Método para validar el formato de las fechas.
     */
    private boolean validarFormatoFecha(String textoFecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        formatoFecha.setLenient(false); // Desactiva el modo permisivo para que el formato sea estricto

        try {
            formatoFecha.parse(textoFecha);
            return true; // El formato es válido
        } catch (ParseException e) {
            return false; // El formato es inválido
        }
    } // Cierre del método.

    /**
     * Método para dar formato a las fechas.
     */
    private void formatearFecha() {
        // Obtén el texto del JTextField
        String textoFecha = txtFecha.getText();
        String textoValidez = txtValidez.getText();

        // Elimina los espacios en blanco adicionales al principio y al final del texto
        String textoFechaTrimmed = textoFecha.trim();
        String textoValidezTrimmed = textoValidez.trim();

        // Crea un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");

        // Verifica si el formato de la fecha es válido antes de intentar formatearlo
        if (!validarFormatoFecha(textoFecha) || !validarFormatoFecha(textoValidez)) {
            // Muestra un mensaje de error si el formato es inválido
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido, debe ser aaaa-mm-dd.",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            return; // Sale del método sin intentar formatear las fechas
        }

        try {
            // Analiza el texto como una fecha
            java.util.Date fecha = formatoEntrada.parse(textoFechaTrimmed);
            java.util.Date validez = formatoEntrada.parse(textoValidezTrimmed);

            // Formatea la fecha nuevamente en el mismo formato
            String fechaFormateada = formatoSalida.format(fecha);
            String validezFormateada = formatoSalida.format(validez);

            // Establece el texto formateado en el JTextField
            txtFecha.setText(fechaFormateada);
            txtValidez.setText(validezFormateada);
        } catch (ParseException e) {
            // Maneja la excepción si el texto no puede ser analizado como una fecha
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido, debe ser aaaa-mm-dd.",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            txtFecha.setText("");
            txtValidez.setText("");
        }

    } // Cierre del método.

    /**
     * Método para establecer el foco a las fechas.
     */
    private void focoFechas() {
        txtFecha.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                formatearFecha();
            }
        });
        txtValidez.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                formatearFecha();
            }
        });
    } // Cierre del método.

    /**
     * Método para establecer la fecha actual por defecto en el campo fecha y 30
     * días de validez.
     */
    private void setearFechaPorDefecto() {
        // Obtén la fecha actual
        java.util.Date fechaActual = new Date(System.currentTimeMillis());

        // Crea un objeto Calendar y establece la fecha actual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);

        // Agrega 30 días a la fecha de inicio
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        // Crea un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");

        // Formatea la fecha actual en el formato deseado
        String fechaFormateada = formatoSalida.format(fechaActual);

        String fechaValidezFormateada = formatoSalida.format(calendar.getTime());

        // Establece la fecha formateada como texto en el JTextField
        txtFecha.setText(fechaFormateada);
        txtValidez.setText(fechaValidezFormateada);
    } // Cierre del método.

    /**
     * Método que muestra los datos seleccionados.
     *
     * @param idProforma El ID de la proforma seleccionada.
     * @param datosFila Los datos de la proforma seleccionada.
     */
    public void mostrarDatos(int idProforma, Object[] datosFila) {

        lblTitulo.setText("Editar factura proforma");
        btnCrear.setText("Actualizar");
        btnInsertar.setText("Añadir más");
        cbCliente.setEnabled(false);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        symbols.setCurrencySymbol("€");
        decimalFormat = new DecimalFormat("#,##0.00", symbols);

        double transporte = (Double) datosFila[4];
        double seguro = (Double) datosFila[5];
        double total = (Double) datosFila[7];

        // Conversión de la fecha de java.sql.Date a String
        java.sql.Date fecha = (java.sql.Date) datosFila[0];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaStr = sdf.format(fecha);
        txtFecha.setText(fechaStr);

        // Conversión de la fecha de validez de java.sql.Date a String
        java.sql.Date fechaValidez = (java.sql.Date) datosFila[2];
        String fechaValidezStr = sdf.format(fechaValidez);
        txtValidez.setText(fechaValidezStr);

        // Conversión de los valores Double a String
        txtNumero.setText((String) datosFila[1]);
        txtTransporte.setText(decimalFormat.format((transporte)) + " €");
        txtSeguro.setText(decimalFormat.format((seguro)) + " €");
        txtKilos.setText(String.valueOf((Double) datosFila[6]));
        txtTotal.setText(decimalFormat.format((total)) + " €");

        // Seleccionar el cliente correspondiente
        String cliente = (String) datosFila[3];

        // Establecer modelo de ComboBox para clientes
        DefaultComboBoxModel<String> modeloCliente = new DefaultComboBoxModel<>();
        modeloCliente.addElement("");
        modeloCliente.addElement(cliente);
        cbCliente.setModel(modeloCliente);
        cbCliente.setSelectedIndex(1);
        datosClienteCombo();

        // Establecer modelo de ComboBox para estado
        DefaultComboBoxModel<String> modeloEstado = new DefaultComboBoxModel<>();
        modeloEstado.addElement("Seleccionar estado:");
        modeloEstado.addElement("Pedido en curso");
        modeloEstado.addElement("Pedido realizado");
        modeloEstado.addElement("Pedido cancelado");
        cbEstado.setModel(modeloEstado);

        // Seleccionar el estado correspondiente
        String estado = (String) datosFila[8];
        cbEstado.setSelectedItem(estado);

        CargarTablaProductos();
        CargarDatosProforma();

        listenersCalculos();

        calcularTotales();

    } // Cierre del método.

    /**
     * Listeners para detectar cambios en transporte, seguro y descuento.
     */
    private void listenersCalculos() {
        // Listener para txtTransporte para detectar cambios en el texto
        txtTransporte.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularSubtotal2();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularSubtotal2();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularSubtotal2();
            }
        });
        // Listener para txtSeguro para detectar cambios en el texto
        txtSeguro.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularSubtotal2();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularSubtotal2();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularSubtotal2();
            }
        });
        // Listener para txtDescuento para detectar cambios en el texto
        txtDescuento.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularTotal();
            }
        });

    } // Cierre de los listeners.

    /**
     * Método para calcular totales.
     */
    public void calcularTotales() {

        // Calcular la suma de los kilos, subtotales e IVA de los productos en la tabla
        double totalKilos = 0.0;
        totalSubtotal = 0.0;
        double totalIva = 0.0;

        for (int i = 0; i < tblProductos.getRowCount(); i++) {
            // Sumar los kilos de la columna 2
            totalKilos += Double.parseDouble(tblProductos.getValueAt(i, 2).toString());

            // Sumar los subtotales de la columna 4
            totalSubtotal += Double.parseDouble(tblProductos.getValueAt(i, 4).toString());

            // Sumar los totales de IVA de la columna 6
            totalIva += Double.parseDouble(tblProductos.getValueAt(i, 6).toString());
        }

        // Actualizar los campos de texto con los resultados calculados
        txtKilos.setText(decimalFormat.format(totalKilos) + " kg");
        txtSubtotal.setText(decimalFormat.format(totalSubtotal) + " €");
        txtTotalIva.setText(decimalFormat.format(totalIva) + " €");

        // Calcular subtotal total
        subtotalTotal = totalSubtotal; // Total de subtotales de los productos

        // Agregar transporte, si está presente
        if (null != txtTransporte.getText() && !txtTransporte.getText().isBlank()) {
            subtotalTotal += Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTransporte.getText()));
        }

        // Agregar seguro, si está presente
        if (null != txtSeguro.getText() && !txtSeguro.getText().isBlank()) {
            subtotalTotal += Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSeguro.getText()));
        }

        // Actualizar txtSubtotal2 con el resultado
        txtSubtotal2.setText(decimalFormat.format(subtotalTotal) + " €");

        // Calcular total de IVA
        // Inicializar totalIva2 a 0 cada vez que se llama al método
        totalIva2 = 0.0;

        // Sumar el IVA de cada línea de producto en la tabla
        for (int i = 0; i < tblProductos.getRowCount(); i++) {
            // Obtener el importe de IVA de la columna 6
            double importeIvaProducto = Double.parseDouble(tblProductos.getValueAt(i, 6).toString());
            // Sumar el importe de IVA al totalIva2
            totalIva2 += importeIvaProducto;
        }

        // Calcular IVA del transporte si el país es España y se ha introducido un importe en txtTransporte
        if (!txtPais.getText().isBlank() && txtPais.getText().equals("España") && !txtTransporte.getText().isBlank()) {
            double ivaTransporte = 0.21 * Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTransporte.getText()));
            // Sumar el IVA del transporte al totalIva2
            totalIva2 += ivaTransporte;
        }

        // Actualizar txtTotalIva2 con el resultado
        txtTotalIva2.setText(decimalFormat.format(totalIva2) + " €");

        // Calcular total
        double total = subtotalTotal + totalIva2;

        // Restar el descuento, si está presente
        if (null != txtDescuento.getText() && !txtDescuento.getText().isBlank()) {
            total -= Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtDescuento.getText()));
        }

        // Actualizar txtTotal con el resultado
        txtTotal.setText(decimalFormat.format(total) + " €");

    } // Cierre del método. 

    /**
     * Método para calcular txtSubtotal2.
     */
    private void calcularSubtotal2() {
        try {
            // Calcular subtotal total
            subtotalTotal = totalSubtotal; // Total de subtotales de los productos

            // Agregar transporte, si está presente
            if (!txtTransporte.getText().isBlank()) {
                subtotalTotal += Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTransporte.getText()));
            }

            // Agregar seguro, si está presente
            if (!txtSeguro.getText().isBlank()) {
                subtotalTotal += Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSeguro.getText()));
            }

            // Actualizar txtSubtotal2 con el resultado
            txtSubtotal2.setText(decimalFormat.format(subtotalTotal) + " €");

            // Llamar a calcularTotalIva2 y calcularTotal para actualizar los valores dependientes
            calcularTotalIva2();
            calcularTotal();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido en los campos transporte y seguro.",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
    } // Cierre del método.

    /**
     * Método para calcular txtTotalIva2.
     */
    private void calcularTotalIva2() {
        try {
            // Inicializar totalIva2 a 0 cada vez que se llama al método
            totalIva2 = 0.0;

            // Sumar el IVA de cada línea de producto en la tabla
            for (int i = 0; i < tblProductos.getRowCount(); i++) {
                // Obtener el importe de IVA de la columna 6
                double importeIvaProducto = Double.parseDouble(tblProductos.getValueAt(i, 6).toString());
                // Sumar el importe de IVA al totalIva2
                totalIva2 += importeIvaProducto;
            }

            // Calcular IVA del transporte si el país es España y se ha introducido un importe en txtTransporte
            if (!txtPais.getText().isBlank() && txtPais.getText().equals("España") && !txtTransporte.getText().isBlank()) {
                double ivaTransporte = 0.21 * Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTransporte.getText()));
                // Sumar el IVA del transporte al totalIva2
                totalIva2 += ivaTransporte;
            }

            // Actualizar txtTotalIva2 con el resultado
            txtTotalIva2.setText(decimalFormat.format(totalIva2) + " €");

            // Llamar a calcularTotal para actualizar el valor total
            calcularTotal();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido en el campo transporte.",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));

        }
    } // Cierre del método.

    /**
     * Método para calcular txtTotal.
     */
    private void calcularTotal() {
        try {
            // Calcular subtotal total
            subtotalTotal = totalSubtotal; // Total de subtotales de los productos

            // Agregar transporte, si está presente
            if (!txtTransporte.getText().isBlank()) {
                subtotalTotal += Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTransporte.getText()));
            }

            // Agregar seguro, si está presente
            if (!txtSeguro.getText().isBlank()) {
                subtotalTotal += Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtSeguro.getText()));
            }

            // Calcular total
            double total = subtotalTotal;

            // Agregar total de IVA, si está presente
            if (!txtTotalIva2.getText().isBlank()) {
                total += Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtTotalIva2.getText()));
            }

            // Restar el descuento, si está presente
            if (!txtDescuento.getText().isBlank()) {
                total -= Double.parseDouble(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtDescuento.getText()));
            }

            // Actualizar txtTotal con el resultado
            txtTotal.setText(decimalFormat.format(total) + " €");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido en los campos transporte, seguro y descuento.",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
    } // Cierre del método.

    /**
     * Método para recargar la tabla con todos los productos registrados cuando
     * se añade uno nuevo y se elimina un producto.
     */
    public void recargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos

        CargarTablaProductos();
    } // Cierre del método.

    /**
     * Clase para conectar a la base de datos.
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
     * Método para cargar la tabla con todos los productos registrados.
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

            String sql = "SELECT idProducto, idProforma, codigo_producto, descripcion, cantidad, precio_unitario, tipo_iva, importe_iva, subtotal FROM tb_productos_proforma WHERE idProforma='" + idProforma + "'";

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                listaProductosProforma = new ArrayList<>();

                while (rs.next()) {

                    ProductoProforma productoProforma = ServicioProductoProforma.asignarDatosProducto(rs);
                    listaProductosProforma.add(productoProforma);

                    Object[] datosFila = this.asignarDatosModelo(productoProforma);
                    model.addRow(datosFila);

                    // Obtener el ID del producto de la fila actual
                    idProducto = rs.getInt("idProducto");

                    // Obtener el índice de la fila recién agregada
                    int fila = model.getRowCount() - 1;

                    // Asociar el ID del producto con el índice de fila en el HashMap
                    idProductoPorFila.put(fila, idProducto);

                }

            } catch (SQLException e) {

                System.out.println("Error al llenar la tabla productos: " + e);
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
     * Método para cargar los datos de la proforma que no están en la tabla.
     */
    private void CargarDatosProforma() {
        ConexionBD conexion = new ConexionBD();

        try {
            Class.forName(conexion.driver);
            conexion.con = DriverManager.getConnection(conexion.url, conexion.usuario, conexion.clave);

            String sql = "SELECT idProforma, idCliente, fecha, numero, nombre_cliente, nif, condiciones_pago, validez, direccion, "
                    + "poblacion, c_postal, provincia, incoterm, pais, transporte, seguro, kilos, suma_subtotal, suma_iva, descuento, "
                    + "total, observaciones, estado FROM tb_proformas WHERE idProforma=" + idProforma;

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    // Obtener los valores de cada campo de la tabla
                    String incoterm = rs.getString("incoterm");
                    Double sumaSubtotal = rs.getDouble("suma_subtotal");
                    Double sumaIva = rs.getDouble("suma_iva");
                    Double descuento = rs.getDouble("descuento");
                    String observaciones = rs.getString("observaciones");

                    txtIncoterm.setText(incoterm);
                    txtSubtotal2.setText(decimalFormat.format(sumaSubtotal) + " €");
                    txtTotalIva2.setText(decimalFormat.format(sumaIva) + " €");
                    txtDescuento.setText(decimalFormat.format(descuento) + " €");
                    txaObservaciones.setText(observaciones);

                } else {
                    System.out.println("No se encontró ninguna proforma con el ID proporcionado.");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al cargar los datos de la proforma: " + e);
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
     * Método de foco para el campo transporte.
     */
    private void focoTransporte() {
        if (txtTransporte.getText().equals("0,00 €")) {
            txtTransporte.setText("");
            txtTransporte.setForeground(Color.black);
        }
        if (txtSeguro.getText().isEmpty()) {
            txtSeguro.setText("0,00 €");
            txtSeguro.setForeground(new Color(204, 204, 204));
        }
        if (txtDescuento.getText().isEmpty()) {
            txtDescuento.setText("0,00 €");
            txtDescuento.setForeground(new Color(204, 204, 204));
        }
    } // Cierre del método.

    /**
     * Método de foco para el campo seguro.
     */
    private void focoSeguro() {
        if (txtSeguro.getText().equals("0,00 €")) {
            txtSeguro.setText("");
            txtSeguro.setForeground(Color.black);
        }
        if (txtTransporte.getText().isEmpty()) {
            txtTransporte.setText("0,00 €");
            txtTransporte.setForeground(new Color(204, 204, 204));
        }
        if (txtDescuento.getText().isEmpty()) {
            txtDescuento.setText("0,00 €");
            txtDescuento.setForeground(new Color(204, 204, 204));
        }
    } // Cierre del método.

    /**
     * Método de foco para el campo descuento.
     */
    private void focoDescuento() {
        if (txtDescuento.getText().equals("0,00 €")) {
            txtDescuento.setText("");
            txtDescuento.setForeground(Color.black);
        }
        if (txtTransporte.getText().isEmpty()) {
            txtTransporte.setText("0,00 €");
            txtTransporte.setForeground(new Color(204, 204, 204));
        }
        if (txtSeguro.getText().isEmpty()) {
            txtSeguro.setText("0,00 €");
            txtSeguro.setForeground(new Color(204, 204, 204));
        }
    } // Cierre del método.

    /**
     * Método de foco para el campo incoterm.
     */
    private void focoIncoterm() {
        if (txtIncoterm.getText().equals("")) {
            txtIncoterm.setText("");

        }
        if (txtTransporte.getText().isEmpty()) {
            txtTransporte.setText("0,00 €");
            txtTransporte.setForeground(new Color(204, 204, 204));
        }
        if (txtSeguro.getText().isEmpty()) {
            txtSeguro.setText("0,00 €");
            txtSeguro.setForeground(new Color(204, 204, 204));
        }
        if (txtDescuento.getText().isEmpty()) {
            txtDescuento.setText("0,00 €");
            txtDescuento.setForeground(new Color(204, 204, 204));
        }
    } // Cierre del método.

    /**
     * Método para asignar los datos de los productos registrados al modelo de
     * tabla.
     */
    private Object[] asignarDatosModelo(ProductoProforma productoProforma) {

        Object fila[] = new Object[7];

        fila[0] = productoProforma.getCodigo_producto();
        fila[1] = productoProforma.getDescripcion();
        fila[2] = productoProforma.getCantidad();
        fila[3] = productoProforma.getPrecio_unitario();
        fila[4] = productoProforma.getSubtotal();
        fila[5] = productoProforma.getTipo_iva();
        fila[6] = productoProforma.getImporte_iva();

        return fila;
    } // Cierre del método.

    /**
     * Método que establece los campos que no son editables.
     */
    private void camposNoEditables() {
        // Establecemos estos campos como no editables.
        txtNumero.setEditable(false);
        txtKilos.setEditable(false);
        txtSubtotal.setEditable(false);
        txtTotalIva.setEditable(false);
        txtSubtotal2.setEditable(false);
        txtTotalIva2.setEditable(false);
        txtTotal.setEditable(false);
        txtNif.setEditable(false);
        txtCondiciones.setEditable(false);
        txtDireccion.setEditable(false);
        txtProvincia.setEditable(false);
        txtPoblacion.setEditable(false);
        txtPais.setEditable(false);
        txtTipo.setEditable(false);

    } // Cierre del método.

    /**
     * Método para limpiar los campos.
     */
    private void Limpiar() {

        cbCliente.setSelectedIndex(0);
        txtNif.setText("");
        cbEstado.setSelectedItem("Seleccionar estado:");
        txaObservaciones.setText("");
        txtCondiciones.setText("");
        txtDireccion.setText("");
        txtCpostal.setText("");
        txtProvincia.setText("");
        txtPoblacion.setText("");
        txtIncoterm.setText("");
        txtPais.setText("");
        txtTipo.setText("");
        txtTransporte.setText("");
        txtSeguro.setText("");
        txtDescuento.setText("");
        txtSubtotal2.setText("0,00 €");
        txtTotalIva2.setText("0,00 €");
        txtTotal.setText("0,00 €");
        txtKilos.setText("0,00 kg");
        txtSubtotal.setText("0,00 €");
        txtTotalIva.setText("0,00 €");
    } // Cierre del método.

    /**
     * Método getter que llama al internal frame Proformas.
     *
     * @return Internal Frame Proformas.
     */
    public InterProformas getIfProforma() {
        return ifProforma;
    } // Cierre del método.

    /**
     * Método que establece el internal frame Proformas.
     *
     * @param ifProforma Internal Frame Proformas.
     */
    public void setIfProforma(InterProformas ifProforma) {
        this.ifProforma = ifProforma;
    } // Cierre del método.

    /**
     * Método para establecer el ID de la proforma.
     *
     * @param idProformaAux El ID de la proforma a establecer.
     */
    public void setIdProforma(int idProformaAux) {
        idProforma = idProformaAux;
    } // Cierre del método.

    /**
     * Método para obtener el ID de la proforma.
     *
     * @return El ID de la proforma obtenido.
     */
    public static int getIdProforma() {
        return idProforma;
    } // Cierre del método.

    /**
     * Método para obtener el tipo de precio del cliente seleccionado.
     *
     * @return El tipo de precio del cliente seleccionado.
     */
    public String obtenerTipoPrecio() {
        return txtTipo.getText();
    } // Cierre del método.

    /**
     * Método para obtener la lista de productos de la proforma.
     *
     * @return La lista de productos.
     */
    public List<ProductoProforma> getListaProductosProforma() {
        return listaProductosProforma;
    } // Cierre del método.

    /**
     * Método para establecer la lista de productos de la proforma.
     *
     * @param listaProductosProforma La lista de productos de la proforma.
     */
    public void setListaProductosProforma(List<ProductoProforma> listaProductosProforma) {
        this.listaProductosProforma = listaProductosProforma;
    }

    /**
     * Método de iconos de atención y/o advertencia.
     *
     * @param path Ruta del icono.
     * @param width Anchura del icono.
     * @param heigth Altura del icono.
     * @return Devuelve la imagen.
     */
    public Icon icono(String path, int width, int heigth) {
        Icon img = new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, heigth, java.awt.Image.SCALE_SMOOTH));
        return img;
    } // Cierre del método.

} // Cierre de la clase.
