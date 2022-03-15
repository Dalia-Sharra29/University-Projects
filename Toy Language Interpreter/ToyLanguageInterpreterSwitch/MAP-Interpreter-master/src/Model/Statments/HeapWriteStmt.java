package Model.Statments;

import Model.Expressions.Exp;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.MyException;

public class HeapWriteStmt implements IStmt{
    private String var_name;
    private Exp expression;

    public HeapWriteStmt(String var_name, Exp expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    public String getVar_name() {
        return var_name;
    }

    public void setVar_name(String var_name) {
        this.var_name = var_name;
    }

    public Exp getExpression() {
        return expression;
    }

    public void setExpression(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Value> heap = state.getHeapTable();

        if(symTbl.isDefined(var_name)) {
            Value val = symTbl.lookup(var_name);
            if (val instanceof RefValue)
            {
                RefValue refVal = (RefValue) val;
                if(heap.get(refVal.getAddress()) != null)
                {
                    Value expVal = expression.eval(symTbl, heap);
                    Type refType = refVal.getLocationType();;
                    if(expVal.getType().equals(refType))
                    {
                        heap.put(refVal.getAddress(), expVal);
                    }
                    else
                    {
                        throw new MyException("Type of the expression is not equal to the locationType of the var_name.");
                    }
                }
                else
                {
                    throw new MyException("The address from the RefValue associated in SymTable is not a key in the heap!");
                }
            }
            else
            {
                throw new MyException("Var_name " + var_name.toString() + " is not RefType!");
            }
        }
        else
        {
            throw new MyException("Var_name is not defined in Symbol Table!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(var_name);
        Type typeExp = expression.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("HeapWrite stmt: right hand side and left hand side have different types!");
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWriteStmt(var_name, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + var_name + "," + expression.toString() + ")";
    }
}
