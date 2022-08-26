package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.first;
import helper.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPayment;
import page.Home;
import page.Payment;

import static com.codeborne.selenide.Selenide.open;
import static helper.DataHelper.validInfo;

public class UITest {

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
    @DisplayName("Отправка валидной формы")//1
    public void inputValidInfo() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.sendTrue();
    }

    @Test
    @DisplayName("Отправка формы DECLINED")//2
    public void inputDeclinedCard() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(1, data.getMonth(),data.getYear(), data.getName(), data.getCvc());
        payment.sendFalse();
    }

    @Test
    @DisplayName("Отправка пустой страницы")//3
    public void emptyInfo() {
        home.payment();
        Payment payment = new Payment();
        payment.emptyField();
    }

    @Test
    @DisplayName("Отправка формы с пустым месяцем")//4
    public void emptyMonth() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, null, data.getYear(), data.getName(), data.getCvc());
        payment.emptyMonth();
    }

    @Test
    @DisplayName("Отправка формы с пустым годом")//5
    public void emptyYear() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), null, data.getName(), data.getCvc());
        payment.emptyYear();
    }

    @Test
    @DisplayName("Отправка формы с пустым именем")//6
    public void emptyOwner() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), null, data.getCvc());
        payment.emptyOwner();
    }

    @Test
    @DisplayName("Отправка формы с пустым CVC")//7
    public void emptyCVC() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), data.getName(), null);
        payment.emptyCVC();
    }

    @Test
    @DisplayName("Отправка формы с пустым номером карты")//8
    public void emptyNumberCard() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(3, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.wrongNumberCard();
    }

    @Test
    @DisplayName("Форма с коротким номером карты")//9
    public void shortCardNumber() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(2, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.wrongNumberCard();
    }

    @Test
    @DisplayName("Отправка формы с неправильным месяцем")//10
    public void wrongMonth() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, DataHelper.nonValidMonth(),data.getYear(), data.getName(), data.getCvc());
        payment.wrongMonth();
    }

    @Test
    @DisplayName("Отправка формы с неверным годом")//11
    public void wrongYear() {
        home.payment();
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), DataHelper.notValidYear(), data.getName(), data.getCvc());
        payment.wrongYear();
    }

    @Test
    @DisplayName("Отправка имени с тире")//12
    public void nameWithDash() {
        home.payment();
        String name = "Ivanov-Ivanov Ivan";
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.sendTrue();
    }

    @Test
    @DisplayName("Отправка имени на кирилице")//13
    public void nameCyrillik() {
        home.payment();
        String name = "Иванов Иван";
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.wrongOwner();
    }

    @Test
    @DisplayName("Отправка имени с цифрами")//14
    public void nameWithNumber() {
        home.payment();
        String name = "Ivanov123";
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.wrongOwner();
    }

    @Test
    @DisplayName("Отправка имени с спец.символами")//29
    public void nameWithKryak() {
        home.payment();
        String name = "@#$%^&*()~-+/*?><|";
        Payment payment = new Payment();
        payment.inputAllInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.wrongOwner();
    }

    @Test
    @DisplayName("Отправка валидной формы в Кредит")//15
    public void inputValidInfoCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.sendTrue();
    }

    @Test
    @DisplayName("Отправка формы DECLINED Кредит")//16
    public void inputDeclinedCardCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(1, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.sendFalse();
    }

    @Test
    @DisplayName("Отправка с пустым полем Кредит")//17
    public void emptyInfoCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.emptyField();
    }

    @Test
    @DisplayName("Отправка формы с пустым месяцем Кредит")//18
    public void emptyMonthCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, null, data.getYear(), data.getName(), data.getCvc());
        creditRequest.emptyMonth();
    }

    @Test
    @DisplayName("Отправка формы с пустым годом Кредит")//19
    public void emptyYearCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), null, data.getName(), data.getCvc());
        creditRequest.emptyYear();
    }

    @Test
    @DisplayName("Отправка формы с пустым именем Кредит")//20
    public void emptyOwnerCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), data.getYear(), null, data.getCvc());
        creditRequest.emptyOwner();
    }

    @Test
    @DisplayName("Отправка формы с пустым CVC Кредит")//21
    public void emptyCVCCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), data.getYear(), data.getName(), null);
        creditRequest.emptyCVC();
    }

    @Test
    @DisplayName("Отправка формы с пустым номером Кредит")//22
    public void emptyNumberCardCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(3, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.wrongNumberCard();
    }

    @Test
    @DisplayName("Отправка формы с коротким номером Кредит")//23
    public void shortCardNumberCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(2, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.wrongNumberCard();
    }

    @Test
    @DisplayName("Отправка формы с неправильным месяцем Кредит")//24
    public void wrongMonthCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, DataHelper.nonValidMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.wrongMonth();
    }

    @Test
    @DisplayName("Отправка формы с неправильным годом Кредит")//25
    public void wrongYearCredit() {
        home.creditPayment();
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), DataHelper.notValidYear(), data.getName(), data.getCvc());
        creditRequest.wrongYear();
    }

    @Test
    @DisplayName("Отправка имени с тире2 Кредит")//26
    public void nameWithDashCredit() {
        home.creditPayment();
        String name = "Ivanov-Ivanov Kirill";
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.sendTrue();
    }

    @Test
    @DisplayName("Отправка имени на кирилице Кредит")//27
    public void nameCyrillikCredit() {
        home.creditPayment();
        String name = "Иванов Иван";
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.wrongOwner();
    }

    @Test
    @DisplayName("Отправка имени с цифрами Кредит")//28
    public void nameWithNumberCredit() {
        home.creditPayment();
        String name = "Ivanov123";
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.wrongOwner();
    }

    @Test
    @DisplayName("Отправка имени с спец.символами Кредит")//30
    public void nameWithKryakCredit() {
        home.creditPayment();
        String name = "@#$%^&*()~-+/*?><|";
        CreditPayment creditRequest = new CreditPayment();
        creditRequest.inputAllInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.wrongOwner();
    }
}