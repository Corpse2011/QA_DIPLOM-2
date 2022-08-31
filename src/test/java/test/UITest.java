package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.CardModel;
import helper.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPayment;
import page.Home;
import page.Payment;

import static com.codeborne.selenide.Selenide.open;
import static helper.DataHelper.validInfo;

public class UITest {

    CardModel data;
    Home home;

    @BeforeEach
    public void prepare() {
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
    public void testValidInfo() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkAcceptedCardData();
    }

    @Test
    @DisplayName("Отправка формы DECLINED")//2
    public void testDeclinedCard() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(1, data.getMonth(),data.getYear(), data.getName(), data.getCvc());
        payment.checkDeclinedCardData();
    }

    @Test
    @DisplayName("Отправка пустой страницы")//3
    public void testEmptyInfo() {
        Payment payment = home.payment();
        payment.checkAllFormsEmpty();
    }

    @Test
    @DisplayName("Отправка формы с пустым месяцем")//4
    public void testEmptyMonth() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, null, data.getYear(), data.getName(), data.getCvc());
        payment.checkEmptyMonthField();
    }

    @Test
    @DisplayName("Отправка формы с пустым годом")//5
    public void testEmptyYear() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), null, data.getName(), data.getCvc());
        payment.checkEmptyYearField();
    }

    @Test
    @DisplayName("Отправка формы с пустым именем")//6
    public void testEmptyOwner() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), null, data.getCvc());
        payment.checkEmptyOwnerField();
    }

    @Test
    @DisplayName("Отправка формы с пустым CVC")//7
    public void testEmptyCVC() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), null);
        payment.checkEmptyCVCField();
    }

    @Test
    @DisplayName("Отправка формы с пустым номером карты")//8
    public void testEmptyNumberCard() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(3, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkWrongNumberCardField();
    }

    @Test
    @DisplayName("Форма с коротким номером карты")//9
    public void testShortCardNumber() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(2, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        payment.checkWrongNumberCardField();
    }

    @Test
    @DisplayName("Отправка формы с неправильным месяцем")//10
    public void testWrongMonth() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, DataHelper.getMonth(-2), data.getYear(), data.getName(), data.getCvc());
        payment.checkWrongMonthField();
    }

    @Test
    @DisplayName("Отправка формы с неверным годом")//11
    public void testWrongYear() {
        Payment payment = home.payment();
        payment.checkFullCardInfo(0, data.getMonth(), DataHelper.getYear(-1), data.getName(), data.getCvc());
        payment.checkWrongYearField();
    }

    @Test
    @DisplayName("Отправка имени с тире")//12
    public void testNameWithDash() {
        Payment payment = home.payment();
        String name = "Ivanov-Ivanov Ivan";
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.checkAcceptedCardData();
    }

    @Test
    @DisplayName("Отправка имени на кирилице")//13
    public void testCyrillikSymbolsInName() {
        Payment payment = home.payment();
        String name = "Иванов Иван";
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка имени с цифрами")//14
    public void testNameWithNumbers() {
        Payment payment = home.payment();
        String name = "Ivanov123";
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка имени с спец.символами")//29
    public void testNameWithSpecSymbols() {
        Payment payment = home.payment();
        String name = "@#$%^&*()~-+/*?><|";
        payment.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        payment.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка валидной формы в Кредит")//15
    public void testValidCreditData() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkAcceptedCardData();
    }

    @Test
    @DisplayName("Отправка формы DECLINED Кредит")//16
    public void testInvalidCreditData() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(1, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkDeclinedCardData();
    }

    @Test
    @DisplayName("Отправка с пустым полем Кредит")//17
    public void testEmptyCreditData() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkAllFormsEmpty();
    }

    @Test
    @DisplayName("Отправка формы с пустым месяцем Кредит")//18
    public void testEmptyMonthFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, null, data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkEmptyMonthField();
    }

    @Test
    @DisplayName("Отправка формы с пустым годом Кредит")//19
    public void testEmptyYearFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), null, data.getName(), data.getCvc());
        creditRequest.checkEmptyYearField();
    }

    @Test
    @DisplayName("Отправка формы с пустым именем Кредит")//20
    public void testEmptyOwnerFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), null, data.getCvc());
        creditRequest.checkEmptyOwnerField();
    }

    @Test
    @DisplayName("Отправка формы с пустым CVC Кредит")//21
    public void testEmptyCVCFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), data.getName(), null);
        creditRequest.checkEmptyCVCField();
    }

    @Test
    @DisplayName("Отправка формы с пустым номером Кредит")//22
    public void testEmptyCardNumberFieldInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(3, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkWrongNumberCardField();
    }

    @Test
    @DisplayName("Отправка формы с коротким номером Кредит")//23
    public void testShortCardNumberInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(2, data.getMonth(), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkWrongNumberCardField();
    }

    @Test
    @DisplayName("Отправка формы с неправильным месяцем Кредит")//24
    public void testWrongMonthInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, DataHelper.getMonth(-2), data.getYear(), data.getName(), data.getCvc());
        creditRequest.checkInvalidMonthField();
    }

    @Test
    @DisplayName("Отправка формы с неправильным годом Кредит")//25
    public void testWrongYearInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        creditRequest.checkFullCardInfo(0, data.getMonth(), DataHelper.getYear(-1), data.getName(), data.getCvc());
        creditRequest.checkInvalidYearField();
    }

    @Test
    @DisplayName("Отправка имени с тире2 Кредит")//26
    public void testNameWithDashInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        String name = "Ivanov-Ivanov Kirill";
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.checkAcceptedCardData();
    }

    @Test
    @DisplayName("Отправка имени на кирилице Кредит")//27
    public void testCyrillikNameInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        String name = "Иванов Иван";
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка имени с цифрами Кредит")//28
    public void testNameWithNumberInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        String name = "Ivanov123";
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.checkWrongOwnerField();
    }

    @Test
    @DisplayName("Отправка имени с спец.символами Кредит")//30
    public void testNameWithSpecSymbolsInCredit() {
        CreditPayment creditRequest = home.creditPayment();
        String name = "@#$%^&*()~-+/*?><|";
        creditRequest.checkFullCardInfo(0, data.getMonth(), data.getYear(), name, data.getCvc());
        creditRequest.checkWrongOwnerField();
    }
}