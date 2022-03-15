from random import sample

from domain.Grade import GradeValidator, grade, gradeException
from custom.iterable import Iterable


class graderepo:
    def __init__(self, studentrepo, disciplinerepo):
        self._grade_list = Iterable()
        self._studentrepo = studentrepo
        self._disiciplinerepo = disciplinerepo

    def __len__(self):
        return len(self._grade_list)

    @property
    def grade_list(self):
        return self._grade_list

    @grade_list.setter
    def grade_list(self, new_list):
        self._grade_list = new_list

    def add_grade(self, discipline_id, student_id, value):
        '''
        Adds a grade to a student given by id at a given discipline
        :param discipline_id: an integer
        :param student_id: an integer
        :return: -
        '''
        gradeval = GradeValidator()
        g = grade(discipline_id, student_id, value)
        gradeval.validate(g)
        found1 = False
        found2 = False
        for s in self._studentrepo.student_list:
            if int(s.id) == int(student_id):
                found1 = True
        for d in self._disiciplinerepo.discipline_list:
            if int(d.id) == int(discipline_id):
                found2 = True
        if not found1:
            raise gradeException('The student with this id does not exit!')
        if not found2:
            raise gradeException('The discipline with this id does not exit!')
        self.grade_list.append(g)
        return g

    def remove_grade(self, discipline_id, student_id):
        for g in self.grade_list:
            if int(g.discipline_id) == int(discipline_id) and int(student_id) == int(g.student_id):
                self.grade_list.remove(g)
                return
        raise gradeException('Such grade does not exist!')

    def test_init_grade(self):
        g = grade(2, 6138, 8)
        self.grade_list.append(g)
        g = grade(10, 4371, 6)
        self.grade_list.append(g)
        g = grade(3, 2327, 7)
        self.grade_list.append(g)
        g = grade(4, 6287, 6)
        self.grade_list.append(g)
        g = grade(1, 7625, 8)
        self.grade_list.append(g)
        g = grade(3, 2736, 10)
        self.grade_list.append(g)
        g = grade(10, 7621, 9)
        self.grade_list.append(g)
        g = grade(5, 2382, 10)
        self.grade_list.append(g)
        g = grade(7, 8723, 5)
        self.grade_list.append(g)
        g = grade(7, 8723, 3)
        self.grade_list.append(g)
        g = grade(5, 7621, 9)
        self.grade_list.append(g)
        g = grade(6, 4371, 5)
        self.grade_list.append(g)
        g = grade(7, 2389, 4)
        self.grade_list.append(g)
        g = grade(9, 2389, 10)
        self.grade_list.append(g)
        g = grade(7, 2389, 3)
        self.grade_list.append(g)





