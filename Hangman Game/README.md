# Hangman
You have to implement a console-based variation of the classical Hangman game. The computer will select a **Sentence** that the user can attempt to guess, letter by letter. Each time the user guesses a correct letter, the computer will fill it in the sentence at the correct positions. In case the letter does not appear, the computer will fill in a new letter in the word "hangman", starting from the empty string. The game ends when the user has guessed the sentence(user wins) or when the computer fills in the "hangman" word(user loses). 

## Requirements
- Use object oriented programming and layered architecture
- All modules with the exception of the UI will be covered with specifications and PyUnit test cases
- The program must protect itself against the user’s invalid input