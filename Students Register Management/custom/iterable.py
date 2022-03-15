class IterableException(Exception):
    '''
    Exception class for students
    '''
    def __init__(self, msg):
        self._msg = msg

class Iterable:
    def __init__(self):
        self.__entities = []

    def __getitem__(self, index):
        if index >= len(self.__entities):
            raise IterableException("Index out of range!")
        return self.__entities[index]

    def __setitem__(self, index, new_item):
        if index >= len(self.__entities):
            raise IterableException("Index out of range!")
        self.__entities[index] = new_item

    def __delitem__(self, index):
        if index >= len(self.__entities):
            raise IterableException("Index out of range!")
        del self.__entities[index]

    def __iter__(self):
        self.__position = 0
        return self

    def __next__(self):
        try:
            item = self.__entities[self.__position]
        except IndexError:
            raise StopIteration
        self.__position += 1
        return item

    def __len__(self):
        return len(self.__entities)

    def append(self, item):
        self.__entities.append(item)

    def remove(self, item):
        self.__entities.remove(item)

