package items;

import java.time.LocalDate;

public class Termin {
    private String terminname;
    private String beschreibung;
    private String prio;
    private int id;
    private LocalDate date;
    private boolean marked;

    public Termin(String terminname, String beschreibung, String prio, LocalDate date, int id) {
        this.terminname = terminname;
        this.prio = prio;
        this.id = id;
        this.beschreibung = beschreibung;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
                ", tag='" + date + '\'' +
                '}';
    }
}


