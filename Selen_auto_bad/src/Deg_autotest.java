package javaapplication6;

import java.awt.AWTException;
import java.sql.SQLException;
import java.util.ArrayList;

//import org.openqa.selenium.firefox.FirefoxDriver;
public class Deg_autotest {

    private static ArrayList<String> free_user = new ArrayList();
    private static ArrayList raport = new ArrayList();

    public static void main(String[] args) throws SQLException, InterruptedException, AWTException {
        raport.add("*****ОТЧЕТ О ТЕСТИРОВАНИИ****");
        /*
        for (int i = 0; i < 500; i++) {
            Session session = new Session();
            try {
                session.login();
                System.out.println("vote_is_===" + Cases.vote(true));
                session.logout();
            } catch (Throwable id1) {
                System.out.println("Вышла ошибочка!:" + id1.toString());
                session.logout();
            }
        }
         */

        for (int i = 0; i < 100; i++) {
            Session session = new Session();
            session.login();
            Cases.vote(true);
           // System.out.println(Cases.policy_conf());
            session.logout();
        }

//Cases.lk();     
//Cases.blockchain_open();       
        //Cases.policy_conf();
        //Cases.biography_cand();     
        //Cases.all_blocks();
        //Cases.all_blocks_paginator();
// session.logout();

        /*
        for (int i = 0; i < raport.size(); i++) {
            System.out.println(raport.get(i));
        }
         */
    }

}
