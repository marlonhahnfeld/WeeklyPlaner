package items;

public class Termin {
    private String terminname;
    private String beschreibung;
    private String prio;
    private String id;
    private String tag;
    private boolean marked;

    public Termin(String terminname, String beschreibung, String prio, String tag, String id) {
        this.terminname = terminname;
        this.prio = prio;
        this.id = id;
        this.beschreibung = beschreibung;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public boolean isChecked() {
        return marked;
    }

    public void setChecked(boolean checked) {
        this.marked = checked;
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

