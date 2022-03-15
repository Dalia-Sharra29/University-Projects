 package Model.ProgramState;

import Repository.MyException;

import java.util.HashMap;
import java.util.Map;

public class MySemaphoreTable<T, S> implements MyISemaphoreTable<T, S>{
    private Map<T, S> semaphore;
    private int semaphoreAddress;

    public MySemaphoreTable() {
        this.semaphore = new HashMap<>();
        this.semaphoreAddress = 1;
    }

    @Override
    public S lookup(T key) throws MyException {
        S elem = semaphore.get(key);
        if(elem != null)
        {
            return elem;
        }
        throw new MyException("Variable " + key + "is not defined");
    }

    @Override
    public void update(T key, S value) {
        this.semaphore.put(key, value);
    }

    @Override
    public void add(T key, S value) {
        this.semaphore.put(key, value);
    }

    @Override
    public Integer getAddress() {
        int oldAddress = this.semaphoreAddress;
        this.semaphoreAddress ++;
        return oldAddress;
    }

    @Override
    public boolean isDefined(T id) {
        return semaphore.containsKey(id);
    }

    @Override
    public void setTable(Map<T, S> semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public Map<T, S> getContent() {
        return this.semaphore;
    }

    @Override
    public String toString() {
        String result = "";
        for (T key : semaphore.keySet())
            result += key.toString() + " -> " + semaphore.get(key).toString() + ";" + "\n";
        return result;
    }
}
