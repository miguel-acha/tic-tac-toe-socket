/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.metodosDePago;

/**
 *
 * @author Miguel Angel
 */
public class BebidaCafe extends Bebidas{

    public BebidaCafe() {
        super("Cafe");
    }

    @Override
    public String preparar() {
      return "Estoy preparando un cafe";   
    }
}
