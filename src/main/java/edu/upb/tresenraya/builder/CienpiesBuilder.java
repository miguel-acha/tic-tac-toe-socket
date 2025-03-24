/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.builder;

/**
 *
 * @author Miguel Angel
 */
public class CienpiesBuilder {
    private Cienpies cienpies;
    
    public CienpiesBuilder(){
        this.cienpies = new Cienpies();
    }
    
    public CienpiesBuilder nombre(String nombre){
        this.cienpies.setNombre(nombre);
        return this;
    }
    
    public CienpiesBuilder especie(String especie){
        this.cienpies.setEspecie(especie);
        return this;
    }
    
    public CienpiesBuilder color(String color){
        this.cienpies.setColor(color);
        return this;
    }
    
    public CienpiesBuilder tamaño(float tamaño){
        this.cienpies.setTamaño(tamaño);
        return this;
    }
    
    public Cienpies build(){
    return this.cienpies;
    }
    
    
}
