from service.UndoService import FunctionCall, CascadedOperation, Operation


class disciplineservice:
    def __init__(self, undoservice, disciplinerepo, graderepo):
        self._disciplinerepo = disciplinerepo
        self._graderepo = graderepo
        self._undoservice = undoservice
    @property
    def discipline_list(self):
        return self._disciplinerepo.discipline_list

    def add_discipline(self, discipline_id, discipline_name):
        dis = self._disciplinerepo.add_discipline(discipline_id, discipline_name)
        undo = FunctionCall(self._disciplinerepo.remove_discipline, dis.id)
        redo = FunctionCall(self._disciplinerepo.add_discipline, dis.id, dis.name)
        op = Operation(undo, redo)
        self._undoservice.record(op)

    def remove_discipline(self, discipline_id):
        found, obj = self._disciplinerepo.remove_discipline(discipline_id)
        undo = FunctionCall(self._disciplinerepo.add_discipline, obj.id, obj.name)
        redo = FunctionCall(self._disciplinerepo.remove_discipline, obj.id)
        op = Operation(undo, redo)
        cascade_list = [op]

        if found is True:
            list = self._graderepo.grade_list
            i = 0
            while i < len(list):
                if int(list[i].discipline_id) == int(discipline_id):
                    g = list[i]
                    undo = FunctionCall(self._graderepo.add_grade, g.discipline_id, g.student_id, g.grade)
                    redo = FunctionCall(self._graderepo.remove_grade, g.discipline_id, g.student_id)
                    cascade_list.append(Operation(undo, redo))
                    self._graderepo.remove_grade(g.discipline_id, g.student_id)
                else:
                    i += 1

        cop = CascadedOperation(*cascade_list)
        self._undoservice.record(cop)

    def update_discipline(self, discipline_id, discipline_name):
        discipline, name = self._disciplinerepo.update_discipline(discipline_id, discipline_name)
        undo = FunctionCall(self.update_discipline, discipline.id, name)
        redo = FunctionCall(self.update_discipline, discipline_id, name)
        op = Operation(undo, redo)
        self._undoservice.record(op)

    def search_discipline(self, search_str):
        return self._disciplinerepo.search_discipline(search_str)
