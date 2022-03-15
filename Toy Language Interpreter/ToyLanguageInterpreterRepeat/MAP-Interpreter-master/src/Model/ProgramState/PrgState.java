package Model.ProgramState;

import Model.Statments.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.MyException;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Value> heapTable;
    IStmt originalProgram; //optional field, but good to have
    private int id;
    private static int nextId = 0;

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String,Value> symTable, MyIList<Value>
            out, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Value> heapTable, IStmt prg){
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.originalProgram = prg.deepCopy(); //recreate the entire original prg
        this.id = getNextId();
        stack.push(prg);
    }

    public PrgState(IStmt originalProgram){
        this.exeStack = new MyStack<IStmt>();
        this.symTable = new MyDictionary<String,Value>();
        this.out = new MyList<Value>();
        this.fileTable = new MyDictionary<StringValue, BufferedReader>();
        this.heapTable = new MyHeap<>();
        this.id = getNextId();
        this.exeStack.push(originalProgram);
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public static synchronized int getNextId() {
        nextId++;
        return nextId;
    }

    public int getId(){
        return this.id;
    }

    public MyIHeap<Value> getHeapTable() {
        return heapTable;
    }

    public void setHeapTable(MyIHeap<Value> heapTable) {
        this.heapTable = heapTable;
    }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty()) {
            throw new MyException("Prgstate stack is empty");
        }
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

        @Override
    public String toString() {
        return  "Thread number id: " + this.id + "\n" +
                "------------------------------------------------------\n" +
                "***** ExecutionStack *****\n" +
                exeStack.toString() + "\n" +
                "***** SymbolTable *****\n" +
                symTable.toString() + "\n" +
                "***** Heap *****\n" +
                heapTable.toString() + "\n" +
                "***** OutputList *****\n" +
                out.toString() + "\n" +
                "***** FileTable *****\n" +
                fileTable.toString() + "\n" +
                "------------------------------------------------------\n\n\n";
    }
}
