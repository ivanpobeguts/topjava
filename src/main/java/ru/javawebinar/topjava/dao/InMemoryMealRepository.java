package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository{

    private Map<Integer, Meal> mainMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository(){

    }

    @Override
    public List<Meal> get() {
        return new ArrayList<>(mainMap.values());
    }

    @Override
    public void create(LocalDateTime dateTime, String description, int calories) {
        int id = counter.incrementAndGet();
        mainMap.put(id, new Meal(dateTime, description, calories, id));
    }

    @Override
    public void delete(int id) {
        mainMap.remove(id);
    }

    @Override
    public void update(int id) {

    }
}
