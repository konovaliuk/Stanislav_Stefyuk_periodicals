package actions;

import manager.PageManager;
import services.PaymentService;
import services.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class DeletePaymentAction implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = null;
        SubscriptionService subSer = new SubscriptionService();
        PaymentService paySer = new PaymentService();
        long payId = Long.parseLong(request.getParameter("payId"));
        long subId = paySer.findById(payId).getSubscriptionId();
        paySer.deletePayment(payId);
        subSer.deleteSubscription(subId);
        page = PageManager.getInstance().getProperty(PageManager.CART);
        return page;
    }
}
