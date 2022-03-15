package Model.Statments;

import Model.Expressions.Exp;
import Model.Expressions.RelExp;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.Value;
import Repository.MyException;

public class SwitchStmt implements IStmt{
    private Exp exp, exp1, exp2;
    private IStmt stmt1, stmt2, stmt3;

    public SwitchStmt(Exp exp, Exp exp1, Exp exp2, IStmt stmt1, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();

        IStmt newStmt = new IfStmt(new RelExp(exp, exp2, "=="), stmt1, new IfStmt(new RelExp(exp, exp2, "=="), stmt2, stmt3));
        stack.push(newStmt);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typecheck(typeEnv);
        Type typeExp1 = exp1.typecheck(typeEnv);
        Type typeExp2 = exp2.typecheck(typeEnv);
        if(typeExp.equals(typeExp1) && typeExp.equals(typeExp2))
        {
            stmt1.typecheck(typeEnv.clone());
            stmt2.typecheck(typeEnv.clone());
            stmt3.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("SwitchStmt: expressions do not have the same types!");
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), exp2.deepCopy(), stmt1.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public String toString() {
        return "switch(" + exp + "(case" + exp1 + ":" + stmt1 + ") (case " + exp2 + ":" + stmt2 + ") default: " + stmt3;
    }
}
