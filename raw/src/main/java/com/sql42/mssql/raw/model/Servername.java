package com.sql42.mssql.raw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

@Entity
@Subselect(
    "SELECT @@SERVERNAME as server_name")
@Immutable
public class Servername {
   
    @Id
    @Column(name="server_name")
    String serverName;

    public Servername(){

    }

    public Servername(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

}
