package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {


    @Autowired
    private CrudMealRepository mealRepository;

    @Autowired
    private CrudUserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(userRepository.getOne(userId));
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        return mealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return mealRepository.deleteByUserIdAndId(userId, id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return mealRepository.findByUserIdAndId(userId, id).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return mealRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return mealRepository.findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(userId, startDate, endDate);
    }

}
