# Toy Language Interpreter
 * Implemented an interpreter for a toy language, using the model-view-controller architectural pattern and the object-oriented 
concepts.
 * The interpreter handles *expressions* and *statements* and can have *variables* of different *types*.
 * A program (`Prg`) in this language consists of a statement (`Stmt`).
 * A statement can be:
    - a compound statement (`CompStmt`)
    - an assignment statement (`AssignStmt`)
    - a print statement (`PrintStmt`)
    - a conditional statement (`IfStmt`)
    - a no opertaion statement (`NopStmt`) 
    - a variable declaration statement (`VarDeclStmt`)
    - a while statement (`WhileStmt`)
    - a for statement (`ForStmt`)
    - a new statement (`NewStmt`)
    - a open file for read statement (`OpenRFileStmt`)
    - a close read file statement(`CloseRFileStmt`)
    - a read file statement (`ReadFileStmt`)
    - a heap write statement (`HeapWriteStmt`)
    - a fork statement (`ForkStmt`)
 * An expression can be:
    - an arithmetic expression (`ArithExp`)
    - a relational expression (`RelExp`)
    - a logic expression (`LogicExp`)
    - a value expression (`ValueExp`)
    - a variable expression (`VarExp`)
    - a heap read expression (`HeapReadExp`)
 * A type can be:
    - boolean (`BoolType`)
    - integer (`IntType`)
    - reference (`RefType`)
    - string (`StringType`)
 * A value can be:
    - boolean (`BoolValue`)
    - integer (`IntValue`)
    - reference (`RefValue`)
    - string (`StringValue`)
 * Our mini interpreter uses five main structures to denote the program state (`PrgState`):
    - **Execution Stack** (`ExeStack`): a stack of statements to execute the currrent program
    - **Table of Symbols** (`SymTable`): a table which keeps the variables and their values
    - **Output** (`Out`): keeps all the messages printed by the toy program
    - **File Table** (`FileTable`): manages the files opened in our Toy Language
    - **Heap Table** (`HeapTable`): manages the heap memory.
 * Our interpreter can execute multiple programs but for each of them use a different PrgState structures (that means different ExeStack, SymTable and Out structures).
 * At the beginning, ExeStack contains the original program, and SymTable and Out are empty. After the evaluation has started, ExeStack contains the remaining part of the program that must be evaluated, SymTable contains the variables (from the variable declarations statements evaluated so far) with their assigned values, and Out contains the values printed so far. At the end of a program evaluation, ExeStack is empty, SymTable contains all the program variables, and Out contains all the program print outputs.
 * The interpreter supports **concurrancy** and has implemented a **Type Checker**.
 * The interpreter provides **synchronization mechanisms** such as:
   - Lock
   - Semaphore
   - Count Down Latch
 * The interpreter has a GUI implemented in JavaFX that shows execution of a program step by step.

![SelectWindow](https://user-images.githubusercontent.com/72136776/158454135-ad71e5fb-cd22-4b21-a91e-a7e89f5b1eef.png)

 ![MainWindow](https://user-images.githubusercontent.com/72136776/158454161-9f599f12-9888-4805-a852-66f7e33ca60c.png)

