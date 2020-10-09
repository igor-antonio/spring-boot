package com.sql42.mssql.watchmen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Callable;

import com.sql42.mssql.watchmen.dao.ServernameDynDao;

// We need a scope of prototype so that a new instance is created each time. The default scope is singleton so you would always get the same instance
// if we did not specify a scope of prototype.

@Component
@Scope("prototype")
public class WorkerThread implements Callable<String> { 

    @Value("${spring.datasource.url}") private String url;
    
    @Value("${spring.datasource.username}") private String username;
    
    @Value("${spring.datasource.password}") private String password;

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerThread.class);

    private String request;

    public WorkerThread(String request) {
        this.request = request;
    }

    @Override
    public String call() throws Exception {
        LOGGER.debug("Thread started [" + request + "]");
        return doWork();
    }

    private String doWork() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOGGER.error("An unexpected interrupt exception occurred!", e);
        }

        LOGGER.debug("Url [" + url + "]");

        String alias = ServerList.getAliasFromQueue();

        LOGGER.debug("Alias [" + alias + "]");

        String servernameSource = ServerList.getServername(alias);

        LOGGER.debug("ServernameSource [" + servernameSource + "]");

        ServernameDynDao servernameDynDao = new ServernameDynDao(servernameSource, url, username, password);

        servernameSource = "";

        servernameSource = servernameDynDao.insertMetric();

        LOGGER.debug("ServernameSource executed [" + servernameSource + "]");

        return "Request [" + servernameSource + "] ";
    }

}
