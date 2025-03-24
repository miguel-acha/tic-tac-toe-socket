/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.command;

/**
 *
 * @author Miguel Angel
 */
public class RechazoJuego extends Comando {

    public RechazoJuego() {
        super();
        super.setCodigoComando("0005");
    }

    @Override
    public void parsear(String mensaje) throws Exception {
        if (!mensaje.equals("0005")) {
            throw new Exception("Comando inválido");
        }
        super.setCodigoComando(mensaje);
    }

    @Override
    public String getComando() {
        return super.getCodigoComando() + System.lineSeparator();
    }
    
    @Override
    public String toString() {
        return "0005";
    }
}
