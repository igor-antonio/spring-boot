package com.sql42.mssql.watchmen;

import java.util.concurrent.TimeUnit;

import com.sql42.mssql.watchmen.dao.ServernameDynDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;

public class ConsumerServer extends Thread {

    /*
    @Value("${spring.datasource.url}") private String url;
    
    @Value("${spring.datasource.username}") private String username;
    
    @Value("${spring.datasource.password}") private String password;
    */

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerServer.class);

    public void run() {

        LOGGER.debug("consumer server [started]");

        while (ServerListManager.isRunning()) {

            while (ServerListManager.isActive()) {

                if(ServerListManager.isQueueEmpty()){
                    LOGGER.debug("consumer server [nothing to do]");
                }

                while (!ServerListManager.isQueueEmpty()) {

                    String alias = ServerListManager.getAliasFromQueue();

                    LOGGER.debug("consumer server - alias from queue [" + alias + "]");

                    String servernameSource = ServerListManager.getServername(alias);

                    LOGGER.debug("consumer server - server name source from queue [" + servernameSource + "]");

                    //ServernameDynDao servernameDynDao = new ServernameDynDao(servernameSource, url, username, password);
                    ServernameDynDao servernameDynDao = new ServernameDynDao(servernameSource);

                    servernameSource = "";

                    servernameSource = servernameDynDao.insertMetric();

                    LOGGER.debug("consumer server - server name source processed [" + servernameSource + "]");
                    
                }

                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    
}
