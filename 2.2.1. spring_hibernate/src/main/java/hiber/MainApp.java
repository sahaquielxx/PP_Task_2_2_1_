package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }
        User alex = new User("Alexandr", "Ponomariov", "ponomariov@mail.ru");
        User vladimir = new User("Vladimir", "Aronov", "aronov@mail.ru");
        User irina = new User("Irina", "Romanova", "romanova@mail.ru");
        User anatoly = new User("Anatoly", "Ivanov", "ivanov@mail.ru");

        Car kia = new Car("Kia", 2);
        Car hyundai = new Car("Hyundai", 6);
        Car toyota = new Car("Toyota", 70);
        Car lada = new Car("Lada", 2100);

        userService.add(alex.setCar(kia).setUser(alex));
        userService.add(vladimir.setCar(hyundai).setUser(vladimir));
        userService.add(irina.setCar(toyota).setUser(irina));
        userService.add(anatoly.setCar(lada).setUser(anatoly));
        for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

        System.out.println(userService.getUserByCar("Hyundai", 6));

        try {
            User notFoundUser = userService.getUserByCar("Renault", 54);
        } catch (NoResultException e) {
            System.out.println("User not found");
        }
        context.close();
    }
}
