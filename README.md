# Snake 2D

A Snake game implementation written in Java.

The goal is to feed the snake with apples, the red dots that appears randomly during the game play.

Snake must be kept in the board limits and must not collide with itself. Failure to keep those constraints cause the game to end.

## Instructions
Use the arrows keys (&#8593;, &#8594;, &#8595;, &#8592;) to navigate the snake up, right, down and left.

Press F2 to pause.

### Prerequisites

* Java JDK 17+
* Maven 3.8.4+

### Installing

Clone this repository and compile it:

```bash
git clone https://github.com/thiagomfsup/snake-2d.git
cd snake-2d

mvn package
```

An executable Jar file will be created. Double-click on it or execute the following command to start playing:
```bash
java -jar ./target/snake-2d-1.0-SNAPSHOT.jar
```


## Authors

* [Thiago M. Ferreira](https://github.com/thiagomfsup)

## Acknowledgments

* Tania Rascia's [Writing a Snake Game for the Terminal in JavaScript](https://www.taniarascia.com/snake-game-in-javascript/) blog for the initial inspiration.
* Google's [Snake](https://www.google.com/search?q=play+snake) as a source of ideas for game improvements.
* [Leonel Gayard](https://github.com/leonelag) for reviewing the code and give me some code improvement suggestions.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
