package items;

import com.example.weeklyplaner.Add;

public class Termin {
    private String terminname;
    private String beschreibung;
    private String prio;
    private int id;
    private String tag;

    public Termin(String terminname, String beschreibung, String prio, String tag) {
        this.terminname = terminname;
        this.prio = prio;
        this.beschreibung = beschreibung;
        this.id = Add.saveCounter;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Termin{" +
                "terminname='" + terminname + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", prio='" + prio + '\'' +
                ", id=" + id +
                ", tag='" + tag + '\'' +
                '}';
    }
}
