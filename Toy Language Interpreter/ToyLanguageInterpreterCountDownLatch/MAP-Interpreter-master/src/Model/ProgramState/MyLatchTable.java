package Model.ProgramState;

import Repository.MyException;

import java.util.HashMap;
import java.util.Map;

public class MyLatchTable<T, S> implements MyILatchTable<T, S>{
    private Map<T, S> latch;
    private int latchAddress;

    public MyLatchTable() {
        this.latch = new HashMap<T, S>();
        this.latchAddress = 1;
    }

    @Override
    public S lookup(T key) throws MyException {
        S elem = latch.get(key);
        if(elem != null)
        {
            return elem;
        }
        throw new MyException("Variable " + key + "is not defined");
    }

    @Override
    public void update(T key, S value) {
        this.latch.put(key, value);
    }

    @Override
    public void add(T key, S value) {
        this.latch.put(key, value);
        this.latchAddress += 1;
    }

    @Override
    public Integer getAddress() {
        this.latchAddress++;
        return this.latchAddress;
    }

    @Override
    public boolean isDefined(T id) {
        return this.latch.containsKey(id);
    }

    @Override
    public void setTable(Map<T, S> latch) {
        this.latch = latch;
    }

    @Override
    public Map getContent() {
        return this.latch;
    }

    @Override
    public String toString() {
        String result = "";
        for (T key : latch.keySet())
            result += key.toString() + " -> " + latch.get(key).toString() + ";" + "\n";
        return result;
    }
}
