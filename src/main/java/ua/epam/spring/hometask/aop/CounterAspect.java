package ua.epam.spring.hometask.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CounterAspect {

    enum Operations {
        GET_EVENT_BY_NAME
    }

    private Map<Operations, Integer> counter;

    public CounterAspect() {
        counter = new HashMap<>();
        counter.put(Operations.GET_EVENT_BY_NAME, 0);
    }

    @Before("execution(* ua.epam.spring.hometask.service.EventService.getByName(..)) && args(name)")
    public void countHowManyTimesEventAccessedByName(String name) {
        incrementCounter(Operations.GET_EVENT_BY_NAME);
    }

    public int getNoOfGetEventByNameOperations() {
        return counter.get(Operations.GET_EVENT_BY_NAME);
    }

    private void incrementCounter(Operations operation) {
        Integer noOfExecutions = counter.get(operation) + 1;
        counter.put(operation, noOfExecutions);
    }
}
