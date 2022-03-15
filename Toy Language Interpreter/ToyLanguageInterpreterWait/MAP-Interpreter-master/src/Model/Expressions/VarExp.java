package Model.Expressions;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;
import Repository.MyException;

public class VarExp implements Exp{
    private String id;

    public VarExp(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Value> heapTable) throws MyException {
        return symTable.lookup(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }
}
