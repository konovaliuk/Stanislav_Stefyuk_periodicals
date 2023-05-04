package controller;

import actions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class ServletHelper {
    private static ServletHelper instance = null;
    HashMap<String, Action> actions = new HashMap<>();


    private ServletHelper() {
        actions.put("login", new LoginAction());
        actions.put("signup", new SignUpAction());
        actions.put("logout", new LogOutAction());
        actions.put("gotologin", new GoToLoginAction());
        actions.put("gotomain", new GoToMainAction());
        actions.put("gotocart", new GoToCartAction());
        actions.put("gotolibrary", new GoToLibAction());
        actions.put("managePayment", new ManagePaymentAction());
        actions.put("addtocart", new AddToCartAction());
        actions.put("confirmPayment", new ConfirmPaymentAction());
        actions.put("deletePayment", new DeletePaymentAction());
    }

    public Action getAction(HttpServletRequest request) {
        String actionName = request.getParameter("command");
        System.out.println("Executing command: "+actionName);
        Action action = actions.get(actionName);

        HttpSession session = request.getSession();

        String email = (String) session.getAttribute("email");

        if (action == null && email == null) {
            action = new LogOutAction();
        }
        if (action == null)
            action = new MissingAction();


        return action;
    }

    public static ServletHelper getInstance() {
        if (instance == null) {
            instance = new ServletHelper();
        }

        return instance;
    }
}
