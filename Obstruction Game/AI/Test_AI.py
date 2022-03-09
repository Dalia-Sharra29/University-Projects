import unittest

from AI.strategy import RandomStrategy, SmarterStrategy
from Domain.board import Board


class TestRandomStrategy(unittest.TestCase):
    def test_next_move(self):
        board = Board(5, 6)
        strategy = RandomStrategy()
        self.assertEqual(strategy.next_move(board), True)

class TestSmarterStrategy(unittest.TestCase):
    def test_next_move(self):
        board = Board(5, 6)
        strategy = SmarterStrategy()
        self.assertEqual(strategy.next_move(board), True)
        board.move(3, 3, 'X')
        board.move(1, 1, 'O')
        board.move(1, 4, 'X')
        board.move(4, 0, 'O')
        board.move(4, 5, 'X')
        self.assertEqual(strategy.next_move(board), None)

