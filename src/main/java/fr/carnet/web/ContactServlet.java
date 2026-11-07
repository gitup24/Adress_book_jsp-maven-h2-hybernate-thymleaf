package fr.carnet.web;

import fr.carnet.dao.ContactDao;
import fr.carnet.model.Contact;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

/** CRUD des contacts, pages rendues avec Thymeleaf. */
@WebServlet("/contacts/*")
public class ContactServlet extends HttpServlet {

    private ContactDao dao;
    private TemplateEngine engine;
    private JakartaServletWebApplication webApp;

    @Override
    public void init() {
        ServletContext ctx = getServletContext();
        dao = (ContactDao) ctx.getAttribute("contactDao");
        engine = (TemplateEngine) ctx.getAttribute("templateEngine");
        webApp = (JakartaServletWebApplication) ctx.getAttribute("webApp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();
        switch (path) {
            case "/new" -> render(req, resp, "form", new Contact(), null);
            case "/edit" -> {
                Long id = parseId(req.getParameter("id"));
                Contact c = id == null ? null : dao.find(id);
                if (c == null) {
                    redirectToList(req, resp);
                } else {
                    render(req, resp, "form", c, null);
                }
            }
            default -> render(req, resp, "list", null, null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo() == null ? "/" : req.getPathInfo();
        switch (path) {
            case "/save" -> {
                Long id = parseId(req.getParameter("id"));
                Contact c = id == null ? new Contact() : dao.find(id);
                if (c == null) {
                    c = new Contact();
                }
                c.setFirstName(clean(req.getParameter("firstName")));
                c.setLastName(clean(req.getParameter("lastName")));
                c.setEmail(clean(req.getParameter("email")));
                c.setPhone(clean(req.getParameter("phone")));
                c.setAddress(clean(req.getParameter("address")));

                if (c.getFirstName().isEmpty() || c.getLastName().isEmpty()) {
                    render(req, resp, "form", c, "Le prénom et le nom sont obligatoires.");
                    return;
                }
                dao.save(c);
            }
            case "/delete" -> {
                Long id = parseId(req.getParameter("id"));
                if (id != null) {
                    dao.delete(id);
                }
            }
            default -> { }
        }
        redirectToList(req, resp);
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, String template,
                        Contact contact, String error) throws IOException {
        IWebExchange exchange = webApp.buildExchange(req, resp);
        WebContext context = new WebContext(exchange, req.getLocale());
        if ("list".equals(template)) {
            context.setVariable("contacts", dao.findAll());
        } else {
            context.setVariable("contact", contact);
        }
        context.setVariable("error", error);
        resp.setContentType("text/html;charset=UTF-8");
        engine.process(template, context, resp.getWriter());
    }

    private void redirectToList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/contacts");
    }

    private static Long parseId(String s) {
        try {
            return (s == null || s.isBlank()) ? null : Long.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String clean(String s) {
        return s == null ? "" : s.trim();
    }
}
