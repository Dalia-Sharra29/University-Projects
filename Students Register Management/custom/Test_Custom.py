import unittest

from custom.CombSort import comb_sort
from custom.Filter import custom_filter
from custom.iterable import Iterable, IterableException


class TestCustom(unittest.TestCase):
    def test_iterable(self):
        test_list = Iterable()
        new_list = test_list.__iter__()
        self.assertEqual(test_list, new_list)

        self.assertRaises(StopIteration, test_list.__next__)

        for index in range(0, 3):
            test_list.append(index)
        self.assertEqual(test_list.__next__(), test_list[0])

        for index in range(0, 3):
            self.assertEqual(test_list[index], index)

        self.assertEqual(test_list[0], 0)
        self.assertEqual(test_list[1], 1)
        self.assertEqual(test_list[2], 2)

        self.assertEqual(len(test_list), 3)

        del test_list[0]
        self.assertEqual(len(test_list), 2)

        try:
            del test_list[5]
        except IterableException:
            assert True

        try:
            test_list[5] = 5
        except IterableException:
            assert True

        try:
            a = test_list[5]
        except IterableException:
            assert True

        test_list[0] = 4
        self.assertEqual(test_list[0], 4)

        test_list.remove(test_list[1])
        self.assertEqual(len(test_list), 1)

    def test_combsort(self):
        list_ = [8, 4, 1, 56, 3, -44, 23, -6, 28, 0]
        comb_sort(list_, lambda a, b: a > b)
        self.assertEqual(list_, [-44, -6, 0, 1, 3, 4, 8, 23, 28, 56])

        list_ = [8, 4, 1, 56, 3, -44, 23, -6, 28, 0]
        comb_sort(list_, lambda a, b: a < b)
        self.assertEqual(list_, [56, 28, 23, 8, 4, 3, 1, 0, -6, -44])

        list_ = [56, 28, 23, 8, 4, 3, 1, 0, -6, -44]
        comb_sort(list_, lambda a, b: a < b)
        self.assertEqual(list_, [56, 28, 23, 8, 4, 3, 1, 0, -6, -44])

        list_ = ['z', 'c', 'e', 'x', 'a', 'n']
        comb_sort(list_, lambda a, b: a > b)
        self.assertEqual(list_, ['a', 'c', 'e', 'n', 'x', 'z'])

        list_ = ['z', 'c', 'e', 'x', 'a', 'n']
        comb_sort(list_, lambda a, b: a < b)
        self.assertEqual(list_, ['z', 'x', 'n', 'e', 'c', 'a'])

    def test_custom_filter(self):
        list_ = [-5, 7, 9, -2, 0]
        custom_filter(list_, lambda x: x > 0)
        self.assertEqual(list_, [7, 9])

        list_ = [-5, 7, 9, -2, 0]
        custom_filter(list_, lambda x: x <= 0)
        self.assertEqual(list_, [-5, -2, 0])
