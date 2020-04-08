# Bitcoin Block Hash Finder
A project that can calculate the hash of a bitcoin block given the block header

# How to build and run the project

## 1. The perfect way
Normally you would install the IntelliJ Idea IDE and import the gradle project. Then click the run icon.

## 2. On a GNU/Linux system

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
   
## 3. On Windows

I can only give you the links and you need to install things manually because Windows doesn't have a serious package manager in 2020

   - 
