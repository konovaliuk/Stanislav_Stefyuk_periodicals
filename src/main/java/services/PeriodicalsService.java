package services;

import connection.DBConnection;
import dao.implementations.PeriodicalsDao;
import dao.implementations.SubscriptionDao;
import dao.implementations.UserDao;
import entities.Periodicals;
import entities.Subscription;
import entities.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalsService {

    public List<Periodicals> findAllPeriodicals() throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PeriodicalsDao periodicalsCon = new PeriodicalsDao(conn);
        List<Periodicals> periodicals = periodicalsCon.findAll();
        periodicalsCon.closeConnection();

        return periodicals;
    }

    public boolean addPeriodicals(String name, String pub, String info, float price) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PeriodicalsDao periodicalsCon = new PeriodicalsDao(conn);

        periodicalsCon.createPeriodicals(new Periodicals(name, pub, info, price));
        Periodicals periodicals = periodicalsCon.findByName(name);
        periodicalsCon.closeConnection();
        if(periodicals.getId()!=0){
            return true;}
        else{return false;}
    }

    public boolean updatePeriodicals(long id, Periodicals periodicals) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PeriodicalsDao periodicalsCon = new PeriodicalsDao(conn);
        periodicalsCon.updatePeriodicals(id, periodicals);

        periodicalsCon.closeConnection();

        return periodicals.getId() != 0;
    }

    public List<Periodicals> findByUser(long userId) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        PeriodicalsDao periodicalsCon = new PeriodicalsDao(conn);
        List<Subscription> subscriptionList = subscriptionCon.findByUser(userId);

        List<Periodicals> periodicalsList = new ArrayList<>();
        for (Subscription subscription : subscriptionList) {
            Periodicals periodicals = periodicalsCon.findById(subscription.getPeriodicalsId());
            periodicalsList.add(periodicals);
        }

        subscriptionCon.closeConnection();
        periodicalsCon.closeConnection();

        return periodicalsList;
    }
    public List<Periodicals> findByUserEmail(String email) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        UserDao userCon = new UserDao(conn);
        PeriodicalsDao periodicalsCon = new PeriodicalsDao(conn);
        long userId = userCon.findByEmail(email).getId();
        List<Subscription> subscriptionList = subscriptionCon.findByUser(userId);

        List<Periodicals> periodicalsList = new ArrayList<>();
        for (Subscription subscription : subscriptionList) {
            Periodicals periodicals = periodicalsCon.findById(subscription.getPeriodicalsId());
            periodicalsList.add(periodicals);
        }

        subscriptionCon.closeConnection();
        periodicalsCon.closeConnection();

        return periodicalsList;
    }
    public void deletePeriodicals(Long periodicalsId) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PeriodicalsDao periodicalsCon = new PeriodicalsDao(conn);
        periodicalsCon.deleteById(periodicalsId);

        periodicalsCon.closeConnection();
    }
}
