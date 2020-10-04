package com.sql42.mssql.raw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sql42.mssql.raw.ConnectionPool;
import com.sql42.mssql.raw.model.Servername;

public class ServernameDynDao {

/*
    private DataSource dataSource;
  
    public ServernameDynDao (DataSource dataSource) {
        this.dataSource = dataSource;
    }
*/

    private String servername;

    public ServernameDynDao (String servername) {
        this.servername = servername;
    }

    public List<Servername> findAll(){

        List<Servername> listServername = new ArrayList<Servername>();
  
        String sqlCommand = "SELECT @@SERVERNAME as server_name";
  
        DataSource dataSource = ConnectionPool.getDataSource(servername);

        Connection connection = null;
  
        try {
            connection = dataSource.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(sqlCommand);
            ResultSet resultSet = prepareStatement.executeQuery();

            Servername servername = null;

            if (resultSet.next()) {
                servername = new Servername(resultSet.getString("server_name"));
                listServername.add(servername);
            }
            
            resultSet.close();
            prepareStatement.close();

            connection.close();

            return listServername;

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {}
            }
        }
    }
}