from console.settings import configuration
from domain.board import Board, BoardException
from domain.snake import Snake
from game.game import Game

class UI:
    def __init__(self, game):
        self._game = game

    def read_command(self):
        command = input('Select a command: ')
        tokens = command.strip().split(' ', 1)
        command_word = tokens[0].lower().strip()
        command_param = tokens[1].lower().strip() if len(tokens) == 2 else None

        return command_word, command_param

    def start(self):
        game_over = False

        while not game_over:
            # Print the board state
            print(self._game.board)
            print(' ')
            command, param = self.read_command()
            if command == 'move':
                if param is None:
                    param = 1
                try:
                    self._game.move(int(param))
                except BoardException as be:
                    print('Game Over -', be)
                    game_over = True
            elif command in ['up', 'down', 'left', 'right']:
                try:
                    self._game.change_direction(command)
                except BoardException as be:
                    print('Game Over -', be)
                    game_over = True
                except ValueError as ve:
                    print(ve)
            else:
                print('Bad command!')


config = configuration()
list = []
config.read_setting_file(list)
board = Board(int(list[0]), int(list[1]))
snake = Snake(int(list[0]))
game = Game(board, snake)
ui = UI(game)
ui.start()
