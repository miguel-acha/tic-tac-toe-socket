/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.factoryInterface;

/**
 *
 * @author Miguel Angel
 */
public class BebidaFactory {
    public static Bebida create(String name){
        if (name.equals("Cafe"))
            return new BebidaCafe();
        if(name.equals("Jugo"))
            return new BebidaJugo();
        return null;
    }
}
