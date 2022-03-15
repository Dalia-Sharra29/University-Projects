package View;

import Controller.Controller;
import Repository.MyException;

public class RunCommand extends Command{

    private Controller controller;

    public RunCommand(String key, String desc, Controller ctr){
        super(key, desc);
        this.controller = ctr;
    }

    @Override
    public void execute() {
        try{
            controller.typeCheck();
            controller.allStep();
        }
        catch (MyException | InterruptedException e) {
            System.out.println(e.getMessage());
        } //here you must treat the exceptions that can not be solved in the controller
    }
}
