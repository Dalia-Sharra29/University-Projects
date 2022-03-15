package Model.Statments;

import Model.Expressions.Exp;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;
import Repository.MyException;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt statement;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.statement = stmt;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public IStmt getStatement() {
        return statement;
    }

    public void setStatement(IStmt stmt) {
        this.statement = stmt;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heapTable = state.getHeapTable();

        Value cond = exp.eval(symTable, heapTable);
        if(cond.getType().equals(new BoolType()))
        {
            BoolValue val = (BoolValue) cond;
            if(val.getVal())
            {
                stack.push(new WhileStmt(exp,statement));
                stack.push(statement);
            }
        }
        else{
            throw new MyException("Condition exp is not boolean!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        if(typeExp.equals(new BoolType()))
        {
            statement.typecheck(typeEnv.clone());
            return typeEnv;
        } else
            throw new MyException("The condition of WHILE does not have the type bool!");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "(while(" + exp.toString() + ")" + statement.toString() + ")";
    }
}
