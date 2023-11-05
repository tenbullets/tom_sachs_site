package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import interfaces.SignUpService;
import interfaces.UsersRepository;
import models.Product;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repository.DataRepositoryJdbc;
import repository.SignUpServiceImpl;
import repository.StoreRepository;
import repository.UsersRepositoryJdbcImpl;

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
        StoreRepository storeRepository = new StoreRepository();
        List<Product> products;
        try {
            products = storeRepository.getAllProduct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ServletContext servletContext = servletContextEvent.getServletContext();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        servletContext.setAttribute("userRep", usersRepository);

        DataRepositoryJdbc dataRepository = new DataRepositoryJdbc(dataSource);
        servletContext.setAttribute("dataRep", dataRepository);

        SignUpService signUpService = new SignUpServiceImpl(dataRepository);
        servletContext.setAttribute("signUpService", signUpService);

        servletContext.setAttribute("products", products);
     }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
