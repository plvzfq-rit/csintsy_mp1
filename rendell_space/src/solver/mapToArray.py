w = 19
h = 11

with open("../../maps/original1.txt", "r") as f:
    for line in f:
        arr = list(line)
        if len(arr) < 19:
            arr += [' '] * (19 - len(arr))
        print(arr)