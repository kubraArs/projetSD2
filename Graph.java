import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Graph {

    //station, liste de tt les troncons ou origine = station
    //<ALMA,SET<ALMA=origine>
    public Map<String,Set<Troncon>> mapStationOrigine;

    //SET Lignes
    public List<Ligne> listeLignes;

    public Graph(File lignes, File troncons) {
        mapStationOrigine = new HashMap<>();
        listeLignes = new ArrayList<>();
        for(Ligne l : parseLignes(lignes)){
            listeLignes.add(l);
        }
        for (Troncon t : parseTroncons(troncons)) {
            String depart = t.getDepart();

            if(!mapStationOrigine.containsKey(depart)){
                mapStationOrigine.put(depart,new HashSet<>());
            }
            mapStationOrigine.get(depart).add(t);
        }
    }
    private Set<Troncon> parseTroncons(File troncons){
        Set<Troncon> set = new HashSet<>();
        FileReader filereader;
        BufferedReader bufferedreader;
        Ligne ligneTroncon = null;

        try {
            filereader = new FileReader(troncons);
            bufferedreader = new BufferedReader(filereader);
            String currentLine;
            while ((currentLine = bufferedreader.readLine()) != null) {
                String[]tronc = currentLine.split(",");
                for(Ligne l : listeLignes){
                    if(l.getId().equals(tronc[0])){
                        ligneTroncon = l;
                    }
                }
                Troncon troncon = new Troncon(ligneTroncon, tronc[1], tronc[2],Integer.parseInt(tronc[3]));
                set.add(troncon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }
    private Set<Ligne> parseLignes(File lignes){
        Set<Ligne> set = new HashSet<>();
        FileReader filereader;
        BufferedReader bufferedreader;
        try {
            filereader = new FileReader(lignes);
            bufferedreader = new BufferedReader(filereader);
            String currentLine;
            while ((currentLine = bufferedreader.readLine()) != null) {
                String[]lignos = currentLine.split(",");
                Ligne li = new Ligne(
                        lignos[0],lignos[1],lignos[2],lignos[3],lignos[4],Integer.parseInt(lignos[5]));
                set.add(li);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    public void calculerCheminMinimisantNombreTroncons(String depart, String arrivee){ //boileau, montgomery
        //file des Stations
        Deque<String> file = new ArrayDeque<>();

        //file des stations deja utilisé
        Set<String> dejaUtilise = new HashSet<>();

        HashMap<String, Troncon> mapDernierTroncon = new HashMap<>();

        Deque<Troncon> itineraire = new ArrayDeque<>();

        //si arrivé a destination
        int dureeTransport = 0;
        int dureeTotale = 0;

        //Ajout des departs
        file.add(depart);
        dejaUtilise.add(depart);

        boolean trouve = false;
        while(!file.isEmpty()){
            //chercher le 1er de la file
            String sommet = file.poll();

            for (Troncon t: mapStationOrigine.get(sommet)) {

                //Check Si les differentes destination deja ete utilisé/noté
                if(!dejaUtilise.contains(t.getArrivee())){
                    //Ajout dans file et dejaUtilisé
                    dejaUtilise.add(t.getArrivee());
                    file.add(t.getArrivee());

                    if (t.getArrivee().equals(arrivee)){
                        Troncon troncon = t;

                        //tant qu'il n'est pas arrivé
                        while(!trouve){

                            dureeTransport += troncon.getDuree();
                            itineraire.push(troncon);

                            if(troncon.getDepart().equals(depart)){
                                trouve = true;
                            }
                            //changement de troncon par station de depart
                            troncon = mapDernierTroncon.get(troncon.getDepart());
                        }
                        //vider la file et break
                        file.clear();
                        break;
                    }else{
                        //sinon pas arrivé a destination ajout troncon a la map
                        mapDernierTroncon.put(t.getArrivee(),t);
                    }
                }
            }
        }
        if(itineraire.isEmpty())
            throw new IllegalArgumentException("Pas trouvé");
        //calculer duree totale
        String ok="";
        for(Troncon t : itineraire ){
            if (!t.getLigne().getId().equals(ok)){
                dureeTotale+=t.getDuree()+t.getLigne().getTempsDAttente();
                ok = t.getLigne().getId();
            }else{
                dureeTotale += t.getDuree();

            }
        }
        System.out.println("Troncons : " + itineraire.size());
        System.out.println("DureeTransport : " + dureeTransport);
        System.out.println("DureeTotale : " + dureeTotale);

        while (!itineraire.isEmpty()) {
            System.out.println(itineraire.pop());
        }
    }
    public void calculerCheminMinimisantTempsTransport(String depart, String arrivee) {
        //etiquette provisoire
        HashMap<String,Integer> provisoire = new HashMap<>();

        //etiquette definitive
        HashMap<String,Integer> definitif = new HashMap<>();

        //Map dernier vol arrive a cet aero
        HashMap<String, Troncon> mapDernierTroncon = new HashMap<>();

        //Ajoute le depart dans l'etiquette provisoire avec une duree de 0
        provisoire.put(depart,0);

        //Tant qu'on est pas arrivé + provisoire est pas vide
        while(!depart.equals(arrivee) && !provisoire.isEmpty()){
            //initialise minimumDist au max
            Integer minimumDist = Integer.MAX_VALUE;

            //Pour chaque aeroport dans provisoire on check la distance
            for (String a: provisoire.keySet()) {
                //si minimumDist > on le change et on met depart avec cet aeroport
                if(minimumDist > provisoire.get(a)){
                    minimumDist = provisoire.get(a);
                    depart = a;
                }
            }

            //On le met dans definitif car on a trouvé la dist minimum
            definitif.put(depart,minimumDist);

            //On peut retirer de provisoire car traité
            provisoire.remove(depart);

            //Check tout les vols depuis le depart pour compter la distance
            for (Troncon v: mapStationOrigine.get(depart)) {
                Integer distActuelle = definitif.get(depart);
                //Check si la destination est pas deja traité -> sert a rien on a deja la min dist
                if(!definitif.containsKey(v.getArrivee())){

                    //si pas encore ajouté au provisoire sinon on check la distance et on remplace dans provisoire si plus petite
                    if(provisoire.get(v.getArrivee())==null){
                        //Ajout dans provisoire et dans itineraire
                        provisoire.put(v.getArrivee(),distActuelle+v.getDuree());
                        mapDernierTroncon.put(v.getArrivee(),v);
                    }else if (distActuelle + v.getDuree() < provisoire.get(v.getArrivee())){
                        //Actualisation d'une distance plus petite trouvé et ajout dans itineraire
                        provisoire.put(v.getArrivee(), distActuelle + v.getDuree());
                        mapDernierTroncon.put(v.getArrivee(),v);
                    }
                }
            }
        }

        // Partie Print
        boolean estArrive = false;
        int troncon = 0;
        int dureeTotale = 0;
        String ok = "";
        if(!depart.equals(arrivee)){
            throw new ArithmeticException("Test");
        }else{
            int dureeTransport = 0;
            Deque<Troncon> itineraire = new ArrayDeque<>();

            //Calcul de la dist totale tant qu'on est pas arrivé ou si la map est vide -> si on veut faire BRU -> BRU : map vide
            while(!estArrive && !mapDernierTroncon.isEmpty()){
                Troncon v = mapDernierTroncon.get(depart);
                if(v != null){
                    troncon++;
                    dureeTransport += v.getDuree();

                    itineraire.push(v);
                    depart = v.getDepart();
                }else{
                    estArrive = true;
                }
            }
            for(Troncon t : itineraire ){
                if (!t.getLigne().getId().equals(ok)){
                    dureeTotale+=t.getDuree()+t.getLigne().getTempsDAttente();
                    ok = t.getLigne().getId();
                }else{
                    dureeTotale += t.getDuree();

                }
            }
            System.out.println("Troncons = " + troncon);
            System.out.println("DureeTransport = " + dureeTransport);
            System.out.println("DureeTotale = " + dureeTotale);
            while(!itineraire.isEmpty()){
                System.out.println(itineraire.pop());

            }
        }

    }
}
