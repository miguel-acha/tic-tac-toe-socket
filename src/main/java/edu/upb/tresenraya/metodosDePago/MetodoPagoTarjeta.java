/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.metodosDePago;

import java.math.BigDecimal;

/**
 *
 * @author Miguel Angel
 */
public class MetodoPagoTarjeta extends MetodoPago{
    public MetodoPagoTarjeta(){
        super("QR");
    }

    @Override
    public String cobrar(BigDecimal monto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
