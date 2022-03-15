package Model.ProgramState;

import Repository.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyLock<T, S> implements MyILock<T, S>{
    private HashMap<T, S> lockTable;
    private int currentAddress;

    public MyLock() {
        this.lockTable = new HashMap<T, S>();
        this.currentAddress = 0;
    }

    @Override
    public synchronized S getElem(T key) throws MyException {
        S elem = lockTable.get(key);
        if(elem != null)
        {
            return elem;
        }
        throw new MyException("Variable " + key + "is not defined");
    }

    @Override
    public int size() {
        return this.lockTable.size();
    }

    @Override
    public void add(T key, S value) {
        this.lockTable.put(key, value);
        this.currentAddress ++;
    }

    @Override
    public synchronized void update(T key, S value) {
        this.lockTable.put(key, value);
    }

    @Override
    public Integer getAddress() {
        this.currentAddress++;
        return this.currentAddress;
    }

    @Override
    public boolean isDefined(T id) {
        return this.lockTable.containsKey(id);
    }

    @Override
    public Map getContent() {
        return this.lockTable;
    }

    @Override
    public MyILock<T, S> clone() {
        MyILock<T , S> copy = new MyLock<>();
        for (T key : this.lockTable.keySet()) {
            copy.add(key, this.lockTable.get(key));
        }
        return copy;
    }

    @Override
    public String toString() {
        String result = "";
        for (T key : lockTable.keySet())
            result += key.toString() + " -> " + lockTable.get(key).toString() + ";" + "\n";
        return result;
    }
}
