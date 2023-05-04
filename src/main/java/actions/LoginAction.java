package actions;

import entities.Periodicals;
import entities.Subscription;
import manager.PageManager;
import services.PeriodicalsService;
import services.SubscriptionService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static actions.SignUpAction.EMAIL;
import static actions.SignUpAction.PASSWORD;

public class LoginAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = null;
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();

        page = PageManager.getInstance().getProperty(PageManager.MAIN);

        UserService userService = new UserService();

        if (!userService.checkAuthorization(email, password)) {
            page = PageManager.getInstance().getProperty(PageManager.LOGIN);
            request.setAttribute("message", "login error");
            return page;
        }
        SubscriptionService subscriptionService = new SubscriptionService();
        PeriodicalsService perSer = new PeriodicalsService();

        List<Subscription> subscriptionList = subscriptionService.findByUser(email);
        List<Long> periodicalsIds = new ArrayList<>();

        for (Subscription subscription : subscriptionList) {
            long periodicalsId = subscription.getPeriodicalsId();
            periodicalsIds.add(periodicalsId);
        }
        request.setAttribute("myPeriodicalsId", periodicalsIds);
        session.setAttribute("myPeriodicalsId", periodicalsIds);
//        request.setAttribute("mySubscriptions", subscriptionList);
//        session.setAttribute("mySubscriptions", subscriptionList);

        List<Periodicals> periodicals = perSer.findAllPeriodicals();
        session.setAttribute("allPeriodicals", periodicals);
        session.setAttribute("email", email);
        session.setAttribute("userId", userService.getUserIdByEmail(email));

        return page;
    }
}
