import unittest

from AI.strategy import RandomStrategy
from Domain.board import Board
from Game.game import Game


class TestGame(unittest.TestCase):
    def test_human_move(self):
        board = Board(5, 6)
        strategy = RandomStrategy()
        game = Game(board, strategy)
        self.assertEqual(game.human_move(2, 3), True)

    def test_computer_move(self):
        board = Board(5, 6)
        strategy = RandomStrategy()
        game = Game(board, strategy)
        self.assertEqual(game.computer_move(), True)
