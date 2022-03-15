
from repository.StudentRepository import studentrepo
from service.UndoService import Operation, CascadedOperation, FunctionCall


class studentservice:
    def __init__(self, undoservice, studentrepo, graderepo):
        self._studentrepo = studentrepo
        self._graderepo = graderepo
        self._undoservice = undoservice

    def add_student(self, student_id, name):
        stud = self._studentrepo.add_student(student_id, name)
        undo = FunctionCall(self._studentrepo.remove_student, stud.id)
        redo = FunctionCall(self._studentrepo.add_student, stud.id, stud.name)
        op = Operation(undo, redo)
        self._undoservice.record(op)

    def remove_student(self, student_id):
        found, obj = self._studentrepo.remove_student(student_id)
        undo = FunctionCall(self._studentrepo.add_student, obj.id, obj.name)
        redo = FunctionCall(self._studentrepo.remove_student, obj.id)
        op = Operation(undo, redo)
        cascade_list = [op]

        if found is True:
            list = self._graderepo.grade_list
            i = 0

            while i < len(list):
                if int(list[i].student_id) == int(student_id):
                    g = list[i]
                    undo = FunctionCall(self._graderepo.add_grade, g.discipline_id, g.student_id, g.grade)
                    redo = FunctionCall(self._graderepo.remove_grade, g.discipline_id, g.student_id)
                    cascade_list.append(Operation(undo, redo))
                    self._graderepo.remove_grade(g.discipline_id, g.student_id)
                else:
                    i += 1

        cop = CascadedOperation(*cascade_list)
        self._undoservice.record(cop)

    def update_student(self, student_id, student_name):
        student, name = self._studentrepo.update_student(student_id, student_name)
        undo = FunctionCall(self.update_student, student.id, name)
        redo = FunctionCall(self.update_student, student_id, name)
        op = Operation(undo, redo)
        self._undoservice.record(op)

    def search_student(self, search_str):
        return self._studentrepo.search_student(search_str)

    @property
    def student_list(self):
        return self._studentrepo.student_list
