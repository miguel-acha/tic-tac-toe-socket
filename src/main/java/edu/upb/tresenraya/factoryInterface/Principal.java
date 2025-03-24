/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.factoryInterface;

/**
 *
 * @author Miguel Angel
 */
public class Principal {
     public static void main(String[] args) {
     Bebida bebida1 = BebidaFactory.create("Cafe");
     Bebida bebida2 = BebidaFactory.create("Jugo");
     System.out.println(bebida1.preparar()); 
     System.out.println(bebida2.preparar());  
    }
}
