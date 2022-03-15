from domain.board import BoardException, Board
from game.game import Game, GameException

class UI:
    def __init__(self, game):
        self._game = game

    @staticmethod
    def print_menu():
        print("----- COMMANDS -----")
        print("1. warp <coordinate>")
        print("2. fire <coordinate>")
        print("3. cheat")
        print("")

    def read_command(self):
        command = input('Select a command: ')
        tokens = command.strip().split(' ', 1)
        command_word = tokens[0].lower().strip()
        command_param = tokens[1].lower().strip() if len(tokens) == 2 else None

        if command_param is not None:
            row = ord(command_param[0].lower()) - 97
            col = int(command_param[1]) - 1
            command_param = [row, col]

        return command_word, command_param

    def start(self):
        game_over = False

        while not game_over:
            # Print the board state
            print(self._game.board)
            print(' ')
            ui.print_menu()
            command, param = self.read_command()
            if command == 'warp' and param is not None:
                try:
                    self._game.warp(param[0], param[1])
                except BoardException as be:
                    print(be)
                except GameException as ge:
                    print(ge)
                    game_over = True
            elif command == "fire" and param is not None:
                try:
                    self._game.fire(param[0], param[1])
                except BoardException as be:
                    print(be)
                except GameException as ge:
                    print(ge)
                    print("Player Wins!")
                    game_over = True
            elif command == "cheat":
                self._game.cheat()
            else:
                print('Bad command!')


board = Board()
game = Game(board)
ui = UI(game)
ui.start()
