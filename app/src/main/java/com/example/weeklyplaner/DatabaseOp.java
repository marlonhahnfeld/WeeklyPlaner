package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import items.DatabaseLoadListener;
import items.Termin;

public class DatabaseOp {
    private static final String TAG = "Firebase";
    private static final String COLLECTION_USERS = "users";
    private static final String COLLECTION_TERMINE = "termine";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_TERMIN_NAME = "terminName";
    private static final String FIELD_BESCHREIBUNG = "description";
    private static final String FIELD_PRIO = "prio";
    private static final String FIELD_DAY = "day";
    private static final String FIELD_ID = "id";

    private static FirebaseFirestore firebaseDB;
    private boolean connected;

    public DatabaseOp() {
        firebaseDB = FirebaseFirestore.getInstance();
        connected = true;
    }

    public static void registerNewUser(String email, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put(FIELD_EMAIL, email);
        user.put(FIELD_PASSWORD, password);

        CollectionReference usersCollection = firebaseDB.collection(COLLECTION_USERS);
        usersCollection.document(email).set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User registration successful");
                    } else {
                        Log.e(TAG, "Error registering user: " + task.getException());
                    }
                });
    }

    public void checkLogInData(String email, String password, LoginCallback callback) {
        CollectionReference usersCollection = firebaseDB.collection(COLLECTION_USERS);
        Query query = usersCollection.whereEqualTo(FIELD_EMAIL, email).whereEqualTo(FIELD_PASSWORD, password);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (snapshot != null && !snapshot.isEmpty()) {
                    callback.onLoginSuccess(); // Aufruf, wenn die Anmeldeinformationen korrekt sind
                } else {
                    callback.onLoginFailure("Falsche E-Mail oder Passwort"); // Aufruf, wenn die Anmeldeinformationen falsch sind
                }
            } else {
                callback.onLoginFailure("Fehler beim Überprüfen der Anmeldeinformationen"); // Aufruf, wenn ein Fehler auftritt
                Log.e(TAG, "Error checking login data: " + task.getException());
            }
        });
    }

    public interface LoginCallback {
        void onLoginSuccess();
        void onLoginFailure(String message);
    }


    public static void saveAppointment(String email, String terminName, String description,
                                String prio, String day, int id) {

        CollectionReference appointmentsCollection = firebaseDB.collection(COLLECTION_USERS)
                .document(email).collection(COLLECTION_TERMINE);

        Map<String, Object> appointment = new HashMap<>();
        appointment.put(FIELD_TERMIN_NAME, terminName);
        appointment.put(FIELD_BESCHREIBUNG, description);
        appointment.put(FIELD_PRIO, prio);
        appointment.put(FIELD_DAY, day);
        appointment.put(FIELD_ID, id);

        appointmentsCollection.add(appointment)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Appointment saved successfully");
                    } else {
                        Log.e(TAG, "Error saving appointment: " + task.getException());
                    }
                });
    }

    public static int getMaxID() {
        CollectionReference appointmentCollection = firebaseDB.collection(COLLECTION_TERMINE);
        Query query = appointmentCollection.orderBy(FIELD_ID, Query.Direction.DESCENDING).limit(1);

        final int[] maxId = new int[1]; // Array zur Speicherung der maximalen ID

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (snapshot != null && !snapshot.isEmpty()) {
                    DocumentSnapshot document = snapshot.getDocuments().get(0);
                    Long maxIdLong = document.getLong(FIELD_ID);
                    if (maxIdLong != null) {
                        maxId[0] = maxIdLong.intValue(); // Wandelt Long in int um und speichert es im Array
                    } else {
                        // Wenn das Feld FIELD_ID null ist, setze den Wert auf 0 oder einen Standardwert
                        maxId[0] = 0; // Oder einen anderen Standardwert, falls gewünscht
                    }
                    Log.d(TAG, "Max ID: " + maxId[0]);
                } else {
                    Log.d(TAG, "No appointments found");
                }
            } else {
                Log.e(TAG, "Error getting appointments: " + task.getException());
            }
        });

        return maxId[0]; // Gibt den Wert der maximalen ID zurück
    }



    public static void loadAppointments(String email) {
        CollectionReference appointmentsCollection = firebaseDB.collection(COLLECTION_USERS)
                .document(email).collection(COLLECTION_TERMINE);
        appointmentsCollection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Termin termin = new Termin(
                                    document.getString(FIELD_TERMIN_NAME),
                                    document.getString(FIELD_BESCHREIBUNG),
                                    document.getString(FIELD_PRIO),
                                    document.getString(FIELD_DAY),
                                    document.getId()
                            );
                            getSpecificTerminliste(document.getString(FIELD_DAY)).add(termin);
                            Log.d(TAG, "Loaded appointment: " + termin);
                        }
                        SpecificDay.refresh_needed = true;
                    } else {
                        Log.e(TAG, "Error loading appointments: " + task.getException());
                    }
                });
    }

    public static void deleteAppointment(String email, String appointmentId) {
        CollectionReference appointmentsCollection = firebaseDB.collection(COLLECTION_USERS)
                .document(email).collection(COLLECTION_TERMINE);
        appointmentsCollection.document(appointmentId).delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Appointment deleted successfully");
                    } else {
                        Log.e(TAG, "Error deleting appointment: " + task.getException());
                    }
                });
    }

    public static void loadDataFromDatabase(DatabaseLoadListener listener, String email) {
        loadAppointments(email);
        listener.onDatabaseLoadComplete();
    }
}
