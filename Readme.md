![release](https://github.com/zhou0998/ing-sw-2020-Zhou-Zhou-Xu/workflows/release/badge.svg) ![development](https://github.com/zhou0998/ing-sw-2020-Zhou-Zhou-Xu/workflows/development/badge.svg)

# Santorini Game (Ing-sw-2020 GroupGC8)

This is the final project of the group GC8 of Computer Science's Bachelor's Degree of Politecnico di Milano in year 2019/2020.

- [Background](#Background)
- [UML](#UML)
- [Realized Functions](#Realized-functions)
- [Tests Coverage](#Tests-Coverage)
- [Usage](#Usage)
- [Getting Started](#Getting-Started)
- [Documentation](https://zhou0998.github.io/ing-sw-2020-Zhou-Zhou-Xu/docs)
- [Contributors](#Contributors)
- [License](#License)

## Background

The aim of the project is to learn how to design a project by our own and it implements the game **Santorini**.  
Santorini is a strategy-based board game, it's inspired by the beautiful view of Santorini (an island in the southern Aegean Sea) and its unique buildings based on Cycladic architecture.

The game is originally developed by [Roxley](https://roxley.com/ "Roxley official website") and [Spin Master](http://www.spinmaster.com/ "Spin Master official website"), [Click here to see the official game website](https://roxley.com/products/santorini?currency=EUR).

It's a very simple game, the rules are quite easy, every players has two workers and each turn consists of 2 steps: _Move_ and _Build_.

**Move**: Move one of your worker into a neighboring space. You may move your worker on the same level, step-up one level, or step down any number of levels.

**Build**: Then construct a building level adjacent to the worker you moved. When building on top of the third level, place a dome instead, removing that space from play.

This game provides also many tool cards called _God Power_ that makes the game even more interesting and challenging!  
 [Click here for the full rules](/example/santoriniRules.md)

## UML

The Unified Modeling Language (UML) is a general-purpose, developmental, modeling language in the field of software engineering that is intended to provide a standard way to visualize the design of a system. (_Wikipedia definition_)

For this project we've made several version of UML diagram.

[Click here to see the full version of the UML](/UML)

## Realized Functions

- **Complete game rules** : It's possible to play a game with 2 or 3 players and it fully supports all simple God's cards required.
- Additional function **Multiple Games** : The server supports more games in the same time.
- Additional function **Advanced Gods** : The game supports other Advanced God's cards which are: _Hera_, _Medusa_, _Triton_, _Poseidon_, _Zeus_.

### Server Side

- Implemented with JavaSE
- Creates connections with Socket
- Supports multiple games

### Client Side

- Implemented with JavaSE
- Supports two type of user interface: [CLI](#CLI) and [GUI](#GUI)
- GUI implemented with JavaFX
- Clients are connected to server through Socket

### Game

- There will be different lobby for players with different game mode
- Game starts when all players are logged in (two player or three players, depending on user's choice)
- _Challenger_ player and _Choose Start Player_ player are chosen by server randomly
- For every **game**, player's id (username) is unique
- During the game, if any player disconnects from game manually or accidentaly (connection's problem), the game will be ended for all players, unless the disconnected player has already lost the game.
- Players **cannot** reconnect to game
- Players must folloew game's [rules](/example/santoriniRules.md)

## Tests Coverage

**Controller and Model are all tested with a coverage 100%.**

![Failed loading][tests]

[tests]: /docs/test/TestCoverage "Tests Coverage"

[Click here for tests coverage report](https://zhou0998.github.io/ing-sw-2020-Zhou-Zhou-Xu/test)

## Getting Started

**Before starting**

This project requires **JAVA 11** or later to run.  
For developers it requires also **maven**.

### For Non-Developers (If you just want to play)

1. Download the following assets from the [lastest release](https://github.com/zhou0998/ing-sw-2020-Zhou-Zhou-Xu/releases)

- Application.jar
- Server.jar

2. Run the server via command line  
   `java -jar Server.jar`

3. Run GUI client by double clicking on the Application.jar or via command line  
   `java -jar Application.jar` for GUI  
   `java -jar Application.jar cli` for CLI

### For Developers

1. Clone the repository

2. Create assets via command line  
   `mvn clean package`

3. In the `shade` folder will be two .jar file

- Application.jar
- Server.jar

4. Run the server via command line  
   `java -jar Server.jar`

5. Run GUI client by double clicking on the Application.jar or via command line  
   `java -jar Application.jar` for GUI  
   `java -jar Application.jar cli` for CLI

## Usage

### CLI

**After you run the `java -jar Application.jar CLI` for CLI, you should be able to see the following view**:

**Type correctly server ip and port then insert your preferred game mode** ![Failed loading][cli1] ![Failed loading][cli2]

**Insert your username** ![Failed loading][cli3]

**Then follow the instructions by typing the numbers** ![Failed loading][cli4] ![Failed loading][cli5]

### GUI

**After you run the `java -jar Application.jar` for GUI, you should be able to see the following view:** ![Failed loading][gui1]

**Choose which game mode do you prefer: 2 players or 3 players** ![Failed loading][gui2]

**Insert your username:** ![Failed loading][gui3]

**Then wait for other players to join**

<!--- ![Failed loading][GUI4] --->

**Once the the lobby is fulled with the correct number of players, the game will start immediately.**

**A random player will be chosen to choose gods cards, the numbers of cards chosen should be the same number of players.** ![Failed loading][gui5]

**Move your mouse above gods and you'll be able to read the descrption of their power by putting the mouse above the gods.**

<!--- ![Failed loading][GUI6] --->

**Once every player finished to choose their own gods, a random player will choose a player to start the game.** ![Failed loading][gui7]

**Choose a color for your worker on the right side and place them on the board.** ![Failed loading][gui8]

**On your turn you'll see highlighted grids on the board that means all your possible moves in game at the current turn, choose your move(move/build) on the right side.** ![Failed loading][gui9]

**You can move your mouse above the gods to see their power.** ![Failed loading][gui10]

**Now open up your mind and enjoy the game!**

[cli1]: /example/CLI1.png "Initial page"
[cli2]: /example/CLI2.png "Choose mode"
[cli3]: /example/CLI3.png "Insert username"
[cli4]: /example/CLI4.png
[cli5]: /example/CLI5.png
[gui1]: /example/GUI1.png "Initial page"
[gui2]: /example/GUI2.png "Choose mode"
[gui3]: /example/GUI3.png "Insert username"

<!--- [GUI4]:/example/GUI4.png "Waiting for other players" --->

[gui5]: /example/GUI5.png "Choose gods"

<!--- [GUI6]:/example/GUI6.png "Read power" --->

[gui7]: /example/GUI7.png "Read power"
[gui8]: /example/GUI8.png "Board view"
[gui9]: /example/GUI9.png "Moves"
[gui10]: /example/GUI10.png "God Power"

## Contributors

- [Jian Zhou](https://github.com/zhou0998 "Jian's GitHub profile") E-mail: jian.zhou@mail.polimi.it
- [Zewei Xu](https://github.com/xuzewei28 "Zewei's GitHub profile") E-mail: zewei.xu@mail.polimi.it
- [Lisa Zhou](https://github.com/LilySana "Lisa's GitHub profile") E-mail: lisa.zhou@mail.polimi.it

## License

[Apache](/LICENSE)
