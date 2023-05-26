package items;

/**
 * Schnittstelle für die Datenbank Lade-Listener.
 * Wird verwendet, um den Abschluss des Datenbankladevorgangs zu signalisieren.
 */
public interface DatabaseLoadListener {
    /**
     * Wird aufgerufen, wenn der Ladevorgang der Datenbank abgeschlossen ist.
     */
    void onDatabaseLoadComplete();
}
