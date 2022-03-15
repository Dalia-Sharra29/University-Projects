package Model.ProgramState;

import Repository.MyException;

import java.util.ArrayList;

public interface MyIList<T>{
    boolean addElement(T e);
    T removeElement(int index) throws MyException;
    T getElement(int index) throws MyException;
    boolean isEmpty();
    int size();
    ArrayList<T> getContent();
    String toString();
}
