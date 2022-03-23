import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTest {

    @BeforeAll
    static void beforeAll() {
        //Configuration.startmaximized = true; // doesn't work -- I have to maximize the browser window manualy to pass the test:(
        Configuration.browserSize = "1080x1680";
        Configuration.browserPosition = "0x0";
        Configuration.baseUrl = "https://demoqa.com";
    }

    @AfterEach
    void afterEach() {
        Selenide.closeWebDriver();
    }

    @Test
    void successFilledInForm() {
        open("/automation-practice-form");
        $(".main-header").shouldHave(text("Practice Form"));
        $("#firstName").setValue("Лера");
        $("#lastName").setValue("Никулина");
        $("#userEmail").sendKeys("test@test.ru");
        $(byText("Other")).click();
        String phone = "92" + Math.round(10000000 + 10000000 * Math.random());
        $("#userNumber").setValue(phone);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").selectOptionByValue("1986");
        $(".react-datepicker__month-select").selectOptionContainingText("June");
        $(".react-datepicker__day--023").click();
        $("#subjectsInput").setValue("Biology").pressEnter();
        $("#subjectsInput").setValue("Chemistry").pressEnter();
        $(byText("Reading")).click();
        $(byText("Sports")).click();
        File pic = new File("src/test/resourses/test.jpg");
        $("#uploadPicture").uploadFile(pic);
        $("#currentAddress").sendKeys("Земля");
        $("#react-select-3-input").setValue("NCR").pressEnter();
        $("#react-select-4-input").setValue("Delhi").pressEnter();
        $("#submit").scrollTo().click();
        //Check this form
        $(".modal-header").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(text("Лера Никулина"),
                text("test@test.ru"),text("Other"),text(phone),text("23 June,1986"),text("Biology, Chemistry"),
                text("Reading, Sports"),text("test.jpg"),text("Земля"),text("NCR Delhi")
        );
    }
}