package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.UserServlet;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepository implements MealRepository{

    private Map<Integer, Meal> mainMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = getLogger(UserServlet.class);

    public InMemoryMealRepository(){
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mainMap.values());
    }

    @Override
    public Meal getById(int id) {
        return mainMap.get(id);
    }

    @Override
    public void create(Meal meal) {
        if (meal.getId() == null){
            meal.setId(counter.incrementAndGet());
        }
        mainMap.put(meal.getId(), new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public void delete(int id) {
        mainMap.remove(id);
    }

}
