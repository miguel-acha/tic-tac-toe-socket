/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya;

import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Angel
 */

public class Cobro {
    public static void realizarCobroQR(String monto) {
        System.out.println("Procesando pago por QR de " + monto + "...");
        JOptionPane.showMessageDialog(null, "Pago por QR de " + monto + " procesado.");
    }
    
    public static void realizarCobroTarjeta(String numero, String nombre, String expiracion, String cvv, String monto) {
        System.out.println("Procesando pago con tarjeta...");
        JOptionPane.showMessageDialog(null, "Pago con tarjeta procesado.\nNúmero: " + numero +
                "\nNombre: " + nombre + "\nExpiración: " + expiracion + "\nCVV: " + cvv + "\nMonto: " + monto);
    }
}


