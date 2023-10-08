package org.example.client;

public interface ClientView {
    void showMessage(String text);
    void disconnectFromServer();
    void clearLog();
}
