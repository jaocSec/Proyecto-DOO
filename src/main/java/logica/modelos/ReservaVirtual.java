package logica.modelos;

public class ReservaVirtual extends Reserva implements java.io.Serializable{
    private String linkReunion;
    private String plataforma;

    public ReservaVirtual(Estudiante estudiante, Tutor tutor, String materia, String fecha, String horario, String plataforma){
        super(estudiante, tutor, materia, fecha, horario);

        this.linkReunion= "link"; //Placeholder
        this.plataforma= plataforma;
    }

    @Override
    public String getTipo(){
        return "Virtual";
    }

    public String getLinkReunion() {
        return linkReunion;
    }

    public void setEnlaceVideollamada(String enlaceVideollamada) {
        this.linkReunion = enlaceVideollamada;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setLinkReunion(String link){
         this.linkReunion= link;
    }
}


