/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.singletonPrueba;

/**
 *
 * @author Miguel Angel
 */
public class Main {
    public static void main(String[] args) {
        // Obtener la única instancia de Configuracion
        Configuracion configuracion1 = Configuracion.obtenerInstancia();
        System.out.println("Idioma: " + configuracion1.obtenerIdioma());
        System.out.println("Tema: " + configuracion1.obtenerTema());

        // Cambiar la configuración
        configuracion1.establecerIdioma("Ingles");
        configuracion1.establecerTema("Oscuro");

        // Verificamos si los cambios afectan a todas las instancias
        Configuracion configuracion2 = Configuracion.obtenerInstancia();
        System.out.println("Nuevo Idioma: " + configuracion2.obtenerIdioma());
        System.out.println("Nuevo Tema: " + configuracion2.obtenerTema());

        // Verificamos si ambas instancias son la misma
        System.out.println(configuracion1 == configuracion2);  // Imprime 'true', ya que ambas son la misma instancia
    }
}

