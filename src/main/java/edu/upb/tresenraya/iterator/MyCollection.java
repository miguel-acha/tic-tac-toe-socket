/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.iterator;

import java.util.ArrayList;
import lombok.*;

/**
 *
 * @author Miguel Angel
 */
@Getter
@Setter
public class MyCollection<T> implements Iterator {
    
    private int index;
    private ArrayList<T> lista;

    public MyCollection() {
        this.index = 0;
        this.lista = new ArrayList<>();
    }

    @Override
    public boolean hasNext() {
        return index < lista.size();
    }

    @Override
    public T getNext() {
        if (hasNext())
            return lista.get(index++);
        return null; 
    }
    
    public boolean hasPrevious() {
        return index > 0; 
    }
    
    public T getPrevious() {
        if (hasPrevious()) {
            return lista.get(--index);
        }
        return null; 
    }
    
    public void addItem(T item) {
        lista.add(item);
    }
    
    public int getSize() {
        return lista.size();
    }
}
