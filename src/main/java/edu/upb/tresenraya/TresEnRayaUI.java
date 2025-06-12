/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.upb.tresenraya;

import edu.upb.tresenraya.command.AceptacionConexion;
import edu.upb.tresenraya.command.AceptacionJuego;
import edu.upb.tresenraya.command.Comando;
import edu.upb.tresenraya.command.JugadaExtra;
import edu.upb.tresenraya.command.Marcar;
import edu.upb.tresenraya.command.Mensaje;
import edu.upb.tresenraya.command.RechazoConexion;
import edu.upb.tresenraya.command.RechazoJuego;
import edu.upb.tresenraya.command.SolicitudConexion;
import edu.upb.tresenraya.command.SolicitudIniciarJuego;
import edu.upb.tresenraya.db.ConexionDb;
import edu.upb.tresenraya.mediador.Mediador;
import edu.upb.tresenraya.mediador.OnMessageListener;
import edu.upb.tresenraya.server.ServidorJuego;
import edu.upb.tresenraya.server.SocketClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.sound.sampled.Clip; // Añade este import
import java.io.IOException; // Añade este import
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author rlaredo
 */
public class TresEnRayaUI extends javax.swing.JFrame implements OnMessageListener, ActionListener {

    private ServidorJuego servidorJuego;
    private Juego juego;
    private SocketClient socketClient;
    private boolean soyInvitante;
    private char miSimbolo;
    private boolean myTurn;
    private boolean juegoIniciado;
    private boolean juegoTerminado;
    private DefaultListModel<Jugador> jugadorModel = new DefaultListModel<>();
    private boolean sonidoSilenciado = false; // Nueva variable para controlar 
    private Clip backgroundMusicClip; // Referencia al Clip de la música 
    private Connection connection;
    private String ipJugando;

