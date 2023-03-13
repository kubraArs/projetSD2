public class Troncon {
    private final String ligne;

    private final String depart;

    private final String arrivee;

    private final int duree;

    public Troncon(String ligne, String depart, String arrivee, int duree) {
        this.ligne = ligne;
        this.depart = depart;
        this.arrivee = arrivee;
        this.duree = duree;
    }

    public String getArrivee() {
        return arrivee;
    }

    public int getDuree() {
        return duree;
    }

    public String getDepart() {
        return depart;
    }

    public String getLigne() {
        return ligne;
    }

    @Override
    public String toString() {
        return "Troncon{" +
                "ligne='" + ligne + '\'' +
                ", depart='" + depart + '\'' +
                ", arrivee='" + arrivee + '\'' +
                ", duree=" + duree +
                '}';
    }
}

