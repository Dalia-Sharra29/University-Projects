class Snake:
    def __init__(self, dim):
        self._positions = []
        self.init_snake(dim)

    @property
    def positions(self):
        return self._positions

    def head(self):
        return self._positions[0]

    def __len__(self):
        return len(self._positions)

    def snake_orientation(self, head_position):
        s_row = head_position[0]
        s_col = head_position[1]
        if [s_row+1, s_col] == self._positions[1]:
            return 'up'
        if [s_row, s_col - 1] == self._positions[1]:
            return 'right'
        if [s_row, s_col+1] == self._positions[1]:
            return 'left'
        if [s_row-1, s_col] == self._positions[1]:
            return 'down'

    def init_snake(self, dim):
        self._positions.append([dim // 2 - 1, dim // 2])
        self._positions.append([dim // 2, dim // 2])
        self._positions.append([dim // 2 + 1, dim // 2])
