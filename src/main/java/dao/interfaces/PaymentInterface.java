package dao.interfaces;

import entities.Payment;
import entities.User;

import java.util.List;

public interface PaymentInterface {
    Payment findById(long id);
    List<Payment> findByUser(User user);
    List<Payment> findByStatus(String status);
    List<Payment> findAll();
    void createPayment(Payment payment);
    void updatePayment(long id, Payment payment);
    void deleteById(long id);

    void deleteExpiredSubs();
}
