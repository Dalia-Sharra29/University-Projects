package Model.Expressions;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;
import Repository.MyException;

public class LogicExp implements Exp{
    private Exp e1;
    private Exp e2;
    private String op;

    public LogicExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op; // "and" / "or"
    }

    public Exp getE1() {
        return e1;
    }

    public void setE1(Exp e1) {
        this.e1 = e1;
    }

    public Exp getE2() {
        return e2;
    }

    public void setE2(Exp e2) {
        this.e2 = e2;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap<Value> heapTable) throws MyException {
        Value nr1,nr2;
        nr1 = e1.eval(symTable, heapTable);
        if (nr1.getType().equals(new BoolType())) {
            nr2 = e2.eval(symTable, heapTable);
            if (nr2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue) nr1;
                BoolValue i2 = (BoolValue) nr2;
                boolean n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op.equals("and")) return new BoolValue(n1 && n2);
                else if (op.equals("or")) return new BoolValue(n1 | n2);
                else
                    throw new MyException("Unknown operation");
            } else {
                throw new MyException("second operand is not a boolean");
            }
        } else
            throw new MyException("first operand is not a boolean");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if(type1.equals(new BoolType()))
        {
            if(type2.equals(new BoolType()))
            {
                return new BoolType();
            } else
                throw new MyException("second operand is not a bool");
        } else
            throw new MyException("first operand is not a bool");
    }

    @Override
    public String toString() {
        return "(" + e1.toString() + op + e2.toString() +")";
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }
}
