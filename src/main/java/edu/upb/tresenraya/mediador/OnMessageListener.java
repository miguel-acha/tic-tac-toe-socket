/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.upb.tresenraya.mediador;

import edu.upb.tresenraya.command.Comando;

/**
 *
 * @author Miguel Angel
 */
public interface OnMessageListener {
    void onMessage(Comando c);
    void onClose();
}
