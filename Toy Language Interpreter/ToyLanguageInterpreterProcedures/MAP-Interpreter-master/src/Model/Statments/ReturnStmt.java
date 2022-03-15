package Model.Statments;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.PrgState;
import Model.Types.Type;
import Repository.MyException;

public class ReturnStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getSymTable().pop();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReturnStmt();
    }

    @Override
    public String toString() {
        return "return";
    }
}
