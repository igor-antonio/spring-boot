package com.sql42.mssql.watchmen;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class ServerListManager {

    private static Hashtable<String, String> listServername = new Hashtable<String, String>();

    private static Queue<String> queue = new LinkedList<>();
    
    private static String databaseName
        , username
        , password
        , url;
    
    private static boolean isActive, isRunning;

    public static void addServer(String alias, String servername){

        listServername.put(alias, servername);
        queue.add(alias);

    }

    public static String getAliasFromQueue() {

        return queue.remove();
        
    }

    public static String getServername(String alias) {

        String aliasToRemove = alias;

        alias = listServername.get(alias);

        listServername.remove(aliasToRemove);

        return alias;

    }

    public static int getQueueSize() {

        return queue.size();
        
    }

    public static boolean isQueueEmpty() {

        return queue.isEmpty();
        
    }

    //gets and sets

    public static String getDatabaseName() {
        return databaseName;
    }

    public static void setDatabaseName(String databaseName) {
        ServerListManager.databaseName = databaseName;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ServerListManager.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ServerListManager.password = password;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        ServerListManager.url = url;
    }

    public static boolean isActive() {
        return isActive;
    }

    public static void setActive(boolean isActive) {
        ServerListManager.isActive = isActive;
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public static void setRunning(boolean isRunning) {
        ServerListManager.isRunning = isRunning;
    }
    
}
