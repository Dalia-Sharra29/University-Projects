package Model.Expressions;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;

public class RelExp implements Exp{
    private Exp e1;
    private Exp e2;
    private String op;

    public RelExp(Exp e1, Exp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
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
        if (nr1.getType().equals(new IntType())) {
            nr2 = e2.eval(symTable, heapTable);
            if (nr2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) nr1;
                IntValue i2 = (IntValue) nr2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op.equals("<")) return new BoolValue(n1 < n2);
                else if (op.equals("<=")) return new BoolValue(n1 <= n2);
                else if (op.equals(">")) return new BoolValue(n1 > n2);
                else if (op.equals(">=")) return new BoolValue(n1 >= n2);
                else if (op.equals("==")) return new BoolValue(n1 == n2);
                else if (op.equals("!=")) return new BoolValue(n1 != n2);
                else
                    throw new MyException("Unknown operation");
            } else {
                throw new MyException("second operand is not a int");
            }
        } else
            throw new MyException("first operand is not a int");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if(type1.equals(new IntType()))
        {
            if(type2.equals(new IntType()))
            {
                return new BoolType();
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public Exp deepCopy() {
        return new RelExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString() {
        return "(" + e1.toString() + op + e2.toString() +")";
    }
}
