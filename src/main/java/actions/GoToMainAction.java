package actions;

import entities.Periodicals;
import entities.Subscription;
import manager.PageManager;
import services.PeriodicalsService;
import services.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoToMainAction implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = null;
        HttpSession session = request.getSession();
        SubscriptionService subscriptionService = new SubscriptionService();
        PeriodicalsService perSer = new PeriodicalsService();
        String email = (String)session.getAttribute("email");
        page = PageManager.getInstance().getProperty(PageManager.MAIN);

        List<Subscription> subscriptionList = subscriptionService.findByUser(email);
        List<Long> periodicalsIds = new ArrayList<>();

        for (Subscription subscription : subscriptionList) {
            long periodicalsId = subscription.getPeriodicalsId();
            periodicalsIds.add(periodicalsId);
        }
        session.setAttribute("myPeriodicalsId", periodicalsIds);
//        session.setAttribute("mySubscriptions", subscriptionList);

        List<Periodicals> periodicals = perSer.findAllPeriodicals();
        session.setAttribute("allPeriodicals", periodicals);
        return page;
    }
}
