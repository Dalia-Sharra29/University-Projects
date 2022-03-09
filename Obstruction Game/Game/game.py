class Game:
    def __init__(self, board, strategy):
        self._board = board
        self._strategy = strategy

    @property
    def board(self):
        return self._board

    def human_move(self, x, y):
        return self._board.move(x, y, 'X')

    def computer_move(self):
        return self._strategy.next_move(self.board)
