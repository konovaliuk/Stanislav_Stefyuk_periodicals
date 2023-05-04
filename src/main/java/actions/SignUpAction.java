package actions;

import manager.PageManager;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class SignUpAction implements Action{
    static final String EMAIL = "email";
    static final String PASSWORD = "password";
    private static final String NAME = "name";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String page = null;
        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String name = request.getParameter(NAME);

        page = PageManager.getInstance().getProperty(PageManager.SIGNUP);

        UserService userService = new UserService();

        if (email == null || email.equals("") || password == null || password.equals("")) {
            request.setAttribute("message", "Username and password cannot be empty!");
            return page;
        }

        if (userService.findUserByEmail(email) != null) {
            request.setAttribute("message", "This email is already occupied :(");
            return page;
        }

        userService.addUser(name,email,password);
        page = PageManager.getInstance().getProperty(PageManager.LOGIN);

        return page;
    }
}
