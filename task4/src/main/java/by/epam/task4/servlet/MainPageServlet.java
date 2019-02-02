package by.epam.task4.servlet;

import by.epam.task4.exception.TaskException;
import by.epam.task4.model.Medicines;
import by.epam.task4.service.MedicineService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Main servlet for this application that allows browser to get response
 * with medicine data.
 *
 * @author Rostislav Pekhovksy 2018
 * @version 0.1
 */
@WebServlet("")
@Log4j2
public class MainPageServlet extends HttpServlet {

    /**
     * Service object with methods to indulge requests from user
     * about type of parsers.
     */
    private MedicineService service;

    /**
     * This method performs when there is first request to this servlet.
     * Initializes variables.
     *
     * @param config servlet config file.
     */
    @Override
    public void init(final ServletConfig config) {
        try {
            service = new MedicineService();
        } catch (TaskException e) {
            log.fatal(e.getMessage());
        }
    }

    /**
     * @param req  request object (contains request data)
     * @param resp response object
     * @throws IOException      when jsp page path is not found
     * @throws ServletException if there is a trouble to forward to page
     */
    @Override
    protected void doGet(final HttpServletRequest req,
                         final HttpServletResponse resp)
            throws IOException, ServletException {
        try {
            Medicines medicines;
            String parserType = req.getParameter("parserType");
            if (parserType == null) {
                parserType = "sax";
            }

            switch (parserType) {
                case "sax":
                    medicines = service.findMedicinesUsingSax();
                    break;

                case "stax":
                    medicines = service.findMedicinesUsingStax();
                    break;

                case "dom":
                    medicines = service.findMedicinesUsingDom();
                    break;

                default:
                    throw new TaskException("Invalid parser type");
            }

            req.setAttribute("parserType", parserType);
            req.setAttribute("medicines", medicines.getMedicineList());
            req.getRequestDispatcher("/index.jsp").forward(req, resp);

        } catch (TaskException e) {
            req.setAttribute("errorMessage", e.getMessage()
                    + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }

    }
}
