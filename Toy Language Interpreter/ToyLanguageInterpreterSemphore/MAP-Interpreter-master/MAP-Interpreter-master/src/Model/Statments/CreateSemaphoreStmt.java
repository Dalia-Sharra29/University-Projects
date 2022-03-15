package Model.Statments;

import Model.Expressions.Exp;
import Model.ProgramState.*;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStmt implements IStmt{
    private String var;
    private Exp exp;
    private Lock lock = new ReentrantLock();

    public CreateSemaphoreStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Value> heap = state.getHeapTable();
        MyISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        Value val = exp.eval(symTbl, heap);
        if(val.getType().equals(new IntType()))
        {
            int value = ((IntValue) val).getVal();
            int newFreeLocation = semaphoreTable.getAddress();
            semaphoreTable.add(newFreeLocation, new Pair<>(value, new ArrayList<>()));
            if(symTbl.lookup(var).getType().equals(new IntType()))
            {
                symTbl.update(var, new IntValue(newFreeLocation));
            }
            else
                throw new MyException("Create semaphore: variable not defined/ not of type int");
        }
        else throw new MyException("Create semaphore: expression value is not int");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var);
        Type typeExp = exp.typecheck(typeEnv);
        if(typeVar.equals(new IntType()))
            if(typeExp.equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("CreateSemaphore: expression is not of type int!");
        else
            throw new MyException("CreateSemaphore: var is not of type int!");
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(var, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "createSemaphore( " + var + ", " + exp.toString() + ");";
    }
}
