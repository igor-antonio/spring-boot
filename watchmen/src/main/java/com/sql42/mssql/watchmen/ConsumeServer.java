package com.sql42.mssql.watchmen;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumeServer extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeServer.class);

    public void run() {

        while (ServerList.isAlive()) {

            if(ServerList.isQueueEmpty()){
                LOGGER.debug("Nothing to do");
            }

            while (!ServerList.isQueueEmpty()) {

                LOGGER.debug("Alias [" + ServerList.getAliasFromQueue() + "]");

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }

            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    
}
