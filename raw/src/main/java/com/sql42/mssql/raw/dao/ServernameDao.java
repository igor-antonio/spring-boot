package com.sql42.mssql.raw.dao;

import com.sql42.mssql.raw.model.Servername;

import org.springframework.data.repository.CrudRepository;

public interface ServernameDao extends CrudRepository<Servername, String>{
    
}
