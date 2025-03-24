/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.iterator;

/**
 *
 * @author Miguel Angel
 */
public class MainIterator {

    public static void main(String[] args) {
        MyCollection coleccion = new MyCollection();

        coleccion.addItem("Hola");
        coleccion.addItem(123);
        coleccion.addItem(45.67);
        coleccion.addItem(true);
        coleccion.addItem(coleccion);

        System.out.println("Iterando sobre elementos de la coleccion:");
        System.out.println(coleccion);
        /*while (coleccion.hasNext()) {
            System.out.println(coleccion.getNext());
            //coleccion.setIndex(coleccion.getIndex()+1);
        }*/

        coleccion.setIndex(coleccion.getSize());
        while (coleccion.hasPrevious()) {
            System.out.println(coleccion.getPrevious());
        }
    }
}
