package org.example.server;

public interface ServerLog {
    default String getBase() {
        return null;
    }

    void saveInLog(String text);
}
