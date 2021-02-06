package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Generator;
import ru.netology.data.RegistrationDto;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;

import static io.restassured.RestAssured.given;

class AuthTest {
    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLoginActiveUser() {
        RegistrationDto dataOrderCard= Generator.Registration.generateActiveUser();
        Generator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible);
    }
    @Test
    void shouldLoginBlockedUser() {
        RegistrationDto dataOrderCard= Generator.Registration.generateBlockedUser();
        Generator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldInvalidLogin() {
        RegistrationDto dataOrderCard= Generator.Registration.generateActiveUser();
        Generator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue("oleg");
        $("[name=password]").setValue(dataOrderCard.getPassword());
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин")).shouldBe(Condition.visible);
    }

    @Test
    void shouldInvalidPassword() {
        RegistrationDto dataOrderCard= Generator.Registration.generateActiveUser();
        Generator.SendOnServer.setUpAll(dataOrderCard);
        $("[name=login]").setValue(dataOrderCard.getLogin());
        $("[name=password]").setValue("worker");
        $("[data-test-id=action-login]").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}