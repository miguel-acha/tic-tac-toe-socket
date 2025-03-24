package edu.upb.tresenraya.mediador;

import edu.upb.tresenraya.command.Comando;
import edu.upb.tresenraya.Jugador;
import edu.upb.tresenraya.TresEnRayaUI;
import java.util.Iterator;

/**
 *
 * @author Miguel Angel
 */
public class Mediador {

    public static MyCollectionMediador coleccion = new MyCollectionMediador();

    public static void onClose() {
        while (coleccion.hasNext()) {
            OnMessageListener listener = coleccion.getNext();
            if (listener != null) {
                listener.onClose();
            }
        }
        coleccion.resetIndex();
    }

    public static void sendNotify(Comando c) {
        while (coleccion.hasNext()) {
            OnMessageListener listener = coleccion.getNext();
            if (listener != null) {
                java.awt.EventQueue.invokeLater(() -> listener.onMessage(c));
            }
        }
        coleccion.resetIndex();
    }

    public Mediador() {
    }

    public static void addListener(OnMessageListener messageLister) {
        coleccion.add(messageLister);
    }
}
