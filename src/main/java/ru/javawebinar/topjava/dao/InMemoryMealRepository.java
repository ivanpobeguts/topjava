package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository{

    private Map<Integer, Meal> mainMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository(){
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> get() {
        return new ArrayList<>(mainMap.values());
    }

    @Override
    public Meal getById(int id) {
        return mainMap.get(id);
    }

    @Override
    public void create(Meal meal) {
        if (meal.getId() != null){
            mainMap.put(meal.getId(), new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId()));
        }
        else {
            int newId = counter.incrementAndGet();
            mainMap.put(newId, new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), newId));
        }
    }

    @Override
    public void delete(int id) {
        mainMap.remove(id);
    }

}
