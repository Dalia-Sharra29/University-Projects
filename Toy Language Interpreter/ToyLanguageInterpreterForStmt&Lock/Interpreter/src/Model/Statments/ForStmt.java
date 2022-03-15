package Model.Statments;

import Model.Expressions.Exp;
import Model.Expressions.RelExp;
import Model.Expressions.VarExp;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;
import Repository.MyException;

public class ForStmt implements IStmt{

    private String id;
    private Exp exp1, exp2, exp3;
    private IStmt stmt;

    public ForStmt(String id, Exp exp1, Exp exp2, Exp exp3, IStmt stmt) {
        this.id = id;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Value> heapTable = state.getHeapTable();

        Value val1 = exp1.eval(symTable, heapTable);
        if(val1.getType().equals(new IntType()))
        {
            Value val2 = exp2.eval(symTable, heapTable);
            if(val2.getType().equals(new IntType()))
            {
                Value val3 = exp3.eval(symTable, heapTable);
                if(val3.getType().equals(new IntType()))
                {
                    IStmt forStmt = new CompStmt(new AssignStmt(id, exp1),
                            new WhileStmt(new RelExp(new VarExp(id), exp2, "<" ), new CompStmt(stmt, new AssignStmt(id, exp3))));
                    stack.push(forStmt);
                }
                else{
                    throw new MyException("Exp3 is not of type int!");
                }

            }
            else{
                throw new MyException("Exp2 is not of type int!");
            }
        }
        else{
            throw new MyException("Exp1 is not of type int!");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        if(type1 instanceof IntType && type2 instanceof IntType){
            return typeEnv;
        }else {
            throw new MyException("Mismatch between types!");
        }
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "for(" + id + "=" + exp1 + ";" + id + "<" + exp2 + ";" + id + "=" + exp3 + ") " + stmt;
    }
}
