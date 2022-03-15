package Repository;

import Model.ProgramState.PrgState;

import java.util.List;

public interface IRepository {
    PrgState getCrtPrg();
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> states);
    void addProgram(PrgState prg);
    void logPrgStateExec(PrgState prg) throws MyException;
}
