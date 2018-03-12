package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        log.info("getAll");
        log.info("ID ", userId);
        return MealsUtil.getFilteredWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay(), LocalTime.MIN, LocalTime.MAX);
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = AuthorizedUser.id();
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = AuthorizedUser.id();
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealWithExceed> getFilteredByDate(LocalTime start, LocalTime end) {
        int userId = AuthorizedUser.id();
        log.info("getFilteredByDate");
        log.info("ID ", userId);
        return MealsUtil.getFilteredWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay(), start, end);
    }

}