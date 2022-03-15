package Model.Expressions;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.MyException;

public class HeapReadExp implements Exp{

    private Exp expression;

    public HeapReadExp(Exp expression) {
        this.expression = expression;
    }

    public Exp getExpression() {
        return expression;
    }

    public void setExpression(Exp expression) {
        this.expression = expression;
    }


    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeap<Value> heapTable) throws MyException {
        Value val = expression.eval(symbolTable, heapTable);
        if(val.getType() instanceof RefType)
        {
            int address = ((RefValue) val).getAddress();
            if(heapTable.get(address) != null)
            {
                return heapTable.get(address);
            }
            else
            {
                throw new MyException("The address is not a key in the heapTable!");
            }
        }
        else
        {
            throw new MyException("The expression is not a RefValue.");
        }
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typecheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public Exp deepCopy() {
        return new HeapReadExp(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }
}
