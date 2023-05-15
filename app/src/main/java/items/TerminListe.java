package items;

import java.util.ArrayList;
import java.util.List;

public class TerminListe extends ArrayList<Termin> {
    private List<Termin> termine;

    public TerminListe() {
        termine = new ArrayList<>();
    }

    public void addTermin(Termin termin) {
        this.add(termin);
    }

    public void removeTermin(Termin termin) {
        termine.remove(termin);
    }

    public List<Termin> getTerminListe() {
        return termine;
    }

    public Termin getLastTermin() {
        int size = termine.size();
        if (size > 0) {
            return termine.get(size - 1);
        } else {
            return null;
        }
    }

}
