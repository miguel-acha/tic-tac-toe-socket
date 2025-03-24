/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.command;

import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Miguel Angel
 */
@Getter
@Setter
public class JugadaExtra extends Comando{
    private String simbolo;
    private int posicionX;
    private int posicionY;

    public JugadaExtra(String simbolo, int posicionX, int posicionY) {
        super();
        super.setCodigoComando("0011");
        this.simbolo = simbolo;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public JugadaExtra() {}

    @Override
    public void parsear(String mensaje) throws Exception {
        String[] s = mensaje.split(Pattern.quote("|"));
        if (s.length != 4) {
            throw new Exception("Comando inválido");
        }
        super.setCodigoComando(s[0]);
        this.simbolo = s[1];
        this.posicionX = Integer.parseInt(s[2]);
        this.posicionY = Integer.parseInt(s[3]);
    }

    @Override
    public String getComando() {
        return super.getCodigoComando() + "|" + this.simbolo + "|" + this.posicionX + "|" + this.posicionY + System.lineSeparator();
    }

    @Override
    public String toString() {
        return "0011|"+ simbolo + "|" + posicionX + "|" + posicionY;
    }    
}
