package ru.javawebinar.topjava;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.web.json.JsonUtil;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static ru.javawebinar.topjava.web.json.JsonUtil.*;

public class TestUtil {

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action), clazz);
    }

    public static <T> ResultMatcher writeToJson(T expected) {
        return content().json(writeValue(expected));
    }

    public static <T> ResultMatcher writeToJson(String ignoredProps, T expected) {
        return content().json(writeIgnoreProps(expected, ignoredProps));
    }

    public static <T> ResultMatcher writeToJson(String ignoredProps, T... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), ignoredProps));
    }
}
