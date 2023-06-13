package com.example.weeklyplaner;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import items.Termin;

public class TerminSorter {
    public static void sortAscendingByPriority(List<Termin> terminliste) {
        Collections.sort(terminliste, new Comparator<Termin>() {
            @Override
            public int compare(Termin t1, Termin t2) {
                String prio1 = t1.getPrio();
                String prio2 = t2.getPrio();

                // Extrahiere die letzten Zeichen als Vergleichswert
                char lastChar1 = prio1.charAt(prio1.length() - 1);
                char lastChar2 = prio2.charAt(prio2.length() - 1);

                return Character.compare(lastChar1, lastChar2);
            }
        });
    }

    public static void sortDescendingByPriority(List<Termin> terminliste) {
        Collections.sort(terminliste, (t1, t2) -> {
            String prio1 = t1.getPrio();
            String prio2 = t2.getPrio();

            // Extrahiere die letzten Zeichen als Vergleichswert
            char lastChar1 = prio1.charAt(prio1.length() - 1);
            char lastChar2 = prio2.charAt(prio2.length() - 1);

            return Character.compare(lastChar2, lastChar1);
        });
    }
}