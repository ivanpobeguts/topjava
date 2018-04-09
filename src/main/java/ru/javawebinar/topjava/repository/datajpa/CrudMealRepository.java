package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    int deleteByUserIdAndId(int userId, int id);

    @Override
    @Modifying
    Meal save(Meal meal);

    @Transactional(readOnly = true)
    Meal findByUserIdAndId(int userId, int id);

    @Transactional(readOnly = true)
    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    @Transactional(readOnly = true)
    List<Meal> findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(
            int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Transactional(readOnly = true)
    @Query("SELECT m FROM Meal m JOIN FETCH m.user u WHERE u.id=?1 AND m.id=?2")
    Optional<Meal> getWithUser(int userId, int id);
}

