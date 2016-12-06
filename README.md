# Hinix

[![Travis branch](https://img.shields.io/travis/aviaryan/Hinix/neo-reborn.svg?maxAge=2592000)]()
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/16ed87cf73834773abb8d331d0ac9a8f)](https://www.codacy.com/app/aviaryan/Hinix)

[![Get it on Google Play](https://play.google.com/intl/en_us/badges/images/badge_new.png)](https://play.google.com/store/apps/details?id=in.aviaryan.hinix)

A Interesting Word game. Hackathon Project. Inspired from the classic [Boggle game](https://en.wikipedia.org/wiki/Boggle)

**Won first prize in Hackathon** :tada: :fireworks: :champagne: 

## Screenshots

| ![](https://s16.postimg.org/fio96g6r9/device_2016_12_06_223106.png) | ![](https://s16.postimg.org/8cwi3zxo5/device_2016_12_06_223226.png) | ![](https://s16.postimg.org/yz8ysz1v9/device_2016_12_06_223158.png) |
|-------------------------------------|-------------------------------------|-------------------------------------|
| ![](https://s16.postimg.org/t3jmjbbxx/device_2016_12_06_223001.png) | ![](https://s16.postimg.org/kwn1dzwhh/device_2016_12_06_223031.png) | ![](https://s16.postimg.org/mn62fhe0l/device_2016_12_06_223048.png)|

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
