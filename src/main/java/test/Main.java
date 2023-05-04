package test;

import dao.implementations.PaymentDao;
import dao.implementations.PeriodicalsDao;
import dao.implementations.SubscriptionDao;
import dao.implementations.UserDao;
import entities.Payment;
import entities.Periodicals;
import entities.Subscription;
import entities.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("periodicals");

        UserDao userDao = new UserDao(entityManagerFactory);
        PeriodicalsDao periodicalsDao = new PeriodicalsDao(entityManagerFactory);
        SubscriptionDao subscriptionDao = new SubscriptionDao(entityManagerFactory);
        PaymentDao paymentDao = new PaymentDao(entityManagerFactory);

        User user = new User("dao1","dao1@gmail.com", "1111");
        userDao.createUser(user);

        Periodicals periodicals = new Periodicals("A weekly news magazine", "Some pub", "News Weekly", 11.9F);
        periodicalsDao.createPeriodicals(periodicals);

        User foundUser = userDao.findById(user.getId());
        System.out.println("Found user by ID: " + foundUser.getEmail());

        Periodicals foundPeriodicals = periodicalsDao.findById(periodicals.getId());
        System.out.println("Found Periodicals by ID: " + foundPeriodicals.getName());

        Subscription subscription = new Subscription(periodicals.getId(), user.getId());
        subscriptionDao.createSubscription(subscription);

        Subscription foundSubscription = subscriptionDao.findById(subscription.getId());
        System.out.println("Found Subscription by ID (output is user id): " + foundSubscription.getUserId());

        Payment payment = new Payment(11.9F, foundSubscription.getId());
        paymentDao.createPayment(payment);

        Payment foundPayment = paymentDao.findById(payment.getId());
        System.out.println("Found Payment by ID (output is money): " + foundPayment.getAmount());

        paymentDao.deleteById(foundPayment.getId());
        subscriptionDao.deleteById(foundSubscription.getId());
        periodicalsDao.deleteById(foundPeriodicals.getId());

        List<User> allUsers = userDao.findAll();
        for (User u : allUsers) {
            System.out.println("User email: " + u.getEmail());
        }

        userDao.deleteById(foundUser.getId());

        entityManagerFactory.close();

    }
}