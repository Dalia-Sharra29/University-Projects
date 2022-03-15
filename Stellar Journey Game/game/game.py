from domain.board import BoardException

class GameException(Exception):
    '''
        Exception class for board
    '''

    def __init__(self, msg):
        self._msg = msg

class Game:
    def __init__(self, board):
        self._board = board

    @property
    def board(self):
        return self._board

    def warp(self, x, y):
        ship_position = self.board.get_ship()
        row = ship_position[0]
        col = ship_position[1]

        if self.board.get(x, y) == 1 or self.board.get(x,y) == 3:
            raise GameException("Game Over - The Endeavour has been destroyed!")
        if row == x:
            for j in range(min(col, y), max(col, y) + 1):
                if self.board.get(row, j) == 2:
                    raise BoardException("Move can't be done - a star is in the way")
            self.board.data[x][y] = 0
            self.board.data[row][col] = None
        elif col == y:
            for i in range(min(row, x), max(row, x) + 1):
                if self.board.get(i, col) == 2:
                    raise BoardException("Move can't be done - a star is in the way")
            self.board.data[x][y] = 0
            self.board.data[row][col] = None
        elif abs(x-y) == abs(row-col):
            j = min(col, y)
            for i in range(min(row, x), max(row, x) + 1):
                if self.board.get(i, j) == 2:
                    raise BoardException("Move can't be done - a star is in the way")
                j += 1
            self.board.data[x][y] = 0
            self.board.data[row][col] = None
        elif (x + y) == (row + col):
            j = max(col, y)
            for i in range(min(row, x), max(row, x) + 1):
                if self.board.get(i, j) == 2:
                    raise BoardException("Move can't be done - a star is in the way")
                j -= 1
            self.board.data[x][y] = 0
            self.board.data[row][col] = None
        else:
            raise BoardException("Move can't be done - the new position is not on the same rank, file"
                                 " or diagonal as the starting position")

    def fire(self, x, y):
        if self.board.get(x, y) == 3:
            self.board.data[x][y] = None
            cruises = self.board.get_cruises()
            if len(cruises) > 0:
                for pos in cruises:
                    self.board.data[pos[0]][pos[1]] = None
                self.board.lay_cruises(len(cruises))
            else:
                raise GameException("Game Over - All enemy ships were destroyed!")
        else:
            raise BoardException("Can't fire there - Wrong coordinates!")

    def cheat(self):
        self.board.reveal()