package com.sql42.mssql.watchmen;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerServer extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServer.class);

    public void run() {

        ServerList.setAlive(true);

        for (int i = 0; i < 10; i++) {

            ServerList.addServer("sql100", "127.0.0.1:14330");
            ServerList.addServer("sql101", "127.0.0.1:14331");
            ServerList.addServer("sql102", "127.0.0.1:14332");
            ServerList.addServer("sql103", "127.0.0.1:14333");
            ServerList.addServer("sql104", "127.0.0.1:14334");

            LOGGER.debug("Round [" + i + "]");

            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        
        ServerList.setAlive(false);

    }
    
}
