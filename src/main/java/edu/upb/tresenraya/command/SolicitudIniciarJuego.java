/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.command;

import java.util.regex.Pattern;


/**
 *
 * @author Miguel Angel
 */

public class SolicitudIniciarJuego extends Comando {
    private String simbolo;

    public SolicitudIniciarJuego(String simbolo) {
        super();
        super.setCodigoComando("0004");
        this.simbolo = simbolo;
    }

    public SolicitudIniciarJuego() {}

    @Override
    public void parsear(String mensaje) throws Exception {
        String[] s = mensaje.split(Pattern.quote("|"));
        if (s.length != 2) {
            throw new Exception("Comando inválido");
        }
        super.setCodigoComando(s[0]);
        this.simbolo = s[1];
    }

    @Override
    public String getComando() {
        return super.getCodigoComando() + "|" + this.simbolo + System.lineSeparator();
    }

    public String getSimbolo() {
        return simbolo;
    }

    @Override
    public String toString() {
        return "0004|"+simbolo;
    }


}
