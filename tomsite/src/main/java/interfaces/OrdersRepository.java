package interfaces;

import models.Order;

import java.util.List;

public interface OrdersRepository {
    List<Order> getOrders(String id);
    List<Order> getUsersOrders();
    void saveOrder(Order order);
    void delOrder(String uuid);
    Order getOrder(String uuid);
    boolean findOrder(String uuid);
    String returnUserId(String uuid);
    void delUserOrders(String id);
}
