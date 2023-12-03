package repository;

import interfaces.OrdersRepository;
import models.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryJdbc implements OrdersRepository {
    private static final String ORD = "select * from orders";
    private final DataSource dataSource;

    public OrderRepositoryJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveOrder(Order order) {
        String status = "Ожидает обработки";
        try {
            Connection conn = dataSource.getConnection();
            String sql = "INSERT INTO orders (user_id, uuid, order_list, price, status, date) Values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, Integer.parseInt(order.getUserID()));
            preparedStatement.setString(2, order.getUniqueID());
            preparedStatement.setString(3, order.getOrderList());
            preparedStatement.setInt(4, order.getPrice());
            preparedStatement.setString(5, status);
            preparedStatement.setString(6, order.getDate());

            preparedStatement.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrders(String id) {
        List<Order> orders = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(ORD);

            while (resultSet.next()) {
                Order order = Order.builder()
                        .userID(resultSet.getString("user_id"))
                        .uniqueID(resultSet.getString("uuid"))
                        .orderList(resultSet.getString("order_list"))
                        .price(resultSet.getInt("price"))
                        .status(resultSet.getString("status"))
                        .date(resultSet.getString("date"))
                        .build();

                if(order.getUserID().equals(id)) orders.add(order);
            }

            connection.close();
            return orders;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Order> getUsersOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(ORD);

            while (resultSet.next()) {
                Order order = Order.builder()
                        .userID(resultSet.getString("user_id"))
                        .uniqueID(resultSet.getString("uuid"))
                        .orderList(resultSet.getString("order_list"))
                        .price(resultSet.getInt("price"))
                        .status(resultSet.getString("status"))
                        .date(resultSet.getString("date"))
                        .build();

                 orders.add(order);
            }

            connection.close();
            return orders;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delOrder(String uuid) {
        try {
            Connection connection = dataSource.getConnection();

            String SQL = "DELETE FROM orders WHERE uuid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, uuid);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Order getOrder(String uuid) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(ORD);

            while (resultSet.next()) {
                Order order = Order.builder()
                        .userID(resultSet.getString("user_id"))
                        .uniqueID(resultSet.getString("uuid"))
                        .orderList(resultSet.getString("order_list"))
                        .price(resultSet.getInt("price"))
                        .status(resultSet.getString("status"))
                        .date(resultSet.getString("date"))
                        .build();

                if(order.getUniqueID().equals(uuid)) return order;
            }

            connection.close();
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean findOrder(String uuid) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(ORD);

            while (resultSet.next()) {
                Order order = Order.builder()
                        .userID(resultSet.getString("user_id"))
                        .uniqueID(resultSet.getString("uuid"))
                        .orderList(resultSet.getString("order_list"))
                        .price(resultSet.getInt("price"))
                        .status(resultSet.getString("status"))
                        .date(resultSet.getString("date"))
                        .build();

                if(order.getUniqueID().equals(uuid)) return true;
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String returnUserId(String uuid) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(ORD);

            while (resultSet.next()) {
                Order order = Order.builder()
                        .userID(resultSet.getString("user_id"))
                        .uniqueID(resultSet.getString("uuid"))
                        .orderList(resultSet.getString("order_list"))
                        .price(resultSet.getInt("price"))
                        .status(resultSet.getString("status"))
                        .date(resultSet.getString("date"))
                        .build();

                if(order.getUniqueID().equals(uuid)) return order.getUserID();
            }

            connection.close();
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delUserOrders(String id) {
        try {
            Connection connection = dataSource.getConnection();

            String SQL = "DELETE FROM orders WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
