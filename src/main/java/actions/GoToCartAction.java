package actions;


import entities.Payment;
import entities.Periodicals;
import manager.PageManager;
import services.PaymentService;
import services.PeriodicalsService;
import services.SubscriptionService;
import utils.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoToCartAction implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = null;
        page = PageManager.getInstance().getProperty(PageManager.CART);
        PaymentService paySer = new PaymentService();
        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        List<Payment> payments = paySer.findByUserEmailAndStatus(email, "pending");
        List<Pair<Payment, Periodicals>> pairList = new ArrayList<>();
        for (Payment payment : payments) {
            Periodicals periodicals = paySer.getPeriodicals(payment);
            pairList.add(new Pair<Payment, Periodicals>(payment, periodicals));
        }
        session.setAttribute("myPayments", pairList);
        return page;
    }
}
