package datenbank_listener;

public interface LoginListener {
    void onLoginSuccess();

    void onLoginFailure(String message);
}
