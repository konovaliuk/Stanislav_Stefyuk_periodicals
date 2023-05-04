package actions;

import manager.PageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogOutAction implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession();
        session.invalidate();

        page = PageManager.getInstance().getProperty(PageManager.LOGIN);
        return page;
    }
}
