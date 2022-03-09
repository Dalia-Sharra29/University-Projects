from Domain.board import Board, BoardException


class UI:
    def __init__(self, game):
        self._game = game

    def read_human_move(self):
        coord = input('Select a position: ')
        if not coord[0].isalpha() or not coord[1].isdigit():
            raise ValueError('Bad input!')
        row = int(coord[1])
        # Calculate column index from character's ASCII code
        col = ord(coord[0].lower()) - 97
        return row, col

    def start(self):
        finished = False
        human_turn = True

        while not finished:
            # Print the board state
            print(self._game.board)
            print(' ')

            if human_turn:
                try:
                    coord = self.read_human_move()
                    move = self._game.human_move(coord[0], coord[1])
                except BoardException as be:
                    print(be)
                    continue
                except Exception as e:
                    print(e)
                    continue
                if move is False:
                    print('You win!')
                    print(self._game.board)
                    return
            else:
                if self._game.computer_move() is False:
                    print('Computer wins!')
                    print(self._game.board)
                    return
            human_turn = not human_turn
