Game Of Life
==============

  Conway's Game of Life is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input. One interacts with the Game of Life by creating an initial configuration and observing how it evolves.

  The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead. Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:
  
  1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
  2. Any live cell with two or three live neighbours lives on to the next generation.
  3. Any live cell with more than three live neighbours dies, as if by over-population.
  4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 
--------- 
#### MAKE .jar and RUN

    $ cd src
    /src$ javac -d ../bin *.java
    /src$ cd ../bin
    /bin$ jar -cvmf manifest.txt ../game-of-life.jar *.class
    /bin$ cd ..
    $ java -jar game-of-life.jar

---------
#### VOILA!

![screenshot from 2016-09-26 10 51 16](https://cloud.githubusercontent.com/assets/10475447/18828297/6c68be60-83d7-11e6-9de3-cb701ffd811a.png)
