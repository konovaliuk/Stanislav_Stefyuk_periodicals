package actions;

import manager.PageManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToLoginAction implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;

        page = PageManager.getInstance().getProperty(PageManager.LOGIN);

        return page;
    }
}
