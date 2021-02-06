package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class Generator {
    private Generator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto generateActiveUser() {
            Faker faker = new Faker(new Locale("usa"));
            return new RegistrationDto(faker.name().firstName(), faker.internet().password(), "active");
        }

        public static RegistrationDto generateBlockedUser() {
            Faker faker = new Faker(new Locale("usa"));
            return new RegistrationDto(faker.name().firstName(), faker.internet().password(), "blocked");
        }
    }

    public static class SendOnServer {
        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static void setUpAll(RegistrationDto user) {

            given()
                    .spec(requestSpec)
                    .body(user)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }
    }
}

