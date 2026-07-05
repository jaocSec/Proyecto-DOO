package logica;

public class ReservaVirtual extends Reserva{
    private String linkReunion;
    private String plataforma;

    public ReservaVirtual(String idReserva, Estudiante estudiante, Tutor tutor, String materia, String horario, String plataforma){
        super(idReserva, estudiante, tutor, materia, horario);

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


