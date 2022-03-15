package Model.Statments;

import Model.ProgramState.*;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements IStmt{
    private String var;
    private Lock lock = new ReentrantLock();

    public ReleaseStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Value> heap = state.getHeapTable();
        MyISemaphoreTable<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        Value val = symTbl.lookup(var);
        if(val.getType().equals(new IntType()))
        {
            int foundIndex = ((IntValue) val).getVal();
            if(semaphoreTable.isDefined(foundIndex))
            {
                Pair<Integer, List<Integer>> entry = semaphoreTable.lookup(foundIndex);
                List<Integer> list = entry.getValue();
                if(list.contains(state.getId()))
                    list.remove(new Integer(state.getId()));
            }
            else
                throw new MyException("Release Stmt: the index is not in the the Semaphore Table!");
        }
        else
            throw new MyException("Release Stmt: var does not have type int!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var);
        if(typeVar.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Release Stmt: var is not of type int!");
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseStmt(var);
    }

    @Override
    public String toString() {
        return "release(" + var + ");";
    }
}
