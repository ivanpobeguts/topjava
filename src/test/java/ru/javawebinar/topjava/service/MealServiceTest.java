package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService service;

    @Test
    public void get() throws Exception {
        assertThat(meal1.toString(), is(service.get(MEAL_ID, START_SEQ).toString()));
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(MEAL_ID, START_SEQ + 1);
    }

    @Test
    public void delete() throws Exception{
        service.delete(MEAL_ID, START_SEQ);
        assertThat(Arrays.asList(meal6, meal5, meal4, meal3, meal2).toString(), is(service.getAll(START_SEQ).toString()));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(MEAL_ID, START_SEQ + 1);
    }

    @Test
    public void getBetweenDateTimes() {
        assertThat(Arrays.asList(meal2, meal1).toString(),
                is(service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 8, 0),
                        LocalDateTime.of(2015, Month.MAY, 30, 14, 0), START_SEQ).toString()));
    }

    @Test
    public void getAll() throws Exception {
        assertThat(Arrays.asList(meal6, meal5, meal4, meal3, meal2, meal1).toString(), is(service.getAll(START_SEQ).toString()));
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 600);
        service.update(updated, START_SEQ);
        assertThat(updated.toString(), is(service.get(MEAL_ID, START_SEQ).toString()));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal meal = service.get(MEAL_ID,START_SEQ);
        service.update(meal, START_SEQ + 1);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 600);
        service.create(newMeal, START_SEQ);
        assertThat(Arrays.asList(meal6, meal5, meal4, meal3, meal2, meal1, newMeal).toString(), is(service.getAll(START_SEQ).toString()));
    }
}