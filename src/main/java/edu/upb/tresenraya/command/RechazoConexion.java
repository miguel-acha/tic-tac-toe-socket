/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.command;

import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Angel
 */
public class RechazoConexion extends Comando {

    public RechazoConexion() {
        super();
        super.setCodigoComando("0002");
    }

    @Override
    public void parsear(String mensaje) throws Exception {
        if (!mensaje.equals("0002")) {
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
        return super.getCodigoComando() + System.lineSeparator();
    }
}