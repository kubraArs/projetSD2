import java.time.LocalTime;
import java.util.Objects;

public class Ligne {
    private final String id;
    private final int numero;
    private final String origine;
    private final String destination;
    private final LocalTime tempsDAttente;
    private final Transport transport;
    enum Transport{
        METRO,TRAM,BUS
    }

    public Ligne(String id, int numero, String origine, String destination, LocalTime tempsDAttente, Transport transport) {
        this.id = id;
        this.numero = numero;
        this.origine = origine;
        this.destination = destination;
        this.tempsDAttente = tempsDAttente;
        this.transport = transport;
    }

    public String getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public String getOrigine() {
        return origine;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getTempsDAttente() {
        return tempsDAttente;
    }

    public Transport getTransport() {
        return transport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ligne nv = (Ligne) o;
        if(id == null){
            if(nv.id != null){
                return false;
            }
        }else if(!id.equals(nv.id)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        //primer = 31?
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "id='" + id + '\'' +
                ", numero=" + numero +
                ", origine='" + origine + '\'' +
                ", destination='" + destination + '\'' +
                ", tempsDAttente=" + tempsDAttente +
                ", transport=" + transport +
                '}';
    }
}
