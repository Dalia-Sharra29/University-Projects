package Model.ProgramState;

import Repository.MyException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyILatchTable<T, S> {
    S lookup(T key) throws MyException;
    void update(T key, S value);
    void add(T key, S value);
    Integer getAddress();
    boolean isDefined(T id);
    void setTable(Map<T, S> latch);
    Map getContent();
}
