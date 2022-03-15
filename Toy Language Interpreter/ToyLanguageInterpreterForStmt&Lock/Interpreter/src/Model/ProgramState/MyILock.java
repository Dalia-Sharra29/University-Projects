package Model.ProgramState;

import Repository.MyException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyILock<T, S> {
    S getElem(T key) throws MyException;
    int size();
    void add(T key, S value);
    void update(T key, S value);
    Integer getAddress();
    boolean isDefined(T id);
    String toString();
    Map<T, S> getContent();
    MyILock<T, S> clone();
}
