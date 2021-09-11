import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;



import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    LocalDate date = LocalDate.now().plusDays(4);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    @Test
    void shouldSuccessTest() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $(withText("Успешно!")).shouldBe(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + formatter.format(date)));

    }

    @Test
    void shouldSuccessTestWithHyphen() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин-Иванов");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $(withText("Успешно!")).shouldBe(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + formatter.format(date)));

    }

    @Test
    void dontClickCheckbox() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=button__text]").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void englishName() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Andrew Shchepkin");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void emptyNameField() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void inputIncorrectCity() {
        $("[data-test-id='city'] input").setValue("Енисейск");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyCityField() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void numbersInCityField() {
        $("[data-test-id='city'] input").setValue("1234567");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));

    }

    @Test
    void fieldTelOnlyPlus() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void emptyPhoneField() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void fieldTelThirteenSymbols() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+790012345678");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void fieldTelTwoSymbols() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").sendKeys(formatter.format(date));
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+7");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void emptyDateField() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Андрей Щепкин");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(Condition.text("Неверно введена дата"));
    }

}
