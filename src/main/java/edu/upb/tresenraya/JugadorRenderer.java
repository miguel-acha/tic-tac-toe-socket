package edu.upb.tresenraya;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Miguel Angel (Adaptado de rlaredo)
 */
import javax.swing.border.EmptyBorder;

// ...

public class JugadorRenderer extends JLabel implements ListCellRenderer<Jugador> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Jugador> list, Jugador jugador,
            int index, boolean isSelected, boolean cellHasFocus) {

        // Check if getSocketClient return null or SocketClient is not connected
        boolean isConnected = false;
        if (jugador.getSocketClient() != null) {
            isConnected = jugador.getSocketClient().isConectado();
        }

        // Determinar el icono dependiendo del estado de conexión del jugador
        String iconPath = "/images/" + (isConnected ? "on-line.png" : "off-line.png");
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

        // Configurar el texto que se mostrará en el JList
        setText("<html><b>" + jugador.getNombre() + "</b><br>" + jugador.getIp() + "</html>");

        // Establecer el borde para agregar padding a la izquierda
        setBorder(new EmptyBorder(0, 5, 0, 0));

        // Cambiar la apariencia si el ítem está seleccionado
        if (isSelected) {
            setBackground(new java.awt.Color(173, 216, 230)); // Fondo más claro
            setForeground(java.awt.Color.BLACK); // Color del texto cuando está seleccionado
            setBorder(new EmptyBorder(0, 5, 0, 0));  // Puedes agregar más espacio si lo deseas
        } else {
            setBackground(java.awt.Color.WHITE); // Fondo blanco cuando no está seleccionado
            setForeground(java.awt.Color.BLACK); // Color del texto por defecto
        }

        setOpaque(true); // Necesario para que el fondo de selección se vea

        return this;
    }
}
