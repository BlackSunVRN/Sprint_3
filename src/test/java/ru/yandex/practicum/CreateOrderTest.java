package ru.yandex.practicum;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.scooter.api.OrderClient;
import ru.yandex.practicum.scooter.api.OrderGenerator;
import ru.yandex.practicum.scooter.api.model.MakeOrder;
import ru.yandex.practicum.scooter.api.model.OrderCreateResponse;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}

        };
    }

    @Test
    public void createOrderTest() {
        MakeOrder order = OrderGenerator.generateOrder(color);
        OrderClient orderClient = new OrderClient();
        Response responseCreate = orderClient.createOrder(order);
        assertEquals(SC_CREATED, responseCreate.statusCode());
        int track = responseCreate.body().jsonPath().getInt("track");
        OrderCreateResponse orderCreateResponse = responseCreate.as(OrderCreateResponse.class);
        assertEquals(track, orderCreateResponse.track);
    }

}
