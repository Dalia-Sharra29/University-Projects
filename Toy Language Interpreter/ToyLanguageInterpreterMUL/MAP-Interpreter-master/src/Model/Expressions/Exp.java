package Model.Expressions;

import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;
import Repository.MyException;

public interface Exp {
    Value eval(MyIDictionary<String,Value> symbolTable, MyIHeap<Value> heapTable) throws MyException;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
    String toString();
    Exp deepCopy();
}
