# sandhills

This is a little for fun project that simulates an idealized (theoretical) sand pile.
It is basically a java implementation of the following article: http://nautil.us/issue/23/dominoes/the-amazing-autotuning-sandpile
Sand grains are slowly added to the pile (a grid). Each grid cell has a maximum height of 3 sand grains, if its height reaches 4,
the pile collapses and the four grains are added to each of the neighbors (east west north south). The collapsed pile is then empty again (0 sand grains).
When this is repeated on a large scale, the distribution of the sand grains start to show symmetric patterns.

At the end, the an image is constructed. Each grid cell is mapped to a pixel on the image, the color depends on the number of sand grains in each cell.
Currently:
* blue = 0 grains
* green = 1 grain
* yellow = 2 grains
* red = 3 grains

Here the result of a simulation with 10 Million grains in a 2500 x 2500 cell grid:
![Resulting Image](/docs/images/result.png)

Ane here you can watch it grow: https://youtu.be/FpEApjaj46A

To try it yourself run:

```
gradlew run -Dexec.args="300 300 10000"
```

to run a simulation wit a 300 by 300 grid with 10000 sand grains.
Each simulation creates a csv file and one or multiple resulting png images.

The CSV-file can be used to create images with different colors after a simulation is done.
For this, use the ResultConverter class.







