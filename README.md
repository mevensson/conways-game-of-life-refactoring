

# What? 

This is a Java refactoring practice project. It is a *Conway's-game-of-life-on-command-line-program*. The production code is hard to read and hard to maintain, weighing in on around 500 LOC. 

It is written in such a way that it really needs some refactoring. Feature by feature has been added without ever improving on the existing code base. Real world bad practice&trade;.

Luckily this project has a quite good test suite of jUnit 4 tests. Further, the test cases do test the GameOfLife program as a whole, so the internal design of the production code is not locked down by the test cases.


## Using the code

The code to improve is in the **src**-folder. The tests are in the **test**-folder.

### Good to know

* The project is a Eclipse project.
* It uses Java 8.

### Getting started

1. Clone this repository.
2. Import into Eclipse or squeeze it into your favorite IDE.
3. Practice your refactoring magic.




## Using the GameOfLife program

Use the flag **-?** to display usage help.

>    **`$ java gol.GameOfLife -?`**

>     Help requested
>      
>     Usage
>      java gol.GameOfLife [ARGUMENTS...]
>     
>      arguments:
>        -?              Prints this usage help.
>        -w <WIDTH>      Width of simulation view port. Default is 20.
>        -h <HEIGHT>     Height of simulation view port. Default is 15.
>        -f <FILE_PATH>  File with start state. Default is a random start state.
>        -@              Use spaced '@' and '.' instead of default '#' and '-'.
>        -O              Use 'O' instead of default '#'.
>        -s <STEPS>      Number of maximum generation steps. Default is 100.
>        -l <X>          Detect loops of maximum length x. Default is 0 - no loop detection.
>        -t <MS>         Time delay (ms) to wait between each step. Default is 0 ms.
>        -q              Quiet mode. Only outputs the last step in a simulation. Ignores time delay.

  
	
The following set of arguments would result in a 1000 steps simulation, detecting loops of max length 200, with view port size of 70x40, using @-signs for displaying cells and using a 150 ms delay between each simulation step.

>    **`$ java gol.GameOfLife -s 1000 -l 200 -w 70 -h 40 -@ -t 150`**

The default values also work fine. If running from the project root folder, use class path.

>   **`$ java -cp bin/ gol.GameOfLife`**

There are some example start states in the **examples**-folder in the project root. One of these is a 100-steps oscillator called *centinal*.

>   **`$ java -cp bin/ gol.GameOfLife -f examples/centinal -t 100 -O`**



## External resourses on Conways game of life

* [Wikipedia article on Conway's_Game_of_Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life)
* [LifeWiki, the wiki for Conway's Game of Life](http://www.conwaylife.com/wiki/Main_Page)

 
