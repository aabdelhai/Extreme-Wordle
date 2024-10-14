# EXTREME WORDLE

Zack Lea, Ahmed Abdelhai, Sam Price

A game where you have 5 guesses to guess a random 5 letter word with the features of a timer, points, highscore, personal record time to guess a word, and the player recieves iterative feedback on each letter of their guess.
To run this game it only requires that you have Java 17 installed. The main class to run this program is inside the Game class.
We'd like to acknowledge New York Times' Wordle game as our inspiration along with Charles Reid for their list of 5 letter words we used for this program. We have put the link to this below.

https://github.com/charlesreid1/five-letter-words/blob/master/sgb-words.txt

Our main fundemental design limitation is that we only have around 5,200 words so we do not have access to every 5 letter word possible so if a user inputs a valid word not in our dictionary, our program will tell the user it does not exist. Another design limitation is that if it takes a user longer then around 11 days to guess a word the personal record time will be set to 264 hours. 

We would like to acknowledge the societal impact of our program. Our program has several limitations that might lead to societal impacts/ lack of accessibility to certain groups.
1. Language: Our game only includes English words. In the future, an extension of this program could be adding non English language libraries, for but for right now, this game might not be accessible to non English speaking users.
2. Color Blindness: Our game requires the user to be able to differentiate between red, yellow, and green, and lack of ability to do so may impact the user's imperience. In the future, this program could include a color-blind version that the user could select. 
3. Vision: Playing our game requires the user be able to see clearly since our game does not have the ability to translate on screen letters/ words to an audio medium. 
4. Literacy: This game might be difficult to access for people who have limited English procificiency.
5. Keyboard: This game requires the physical ability to operate a standard keyboard to input letter/word choices.
