package Model.ProgramState;

import Repository.MyException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIProcTable<T, S>{
    S lookup(T key) throws MyException;
    void update(T key, S value);
    boolean isDefined(T id);
    Map<T, S> getContent();
    MyIProcTable<T, S> clone();
}
