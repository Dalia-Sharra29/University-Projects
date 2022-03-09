import unittest

from texttable import Texttable

from Domain.board import Board, BoardException


class TestBoard(unittest.TestCase):
    def test_get(self):
        board = Board(5,6)
        self.assertEqual(board.get(2,3), None)

    def test_get_empty_cells(self):
        board = Board(5, 6)
        self.assertEqual(board.get_empty_cells(), [(0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (1, 0), (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (2, 0), (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (3, 0), (3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (4, 0), (4, 1), (4, 2), (4, 3), (4, 4), (4, 5)])
        board.move(2,3,'X')
        self.assertEqual(board.get_empty_cells(),[(0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (1, 0), (1, 1), (1, 5), (2, 0), (2, 1), (2, 5), (3, 0), (3, 1), (3, 5), (4, 0), (4, 1), (4, 2), (4, 3), (4, 4), (4, 5)])

    def test_count_neighbours(self):
        board = Board(5, 6)
        self.assertEqual(board.count_neighbours(2,3), 8)
        board.move(2, 3, 'X')
        self.assertEqual(board.count_neighbours(2, 3), -1)
        self.assertEqual(board.count_neighbours(0, 0), 3)
        self.assertEqual(board.count_neighbours(4, 5), 2)

    def test_is_free(self):
        board = Board(5, 6)
        self.assertEqual(board.is_free(2,3), True)
        board.move(2, 3, 'X')
        self.assertEqual(board.is_free(2, 3), False)

    def test_validate_move(self):
        board = Board(5, 6)

        try:
            board.validate_move(2,9,'X')
        except BoardException:
            assert True

        try:
            board.validate_move(9,3,'X')
        except BoardException:
            assert True

        try:
            board.validate_move(2,3,'Z')
        except BoardException:
            assert True

        board.move(2,3,'X')
        try:
            board.validate_move(1,3,'O')
        except BoardException:
            assert True

    def test_move(self):
        board = Board(5, 6)
        board.move(0,0,'X')
        self.assertEqual(board._free_spaces, 26)
        self.assertEqual(board.get(0, 0), 'X')
        board.move(4, 5, 'O')
        self.assertEqual(board._free_spaces, 22)
        self.assertEqual(board.get(4, 5), 'O')

    def test_str(self):
        board = Board(5, 6)
        board.move(0,0, 'X')
        t = Texttable()
        t.header([' ', 'A', 'B', 'C', 'D', 'E', 'F'])
        t.add_row(['0', 'X', '*', ' ', ' ', ' ', ' '])
        t.add_row(['1', '*', '*', ' ', ' ', ' ', ' '])
        t.add_row(['2', ' ', ' ', ' ', ' ', ' ', ' '])
        t.add_row(['3', ' ', ' ', ' ', ' ', ' ', ' '])
        t.add_row(['4', ' ', ' ', ' ', ' ', ' ', ' '])
        self.assertEqual(str(board), t.draw())