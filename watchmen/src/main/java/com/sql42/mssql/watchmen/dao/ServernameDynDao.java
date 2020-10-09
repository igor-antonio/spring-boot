package com.sql42.mssql.watchmen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class ServernameDynDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServernameDynDao.class);

    private String servernameSource
        , urlDestination
        , username
        , password;

    public ServernameDynDao (String servernameSource
        , String urlDestination
        , String username
        , String password) {
        this.servernameSource = servernameSource;
        this.urlDestination = urlDestination;
        this.username = username;
        this.password = password;
    }

    public String insertMetric(){

        servernameSource = "jdbc:sqlserver://" + servernameSource + ";databaseName=master;applicationName=watchmen";
        LOGGER.debug("url source [" + servernameSource + "]");
        LOGGER.debug("url destination [" + urlDestination + "]");

        String resutSetServername
            , resultSetSqlServerStartTime;

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create().type(SimpleDriverDataSource.class);
        DataSourceBuilder dataDestinationBuilder = DataSourceBuilder.create();
        DataSource dataSource;
        DataSource dataDestination;
        
        dataSourceBuilder.url(servernameSource);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        
        dataDestinationBuilder.url(urlDestination);
        dataDestinationBuilder.username(username);
        dataDestinationBuilder.password(password);
  
        dataSource = dataSourceBuilder.build();
        dataDestination = dataDestinationBuilder.build();

        String sqlCommand = "SELECT @@SERVERNAME AS server_name, a.sqlserver_start_time FROM sys.dm_os_sys_info AS a;";

        Connection connectionSource = null;
        resutSetServername = "";
        resultSetSqlServerStartTime = "";

        try {

            connectionSource = dataSource.getConnection();
            PreparedStatement prepareStatement = connectionSource.prepareStatement(sqlCommand);
            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {            
                resutSetServername = resultSet.getString("server_name");
                resultSetSqlServerStartTime = resultSet.getString("sqlserver_start_time");
            }
            
            LOGGER.debug("resutSetServername [" + resutSetServername + "]");
            LOGGER.debug("resultSetSqlServerStartTime [" + resultSetSqlServerStartTime + "]");
            
            resultSet.close();
            prepareStatement.close();

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                }
                catch (SQLException e) {}
            }
        }

        sqlCommand = "INSERT INTO dbo.utb_sqlserver_start_time(server_name, sqlserver_start_time) VALUES('" + resutSetServername + "', '" + resultSetSqlServerStartTime + "');";

        Connection connectionDestination = null;

        try {

            connectionDestination = dataDestination.getConnection();
            PreparedStatement prepareStatement = connectionDestination.prepareStatement(sqlCommand);
            prepareStatement.execute();
            
            prepareStatement.close();

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                }
                catch (SQLException e) {}
            }
        }

        return resutSetServername;

    }
}