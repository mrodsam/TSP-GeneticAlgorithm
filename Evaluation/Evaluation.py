import numpy
import matplotlib.pyplot as plt

files = []
runs = 40
generations = 300

for i in range(runs):
    lines = []
    with open('./progressCurves'+str(i)+'.txt', 'r') as reader:
        for line in reader:

            line = line.strip()
            fitness = float(line.split()[1])
            lines.append(fitness)
    files.append(lines)

fitnessAvg = []
for j in range(generations):
    fitnessSum = 0
    for k in range(runs):
        fitnessSum += files[k][j]

    fitnessAvg.append(fitnessSum/runs)

avgFitnessFile = open('avgFitness.txt', 'w')
for i in range(len(fitnessAvg)):
    avgFitnessFile.write(str(fitnessAvg[i])+'\n')

x = numpy.arange(generations)

plt.ylabel("Valor de adaptación")
plt.xlabel("Número de generaciones")
plt.plot(x, fitnessAvg, 'b')
plt.savefig("progressCurve.png")
plt.show()
