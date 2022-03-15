package Model.Statments;

import Model.Expressions.Exp;
import Model.Expressions.LogicExp;
import Model.Expressions.NotExp;
import Model.ProgramState.*;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;
import Repository.MyException;

public class RepeatStmt implements IStmt{
    private IStmt stmt;
    private Exp exp;

    public RepeatStmt(IStmt stmt, Exp exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeapTable();

        Value val = exp.eval(symTable,heap);
        if(val.getType().equals(new BoolType())) {
            BoolValue value = (BoolValue) val;
            IStmt newStmt = new CompStmt(stmt, new WhileStmt(new NotExp(exp), stmt));
            stk.push(newStmt);
        }
        else
            throw new MyException("Repeat: exp is not of type bool!");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            stmt.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("repeat stmt: condition must be boolean");
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatStmt(stmt.deepCopy(), exp.deepCopy());
    }

    @Override
    public String toString() {
        return "(repeat(" + stmt.toString() + ")" + exp.toString() + ")";
    }
}
