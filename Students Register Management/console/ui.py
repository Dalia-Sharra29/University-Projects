from repository.DisciplineTextRepository import DisciplineTextRepository, DisciplineBinaryFileRepository
from repository.GradeRepository import graderepo
from repository.GradeTextRepository import GradeTextRepository, GradeBinaryFileRepository
from repository.StudentTextRepository import StudentTextRepository, StudentBinaryFileRepository
from console.settings import configuration
from domain.Student import StudentException
from domain.Discipline import DisciplineException
from domain.Grade import gradeException
from service.DisciplineService import disciplineservice
from service.GradeService import gradeservice
from service.StudentService import studentservice
from repository.StudentRepository import studentrepo
from repository.DisciplineRepository import disciplinerepo
from service.UndoService import undoservice

class UI:
    def __init__(self, studentservice, disciplineservice, gradeservice, undoservice):
        self._studentservice = studentservice
        self._disciplineservice = disciplineservice
        self._gradeservice = gradeservice
        self._undoservice = undoservice

    def PrintMenu(self):
        print(' ')
        print(' < add student > for adding a new student')
        print(' < remove student > for deleting a student')
        print(' < update student > for updating the name of a student')
        print(' < list student > for displaying all the students')
        print(' < search student > for searching student')
        print(' < add discipline > for adding a new discipline')
        print(' < remove discipline > for deleting a discipline')
        print(' < update discipline > for updating the name of a discipline')
        print(' < list discipline > for displaying all the disciplines')
        print(' < search discipline > for searching discipline')
        print(' < add grade > for adding a new grade')
        print(' < list grade > for displaying all the grades')
        print(' < failing students > for displaying all students failing at one or more disciplines')
        print(' < best > for displaying students with the best school situation')
        print(' < sort discipline> for displaying all disciplines sorted in descending order of the average grade ')
        print(' < undo > for undoing the last operation')
        print(' < redo > for redoing the last undo')

    def add_student_ui(self):
        student_id = int(input('Give student id: '))
        student_name = input('Give student name: ')
        self._studentservice.add_student(student_id, student_name)

    def remove_student_ui(self):
        student_id = int(input('Give student id: '))
        self._studentservice.remove_student(student_id)

    def update_student_ui(self):
        student_id = int(input('Give the id of the student you want to update: '))
        student_name = input('Give the new student name: ')
        self._studentservice.update_student(student_id, student_name)

    def search_student_ui(self):
        search_str = input('Give a string to search by: ')
        list = self._studentservice.search_student(search_str)
        for i in list:
            print(i)

    def list_student_ui(self):
        for s in self._studentservice.student_list:
            print(str(s))

    def add_discipline_ui(self):
        discipline_id = int(input('Give discipline id: '))
        discipline_name = input('Give discipline name: ')
        self._disciplineservice.add_discipline(discipline_id, discipline_name)

    def remove_discipline_ui(self):
        discipline_id = int(input('Give discipline id: '))
        self._disciplineservice.remove_discipline(discipline_id)

    def update_discipline_ui(self):
        discipline_id = int(input('Give the id of the discipline you want to update: '))
        discipline_name = input('Give the new discipline name: ')
        self._disciplineservice.update_discipline(discipline_id, discipline_name)

    def list_discipline_ui(self):
        for d in self._disciplineservice.discipline_list:
            print(str(d))

    def search_discipline_ui(self):
        search_str = input('Give a string to search by: ')
        list = self._disciplineservice.search_discipline(search_str)
        for i in list:
            print(i)

    def add_grade_ui(self):
        discipline_id = int(input('Give discipline id: '))
        student_id = int(input('Give student id: '))
        value = int(input('Give a grade: '))
        self._gradeservice.add_grade(discipline_id, student_id, value)

    def failing_students(self):
        print('The students that failed at least at one discipline are:')
        list = self._gradeservice.failing_students()
        if len(list) == 0:
            print('No failing students!')
        else:
            for i in list:
                print(i)
    def best_school_situation(self):
        print('The students with best school situation are:')
        list = self._gradeservice.best_school_situation()
        if len(list) == 0:
            print('No grades attributed.')
        else:
            for i in list:
                print(i)

    def discipline_average(self):
        print('The disciplines sorted in descending order of the average grade received by all students enrolled at that discipline are: ')
        list = self._gradeservice.discipline_average()
        if len(list) == 0:
            print('No grades attributed.')
        else:
            for i in list:
                print('Discipline:', i[0], 'Average Grade:', i[1])

    def list_grade_ui(self):
        for g in self._gradeservice.grade_list:
            print(str(g))

    def undo_ui(self):
        if not self._undoservice.undo():
            print('No more undoes!')

    def redo_ui(self):
        if not self._undoservice.redo():
            print('No more redoes!')

    def start(self):

        self._commands = {'add student' : self.add_student_ui, 'remove student' : self.remove_student_ui, 'update student' : self.update_student_ui, 'list student' : self.list_student_ui, 'search student' : self.search_student_ui}
        self._commands2 = {'add discipline' : self.add_discipline_ui, 'remove discipline' : self.remove_discipline_ui, 'update discipline' : self.update_discipline_ui, 'list discipline' : self.list_discipline_ui, 'search discipline' : self.search_discipline_ui}
        self._commands3 = {'add grade' : self.add_grade_ui, 'list grade' : self.list_grade_ui, 'failing students' : self.failing_students, 'best' : self.best_school_situation, 'sort discipline' : self.discipline_average}
        self._commands4 = {'undo': self.undo_ui, 'redo' : self.redo_ui}
        done = False
        while not done:
            self.PrintMenu()
            command = input('Give command: ')
            print(' ')
            if command in self._commands:
                try:
                    self._commands[command]()
                except StudentException as se:
                    print(str(se))
            elif command in self._commands2:
                try:
                    self._commands2[command]()
                except DisciplineException as de:
                    print(str(de))
            elif command in self._commands3:
                try:
                    self._commands3[command]()
                except gradeException as ge:
                    print(str(ge))
            elif command in self._commands4:
                self._commands4[command]()
            elif command == 'exit':
                done = True
                print('Bye bye!')
            else:
                print('Bad command!')




config = configuration()
list = []
config.read_setting_file(list)
if list[0] == 'inmemory':
    studentrepo = studentrepo()
    disciplinerepo = disciplinerepo()
    graderepo = graderepo(studentrepo, disciplinerepo)
    studentrepo.test_init_students()
    disciplinerepo.test_init_discipline()
    graderepo.test_init_grade()

elif list[0] == 'textfiles':
    studentrepo = StudentTextRepository(list[1])
    disciplinerepo = DisciplineTextRepository(list[2])
    graderepo = GradeTextRepository(list[3], studentrepo, disciplinerepo)

elif list[0] == 'binaryfiles':
    studentrepo = StudentBinaryFileRepository(list[1])
    disciplinerepo = DisciplineBinaryFileRepository(list[2])
    graderepo = GradeBinaryFileRepository(list[3], studentrepo, disciplinerepo)

undoservice = undoservice()
gradeservice = gradeservice(undoservice, graderepo)
studentservice = studentservice(undoservice, studentrepo, graderepo)
disciplineservice = disciplineservice(undoservice, disciplinerepo, graderepo)

ui = UI(studentservice, disciplineservice, gradeservice, undoservice)
ui.start()
