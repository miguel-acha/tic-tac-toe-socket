/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.metodosDePago;
import java.math.BigDecimal;

/**
 * 
 * @author Miguel Angel
 * 
 */

public abstract class MetodoPago {
    private BigDecimal monto;
    private String nombre;
    
public MetodoPago(String nombre){
    this.nombre = nombre;
}
    
public static MetodoPago create(String name){
    if(name.equals("QR")) 
        return new MetodoPagoQR();
    
    if(name.equals("Tarjeta")) 
        return new MetodoPagoTarjeta();
    
    return null;
    }

public abstract String cobrar(BigDecimal monto);
    
}


