import numpy
import matplotlib.pyplot as plt

curves = []
generations = 20000

for i in range(6):
    print(i)
    lines = []
    with open('./avgFitness'+str(0.5+i/10)+'.txt', 'r') as reader:
        for line in reader:
            line = line.strip()
            fitness = float(line)
            lines.append(fitness)

    curves.append(lines)

x = numpy.arange(generations)

plt.ylabel("Valor de adaptación")
plt.xlabel("Número de generaciones")
plt.plot(x, curves[0], 'b', curves[1], 'g', curves[2], 'y', curves[3], 'c', curves[4], 'm', curves[5], 'k')
plt.savefig("progressCurves.png")
plt.show()
