package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    List<Meal> get();
    Meal getById(int id);
    void create(Meal meal);
    void delete (int id);
//    void update (int id, LocalDateTime dateTime, String description, int calories);
}
