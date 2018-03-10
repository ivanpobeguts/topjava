package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.adminId;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.userId;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), userId);

    }

    @Override
    public Meal save(Meal meal, int userId) {
        Integer id = meal.getId();
        if (meal.isNew()) {
            id = counter.incrementAndGet();
            meal.setId(counter.incrementAndGet());
        }
        // treat case: update, but absent in storage
        Map<Integer, Meal> map = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        map.put(id, meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        if (map != null && map.containsKey(id)) {
            map.remove(id);
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        return map == null ? null : map.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream().sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());

    }
}

