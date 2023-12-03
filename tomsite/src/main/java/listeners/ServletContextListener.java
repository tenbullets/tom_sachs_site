package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import interfaces.OrdersRepository;
import interfaces.SignUpService;
import interfaces.UsersRepository;
import models.Exh;
import models.Product;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repository.*;
import service.SignUpServiceImpl;

import java.io.IOException;
import java.util.List;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        // Repository
        StoreRepositoryJdbc storeRepository = new StoreRepositoryJdbc(dataSource);
        servletContext.setAttribute("storeRep", storeRepository);

        ExhRepositoryJdbc exhRepositoryJdbc = new ExhRepositoryJdbc(dataSource);
        servletContext.setAttribute("ExhRep", exhRepositoryJdbc);

        OrdersRepository ordersRepository = new OrderRepositoryJdbc(dataSource);
        servletContext.setAttribute("orderRep", ordersRepository);

        UsersRepository usersRepository = new UsersRepositoryJdbc(dataSource);
        servletContext.setAttribute("userRep", usersRepository);

        DataRepositoryJdbc dataRepository = new DataRepositoryJdbc(dataSource);
        servletContext.setAttribute("dataRep", dataRepository);

        SignUpService signUpService = new SignUpServiceImpl(dataRepository);
        servletContext.setAttribute("signUpService", signUpService);


        // Exhibitions and products
        List<Product> productsList;
        try {
            productsList = storeRepository.getAllProduct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        servletContext.setAttribute("products", productsList);

        List<Exh> exhList;
        try {
            exhList = exhRepositoryJdbc.getExhList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        servletContext.setAttribute("exhs", exhList);


        // Update
        List<Product> storeUpdate;
        try {
            storeUpdate = storeRepository.getProdUpdate(storeRepository.getAllProduct());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        servletContext.setAttribute("update", storeUpdate);

        List<Exh> exhUpdate;
        try {
            exhUpdate = exhRepositoryJdbc.getExhUpdate(exhRepositoryJdbc.getExhList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        servletContext.setAttribute("exhUpdate", exhUpdate);
     }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}

}
