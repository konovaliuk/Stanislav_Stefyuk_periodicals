package actions;

import entities.Payment;
import manager.PageManager;
import services.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManagePaymentAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = null;
        page = PageManager.getInstance().getProperty(PageManager.CART);
        PaymentService paySer = new PaymentService();
        HttpSession session = request.getSession();
        if (request.getParameter("confirm") != null) {
            // Confirm button was clicked
        } else if (request.getParameter("reject") != null) {
            // Reject button was clicked
        }
        return page;
    }
}
