package Model.Statments;

import Model.Expressions.Exp;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.MyException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{

    private Exp e;

    public CloseRFileStmt(Exp e) {
        this.e = e;
    }

    public Exp getE() {
        return e;
    }

    public void setE(Exp e) {
        this.e = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl= state.getTopOfSymStack();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value val = e.eval(symTbl, state.getHeapTable());
        if(val.getType().equals(new StringType())) {
            StringValue i = (StringValue) val;
            if (fileTable.isDefined(i)) {
                BufferedReader br = fileTable.lookup(i);
                try {
                    br.close();
                }catch (IOException e)
                {
                    throw new MyException("Can not close the file!");
                }
                fileTable.delete(i);
            }
            else{
                throw new MyException("There is no entry in the FileTable for this value!");
            }
        }
        else {
            throw new MyException("Expression is not a string value!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = e.typecheck(typeEnv);
        if(typeExp.equals(new StringType()))
        {
            return typeEnv;
        }
        else
            throw new MyException("CloseRFile stmt: Expression is not a string!");
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(e.deepCopy());
    }

    @Override
    public String toString() {
        return "close(" + e.toString() + ")";
    }
}
