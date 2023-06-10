package com.example.weeklyplaner;

import static com.example.weeklyplaner.Utils.getSpecificTerminliste;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import datenbank_listener.EmailExistsListener;
import datenbank_listener.LoadAppointmentsListener;
import datenbank_listener.LoginListener;
import datenbank_listener.MaxIDListener;
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

    public DatabaseOp() {
        firebaseDB = FirebaseFirestore.getInstance();
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

    public static void doesUserExist(String email, EmailExistsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        usersRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> listener.onEmailExists(task.isSuccessful()
                        && task.getResult() != null && !task.getResult().isEmpty()));
    }


    public void checkLogInData(String email, String password, LoginListener callback) {
        CollectionReference usersCollection = firebaseDB.collection(COLLECTION_USERS);
        Query query = usersCollection.whereEqualTo(FIELD_EMAIL, email)
                .whereEqualTo(FIELD_PASSWORD, password);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                callback.onLoginSuccess();
            } else {
                callback.onLoginFailure("Falsche E-Mail oder Passwort");
            }
        }).addOnFailureListener(e -> {
            callback.onLoginFailure("Fehler beim Überprüfen der Anmeldeinformationen");
            Log.e(TAG, "Error checking login data: " + e.getMessage());
        });
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

    public static void getHighestID(String email, MaxIDListener listener) {
        CollectionReference appointmentsCollection = firebaseDB.collection(COLLECTION_USERS)
                .document(email).collection(COLLECTION_TERMINE);

        appointmentsCollection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int highestID = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Long maxIdLong = document.getLong(FIELD_ID);
                            int currentID = (maxIdLong != null) ? maxIdLong.intValue() : 0;
                            Log.d(TAG, "Current ID: " + currentID + ", Highest ID: " +
                                    highestID);
                            highestID = Math.max(highestID, currentID);
                        }
                        listener.onMaxIDReceived(highestID);
                    } else {
                        Log.e(TAG, "Error getting appointments: " + task.getException());
                        listener.onMaxIDReceived(-1);
                    }
                });
    }


    public static void loadAppointments(String email, LoadAppointmentsListener listener) {
        CollectionReference appointmentsCollection = firebaseDB.collection(COLLECTION_USERS)
                .document(email).collection(COLLECTION_TERMINE);
        appointmentsCollection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            long idLong = document.getLong(FIELD_ID);
                            int id = (int) idLong;
                            Termin termin = new Termin(
                                    document.getString(FIELD_TERMIN_NAME),
                                    document.getString(FIELD_BESCHREIBUNG),
                                    document.getString(FIELD_PRIO),
                                    document.getString(FIELD_DAY),
                                    id
                            );
                            getSpecificTerminliste(document.getString(FIELD_DAY)).add(termin);
                            getSpecificTerminliste(document.getString(FIELD_DAY))
                                    .sort(Comparator.comparingInt(Termin::getId));
                            Log.d(TAG, "Loaded appointment: " + termin);
                        }
                        SpecificDay.refresh_needed = true;
                        listener.onLoadAppointments();
                    } else {
                        Log.e(TAG, "Error loading appointments: " + task.getException());
                    }
                });
    }

    // TODO: Methode überarbeiten, sodass Termin richtige entfernt wird
    public static void deleteAppointment(String email, int appointmentId) {
//        CollectionReference appointmentsCollection = firebaseDB.collection(COLLECTION_USERS)
//                .document(email).collection(COLLECTION_TERMINE);
//        appointmentsCollection.document(appointmentId).delete()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Log.d(TAG, "Appointment deleted successfully");
//                    } else {
//                        Log.e(TAG, "Error deleting appointment: " + task.getException());
//                    }
//                });
    }

}
