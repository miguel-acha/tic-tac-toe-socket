/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.mediadorPrueba;

/**
 *
 * @author Miguel Angel
 */
public class Main {
    public static void main(String[] args) {
        // Crear el mediador (ChatRoom)
        ChatRoom chatRoom = new ChatRoom();

        // Crear usuarios
        Usuario usuario1 = new Usuario("Miguel", chatRoom);
        Usuario usuario2 = new Usuario("Ana", chatRoom);

        // Enviar mensajes
        usuario1.enviarMensaje("Hola, como estas");
        usuario2.enviarMensaje("Hola Miguel Estoy bien");
    }
}

