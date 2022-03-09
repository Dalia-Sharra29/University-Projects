from texttable import Texttable

class BoardException(Exception):
    '''
        Exception class for board
    '''
    def __init__(self, msg):
        self._msg = msg

class Board:
    def __init__(self, rows, columns):
        self._rows = rows
        self._columns = columns

        # None - empty square
        self._data = [[None for j in range(self._columns)] for i in range(self._rows)]
        self._free_spaces = self._rows * self._columns

    @property
    def get_rows(self):
        return self._rows

    @property
    def get_colums(self):
        return self._columns

    def get(self, x, y):
        """
        Return symbol at position [x,y] on board
            None     -> empty square
            'X', 'O' -> symbols
            1        -> blocked cell
        """
        return self._data[x][y]

    def get_empty_cells(self):
        """
        Return a list of cells objects that are not occupied or blocked
        """
        list = []
        for row in range(self.get_rows):
            for col in range(self.get_colums):
                if self.is_free(row, col):
                    list.append((row, col))
        return list

    def count_neighbours(self, x, y):
        """
        Count the number of neighbours of a cell given by coordinates x, y
        :param x: row
        :param y: column
        :return: number of neighbours
        """
        count = 0
        if x == 0:
            r1 = x
            r2 = x + 2
        elif x == self._rows - 1:
            r1 = x - 1
            r2 = x + 1
        else:
            r1 = x - 1
            r2 = x + 2
        if y == 0:
            c1 = y
            c2 = y + 2
        elif y == self._columns - 1:
            c1 = y - 1
            c2 = y + 1
        else:
            c1 = y - 1
            c2 = y + 2

        for row in range(r1, r2):
            for col in range(c1, c2):
                if self.is_free(row, col):
                    count += 1
        return count - 1

    def is_free(self, x, y):
        '''
        Check if a cell given by the coordinates x, y is free or not
        :param x: row
        :param y: column
        :return: True or False
        '''
        return self.get(x, y) is None

    def validate_move(self, x, y, symbol):
        '''
        Validates a given move
        :param x: int, row
        :param y: int, column
        :param symbol: 'X' or 'O'
        :return:
        '''
        if symbol not in ['X', 'O']:
            raise BoardException('Bad symbol!')
        if x < 0 or y < 0 or x >= self._rows or y >= self._columns:
            raise BoardException("Move outside the board!")
        if self._data[x][y] == 1:
            raise BoardException("Square already taken!")
        return True

    def move(self, x, y, symbol):
        '''
        Make a move on the board
        :param x: int, row
        :param y: int, column
        :param symbol: 'X' or 'O'
        :return: True if there still exist free cells and False otherwise
        '''
        self.validate_move(x, y, symbol)

        # Check were we are on the board
        if x == 0:
            r1 = x
            r2 = x + 2
        elif x == self._rows - 1:
            r1 = x - 1
            r2 = x + 1
        else:
            r1 = x - 1
            r2 = x + 2
        if y == 0:
            c1 = y
            c2 = y + 2
        elif y == self._columns - 1:
            c1 = y - 1
            c2 = y + 1
        else:
            c1 = y - 1
            c2 = y + 2

        # Mark the neighbours
        for row in range(r1, r2):
            for col in range(c1, c2):
                if self._data[row][col] not in ['X', 'O', 1]:
                    self._data[row][col] = 1
                    self._free_spaces -= 1

        # Mark the new move on the board
        self._data[x][y] = symbol
        return self._free_spaces > 0

    def __str__(self):
        """
        Overwrite the str function
        """
        t = Texttable()
        header = [' ']
        for h in range(self._columns):
            header.append(chr(65 + h))
        t.header(header)

        for row in range(self._rows):
            row_data = []

            for index in self._data[row]:
                if index is None:
                    row_data.append(' ')
                elif index == 'X' or index == 'O':
                    row_data.append(index)
                elif index == 1:
                    row_data.append('*')
            t.add_row([row] + row_data)

        return t.draw()
