# Hinix

[![Travis branch](https://img.shields.io/travis/aviaryan/Hinix/neo-reborn.svg?maxAge=2592000)]()
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/16ed87cf73834773abb8d331d0ac9a8f)](https://www.codacy.com/app/aviaryan/Hinix)

[![Get it on Google Play](https://play.google.com/intl/en_us/badges/images/badge_new.png)](https://play.google.com/store/apps/details?id=in.aviaryan.hinix)

A Interesting Word game. Hackathon Project. Inspired from the classic [Boggle game](https://en.wikipedia.org/wiki/Boggle)

**Won first prize in Hackathon** :tada: :fireworks: :champagne: 

## Screenshots

| ![](http://i.imgur.com/BwP0Q8K.png) | ![](http://i.imgur.com/6xZkGKU.png) | ![](http://i.imgur.com/FyOCQaH.png) |
|-------------------------------------|-------------------------------------|-------------------------------------|
| ![](http://i.imgur.com/4kNEvQg.png) | ![](http://i.imgur.com/OGntwc1.png) | ![](http://i.imgur.com/SN60kVY.png)|

## Rules of the Game: 
+ You have to make as many words as possible from a given grid of words.
+ You can move up, down, left, right, and diagonally to make a word.
+ Minimum word size is 4.
+ Target is to make every word possible from the grid.
+ You are given some initial points at the starting of each game and these points keep decreasing by the difference of the no. of words you made and total no. of words possible.
+ The game continues till you exhaust all the points.

## Concept Used:

+ Depth First Search and a custom optmized greedy algorithm for generating the boards.
+ Depth First Traversal for solving the board.

### Team Members (with contribution in Hackathon)

* [Avi Aryan](https://github.com/aviaryan) - Board generation and solution algorithm
* [Nilesh Chaturvedi](https://github.com/Nilesh4145) - UI (home activity)
* [Raju Koushik](https://github.com/RajuKoushik) - Algorithm and logic for Gameboard word selection
* [Saurabh Jain](https://github.com/saurabhjn76) - Gameplay UI + supporting logic
