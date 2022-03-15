package Model.Statments;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Types.Type;
import Repository.MyException;

public class SleepStmt implements IStmt{
    private Integer number;

    public SleepStmt(Integer number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        if(number != 0)
            stk.push(new SleepStmt(number -1));
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
        return "sleep(" + number.toString() + ")";
    }
}
