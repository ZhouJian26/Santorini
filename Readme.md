![release](https://github.com/zhou0998/ing-sw-2020-Zhou-Zhou-Xu/workflows/release/badge.svg) ![development](https://github.com/zhou0998/ing-sw-2020-Zhou-Zhou-Xu/workflows/development/badge.svg)

# Santorini Game (Ing-sw-2020 GroupGC8)

This is the final project of the group GC8 of Computer Science's Bachelor's Degree of Politecnico di Milano in year 2019/2020.

- [Background](#Background)
- [UML](#UML)
- [Realized Functions](#Realized-functions)
- [Tests running](#Tests-running)
- [Usage](#Usage)
- [Getting Started](#Getting-Started)
- [Documentation](https://zhou0998.github.io/ing-sw-2020-Zhou-Zhou-Xu/)
- [Contributors](#Contributors)
- [License](#License)

## Background

The aim of the project is to learn how to design a project by our own and it implements the game **Santorini**.  
Santorini is a strategy-based board game, it's inspired by the beautiful view of Santorini (an island in the southern Aegean Sea) and its unique buildings based on Cycladic architecture.

The game is originally developed by [Roxley](https://roxley.com/ "Roxley official website") and [Spin Master](http://www.spinmaster.com/ "Spin Master official website"), [Click here to see the official game website](https://roxley.com/products/santorini?currency=EUR).

It's a very simple game, the rules are quite easy, every players has two workers and each turn consists of 2 steps: *Move* and *Build*.

**Move**: Move one of your worker into a neighboring space. You may move your worker on the same level, step-up one level, or step down any number of levels.  

**Build**: Then construct a building level adjacent to the worker you moved. When building on top of the third level, place a dome instead, removing that space from play.  

This game provides also many tool cards called *God Power* that makes the game even more interesting and challenging!  
  [Click here for the full rules](/example/santoriniRules.md)


## UML

The Unified Modeling Language (UML) is a general-purpose, developmental, modeling language in the field of software engineering that is intended to provide a standard way to visualize the design of a system. (*Wikipedia definition*)  

For this project we've made several version of UML diagram.  

  [Click here to see the full version of the UML](/UML)

## Realized Functions

  - **Complete game rules** : It's possible to play a game with 2 or 3 players and it fully supports all simple God's cards.
  - Additional function **Multiple Games** : The server supports more games in the same time.
  - Additional function **Advanced Gods** : The game supports other Advanced God's cards which are: *Hera*, *Medusa*, *Triton*, *Chronus*, *Poseidon*, *Zeus*.

(add server client implementatin ecc)

## Tests running

(To complete)

## Getting Started

**Before starting**

This project requires **JAVA 11** or higher versions to run.  
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

### GUI

After you run the `java -jar Application.jar` for GUI, you should be able to see the following view:
![Failed loading][GUI1]


[GUI1]:/example/GUI1.png "Initial page"



(usage + demo + shortcuts)

## Contributors

  - [Jian Zhou](https://github.com/zhou0998 "Jian's GitHub profile")
    E-mail: jian.zhou@mail.polimi.it
  - [Zewei Xu](https://github.com/xuzewei28 "Zewei's GitHub profile")
    E-mail: zewei.xu@mail.polimi.it
  - [Lisa Zhou](https://github.com/LilySana "Lisa's GitHub profile")
    E-mail: lisa.zhou@mail.polimi.it



## License

[Apache](/LICENSE)
