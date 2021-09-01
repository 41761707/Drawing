# Drawing
Simple Graphics Editor made in Java
Note: This program is only avaiable in Polish language(so far).
## Installation
clone all the files. Open command prompt, change directory to that, which holds all cloned files.
```bash
javac Test.java
```
WARNING: This action requires Java compilator. If you lack one, please install it using:
```bash
sudo apt install openjdk-11-jdk-headless
```
After compilation is done, in order to lanunch the program type
```bash
java Test
```
## Content
This tool allow user to draw shapes just like in well know Graphics Editos such as paint.
So far, three shapes are allowed to be drawn (which may increase in a future): 
1. Rectangle
2. Circle
3. Triangle


In order to draw, choose given option from drop-down menu and press "Rysowanie" button.
After that you're able to draw your chosen shape with your mouse!
In order to modify, press "Modyfiowanie" button. 
Since that, you're only able to modify shapes, drawing is not allowed.
- Right click allows you to change color of a given shape
- Pressing wheel button allows you to increase shape (decreasing not implemented yet)
- Pushing down left mouse button and dragging allows yout to move your shape to given destination.

Button "info" contains information about creator and purpouse of a program
Button "instrukcja" contains usage information (Polish only).

## TO-DO:
1. Ability to save your work and load existing file.
2. Increase number of shapes avaiable to draw
3. Make user interface more frendly
4. Change drop-down menu with shapes into one with small pictures.
