/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.server;

import edu.upb.tresenraya.command.AceptacionConexion;
import edu.upb.tresenraya.command.AceptacionJuego;
import edu.upb.tresenraya.command.CierreConexion;
import edu.upb.tresenraya.command.Comando;
import edu.upb.tresenraya.command.JugadaExtra;
import edu.upb.tresenraya.command.Marcar;
import edu.upb.tresenraya.command.Mensaje;
import edu.upb.tresenraya.command.NuevaPartida;
import edu.upb.tresenraya.command.RechazoConexion;
import edu.upb.tresenraya.command.RechazoJuego;
import edu.upb.tresenraya.command.SolicitudConexion;
import edu.upb.tresenraya.command.SolicitudIniciarJuego;
import edu.upb.tresenraya.exception.ComandoIncorrectoException;
import edu.upb.tresenraya.mediador.Mediador;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lombok.*;

/**
 * @author rlaredo
 */
@Getter
@Setter
public class SocketClient extends Thread {

    private Socket socket;
    private String ip;
    private DataOutputStream dout;
    private BufferedReader br;
    private boolean conectado = true; // Añade este campo

    public SocketClient(Socket socket) throws IOException {
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        dout = new DataOutputStream(socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

    }

    public boolean isConectado() { // Añade este método
        return conectado && !socket.isClosed();
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = br.readLine()) != null) {
                if (message.equals("Cerrar")) {
                    Mediador.onClose();
                    return;
                } else {
                    //Mediador.sendMessage(message);
                }
                // Definir la variable Comando
                Comando c = null;
                // Determinar qué tipo de Comando crear según el mensaje recibido
                if (message.contains("0001")) {
                    c = new SolicitudConexion();
                } else if (message.contains("0002")) {
                    c = new RechazoConexion();
                } else if (message.contains("0003")) {
                    c = new AceptacionConexion();
                } else if (message.contains("0004")) {
                    c = new SolicitudIniciarJuego();
                } else if (message.contains("0005")) {
                    c = new RechazoJuego();
                } else if (message.contains("0006")) {
                    c = new AceptacionJuego();
                } else if (message.contains("0007")) {
                    c = new NuevaPartida();
                } else if (message.contains("0008")) {
                    c = new Marcar();
                } else if (message.contains("0009")) {
                    c = new CierreConexion();
                } else if (message.contains("0011")) {
                    c = new JugadaExtra();
                } else if (message.contains("0012")) {
                    c = new Mensaje();
                }
                if (c != null) {
                    try {
                        c.parsear(message);
                        c.setSocketclient(this);
                        Mediador.sendNotify(c);
                    } catch (ComandoIncorrectoException e) {
                        System.out.println("Error en la interpretación del comando: " + message);
                    } catch (Exception ex) {
                        Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (IOException e) {
            conectado = false; // Actualiza estado al desconectarse
        } finally {
            try {
                socket.close();
                conectado = false; // Actualiza estado al cerrar
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void send(byte[] buffer) {
        try {
            dout.write(buffer);
            dout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detener() {
        if (this.socket != null) {
            try {
                this.socket.close();
                this.br.close();
                this.dout.close();
            } catch (IOException e) {

            }
        }
    }

    public static void main(String[] args) throws IOException {
        SocketClient socketClient = new SocketClient(new Socket("localhost", 1825));
        socketClient.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Escriba un mensaje: ");
            socketClient.send((br.readLine() + System.lineSeparator()).getBytes());
        }
        //172.16.41.64 pablo
        //172.16.61.10 profe
    }
}
