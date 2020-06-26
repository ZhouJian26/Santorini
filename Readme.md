![release](https://github.com/zhou0998/ing-sw-2020-Zhou-Zhou-Xu/workflows/release/badge.svg) ![development](https://github.com/zhou0998/ing-sw-2020-Zhou-Zhou-Xu/workflows/development/badge.svg)

# Santorini Game (Ing-sw-2020 GroupGC8)

This is the final project of the group GC8 of Computer Science's Bachelor's Degree of Politecnico di Milano in year 2019/2020.

- [Background](#Background)
- [Contributors](#Contributors)
- [Tests running](#Tests-running)
- [Usage](#Usage)
- [Getting Started](#Getting-Started)
- [Documentation](https://zhou0998.github.io/ing-sw-2020-Zhou-Zhou-Xu/)
- [License](#License)

## Background

This is Politecnico di Milano's Computer Science's bachelor's students'project. The aim of the project is to learn how to design a project by our own and it implements the game **Santorini**.


- ##### UML

For this project we've made several version of UML diagram.
  [Click here to see the full version of the UML](/UML)

- ##### Realized functions
  - **Complete game rules** : It's possible to play a game with 2 or 3 players and it fully supports all simple God's cards (*Including also Hermes*).
  - Additional function **Multiple games** : The server supports more games in the same time.
  - Additional function **Advanced Gods** : The game supports other Advanced God's cards which are: *Hera*, *Medusa*, *Persephone*, *Poseidon*, *Zeus*.


- ##### Game rules
  [Click here to see game rules](/example/santorini_rules_en.pdf)


## Contributors

  - [Jian Zhou](https://github.com/zhou0998 "Jian's GitHub profile")
    E-mail: jian.zhou@mail.polimi.it
  - [Zewei Xu](https://github.com/zhou0998 "Zewei's GitHub profile")
    E-mail: zewei.xu@mail.polimi.it
  - [Lisa Zhou](https://github.com/LilySana "Lisa's GitHub profile")
    E-mail: lisa.zhou@mail.polimi.it


## Tests running

(To complete)

## Getting Started

This project requires JAVA 11 or later versions to run.
For developers it requires also maven.


## Usage

[If you're a developer, please click here](#For-Developers)

1. Download the following assets from the [lastest release](https://github.com/zhou0998/ing-sw-2020-Zhou-Zhou-Xu/releases)

- Application.jar
- Server.jar

2. Run the server via command line  
   `java -jar Server.jar`

3. Run GUI client via double click on the Application.jar or via command line  
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

5. Run GUI client via double click on the Application.jar or via command line  
   `java -jar Application.jar` for GUI  
   `java -jar Application.jar cli` for CLI


(usage + demo + shortcuts)


## License

(to complete)
