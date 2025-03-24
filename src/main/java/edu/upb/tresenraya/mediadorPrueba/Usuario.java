/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.mediadorPrueba;

/**
 *
 * @author Miguel Angel
 */
public class Usuario {
    private String nombre;
    private ChatRoom chatRoom;

    public Usuario(String nombre, ChatRoom chatRoom) {
        this.nombre = nombre;
        this.chatRoom = chatRoom;
    }

    public String getNombre() {
        return nombre;
    }

    public void enviarMensaje(String mensaje) {
        chatRoom.enviarMensaje(mensaje, this);
    }
}

