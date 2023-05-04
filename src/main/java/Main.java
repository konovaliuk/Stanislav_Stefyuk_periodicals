package main.java;

import main.java.connection.DBConnection;
import main.java.dao.implementations.PaymentDao;
import main.java.dao.implementations.PeriodicalsDao;
import main.java.dao.implementations.SubscriptionDao;
import main.java.dao.implementations.UserDao;
import main.java.entities.Payment;
import main.java.entities.Periodicals;
import main.java.entities.Subscription;
import main.java.entities.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            //Connections
            System.out.println("Testing connection");
            UserDao userCon = new UserDao(DBConnection.getConnection());
            SubscriptionDao subscriptionCon = new SubscriptionDao(DBConnection.getConnection());
            PeriodicalsDao periodicalsCon = new PeriodicalsDao(DBConnection.getConnection());
            PaymentDao paymentCon = new PaymentDao(DBConnection.getConnection());
            //Creating users
            System.out.println("\nCreating user");
            userCon.createUser(new User("Stas", "stas@gmail.com", "1111", "reader"));
            //Finding user by email
            User user = userCon.findByEmail("stas@gmail.com");
            System.out.println(user);

            //Creating periodicals
            System.out.println("\nCreating periodicals");
            periodicalsCon.createPeriodicals(new Periodicals("Fairytales", "Abababalamaga", "1111", 11.99F));
            //Finding periodicals by name
            Periodicals periodicals = periodicalsCon.findByName("Fairytales");
            System.out.println(periodicals);

            //Creating subscription
            System.out.println("\nCreating subscription");
            long monthMillis = 1000L *60*60*24*30;
            subscriptionCon.createSubscription(new Subscription(periodicals.getId(), user.getId(),  new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()+monthMillis)));
            //All subscriptions
            Subscription subscription = subscriptionCon.findByUserAndPeriodicals(user.getId(), periodicals.getId());
            System.out.println(subscription);

            //Creating payment
            System.out.println("\nCreating payment");
            paymentCon.createPayment(new Payment(11.99F, subscription.getId(), "pending"));
            //Find payment by user
            List<Payment> paymentList = paymentCon.findByUser(user);
            System.out.println(paymentList);

            //Deleting payments
            System.out.println("\nDeleting payments");
            for(Payment element : paymentList) {
                System.out.println("Payment with id: "+element.getId()+" is deleted");
                paymentCon.deleteById(element.getId());
            }
            paymentList = paymentCon.findAll();
            System.out.println(paymentList);

            //Deleting subscription
            System.out.println("\nDeleting subscription");
            for(Subscription element : subscriptionCon.findAll()) {
                System.out.println("Subscription with id: "+element.getId()+" is deleted");
                subscriptionCon.deleteById(element.getId());
            }
            List<Subscription> subscriptionList = subscriptionCon.findAll();
            System.out.println(subscriptionList);

            //Deleting periodicals
            System.out.println("\nDeleting periodicals");
            for(Periodicals element : periodicalsCon.findAll()) {
                System.out.println("Periodicals with id: "+element.getId()+" is deleted");
                periodicalsCon.deleteById(element.getId());
            }
            List<Periodicals> periodicalsList = periodicalsCon.findAll();
            System.out.println(periodicalsList);

            //Deleting user
            System.out.println("\nDeleting user");
            for(User element : userCon.findAll()) {
                System.out.println("User with id: "+element.getId()+" is deleted");
                userCon.deleteById(element.getId());
            }
            List<User> userList = userCon.findAll();
            System.out.println(userList);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}