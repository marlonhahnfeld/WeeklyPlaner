package items;

import android.os.Parcel;
import android.os.Parcelable;

public class Termin implements Parcelable {

    public String terminname;
    public String beschreibung;
    public String prio;
    private int id = 0, xml_id;

    public Termin(String terminname, String beschreibung, String prio, int id){
        this.terminname = terminname;
        this.prio = prio;
        this.beschreibung = beschreibung;
        this.id = id+1;
        this.xml_id = xml_id;
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

    // Implement the Parcelable interface
    protected Termin(Parcel in) {
        terminname = in.readString();
        beschreibung = in.readString();
        prio = in.readString();
        id = in.readInt();
        xml_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(terminname);
        dest.writeString(beschreibung);
        dest.writeString(prio);
        dest.writeInt(id);
        dest.writeInt(xml_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Termin> CREATOR = new Creator<Termin>() {
        @Override
        public Termin createFromParcel(Parcel in) {
            return new Termin(in);
        }

        @Override
        public Termin[] newArray(int size) {
            return new Termin[size];
        }
    };
}
