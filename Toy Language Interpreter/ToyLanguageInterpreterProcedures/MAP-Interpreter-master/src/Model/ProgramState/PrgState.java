package Model.ProgramState;

import Model.Statments.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.MyException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIStack<MyIDictionary<String, Value>> symTables;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Value> heapTable;
    private MyIProcTable<String, Pair<List<String>, IStmt>> procTable;
    IStmt originalProgram; //optional field, but good to have
    private int id;
    private static int nextId = 0;

    public PrgState(MyIStack<IStmt> stack, MyIStack<MyIDictionary<String,Value>> symTable, MyIList<Value>
            out, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Value> heapTable, MyIProcTable<String, Pair<List<String>, IStmt>> procTable, IStmt prg){
        this.exeStack = stack;
        this.symTables = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.procTable = procTable;
        this.originalProgram = prg.deepCopy(); //recreate the entire original prg
        this.id = getNextId();
        stack.push(prg);
    }

    public PrgState(IStmt originalProgram){
        this.exeStack = new MyStack<IStmt>();
        this.symTables = new MyStack<>();
        this.symTables.push(new MyDictionary<>());
        this.out = new MyList<Value>();
        this.fileTable = new MyDictionary<StringValue, BufferedReader>();
        this.heapTable = new MyHeap<>();
        this.procTable = new MyProcTable<>();
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

    public MyIStack<MyIDictionary<String, Value>> getSymTable() {
        return symTables;
    }

    public void setSymTable(MyIStack<MyIDictionary<String, Value>> symTable) {
        this.symTables = symTable;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyIDictionary<String , Value> getTopOfSymStack() throws MyException {return
            this.symTables.clone().pop();}

    public MyIProcTable<String, Pair<List<String>, IStmt>> getProcTable() {
        return procTable;
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
                symTables.toString() + "\n" +
                "***** Heap *****\n" +
                heapTable.toString() + "\n" +
                "***** OutputList *****\n" +
                out.toString() + "\n" +
                "***** FileTable *****\n" +
                fileTable.toString() + "\n" +
                "***** ProcTable *****\n" +
                procTable.toString() + "\n" +
                "------------------------------------------------------\n\n\n";
    }
}
