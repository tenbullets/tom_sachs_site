package servlets.orders;

import interfaces.BucketService;
import interfaces.OrdersRepository;
import interfaces.UsersRepository;
import models.Order;
import repository.StoreRepositoryJdbc;
import service.BucketServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@WebServlet("/addOrder")
public class AddOrderServlet extends HttpServlet {
    private BucketService bucketCookie;
    private StoreRepositoryJdbc storeRepository;
    private UsersRepository usersRepository;
    private OrdersRepository ordersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        bucketCookie = new BucketServiceImpl();
        storeRepository = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
        ordersRepository = (OrdersRepository) config.getServletContext().getAttribute("orderRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String username = (String) httpSession.getAttribute("username");
        Cookie[] inputCookie = (request.getCookies());
        String cookie = bucketCookie.getProd(inputCookie);

        String[] tags = cookie.split("_");
        List<String> prod = new ArrayList<>(Arrays.asList(tags));

        if(bucketCookie.cookieIsReal(inputCookie, "prod") && !cookie.isEmpty()) {
            String uniqueID = java.util.UUID.randomUUID().toString();
            String id = usersRepository.returnId(username);
            int price = bucketCookie.getOrderPrice(cookie, storeRepository);
            String date = getDate();
            String orderList = createOrder(prod);

            Order order = Order.builder()
                    .userID(id)
                    .uniqueID(uniqueID)
                    .orderList(orderList)
                    .price(price)
                    .date(date)
                    .build();

            ordersRepository.saveOrder(order);

            bucketCookie.delAllBucketCookie(response);
        }
        request.getRequestDispatcher("/userOrders").forward(request, response);
    }

    // Оформить в клас
    public String getDate() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        return formatForDateNow.format(dateNow);
    }

    public String createOrder(List<String> tags) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            if(tags.get(i).isEmpty()) {
                continue;
            } else if(i != tags.size() - 1) {
                res.append(tags.get(i));
                res.append("_");
            } else {
                res.append(tags.get(i));
            }
        }
        return res.toString();
    }

}
