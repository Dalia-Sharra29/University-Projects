def comb_sort(array, condition):
    n = len(array)

    # Initialize gap
    gap = n

    # Initialize swapped as true to make sure that loop runs
    swapped = True

    # Keep running while gap is more than 1 and last iteration caused a swap
    while gap != 1 or swapped is True:
        # Find next gap
        gap = (gap * 10) // 13
        if gap < 1:
            gap = 1

        # Initialize swapped as false so that we can check if swap happened or not
        swapped = False

        # Compare all elements with current gap
        for i in range(0, n - gap):
            if condition(array[i], array[i + gap]):
                array[i], array[i + gap] = array[i + gap], array[i]
                swapped = True