package com.sql42.mssql.watchmen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Index;

@Entity
@Table(name = "utb_server_list"
,indexes = {

    @Index(name = "uix_server_list_01",  columnList="alias", unique = true)
    , @Index(name = "uix_server_list_02",  columnList="ip_port", unique = true)

} //, indexes = {
)
public class ServerList {

    public ServerList(){

    }

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;
    
    @Column(name = "alias", length = 128, nullable = false)
	private String alias;
    
	@Column(name = "ip_port", length = 21, nullable = false)
    private String ipPort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }
    
}
