package Model.Statments;

import Model.Expressions.Exp;
import Model.Expressions.ValueExp;
import Model.ProgramState.*;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.MyException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;

public class CallStmt implements IStmt{
    private String fname;
    private List<Exp> expressionList;

    public CallStmt(String fname, List<Exp> expressionList) {
        this.fname = fname;
        this.expressionList = expressionList;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl= state.getTopOfSymStack();
        MyIHeap<Value> heap = state.getHeapTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIProcTable<String, Pair<List<String>, IStmt>> procTable = state.getProcTable();

        Pair<List<String>, IStmt> entry = procTable.lookup(fname);
        List<String> listOfVariables = entry.getKey();
        IStmt bodyStmt = entry.getValue();
        List<Value> values = this.expressionList.stream()
                .map(exp -> {
                    try{
                        return exp.eval(symTbl, heap);
                    } catch (MyException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());

        state.getSymTable().push(state.getTopOfSymStack().clone());
        stk.push(new ReturnStmt());
        stk.push(bodyStmt);
        for(int i = 0; i < values.size(); i++){
            String value = listOfVariables.get(i);
            state.getExeStack().push(new AssignStmt(value, new ValueExp(values.get(i))));
            state.getExeStack().push(new VarDeclStmt(value, values.get(i).getType()));
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CallStmt(fname, expressionList);
    }

    @Override
    public String toString() {
        return "call " + fname + "(" + expressionList.toString() + ")";
    }
}
