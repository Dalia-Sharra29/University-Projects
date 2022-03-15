package GUI;

import Controller.Controller;
import Model.Expressions.*;
import Model.ProgramState.*;
import Model.Statments.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.IRepository;
import Repository.Repository;
import Repository.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectWindowController implements Initializable {

    @FXML
    private Button selectButton;

    @FXML
    private ListView<IStmt> programListView;

    private MainWindowController mainWindowController;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programListView.setItems(initList());

        programListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        programListView.getSelectionModel().selectIndices(0);

        selectExample();
    }

    private ObservableList<IStmt> initList()
    {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()), new CompStmt(new AssignStmt("a",
                        new ArithExp(new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)),
                                new ValueExp(new IntValue(5)),'*'),'+')), new CompStmt(new AssignStmt("b",
                        new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), '+')), new PrintStmt(new VarExp("b"))))));

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new VarExp("varf"))))))))));

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
                                new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')))), new PrintStmt(new VarExp("v")))));

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewStmt("a", new VarExp("v")),
                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a")))))))));

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                        new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(
                                new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a")))))))));

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new BoolType()), new AssignStmt("v", new ValueExp(new IntValue(40))));

        IStmt ex9 = new CompStmt(new VarDeclStmt("v1", new IntType()), new CompStmt(new VarDeclStmt("v2", new IntType()),
                new CompStmt(new AssignStmt("v1", new ValueExp(new IntValue(2))), new CompStmt(new AssignStmt("v2", new ValueExp(new IntValue(3))),
                        new IfStmt(new RelExp(new VarExp("v1"), new ValueExp(new IntValue(0)), "!="), new PrintStmt(new MulExp(new VarExp("v1"), new VarExp("v2"))),
                                new PrintStmt(new VarExp("v1")))))));

        ObservableList<IStmt> examples = FXCollections.observableArrayList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9);

        return examples;
    }

    private void selectExample()
    {
        selectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int index = programListView.getSelectionModel().getSelectedIndex();
                IStmt selectedProgram = programListView.getSelectionModel().getSelectedItem();
                index++;
                PrgState prgState = new PrgState(selectedProgram);
                IRepository repository = new Repository("log" + index + ".txt");
                Controller controller = new Controller(repository, true);
                controller.addProgram(prgState);
                try{
                    selectedProgram.typecheck(new MyDictionary<String, Type>());
                    mainWindowController.setController(controller);

                }catch(MyException ex)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,ex.getMessage());
                    alert.show();
                }

            }
        });
    }
}
