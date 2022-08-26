package page;

import com.codeborne.selenide.SelenideElement;
import helper.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class Payment {
    private SelenideElement inputNumberCard = $x("//input[@placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement inputMonth = $x("//input[@placeholder=\"08\"]");
    private SelenideElement inputYear = $x("//input[@placeholder=\"22\"]");
    private SelenideElement inputOwner = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[1]/span/span/span[2]/input");
    private SelenideElement inputCVC = $x("//input[@placeholder=\"999\"]");
    private SelenideElement badge = $x("//button[@role=\"button\"]//*[text()=\"Продолжить\"]");
    private SelenideElement errNumberCard = $x("//*[@id=\"root\"]/div/form/fieldset/div[1]/span/span/span[3]");
    private SelenideElement errMonth = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[1]/span/span/span[3]");
    private SelenideElement errYear = $x("//*[@id=\"root\"]/div/form/fieldset/div[2]/span/span[2]/span/span/span[3]");
    private SelenideElement errOwner = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[1]/span/span/span[3]");
    private SelenideElement errCVC = $x("//*[@id=\"root\"]/div/form/fieldset/div[3]/span/span[2]/span/span/span[3]");
    private SelenideElement badgeSend = $x("//*[@id=\"root\"]/div/form/fieldset/div[4]/button/span/span/span");
    private SelenideElement reportWaitSend = $x("//*[text()=\"Отправляем запрос в Банк...\"]");
    private SelenideElement reportApproved = $x("//div[@class = \"notification notification_visible notification_status_ok notification_has-closer notification_stick-to_right notification_theme_alfa-on-white\"]//div[@class = \"notification__content\"]");
    private SelenideElement snapCloseReportApproved = $x("//*[@id=\"root\"]/div/div[2]/button/span/span");
    private SelenideElement reportDeclined = $x("//*[@id=\"root\"]/div/div[3]/div[3]");
    private SelenideElement snapCloseDeclined = $x("//*[@id=\"root\"]/div/div[3]/button");

    public void inputAllInfo(int numberCard, String month, String year, String ownerName, String cvc) {
        inputNumberCard.val(DataHelper.numberCards(numberCard));
        inputMonth.val(String.valueOf(month));
        inputYear.val(String.valueOf(year));
        inputOwner.val(ownerName);
        inputCVC.val(String.valueOf(cvc));
        badge.click();
    }

    public void sendTrue() {
        errNumberCard.should(hidden);
        errMonth.should(hidden);
        errYear.should(hidden);
        errOwner.should(hidden);
        errCVC.should(hidden);
        badgeSend.should(visible);
        reportWaitSend.should(text("Отправляем запрос в Банк..."));
        reportApproved.should(text("Операция одобрена Банком."), Duration.ofSeconds(15));
        snapCloseReportApproved.click();
    }

    public void sendFalse() {
        errNumberCard.should(hidden);
        errMonth.should(hidden);
        errYear.should(hidden);
        errOwner.should(hidden);
        errCVC.should(hidden);
        badgeSend.should(visible);
        reportWaitSend.should(text("Отправляем запрос в Банк..."));
        reportDeclined.should(text("Ошибка! Банк отказал в проведении операции."));
        snapCloseDeclined.click();
    }

    public void emptyField() {
        badge.click();
        errNumberCard.should(visible);
        errMonth.should(visible);
        errYear.should(visible);
        errOwner.should(visible);
        errCVC.should(visible);
    }

    public void wrongMonth() {
        errNumberCard.should(hidden);
        errMonth.should(visible);
        errMonth.should(text("Неверно указан срок действия карты"));
        errYear.should(hidden);
        errOwner.should(hidden);
        errCVC.should(hidden);
    }

    public void wrongYear() {
        errNumberCard.should(hidden);
        errMonth.should(hidden);
        errYear.should(visible);
        errYear.should(text("Истёк срок действия карты"));
        errOwner.should(hidden);
        errCVC.should(hidden);
    }

    public void emptyMonth() {
        errNumberCard.should(hidden);
        errMonth.should(visible);
        errMonth.should(text("Неверный формат"));
        errYear.should(hidden);
        errOwner.should(hidden);
        errCVC.should(hidden);
    }

    public void emptyYear() {
        errNumberCard.should(hidden);
        errMonth.should(hidden);
        errYear.should(visible);
        errYear.should(text("Неверный формат"));
        errOwner.should(hidden);
        errCVC.should(hidden);
    }

    public void emptyOwner() {
        errNumberCard.should(hidden);
        errMonth.should(hidden);
        errYear.should(hidden);
        errOwner.should(visible);
        errOwner.should(text("Поле обязательно для заполнения"));
        errCVC.should(hidden);
    }

    public void emptyCVC() {
        errNumberCard.should(hidden);
        errMonth.should(hidden);
        errYear.should(hidden);
        errOwner.should(hidden);
        errCVC.should(visible);
        errCVC.should(text("Неверный формат"));
    }

    public void wrongNumberCard() {
        errNumberCard.should(visible);
        errNumberCard.should(text("Неверный формат"));
        errMonth.should(hidden);
        errYear.should(hidden);
        errOwner.should(hidden);
        errCVC.should(hidden);
    }

    public void wrongOwner() {
        errNumberCard.should(hidden);
        errMonth.should(hidden);
        errYear.should(hidden);
        errOwner.should(visible);
        errOwner.should(text("Неверный формат данных"));
        errCVC.should(hidden);
    }
}