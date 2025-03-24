/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.command;

import edu.upb.tresenraya.mediador.Mediador;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import lombok.Getter;

/**
 *
 * @author Miguel Angel
 */

@Getter
public class SolicitudConexion extends Comando{
    private String nombre;
    
    public SolicitudConexion(String nombre){
        super();
        super.setCodigoComando("0001");
        this.nombre = nombre;
    }
    
    public SolicitudConexion(){}

    @Override
    public void parsear(String mensaje)throws Exception{
        String [] s = mensaje.split(Pattern.quote("|"));
        if(s.length != 2){
            throw new Exception("Comando inválido");
        }
        super.setCodigoComando(s[0]);
        this.nombre = s[1];
    }

    @Override
    public String getComando() {
        return super.getCodigoComando()+ "|" + this.nombre + System.lineSeparator();
    }

    @Override
    public String toString() {
        return super.getCodigoComando()+ "|" + this.nombre + System.lineSeparator();
    }
    
}
