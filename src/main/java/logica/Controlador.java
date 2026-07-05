package logica;

import logica.modelos.Estudiante;
import logica.modelos.Tutor;
import logica.modelos.Reserva;
import logica.observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class Controlador extends Observable{
    private List<Estudiante> estudiantes;
    private List<Tutor> tutores;
    private List<Reserva> reservas;

    public Controlador(){
        super();

        this.estudiantes= new ArrayList<>();
        this.tutores= new ArrayList<>();
        this.reservas= new ArrayList<>();
    }

}
