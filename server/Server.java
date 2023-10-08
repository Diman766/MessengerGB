package org.example.server;

import org.example.client.Client;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final ServerLogger serverLogger;
    private final ServerWindow serverWindow;
    List<Client> clientList;
    boolean work;

    public Server() {

        serverLogger = new ServerLogger();
        serverWindow = new ServerWindow(this);
        clientList = new ArrayList<>();
    }

    public void serverStart() {
        if (work) {
            serverWindow.showMessage("Сервер уже был запущен");
        } else {
            work = true;
            serverWindow.showMessage("Сервер запущен!");
        }
    }

    public void serverStop() {
        if (!work) {
            serverWindow.showMessage("Сервер уже был остановлен");
        } else {
            work = false;
            serverWindow.showMessage("Сервер остановлен!");
            int size = clientList.size();
            for (int i = 0; i < size; i++) {
                disconnectUser(clientList.get(0));
            }
        }
    }

    public boolean connectUser(Client client) {
        if (!work) {
            return false;
        }
        clientList.add(client);
        serverWindow.showMessage("Присоеденился : " + client.getName());
        answerAll("Присоеденился : " + client.getName());
        return true;
    }

    public String getHistory() {
        return readLog();
    }

    public void disconnectUser(Client client) {
        clientList.remove(client);
        if (client != null) {
            client.disconnectFromServer();
            serverWindow.showMessage("Отсоединился : " + client.getName());
            answerAll("Отсоединился : " + client.getName());


        }
    }

    public void sendMessage(String text) {
        if (!work) {
            return;
        }
        answerAll(text);
        serverLogger.saveInLog(text);
    }

    private void answerAll(String text) {
        for (Client client : clientList) {
            client.serverAnswer(text);
        }
    }

    private String readLog() {
        return serverLogger.getBase();
    }
}
