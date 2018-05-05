package pl.edu.wat;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/servlet")
public class ExampleServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ExampleServlet.class);

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {
        LOGGER.info("ZAREJESTROWANO GET");
        response.getWriter().println("Hello");
    }
}