package services;

import connection.DBConnection;
import dao.implementations.PaymentDao;
import dao.implementations.PeriodicalsDao;
import dao.implementations.SubscriptionDao;
import dao.implementations.UserDao;
import entities.Payment;
import entities.Periodicals;
import entities.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    public List<Payment> findByUser(User user) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);
        paymentCon.deleteExpiredSubs();
        List<Payment> paymentList = paymentCon.findByUser(user);

        paymentCon.closeConnection();

        return paymentList;
    }
    public Periodicals getPeriodicals(Payment payment) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        SubscriptionDao subscriptionCon = new SubscriptionDao(conn);
        PeriodicalsDao periodicalsCon = new PeriodicalsDao(conn);
        long perId = subscriptionCon.findById(payment.getSubscriptionId()).getPeriodicalsId();
        Periodicals periodicals = periodicalsCon.findById(perId);

        subscriptionCon.closeConnection();
        periodicalsCon.closeConnection();

        return periodicals;
    }
    public List<Payment> findByUserEmail(String email) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);
        UserDao userCon = new UserDao(conn);
        paymentCon.deleteExpiredSubs();
        User user = userCon.findByEmail(email);
        List<Payment> paymentList = paymentCon.findByUser(user);

        paymentCon.closeConnection();
        userCon.closeConnection();

        return paymentList;
    }

    public List<Payment> findByUserEmailAndStatus(String email, String status) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);
        UserDao userCon = new UserDao(conn);
        paymentCon.deleteExpiredSubs();
        User user = userCon.findByEmail(email);
        List<Payment> paymentList = paymentCon.findByUser(user);
        List<Payment> resultList = new ArrayList<>();
        for (Payment payment:paymentList){
            String payStat = payment.getStatus();
            if (payStat.equals(status)){
                resultList.add(payment);
            }
        }
        paymentCon.closeConnection();
        userCon.closeConnection();

        return resultList;
    }

    public Payment findById(long id) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);
        Payment payment = paymentCon.findById(id);

        paymentCon.closeConnection();

        return payment;
    }

    public List<Payment> findByStatus(String status) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);
        List<Payment> paymentList = paymentCon.findByStatus(status);

        paymentCon.closeConnection();

        return paymentList;
    }
//    public boolean addPayment(float amount, long subscriptionId) throws SQLException, IOException {
//        Connection conn = DBConnection.getConnection();
//        PaymentDao paymentCon = new PaymentDao(conn);
//
//        paymentCon.createPayment(new Payment(amount, subscriptionId));
//        List<Payment> paymentList = paymentCon.findByStatus("pending");
//        paymentCon.closeConnection();
//        if(paymentList.get(paymentList.size() - 1).getId()!=0){
//            return true;}
//        else{return false;}
//    }

    public boolean addPayment(Payment payment) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);

        paymentCon.createPayment(payment);

        paymentCon.closeConnection();

        return true;
    }

    public boolean updatePayment(long id, Payment payment) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);
        paymentCon.updatePayment(id, payment);

        paymentCon.closeConnection();

        return true;
    }

    public boolean updatePaymentStatus(long id, String newStatus) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);
        Payment payment = paymentCon.findById(id);
        paymentCon.updatePayment(id, new Payment(payment.getAmount(), payment.getSubscriptionId(), newStatus));

        paymentCon.closeConnection();

        return true;
    }

    public void deletePayment(Long paymentId) throws SQLException, IOException {
        Connection conn = DBConnection.getConnection();
        PaymentDao paymentCon = new PaymentDao(conn);
        paymentCon.deleteById(paymentId);

        paymentCon.closeConnection();

    }
}
