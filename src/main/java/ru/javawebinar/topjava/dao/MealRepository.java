package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    List<Meal> getAll();
    Meal getById(int id);
    Meal create(Meal meal);
    void delete (int id);
}
