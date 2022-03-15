package View;

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

public class Interpreter {

    public static void main(String[] args) {

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        PrgState prog1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphoreTable<>(), ex1);
        IRepository repo1 = new Repository("log1.txt");
        Controller ctr1 = new Controller(repo1, true);
        ctr1.addProgram(prog1);

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()), new CompStmt(new AssignStmt("a",
                        new ArithExp(new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)),
                                new ValueExp(new IntValue(5)),'*'),'+')), new CompStmt(new AssignStmt("b",
                        new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), '+')), new PrintStmt(new VarExp("b"))))));
        PrgState prog2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphoreTable<>(), ex2);
        IRepository repo2 = new Repository("log2.txt");
        Controller ctr2 = new Controller(repo2, true);
        ctr2.addProgram(prog2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        PrgState prog3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphoreTable<>(), ex3);
        IRepository repo3 = new Repository("log3.txt");
        Controller ctr3 = new Controller(repo3, true);
        ctr3.addProgram(prog3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new VarExp("varf"))))))))));
        PrgState prog4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphoreTable<>(), ex4);
        IRepository repo4 = new Repository("log4.txt");
        Controller ctr4 = new Controller(repo4, true);
        ctr4.addProgram(prog4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
                                new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')))), new PrintStmt(new VarExp("v")))));

        PrgState prog5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphoreTable<>(), ex5);
        IRepository repo5 = new Repository("log5.txt");
        Controller ctr5 = new Controller(repo5, true);
        ctr5.addProgram(prog5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewStmt("a", new VarExp("v")),
                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a")))))))));

        PrgState prog6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphoreTable<>(), ex6);
        IRepository repo6 = new Repository("log6.txt");
        Controller ctr6 = new Controller(repo6, true);
        ctr6.addProgram(prog6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                        new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(
                                new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a")))))))));

        PrgState prog7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphoreTable<>(), ex7);
        IRepository repo7 = new Repository("log7.txt");
        Controller ctr7 = new Controller(repo7, true);
        ctr7.addProgram(prog7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new BoolType()), new AssignStmt("v", new ValueExp(new IntValue(40))));

        PrgState prog8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphoreTable<>(), ex8);
        IRepository repo8 = new Repository("log8.txt");
        Controller ctr8 = new Controller(repo8, true);
        ctr8.addProgram(prog8);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1",ex1.toString(),ctr1));
        menu.addCommand(new RunCommand("2",ex2.toString(),ctr2));
        menu.addCommand(new RunCommand("3",ex3.toString(),ctr3));
        menu.addCommand(new RunCommand("4",ex4.toString(),ctr4));
        menu.addCommand(new RunCommand("5",ex5.toString(),ctr5));
        menu.addCommand(new RunCommand("6",ex6.toString(),ctr6));
        menu.addCommand(new RunCommand("7",ex7.toString(),ctr7));
        menu.addCommand(new RunCommand("8",ex8.toString(),ctr8));

        menu.show();
    }
}
