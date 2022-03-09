import random

class RandomStrategy():

    def next_move(self, board):
        """
        Make a random, but valid move
        """
        # Store possible moves here
        available_moves = []

        for col in range(board.get_colums):
            for row in range(board.get_rows):
                if board.is_free(row, col):
                    available_moves.append((row, col))
        # Pick one of the available moves
        move = random.choice(available_moves)
        return board.move(move[0], move[1], 'O')


class SmarterStrategy:

    def next_move(self, board):
        """
        The strategy consists in the fact that the computer is not making its move randomly,
        but instead, he places his move on the cell that will block the most
        surrounding cells.
        """
        # a list of list which contains the coordinates of the empty cells
        empty = board.get_empty_cells()
        if len(empty) == 0:
            return None
        max_neighbours = -1
        max_index = 0
        for index in range(len(empty)):
            l = board.count_neighbours(empty[index][0], empty[index][1])
            if l > max_neighbours:
                max_neighbours = l
                max_index = index

        move = empty[max_index]
        return board.move(move[0], move[1], 'O')
