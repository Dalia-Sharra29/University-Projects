package Model.ProgramState;

import Repository.MyException;

import java.util.List;
import java.util.Stack;

public interface MyIStack<T>{
    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    int size();
    T top() throws MyException;
    List<T> getValues();
    String toString();
    MyIStack<T> clone();
    Stack<T> getContent();

}
