package Model.ProgramState;

import Repository.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T, S> implements MyIDictionary<T, S> {
    private HashMap<T, S> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<T, S>();
    }

    @Override
    public S lookup(T key) throws MyException{
        S elem = dictionary.get(key);
        if(elem != null)
        {
            return elem;
        }
        throw new MyException("Variable " + key + "is not defined");
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public void update(T key, S value) {
        dictionary.put(key, value);
    }

    @Override
    public Set<T> keySet() {
        return dictionary.keySet();
    }

    @Override
    public Collection<S> values() {
        return dictionary.values();
    }

    @Override
    public boolean isDefined(T id) {
        return dictionary.containsKey(id);
    }

    @Override
    public void delete(T key) throws MyException {
        if (!dictionary.containsKey(key))
            throw new MyException("Key doesn't exist.");
        dictionary.remove(key);
    }

    @Override
    public Map getContent() {
        return this.dictionary;
    }

    @Override
    public MyIDictionary<T, S> clone() {
        MyIDictionary<T , S> copy = new MyDictionary<>();
        for (T key : this.keySet()) {
            copy.update(key, this.dictionary.get(key));
        }
        return copy;
    }

    @Override
    public String toString() {
        String result = "";
        for (T key : dictionary.keySet())
            result += key.toString() + " -> " + dictionary.get(key).toString() + ";" + "\n";
        return result;
    }
}
