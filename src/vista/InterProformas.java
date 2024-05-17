package vista;

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
import modelo.Proforma;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import servicios.ServicioProforma;

/**
 * Esta clase representa un marco interno utilizado para gestionar la
 * información relacionada con las facturas proforma, incluyendo la creación,
 * edición, impresión y visualización de dicha información.
 *
 * @author Alfonso Lanzarot
 */
public class InterProformas extends javax.swing.JInternalFrame {

    /**
     * Variables de instancia de la clase.
     */
    private final Map<Integer, Integer> idProformaPorFila = new HashMap<>();
    private int idProforma;

    List<Proforma> listaProformas = new ArrayList<>();

    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();

    int ancho = (int) d.getWidth();
    int alto = (int) d.getHeight();

    /**
     * Constructor de la clase InterProformas. Inicializa el JInternalFrame,
     * configura su tamaño y título, carga y configura la tabla de proformas, y
     * añade un WindowListener para cargar la tabla cuando se abre el frame
     * interno.
     */
    public InterProformas() {
        initComponents();
        this.setSize(ancho, alto);
        this.setTitle("PROFORMAS");
        CargarTablaProformas();
        configurarTablaProformas();

        // Añadimos WindowListener para detectar cuándo se abre el frame interno.
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                CargarTablaProformas(); // Llama al método para cargar la tabla cuando se abre el frame.
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
         * dependiendo del estado de la proforma.
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
            String estado = (String) table.getValueAt(row, 8);

            // Cambiar el color del texto basado en el estado.
            switch (estado) {
                case "Pedido en curso":
                    cellComponent.setForeground(new Color(15, 158, 213));
                    break;
                case "Pedido realizado":
                    cellComponent.setForeground(new Color(0, 176, 80));
                    break;
                case "Pedido cancelado":
                    cellComponent.setForeground(Color.RED);
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
     * Este método configura la tabla de proformas, estableciendo el modelo de
     * la tabla, personalizando el encabezado, el tamaño de las filas, el tipo
     * de letra y tamaño del contenido de la tabla, así como el color de fondo
     * del JScrollPane. También alinea el contenido de ciertas columnas y
     * personaliza el renderizado del encabezado de la tabla.
     */
    private void configurarTablaProformas() {
        // Crear un modelo de tabla.
        DefaultTableModel model = new DefaultTableModel();

        // Agregar columnas al modelo de tabla.
        model.addColumn("Fecha");
        model.addColumn("Número");
        model.addColumn("Vencimiento");
        model.addColumn("Cliente");
        model.addColumn("Transporte");
        model.addColumn("Seguro");
        model.addColumn("Total kilos/uds.");
        model.addColumn("Total euros");
        model.addColumn("Estado");

        // Establecer el modelo de tabla en la tabla.
        tblProformas.setModel(model);

        // Personalizar el encabezado de la tabla.
        JTableHeader header = tblProformas.getTableHeader();
        header.setDefaultRenderer(new CustomHeaderRenderer());

        // Aumentar la altura del encabezado de la tabla.
        int alturaEncabezado = 42;
        header.setPreferredSize(new Dimension(header.getWidth(), alturaEncabezado));

        // Obtener el renderizador predeterminado del encabezado.
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tblProformas.getTableHeader().getDefaultRenderer();

        // Establecer alineación centrada para el renderizador del encabezado.
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Personalizar el tamaño de las filas
        tblProformas.setRowHeight(60); // Cambiar el tamaño de las filas.

        // Personalizar el tipo de letra y tamaño de la letra del contenido de la tabla.
        tblProformas.setFont(new Font("Roboto", Font.PLAIN, 12)); // Cambiar el tipo de letra y tamaño.

        // Cambiar el color de fondo del jScrollPane.
        jScrollPane1.getViewport().setBackground(new Color(247, 247, 252));

        // Renderizador para alinear al centro las celdas de las columnas de fecha, número, vencimiento y los importes y pesos.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblProformas.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Fecha.
        tblProformas.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Número.
        tblProformas.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Vencimiento.

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

        // Aplicar el renderizador personalizado a las columnas de los importes del transporte, el seguro, y el total euros.
        tblProformas.getColumnModel().getColumn(4).setCellRenderer(decimalRenderer); // Transporte.
        tblProformas.getColumnModel().getColumn(5).setCellRenderer(decimalRenderer); // Seguro.
        tblProformas.getColumnModel().getColumn(7).setCellRenderer(decimalRenderer); // Total euros.

        // Renderizador para las unidades de peso.
        DefaultTableCellRenderer unitRenderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Double) {
                    value = decimalFormat.format(value) + " kg"; // Añadir la unidad kg después del valor.
                }
                super.setValue(value);
            }

            // Asegurar que la alineación se mantenga centrada.
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
            }
        };
        tblProformas.getColumnModel().getColumn(6).setCellRenderer(unitRenderer); // Peso unitario.

        // Establecer el renderizador personalizado para la columna "Estado".
        tblProformas.getColumnModel().getColumn(8).setCellRenderer(new CustomTableCellRenderer());

    } // Cierre del método

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
     * Clase de la conexión a la base de datos.
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
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProformas = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(52, 98, 139));
        lblTitulo.setText("Facturas proforma");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 35, -1, -1));

        btnAnadir.setBackground(new java.awt.Color(106, 141, 162));
        btnAnadir.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        btnAnadir.setForeground(new java.awt.Color(255, 255, 255));
        btnAnadir.setText("Nueva proforma");
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

        pnlTabla.setBackground(new java.awt.Color(247, 247, 252));
        pnlTabla.setPreferredSize(new java.awt.Dimension(1000, 300));
        pnlTabla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblProformas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblProformas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblProformas.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProformas.setFocusable(false);
        tblProformas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProformas);
        if (tblProformas.getColumnModel().getColumnCount() > 0) {
            tblProformas.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            tblProformas.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            tblProformas.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            tblProformas.getColumnModel().getColumn(3).setHeaderValue("Title 4");
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

        btnImprimir.setBackground(new java.awt.Color(0, 79, 40));
        btnImprimir.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        btnImprimir.setForeground(new java.awt.Color(255, 255, 255));
        btnImprimir.setText("Imprimir");
        btnImprimir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 50, 30), 3));
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.setFocusPainted(false);
        btnImprimir.setOpaque(true);
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnImprimirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnImprimirMouseExited(evt);
            }
        });
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        getContentPane().add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 240, 130, 55));

        lblFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo6.png"))); // NOI18N
        lblFondo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 950));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Acción del botón Añadir Factura Proforma.
     *
     * @param evt Abre el diálogo para crear una factura proforma nueva.
     */
    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        Frame f = JOptionPane.getFrameForComponent(this);
        DlgProformas dlgProformas = new DlgProformas(f, true);
        dlgProformas.setIfProforma(this);
        dlgProformas.setVisible(true);


    }//GEN-LAST:event_btnAnadirActionPerformed
    /**
     * Acción del botón Editar Factura Proforma.
     *
     * @param evt Abre el diálogo para editar la factura proforma seleccionada
     * en la tabla.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editarProforma();
    }//GEN-LAST:event_btnEditarActionPerformed
    /**
     * Acción del botón Buscar.
     *
     * @param evt Busca las facturas proforma de la tabla.
     */
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed
    /**
     * Acción de buscar presionando Enter.
     *
     * @param evt Busca las facturas proforma de la tabla.
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
     * Cambia el color de fondo del botón Imprimir cuando el mouse entra en el
     * área del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseEntered
        btnImprimir.setBackground(new Color(0, 105, 43));
    }//GEN-LAST:event_btnImprimirMouseEntered
    /**
     * Restaura el color de fondo del botón Imprimir cuando el mouse entra en el
     * área del botón.
     *
     * @param evt Evento de ratón.
     */
    private void btnImprimirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseExited
        btnImprimir.setBackground(new Color(0, 79, 40));
    }//GEN-LAST:event_btnImprimirMouseExited
    /**
     * Acción de imprimir factura proforma.
     *
     * @param evt Imprime la factura proforma seleccionada en la tabla.
     */
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        imprimirProforma();
    }//GEN-LAST:event_btnImprimirActionPerformed

    /**
     * JScrollPane utilizado para mostrar una lista de proformas. JTable
     * utilizado para mostrar información sobre las proformas.
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadir;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnImprimir;
    public static javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlTabla;
    public static javax.swing.JTable tblProformas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    /**
     * Método para establecer el ID de la proforma.
     *
     * @param idProformaAux El ID de la proforma a establecer.
     */
    public void setIdProforma(int idProformaAux) {
        idProforma = idProformaAux;
    }

    /**
     * Método para cargar la tabla con todas las proformas registradas.
     */
    private void CargarTablaProformas() {
        ConexionBD conexion = new ConexionBD();

        try {
            try {
                Class.forName(conexion.driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterProformas.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.con = DriverManager.getConnection(conexion.url, conexion.usuario, conexion.clave);

            DefaultTableModel model = (DefaultTableModel) tblProformas.getModel();

            String sql = "SELECT idProforma, idCliente, fecha, numero, nombre_cliente, nif, condiciones_pago, validez, direccion, poblacion, "
                    + "c_postal, provincia, incoterm, pais, transporte, seguro, kilos, suma_subtotal, suma_iva, descuento, total, observaciones, estado FROM tb_proformas";

            try (Statement st = conexion.con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                listaProformas = new ArrayList<>();

                while (rs.next()) {

                    Proforma proforma = ServicioProforma.asignarDatosProforma(rs);
                    listaProformas.add(proforma);

                    Object[] datosFila = this.asignarDatosModelo(proforma);
                    model.addRow(datosFila);

                    // Obtener el ID de la proforma de la fila actual
                    idProforma = rs.getInt("idProforma");

                    // Obtener el índice de la fila recién agregada
                    int fila = model.getRowCount() - 1;

                    // Asociar el ID de la proforma con el índice de fila en el HashMap
                    idProformaPorFila.put(fila, idProforma);

                }

            } catch (SQLException e) {

                System.out.println("Error al llenar la tabla proformas: " + e);
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
     * Método para editar las proformas registradas.
     */
    private void editarProforma() {
        // Obtener la fila seleccionada.
        int filaSeleccionada = tblProformas.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtener el ID de la proforma de la fila seleccionada utilizando el HashMap
            idProforma = idProformaPorFila.get(filaSeleccionada);

            // Obtener los datos de la fila seleccionada.
            DefaultTableModel modelo = (DefaultTableModel) tblProformas.getModel();
            Object[] datosFila = new Object[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                datosFila[i] = modelo.getValueAt(filaSeleccionada, i);
            }

            // Pasar el ID de la proforma y los datos de la fila al diálogo de edición.
            Frame f = JOptionPane.getFrameForComponent(this);
            DlgProformas dlgProformas = new DlgProformas(f, true);
            dlgProformas.setIdProforma(idProforma);
            dlgProformas.mostrarDatos(idProforma, datosFila); // Pasa el ID de la proforma y los datos de la fila al diálogo
            dlgProformas.setIfProforma(this);
            dlgProformas.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una proforma para editarla.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
        }
    } // Cierre del método.

    /**
     * Método para imprimir la proforma seleccionada.
     */
    private void imprimirProforma() {
        // Obtener el idProforma de la fila seleccionada en la tabla
        int filaSeleccionada = tblProformas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una proforma para imprimirla.",
                    "INFORMACIÓN", JOptionPane.PLAIN_MESSAGE, icono("/img/informacion.png", 40, 40));
            return;
        }
        // Obtener el ID de la proforma de la fila seleccionada utilizando el HashMap
        idProforma = idProformaPorFila.get(filaSeleccionada);

        try {
            // Establecer la conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_boms", "root", "dugu&7Photh&");

            // Crear un mapa de parámetros para pasar al informe
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idProforma", idProforma);

            // Generar el informe con los parámetros y la conexión a la base de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport("informes/proforma.jasper", parametros, conn);

            // Mostrar el informe en una ventana
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);

            // Cerrar la conexión
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al imprimir la proforma: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    } // Cierre del método.

    /**
     * Método para buscar proformas en la tabla. Busca por número, nombre y por
     * estado.
     */
    private void buscar() {
        List<Proforma> listaFiltrada = new ArrayList<>();
        for (Proforma pr : this.listaProformas) {
            if (pr.getNumero().toLowerCase().contains(txtBuscar.getText().toLowerCase()) || pr.getNombre_cliente().toLowerCase().contains(txtBuscar.getText().toLowerCase())
                    || pr.getEstado().toLowerCase().contains(txtBuscar.getText().toLowerCase())) {
                listaFiltrada.add(pr);
            }
        }
        Object[] arrayObjetos = new Object[listaFiltrada.size()];
        DefaultTableModel model = (DefaultTableModel) tblProformas.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos
        for (int i = 0; i < listaFiltrada.size(); i++) {
            arrayObjetos[i] = this.asignarDatosModelo(listaFiltrada.get(i));
            model.addRow((Object[]) arrayObjetos[i]);
        }
    } // Cierre del método.

    /**
     * Método para asignar los datos de las proformas registradas al modelo de
     * tabla.
     */
    private Object[] asignarDatosModelo(Proforma proforma) {

        Object fila[] = new Object[9];

        fila[0] = proforma.getFecha();
        fila[1] = proforma.getNumero();
        fila[2] = proforma.getValidez();
        fila[3] = proforma.getNombre_cliente();
        fila[4] = proforma.getTransporte();
        fila[5] = proforma.getSeguro();
        fila[6] = proforma.getKilos();
        fila[7] = proforma.getTotal();
        fila[8] = proforma.getEstado();

        return fila;
    } // Cierre del método.

    /**
     * Método para recargar la tabla con todas las proformas registradas cuando
     * se añade un nueva.
     */
    public void recargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblProformas.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de volver a cargar los datos

        CargarTablaProformas();
    } // Cierre del método.

    /**
     * Devuelve el botón utilizado para editar facturas proforma.
     *
     * @return El botón utilizado para editar facturas proforma.
     */
    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Devuelve el botón utilizado para imprimir facturas proforma.
     *
     * @return El botón utilizado para imprimir facturas proforma.
     */
    public JButton getBtnProforma() {
        return btnImprimir;
    }

    /**
     * Devuelve el botón utilizado para añadir facturas proforma.
     *
     * @return El botón utilizado para añadir facturas proforma.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
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
