/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.mediadorPrueba;

/**
 *
 * @author Miguel Angel
 */
public class ChatRoom {
    public void enviarMensaje(String mensaje, Usuario usuario) {
        System.out.println(usuario.getNombre() + ": " + mensaje);
    }
}

