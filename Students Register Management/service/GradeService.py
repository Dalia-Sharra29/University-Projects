from custom.CombSort import comb_sort
from custom.Filter import custom_filter
from service.UndoService import FunctionCall, Operation


class gradeservice:
    def __init__(self, undoservice, graderepo):
        self._graderepo = graderepo
        self._undoservice = undoservice

    @property
    def grade_list(self):
        return self._graderepo.grade_list

    def add_grade(self, discipline_id, student_id, value):
        grade = self._graderepo.add_grade(discipline_id, student_id, value)
        undo = FunctionCall(self._graderepo.remove_grade, grade.discipline_id, grade.student_id)
        redo = FunctionCall(self._graderepo.add_grade, grade.discipline_id, grade.student_id, grade.grade)
        op = Operation(undo, redo)
        self._undoservice.record(op)

    def average(self):
        student_dict = {}
        for g in self.grade_list:
            if g.student_id not in student_dict:
                student_dict[g.student_id] = {}
            if g.discipline_id not in student_dict[g.student_id]:
                student_dict[g.student_id][g.discipline_id] = []
            student_dict[g.student_id][g.discipline_id].append(g.grade)

        average_list = []
        for student in student_dict:
            for discipline in student_dict[student]:
                sum = 0
                for grade in range(len(student_dict[student][discipline])):
                    sum = sum + int(student_dict[student][discipline][grade])
                average_list.append(StudentDisciplineAverage(student, discipline, sum/len(student_dict[student][discipline])))

        return average_list

    def failing_students(self):
        average_list = self.average()
        custom_filter(average_list, lambda i: i._average < 5)
        return average_list

    def best_school_situation(self):
        average_list = self.average()
        total_average_dict = {}
        result = []
        for i in average_list:
            if i.student not in total_average_dict:
                total_average_dict[i.student] = []
            total_average_dict[i.student].append(i.average)

        for stud in total_average_dict:
            avg = sum(total_average_dict[stud])/len(total_average_dict[stud])
            if avg > 7:
                result.append(StudentDisciplineAverage(stud, 'all', avg))

        comb_sort(result, lambda x, y: x.average < y.average)
        # result.sort(key=lambda x: x.average, reverse=True)
        return result

    def discipline_average(self):
        discipline_dict = {}
        result = []
        for g in self.grade_list:
            if g.discipline_id not in discipline_dict:
                discipline_dict[g.discipline_id] = []
            discipline_dict[g.discipline_id].append(g.grade)
        for discipline in discipline_dict:
            avg = sum(discipline_dict[discipline])/len(discipline_dict[discipline])
            result.append([discipline, avg])

        comb_sort(result, lambda x, y: x[1] < y[1])
        # result.sort(key=lambda x:x[1], reverse=True)
        return result


class StudentDisciplineAverage:
    def __init__(self, student_id, discipline_id, average):
        self._student_id = student_id
        self._discipline_id = discipline_id
        self._average = average

    @property
    def student(self):
        return self._student_id

    @property
    def average(self):
        return self._average

    @property
    def discipline(self):
        return self._discipline_id

    def __str__(self):
        return 'Student: ' + str(self.student) + ' Discipline: ' + str(self.discipline) + ' Average: ' +str(self.average)


