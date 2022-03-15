package Model.Statments;

import Model.ProgramState.*;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt{
    private String var;
    private Lock lock = new ReentrantLock();

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyILatchTable<Integer, Integer> latchTable = state.getLatchTable();

        Value val = symTbl.lookup(var);
        if(val.getType().equals(new IntType()))
        {
            IntValue value = (IntValue) val;
            int foundIndex = value.getVal();
            if(latchTable.isDefined(foundIndex))
            {
                if(latchTable.lookup(foundIndex) != 0)
                    stk.push(new AwaitStmt(var));
            }
            else
                throw new MyException("Await: foundIndex is not in the latch table!");
        }
        else
            throw new MyException("Await: var is not of type int!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var);
        if (typeVar.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Await: var is not of type int!");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}
