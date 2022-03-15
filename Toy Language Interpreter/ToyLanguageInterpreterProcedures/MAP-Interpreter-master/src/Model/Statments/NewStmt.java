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

public class NewStmt implements IStmt{

    private String var_name;
    private Exp expression;

    public NewStmt(String var_name, Exp expression) {
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
        MyIDictionary<String, Value> symTbl= state.getTopOfSymStack();
        MyIHeap<Value> heap = state.getHeapTable();

        if(symTbl.isDefined(var_name))
        {
            Value val = symTbl.lookup(var_name);
            if(val instanceof RefValue)
            {
                Value expVal = expression.eval(symTbl, heap);
                RefValue refVal = (RefValue) val;
                Type refType = refVal.getLocationType();;
                if(expVal.getType().equals(refType))
                {
                    int address = heap.allocate(expVal);
                    symTbl.update(var_name, new RefValue(address, refType));
                }
                else
                {
                    throw new MyException("Type of the expression is not equal to the locationType from the value associated to var_name in SymTable.");
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
            throw new MyException("NEW stmt: right hand side and left hand side have different types!");
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(var_name, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + var_name + ", " + expression.toString() + ")";
    }
}
