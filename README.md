# Bitcoin Block Hash Finder
A project that can calculate the hash of a bitcoin block given the block header

## How to build and run the project

### 1. The perfect way
Normally you would install the [IntelliJ Idea IDE](https://www.jetbrains.com/idea/download/index.html) and import the gradle project. Then click the run icon.

### 2. On a GNU/Linux system

1. Install JAVA. Both the JDK and the JRE. I compiled the project with JAVA 13, but it is compatible with down to JAVA 8
   
   - on **Ubuntu** based systems you can run `sudo apt install openjdk-11-jdk` or `sudo apt install openjdk-8-jdk` (I hope)
   - on **Arch** based systems run `sudo pacman -S jdk13-openjdk` or the same command for `jdk11-openjdk` etc.
   - on the rest: I don't know, you are on your own

2. Install the gradle build system
   
   - **Ubuntu** `sudo apt install gradle`
   - **Arch** `sudo pacman -S gradle`
   
3. Install Kotlin

   - **Ubuntu**  `snap install kotlin`
   - **Arch** same as above, or if you don't have snap support or hate them `sudo pacman -S kotlin`
   
### 3. On Windows

I can only give you the links and you need to install things manually because Windows doesn't have a serious package manager in 2020

- **[JAVA](https://jdk.java.net/java-se-ri/13):** Download the Windows 10 build and then set the [JAVA PATH](https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html)
- **[Gradle](https://gradle.org/install/)**: Follow the instructions for windows
- **[Kotlin](https://kotlinlang.org/docs/tutorials/command-line.html):** I recommend following the chocolatey route

## How to run the project (without the IDE)

0. Download this repo either with `git clone https://github.com/alexandru-balan/btcblock-hash-finder.git` or through the download button

1. Navigate to `btcblock-hash-finder/`

2. Run in this order:
   - `./gradlew build` or to speciffy a java folder path `./gradlew build -Dorg.gradle.java.home={path_to_java_folder}`, for example I run `./gradlew build -Dorg.gradle.java.home=/usr/lib/jvm/java-13-openjdk/`
   - `./gradlew run`; I run `./gradlew run -Dorg.gradle.java.home=/usr/lib/jvm/java-13-openjdk/`
   
## How to use the project

This project is intended to be just a starting point that I made as a university project. I architected the project to be easy to build through gradle and easy to incorporate in other projects.

The main classes are **BlockHeader** and **Finder** and are essential to the project. The **BlockHeader** class implements a **Hashable** interface which means that you can use `blockHeader.hash()` to generate a hash of the block. 

Everything was designed with speed in mind so most auxiliary stuff like the files you will find in the *extensions* package uses bitwise operations. The **Finder** class is multithreaded and can run through 100_000_000 hashes in a matter of minutes on most modern laptops. *(7 minutes on a laptop with Intel i7 3.2GHz and 8 cores)*

The *playground* package is used for the executable part of the project. Please put the **Main.kt** file there and any other scripts you might want to run.
