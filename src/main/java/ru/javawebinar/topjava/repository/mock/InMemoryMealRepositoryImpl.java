package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
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

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), userId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), userId);

        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), adminId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), adminId);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), adminId);

    }

    @Override
    public Meal save(Meal meal, int userId) {
        Integer id = meal.getId();
        if (meal.isNew()) {
            id = counter.incrementAndGet();
            meal.setId(id);
        }
        // treat case: update, but absent in storage
        Map<Integer, Meal> map = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        map.put(id, meal);
        log.info("save {}", meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        log.info("delete userId {}", userId);
        if (map != null && map.containsKey(id)) {
            map.remove(id);
        }
        log.info("delete {}", id);
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        log.info("get {}", id, userId);
        return map == null ? null : map.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.get(userId).values().stream().sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());

    }
}

