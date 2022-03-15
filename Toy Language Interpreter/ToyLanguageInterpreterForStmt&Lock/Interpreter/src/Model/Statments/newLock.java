package Model.Statments;

import Model.ProgramState.*;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newLock implements IStmt{
    private String var;
    private static Lock lock = new ReentrantLock();

    public newLock(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symbolTable= state.getSymTable();
        MyILock<Integer, Integer> lockTable = state.getLockTable();
        int newFreeLocation = lockTable.getAddress();
        symbolTable.update(var, new IntValue(newFreeLocation));
        lockTable.update(newFreeLocation, -1);
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "newLock(" + this.var + ")";
    }
}
