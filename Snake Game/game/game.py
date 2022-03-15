from domain.board import BoardException


class Game:
    def __init__(self, board, snake):
        self._board = board
        self._snake = snake

    @property
    def board(self):
        return self._board

    def add_square(self, list, number):
        while number != 0:
            last = list.pop()
            self.board.data[last[0]][last[1]] = 1
            self._snake.positions.append(last)
            number -= 1

    def move(self, squares):
        snake_head = self._snake.positions[0]
        orientation = self._snake.snake_orientation(snake_head)
        row = snake_head[0]
        col = snake_head[1]
        new_positions = []
        aux = []
        length = len(self._snake.positions)
        apples = 0
        delete = []
        if orientation == 'up':
            if 0 <= row-squares:
                if self.board.is_apple(row - squares, col):
                    apples += 1
                elif self.board.is_snake(row - squares, col):
                    raise BoardException("Snake hits himself :(")
                self.board.data[row-squares][col] = 0
                for i in range(squares):
                    if self._snake.positions != []:
                        last = self._snake.positions.pop()
                        delete.append(last)
                        self.board.data[last[0]][last[1]] = None
                i = 1
                j = row - squares + 1
                if len(self._snake.positions) == 0:
                    lim = length
                else:
                    lim = length - len(self._snake.positions) + 1
                while i < lim:
                    if self.board.is_apple(j, col):
                        apples += 1
                    self.board.data[j][col] = 1
                    new_positions.append([j, col])
                    i += 1
                    j += 1

                aux = self._snake.positions[1:]
                self._snake.positions.clear()
                self._snake.positions.append([row - squares, col])
                self._snake.positions.extend(new_positions)
                self._snake.positions.extend(aux)

                if apples != 0:
                    self.add_square(delete, apples)
                    self.board.lay_apples(apples)

            else:
                raise BoardException('Snake hits the top wall!')
        elif orientation == 'down':
            if row+squares < self.board.dim:
                if self.board.is_apple(row + squares, col):
                    apples += 1
                elif self.board.is_snake(row + squares, col):
                    raise BoardException("Snake hits himself :(")
                self.board.data[row + squares][col] = 0
                self.board.data[row][col] = None
                for i in range(squares):
                    if self._snake.positions != []:
                        last = self._snake.positions.pop()
                        delete.append(last)
                        self.board.data[last[0]][last[1]] = None
                j = row + squares - 1
                i = 1
                if len(self._snake.positions) == 0:
                    lim = length
                else:
                    lim = length - len(self._snake.positions) + 1
                while i < lim:
                    if self.board.is_apple(j, col):
                        apples += 1
                    self.board.data[j][col] = 1
                    new_positions.append([j, col])
                    i += 1
                    j -= 1

                aux = self._snake.positions[1:]
                self._snake.positions.clear()
                self._snake.positions.append([row + squares, col])
                self._snake.positions.extend(new_positions)
                self._snake.positions.extend(aux)

                if apples != 0:
                    self.add_square(delete, apples)
                    self.board.lay_apples(apples)
            else:
                raise BoardException('Snake hits the bottom wall!')
        elif orientation == 'left':
            if col-squares >= 0:
                if self.board.is_apple(row, col - squares):
                    apples += 1
                elif self.board.is_snake(row, col - squares):
                    raise BoardException("Snake hits himself :(")
                self.board.data[row][col-squares] = 0
                self.board.data[row][col] = None

                for i in range(squares):
                    if self._snake.positions != []:
                        last = self._snake.positions.pop()
                        delete.append(last)
                        self.board.data[last[0]][last[1]] = None

                i = 1
                j = col - squares + 1
                if len(self._snake.positions) == 0:
                    lim = length
                else:
                    lim = length - len(self._snake.positions) + 1
                while i < lim:
                    if self.board.is_apple(row, j):
                        apples += 1
                    self.board.data[row][j] = 1
                    new_positions.append([row, j])
                    i += 1
                    j += 1

                aux = self._snake.positions[1:]
                self._snake.positions.clear()
                self._snake.positions.append([row, col - squares])
                self._snake.positions.extend(new_positions)
                self._snake.positions.extend(aux)

                if apples != 0:
                    self.add_square(delete, apples)
                    self.board.lay_apples(apples)

            else:
                raise BoardException('Snake hits the left side wall!')
        else:
            if col + squares < self.board.dim:
                if self.board.is_apple(row, col + squares):
                    apples += 1
                elif self.board.is_snake(row, col + squares):
                    raise BoardException("Snake hits himself :(")
                self.board.data[row][col+squares] = 0
                self.board.data[row][col] = None
                for i in range(squares):
                    if self._snake.positions != []:
                        last = self._snake.positions.pop()
                        delete.append(last)
                        self.board.data[last[0]][last[1]] = None

                i = 1
                j = col + squares - 1
                if len(self._snake.positions) == 0:
                    lim = length
                else:
                    lim = length - len(self._snake.positions) + 1
                while i < lim:
                    if self.board.is_apple(row, j):
                        apples += 1
                    self.board.data[row][j] = 1
                    new_positions.append([row, j])
                    i += 1
                    j -= 1

                aux = self._snake.positions[1:]
                self._snake.positions.clear()
                self._snake.positions.append([row, col + squares])
                self._snake.positions.extend(new_positions)
                self._snake.positions.extend(aux)

                if apples != 0:
                    self.add_square(delete, apples)
                    self.board.lay_apples(apples)

            else:
                raise BoardException('Snake hits the right side wall!')

    def change_direction(self, direction):
        snake_head = self._snake.positions[0]
        row = snake_head[0]
        col = snake_head[1]
        currently_orientation = self._snake.snake_orientation(snake_head)
        apples = 0

        if currently_orientation == direction:
            return

        if currently_orientation == 'up' and direction == 'down' or currently_orientation == 'down' and direction =='up':
            raise ValueError("Cannot change the snake's direction by 180 degrees!")

        if currently_orientation == 'left' and direction == 'right' or currently_orientation == 'right' and direction == 'left':
            raise ValueError("Cannot change the snake's direction by 180 degrees!")

        if direction == 'right':
            if col + 1 < self.board.dim:
                if self.board.is_apple(row, col + 1):
                    apples += 1
                elif self.board.is_snake(row, col + 1):
                    raise BoardException("Snake hits himself :(")
                self.board.data[row][col + 1] = 0
                self.board.data[row][col] = 1
                if apples == 0:
                    last = self._snake.positions.pop()
                    self.board.data[last[0]][last[1]] = None
                else:
                    self.board.lay_apples(apples)
                self._snake.positions.reverse()
                self._snake.positions.append([row, col + 1])
                self._snake.positions.reverse()

            else:
                raise BoardException('Snake hits the right side wall!')

        elif direction == 'left':
            if col - 1 >= 0:
                if self.board.is_apple(row, col - 1):
                    apples += 1
                elif self.board.is_snake(row, col - 1):
                    raise BoardException("Snake hits himself :(")
                self.board.data[row][col - 1] = 0
                self.board.data[row][col] = 1
                if apples == 0:
                    last = self._snake.positions.pop()
                    self.board.data[last[0]][last[1]] = None
                else:
                    self.board.lay_apples(apples)
                self._snake.positions.reverse()
                self._snake.positions.append([row, col - 1])
                self._snake.positions.reverse()
            else:
                raise BoardException('Snake hits the left side wall!')

        elif direction == 'up':
            if row - 1 >= 0:
                if self.board.is_apple(row - 1, col):
                    apples += 1
                elif self.board.is_snake(row - 1, col):
                    raise BoardException("Snake hits himself :(")
                self.board.data[row - 1][col] = 0
                self.board.data[row][col] = 1
                if apples == 0:
                    last = self._snake.positions.pop()
                    self.board.data[last[0]][last[1]] = None
                else:
                    self.board.lay_apples(apples)
                self._snake.positions.reverse()
                self._snake.positions.append([row - 1, col])
                self._snake.positions.reverse()
            else:
                raise BoardException('Snake hits the top wall!')

        else:
            if row + 1 < self.board.dim:
                if self.board.is_apple(row + 1, col):
                    apples += 1
                elif self.board.is_snake(row + 1, col):
                    raise BoardException("Snake hits himself :(")
                self.board.data[row + 1][col] = 0
                self.board.data[row][col] = 1
                if apples == 0:
                    last = self._snake.positions.pop()
                    self.board.data[last[0]][last[1]] = None
                else:
                    self.board.lay_apples(apples)
                self._snake.positions.reverse()
                self._snake.positions.append([row + 1, col])
                self._snake.positions.reverse()
            else:
                raise BoardException('Snake hits the bottom wall!')






