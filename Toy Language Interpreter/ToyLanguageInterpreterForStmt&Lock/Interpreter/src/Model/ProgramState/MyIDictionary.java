package Model.ProgramState;

import Repository.MyException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<T, S> {
    S lookup(T key) throws MyException;
    int size();
    void update(T key, S value);
    Set<T> keySet();
    Collection<S> values();
    boolean isDefined(T id);
    void delete(T key) throws MyException;
    String toString();
    Map getContent();
    MyIDictionary<T, S> clone();
}
