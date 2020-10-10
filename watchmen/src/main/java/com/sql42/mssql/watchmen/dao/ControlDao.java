package com.sql42.mssql.watchmen.dao;

import com.sql42.mssql.watchmen.model.Control;

import org.springframework.data.repository.CrudRepository;

public interface ControlDao extends CrudRepository<Control, Integer>{

    public Control findByCollectorName(String collectorName);
    
}
