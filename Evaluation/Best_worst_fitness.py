
runs = 40
values = []
bestFitness = 0
worstFitness = 0
for i in range(runs):
    with open('./progressCurves'+str(i)+'.txt', 'r') as reader:
        for line in reader:
            line = line.strip()
            fitness = float(line.split()[1])
            values.append(fitness)

bestFitness = min(values)
print(bestFitness)
worstFitness = max(values)
print(worstFitness)

