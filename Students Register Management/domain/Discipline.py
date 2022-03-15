class DisciplineException(Exception):
    def __init__(self, msg):
        self._msg = msg

class discipline:
    def __init__(self, id, name):
        self._discipline_id = id
        self._discipline_name = name

    @property
    def name(self):
        return self._discipline_name

    @property
    def id(self):
        return self._discipline_id

    @name.setter
    def name(self, new_name):
        self._discipline_name = new_name

    @id.setter
    def id(self, new_id):
        self._discipline_id = new_id

    def __str__(self):
        return 'ID: ' + str(self.id) + ' ' + 'NAME: ' + str(self.name)


class DisciplineValidator():
    '''
    Validator class
    '''
    def validate(self, discipline):
        '''
        Validates a given discipline an raises exception if is needed
        :param discipline: a given discipline
        :return: -
        '''
        if discipline.id == '':
            raise DisciplineException('Id field can not be empty!')
        if discipline.name == '':
            raise DisciplineException('Name field can not be empty!')
        if not str(discipline.id).isdigit():
            raise DisciplineException('Id field should be a number')

