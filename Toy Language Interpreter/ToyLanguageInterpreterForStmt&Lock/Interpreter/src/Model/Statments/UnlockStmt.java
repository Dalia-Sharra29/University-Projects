package Model.Statments;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyILock;
import Model.ProgramState.PrgState;
import Model.Types.Type;
import Model.Values.Value;
import Repository.MyException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStmt implements IStmt{
    private String var;
    private static Lock lock = new ReentrantLock();

    public UnlockStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symbolTable= state.getSymTable();
        MyILock<Integer, Integer> lockTable = state.getLockTable();

        Integer foundIndex = Integer.parseInt(symbolTable.lookup(var).toString());

        if(lockTable.isDefined(foundIndex))
        {
            if(lockTable.getElem(foundIndex) == state.getId())
                lockTable.update(foundIndex, -1);
        }
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
        return "unlock( " + var + " )";
    }
}
