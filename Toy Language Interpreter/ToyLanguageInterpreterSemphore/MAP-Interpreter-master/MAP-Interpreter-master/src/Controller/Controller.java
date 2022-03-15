package Controller;

import Model.ProgramState.MyDictionary;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIStack;
import Model.ProgramState.PrgState;
import Model.Statments.IStmt;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.MyException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repo, boolean flag) {
        this.repo = repo;
        this.displayFlag = flag;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public IRepository getRepo()
    {
        return this.repo;
    }

    public void addProgram(PrgState prg){
        repo.addProgram(prg);
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddress, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTableAddress.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressFromSymTable(Collection<Value> symTableValues, Collection<Value> heap){
        return Stream.concat(
                symTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();}),
                heap.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
        )
                .collect(Collectors.toList());
    }


    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                displayPrgState(prg);
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(prg -> {
            try {
                displayPrgState(prg);
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        repo.setPrgList(prgList);
    }

    public void allStep() throws MyException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        while (prgList.size() > 0) {
            callGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }

        executor.shutdownNow();

        repo.setPrgList(prgList);
    }

    public void callGarbageCollector(List<PrgState> prgStateList)
    {
        prgStateList.forEach(prgState -> prgState.getHeapTable().setContent(safeGarbageCollector(getAddressFromSymTable(
                prgState.getSymTable().getContent().values(), prgState.getHeapTable().getContent().values()),
                prgState.getHeapTable().getContent())));
    }

    public void typeCheck() throws MyException{
        MyIDictionary<String, Type> typeEnv = new MyDictionary<String, Type>();

        List<PrgState> prgStates = repo.getPrgList();
        PrgState prgState = prgStates.get(0);
        MyIStack<IStmt> exeStack = prgState.getExeStack();
        IStmt topStatement = exeStack.top();
        topStatement.typecheck(typeEnv);
    }

    public void runOneStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        repo.setPrgList(removeCompletedPrg(repo.getPrgList()));
        List<PrgState> programStates = repo.getPrgList();
        if(programStates.size() > 0)
        {
            try {
                oneStepForAllPrg(repo.getPrgList());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            repo.setPrgList(removeCompletedPrg(repo.getPrgList()));
            executor.shutdownNow();
            callGarbageCollector(programStates);
        }
    }

    public void displayPrgState(PrgState programState) {
        System.out.print("Thread number id: " + programState.getId() + "\n");
        System.out.print("------------------------------------------------------\n");

        System.out.print("***** Execution Stack *****\n");
        System.out.print(programState.getExeStack().toString() + "\n");

        System.out.print("***** Symbol Table *****\n");
        System.out.print(programState.getSymTable().toString() + "\n");

        System.out.print("***** Heap *****\n");
        System.out.print(programState.getHeapTable().toString() + "\n");

        System.out.print("***** Output List *****\n");
        System.out.print(programState.getOut().toString() + "\n");

        System.out.print("------------------------------------------------------\n");
    }

}
