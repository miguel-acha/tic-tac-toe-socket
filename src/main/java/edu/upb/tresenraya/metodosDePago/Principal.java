/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.metodosDePago;

/**
 *
 * @author Miguel Angel
 */
public class Principal {
    /*MetodoPago metodoPago = MetodoPago.create("QR");
    metodoPago.cobrar(new BigDecimal(10));
    
    MetodoPago tarjeta = MetodoPago.create("");
    tarjeta.cobrar(new BigDecimal(10));*/
    
    public static void main(String[] args) {
     Bebidas bebida1 = Bebidas.create("Cafe");
     Bebidas bebida2 = Bebidas.create("Jugo");
     System.out.println(bebida1.preparar()); 
     System.out.println(bebida2.preparar());  
    }
}
