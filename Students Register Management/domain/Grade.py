

class gradeException(Exception):
    def __init__(self, msg):
        self._msg = msg

class grade:
    def __init__(self, discipline, student, value):
        self.__discipline_id = discipline
        self.__student_id = student
        self._grade_value = value

    @property
    def discipline_id(self):
       return self.__discipline_id

    @property
    def student_id(self):
        return self.__student_id

    @property
    def grade(self):
        return self._grade_value

    @grade.setter
    def grade(self, new_grade):
        self._grade_value = new_grade

    def __str__(self):
        return 'DISCIPLINE ID: ' + str(self.discipline_id) + ' ' + 'STUDENT ID: ' + str(self.student_id) + ' ' + 'GRADE: ' + str(self.grade)


class GradeValidator():
    '''
    Validator class
    '''

    def validate(self, grade):
        '''
        Validates a given grade and raises exceptions if is needed
        :param grade: a given grade
        :return: -
        '''
        if grade.discipline_id == '':
            raise gradeException('Discipline id field can not be empty!')
        if grade.student_id == '':
            raise gradeException('Student id field can not be empty!')
        if grade.grade == '':
            raise gradeException('Grade field can not be empty!')
        if int(grade.grade) < 1 or int(grade.grade) > 10:
            raise gradeException('Grade should be between 1 and 10!')
