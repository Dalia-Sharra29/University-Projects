def custom_filter(array, acceptance_function):
    i = 0
    while i < len(array):
        if not acceptance_function(array[i]):
            del array[i]
        else:
            i += 1
