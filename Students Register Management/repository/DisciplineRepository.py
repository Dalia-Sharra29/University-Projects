from random import sample

from domain.Discipline import discipline, DisciplineException, DisciplineValidator
from custom.iterable import Iterable


class disciplinerepo:
    def __init__(self):
        self._discipline_list = Iterable()

    def __len__(self):
        return len(self._discipline_list)

    @property
    def discipline_list(self):
        return self._discipline_list

    @discipline_list.setter
    def discipline_list(self, new_list):
        self._discipline_list = new_list

    def add_discipline(self, discipline_id, discipline_name):
        '''
        Adds a new discipline to the list
        :param discipline_id: an integer
        :param discipline_name: a string
        :return: -
        '''
        discval = DisciplineValidator()
        d = discipline(discipline_id, discipline_name)
        discval.validate(d)
        for dis in self.discipline_list:
            if int(dis.id) == int(discipline_id):
                raise DisciplineException('Discipline with given id already exist!')
        self.discipline_list.append(d)
        return d

    def remove_discipline(self, discipline_id):
        '''
        Removes a discipline given by discipline id from the list
        :param discipline_id: an integer
        :return: -
        '''
        found = False
        obj = 0
        for d in self.discipline_list:
            if int(d.id) == int(discipline_id):
                found = True
                obj = d
                self.discipline_list.remove(d)
        if not found:
            raise DisciplineException('Discipline with given id not found!')
        return found, obj

    def update_discipline(self, discipline_id, discipline_name):
        '''
        Update a discipline name given by id
        :param discipline_id: an integer
        :param discipline_name: the new discipline name
        :return: -
        '''
        for d in self.discipline_list:
            if int(d.id) == int(discipline_id):
                obj = d
                name = d.name
                d.name = discipline_name
                return obj, name
        raise DisciplineException('Discipline with given id not found!')

    def search_discipline(self, search_str):
        found_list = []
        if search_str.isalpha():
            for d in self.discipline_list:
                if d.name.lower().find(search_str.lower()) != -1:
                    found_list.append(d)
        elif search_str.isdigit():
            for d in self.discipline_list:
                if str(d.id).lower().find(search_str.lower()) != -1:
                    found_list.append(d)
        else:
            raise DisciplineException('Bad input!')
        if found_list == []:
            raise DisciplineException('No such discipline was found!')
        return found_list

    def test_init_discipline(self):
        d = discipline(1, 'Algebra')
        self.discipline_list.append(d)
        d = discipline(2, 'Fundaments of Programming')
        self.discipline_list.append(d)
        d = discipline(3, 'Analysis')
        self.discipline_list.append(d)
        d = discipline(4, 'Informatics')
        self.discipline_list.append(d)
        d = discipline(5, 'Geometry')
        self.discipline_list.append(d)
        d = discipline(6, 'Chemistry')
        self.discipline_list.append(d)
        d = discipline(7, 'Physics')
        self.discipline_list.append(d)
        d = discipline(8, 'Computational Logic')
        self.discipline_list.append(d)
        d = discipline(9, 'English')
        self.discipline_list.append(d)
        d = discipline(10, 'French')
        self.discipline_list.append(d)
