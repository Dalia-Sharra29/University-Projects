package GUI;

import Controller.Controller;
import Model.ProgramState.MyIList;
import Model.ProgramState.MyIStack;
import Model.ProgramState.MyList;
import Model.ProgramState.PrgState;
import Model.Statments.IStmt;
import Model.Values.Value;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    @FXML
    private ListView<String> execStackView;

    @FXML
    private ListView<String> fileTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapTableAddress;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> heapTableValue;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    private TextField noProgStates;

    @FXML
    private ListView<String> outputView;

    @FXML
    private ListView<Integer> progIdView;

    @FXML
    private Button runButton;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> symTableName;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> symTableValue;

    @FXML
    private TableView<Map.Entry<String, Integer>> symbolTableView;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> lockTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> locationLock;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> valueLock;

    private Controller controller;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        initNoProgStates();
        initProgIdView();
        runButton.setDisable(false);
    }

    public void initNoProgStates()
    {
        noProgStates.setText("The number of Program States is: " + controller.getRepo().getPrgList().size());
    }

    public void initLockTable(PrgState prgState)
    {
        lockTableView.setItems(FXCollections.observableList(new ArrayList<>(prgState.getLockTable().getContent().entrySet())));
        lockTableView.refresh();
    }

    public void initHeapTableView(PrgState prgState)
    {
        heapTableView.setItems(FXCollections.observableList(new ArrayList<>(prgState.getHeapTable().getContent().entrySet())));
        heapTableView.refresh();
    }

    public void initOutputView(PrgState prgState)
    {
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        for(Value v : prgState.getOut().getContent()) {
            outObservableList.add(v.toString());
        }
        outputView.setItems(outObservableList);
    }

    public void initFileTableView(PrgState prgState)
    {
        fileTableView.setItems(FXCollections.observableArrayList(prgState.getFileTable().getContent().keySet()));
    }

    public void initProgIdView()
    {
        progIdView.setItems(FXCollections.observableArrayList(controller.getRepo().getPrgList().stream().map(PrgState::getId).collect(Collectors.toList())));
        progIdView.refresh();
    }

    public void initExeStackView(PrgState prgState)
    {
        MyIStack<IStmt> stack = prgState.getExeStack();
        List<String> stackOutput = new ArrayList<>();
        for (IStmt statement : stack.getValues()){
            stackOutput.add(statement.toString());
        }
        Collections.reverse(stackOutput);
        execStackView.setItems(FXCollections.observableArrayList(stackOutput));
    }

    public void initSymTableView(PrgState prgState)
    {
        symbolTableView.setItems(FXCollections.observableList(new ArrayList<>(prgState.getSymTable().getContent().entrySet())));
        symbolTableView.refresh();
    }

    public void update(PrgState prgState)
    {
        if(prgState == null)
            return;
        initNoProgStates();
        initHeapTableView(prgState);
        initOutputView(prgState);
        initFileTableView(prgState);
        initProgIdView();
        if(progIdView.getSelectionModel().getSelectedItem() == null) {
            progIdView.getSelectionModel().selectFirst();
        }
        initExeStackView(prgState);
        initSymTableView(prgState);
        initLockTable(prgState);
    }

    public void oneStep()
    {
        PrgState programState = getSelectedProgramState();
        if(programState != null && !programState.isNotCompleted()){
            Alert error = new Alert(Alert.AlertType.ERROR,"Nothing left to execute!");
            error.show();
            return;
        }

        try {
            controller.runOneStep();
            update(programState);
            if (controller.getRepo().getPrgList().size() == 0)
                runButton.setDisable(true);
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.show();
            runButton.setDisable(true);
        }
    }

    private PrgState getSelectedProgramState(){
        if(progIdView.getSelectionModel().getSelectedIndex() == -1)
            return null;
        int id = progIdView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getPrgList().get(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller = null;

        heapTableAddress.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapTableValue.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        symTableName.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getKey() + " "));
        symTableValue.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        locationLock.setCellValueFactory(p-> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueLock.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue() + " "));

        progIdView.setOnMouseClicked(e ->update(getSelectedProgramState()));
        progIdView.getSelectionModel().selectFirst();
        runButton.setDisable(true);
    }
}
