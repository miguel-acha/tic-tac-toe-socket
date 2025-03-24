/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.metodosDePago;

/**
 *
 * @author Miguel Angel
 */
public class BebidaJugo extends Bebidas{
    
    public BebidaJugo(){
        super("Jugo");
    }

    @Override
    public String preparar() {
        return "Estoy preparando un jugo";
    }
}
