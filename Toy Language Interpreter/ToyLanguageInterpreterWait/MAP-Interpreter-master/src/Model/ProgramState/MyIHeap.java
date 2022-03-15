package Model.ProgramState;

import java.util.Map;

public interface MyIHeap<T> {
    int allocate(T val);
    T get(int address);
    void put(int address, T val);
    T deallocate(int address);
    Map<Integer,T> getContent();
    void setContent(Map<Integer,T> content);
}
