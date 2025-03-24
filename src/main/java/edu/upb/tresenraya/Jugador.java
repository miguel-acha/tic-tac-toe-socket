package edu.upb.tresenraya;

import edu.upb.tresenraya.server.SocketClient;
import lombok.*;

/**
 *
 * @author Miguel Angel
 */
// Clase para representar a un jugador
@Setter
@Getter
public class Jugador {

    private final String nombre;
    private final String ip;
    private SocketClient socketClient;

    public Jugador(String nombre, String ip, SocketClient socketClient) {
        this.nombre = nombre;
        this.ip = ip;
        this.socketClient = socketClient;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIp() {
        return ip;
    }

    public SocketClient getSocketClient() {
        return socketClient;
    }
}
