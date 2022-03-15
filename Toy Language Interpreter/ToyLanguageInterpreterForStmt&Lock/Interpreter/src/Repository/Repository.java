package Repository;

import Model.ProgramState.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> states;
    private String logFilePath;

    public Repository(String filePath) {
        this.states = new ArrayList<>();
        this.logFilePath = filePath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return this.states.get(states.size()-1);
    }

    @Override
    public List<PrgState> getPrgList() {
        return states;
    }

    @Override
    public void setPrgList(List<PrgState> states) {
        this.states = states;
    }

    @Override
    public void addProgram(PrgState prg){
        states.add(prg);
    }

    @Override
    public void logPrgStateExec(PrgState program) throws MyException {
        PrintWriter logFile;
        try
        {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        }catch (IOException e)
        {
            throw new MyException("Can not write in the logFile!");
        }
        logFile.write(program.toString());
        logFile.close();
    }
}
