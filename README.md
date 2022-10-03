
# Connect 4

Android app of the classic "4-in-a-row" game.

Developed for the [monthly app challenge](https://github.com/mouredev/Monthly-App-Challenge-2022) proposed by [MoureDev](https://github.com/mouredev).

## Installation
You can download the app using the provided [APK file](https://github.com/miguelcanton97/Connect4/blob/master/app/release/Connect4.apk).

## Screenshots
Home | Home (Dark mode)
--- | ---
<img src="https://github.com/miguelcanton97/Connect4/blob/master/Conecta4%20Screenshots/home%20(eng).png" width="294" height="600"> | <img src="https://github.com/miguelcanton97/Connect4/blob/master/Conecta4%20Screenshots/home%20dark%20(eng).png" width="294" height="600">

Game | Game (Dark mode)
--- | ---
<img src="https://github.com/miguelcanton97/Connect4/blob/master/Conecta4%20Screenshots/game%20(eng).png" width="294" height="600"> | <img src="https://github.com/miguelcanton97/Connect4/blob/master/Conecta4%20Screenshots/game%20dark%20(eng).png" width="294" height="600">

## Requirements
* 7x6 board (7 on the "x" axis and 6 on the "y").
* Red and Yellow Chips. The first game is always started by Red, Yellow second, Red third...). (Chosen blue instead of yellow).
* There is no need to implement a functionality that allows you to play against the App. It is assumed that two real people will play alternately.
* Selecting a column places the chip at the bottom.
* Save the number of games won by each color while the App is not finished.
* Two buttons to restart the game in progress and to reset the wins/losses counter.
* You can add all the extra features you consider.

## Tech Stack
* Android Studio.
* Kotlin.
* MVVM pattern with StateFlow.
* Clean architecture.
* Dependency Injection with Dagger Hilt.
* Repository pattern.
* SharedPreferences.

## Features
* Light/dark mode: selected with an animated toggle on settings screen. Downloaded from LottieFiles. Available [here](https://lottiefiles.com/47047-dark-mode-button).
* Multi-language: English and Spanish. Language is selected using Device System Preferences.

