# Snake 2D

A Snake game implementation written in Java.

## Instructions
Use the arrows keys (&#8593;, &#8594;, &#8595;, &#8592;) to navigate the snake up, right, down and left. 

The object is to feed the snake with apples, the red dots that appears randomly during the game play. 

Snake must be kept in the board limits and must not collides with itself. Failure to keep those constraints cause the game to end.

### Prerequisites

Java JDK must be installed in your environment in order to compile and run this project.

### Installing

Clone this repository, compile it, and them run it:

```bash
git clone https://github.com/thiagomfsup/snake-2d.git
cd snake-2d

mkdir target

javac -s src -d target src\com\tmf\snake2d\*.java src\com\tmf\snake2d\model\*.java

java -cp target com.tmf.snake2d.Snake2D
```

## Authors

* [Thiago M. Ferreira](https://github.com/thiagomfsup)

## Acknowledgments

* Tania Rascia's [Writing a Snake Game for the Terminal in JavaScript](https://www.taniarascia.com/snake-game-in-javascript/) blog for the initial inspiration.
* Google's [Snake](https://www.google.com/search?q=play+snake) as a source of ideas for game improvements.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
