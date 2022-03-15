package Model.ProgramState;

import Repository.MyException;

import java.util.HashMap;
import java.util.Map;

public class MyProcTable<T, S> implements MyIProcTable<T, S>{
    private HashMap<T, S> procTable;

    public MyProcTable() {
        this.procTable = new HashMap<T, S>();
    }

    @Override
    public S lookup(T key) throws MyException {
        S elem = procTable.get(key);
        if(elem != null)
        {
            return elem;
        }
        throw new MyException("Variable " + key + "is not defined in ProcTable!");
    }

    @Override
    public void update(T key, S value) {
        procTable.put(key, value);
    }

    @Override
    public boolean isDefined(T id) {
        return procTable.containsKey(id);
    }

    @Override
    public Map<T, S> getContent() {
        return this.procTable;
    }

    @Override
    public MyIProcTable<T, S> clone() {
        MyIProcTable<T , S> copy = new MyProcTable<>();
        for (T key : this.procTable.keySet()) {
            copy.update(key, this.procTable.get(key));
        }
        return copy;
    }

    @Override
    public String toString() {
        String result = "";
        for (T key : procTable.keySet())
            result += key.toString() + " -> " + procTable.get(key).toString() + ";" + "\n";
        return result;
    }
}
