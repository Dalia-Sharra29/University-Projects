from AI.strategy import SmarterStrategy
from Console.ui import UI
from Domain.board import Board
from Game.game import Game

board = Board(6, 6)
strategy = SmarterStrategy()
game = Game(board, strategy)
ui = UI(game)
ui.start()
