package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500)

        );

        for (UserMealWithExceed m : getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)) {
            System.out.println(m.getDateTime() + ", " + m.isExceed());
        }
//        .toLocalDate();
//        .toLocalTime();
    }


    /*
    Method with loops
     */
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> daysMap = new HashMap<>();
        mealList.forEach(m -> daysMap.merge(m.getDateTime().toLocalDate(), m.getCalories(), (m1, m2) -> m1 + m2));
        List<UserMealWithExceed> result = new ArrayList<>();
        mealList.forEach(m -> {
            if (TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                result.add(new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), daysMap.getOrDefault(m.getDateTime().toLocalDate(), 0) > caloriesPerDay));
        });
        return result;
    }


    /*
    Method with Streams
     */
    public static List<UserMealWithExceed> getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<Object, Integer> daysMap = mealList.stream()
                .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map((m) -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), daysMap.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
