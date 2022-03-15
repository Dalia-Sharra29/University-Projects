package Model.Statments;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.PrgState;
import Model.Types.Type;
import Repository.MyException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
    String toString();
    IStmt deepCopy();
}
