package logica.observer;
import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<InterfazObserver> observadores;

    public Observable() {
        this.observadores = new ArrayList<>();
    }

    /**
     * Agrega un nuevo observador para recibir notificaciones.
     */
    public void agregarObservador(InterfazObserver observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    /**
     * Elimina un observador de la lista.
     */
    public void removerObservador(InterfazObserver observador) {observadores.remove(observador);
    }

    /**
     * Notifica a todos los observadores suscritos que ocurrió un cambio.
     * Este método será llamado por el Controlador tras registrar o modificar una reserva.
     */
    protected void notificarObservadores() {
        for (InterfazObserver obs : observadores) {
            obs.actualizar();
        }
    }
}
