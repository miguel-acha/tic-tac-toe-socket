/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.builder;

/**
 *
 * @author Miguel Angel
 */
public class MainBuilder {
    public static void main(String[] args) {
     Cienpies cienp = Cienpies.builder().nombre("Cienmil").especie("HOla").build();  
     
     Universidad u = Universidad.builder().nombre("UPB").aulas(1).build();
     System.out.println(u);
     Universidad u2 = Universidad.builder().nombre("UPSA").aulas(1).estudiantes(100).build();
     System.out.println(u2);
     
    }
}
