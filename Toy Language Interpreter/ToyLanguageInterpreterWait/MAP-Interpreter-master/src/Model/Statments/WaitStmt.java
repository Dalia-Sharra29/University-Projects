package Model.Statments;

import Model.Expressions.ArithExp;
import Model.Expressions.ValueExp;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;

public class WaitStmt implements IStmt{
    private Integer number;

    public WaitStmt(Integer number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl= state.getSymTable();

        if(number != 0)
        {
            IStmt newStmt = new CompStmt(new PrintStmt(new ValueExp(new IntValue(number))), new WaitStmt(number - 1 ));
            stk.push(newStmt);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "wait(" + number.toString() + ")";
    }
}
