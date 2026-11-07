package fr.carnet.web;

import fr.carnet.dao.ContactDao;
import fr.carnet.model.Contact;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.nio.file.Path;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

/** Démarre Hibernate (ORM + H2) et Thymeleaf au lancement de l'application. */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        // --- Hibernate + H2 (fichier dans le dossier personnel de l'utilisateur) ---
        String dbPath = Path.of(System.getProperty("user.home"), "carnet-db", "carnet")
                            .toAbsolutePath().toString().replace('\\', '/');
        Configuration cfg = new Configuration()
            .addAnnotatedClass(Contact.class)
            .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
            .setProperty("hibernate.connection.url", "jdbc:h2:file:" + dbPath)
            .setProperty("hibernate.connection.username", "sa")
            .setProperty("hibernate.connection.password", "")
            .setProperty("hibernate.hbm2ddl.auto", "update")
            .setProperty("hibernate.show_sql", "false");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        ctx.setAttribute("sessionFactory", sessionFactory);
        ctx.setAttribute("contactDao", new ContactDao(sessionFactory));

        // --- Thymeleaf sans Spring ---
        JakartaServletWebApplication webApp = JakartaServletWebApplication.buildApplication(ctx);
        WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(webApp);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false); // mettre true en production

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        ctx.setAttribute("webApp", webApp);
        ctx.setAttribute("templateEngine", engine);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactory sf = (SessionFactory) sce.getServletContext().getAttribute("sessionFactory");
        if (sf != null) {
            sf.close();
        }
    }
}
