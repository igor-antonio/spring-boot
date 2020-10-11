package com.sql42.mssql.watchmen.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.sql42.mssql.watchmen.ServerListManager;
import com.sql42.mssql.watchmen.dao.ControlDao;
import com.sql42.mssql.watchmen.dao.ServerListDao;
import com.sql42.mssql.watchmen.model.Control;
import com.sql42.mssql.watchmen.model.ServerList;

@Service
@Scope("prototype")
public class ProducerServerController extends Thread {
    
    @Autowired
    private ControlDao controlDao;

    @Autowired
    private ServerListDao ServerListDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServerController.class);

    public void run() {

        LOGGER.debug("producer server [started]");
        
        Control control = controlDao.findByCollectorName("sqlserver_start_time");

        ServerListManager.setRunning(control.isRunning());

        ServerListManager.setActive(control.isActive());

        LOGGER.debug("producer server is active[" + ServerListManager.isActive() + "]");

        while (ServerListManager.isRunning()) {

            while (ServerListManager.isActive()) {

                List<ServerList> serverlist = (List<ServerList>) ServerListDao.findAll();

                for (ServerList serverListItem : serverlist) {
                
                    ServerListManager.addServer(serverListItem.getAlias(), serverListItem.getIpPort());
        
                }

                while (!ServerListManager.isQueueEmpty()){

                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                control = controlDao.findByCollectorName("sqlserver_start_time");

                ServerListManager.setActive(control.isActive());

                LOGGER.debug("producer server is active[" + ServerListManager.isActive() + "]");

                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            control = controlDao.findByCollectorName("sqlserver_start_time");

            ServerListManager.setRunning(control.isRunning());

            ServerListManager.setActive(control.isActive());

            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    
}
