package actions;

import manager.PageManager;
import services.PaymentService;
import services.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ConfirmPaymentAction implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = null;
        PaymentService paySer = new PaymentService();
        long payId = Long.parseLong(request.getParameter("payId"));
        paySer.updatePaymentStatus(payId, "completed");
        page = PageManager.getInstance().getProperty(PageManager.CART);
        return page;
    }
}
