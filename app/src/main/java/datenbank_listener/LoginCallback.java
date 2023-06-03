package datenbank_listener;

public interface LoginCallback {
    void onLoginSuccess();

    void onLoginFailure(String message);
}
