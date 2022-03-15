package Model.Expressions;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;
import Repository.MyException;

public class NotExp implements Exp{
    private Exp exp;

    public NotExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heapTable) throws MyException {
        Value val = exp.eval(symbolTable, heapTable);
        if (val.getType().equals(new BoolType())) {
            BoolValue value = (BoolValue) val;
            return new BoolValue(!value.getVal());
        }
        else
            throw new MyException("NotExpression: " + exp.toString()+ " is not of type bool!");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp =  exp.typecheck(typeEnv);
        if(typeExp.equals(new BoolType()))
            return new BoolType();
        else
            throw new MyException("NotExpression: " + exp.toString()+ " is not of type bool!");
    }

    @Override
    public Exp deepCopy() {
        return new NotExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "!(" + exp.toString() + ")";
    }
}
