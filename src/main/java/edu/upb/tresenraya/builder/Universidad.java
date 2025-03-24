/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.builder;

import lombok.*;

/**
 *
 * @author Miguel Angel
 */
@Builder
@ToString 
@Setter @Getter 
public class Universidad {
    private String nombre;
    private int aulas;
    private int estudiantes;
}
