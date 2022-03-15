# Toy Language Interpreter
 * Implemented an interpreter for a toy language, using the model-view-controller architectural pattern and the object-oriented 
concepts.
 * The interpreter handles expressions and statements.
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
    - etc.
 * Our mini interpreter uses three main structures to denote the program state (`PrgState`):
    – **Execution Stack** (`ExeStack`): a stack of statements to execute the currrent program
    – **Table of Symbols** (`SymTable`): a table which keeps the variables values
    – **Output** (`Out`): that keeps all the mesages printed by the toy program
 * Our interpreter can execute multiple programs but for each of them use a different PrgState structures (that means different ExeStack, SymTable and Out structures).
 * At the beginning, ExeStack contains the original program, and SymTable and Out are empty. After the evaluation has started, ExeStack contains the remaining part of the program that must be evaluated, SymTable contains the variables (from the variable declarations statements evaluated so far) with their assigned values, and Out contains the values printed so far.
 * At the end of a program evaluation, ExeStack is empty, SymTable contains all the program variables, and Out contains all the program print outputs.


 
