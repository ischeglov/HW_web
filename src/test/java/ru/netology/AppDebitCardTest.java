package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppDebitCardTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/mac/chromedriver");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSubmittingDebitCardApplicationform() throws InterruptedException {
        driver.get("http://0.0.0.0:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Щеглов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79278883388");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldValidationLastFirstNameFieldDebitCardApplicationForm() throws InterruptedException {
        driver.get("http://0.0.0.0:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Shcheglov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79278883388");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();

        assertEquals(expected, actual);
    }

    @Test
    void shouldValidationEmptyLastFirstNameFieldDebitCardApplicationForm() throws InterruptedException {
        driver.get("http://0.0.0.0:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79278883388");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();

        assertEquals(expected, actual);
    }

    @Test
    void shouldValidationTelephoneFieldDebitCardApplicationForm() throws InterruptedException {
        driver.get("http://0.0.0.0:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Щеглов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+792788833883");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals(expected, actual);
    }

    @Test
    void shouldValidationEmptyTelephoneFieldDebitCardApplicationForm() throws InterruptedException{
        driver.get("http://0.0.0.0:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Щеглов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__text")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();

        assertEquals(expected, actual);
    }

    @Test
    void shouldValidationCheckboxFieldDebitCardApplicationForm() {
        driver.get("http://0.0.0.0:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Щеглов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79278883388");
        driver.findElement(By.className("button__text")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();

        assertEquals(expected, actual);
    }
}
