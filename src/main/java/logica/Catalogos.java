package logica;
import java.util.ArrayList;
import java.util.List;

public class Catalogos {
    private List<String> materiasDisponibles = new ArrayList<>();
    private List<String> horariosDisponibles = new ArrayList<>();

    public Catalogos() {
        materiasDisponibles.add("Calculo I");
        materiasDisponibles.add("Calculo II");
        materiasDisponibles.add("Calculo III");
        materiasDisponibles.add("Física I");
        materiasDisponibles.add("Física II");

        horariosDisponibles.add("LUN: 10:00 - 12:45");
        horariosDisponibles.add("LUN: 13:00 - 14:45");
        horariosDisponibles.add("LUN: 15:00 - 16:45");

        horariosDisponibles.add("MAR: 10:00 - 12:45");
        horariosDisponibles.add("MAR: 13:00 - 14:45");
        horariosDisponibles.add("MAR: 15:00 - 16:45");

        horariosDisponibles.add("MIE: 10:00 - 12:45");
        horariosDisponibles.add("MIE: 13:00 - 14:45");
        horariosDisponibles.add("MIE: 15:00 - 16:45");

        horariosDisponibles.add("JUE: 10:00 - 12:45");
        horariosDisponibles.add("JUE: 13:00 - 14:45");
        horariosDisponibles.add("JUE: 15:00 - 16:45");

        horariosDisponibles.add("VIE: 10:00 - 12:45");
        horariosDisponibles.add("VIE: 13:00 - 14:45");
        horariosDisponibles.add("VIE: 15:00 - 16:45");
    }
    public List<String> getHorarios() {return horariosDisponibles;}
    public List<String> getMaterias() {return materiasDisponibles;}

    public void agregarMateria(String materia) { materiasDisponibles.add(materia); }
    public void removerMateria(String materia) { materiasDisponibles.remove(materia); }

}