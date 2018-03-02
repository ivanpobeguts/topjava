package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    List<Meal> get();
    void create(LocalDateTime dateTime, String description, int calories);
    void delete (int id);
    void update (int id);
}
