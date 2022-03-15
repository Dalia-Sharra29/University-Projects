class undoservice:
    def __init__(self):
        self._history = []
        self._index = -1

    def __len__(self):
        return len(self._history)

    def record(self, operation):
        self._history = self._history[:self._index + 1]
        self._history.append(operation)
        self._index += 1

    def undo(self):
        if self._index == -1:
            return False

        self._history[self._index].undo()
        self._index -= 1
        return True

    def redo(self):
        if self._index == len(self._history) - 1:
            return False

        self._index += 1
        self._history[self._index].redo()
        return True


class CascadedOperation:
    def __init__(self, *operations):
        self._operations = operations

    def undo(self):
        for oper in self._operations:
            oper.undo()

    def redo(self):
        for oper in self._operations:
            oper.redo()


class Operation:
    def __init__(self, call_undo, call_redo):
        self._call_undo = call_undo
        self._call_redo = call_redo

    def undo(self):
        self._call_undo()

    def redo(self):
        self._call_redo()


class FunctionCall:
    def __init__(self, function, *fun_params):
        self._function = function
        self._fun_params = fun_params

    def call(self):
        return self._function(*self._fun_params)

    def __call__(self):
        return self.call()
