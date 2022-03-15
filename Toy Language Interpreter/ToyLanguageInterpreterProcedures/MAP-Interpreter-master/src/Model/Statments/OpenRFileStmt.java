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
import java.io.File;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt{
    private Exp e;

    public OpenRFileStmt(Exp e) {
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
        if(val.getType().equals(new StringType()))
        {
            StringValue i = (StringValue) val;
            if(!fileTable.isDefined(i))
            {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File(i.toString())));
                    fileTable.update(i, br);
                }
                catch (Exception e)
                {
                    throw new MyException("Error on opening the file: " + e.toString());
                }
            }
            else{
                throw new MyException("File already defined in the FileTable!");
            }
        }
        else
        {
            throw new MyException("Expression is not a string!");
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
            throw new MyException("OpenRFile stmt: Expression is not a string!");
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(e.deepCopy());
    }

    @Override
    public String toString() {
        return "open(" + e.toString() + ")";
    }
}
