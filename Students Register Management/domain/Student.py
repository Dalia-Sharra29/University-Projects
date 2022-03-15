
class StudentException(Exception):
    '''
    Exception class for students
    '''
    def __init__(self, msg):
        self._msg = msg


class student:
    '''
    Class to manage the student entity
    '''
    def __init__(self, id, name):
        self._student_id = id
        self._student_name = name

    @property
    def id(self):
        return self._student_id

    @property
    def name(self):
        return self._student_name

    def set_name(self, new_name):
        self._student_name = new_name

    @id.setter
    def id(self, new_id):
        self._student_id = new_id

    def __str__(self):
        return 'ID: ' + str(self.id) + ' ' + 'NAME: ' + str(self.name)


class StudentValidator():
    '''
    Validator class
    '''
    def validate(self, student):
        '''
        Vallidates a given student and raises exception if is needed
        :param student: a given student
        :return: -
        '''
        if student.id == '':
            raise StudentException('Id field can not be empty!')
        if student.name == '':
            raise StudentException('Name field can not be empty!')
        if not str(student.id).isdigit():
            raise StudentException('Id field should be a number')
