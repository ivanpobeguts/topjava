package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;


    @Override
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return repository.get(id, userId);
    }

    @Override
    public Meal update(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new ArrayList<>(repository.getAll(userId));
    }
}