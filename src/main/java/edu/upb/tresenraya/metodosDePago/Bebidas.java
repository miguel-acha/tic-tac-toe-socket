/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.metodosDePago;

import edu.upb.tresenraya.metodosDePago.BebidaCafe;

/**
 *
 * @author Miguel Angel
 */
public abstract class Bebidas {
    private String nombre;
    private int precio;
    private String ingredientes;
    
public Bebidas(String nombre){
    this.nombre = nombre;
}
    
public static Bebidas create(String name){
    if(name.equals("Cafe")) 
        return new BebidaCafe();
    
    if(name.equals("Jugo")) 
        return new BebidaJugo();
    
    return null;
    }

public abstract String preparar();
    
}


