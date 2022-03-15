package Model.Expressions;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;

public class ArithExp implements Exp{
    private Exp e1;
    private Exp e2;
    private char op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(Exp e1, Exp e2, char op) {
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

    public char getOp() {
        return op;
    }

    public void setOp(char op) {
        this.op = op;
    }

    public Value eval(MyIDictionary<String,Value> symTable, MyIHeap<Value> heapTable) throws MyException {
        Value v1,v2;
        v1 = e1.eval(symTable, heapTable);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(symTable, heapTable);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op == '+') return new IntValue(n1+n2);
                else if (op == '-') return new IntValue(n1-n2);
                else if (op == '*') return new IntValue(n1*n2);
                else if (op == '/'){
                    if(n2 == 0) throw new MyException("division by zero");
                        else return new IntValue(n1/n2);
                }
                else
                    throw new MyException("Unknown operation");
            } else {
                throw new MyException("second operand is not an integer");
            }
        } else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return "(" + e1.toString() + op + e2.toString() + ")" ;
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(e1.deepCopy(), e2.deepCopy(), op) {
        };
    }
}
