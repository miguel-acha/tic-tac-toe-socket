/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.singletonPrueba;

/**
 *
 * @author Miguel Angel
 */
public class Configuracion {
    // La única instancia de la clase, inicializada a null
    private static Configuracion instancia;

    // Atributos de configuración
    private String idioma;
    private String tema;

    // Constructor privado para evitar la creación de múltiples instancias
    private Configuracion() {
        // Establecemos valores por defecto
        idioma = "Español";
        tema = "Claro";
    }

    // Método público que devuelve la única instancia de la clase
    public static Configuracion obtenerInstancia() {
        if (instancia == null) {
            instancia = new Configuracion();  // Crear la instancia solo si no existe
        }
        return instancia;
    }

    // Métodos para acceder y modificar la configuración
    public String obtenerIdioma() {
        return idioma;
    }

    public void establecerIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String obtenerTema() {
        return tema;
    }

    public void establecerTema(String tema) {
        this.tema = tema;
    }
}
