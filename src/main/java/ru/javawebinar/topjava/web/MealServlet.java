package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealRepository;
import ru.javawebinar.topjava.dao.MealRepository;
import ru.javawebinar.topjava.model.Meal;
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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("ACTION");
        String id = request.getParameter("id");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String date = request.getParameter("date");
        switch (action) {
            case "create": {
                repository.create(new Meal(LocalDateTime.parse(date), description, Integer.valueOf(calories)));
                break;
            }
            case "remove":
                repository.delete(Integer.parseInt(id));
                break;
            case "edit": {
                repository.create(new Meal(Integer.parseInt(id), LocalDateTime.parse(date), description, Integer.valueOf(calories)));
                break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceeded(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("mealList", mealsWithExceeded);
            request.setAttribute("formatter", formatter);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        else if (action.equals("edit")){
            String id = request.getParameter("id");
            Meal meal = repository.getById(Integer.parseInt(id));
            request.setAttribute("meal", meal);
            request.setAttribute("formatter", formatter);
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
        }
    }
}
