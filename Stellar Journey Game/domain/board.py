import random

from texttable import Texttable


class BoardException(Exception):
    """
        Exception class for board
    """

    def __init__(self, msg):
        self._msg = msg


class Board:
    def __init__(self):
        self._dim = 8

        # None - empty square
        self._data = [[None for j in range(self._dim)] for i in range(self._dim)]

        self.lay_stars()
        self.lay_player_ship()
        self.lay_cruises(3)

    @property
    def dim(self):
        return self._dim

    @property
    def data(self):
        return self._data

    @data.setter
    def data(self, value):
        self._data = value

    def get(self, x, y):
        """
        Return symbol at position [x,y] on board
            0       -> the player's ship
            1       -> Blingon cruises
            3       -> Blingon cruises adjacent to the player's ship
            None    -> empty square
            2       -> star
        """
        return self._data[x][y]

    def get_ship(self):
        for row in range(self.dim):
            for col in range(self.dim):
                if self.get(row, col) == 0:
                    return [row, col]

    def get_cruises(self):
        list = []
        for row in range(self.dim):
            for col in range(self.dim):
                if self.get(row, col) == 1 or self.get(row, col) == 3:
                    list.append([row, col])
        return list

    def get_empty(self):
        list = []
        for row in range(self.dim):
            for col in range(self.dim):
                if self.get(row, col) is None:
                    list.append([row, col])
        return list

    def lay_stars(self):
        empty = self.get_empty()

        stars = 10
        while stars != 0:
            # Choose the location of the next apple
            location = random.choice(empty)
            row = location[0]
            col = location[1]

            if self.validate_position(row, col, 2):
                self._data[row][col] = 2
                stars -= 1
            empty.remove(location)

    def validate_position(self, x, y, val):
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
                if self.get(row, col) == val:
                    return False
        return True

    def lay_player_ship(self):
        empty = self.get_empty()

        location = random.choice(empty)
        row = location[0]
        col = location[1]
        self._data[row][col] = 0

    def lay_cruises(self, cruises):
        empty = self.get_empty()

        while cruises != 0:
            location = random.choice(empty)
            row = location[0]
            col = location[1]
            if self.is_adjacent(row, col):
                self._data[row][col] = 3
            else:
                self._data[row][col] = 1
            cruises -= 1
            empty.remove(location)

    def is_adjacent(self, x, y):
        if not self.validate_position(x, y, 0):
            return True
        return False

    def reveal(self):
        for row in range(self.dim):
            for col in range(self.dim):
                if self.get(row, col) == 1:
                    self.data[row][col] = 3

    def __str__(self):
        """
        Overwrite the str function
        """
        t = Texttable()
        header = ['0']
        for h in range(1, self.dim + 1):
            header.append(h)
        t.header(header)

        for row in range(self.dim):
            row_data = []

            for index in self._data[row]:
                if index is None:
                    row_data.append(' ')
                elif index == 0:
                    row_data.append('E')
                elif index == 3:
                    row_data.append('B')
                elif index == 1:
                    row_data.append(' ')
                else:
                    row_data.append('*')
            t.add_row([chr(65 + row)] + row_data)

        return t.draw()



