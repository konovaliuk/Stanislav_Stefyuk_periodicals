package actions;

import entities.Payment;
import entities.Subscription;
import entities.User;
import manager.PageManager;
import services.PaymentService;
import services.SubscriptionService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AddToCartAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = null;
        HttpSession session = request.getSession();
        UserService userService = new UserService();
        SubscriptionService subscriptionService = new SubscriptionService();
        PaymentService paymentService = new PaymentService();
        long perId = Long.parseLong(request.getParameter("perId"));
        String email = (String)session.getAttribute("email");
        long userId = userService.getUserIdByEmail(email);
        if(subscriptionService.findByUserAndPeriodicals(userId, perId).getId() == 0){
            subscriptionService.addSubscription(new Subscription(perId, userId));
            long subId = subscriptionService.findByUserAndPeriodicals(userId, perId).getId();
            float amount = subscriptionService.getAmount(subId);
            paymentService.addPayment(new Payment(amount, subId));
        }
        page = PageManager.getInstance().getProperty(PageManager.MAIN);
        return page;
    }
}
