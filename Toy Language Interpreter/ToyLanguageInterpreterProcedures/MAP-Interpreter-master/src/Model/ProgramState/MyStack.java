package Model.ProgramState;


import Model.ProgramState.MyIStack;
import Repository.MyException;

import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack; //a field whose type CollectionType is an appropriate
                             // generic java library collection

    public MyStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws MyException {
        if (stack.isEmpty())
            throw new MyException("Stack is empty!");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public T top() throws MyException {
        if (stack.isEmpty())
            throw new MyException("Stack is empty!");
        return stack.get(0);
    }

    @Override
    public List<T> getValues() {
        return stack.subList(0,stack.size());
    }

    @Override
    public String toString() {
        String result = "";
        for (T el : this.stack) {
            result = el.toString() + ";\n" + result;
        }
        result += "\n";
        return result;
    }

    @Override
    public MyIStack<T> clone() {
        Stack<T> stk = new Stack<>();
        this.stack
                .forEach(stk::push);
        MyStack<T> copyStack = new MyStack<>();
        copyStack.stack = stk;
        return copyStack;
    }

    @Override
    public Stack<T> getContent() {
        return this.stack;
    }
}