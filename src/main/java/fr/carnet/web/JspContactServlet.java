package fr.carnet.web;

import fr.carnet.dao.ContactDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Vue JSP en lecture seule de la liste des contacts. */
@WebServlet("/jsp/contacts")
public class JspContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ContactDao dao = (ContactDao) getServletContext().getAttribute("contactDao");
        req.setAttribute("contacts", dao.findAll());
        req.getRequestDispatcher("/WEB-INF/jsp/contacts.jsp").forward(req, resp);
    }
}
