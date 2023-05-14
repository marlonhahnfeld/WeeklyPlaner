package items;

import android.os.Parcel;
import android.os.Parcelable;

public class Termin {

    public String terminname;
    public String beschreibung;
    public String prio;
    public int id = 0;

    public String tag;

    public Termin(String terminname, String beschreibung, String prio, int id, String Tag){
        this.terminname = terminname;
        this.prio = prio;
        this.beschreibung = beschreibung;
        this.id = id+1;
        this.tag = Tag;
    }

    public String getTerminname() {
        return terminname;
    }

    public void setTerminname(String terminname) {
        this.terminname = terminname;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getPrio() {
        return prio;
    }

    public void setPrio(String prio) {
        this.prio = prio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
