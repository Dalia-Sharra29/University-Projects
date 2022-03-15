import random

from texttable import Texttable


class BoardException(Exception):
    '''
        Exception class for board
    '''

    def __init__(self, msg):
        self._msg = msg


class Board:
    def __init__(self, dim, apples):
        self._dim = dim
        self._apples_count = apples

        # None - empty square
        self._data = [[None for j in range(self._dim)] for i in range(self._dim)]
        self._data[self._dim // 2 - 1][self._dim // 2] = 0
        self._data[self._dim // 2][self._dim // 2] = 1
        self._data[self._dim // 2 + 1][self._dim // 2] = 1

        self.lay_apples(apples)

    @property
    def dim(self):
        return self._dim

    @property
    def apples(self):
        return self._apples_count

    @property
    def data(self):
        return self._data

    @data.setter
    def data(self, value):
        self._data = value

    @dim.setter
    def dim(self, value):
        self._dim = value

    def get(self, x, y):
        """
        Return symbol at position [x,y] on board
            0       -> head of the snake
            1       -> body of the snake
            None    -> empty square
            2       -> apple
        """
        return self._data[x][y]

    def is_apple(self, x, y):
        if self.get(x, y) == 2:
            return True
        return False

    def is_snake(self, x, y):
        if self.get(x, y) == 1:
            return True
        return False

    def empty(self):
        list = []
        for row in range(self.dim):
            for col in range(self.dim):
                if self.get(row, col) is None:
                    list.append([row, col])
        return list

    def lay_apples(self, apples):
        map = self.empty()

        while apples > 0 and map != []:
            # Choose the location of the next apple
            location = random.choice(map)
            row = location[0]
            col = location[1]

            if self.validate_position(row, col):
                # map.remove(location)
                self._data[row][col] = 2
                apples -= 1
            map.remove(location)


    def validate_position(self, x, y):
        if x == 0:
            r1 = x
            r2 = x + 2
        elif x == self._dim - 1:
            r1 = x - 1
            r2 = x + 1
        else:
            r1 = x - 1
            r2 = x + 2
        if y == 0:
            c1 = y
            c2 = y + 2
        elif y == self._dim - 1:
            c1 = y - 1
            c2 = y + 1
        else:
            c1 = y - 1
            c2 = y + 2

        for row in range(r1, r2):
            for col in range(c1, c2):
                if self.get(row, col) == 2:
                    return False
        return True

    def __str__(self):
        """
        Overwrite the str function
        """
        t = Texttable()

        for row in range(self.dim):
            row_data = []

            for index in self._data[row]:
                if index is None:
                    row_data.append(' ')
                elif index == 0:
                    row_data.append('*')
                elif index == 1:
                    row_data.append('+')
                else:
                    row_data.append('.')
            t.add_row(row_data)

        return t.draw()

