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

    private Map<Operations, Map<String, Integer>> methodCallCounter;

    public CounterAspect() {
        methodCallCounter = new HashMap<>();
        methodCallCounter.put(Operations.GET_EVENT_BY_NAME, new HashMap<>());
    }

    @Before("execution(* ua.epam.spring.hometask.service.EventService.getByName(..)) && args(name)")
    public void countHowManyTimesEventAccessedByName(String name) {
        incrementCounter(Operations.GET_EVENT_BY_NAME, name);
    }

    public int getNoOfGetEventByNameOperations(String eventName) {
        return methodCallCounter.get(Operations.GET_EVENT_BY_NAME).get(eventName);
    }

    private void incrementCounter(Operations operation, String methodName) {
        Map<String, Integer> operationCounter = methodCallCounter.get(operation);
        operationCounter.merge(methodName, 1, (key, value) -> value + 1);
        methodCallCounter.put(operation, operationCounter);
    }
}
