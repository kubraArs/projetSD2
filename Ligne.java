import java.time.LocalTime;
import java.util.Objects;

public class Ligne {
    private final String id;
    private final String numero;
    private final String origine;
    private final String destination;
    private final int tempsDAttente;
    private final String transport;

    public Ligne(String id, String numero, String origine, String destination,String transport,  int tempsDAttente) {
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

    public String getNumero() {
        return numero;
    }

    public String getOrigine() {
        return origine;
    }

    public String getDestination() {
        return destination;
    }

    public int getTempsDAttente() {
        return tempsDAttente;
    }

    public String getTransport() {
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
