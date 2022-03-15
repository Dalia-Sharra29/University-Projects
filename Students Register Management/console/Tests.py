import unittest

from custom.CombSort import comb_sort
from custom.Filter import custom_filter
from repository.DisciplineRepository import disciplinerepo
from repository.GradeRepository import graderepo
from repository.StudentRepository import studentrepo
from domain.Student import student, StudentException, StudentValidator
from domain.Discipline import discipline, DisciplineException, DisciplineValidator
from domain.Grade import grade, GradeValidator, gradeException


class TestStudent(unittest.TestCase):
    def test_student(self):
        s1 = student(1, 'Pop Ana')
        assert s1.id == 1
        assert s1.name == 'Pop Ana'

        s1.id = 2
        assert s1.id == 2

        s1.set_name('Muresan Ana')
        assert s1.name == 'Muresan Ana'

    def test_student_validator(self):
        try:
            s = student('', 'Pop Ana')
            sv = StudentValidator()
            sv.validate(s)
            assert False
        except StudentException:
            assert True

        try:
            s = student('gj7b', 'Pop Ana')
            sv = StudentValidator()
            sv.validate(s)
            assert False
        except StudentException:
            assert True

        try:
            s = student('5', '')
            sv = StudentValidator()
            sv.validate(s)
            assert False
        except StudentException:
            assert True

    def test_add(self):
        student_list = studentrepo()
        assert len(student_list) == 0

        student_list.add_student(2761, 'Pop David')
        assert len(student_list) == 1

        student_list.add_student(823, 'Muresan Ana')
        assert len(student_list) == 2

    def test_update(self):
        sr = studentrepo()
        sr.test_init_students()
        assert len(sr.student_list) == 10
        sr.add_student(28197, 'Misan Ion')

        assert len(sr.student_list) == 11
        sr.update_student(28197, 'Misan Ion')
        for s in sr.student_list:
            if s.id == 28197:
                assert s.name == 'Misan Ion'

    def test_remove(self):
        sr = studentrepo()
        sr.add_student(2761, 'ahxbak')

        try:
            sr.remove_student(823)
            assert False
        except StudentException:
            pass

        sr.remove_student(2761)
        assert len(sr) == 0


class TestDiscipline(unittest.TestCase):
    def test_discipline(self):
        d = discipline(1, 'Algebra')
        assert d.id == 1
        assert d.name == 'Algebra'

        d.id = 2
        assert d.id == 2

        d.name = 'Linear Algebra'
        assert d.name == 'Linear Algebra'

    def test_discipline_validator(self):
        try:
            d = discipline('', 'Algebra')
            dv = DisciplineValidator()
            dv.validate(d)
            assert False
        except DisciplineException:
            assert True

        try:
            d = discipline('sxnjak', 'Algebra')
            dv = DisciplineValidator()
            dv.validate(d)
            assert False
        except DisciplineException:
            assert True

        try:
            d = discipline('10', '')
            dv = DisciplineValidator()
            dv.validate(d)
            assert False
        except DisciplineException:
            assert True

    def test_add(self):
        discipline_list = disciplinerepo()

        assert len(discipline_list) == 0

        discipline_list.add_discipline(5, 'Geometry')
        assert len(discipline_list) == 1

    def test_update(self):
        dr = disciplinerepo()
        dr.add_discipline(5, 'Algebra')

        dr.update_discipline(5, 'Linear Algebra')
        for d in dr.discipline_list:
            if d.id == 1:
                assert d.name == 'Linear Algebra'

    def test_remove(self):
        discipline_list = disciplinerepo()
        discipline_list.add_discipline(5, 'Geometry')
        discipline_list.remove_discipline(5)
        assert len(discipline_list) == 0

        try:
            discipline_list.remove_discipline(1)
            assert False
        except DisciplineException:
            assert True


class TestGrade(unittest.TestCase):
    def test_grade(self):
        g = grade(2, 3, 7)
        assert g.grade == 7
        assert g.student_id == 3
        assert g.discipline_id == 2

        g.grade = 8
        assert g.grade == 8

    def test_grade_validator(self):
        try:
            g = grade('', 412, 8)
            gv = GradeValidator()
            gv.validate(g)
            assert False
        except gradeException:
            assert True

        try:
            g = grade(3, '', 8)
            gv = GradeValidator()
            gv.validate(g)
            assert False
        except gradeException:
            assert True

        try:
            g = grade(3, 412, '')
            gv = GradeValidator()
            gv.validate(g)
            assert False
        except gradeException:
            assert True

        try:
            g = grade(3, 412, -2)
            gv = GradeValidator()
            gv.validate(g)
            assert False
        except gradeException:
            assert True

        try:
            g = grade('', 412, 15)
            gv = GradeValidator()
            gv.validate(g)
            assert False
        except gradeException:
            assert True