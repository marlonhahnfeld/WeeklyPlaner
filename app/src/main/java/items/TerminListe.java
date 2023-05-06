package items;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

public class TerminListe extends ArrayList<Termin> {
    private ArrayList<Termin> terminListe;

    public TerminListe() {

    }

    public void addTermin(Termin termin) {
        this.add(termin);
    }

    public void removeTermin(Termin termin) {
        terminListe.remove(termin);
    }

    public ArrayList<Termin> getTerminListe() {
        return terminListe;
    }

    public Termin getLastTermin() {
        int size = terminListe.size();
        if (size > 0) {
            return terminListe.get(size - 1);
        } else {
            return null;
        }
    }

}
