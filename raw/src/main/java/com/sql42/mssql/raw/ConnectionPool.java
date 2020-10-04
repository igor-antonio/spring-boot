package com.sql42.mssql.raw;

import java.util.Hashtable;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {

    private static Hashtable<String, HikariDataSource> connectionPool = new Hashtable<String, HikariDataSource>();

    public static void newDataSource(
        String servername
        , String username
        , String password
        , String poolName
        , int minimumIdle
        , int maximumPoolSize) {

        if (!connectionPool.containsKey(servername)) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            hikariConfig.setPoolName(poolName);
            hikariConfig.setMinimumIdle(minimumIdle);
            hikariConfig.setMaximumPoolSize(maximumPoolSize);

            hikariConfig.setJdbcUrl("jdbc:sqlserver://" + servername + ";databaseName=master;applicationName=raw_dyn");

            HikariDataSource datasource = new HikariDataSource(hikariConfig);

            connectionPool.put(servername, datasource);

        }

    }

    public static HikariDataSource getDataSource(String servername) {

        return connectionPool.get(servername);

    };
    
}
