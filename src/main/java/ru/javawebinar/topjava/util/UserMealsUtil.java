package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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

        for (UserMealWithExceed m : getFilteredWithExceeded2(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)) {
            System.out.println(m.getDateTime() + ", " + m.isExceed());
        }
//        .toLocalDate();
//        .toLocalTime();
    }


    /*
    Method with loops
     */
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> userMealListExceed = new ArrayList<>();
        LocalDate date = mealList.get(0).getDateTime().toLocalDate();
        int counter = 0;
        Map<LocalDate, Boolean> daysMap = new HashMap<>();
        List<UserMeal> filteredMealList = new ArrayList<>();

        mealList.sort(Comparator.comparing(UserMeal::getDateTime));
        for (UserMeal m : mealList) {
            // Если мы перешли к следующему дню, то проверяем, превысили ли мы сумму калорий. Кладём соответствующее и предыдущую дату во временный Map.
            if (!m.getDateTime().toLocalDate().equals(date)) {
                if (counter > caloriesPerDay) {
                    daysMap.put(date, true);
                } else{
                    daysMap.put(date, false);
                }
                date = m.getDateTime().toLocalDate();
                counter = 0;
            }
            counter += m.getCalories();
            // Если это последний элемент списка, то проверяем, превысили ли мы сумму калорий. Кладём соответствующее и текущую дату во временный Map.
            if (mealList.indexOf(m) == (mealList.size() -1)) {
                if (counter > caloriesPerDay) {
                    daysMap.put(m.getDateTime().toLocalDate(), true);
                } else{
                    daysMap.put(m.getDateTime().toLocalDate(), false);
                }
            }
            // Фильтруем лист по времени, кладём значения во временный list.
            if (TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredMealList.add(new UserMeal(m.getDateTime(), m.getDescription(), m.getCalories()));
            }
        }

        // Проходим по временному списку и создаём конечный список с UserMealWithExceed. Используем данные из Map, сформированном в первом цикле.
        for (UserMeal m : filteredMealList) {
            userMealListExceed.add(new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), daysMap.get(m.getDateTime().toLocalDate())));
        }
        return userMealListExceed;
    }


    /*
    Method with Streams
     */
    public static List<UserMealWithExceed> getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final LocalDate[] date = {mealList.get(0).getDateTime().toLocalDate()};
        final int[] counter = {0};
        final Map<LocalDate, Boolean> daysMap = new HashMap<>();
        final List<UserMeal> filteredMealList = new ArrayList<>();

        mealList.sort(Comparator.comparing(UserMeal::getDateTime));

        mealList.stream()
            .sorted(Comparator.comparing(UserMeal::getDateTime))
            .forEach((m) -> {
                if (!m.getDateTime().toLocalDate().equals(date[0])) {
                    if (counter[0] > caloriesPerDay) {
                        daysMap.put(date[0], true);
                    } else{
                        daysMap.put(date[0], false);
                    }
                    date[0] = m.getDateTime().toLocalDate();
                    counter[0] = 0;
                }
                counter[0] += m.getCalories();
                if (mealList.indexOf(m) == (mealList.size() -1)) {
                    if (counter[0] > caloriesPerDay) {
                        daysMap.put(m.getDateTime().toLocalDate(), true);
                    } else{
                        daysMap.put(m.getDateTime().toLocalDate(), false);
                    }
                }
                if (TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime)) {
                    filteredMealList.add(new UserMeal(m.getDateTime(), m.getDescription(), m.getCalories()));
                }
            });

        return filteredMealList.stream()
                .map((m) -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), daysMap.get(m.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }
}
