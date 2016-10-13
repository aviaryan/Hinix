# Hinix

A Interesting Word game. Inspired from the classic [Boggle game](https://en.wikipedia.org/wiki/Boggle)

## Screenshots

| ![](http://i.imgur.com/xmA4t4S.png) | ![](http://i.imgur.com/Hr9U1jP.png) | ![](http://i.imgur.com/OSk0yxN.png) |
|-------------------------------------|-------------------------------------|-------------------------------------|
| ![](http://i.imgur.com/RmrnuRG.png) | ![](http://i.imgur.com/HFL0KLr.png) | ![](http://i.imgur.com/9BvsuNf.png) |

## Rules of the Game: 
+ You have to make as many words as possible from a given grid of words.
+ You can move up, down, left, right, and diagonally to make a word.
+ Minimum word size is 4.
+ Target is to make every word possible from the grid.
+ You are given some initial points at the starting of each game and these points keep decreasing by the differnce of the no. of words you made and total no. of words possible.
+ The game continues till you exhaust all the points.

## Concept Used:

+ Depth First Search and a custom optmized greedy algorithm for generating the boards.
+ Depth First Traversal for solving the board.

### Team

* [Avi Aryan](https://github.com/aviaryan) - Board generation and solution algorithm
* [Nilesh Chaturvedi](https://github.com/Nilesh4145) - UI (home activity)
* [Raju Koushik](https://github.com/RajuKoushik) - Algorithm and logic for Gameboard word selection
* [Saurabh Jain](https://github.com/saurabhjn76) - Gameplay UI + supporting logic
