package items;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Termin {
    private String terminname;
    private String beschreibung;
    private String prio;
    private int id;
    private String tag;
    private LocalDate datum;
    private boolean checked;

    // TODO: checked benutzen
    public Termin(String terminname, String beschreibung, String prio, LocalDate datum, int id) {

        this.terminname = terminname;
        this.prio = prio;
        this.id = id;
        this.beschreibung = beschreibung;
        //this.tag = tag;
        this.datum = datum;
    }

    public LocalDate getDatum(){
        return datum;
    }

    public int getWeek() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return datum.get(weekFields.weekOfWeekBasedYear());
    }

    public String getTerminname() {
        return terminname;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getPrio() {
        return prio;
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


    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }

    public LocalDate getActualDatum() {
        return datum;
    }

    @Override
    public String toString() {
        return "Termin{" +
                "terminname='" + terminname + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", prio='" + prio + '\'' +
                ", id=" + id +
                ", tag='" + datum + '\'' +
                '}';
    }
}


