package ru.yandex.practicum;

import org.junit.Test;
import ru.yandex.practicum.scooter.api.OrderClient;
import static org.junit.Assert.assertTrue;

public class OrderListTest {

    @Test
    public  void  getOrderListTest(){
        OrderClient orderClient = new OrderClient();
        assertTrue(orderClient.getOrderList().get(0).getId() > 0);
        assertTrue(orderClient.getOrderList().get(0).getTrack() > 0);
    }
}
