
package javaapplication6;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Session {

    public static WebDriver driver;

    private static final String baseUrl = "https://vote-test.opencode.su/sign-in"; //test_server
    //private static final String baseUrl = "https://10.10.0.189/sign-in"; // develop_server
    // private static final String baseUrl = " https://vote.opencode.su/sign-in"; //fighting_server         
    private static final int t = 15; //время ожидания в зависимости от интернета

    //необходимо создать метод подгрузки данных из базы
    //перед логаутом записывать в базу данные о юзере
    private static String random_user() {
        int min = 200;
        int max = 9000;
        Random r = new Random();
        int z = r.nextInt(max + 1 - min) + min;
        if (z == 1111) {
            return "user" + z + 1;
        }
        return "user" + z;
    }

    private static boolean autorization(String[] u, boolean wait) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebElement password = Session.driver.findElement(By.id("password"));
        WebElement login = Session.driver.findElement(By.id("login"));
        login.sendKeys(u[0]);
        password.sendKeys(u[1]);
        login.submit();

        WebElement sms_code = Session.driver.findElement(By.name("sms"));
        sms_code.sendKeys(u[2]);
        WebElement butt = Session.driver.findElement(By.className("confirm"));
        WebElement butt2 = butt.findElement(By.className("btn"));

        sleep(1500); // Костыль!!!
        butt2.click();
        return wait;
    }

    private static void Set_up() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions option = new ChromeOptions();
        // option.addArguments("--window-size=2000,2000");
        // option.addArguments("--auto-open-devtools-for-tabs");
        //option.addArguments("--headless");
        Session.driver = new ChromeDriver(option);
        Session.driver.manage().timeouts().implicitlyWait(t, TimeUnit.SECONDS);
        Session.driver.manage().timeouts().pageLoadTimeout(t, TimeUnit.SECONDS);
        Session.driver.manage().window().maximize(); // desctop version
        Session.driver.get(baseUrl);
    }

    private static void UnSet() {
        Session.driver.close();
        Session.driver.quit();
    }

    public void login() throws InterruptedException {
        Set_up();
        String[] true_user = {random_user(), "1", "1111"};
        autorization(true_user, true);
    }

    public void logout() {
        UnSet();
    }

}
