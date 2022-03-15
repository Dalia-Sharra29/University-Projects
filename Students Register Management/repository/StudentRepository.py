
from domain.Student import StudentException, student, StudentValidator
from custom.iterable import Iterable
from service.UndoService import FunctionCall, Operation


class studentrepo:
    """
    Class for the student repository
    """
    def __init__(self):
        self._student_list = Iterable()

    def __len__(self):
        return len(self._student_list)

    # getter
    @property
    def student_list(self):
        return self._student_list

    def add_student(self, student_id, name):
        """
        Adds a new student to the list
        :param student_id: an integer
        :param name: the student name, a string
        :return: -
        """
        studval = StudentValidator()
        s = student(student_id,name)
        studval.validate(s)
        for stud in self.student_list:
            if int(stud.id) == int(student_id):
                raise StudentException('Student with given id already exist!')
        self.student_list.append(s)
        return s

    def remove_student(self, student_id):
        '''
        Removes a student given by student id from the list
        :param student_id: an integer
        :return: -
        '''
        found = False
        obj = 0
        for s in self.student_list:
            if int(s.id) == int(student_id):
                found = True
                obj = s
                self.student_list.remove(s)
        if not found:
            raise StudentException('Student with given id not found!')
        return found, obj

    def update_student(self, student_id, student_name):
        '''
        Update a student name given by id
        :param student_id: an integer
        :param student_name: the new student name
        :return:-
        '''
        for s in self.student_list:
            if int(s.id) == int(student_id):
                obj = s
                name = s.name
                s.set_name(student_name)
                return obj, name
        raise StudentException('Student with given id not found!')

    def search_student(self, search_str):
        found_list = []
        if search_str.isdigit():
            for s in self.student_list:
                if str(s.id).find(search_str) != -1:
                    found_list.append(str(s))
        elif search_str.isalpha():
            for s in self.student_list:
                if s.name.lower().find(search_str.lower()) != -1:
                    found_list.append(str(s))
        else:
            raise StudentException('Bad input!')
        if found_list == []:
            raise StudentException('No such student was found!')
        return found_list

    def test_init_students(self):
        s = student(6138,'Pop Ion')
        self.student_list.append(s)
        s = student(2389, 'Dan Amalia')
        self.student_list.append(s)
        s = student(4371, 'Muresan Ana')
        self.student_list.append(s)
        s = student(2327, 'Adam Anca')
        self.student_list.append(s)
        s = student(6287, 'Haidu Crina')
        self.student_list.append(s)
        s = student(7625, 'Jucan Vlad')
        self.student_list.append(s)
        s = student(2736, 'Horvath Andrei')
        self.student_list.append(s)
        s = student(7621, 'Misan Rares')
        self.student_list.append(s)
        s = student(2382, 'Candrea Cosmin')
        self.student_list.append(s)
        s = student(8723, 'Marina Dan')
        self.student_list.append(s)

    @student_list.setter
    def student_list(self, value):
        self._student_list = value
