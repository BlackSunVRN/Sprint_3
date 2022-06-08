package ru.yandex.practicum;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

public class CourierTest {
    int courierId;
    Courier courier;

    CourierClient courierClient;
    CourierCredentials courierCredentials;

    @Before
    public void init() {
        courier = getRandomCourier();
        courierClient = new CourierClient();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @Test
    public void courierCreateTest() {
        //Создание курьера с рандомными данными
        Response responseCreate = courierClient.createCourier(courier);

        assertEquals(SC_CREATED, responseCreate.statusCode());
        CreateCourierResponse createCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.ok);

        //Доп. проверка для входа в созданную учетную запись
        Response responseLogin = courierClient.login(courierCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
    }

    @Test
    public void courierLoginTest() {
        Response responseCreate = courierClient.createCourier(courier);

        assertEquals(SC_CREATED, responseCreate.statusCode());
        Response responseLogin = courierClient.login(courierCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
        courierId = courierClient.getCourierId(courierCredentials);
        responseLogin
                .then()
                .assertThat()
                .body("id", equalTo(courierId));
    }

    @Test
    public void courierCreateSameDataTest() {
        //Создание первого курьера и проверка ответа
        Response responseCreate = courierClient.createCourier(courier);
        assertEquals(SC_CREATED, responseCreate.statusCode());

        //Создание второго курьера с такими же данными и проверка ответа
        Response responseCreateSameData = courierClient.createCourier(courier);
        assertEquals(SC_CONFLICT, responseCreateSameData.statusCode());
        String errorText = responseCreateSameData.body().jsonPath().getString("message");
        //В документации отсутствует второе предложение. Добавил для прохождения текста фактическое
        assertEquals("Этот логин уже используется. Попробуйте другой.", errorText);
    }

    @Test
    public void courierCreateAlreadyRegistered() {
        Response responseCreate = courierClient.createCourier(courier);
        assertEquals(SC_CREATED, responseCreate.statusCode());

        Courier courierSameLogin = new Courier(courier.getLogin(), RandomStringUtils.randomAlphabetic(10), "Same Login");
        Response responseCreateSame = courierClient.createCourier(courierSameLogin);
        assertEquals(SC_CONFLICT, responseCreateSame.statusCode());
        //В документации отсутствует второе предложение. Добавил для прохождения текста фактическое
        assertEquals("Этот логин уже используется. Попробуйте другой.", responseCreateSame.body().jsonPath().getString("message"));
    }

    @After
    public void clear() {
        courierId = courierClient.getCourierId(courierCredentials);
        courierClient.deleteCourier(courierId);
    }
}
