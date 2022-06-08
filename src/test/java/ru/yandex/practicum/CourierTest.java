package ru.yandex.practicum;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.scooter.api.CourierClient;
import ru.yandex.practicum.scooter.api.model.Courier;
import ru.yandex.practicum.scooter.api.model.CourierCredentials;
import ru.yandex.practicum.scooter.api.model.CreateCourierResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.scooter.api.model.Courier.getRandomCourier;

public class CourierCreateTest {
    int courierId;
    Courier courier;

    CourierClient courierClient;

    @Before
    public void init() {
        courier = getRandomCourier();
        courierClient = new CourierClient();
    }

    @Test
    public void courierCreateTest() {
        //Создание курьера с рандомными данными
        Response responseCreate = courierClient.createCourier(courier);

        assertEquals(SC_CREATED, responseCreate.statusCode());
        CreateCourierResponse createCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.ok);

        //Доп. проверка для входа в созданную учетную запись
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierClient.login(courierCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
        courierId = responseLogin.body().jsonPath().getInt("id");
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
        assertEquals("Этот логин уже используется. Попробуйте другой.", errorText);

        //Логин для получения id курьера
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierClient.login(courierCredentials);
        courierId = responseLogin.body().jsonPath().getInt("id");
    }


    @After
    public void clear() {
        courierClient.deleteCourier(courierId);
    }
}
