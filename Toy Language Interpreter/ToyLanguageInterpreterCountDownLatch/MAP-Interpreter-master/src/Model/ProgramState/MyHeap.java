package Model.ProgramState;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<T> implements MyIHeap<T>{
    private HashMap<Integer, T> heap;
    private int nextPosition;

    public MyHeap() {
        this.heap = new HashMap<Integer, T>();
        this.nextPosition = 0;
    }

    @Override
    public int allocate(T val) {
        this.nextPosition ++;
        this.heap.put(this.nextPosition, val);
        return this.nextPosition;
    }

    @Override
    public T get(int address) {
        return this.heap.get(address);
    }

    @Override
    public void put(int address, T val) {
        this.heap.put(address, val);
    }

    @Override
    public T deallocate(int address) {
        return this.heap.remove(address);
    }

    @Override
    public Map<Integer, T> getContent() {
        return this.heap;
    }

    @Override
    public void setContent(Map<Integer, T> content) {
        this.heap = (HashMap) content;
    }

    @Override
    public String toString() {
        String s = "";
        for(HashMap.Entry<Integer,T> entry : this.heap.entrySet()){
            s += entry.getKey().toString() + "->" + entry.getValue().toString() + "\n";
        }
        return s;
    }
}
