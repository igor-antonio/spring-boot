package com.sql42.mssql.watchmen;

import com.sql42.mssql.watchmen.controller.ProducerServerController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class WatchmenApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(WatchmenApplication.class);

	@Autowired
    private ApplicationContext context;

    @Autowired
    ProducerServerController producerServerController;

    @Value("${spring.datasource.url}")
    private String url;
    
    private static String urlStatic;
 
    //@Value("${spring.datasource.url}")
    public void setUrlStatic(String url){
        WatchmenApplication.urlStatic = url;
    }

    @Value("${spring.datasource.username}")
    private String username;

    private static String usernameStatic;

    //@Value("${spring.datasource.username}")
    public void setUsernameStatic(String username){
        WatchmenApplication.usernameStatic = username;
    }
    
    @Value("${spring.datasource.password}")
    private String password;

    private static String passwordStatic;

    //@Value("${spring.datasource.password}")
    public void setPasswordStatic(String password){
        WatchmenApplication.passwordStatic = password;
    }

    /*
     * @Value("${number.of.requests}") private int numberOfRequests;
     * 
     * @Value("${number.of.threads}") private int numberOfThreads;
     */

    public static void main(String[] args) {
        


        /*
        ServerList.addServer("sql100", "127.0.0.1:14330");
        ServerList.addServer("sql101", "127.0.0.1:14331");
        ServerList.addServer("sql102", "127.0.0.1:14332");
        ServerList.addServer("sql103", "127.0.0.1:14333");
        ServerList.addServer("sql104", "127.0.0.1:14334");
        */

		SpringApplication application = new SpringApplication(WatchmenApplication.class);
        application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
        SpringApplication.run(WatchmenApplication.class, args);
  
    }

    @Override
    public void run(String... strings) throws Exception {

        LOGGER.debug("Spring Boot multithreaded has started....");

        this.setUrlStatic(url);
        this.setUsernameStatic(username);
        this.setPasswordStatic(password);

        ServerListManager.setUrl(urlStatic);
        ServerListManager.setUsername(usernameStatic);
        ServerListManager.setPassword(passwordStatic);

        //LOGGER.debug("Number of requests: " + ServerList.getQueueSize());
        LOGGER.debug("Watchmen - Number of processors [" + Runtime.getRuntime().availableProcessors() + "]");
        LOGGER.debug("Watchmen - Server List url from application.properties [" + urlStatic + "]");
        
        ServerListManager.setRunning(true);

        producerServerController.start();

        //int[] intArray = new type [size]; 

        //List<ConsumerServer> listConsumerServer = new ArrayList<ConsumerServer>();

        ConsumerServer[] consumerServer = new ConsumerServer[Runtime.getRuntime().availableProcessors()];

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            
            consumerServer[i] = new ConsumerServer();
            consumerServer[i].start();

        }

        producerServerController.join();

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            
            consumerServer[i].join();

        }

/*
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // TODO; This does not seem to work and need to research why.
        // A java program can't terminate or exit while a normal thread is executing. So, left over threads
        // waiting for a never-satisfied event can cause problems. However, if you have blocks that need
        // to be executed (like a finally block to clean up resources) then you should not set daemon threads
        // to true. It all depends on the context of your solution.
//        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, threadFactory -> {
//            Thread t = new Thread();
//            t.setDaemon(true);
//            return t;
//        });

        List<WorkerThread> tasks = new ArrayList<>(ServerList.getQueueSize());

        // --------------------------------------------------------------------------------------------------------------
        // Notes for multithreading separate worker tasks.
        // --------------------------------------------------------------------------------------------------------------
        // 1. You need to create the WorkerThread from the spring context so that bean will be properly injected with
        //    it's dependencies. You cannot use this: WorkerThread wt = new WorkerThread();
        // 2. The WorkerThread class must implement the Callable interface so that it can be executed in a separate
        //    thread.
        // 3. The WorkerThread class needs to have a scope of prototype so that it is not a singleton. The singleton
        //    scope is the default scope for a Spring bean. As a result, this class must have this annotation:
        //    @Scope("prototype")
        // --------------------------------------------------------------------------------------------------------------

        for (int i = 0; i < ServerList.getQueueSize(); i++) {
            WorkerThread wt = context.getBean(WorkerThread.class, String.valueOf(i));
            tasks.add(wt);
        }

        // The problem with this approach is that this blocks until all threads have completed. As a result, you need
        // to wait before all threads have executed before you see any of the results in the get() method.
        List<Future<String>> futures = executorService.invokeAll(tasks);

        for (Future<String> future : futures) {
            String result = future.get(600, TimeUnit.SECONDS);
            LOGGER.debug("Thread reply results [" + result + "]");
        }

        executorService.shutdown();
*/
        LOGGER.debug("Spring Boot multithreaded example has ended....");
    }
}
