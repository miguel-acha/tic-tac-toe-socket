/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.command;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Miguel Angel
 */
@Getter
@Setter
public class AceptacionConexion extends Comando {
    private String nombreJugador;

    public AceptacionConexion(String nombreJugador) {
        super();
        super.setCodigoComando("0003");
        this.nombreJugador = nombreJugador;
    }

    public AceptacionConexion() {}

    @Override
    public void parsear(String mensaje) throws Exception {
        String[] s = mensaje.split(Pattern.quote("|"));
        if (s.length != 2) {
            throw new Exception("Comando inválido");
        }
        super.setCodigoComando(s[0]);
        this.nombreJugador = s[1];
    }

    @Override
    public String getComando() {
        return super.getCodigoComando() + "|" + this.nombreJugador + System.lineSeparator();
    }

    @Override
    public String toString() {
        return super.getCodigoComando() + "|" + this.nombreJugador + System.lineSeparator();
    }
}

