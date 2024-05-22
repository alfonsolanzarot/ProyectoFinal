package vista;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import conexion.Conexion;
import controlador.Ctrl_FacturaCompra;
import controlador.Ctrl_VencimientosFacturaCompra;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
import modelo.FacturaCompra;
import modelo.VencimientoFacturaCompra;
import servicios.ServicioFacturaCompra;
import servicios.ServicioProducto;
import servicios.ServicioVencimientoFacturaCompra;

/**
 * Diálogo para gestionar facturas de compra. Permite insertar, eliminar y
 * modificar facturas de compra, así como gestionar sus vencimientos.
 *
 * @author Alfonso Lanzarot
 */
public class DlgFacturasCompra extends javax.swing.JDialog {

    private int xMouse, yMouse;
    private InterFacturasCompra ifFacturaCompra;
    private static int idFacturaCompra;
    private int idProveedor;
    private Ctrl_FacturaCompra ctrlFacturaCompra;
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private DlgVencimientosFacturaCompra dlgVencimientosFacturaCompra;
    private final Map<Integer, Integer> idFacturaCompraPorFila = new HashMap<>();
    private List<VencimientoFacturaCompra> listaVencimientosFacturaCompra = new ArrayList<>();

    /**
     * Crea un nuevo formulario DlgFacturaCompra.
     *
     * @param parent El marco padre del cuadro de diálogo.
     * @param modal Especifica si el diálogo debe ser modal.
     */
    public DlgFacturasCompra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setSize(new Dimension(760, 742));
        this.setLocationRelativeTo(null);
        this.btnInsertar.setText("<html><center>Insertar </br> vencimiento</html>");
        this.btnEliminar.setText("<html><center>Eliminar </br> vencimiento</html>");
        this.CargarComboProveedores();
        this.camposNoEditables();
        this.txtImporteIva.setText("0,00 €");
        this.txtBaseExenta.setText("0,00 €");
        this.txtRetencion.setText("0,00 €");
        this.txtTotal.setText("0,00 €");
        this.listenersCalculos();
        this.configurarTablaVencimientos();
        this.listaVencimientosFacturaCompra = new ArrayList<>();

        // Obtener el próximo idFacturaCompra disponible
        ctrlFacturaCompra = new Ctrl_FacturaCompra();
        idFacturaCompra = ctrlFacturaCompra.obtenerProximoIdFacturaCompra();

