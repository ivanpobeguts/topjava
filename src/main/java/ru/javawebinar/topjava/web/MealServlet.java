package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealRepository;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final Logger log = getLogger(UserServlet.class);
    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
        repository.create(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        repository.create(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        repository.create(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        repository.create(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        repository.create(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        repository.create(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("ACTION");
        if (action.equals("create")) {
            String description = request.getParameter("description");
            int calories = Integer.valueOf(request.getParameter("calories"));
            LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
            log.info("DESC: " + description);
            log.info("CAL: " + calories);
            log.info("DATE: " + date);
            repository.create(date, description,calories);
        }
        else if (action.equals("remove")) {
            String id = request.getParameter("id");
            log.info("ID: " + id);
            repository.delete(Integer.parseInt(id));
        }

        response.sendRedirect("/topjava/meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(repository.get(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        request.setAttribute("mealList", mealsWithExceeded);
        request.setAttribute("formatter", formatter);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