    /**
     * Creates new form TresEnRayaUI
     */
    public TresEnRayaUI() {

        initComponents();

        setTitle("Multi TeR");

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image image = icon.getImage();
        int width = 32;
        int height = 32;
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setIconImage(scaledImage);

        Mediador.addListener(this);
        connection = ConexionDb.intance().getConnection(); // Obtener la conexión de ConexionDb
        jugadorModel = new DefaultListModel<>();
        jListNombres.setCellRenderer(new JugadorRenderer());
        jListNombres.setModel(jugadorModel);
        cargarJugadoresDesdeBaseDeDatos();
        //DefaultListModel<String> modeloLista = new DefaultListModel<>();
        //jListNombres.setModel(modeloLista);
        backgroundMusicClip = Audio.playMusicLoop("/sounds/fondo.wav", 0.05f, sonidoSilenciado);
        jugadorModel = new DefaultListModel<>(); // Inicializa jugadorModel 
        chatArea.setEditable(false);
        juegoTerminado = false;

        if (soyInvitante) {
            miSimbolo = Juego.JUGADOR_X;
            myTurn = true;
        } else {
            miSimbolo = Juego.JUGADOR_O;
            myTurn = false;
        }

        juego = new Juego(miSimbolo + "");
        juegoIniciado = false;

        juego = new Juego(miSimbolo + "");
        juegoIniciado = false;

        // 1. Configurar el modelo de la tabla: 3 filas x 3 columnas, y deshabilitar la edición
        DefaultTableModel model = new DefaultTableModel(3, 3) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar la edición de todas las celdas
            }
        };
        jTable2.setModel(model);

        // 2. Quitar el encabezado de la tabla y del JScrollPane
        jTable2.setTableHeader(null);
        jScrollPane3.setColumnHeaderView(null);

        // 3. Forzar que la tabla llene el viewport y asignar un tamaño preferido mayor
        jTable2.setFillsViewportHeight(true);

        // Establece el tamaño preferido del JScrollPane
        jScrollPane3.setPreferredSize(new Dimension(500, 500));
        jTable2.setPreferredScrollableViewportSize(new Dimension(500, 500));

        // 4. Establecer el espacio entre celdas (margen) a 2x2 píxeles
        jTable2.setIntercellSpacing(new Dimension(5, 5));

        // 5. Configurar la grilla y el fondo general de la tabla:
        jTable2.setGridColor(new Color(220, 220, 220));           // Las separaciones entre celdas serán negras
        jTable2.setBackground(new Color(220, 220, 220));
        jScrollPane3.getViewport().setBackground(Color.BLACK); // Fondo del JViewport negro
        jScrollPane3.setBorder(null);

        // 6. Desactivar el autoajuste para asignar tamaños manualmente
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        // 7. Crear un renderizador personalizado para las celdas con diseño único
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Si la celda está vacía
                if (value == null || value.toString().trim().isEmpty()) {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                    setText(""); // Vacío por defecto
                } else {
                    char simbolo = value.toString().charAt(0);
                    if (simbolo == Juego.JUGADOR_X) {
                        setBackground(new Color(173, 216, 230)); // Azul claro para X
                        setForeground(Color.WHITE);
                        setFont(getFont().deriveFont(36f)); // Ajustar tamaño del texto para X
                        setText("❌"); // Usar "X" en lugar de gráficos grandes
                    } else if (simbolo == Juego.JUGADOR_O) {
                        setBackground(new Color(144, 238, 144)); // Fondo verde claro
                        setForeground(Color.WHITE);
                        setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36)); // Fuente Segoe UI Emoji

                        // Mover el emoji hacia abajo
                        setText("<html><div style='margin-top: 2px;'>⭕</div></html>");

                        setHorizontalAlignment(SwingConstants.CENTER);
                        setVerticalAlignment(SwingConstants.CENTER);
                    }

                }

                // Eliminar borde amarillo al seleccionar una celda
                setBorder(null);

                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        };

        // Asignar el renderizador a cada columna de la tabla
        for (int i = 0; i < jTable2.getColumnCount(); i++) {
            jTable2.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // 8. Agregar un MouseListener para detectar clicks y realizar movimientos en el juego
        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!juegoIniciado || juegoTerminado) {
                    return;
                }
                int row = jTable2.rowAtPoint(e.getPoint());
                int col = jTable2.columnAtPoint(e.getPoint());

                if (!myTurn) {
                    return;
                }

                if (row >= 0 && col >= 0 && jTable2.getValueAt(row, col) == null && socketClient != null) {
                    char simbolo = miSimbolo;
                    if (juego.hacerMovimiento(row, col) && socketClient != null) {
                        Audio.playSound("/sounds/colocar1.wav", 0.5f, sonidoSilenciado);
                        jTable2.setValueAt(simbolo, row, col);
                        String message = "0008|" + simbolo + "|" + row + "|" + col;
                        socketClient.send((message + System.lineSeparator()).getBytes());
                        myTurn = false;

                        // Verificar si hay ganador o empate
                        char ganador = juego.chequearGanador();
                        if (ganador != Juego.VACIO) {
                            if (ganador == miSimbolo) {
                                Audio.playSound("/sounds/ganar.wav", 0.3f, sonidoSilenciado);
                                JOptionPane.showMessageDialog(null, "¡Ganaste!");
                            } else {
                                Audio.playSound("/sounds/perder.wav", 1f, sonidoSilenciado);
                                JOptionPane.showMessageDialog(null, "¡Perdiste!");
                            }

                        } else if (juego.tableroCompleto()) {
                            Audio.playSound("/sounds/empate.wav", 1.3f, sonidoSilenciado);
                            JOptionPane.showMessageDialog(null, "¡Empate!");
                        }
                    }
                } else {
                    if (socketClient == null) {
                        JOptionPane.showMessageDialog(null, "No hay conexión con el servidor.");
                    }
                }
            }
        });

        // 9. Listener para ajustar tamaño de celdas (permanece igual)
        jScrollPane3.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int totalWidth = jScrollPane3.getViewport().getWidth();
                int totalHeight = jScrollPane3.getViewport().getHeight();
                int cols = 3;
                int rows = 3;
                int spacingWidth = jTable2.getIntercellSpacing().width * (cols - 1);
                int spacingHeight = jTable2.getIntercellSpacing().height * (rows - 1);

                // Ajusta el tamaño de las celdas para que llenen la tabla sin espacios adicionales
                int cellWidth = (totalWidth - spacingWidth) / cols;
                int cellHeight = (totalHeight - spacingHeight) / rows;

                // Asegúrate de que las celdas tengan el mismo tamaño
                for (int i = 0; i < cols; i++) {
                    jTable2.getColumnModel().getColumn(i).setPreferredWidth(cellWidth);
                    jTable2.getColumnModel().getColumn(i).setMaxWidth(cellWidth);
                    jTable2.getColumnModel().getColumn(i).setMinWidth(cellWidth);
                }
                jTable2.setRowHeight(cellHeight);

                // Ajusta la altura total de la tabla
                int tableHeight = (cellHeight * rows) + (jTable2.getIntercellSpacing().height * (rows - 1));
                jTable2.setPreferredSize(new Dimension(totalWidth, tableHeight));
                jScrollPane3.revalidate(); // Forzar la revalidación del scrollPane
            }
        });

        jListNombres.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = jListNombres.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        jListNombres.setSelectedIndex(index);
                        Jugador jugadorSeleccionado = jListNombres.getModel().getElementAt(index);
                        mostrarMenuContextual(e.getComponent(), e.getX(), e.getY(), jugadorSeleccionado);
                    }
                }
            }
        });

    }

    public JLabel getLabel() {
        return this.jlMessage;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jButton2 = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btnServer = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButtonAgregarJugador = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButtonInvitacion = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButtonNuevaPartida = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jToggleButton1 = new javax.swing.JToggleButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jlMessage = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        chatInput = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListNombres = new javax.swing.JList<>();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        btnServer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnServer.setLabel("Iniciar Servidor ");
        btnServer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnServer);
        btnServer.getAccessibleContext().setAccessibleName(" Iniciar Servidor ");

        jToolBar1.add(jSeparator3);

        jButtonAgregarJugador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAgregarJugador.setLabel(" Agregar Jugador ");
        jButtonAgregarJugador.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonAgregarJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarJugadorActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAgregarJugador);
        jToolBar1.add(jSeparator2);

        jButtonInvitacion.setFocusable(false);
        jButtonInvitacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonInvitacion.setLabel(" Invitar a Jugar ");
        jButtonInvitacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonInvitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInvitacionActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonInvitacion);
        jToolBar1.add(jSeparator1);

        jButtonNuevaPartida.setFocusable(false);
        jButtonNuevaPartida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonNuevaPartida.setLabel(" Iniciar Nueva Partida ");
        jButtonNuevaPartida.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonNuevaPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevaPartidaActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonNuevaPartida);
        jToolBar1.add(jSeparator4);

        jButton1.setText(" Info ");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator5);

        jToggleButton1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 13)); // NOI18N
        jToggleButton1.setText("🔊");
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton1.setMargin(new java.awt.Insets(5, 14, 1, 14));
        jToggleButton1.setMinimumSize(new java.awt.Dimension(50, 23));
        jToggleButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton1);

        jSplitPane1.setDividerLocation(500);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"x", "x", "x"},
                {"x", "x", "x"},
                {null, "x", "x"}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        chatInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatInputActionPerformed(evt);
            }
        });

        sendButton.setBackground(new java.awt.Color(102, 255, 102));
        sendButton.setForeground(new java.awt.Color(255, 255, 255));
        sendButton.setText(">");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        chatArea.setColumns(20);
        chatArea.setRows(5);
        chatArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(chatArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(chatInput, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chatInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addGap(21, 21, 21)
                .addComponent(jlMessage)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        jListNombres.setModel(jugadorModel);
        jScrollPane1.setViewportView(jListNombres);

        jSplitPane1.setRightComponent(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServerActionPerformed
        // TODO add your handling code here:
        Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
        if (servidorJuego == null) {
            try {
                servidorJuego = new ServidorJuego();
                servidorJuego.start();
                btnServer.setText("Servidor Iniciado");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnServerActionPerformed

    private void jButtonAgregarJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarJugadorActionPerformed
        Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
        String serverIP = JOptionPane.showInputDialog(this, "Ingrese la IP del servidor:", "Solicitar Unirse", JOptionPane.PLAIN_MESSAGE);
        if (serverIP == null || serverIP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se ingresó una IP válida.");
            return;
        }
        try {
            Socket socket = new Socket(serverIP, 1825);
            SocketClient newSocketClient = new SocketClient(socket);
            newSocketClient.start();
            // Enviar el mensaje inicial para solicitar el nombre
            String message = "0001|Multi";
            newSocketClient.send((message + System.lineSeparator()).getBytes());
            // Asigna el socketClient a la variable de instancia
            socketClient = newSocketClient;
            JOptionPane.showMessageDialog(this, "Conectado al servidor " + serverIP);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar al servidor: " + e.getMessage());
        }
    }//GEN-LAST:event_jButtonAgregarJugadorActionPerformed

    private void jButtonInvitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInvitacionActionPerformed
        Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
        Jugador jugadorSeleccionado = (Jugador) jListNombres.getSelectedValue();

        if (jugadorSeleccionado != null) {
            SocketClient targetSocketClient = jugadorSeleccionado.getSocketClient();

            if (targetSocketClient != null) {
                // Mostrar opción con botones "X" y "O"
                int seleccion = JOptionPane.showOptionDialog(
                        this,
                        "Elige tu símbolo:",
                        "Seleccionar símbolo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"X", "O"},
                        "X" // "X" es la opción seleccionada por defecto
                );

                if (seleccion != JOptionPane.CLOSED_OPTION) {
                    // Asignar el símbolo según la selección
                    miSimbolo = (seleccion == JOptionPane.YES_OPTION) ? Juego.JUGADOR_X : Juego.JUGADOR_O;
                    myTurn = true; // El que invita siempre empieza

                    // Enviar la invitación usando el socketClient existente
                    String message = "0004|" + miSimbolo;
                    targetSocketClient.send((message + System.lineSeparator()).getBytes());
                    juegoIniciado = true; // Inicia el juego

                    JOptionPane.showMessageDialog(this, "Invitación enviada a: " + jugadorSeleccionado.getNombre());
                } else {
                    JOptionPane.showMessageDialog(this, "No seleccionaste un símbolo.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El jugador seleccionado no tiene un SocketClient válido.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un jugador primero.");
        }
    }//GEN-LAST:event_jButtonInvitacionActionPerformed

    private void jButtonNuevaPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevaPartidaActionPerformed
        // Enviar el mensaje 0007 al otro jugador para solicitar una nueva partida
        Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
        String message = "0007";
        if (socketClient != null) {
            socketClient.send((message + System.lineSeparator()).getBytes());
        } else {
            JOptionPane.showMessageDialog(null, "No hay conexión con el servidor.");
        }
        reiniciarTabla();
    }//GEN-LAST:event_jButtonNuevaPartidaActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
        sonidoSilenciado = !sonidoSilenciado;
        if (!sonidoSilenciado) {
            backgroundMusicClip = Audio.playMusicLoop("/sounds/fondo.wav", 0.05f, sonidoSilenciado);
        }
        if (sonidoSilenciado) {
            jToggleButton1.setText("🔇");
        } else {
            Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
            jToggleButton1.setText("🔊");
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JDialog aboutDialog = new JDialog(this, "Información adicional", true);
        aboutDialog.setSize(400, 300);
        aboutDialog.setLayout(new BorderLayout());

        JLabel aboutLabel = new JLabel(
                "<html><center><h2>Tres en Raya</h2>"
                + "<p>Versión: 1.0.0</p>"
                + "<p>Desarrollado por: Miguel Angel</p>"
                + "<p>Fecha de lanzamiento: 2025</p>"
                + "<p>Reglas: Gana el primero en alinear tres símbolos.</p>"
                + "<p>© 2025 - Todos los derechos reservados</p>"
                + "<p><a href=''>Visita mi GitHub</a> | <a href=''>Sígueme en Instagram</a></p></center></html>",
                SwingConstants.CENTER
        );

        // Hacer clickeables los enlaces
        aboutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
                if (clickPoint.y > 160) {  // Ajustar para detectar los enlaces
                    if (clickPoint.x < 200) {
                        abrirEnlace("https://github.com/miguel-acha"); // GitHub
                    } else {
                        abrirEnlace("https://instagram.com/miguel_angel_acha_boiano"); // Instagram
                    }
                }
            }
        });

        aboutDialog.add(aboutLabel, BorderLayout.CENTER);
        aboutDialog.setLocationRelativeTo(this);
        aboutDialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String mensaje = chatInput.getText().trim();
        if (!mensaje.isEmpty() && socketClient != null) {
            try {
                // Crear y enviar el comando Mensaje
                edu.upb.tresenraya.command.Mensaje comandoMensaje = new edu.upb.tresenraya.command.Mensaje(mensaje);
                socketClient.send(comandoMensaje.getComando().getBytes());

                // Mostrar el mensaje en el área de texto del chat
                chatArea.append("Yo: " + mensaje + System.lineSeparator());
                chatInput.setText(""); // Limpiar el campo de entrada
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al enviar mensaje: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void chatInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatInputActionPerformed
        // Llama al método sendButtonActionPerformed para enviar el mensaje al presionar Enter
        sendButtonActionPerformed(null);
    }//GEN-LAST:event_chatInputActionPerformed

    private void invitarUsuario(String usuario) {
        JOptionPane.showMessageDialog(null, "Invitación enviada a: " + usuario);
    }

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
            java.util.logging.Logger.getLogger(TresEnRayaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TresEnRayaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TresEnRayaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TresEnRayaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TresEnRayaUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnServer;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JTextArea chatArea;
    private javax.swing.JTextField chatInput;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAgregarJugador;
    private javax.swing.JButton jButtonInvitacion;
    private javax.swing.JButton jButtonNuevaPartida;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JList<Jugador> jListNombres;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel jlMessage;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables

    private final Set<String> posicionesMarcadas = new HashSet<>();

    @Override
    public void onMessage(Comando c) {
        /*if (juegoIniciado==true && !"0008".equals(c.getCodigoComando()))
            return;*/

        jlMessage.setText(c.toString());

        if (c.getCodigoComando().equals("0001")) {
            SolicitudConexion sol = (SolicitudConexion) c;
            String nombreJugador = sol.getNombre();
            //esta linea cambio todo
            this.socketClient = c.getSocketclient();
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Quiere aceptar la solicitud de " + nombreJugador + "?",
                    "Solicitud de conexión",
                    JOptionPane.YES_NO_OPTION
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
                AceptacionConexion comandoAceptacion = new AceptacionConexion(nombreJugador);
                Mediador.sendNotify(comandoAceptacion);
                String message = "0003|Multi";
                socketClient.send((message + System.lineSeparator()).getBytes());
            } else {
                Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
                RechazoConexion comandoRechazo = new RechazoConexion();
                Mediador.sendNotify(comandoRechazo);
            }
        }

        if (c.getCodigoComando().equals("0003")) {
            AceptacionConexion aceptacion = (AceptacionConexion) c;
            String nombre = aceptacion.getNombreJugador();
            String ip = socketClient.getIp();

            DefaultListModel<Jugador> modeloLista = (DefaultListModel<Jugador>) jListNombres.getModel();
            boolean jugadorEncontrado = false;

            // Verificar si ya existe una instancia conectada desde la misma IP
            for (int i = 0; i < modeloLista.size(); i++) {
                Jugador jugadorExistente = modeloLista.getElementAt(i);
                if (jugadorExistente.getIp().equals(ip) && jugadorExistente.getNombre().equals(nombre)) {
                    // Ya existe una instancia desde esta IP
                    if (jugadorExistente.getSocketClient() != null && jugadorExistente.getSocketClient().isConectado()) {
                        JOptionPane.showMessageDialog(this, "Ya tenes a ese amigo agregado");
                        try {
                            socketClient.getSocket().close(); // Cerrar el socket si ya existe una instancia
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        return; // Salir del método sin agregar el jugador
                    } else {
                        // Actualizar el socketClient del jugador existente
                        jugadorExistente.setSocketClient(socketClient);
                        modeloLista.setElementAt(jugadorExistente, i);
                        jugadorEncontrado = true;
                        break;
                    }
                }
            }

            if (!jugadorEncontrado) {
                // Jugador no encontrado en la lista, agregar nuevo jugador
                agregarJugadorDB(nombre, ip);  //Asegúrate de que este método haga la verificación DB
                Jugador nuevoJugador = new Jugador(nombre, socketClient.getIp(), socketClient);
                modeloLista.addElement(nuevoJugador);
            }
        }

        if (c.getCodigoComando().equals("0004")) {
            SolicitudIniciarJuego invitacion = (SolicitudIniciarJuego) c;
            String simboloInvitacion = invitacion.getSimbolo();
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea aceptar la partida? \nEl oponente propone jugar con " + simboloInvitacion + ".",
                    "Invitación de juego",
                    JOptionPane.YES_NO_OPTION
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
                AceptacionJuego comandoAceptado = new AceptacionJuego();
                Mediador.sendNotify(comandoAceptado);

                miSimbolo = (simboloInvitacion.equals(String.valueOf(Juego.JUGADOR_X))) ? Juego.JUGADOR_O : Juego.JUGADOR_X;

                String message = "0006|" + miSimbolo;
                socketClient.send((message + System.lineSeparator()).getBytes());

                juegoIniciado = true;
                juego = new Juego(miSimbolo + "");
                myTurn = false;

            } else {
                Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
                // Enviar comando 0005: Solicitud de jugar rechazado
                String message = "0005"; // Este es el mensaje de RECHAZO
                socketClient.send((message + System.lineSeparator()).getBytes());

                RechazoJuego comandoRechazado = new RechazoJuego();
                Mediador.sendNotify(comandoRechazado);
            }
        }

        if (c.getCodigoComando().equals("0006")) {
            juegoIniciado = true;
            ipJugando = socketClient.getIp();
        }

        if (c.getCodigoComando().equals("0007")) {
            if (ipJugando != null && socketClient != null && !socketClient.getIp().equals(ipJugando)) {
                System.out.println("Mensaje descartado de IP no autorizada: " + socketClient.getIp());
                return;
            }
            reiniciarTabla();
        }

        if (c.getCodigoComando().equals("0008")) {
            if (ipJugando != null && socketClient != null && !socketClient.getIp().equals(ipJugando)) {
                System.out.println("Mensaje descartado de IP no autorizada: " + socketClient.getIp());
                return;
            }
            if (juegoTerminado || myTurn) {
                return;
            }

            Audio.playSound("/sounds/colocar2.wav", 0.5f, sonidoSilenciado);
            Marcar marcar = (Marcar) c;
            String simbolo = marcar.getSimbolo();
            int posX = marcar.getPosicionX();
            int posY = marcar.getPosicionY();

            // Verificar si la celda ya está marcada
            if (jTable2.getValueAt(posX, posY) != null) {
                System.out.println("La celda ya está marcada. No se puede sobreescribir.");
                return;
            }

            // Actualiza la tabla con el movimiento recibido
            SwingUtilities.invokeLater(() -> {
                jTable2.setValueAt(simbolo, posX, posY);
            });

            juego.hacerMovimiento(posX, posY); // Actualiza el juego

            // Cambia el turno
            myTurn = true;

            // Verificar si hay ganador o empate
            char ganador = juego.chequearGanador();
            if (ganador != Juego.VACIO) {
                if (ganador == miSimbolo) {
                    SwingUtilities.invokeLater(() -> {
                        Audio.playSound("/sounds/ganar.wav", 0.3f, sonidoSilenciado);
                        JOptionPane.showMessageDialog(null, "¡Ganaste!");
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        Audio.playSound("/sounds/perder.wav", 0.3f, sonidoSilenciado);
                        JOptionPane.showMessageDialog(null, "¡Perdiste!");
                    });
                }

                juegoTerminado = true;
                juegoIniciado = false;

            } else if (juego.tableroCompleto()) {
                SwingUtilities.invokeLater(() -> {
                    Audio.playSound("/sounds/empate.wav", 1.3f, sonidoSilenciado);
                    JOptionPane.showMessageDialog(null, "¡Empate!");
                });
                juegoTerminado = true;
                juegoIniciado = false;
            }
        }

        if (c.getCodigoComando().equals("0010")) {
            this.socketClient.detener();
        }

        /*if (c.getCodigoComando().equals("0011")) {
            if (juegoTerminado) {
                return;
            }
            JugadaExtra marcar = (JugadaExtra) c;
            String simbolo = marcar.getSimbolo();
            int posX = marcar.getPosicionX();
            int posY = marcar.getPosicionY();
            // Actualiza la tabla con el movimiento recibido
            jTable2.setValueAt(simbolo, posX, posY);
            juego.hacerMovimiento(posX, posY); // Actualiza el juego
            // Cambia el turno
            myTurn = false;
            // Verificar si hay ganador o empate
            char ganador = juego.chequearGanador();
            if (ganador != Juego.VACIO) {
                JOptionPane.showMessageDialog(null, "¡Ganador: " + ganador + "!");
                juegoTerminado = true;
            } else if (juego.tableroCompleto()) {
                JOptionPane.showMessageDialog(null, "¡Empate!");
                juegoTerminado = true;
            }

        }*/
        if (c.getCodigoComando().equals("0012")) {
            Mensaje mensajeRecibido = (Mensaje) c;
            String contenido = mensajeRecibido.getContenido();

            // Muestra el mensaje en el área de texto del chat
            SwingUtilities.invokeLater(() -> {
                chatArea.append("Otro: " + contenido + System.lineSeparator());
            });
        }
    }

    @Override
    public void onClose() {
        System.out.println("UI: Se cayo el cliente");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "jButton1") {
            //System.out.println("A: " + e.getActionCommand());
            System.out.println("hola miguel");
        } else {
            System.out.println("chau miguel");
        }
    }

    public void onThemeChange(String theme) {
        System.out.println("Intentando cambiar tema a: " + theme);
        boolean temaEncontrado = false;
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                //System.out.println("Tema disponible: " + info.getName());
                if (theme.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    temaEncontrado = true;
                    break;
                }
            }
            //if (!temaEncontrado) {
            //    System.out.println("No se encontro el tema: " + theme);
            //}
            // Actualiza la UI para reflejar el nuevo Look and Feel.
            javax.swing.SwingUtilities.updateComponentTreeUI(this);
            this.invalidate();
            this.validate();
            this.repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void reiniciarTabla() {
        // Limpiar la tabla
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                model.setValueAt(null, i, j);
            }
        }
        // Reiniciar el juego
        juego.reiniciar(miSimbolo + "");
        juegoIniciado = true;
        juegoTerminado = false;
        // Asegurarse de que el turno se mantenga como debe ser
        if (miSimbolo == Juego.JUGADOR_X) {
            myTurn = true;
        } else {
            myTurn = false;
        }
    }

    public void actualizarEstadoJugador(Jugador jugador) {
        int index = jugadorModel.indexOf(jugador);
        if (index != -1) {
            jugadorModel.setElementAt(jugador, index);
        }
    }

    private void cargarJugadoresDesdeBaseDeDatos() {
        jugadorModel.clear();
        String sql = "SELECT nombre, ip FROM jugadores";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String ip = rs.getString("ip");
                Jugador jugador = new Jugador(nombre, ip, null); // Note: SocketClient is null here
                jugadorModel.addElement(jugador);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar jugadores desde la base de datos: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al cargar jugadores desde la base de datos.");
        }
    }

    private void agregarJugadorDB(String nombre, String ip) {
        try {
            String sql = "INSERT INTO jugadores(nombre, ip) VALUES(?,?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, ip);
                pstmt.executeUpdate();
                cargarJugadoresDesdeBaseDeDatos(); // Refresh the list

            } catch (SQLException e) {
                System.err.println("Error al agregar jugador a la base de datos: " + e.getMessage());
                JOptionPane.showMessageDialog(this, "Error al agregar jugador a la base de datos.");
            }
        } catch (Exception e) {
            System.err.println("Error al agregar jugador a la base de datos: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al agregar jugador a la base de datos.");
        }
    }

    private void mostrarMenuContextual(Component component, int x, int y, Jugador jugador) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemVerificacion = new JMenuItem("Verificar Conexión");
        JMenuItem menuItemEliminar = new JMenuItem("Eliminar");

        menuItemVerificacion.addActionListener(e -> verificarConexion(jugador));
        menuItemEliminar.addActionListener(e -> eliminarJugador(jugador));

        popupMenu.add(menuItemVerificacion);
        popupMenu.add(menuItemEliminar);
        popupMenu.show(component, x, y);
    }

    private void eliminarJugador(Jugador jugador) {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres eliminar a " + jugador.getNombre() + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            DefaultListModel<Jugador> modeloLista = (DefaultListModel<Jugador>) jListNombres.getModel();
            modeloLista.removeElement(jugador);
            eliminarJugadorDeBaseDeDatos(jugador);
            JOptionPane.showMessageDialog(this, "Jugador eliminado correctamente.");
        }
    }

    private void eliminarJugadorDeBaseDeDatos(Jugador jugador) {
        try {
            String sql = "DELETE FROM jugadores WHERE nombre = ? AND ip = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getIp());
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Jugador eliminado de la base de datos.");
            } else {
                System.out.println("No se encontró el jugador en la base de datos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar de la base de datos: " + e.getMessage());
        }
    }

    private void verificarConexion(Jugador jugador) {
        Audio.playSound("/sounds/click.wav", 0.5f, sonidoSilenciado);
        Jugador jugadorSeleccionado = (Jugador) jListNombres.getSelectedValue();
        if (jugadorSeleccionado != null) {
            String serverIP = jugadorSeleccionado.getIp();
            String nombre = jugadorSeleccionado.getNombre();
            if (serverIP == null || serverIP.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se ingresó una IP válida.");
                return;
            }
            try {
                Socket socket = new Socket(serverIP, 1825);
                SocketClient newSocketClient = new SocketClient(socket);
                newSocketClient.start();
                String message = "0001|Multi";
                newSocketClient.send((message + System.lineSeparator()).getBytes());
                socketClient = newSocketClient;
                JOptionPane.showMessageDialog(this, nombre + " esta conectado!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, nombre + " no esta conectado");
            }
        }
    }

    private void abrirEnlace(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje() {
        String mensaje = chatInput.getText().trim();
        if (!mensaje.isEmpty() && socketClient != null) {
            try {
                // Crear y enviar el comando Mensaje
                Mensaje comandoMensaje = new Mensaje(mensaje);
                socketClient.send(comandoMensaje.getComando().getBytes());

                // Mostrar el mensaje en el área de texto del chat
                chatArea.append("Yo: " + mensaje + System.lineSeparator());
                chatInput.setText(""); // Limpiar el campo de entrada
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al enviar mensaje: " + e.getMessage());
            }
        }
    }
}
