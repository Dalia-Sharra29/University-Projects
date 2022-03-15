package Model.Statments;

import Model.Expressions.Exp;
import Model.ProgramState.*;
import Model.Statments.IStmt;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt {
    private String var;
    private Exp exp;
    private Lock lock = new ReentrantLock();

    public NewLatchStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Value> heap = state.getHeapTable();
        MyILatchTable<Integer, Integer> latchTable = state.getLatchTable();

        Value val = exp.eval(symTbl, heap);
        if(val.getType().equals(new IntType()))
        {
            IntValue value = (IntValue) val;
            int num1 = value.getVal();
            int newFreeLocation = latchTable.getAddress();
            latchTable.update(newFreeLocation, num1);
            if(symTbl.lookup(var).getType().equals(new IntType()))
            {
                symTbl.update(var, new IntValue(newFreeLocation));
            }
            else
                throw new MyException("NewLatch: var is not of type int/not defined!");
        }
        else
            throw new MyException("NewLatch: exp is not of type int!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var);
        Type typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new IntType()) && typeExp.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("NewLatch: var and expression ar not of type int!");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " + exp + ")";
    }
}
