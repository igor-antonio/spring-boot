package com.sql42.mssql.raw.controller;

import java.util.List;

import com.sql42.mssql.raw.ConnectionPool;
import com.sql42.mssql.raw.dao.ServernameDao;
import com.sql42.mssql.raw.dao.ServernameDynDao;
import com.sql42.mssql.raw.model.Servername;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
import org.springframework.web.bind.annotation.CrossOrigin;
*/
import org.springframework.web.servlet.ModelAndView;

@RestController
/*
@CrossOrigin("*")
*/
@RequestMapping("/sec")
public class ServernameController {

    @Autowired
    private ServernameDao servernameDao;

    @Autowired
    private Environment environment;

    @GetMapping("/servername")
	public ModelAndView servername(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("servername");
		return mav;
	}

    @GetMapping("/api/servername")
    public ResponseEntity<List<Servername>> getServername(){
        
        List<Servername> servername = (List<Servername>) servernameDao.findAll();

        return ResponseEntity.ok(servername);
    }

    @GetMapping("/servernamedyn")
	public ModelAndView servernamedyn(Authentication authentication) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("servernamedyn");
		return mav;
	}

    @GetMapping("/api/servernamedyn/{servername}")
    public ResponseEntity<List<Servername>> getServernameDyn(@PathVariable String servername){

        if (ConnectionPool.getDataSource(servername) == null) {
            
            ConnectionPool.newDataSource(
                servername
                , environment.getProperty("spring.datasource.username")
                , environment.getProperty("spring.datasource.password")
                , environment.getProperty("spring.datasource.poolName")
                , Integer.parseInt(environment.getProperty("spring.datasource.minimumIdle"))
                , Integer.parseInt(environment.getProperty("spring.datasource.maximumPoolSize")));

        }

        ServernameDynDao servernameDynDao = new ServernameDynDao(servername);

        List<Servername> listServername = servernameDynDao.findAll();

        return ResponseEntity.ok(listServername);
    }

}