        // Añadir ActionListener a txtBaseImponible y cbTipoIva
        txtBaseImponible.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularImporteIVA();
            }
        });
        cbTipoIva.addActionListener((ActionEvent e) -> {
            calcularImporteIVA();
        });

    }

    /**
     * Método que establece el diálogo para insertar vencimientos a la factura
     * de compra.
     *
     * @param dlgVencimientosFacturaCompra Diálogo facturas de compra.
     */
    public void setDlgVencimientosFacturaCompra(DlgVencimientosFacturaCompra dlgVencimientosFacturaCompra) {
        this.dlgVencimientosFacturaCompra = dlgVencimientosFacturaCompra;
    } // Cierre del método.

    /**
     * Método para actualizar el JTable con los datos de la lista temporal.
     *
     * @param listaVencimientosFacturaCompra Lista de los vencimientos de la
     * factura de compra.
     */
    public void actualizarTablaVencimientos(List<VencimientoFacturaCompra> listaVencimientosFacturaCompra) {
        configurarTablaVencimientos();

        DefaultTableModel model = new DefaultTableModel();

        // Verificar si la lista de vencimientos no está vacía
        if (listaVencimientosFacturaCompra != null && !listaVencimientosFacturaCompra.isEmpty()) {
            // La lista de vencimientos no está vacía, se pueden recorrer los elementos
            for (VencimientoFacturaCompra vencimiento : listaVencimientosFacturaCompra) {
                Object[] row = {vencimiento.getFecha_vencimiento(), vencimiento.getImporte_pendiente(), vencimiento.getFecha_pago(),
                    vencimiento.getImporte_pagado(), vencimiento.isEstado()};
                model.addRow(row);
            }
            // Después de haber agregado los datos a la tabla mediante el método actualizarTablaVencimientos
            // Llama al método revalidate() del JScrollPane para actualizar su contenido y su estado interno
            jScrollPane1.setViewportView(tblVencimientos);
            jScrollPane1.revalidate();

        } else {
            // La lista de productos está vacía o es nula
            JOptionPane.showMessageDialog(null, "La lista de productos está vacía o es nula.", "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));

        }

    } // Cierre del método.

    /**
     * Método que llama a la lista de vencimientos actualizada.
     */
    public void onListaVencimientosActualizada() {
        actualizarTablaVencimientos(this.listaVencimientosFacturaCompra);

    } // Cierre del método.

    /**
     * Clase interna que define un renderizador de celdas personalizado para
     * cambiar el color del texto basado en el estado.
     */
    class CustomTableCellRenderer extends DefaultTableCellRenderer {

        /**
         * Renderiza las celdas de la tabla con un color de texto diferente
         * dependiendo del estado de la factura de compra.
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
            Boolean estado = (Boolean) table.getValueAt(row, 4);

            // Cambiar el color del texto basado en el estado.
            if (estado != null) {
                if (estado) {
                    setValue("PAGADO");
                    cellComponent.setForeground(new Color(0, 176, 80)); // Verde para PAGADA
                } else {
                    setValue("PENDIENTE");
                    cellComponent.setForeground(Color.RED); // Rojo para PENDIENTE
                }
            }

            // Establecer la fuente en negrita.
            Font font = cellComponent.getFont();
            cellComponent.setFont(font.deriveFont(font.getStyle() | Font.BOLD));

            return cellComponent;
        }
    } // Cierre de la clase.

    /**
     * Este método configura la tabla de vencimientos a incorporar en la factura
     * de compra, estableciendo el modelo de la tabla, personalizando el
     * encabezado, el tamaño de las filas, el tipo de letra y tamaño del
     * contenido de la tabla, así como el color de fondo del JScrollPane.
     * También alinea el contenido de ciertas columnas y personaliza el
     * renderizado del encabezado de la tabla.
     */
    private void configurarTablaVencimientos() {
        // Crear un modelo de tabla
        DefaultTableModel model = new DefaultTableModel();

        // Agregar columnas al modelo de tabla
        model.addColumn("Vencimiento");
        model.addColumn("Importe pendiente");
        model.addColumn("Fecha de pago");
        model.addColumn("Importe pagado");
        model.addColumn("Estado");

        // Establecer el modelo de tabla en la tabla
        tblVencimientos.setModel(model);

        // Personalizar el encabezado de la tabla
        JTableHeader header = tblVencimientos.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());

        // Aumentar la altura del encabezado de la tabla
        int alturaEncabezado = 42; // Puedes ajustar este valor según tus preferencias
        header.setPreferredSize(new Dimension(header.getWidth(), alturaEncabezado));

        // Obtener el renderizador predeterminado del encabezado
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tblVencimientos.getTableHeader().getDefaultRenderer();

        // Establecer alineación centrada para el renderizador del encabezado
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Personalizar el tamaño de las filas
        tblVencimientos.setRowHeight(42); // Cambiar el tamaño de las filas

        // Personalizar el tipo de letra y tamaño de la letra del contenido de la tabla
        tblVencimientos.setFont(new Font("Roboto", Font.PLAIN, 12)); // Cambiar el tipo de letra y tamaño

        // Cambiar el color de fondo del jScrollPane.
        jScrollPane1.getViewport().setBackground(new Color(247, 247, 252));

        tblVencimientos.setFillsViewportHeight(true);

        // Establecer el color de fondo del JTable
        tblVencimientos.setBackground(new Color(247, 247, 252));

        // Renderizador para alinear al centro las celdas de las columnas.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblVencimientos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Vencimiento
        tblVencimientos.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Importe pendiente
        tblVencimientos.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Fecha de pago
        tblVencimientos.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Importe pagado
        tblVencimientos.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Estado

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

        // Aplicar el renderizador personalizado a las columnas de los importes del importe pendiente y del importe pagado
        tblVencimientos.getColumnModel().getColumn(1).setCellRenderer(decimalRenderer);
        tblVencimientos.getColumnModel().getColumn(3).setCellRenderer(decimalRenderer);

        // Establecer el renderizador personalizado para la columna "Estado".
        tblVencimientos.getColumnModel().getColumn(4).setCellRenderer(new DlgFacturasCompra.CustomTableCellRenderer());

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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFacturasCompra = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblNumero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblProveedor = new javax.swing.JLabel();
        cbProveedor = new javax.swing.JComboBox<>();
        lblConcepto = new javax.swing.JLabel();
        txtConcepto = new javax.swing.JTextField();
        lblBaseImponible = new javax.swing.JLabel();
        txtBaseImponible = new javax.swing.JTextField();
        lblTipoIva = new javax.swing.JLabel();
        cbTipoIva = new javax.swing.JComboBox<>();
        lblImporteIva = new javax.swing.JLabel();
        txtImporteIva = new javax.swing.JTextField();
        lblBaseExenta = new javax.swing.JLabel();
        txtBaseExenta = new javax.swing.JTextField();
        lblRetencion = new javax.swing.JLabel();
        txtRetencion = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        lblObservaciones = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        lblDomiciliado = new javax.swing.JLabel();
        cbDomiciliado = new javax.swing.JComboBox<>();
        lblAsociada = new javax.swing.JLabel();
        txtAsociada = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        txtVencimientos = new javax.swing.JTextField();
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVencimientos = new javax.swing.JTable();
        btnCrear = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnInsertar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlFacturasCompra.setBackground(new java.awt.Color(247, 247, 252));
        pnlFacturasCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(52, 98, 139)));
        pnlFacturasCompra.setMinimumSize(new java.awt.Dimension(760, 742));
        pnlFacturasCompra.setPreferredSize(new java.awt.Dimension(760, 742));
        pnlFacturasCompra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlFacturasCompraMouseDragged(evt);
            }
        });
        pnlFacturasCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlFacturasCompraMousePressed(evt);
            }
        });
        pnlFacturasCompra.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(52, 98, 139));
        lblTitulo.setText("Nueva factura recibida");
        pnlFacturasCompra.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        lblFecha.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(102, 102, 102));
        lblFecha.setText("Fecha");
        pnlFacturasCompra.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtFecha.setForeground(new java.awt.Color(0, 0, 0));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtFecha.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlFacturasCompra.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 120, -1));

        lblNumero.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblNumero.setForeground(new java.awt.Color(102, 102, 102));
        lblNumero.setText("Número");
        pnlFacturasCompra.add(lblNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, -1, -1));

        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        txtNumero.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNumero.setForeground(new java.awt.Color(0, 0, 0));
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtNumero.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlFacturasCompra.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 130, -1));

        lblProveedor.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblProveedor.setForeground(new java.awt.Color(102, 102, 102));
        lblProveedor.setText("Proveedor");
        pnlFacturasCompra.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        cbProveedor.setBackground(new java.awt.Color(255, 255, 255));
        cbProveedor.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cbProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar proveedor:", "Item 2", "Item 3", "Item 4" }));
        cbProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbProveedor.setPreferredSize(new java.awt.Dimension(430, 27));
        pnlFacturasCompra.add(cbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 430, -1));

        lblConcepto.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblConcepto.setForeground(new java.awt.Color(102, 102, 102));
        lblConcepto.setText("Concepto");
        pnlFacturasCompra.add(lblConcepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        txtConcepto.setBackground(new java.awt.Color(255, 255, 255));
        txtConcepto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtConcepto.setForeground(new java.awt.Color(0, 0, 0));
        txtConcepto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtConcepto.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlFacturasCompra.add(txtConcepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 162, 700, -1));

        lblBaseImponible.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblBaseImponible.setForeground(new java.awt.Color(102, 102, 102));
        lblBaseImponible.setText("Base imponible");
        pnlFacturasCompra.add(lblBaseImponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        txtBaseImponible.setBackground(new java.awt.Color(255, 255, 255));
        txtBaseImponible.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtBaseImponible.setForeground(new java.awt.Color(0, 0, 0));
        txtBaseImponible.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBaseImponible.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtBaseImponible.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlFacturasCompra.add(txtBaseImponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 120, -1));

        lblTipoIva.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblTipoIva.setForeground(new java.awt.Color(102, 102, 102));
        lblTipoIva.setText("Tipo IVA");
        pnlFacturasCompra.add(lblTipoIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 60, -1));

        cbTipoIva.setBackground(new java.awt.Color(255, 255, 255));
        cbTipoIva.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        cbTipoIva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0,00%", "4,00%", "10,00%", "21,00%" }));
        cbTipoIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbTipoIva.setPreferredSize(new java.awt.Dimension(80, 27));
        pnlFacturasCompra.add(cbTipoIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, -1, -1));

        lblImporteIva.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblImporteIva.setForeground(new java.awt.Color(102, 102, 102));
        lblImporteIva.setText("Importe IVA");
        pnlFacturasCompra.add(lblImporteIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 75, -1));

        txtImporteIva.setBackground(new java.awt.Color(255, 255, 255));
        txtImporteIva.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtImporteIva.setForeground(new java.awt.Color(0, 0, 0));
        txtImporteIva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImporteIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtImporteIva.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlFacturasCompra.add(txtImporteIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 110, -1));

        lblBaseExenta.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblBaseExenta.setForeground(new java.awt.Color(102, 102, 102));
        lblBaseExenta.setText("Base exenta");
        pnlFacturasCompra.add(lblBaseExenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, -1, -1));

        txtBaseExenta.setBackground(new java.awt.Color(255, 255, 255));
        txtBaseExenta.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtBaseExenta.setForeground(new java.awt.Color(204, 204, 204));
        txtBaseExenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBaseExenta.setText("0,00 €");
        txtBaseExenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtBaseExenta.setPreferredSize(new java.awt.Dimension(64, 27));
        txtBaseExenta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBaseExentaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBaseExentaFocusLost(evt);
            }
        });
        pnlFacturasCompra.add(txtBaseExenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 110, -1));

        lblRetencion.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblRetencion.setForeground(new java.awt.Color(102, 102, 102));
        lblRetencion.setText("Retención");
        pnlFacturasCompra.add(lblRetencion, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, -1, -1));

        txtRetencion.setBackground(new java.awt.Color(255, 255, 255));
        txtRetencion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtRetencion.setForeground(new java.awt.Color(204, 204, 204));
        txtRetencion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRetencion.setText("0,00 €");
        txtRetencion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtRetencion.setPreferredSize(new java.awt.Dimension(64, 27));
        txtRetencion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRetencionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRetencionFocusLost(evt);
            }
        });
        pnlFacturasCompra.add(txtRetencion, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, 110, -1));

        lblTotal.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(102, 102, 102));
        lblTotal.setText("Total");
        pnlFacturasCompra.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, -1, -1));

        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(0, 0, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtTotal.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlFacturasCompra.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, 120, -1));

        lblObservaciones.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblObservaciones.setForeground(new java.awt.Color(102, 102, 102));
        lblObservaciones.setText("Observaciones");
        pnlFacturasCompra.add(lblObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        txaObservaciones.setBackground(new java.awt.Color(255, 255, 255));
        txaObservaciones.setColumns(20);
        txaObservaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txaObservaciones.setLineWrap(true);
        txaObservaciones.setRows(5);
        txaObservaciones.setWrapStyleWord(true);
        txaObservaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        jScrollPane2.setViewportView(txaObservaciones);

        pnlFacturasCompra.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 330, 110));

        lblDomiciliado.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblDomiciliado.setForeground(new java.awt.Color(102, 102, 102));
        lblDomiciliado.setText("Domiciliado");
        pnlFacturasCompra.add(lblDomiciliado, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 280, -1, -1));

        cbDomiciliado.setBackground(new java.awt.Color(255, 255, 255));
        cbDomiciliado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        cbDomiciliado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SÍ", "NO" }));
        cbDomiciliado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbDomiciliado.setPreferredSize(new java.awt.Dimension(80, 27));
        pnlFacturasCompra.add(cbDomiciliado, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 300, -1, -1));

        lblAsociada.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblAsociada.setForeground(new java.awt.Color(102, 102, 102));
        lblAsociada.setText("Asociada a factura emitida");
        pnlFacturasCompra.add(lblAsociada, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 280, -1, -1));

        txtAsociada.setBackground(new java.awt.Color(255, 255, 255));
        txtAsociada.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtAsociada.setForeground(new java.awt.Color(0, 0, 0));
        txtAsociada.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAsociada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtAsociada.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlFacturasCompra.add(txtAsociada, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 300, 160, -1));

        lblEstado.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(102, 102, 102));
        lblEstado.setText("Estado");
        pnlFacturasCompra.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 340, -1, -1));

        cbEstado.setBackground(new java.awt.Color(255, 255, 255));
        cbEstado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        cbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar estado:", "PENDIENTE", "PARCIALMENTE PAGADA", "PAGADA" }));
        cbEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        cbEstado.setPreferredSize(new java.awt.Dimension(80, 27));
        pnlFacturasCompra.add(cbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, 250, -1));

        txtVencimientos.setBackground(new java.awt.Color(222, 235, 247));
        txtVencimientos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtVencimientos.setForeground(new java.awt.Color(102, 102, 102));
        txtVencimientos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVencimientos.setText("VENCIMIENTOS");
        txtVencimientos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(208, 206, 206)));
        txtVencimientos.setEnabled(false);
        txtVencimientos.setPreferredSize(new java.awt.Dimension(64, 27));
        pnlFacturasCompra.add(txtVencimientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 700, -1));

        pnlTabla.setBackground(new java.awt.Color(247, 247, 252));
        pnlTabla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblVencimientos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblVencimientos);

        pnlTabla.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 700, 150));

        pnlFacturasCompra.add(pnlTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 720, 170));

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
        pnlFacturasCompra.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 670, 109, 42));

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
        pnlFacturasCompra.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 670, 109, 42));

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
        pnlFacturasCompra.add(btnInsertar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 670, 109, 42));

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
        pnlFacturasCompra.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 670, 109, 42));

        getContentPane().add(pnlFacturasCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 742));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Cambia el color de fondo del botón Crear cuando el ratón entra en el área
     * del botón.
     *
     * @param evt El evento del ratón.
     */
    private void btnCrearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseEntered
        btnCrear.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnCrearMouseEntered
    /**
     * Restaura el color de fondo del botón Crear cuando el ratón sale del área
     * del botón.
     *
     * @param evt El evento del ratón.
     */
    private void btnCrearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMouseExited
        btnCrear.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnCrearMouseExited
    /**
     * Maneja la acción del botón Crear. Si el texto del botón es "Crear",
     * muestra un mensaje de éxito y cierra el diálogo. Si el texto del botón es
     * "Actualizar", llama al método para actualizar la factura de compra.
     *
     * @param evt El evento de acción.
     */
    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        if (btnCrear.getText().equals("Crear")) {
            JOptionPane.showMessageDialog(null, "Factura de compra creada correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
            dispose();
        } else if (btnCrear.getText().equals("Actualizar")) {
            actualizarFacturaCompra();
        }
    }//GEN-LAST:event_btnCrearActionPerformed
    /**
     * Maneja el evento de pulsación de tecla en el botón Crear. Si se presiona
     * la tecla Enter, realiza la misma acción que btnCrearActionPerformed.
     *
     * @param evt El evento de la tecla.
     */
    private void btnCrearKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCrearKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (btnCrear.getText().equals("Crear")) {
                JOptionPane.showMessageDialog(null, "Factura de compra creada correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
                dispose();
            } else if (btnCrear.getText().equals("Actualizar")) {
                actualizarFacturaCompra();
            }
        }
    }//GEN-LAST:event_btnCrearKeyPressed

    /**
     * Validación de campos vacíos y creación provisional de la factura de
     * compra antes de insertar los vencimientos.
     *
     * @throws HeadlessException Excepción lanzada si se realiza una operación
     * gráfica en un entorno sin pantalla
     * @throws NumberFormatException Excepción lanzada si se produce un error al
     * convertir una cadena en un número
     */
    private void crear() throws HeadlessException, NumberFormatException {
        FacturaCompra facturaCompra = new FacturaCompra();
        Ctrl_FacturaCompra controlFacturaCompra = new Ctrl_FacturaCompra();

        if (txtFecha.getText().isEmpty() || cbProveedor.getSelectedItem().equals("Seleccionar proveedor:") || cbEstado.getSelectedItem().equals("Seleccionar estado:")) {
            JOptionPane.showMessageDialog(null, "Debe completar todos los campos.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));

        } else {

            // Obtener el ID del proveedor
            idProveedor = obtenerIdProveedor();
            if (idProveedor == -1) {
                JOptionPane.showMessageDialog(null, "Proveedor seleccionado no es válido.",
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
                return;
            }

            // Comprobar si la factura de compra ya existe por número
            String numFacturaCompra = txtNumero.getText().trim();

            if (!controlFacturaCompra.existeFacturaCompra(numFacturaCompra)) {

                facturaCompra.setFecha(txtFecha.getText().trim());
                facturaCompra.setNumero(txtNumero.getText().trim());
                facturaCompra.setNombre_proveedor(cbProveedor.getSelectedItem().toString());
                facturaCompra.setDescripcion(txtConcepto.getText().trim());
                if (null != txtBaseImponible.getText() && !txtBaseImponible.getText().isBlank()) {
                    facturaCompra.setBase_imponible(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtBaseImponible.getText())));
                }
                facturaCompra.setTipo_iva(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje((String) cbTipoIva.getSelectedItem())));
                if (null != txtImporteIva.getText() && !txtImporteIva.getText().isBlank()) {
                    facturaCompra.setIva(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtImporteIva.getText())));
                }
                if (null != txtBaseExenta.getText() && !txtBaseExenta.getText().isBlank()) {
                    facturaCompra.setBase_exenta(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtBaseExenta.getText())));
                }
                if (null != txtRetencion.getText() && !txtRetencion.getText().isBlank()) {
                    facturaCompra.setRetencion(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtRetencion.getText())));
                }
                facturaCompra.setTotal(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtTotal.getText())));
                facturaCompra.setDomiciliado("SÍ".equals((String) cbDomiciliado.getSelectedItem()));
                facturaCompra.setObservaciones(txaObservaciones.getText());
                facturaCompra.setAsociada(txtAsociada.getText().trim());
                facturaCompra.setEstado(cbEstado.getSelectedItem().toString());

                // Actualizar el idProveedor en el objeto FacturaCompra
                facturaCompra.setIdProveedor(idProveedor);

                if (controlFacturaCompra.crear(facturaCompra)) {

                    this.ifFacturaCompra.recargarTabla();

                } else {
                    JOptionPane.showMessageDialog(null, "Error al crear la factura de compra.",
                            "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                    this.Limpiar();
                }

            } else {
                JOptionPane.showMessageDialog(null, "La factura ya está registrada en la base de datos.",
                        "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));

            }
        }
    } // Cierre del método.

    /**
     *
     * Método que actualiza la factura de compra después de insertar más
     * vencimientos o de modificarla.
     */
    private void actualizarFacturaCompra() {
        FacturaCompra facturaCompra = new FacturaCompra();

        Ctrl_FacturaCompra controlFacturaCompra = new Ctrl_FacturaCompra();

        // Obtener el ID del proveedor
        idProveedor = obtenerIdProveedor();
        if (idProveedor == -1) {
            JOptionPane.showMessageDialog(null, "Proveedor seleccionado no es válido.",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            return;
        }

        facturaCompra.setIdFacturaCompra(idFacturaCompra);
        facturaCompra.setIdProveedor(idProveedor);
        facturaCompra.setFecha(txtFecha.getText().trim());
        facturaCompra.setNumero(txtNumero.getText().trim());
        facturaCompra.setNombre_proveedor(cbProveedor.getSelectedItem().toString());
        facturaCompra.setDescripcion(txtConcepto.getText().trim());

        if (null != txtImporteIva.getText() && !txtImporteIva.getText().isBlank()) {
            facturaCompra.setIva(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtImporteIva.getText())));
        }
        if (null != txtBaseExenta.getText() && !txtBaseExenta.getText().isBlank()) {
            facturaCompra.setBase_exenta(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtBaseExenta.getText())));
        }
        if (null != txtRetencion.getText() && !txtRetencion.getText().isBlank()) {
            facturaCompra.setRetencion(Double.valueOf(ServicioProducto.quitarSimboloEuroKiloPorcentaje(txtRetencion.getText())));
        }
        facturaCompra.setTotal(Double.valueOf(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtTotal.getText())));
        facturaCompra.setDomiciliado("SÍ".equals((String) cbDomiciliado.getSelectedItem()));
        facturaCompra.setObservaciones(txaObservaciones.getText());
        facturaCompra.setAsociada(txtAsociada.getText().trim());
        facturaCompra.setEstado(cbEstado.getSelectedItem().toString());

        // Actualizar la información de la factura de compra en la base de datos
        if (controlFacturaCompra.actualizarFacturaCompra(facturaCompra)) {

            JOptionPane.showMessageDialog(null, "Factura de compra actualizada correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));
            dispose(); // Cerrar el diálogo después de actualizar.

            // Recargar tabla
            this.ifFacturaCompra.recargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la factura de compra.", "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
            Limpiar(); // Limpiar los campos en caso de error
        }
    } // Cierre del método.

    /**
     * Cambia el color de fondo del botón Cancelar cuando el ratón entra en el
     * área del botón.
     *
     * @param evt El evento del ratón.
     */
    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnCancelarMouseEntered
    /**
     * Restaura el color de fondo del botón Cancelar cuando el ratón sale del
     * área del botón.
     *
     * @param evt El evento del ratón.
     */
    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnCancelarMouseExited
    /**
     * Maneja la acción del botón Cancelar. Cierra el diálogo y recarga la tabla
     * de facturas.
     *
     * @param evt El evento de acción.
     */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
        this.ifFacturaCompra.recargarTabla();
    }//GEN-LAST:event_btnCancelarActionPerformed
    /**
     * Cambia el color de fondo del botón Insertar cuando el ratón entra en el
     * área del botón.
     *
     * @param evt El evento del ratón.
     */
    private void btnInsertarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInsertarMouseEntered
        btnInsertar.setBackground(new Color(81, 111, 129));
    }//GEN-LAST:event_btnInsertarMouseEntered
    /**
     * Restaura el color de fondo del botón Insertar cuando el ratón sale del
     * área del botón.
     *
     * @param evt El evento del ratón.
     */
    private void btnInsertarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInsertarMouseExited
        btnInsertar.setBackground(new Color(106, 141, 162));
    }//GEN-LAST:event_btnInsertarMouseExited
    /**
     * Maneja la acción del botón Insertar. Llama al método para insertar
     * vencimientos.
     *
     * @param evt El evento de acción.
     */
    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        insertarVencimientos();
    }//GEN-LAST:event_btnInsertarActionPerformed
    /**
     * Cambia el color de fondo del botón Eliminar cuando el ratón entra en el
     * área del botón.
     *
     * @param evt El evento del ratón.
     */
    private void btnEliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseEntered
        btnEliminar.setBackground(new Color(255, 91, 95));
    }//GEN-LAST:event_btnEliminarMouseEntered
    /**
     * Restaura el color de fondo del botón Eliminar cuando el ratón sale del
     * área del botón.
     *
     * @param evt El evento del ratón.
     */
    private void btnEliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseExited
        btnEliminar.setBackground(new Color(255, 124, 128));
    }//GEN-LAST:event_btnEliminarMouseExited
    /**
     * Maneja la acción del botón Eliminar. Llama al método para eliminar
     * vencimientos.
     *
     * @param evt El evento de acción.
     */
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarVencimientos();
    }//GEN-LAST:event_btnEliminarActionPerformed
    /**
     * Método que registra la posición del cursor del mouse en el panel de
     * encabezado.
     *
     * @param evt Evento del mouse.
     */
    private void pnlFacturasCompraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlFacturasCompraMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_pnlFacturasCompraMousePressed
    /**
     * Método que arrastra la ventana a la nueva posición según el movimiento
     * del mouse.
     *
     * @param evt Evento del mouse.
     */
    private void pnlFacturasCompraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlFacturasCompraMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_pnlFacturasCompraMouseDragged
    /**
     * Acción al obtener el foco en el campo de texto de Base Exenta.
     *
     * @param evt Evento del foco.
     */
    private void txtBaseExentaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBaseExentaFocusGained
        focoBaseExenta();
    }//GEN-LAST:event_txtBaseExentaFocusGained
    /**
     * Acción al perder el foco en el campo de texto de Base exenta.
     *
     * @param evt Evento del foco.
     */
    private void txtBaseExentaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBaseExentaFocusLost
        focoRetencion();
    }//GEN-LAST:event_txtBaseExentaFocusLost
    /**
     * Acción al obtener el foco en el campo de texto de Retención.
     *
     * @param evt Evento del foco.
     */
    private void txtRetencionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRetencionFocusGained
        focoRetencion();
    }//GEN-LAST:event_txtRetencionFocusGained
    /**
     * Acción al perder el foco en el campo de texto de Retención.
     *
     * @param evt Evento del foco.
     */
    private void txtRetencionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRetencionFocusLost
        focoBaseExenta();
    }//GEN-LAST:event_txtRetencionFocusLost

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
            java.util.logging.Logger.getLogger(DlgFacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgFacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgFacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgFacturasCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgFacturasCompra dialog = new DlgFacturasCompra(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cbDomiciliado;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JComboBox<String> cbProveedor;
    private javax.swing.JComboBox<String> cbTipoIva;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAsociada;
    private javax.swing.JLabel lblBaseExenta;
    private javax.swing.JLabel lblBaseImponible;
    private javax.swing.JLabel lblConcepto;
    private javax.swing.JLabel lblDomiciliado;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblImporteIva;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblRetencion;
    private javax.swing.JLabel lblTipoIva;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlFacturasCompra;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JTable tblVencimientos;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JTextField txtAsociada;
    private javax.swing.JTextField txtBaseExenta;
    private javax.swing.JTextField txtBaseImponible;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtImporteIva;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtRetencion;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtVencimientos;
    // End of variables declaration//GEN-END:variables

    /**
     * Método que valida los campos requeridos y abre el diálogo para la
     * inserción de vencimientos.
     */
    private void insertarVencimientos() {
        if (txtFecha.getText().isEmpty() || cbProveedor.getSelectedItem().equals("Seleccionar Proveedor:") || cbEstado.getSelectedItem().equals("Seleccionar estado:")) {
            JOptionPane.showMessageDialog(null, "Antes de insertar valores debe completar la fecha, el nombre \ndel proveedor y el estado de la factura de compra.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        } else {

            crear();

            Frame f = JOptionPane.getFrameForComponent(this);
            dlgVencimientosFacturaCompra = new DlgVencimientosFacturaCompra(f, true);
            dlgVencimientosFacturaCompra.setIdFacturaCompra(idFacturaCompra);
            dlgVencimientosFacturaCompra.setDlgFacturasCompra(this);
            dlgVencimientosFacturaCompra.setVisible(true);

            // Agregar el WindowListener después de inicializar dlgVencimientosFacturaCompra
            dlgVencimientosFacturaCompra.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Cuando se cierra el diálogo DlgVencimientosFacturaCompra, actualiza el texto del botón btnInsertar
                    btnInsertar.setText("<html><center>Insertar más </br> vencimientos</html>");
                }
            });

            if (btnInsertar.getText().equals("<html><center>Insertar más </br> vencimientos</html>")) {

                calcularTotal();

            }
        }
    } // Cierre del método.

    /**
     * Método que elimina los vencimientos seleccionados de la tabla.
     */
    private void eliminarVencimientos() {
        Ctrl_VencimientosFacturaCompra controlFacturaCompra = new Ctrl_VencimientosFacturaCompra();
        //Obtener la fila seleccionada.

        int filaSeleccionada = tblVencimientos.getSelectedRow();
        try {
            if (filaSeleccionada != -1) {
                idFacturaCompra = idFacturaCompraPorFila.get(filaSeleccionada);
                System.out.println(idFacturaCompra);
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer eliminar el vencimiento seleccionado?", "ATENCIÓN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icono("/img/pregunta.png", 40, 40));
                if (respuesta == JOptionPane.YES_OPTION) {
                    if (controlFacturaCompra.eliminar(idFacturaCompra)) {

                        this.recargarTabla();

                        calcularTotal();

                        JOptionPane.showMessageDialog(null, "Vencimiento eliminado correctamente.", "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/correcto.png", 40, 40));

                    } else {

                        JOptionPane.showMessageDialog(null, "Error al eliminar el vencimiento.",
                                "ATENCIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/cancelar.png", 40, 40));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un vencimiento para eliminarlo.",
                        "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
            }
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }
    } // Cierre del método.

    /**
     * Método para cargar los proveedores en el combo box de proveedores.
     */
    private void CargarComboProveedores() {
        Connection cn = Conexion.conectar();
        String sql = "SELECT idProveedor, nombre FROM tb_proveedores";
        Statement st;

        try {
            st = (Statement) cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cbProveedor.removeAllItems();
            cbProveedor.addItem("Seleccionar proveedor: ");

            while (rs.next()) {
                cbProveedor.addItem(rs.getString("nombre") + " (ID: " + rs.getInt("idProveedor") + ")");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los proveedores: " + ex,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        } finally {

            if (cn != null) {

                try {

                    cn.close();

                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex,
                            "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));

                }
            }
        }
    } // Cierre del método.

    /**
     * Obtiene el ID del proveedor seleccionado en el JComboBox de proveedores.
     * Si no se selecciona ningún proveedor, devuelve -1.
     *
     * @return El ID del proveedor seleccionado, o -1 si no se selecciona
     * ninguno.
     */
    private int obtenerIdProveedor() {
        Connection cn = Conexion.conectar();
        String proveedorSeleccionado = (String) cbProveedor.getSelectedItem();
        if (proveedorSeleccionado.equals("Seleccionar proveedor:")) {
            return -1;
        }

        String nombreProveedor = proveedorSeleccionado.split(" \\(ID: ")[0]; // Extraer el nombre del proveedor
        String sql = "SELECT idProveedor FROM tb_proveedores WHERE nombre = '" + nombreProveedor + "'";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("idProveedor");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el ID del proveedor: " + ex,
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            return -1;
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex,
                            "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
                }
            }
        }
    } // Cierre del método.

    /**
     * Establece los valores de los campos de texto y áreas de texto con los
     * valores proporcionados. Si alguno de los valores es null, se establece
     * como "0.00" o una cadena vacía, según corresponda.
     *
     * @param baseImponible La base imponible a establecer en el campo de texto.
     * @param tipoIva El tipo de IVA a establecer en el JComboBox.
     * @param totalIva El total del IVA a establecer en el campo de texto.
     * @param baseExenta La base exenta a establecer en el campo de texto.
     * @param retencion La retención a establecer en el campo de texto.
     * @param observaciones Las observaciones a establecer en el área de texto.
     */
    private void setTextFields(Double baseImponible, Double tipoIva, Double totalIva, Double baseExenta, Double retencion, String observaciones) {
        // Asegurarse de que los valores numéricos no sean null
        if (baseImponible != null) {
            txtBaseImponible.setText(decimalFormat.format(baseImponible));
        } else {
            txtBaseImponible.setText("0.00");
        }

        if (tipoIva != null) {
            cbTipoIva.setSelectedItem(decimalFormat.format(tipoIva) + "%");
        } else {
            cbTipoIva.setSelectedItem("0.00%");
        }

        if (totalIva != null) {
            txtImporteIva.setText(decimalFormat.format(totalIva));
        } else {
            txtImporteIva.setText("0.00");
        }

        if (baseExenta != null) {
            txtBaseExenta.setText(decimalFormat.format(baseExenta));
        } else {
            txtBaseExenta.setText("0.00");
        }

        if (retencion != null) {
            txtRetencion.setText(decimalFormat.format(retencion));
        } else {
            txtRetencion.setText("0.00");
        }

        if (observaciones != null) {
            txaObservaciones.setText(observaciones);
        } else {
            txaObservaciones.setText("");
        }
    } // Cierre del método.

    /**
     * Método para cargar los datos de la factura de compra que no están en la
     * tabla.
     */
    private void CargarDatosFacturaCompra() {
        DlgFacturasCompra.ConexionBD conexion = new DlgFacturasCompra.ConexionBD();

        try {
            Class.forName(conexion.driver);
            conexion.con = DriverManager.getConnection(conexion.url, conexion.usuario, conexion.clave);

            String sql = "SELECT idFacturaCompra, idProveedor, fecha, numero, nombre_proveedor, descripcion, base_imponible, tipo_iva, iva, "
                    + "base_exenta, retencion, total, domiciliado, observaciones, asociada, estado FROM tb_factura_compra WHERE idFacturaCompra=" + idFacturaCompra;

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    // Obtener los valores de cada campo de la tabla
                    Double baseImponible = rs.getDouble("base_imponible");
                    Double tipoIva = rs.getDouble("tipo_iva");
                    Double totalIva = rs.getDouble("iva");
                    Double baseExenta = rs.getDouble("base_exenta");
                    Double retencion = rs.getDouble("retencion");
                    String observaciones = rs.getString("observaciones");

                    // Asignar los valores a los campos de texto
                    setTextFields(baseImponible, tipoIva, totalIva, baseExenta, retencion, observaciones);

                } else {
                    System.out.println("No se encontró ninguna factura de compra con el ID proporcionado.");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la factura de compra: " + e,
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
     * Método que muestra los datos seleccionados.
     *
     * @param idFacturaCompra El ID de la factura de compra seleccionada.
     * @param datosFila Los datos de la factura de compra seleccionada.
     */
    public void mostrarDatos(int idFacturaCompra, Object[] datosFila) {

        lblTitulo.setText("Editar factura recibida");
        btnCrear.setText("Actualizar");
        btnInsertar.setText("<html><center>Insertar más</br> vencimientos</html>");
        cbProveedor.setEnabled(false);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        symbols.setCurrencySymbol("€");
        decimalFormat = new DecimalFormat("#,##0.00", symbols);

        double total = (Double) datosFila[4];

        // Conversión de los valores Double a String
        txtFecha.setText((String) datosFila[0]);
        txtNumero.setText((String) datosFila[1]);
        txtConcepto.setText((String) datosFila[3]);
        txtAsociada.setText((String) datosFila[5]);
        txtTotal.setText(decimalFormat.format((total)) + " €");

        // Seleccionar el proveedor correspondiente
        String proveedor = (String) datosFila[2];

        // Establecer modelo de ComboBox para proveedores
        DefaultComboBoxModel<String> modeloProveedor = new DefaultComboBoxModel<>();
        modeloProveedor.addElement("");
        modeloProveedor.addElement(proveedor);
        cbProveedor.setModel(modeloProveedor);
        cbProveedor.setSelectedIndex(1);

        // Establecer modelo de ComboBox para estado
        DefaultComboBoxModel<String> modeloEstado = new DefaultComboBoxModel<>();
        modeloEstado.addElement("Seleccionar estado:");
        modeloEstado.addElement("PENDIENTE");
        modeloEstado.addElement("PARCIALMENTE PAGADA");
        modeloEstado.addElement("PAGADA");
        cbEstado.setModel(modeloEstado);

        // Seleccionar el estado correspondiente
        String estado = (String) datosFila[7];
        cbEstado.setSelectedItem(estado);

        // Establecer modelo de ComboBox para estado
        DefaultComboBoxModel<String> modeloDomiciliado = new DefaultComboBoxModel<>();
        modeloDomiciliado.addElement("NO");
        modeloDomiciliado.addElement("SÍ");
        cbDomiciliado.setModel(modeloDomiciliado);

        // Seleccionar el estado correspondiente
        boolean domiciliado = (boolean) datosFila[6];
        cbDomiciliado.setSelectedItem(domiciliado ? "SÍ" : "NO");

        CargarTablaVencimientos();
        CargarDatosFacturaCompra();

        listenersCalculos();

        calcularTotal();

    } // Cierre del método.

    /**
     * Listeners para detectar cambios en base imponible, tipo de IVA, importe
     * IVA, base exenta y retención.
     */
    private void listenersCalculos() {
        // Listener para txtBaseImponible para detectar cambios en el texto
        txtBaseImponible.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }
        });
        // Listener para cbTipoIva para detectar cambios en la selección
        cbTipoIva.addActionListener((ActionEvent e) -> {
            // Llamar al método de cálculo correspondiente
            calcularImporteIVA();
            calcularTotal();
        });
        // Listener para txtBaseExenta para detectar cambios en el texto
        txtBaseExenta.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }
        });
        // Listener para txtRetencion para detectar cambios en el texto
        txtRetencion.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Llamar al método de cálculo correspondiente
                calcularImporteIVA();
                calcularTotal();
            }
        });

    } // Cierre de los listeners.

    /**
     * Este método calcula el importe del IVA basado en el tipo de IVA
     * seleccionado y la base imponible. Primero, extrae el tipo de IVA
     * seleccionado del JComboBox cbTipoIva y el importe del campo de texto
     * txtBaseImponible. Luego, calcula el importe del IVA multiplicando la base
     * imponible por el porcentaje del tipo de IVA. Finalmente, actualiza el
     * campo de texto txtImporteIva con el importe del IVA calculado y
     * formateado.
     */
    private void calcularImporteIVA() {
        String tipoIvaStr = (String) cbTipoIva.getSelectedItem();
        String baseImponibleText = txtBaseImponible.getText().trim();
        try {
            // Verificar si el campo txtBaseImponible no está vacío
            if (baseImponibleText.isEmpty()) {
                txtImporteIva.setText("0,00 €");
                return;
            }

            // Reemplazar comas por puntos para el parseo y verificar si es un número válido
            baseImponibleText = baseImponibleText.replace(".", "").replace(",", ".");
            double baseImponible = Double.parseDouble(baseImponibleText);
            tipoIvaStr = tipoIvaStr.replace("%", "").replace(",", ".");

            // Obtener el tipo de IVA
            double tipoIva = Double.parseDouble(tipoIvaStr) / 100.0;

            // Calcular el importe del IVA
            double importeIva = baseImponible * tipoIva;

            //Formatear el importe del IVA con el formato deseado
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
            df.applyPattern("#,##0.00 €");
            String importeIvaFormatted = df.format(importeIva);

            txtImporteIva.setText(importeIvaFormatted);
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e);
            txtImporteIva.setText("Error");
        }

    } // Cierre del método.

    /**
     * Método para calcular txtTotal.
     */
    private void calcularTotal() {

        try {
            // Obtener el subtotal
            double subtotal = 0.0;

            // Calcular base imponible
            if (!txtBaseImponible.getText().isBlank()) {
                subtotal += Double.parseDouble(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtBaseImponible.getText()));
            }

            // Agregar el importe de IVA, si está presente
            if (!txtImporteIva.getText().isBlank()) {
                subtotal += Double.parseDouble(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtImporteIva.getText()));
            }

            // Agregar el importe de la base exenta, si está presente
            if (!txtBaseExenta.getText().isBlank()) {
                subtotal += Double.parseDouble(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtBaseExenta.getText()));
            }

            // Restar el importe de retención, si está presente
            if (!txtRetencion.getText().isBlank()) {
                subtotal -= Double.parseDouble(ServicioFacturaCompra.quitarSimboloEuroPorcentaje(txtRetencion.getText()));
            }

            // Actualizar txtTotal con el resultado
            txtTotal.setText(decimalFormat.format(subtotal) + " €");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido en los campos base exenta y retención.",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
        }
    } // Cierre del método.

    /**
     * Método para recargar la tabla con todos los vencimientos registrados
     * cuando se añade uno nuevo y se elimina un vencimiento.
     */
    public void recargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblVencimientos.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos

        CargarTablaVencimientos();
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
            try {
                // Cargar el controlador JDBC
                Class.forName(driver);
                // Establecer la conexión
                con = DriverManager.getConnection(url, usuario, clave);
                System.out.println("Conexión exitosa a la base de datos " + dbName);
            } catch (CommunicationsException e) {
                System.out.println("Error de comunicación con la base de datos: " + e.toString());
                JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos. Verifique su conexión a internet y el estado del servidor.",
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            } catch (SQLException e) {
                System.out.println("Error al iniciar sesión: " + e.toString());
                JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            } catch (ClassNotFoundException e) {
                System.out.println("Error al cargar el controlador JDBC: " + e.toString());
                JOptionPane.showMessageDialog(null, "Error al cargar el controlador JDBC: " + e,
                        "ERROR", JOptionPane.ERROR_MESSAGE, icono("/img/cancelar.png", 40, 40));
            }
        }
    } // Cierre de la clase

    /**
     * Método para cargar la tabla con todos los vencimientos registrados.
     */
    private void CargarTablaVencimientos() {
        DlgFacturasCompra.ConexionBD conexion = new DlgFacturasCompra.ConexionBD();

        try {
            try {
                Class.forName(conexion.driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.con = DriverManager.getConnection(conexion.url, conexion.usuario, conexion.clave);

            DefaultTableModel model = (DefaultTableModel) tblVencimientos.getModel();

            String sql = "SELECT idFacturaCompra, fecha_vencimiento, importe_pendiente, fecha_pago, importe_pagado, estado FROM tb_vencimientos_pago WHERE idFacturaCompra='" + idFacturaCompra + "'";

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                listaVencimientosFacturaCompra = new ArrayList<>();

                while (rs.next()) {

                    VencimientoFacturaCompra vencimientoFacturaCompra = ServicioVencimientoFacturaCompra.asignarDatosVencimiento(rs);
                    listaVencimientosFacturaCompra.add(vencimientoFacturaCompra);

                    Object[] datosFila = this.asignarDatosModelo(vencimientoFacturaCompra);
                    model.addRow(datosFila);

                    // Obtener el ID del producto de la fila actual
                    idFacturaCompra = rs.getInt("idFacturaCompra");

                    // Obtener el índice de la fila recién agregada
                    int fila = model.getRowCount() - 1;

                    // Asociar el ID de la factura de compra con el índice de fila en el HashMap
                    idFacturaCompraPorFila.put(fila, idFacturaCompra);

                }

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(null, "Error al llenar la tabla de vencimientos: " + e,
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
     * Método para asignar los datos de los vencimientos registrados al modelo
     * de tabla.
     */
    private Object[] asignarDatosModelo(VencimientoFacturaCompra vencimientoFacturaCompra) {

        Object fila[] = new Object[5];

        fila[0] = vencimientoFacturaCompra.getFecha_vencimiento();
        fila[1] = vencimientoFacturaCompra.getImporte_pendiente();
        fila[2] = vencimientoFacturaCompra.getFecha_pago();
        fila[3] = vencimientoFacturaCompra.getImporte_pagado();
        fila[4] = vencimientoFacturaCompra.isEstado();

        return fila;
    } // Cierre del método.

    /**
     * Método de foco para el campo Base Exenta.
     */
    private void focoBaseExenta() {
        if (txtBaseExenta.getText().equals("0,00 €")) {
            txtBaseExenta.setText("");
            txtBaseExenta.setForeground(Color.black);
        }
        if (txtRetencion.getText().isEmpty()) {
            txtRetencion.setText("0,00 €");
            txtRetencion.setForeground(new Color(204, 204, 204));
        }
    } // Cierre del método.

    /**
     * Método de foco para el campo Retención.
     */
    private void focoRetencion() {
        if (txtRetencion.getText().equals("0,00 €")) {
            txtRetencion.setText("");
            txtRetencion.setForeground(Color.black);
        }
        if (txtBaseExenta.getText().isEmpty()) {
            txtBaseExenta.setText("0,00 €");
            txtBaseExenta.setForeground(new Color(204, 204, 204));
        }
    } // Cierre del método.

    /**
     * Método que establece los campos que no son editables.
     */
    private void camposNoEditables() {
        // Establecemos estos campos como no editables.
        txtImporteIva.setEditable(false);
        txtTotal.setEditable(false);

    } // Cierre del método.

    /**
     * Método para limpiar los campos.
     */
    private void Limpiar() {

        txtFecha.setText("");
        txtNumero.setText("");
        cbProveedor.setSelectedIndex(0);
        txtConcepto.setText("");
        txtBaseImponible.setText("");
        txtImporteIva.setText("0,00 €");
        txtBaseExenta.setText("0,00 €");
        txtRetencion.setText("0,00 €");
        txtTotal.setText("0,00 €");
        txaObservaciones.setText("");
        cbDomiciliado.setSelectedIndex(0);
        txtAsociada.setText("");
        cbEstado.setSelectedItem("Seleccionar estado:");

    } // Cierre del método.

    /**
     * Método getter que llama al internal frame FacturasCompra.
     *
     * @return Internal Frame FacturasCompra.
     */
    public InterFacturasCompra getIfFacturaCompra() {
        return ifFacturaCompra;
    } // Cierre del método.

    /**
     * Método que establece el internal frame FacturasCompra.
     *
     * @param ifFacturaCompra Internal Frame FacturasCompra.
     */
    public void setIfFacturaCompra(InterFacturasCompra ifFacturaCompra) {
        this.ifFacturaCompra = ifFacturaCompra;
    } // Cierre del método.

    /**
     * Método para establecer el ID de la factura de compra.
     *
     * @param idFacturaCompraAux El ID de la factura de compra a establecer.
     */
    public void setIdFacturaCompra(int idFacturaCompraAux) {
        idFacturaCompra = idFacturaCompraAux;
    } // Cierre del método.

    /**
     * Método para obtener el ID de la factura de compra.
     *
     * @return El ID de la factura de compra obtenido.
     */
    public static int getIdFacturaCompra() {
        return idFacturaCompra;
    } // Cierre del método.

    /**
     * Método para obtener la lista de vencimientos de la factura de compra.
     *
     * @return La lista de vencimientos.
     */
    public List<VencimientoFacturaCompra> getListaVencimientosFacturaCompra() {
        return listaVencimientosFacturaCompra;
    } // Cierre del método.

    /**
     * Método para establecer la lista de vencimientos de la factura de compra.
     *
     * @param listaVencimientosFacturaCompra La lista de vencimientos de la
     * factura de compra.
     */
    public void setListaVencimientosFacturaCompra(List<VencimientoFacturaCompra> listaVencimientosFacturaCompra) {
        this.listaVencimientosFacturaCompra = listaVencimientosFacturaCompra;
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
