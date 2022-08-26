package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.first;
import helper.Helper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.Home;
import page.Payment;

import static com.codeborne.selenide.Selenide.open;
import static helper.DataHelper.validInfo;

public class SQLTest {
    first data;
    Home home;

    @BeforeEach
    public void connect() {
        open("http://localhost:8080/");
        data = validInfo();
        home = new Home();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("APPROVED")
    public void paymentApprovedStatus() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.sendTrue();
        Helper Helper = new Helper();
        Helper.assertStatusPaymentApproved();
    }

    @Test
    @DisplayName("DECLINED")
    public void paymentDeclinedStatus() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(1, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.sendTrue();
        Helper Helper = new Helper();
        Helper.assertStatusPaymentDeclined();
    }

    @Test
    @DisplayName("APPROVED оплата в кредит")
    public void creditPaymentApprovedStatus() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.sendTrue();
        Helper Helper = new Helper();
        Helper.assertStatusCreditApproved();
    }

    @Test
    @DisplayName("DECLINED оплата в кредит")
    public void creditPaymentDeclinedStatus() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(1, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.sendTrue();
        Helper Helper = new Helper();
        Helper.assertStatusCreditDeclined();
    }
}