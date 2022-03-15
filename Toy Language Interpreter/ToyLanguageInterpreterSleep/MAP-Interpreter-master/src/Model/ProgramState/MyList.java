package Model.ProgramState;


import Model.ProgramState.MyIList;
import Repository.MyException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    public MyList() {
        this.list = new ArrayList<T>();
    }

    @Override
    public boolean addElement(T e) { return list.add(e); }

    @Override
    public T removeElement(int index) throws MyException {
        if(list.isEmpty())
            throw new MyException("List is empty!");
        return list.remove(index);
    }

    @Override
    public T getElement(int index) throws MyException{
        if(list.isEmpty())
            throw new MyException("List is empty!");
        return list.get(index);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public ArrayList<T> getContent() {
        return this.list;
    }

    @Override
    public String toString() {
        String result = "";

        for (T e : this.list) {
            result += e.toString() + "\n";
        };
        return result;
    }
}
