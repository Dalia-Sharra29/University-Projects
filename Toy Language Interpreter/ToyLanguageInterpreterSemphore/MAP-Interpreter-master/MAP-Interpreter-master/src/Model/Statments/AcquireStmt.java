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

public class AcquireStmt implements IStmt{
    private String var;
    private Lock lock = new ReentrantLock();

    public AcquireStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIStack<IStmt> stk = state.getExeStack();
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
                int NL = list.size();
                int N1 = entry.getKey();
                if(N1 > NL)
                {
                    if(!list.contains(state.getId()))
                        list.add(state.getId());
                }
                else
                {
                    stk.push(new AcquireStmt(var));
                }
            }
            else
                throw new MyException("Acquire Stmt: the index is not in the Semaphore Table!");
        }
        else
            throw new MyException("Acquire Stmt: var does not have type int!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var);
        if(typeVar.equals(new IntType()))
                return typeEnv;
        else
            throw new MyException("Acquire Stmt: var is not of type int!");
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStmt(var);
    }

    @Override
    public String toString() {
        return "acquire(" + var + ");";
    }
}
