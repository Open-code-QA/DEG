/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.awt.AWTException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cases {

    private static void ProgressBarWait() throws InterruptedException {
        new WebDriverWait(Session.driver, 1) // пока прогресс бар не откиснет
                .until(ExpectedConditions
                        .invisibilityOfElementLocated(By.xpath("/html/body/app-root/app-loading/div/mat-progress-bar")));
    }

    private static void ScrollUp() throws InterruptedException {
        sleep(1000);
        JavascriptExecutor jse = (JavascriptExecutor) Session.driver;
        jse.executeScript("window.scrollBy(0,-2500)", "");
        sleep(1000);
    }

    private static void ScrollDowm() throws InterruptedException {
        sleep(1000);
        JavascriptExecutor jse = (JavascriptExecutor) Session.driver;
        jse.executeScript("window.scrollBy(0,2500)", "");
        sleep(1000);
    }

    public static boolean lk() throws InterruptedException {
        Session.driver.findElement(By.xpath("//a[@href='/voter-account']")).click();
        try {
            WebElement kab = Session.driver.findElement(By.className("general-info__title"));
            if (kab.getText().equals("Мои данные")) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean vote(boolean wait) throws InterruptedException {
        ArrayList<WebElement> buttons = new ArrayList<WebElement>();
        ArrayList<WebElement> buttons2 = new ArrayList<WebElement>();
        ArrayList<WebElement> buttons3 = new ArrayList<WebElement>();
        WebElement but;
        try {

            sleep(2500);

            but = Session.driver.findElement(By.className("btn"));
            but.click();//клик голосовать

            but = Session.driver.findElement(By.className("btn"));
            but.click();//клик Перехода на анон стр

            sleep(1000); // ожидание открытия второго таба - временно

            ArrayList tabs2 = new ArrayList(Session.driver.getWindowHandles());//Получение списка табов
            Session.driver.switchTo().window((String) tabs2.get(1));//Переключение на второй таб в браузере 

            //driver.close();//Закрытие активного таба
            WebElement button = Session.driver.findElement(By.className("btn"));
            button.click();

            buttons = (ArrayList<WebElement>) Session.driver.findElements(By.className("btn"));
            buttons.get(0).click(); // «СГЕНЕРИРОВАТЬ»

            Actions act = new Actions(Session.driver);
            while (!buttons.get(1).isEnabled()) {
                sleep(100);
                Random r = new Random();
                int i = r.nextInt((5 - 1) + 1);
                act.moveByOffset(6, 6).build().perform();
                act.moveByOffset(i, i).build().perform();
            }

            while (!buttons.get(1).isEnabled()) {
                sleep(100);
                System.out.println(buttons.get(1).getText() + buttons.get(1).isEnabled());
            }

            buttons.get(1).click();// СОХРАНИТЬ
            ScrollDowm();
            buttons.get(2).click(); // ДАЛЕЕ НА 2 ЭТАП

            WebElement field = Session.driver.findElement(By.id("anonymous-ballot"));
            String anonymous = field.getAttribute("value");

            Session.driver.switchTo().window((String) tabs2.get(0));//Переключение на первый таб в браузере

            button = Session.driver.findElement(By.className("btn")); // «Перейти к подписи данных»
            button.click();

            field = Session.driver.findElement(By.id("anonymous-ballot"));
            field.sendKeys(anonymous); // вставили код для подписи

            sleep(1000);

            buttons2 = (ArrayList<WebElement>) Session.driver.findElements(By.className("mat-flat-button"));
            buttons2.get(1).click(); // нажали «Подписать данные»

            WebElement checkbox = Session.driver.findElement(By.className("confirm__checkbox"));
            checkbox.click();

            buttons2.clear();
            buttons2 = (ArrayList<WebElement>) Session.driver.findElements(By.className("btn")); // «Подтвердить» сохранение ключей
            buttons2.get(1).click();
            sleep(3000);
            field = Session.driver.findElement(By.id("anonymous-ballot")); // рефреш филды

            String anonymous_ok = field.getAttribute("value");

            Session.driver.switchTo().window((String) tabs2.get(1));//Переключение на второй таб в браузере 
            sleep(1000);

            buttons.clear();
            buttons = (ArrayList<WebElement>) Session.driver.findElements(By.className("btn"));

            buttons.get(5).click(); // ДАЛЕЕ НА 2 ЭТАП 

            field = Session.driver.findElement(By.id("signed-anonymous-ballot"));
            field.sendKeys(anonymous_ok); // вставили код для подписи

            buttons.clear();
            ScrollDowm();
            buttons = (ArrayList<WebElement>) Session.driver.findElements(By.className("btn"));
            buttons.get(buttons.size() - 1).click();

            Random r = new Random();
            int a = r.nextInt((3 - 1) + 1) + 1;

            WebElement voit = Session.driver.findElement(By.id("mat-checkbox-" + a));
            voit.click(); // клик по галке

            Session.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            WebElement postvoit = Session.driver.findElement(By.className("btn"));
            postvoit.click(); // клик голосовать           
            sleep(1900);
            buttons.clear();
            buttons = (ArrayList<WebElement>) Session.driver.findElements(By.className("btn"));

            buttons.get(1).click();//подтвердить
            //buttons.get(2).click();//ОТМЕНА
            WebElement tr_field = Session.driver.findElement(By.id("transaction-number"));

            while (tr_field.getText().equals("")) {
                sleep(10);
            }

            System.out.println(tr_field.getText());

        } catch (NoSuchElementException e) {
            return false;
        }

        return true;

    }

    public static String policy_conf() throws InterruptedException {
        ScrollDowm();
        try {
            WebElement block = Session.driver.findElement(By.xpath("//li[@class='page-footer__item'][3]"));//на главной странице опен
            block.click();
            ScrollUp();
            block = Session.driver.findElement(By.className("page-header__subtitle"));

//  ProgressBarWait();
            if (block.getText().equals("Политика конфиденциальности")) {
                return "Открыть раздел ПОЛИТИКА КОНФИДЕНЦИАЛЬНОСТИ: TRUE";
            } else {
                return "Открыть раздел ПОЛИТИКА КОНФИДЕНЦИАЛЬНОСТИ: FALSE";
            }
        } catch (NoSuchElementException e) {
            return "Открыть раздел ПОЛИТИКА КОНФИДЕНЦИАЛЬНОСТИ: FALSE";
        }
    }

    public static boolean biography_cand() throws InterruptedException {
        try {
            WebElement block = Session.driver.findElement(By.className("companies__wrapper"));
            block.click(); // развернули список
            WebElement block2 = Session.driver.findElement(By.xpath("//button[@class='btn btn--gray mat-flat-button mat-button-base'][1]"));
            block2.click(); // нажали кнопку
            WebElement block3 = Session.driver.findElement(By.className("ng-star-inserted")); // поиск признака в разделе
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean blockchain_open() throws InterruptedException, AWTException {
        ScrollDowm();
        try {

            WebElement block = Session.driver.findElement(By.xpath("//a[@href='/blockchain']"));//на главной странице опен
            block.click();
            WebElement block2 = Session.driver.findElement(By.className("page-header__subtitle"));

            if (block2.getText().equals("БЛОКЧЕЙН")) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public static boolean all_blocks() throws InterruptedException, AWTException {
        blockchain_open();
        ProgressBarWait();
        try {
            WebElement block = Session.driver.findElement(By.xpath("//a[@href='/blockchain/blocks']"));
            block.click();
            //  WebElement block2 = Session.driver.findElement(By.className("blocks__total"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean all_blocks_paginator() throws InterruptedException, AWTException {
        Cases.all_blocks();
        try {

            ArrayList<WebElement> listblock = new ArrayList<WebElement>();
            ArrayList<WebElement> listblock2 = new ArrayList<WebElement>();
            WebElement block = Session.driver.findElement(By.className("blocks__table")); // нашли таблицу, где хранятся блоки
            WebElement clicklist = Session.driver.findElement(By.className("mat-select-arrow-wrapper"));

            int p = 7000;
            for (int j = 0; j < p; j++) {

                if (j % 10 == 0) {
                    System.out.println(Math.round(((j + 1.0) / p) * 100) + "%");
                }
                clicklist.click(); // выпадающий список открыли

                listblock2 = (ArrayList<WebElement>) Session.driver.findElements(By.className("mat-option"));

                Random r = new Random();
                int i = r.nextInt((4 - 1) + 1);

                WebElement val = listblock2.get(i).findElement(By.className("mat-option-text"));

                while ((val.getText().equals(""))) {
                    val = listblock2.get(i).findElement(By.className("mat-option-text"));
                }

                String str = val.getText(); // получили значение поля из выпадающего списка по которому кликаем
                listblock2.get(i).click(); // клик по эллементу из выпадающего списка

                listblock = (ArrayList<WebElement>) block.findElements(By.className("mat-row")); // в таблицу нашли строки и определелили их количество   

                int c = 0;
                while (!(listblock.size() == Integer.parseInt(str))) {
                    listblock = (ArrayList<WebElement>) block.findElements(By.className("mat-row")); // в таблицу нашли строки и определелили их количество   
                    c++;
                    System.out.println(c);
                    if (c == 50000) { // число попыток и выход
                        // unset();
                        return false;
                    } // смысл в том, что если с двадцатой попытки не находит, то значит что кейс не выполнен
                }

            }
            //unset();
            return true;
        } catch (NoSuchElementException e) {
            //   unset();
            return false;

        }
    }

    /*
    public static boolean cik_result_list() throws InterruptedException {

        ArrayList<WebElement> listblock = new ArrayList<WebElement>();
        ArrayList<WebElement> rec = new ArrayList<WebElement>();

        WebElement butts = driver.findElement(By.className("cik__control"));
        listblock = (ArrayList<WebElement>) butts.findElements(By.className("control__item-wrap"));
        listblock.get(0).click();

        int c = 1;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        for (int k = 0; k < 10; k++) { //

            for (int j = 0; j < c; j++) {
                for (int i = 0; i < 100000; i++) { // самый дичайший костыль, который когда видел этот свет //клик по выпадающему списку
                    try {
                        WebElement cl = driver.findElement(By.className("mat-form-field-flex"));
                        cl.click();
                    } catch (NoSuchElementException e) {
                    } catch (ElementClickInterceptedException e1) {
                        i = 100001;
                    }
                }
                sleep(1000);
                WebElement rec_bl = Session.driver.findElement(By.className("mat-select-panel-wrap"));
                //  sleep(1000);
                rec = (ArrayList<WebElement>) rec_bl.findElements(By.className("mat-option"));
                sleep(1000);
                if (c == 1) {
                    c = rec.size();
                }

                rec.get(j).click();
                sleep(500);
                WebElement cand = driver.findElement(By.className("candidates"));
                System.out.println(cand.getText());

            }
        }
        return false;
    
}
     */
}
