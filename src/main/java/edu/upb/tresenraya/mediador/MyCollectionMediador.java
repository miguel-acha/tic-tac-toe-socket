/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upb.tresenraya.mediador;

import edu.upb.tresenraya.iterator.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

/**
 *
 * @author Miguel Angel
 * @param <T>
 */
@Getter
@Setter
public class MyCollectionMediador {
    private List<OnMessageListener> lista = new ArrayList<>();
    private int index = 0;

    public void add(OnMessageListener listener) {
        lista.add(listener);
    }

    public boolean hasNext() {
        return index < lista.size();
    }

    public OnMessageListener getNext() {
        if (hasNext()) {
            return lista.get(index++);
        }
        return null;
    }

    public void resetIndex() {
        index = 0;
    }
}

    

