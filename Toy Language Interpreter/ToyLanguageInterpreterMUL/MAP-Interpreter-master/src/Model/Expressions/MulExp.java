package Model.Expressions;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;

public class MulExp implements Exp{
    private Exp exp1, exp2;

    public MulExp(Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heapTable) throws MyException {
        Value nr1,nr2;
        nr1 = exp1.eval(symbolTable, heapTable);
        if (nr1.getType().equals(new IntType())) {
            nr2 = exp2.eval(symbolTable, heapTable);
            if (nr2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) nr1;
                IntValue i2 = (IntValue) nr2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                return new IntValue((n1*n2) - (n1+n2));
            }
            else {
                throw new MyException("second operand is not an integer");
            }
        } else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = exp1.typecheck(typeEnv);
        type2 = exp2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public Exp deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "MUL(" + exp1 + ", " + exp2 + ")";
    }
}
