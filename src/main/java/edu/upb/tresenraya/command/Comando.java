/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.command;

import edu.upb.tresenraya.server.SocketClient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Miguel Angel
 */
@Getter
@Setter
public abstract class Comando {
    private SocketClient socketclient;
    private String codigoComando;
    
    
    public abstract void parsear(String mensaje) throws Exception;
    public abstract String getComando();
}

