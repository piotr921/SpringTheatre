package ua.epam.spring.hometask.dao;

public interface CounterAspectDao {

    void addOperationOccurrence(String operationType, String eventName);

    int getOccurancesOfOperationForEvent(String operationType, String eventName);
}
