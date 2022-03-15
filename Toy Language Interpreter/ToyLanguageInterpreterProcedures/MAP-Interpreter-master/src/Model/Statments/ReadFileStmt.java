package Model.Statments;

import Model.Expressions.Exp;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.MyException;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{

    private Exp e;
    private String varName;

    public ReadFileStmt(Exp e, String varName) {
        this.e = e;
        this.varName = varName;
    }

    public Exp getE() {
        return e;
    }

    public void setE(Exp e) {
        this.e = e;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl= state.getTopOfSymStack();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if(symTbl.isDefined(varName))
        {
            if(symTbl.lookup(varName).getType().equals(new IntType()))
            {
                Value val = e.eval(symTbl, state.getHeapTable());
                if(val.getType().equals(new StringType())) {
                    StringValue i = (StringValue) val;
                    BufferedReader br;
                    String line;
                    try {
                        br = fileTable.lookup(i);
                    }
                    catch (MyException e)
                    {
                        throw new MyException("There is no entry for " + i.toString() + "in the fileTable!");
                    }

                    try {
                        line = br.readLine();
                    }catch(IOException e)
                    {
                        throw new MyException("Can not read from this file!");
                    }
                    IntValue compValue;
                    if(line == null)
                    {
                        compValue = new IntValue(0);
                    }
                    else
                    {
                        compValue = new IntValue(Integer.parseInt(line));
                    }
                    symTbl.update(varName, compValue);
                }
                else{
                    throw new MyException("Expression is not a string value!");
                }
            }
            else {
                throw new MyException("Type of varName is not int!");
            }
        }
        else{
            throw new MyException("VarName is not defined in the SymTable!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = e.typecheck(typeEnv);
        Type typeVar = typeEnv.lookup(varName);
        if(typeExp.equals(new StringType()))
        {
            if(typeVar.equals(new IntType()))
            {
                return typeEnv;
            }
            else
                throw new MyException("ReadFile: Type of varName is not int!");
        }
        else
            throw new MyException("ReadFile: Expression is not of type string!");
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(e.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return "read(" + e.toString() + "," + varName + ")";
    }
}
