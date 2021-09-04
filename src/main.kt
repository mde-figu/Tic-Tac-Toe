//
//  Tic Tac Toe
//  main.kt
//
//  Created by Harrison Kong @ Coursera
//  for the course "Start Programming with Kotlin (Part I)
//

// Task 6 Step 1: declare a 3 x 3 array with inital values of "_" in all elements
// put your answer here
var gameboard = arrayOf(arrayOf("_","_","_"),arrayOf("_","_","_"),arrayOf("_","_","_"))

fun main(args: Array<String>) {

    // Task 8 Step 1: declare a variable called playAgain to check to see if the user wants to play again
    // this has to be nullable
    // put your answer here
    var playAgain : String? = "n"

    println("Hello my name is Albert!")

    // Task 8 Step 2: use a flow control structure here, we need to execute this block at least once
    //          which one is best? (leave the test condition blank for now)

    do {

        // Task 5 Step 2: call the resetGameBoard function you just created
        resetGameBoard()

        println("Would you like to go first? (Y/N)")
        var goFirst = readLine()

        if (goFirst != "Y" && goFirst != "y") {
            println("Okay I will go first.")
            computerMakesAMove()
        }

        printBoard()
        println("I have circles. You have noughts.")

        // Task 3 Step 1: declare a variable called "currentPlayer" with an initial value of "X"
        //          do you need to specify a type in this case?

        var currentPlayer = "X"

        // Task 4 Step 3: declare a variable called "winner"
        var winner: String? = null
        //         it is a nullable string and it needs to be initialized as null
        //          do you need to specify a type in this case?

        // put your answer here

        // Task 7 Step 0.3 comment these 2 lines out
//    computerMakesAMove()
//    userMakesAMove()

        // Task 7 Step 1: write a while... loop that keeps executing as long as the winner is null
        // Task 7 Step 2: write an if ... else statement inside the while loop
        //                  if the currentPlayer is "O", it is the computer's turn, call computerMakesAMove()
        //                      and set the currentPlayer to "X"
        //                  otherwise, it is the user's turn, call userMakesA Move
        //                      and set the currentPlayer to "O"
        // Task 7 Step 3: move the printBoard() and winner check statement inside the while loop

        while (winner == null) {
            if (currentPlayer == "0") {
                computerMakesAMove()
                currentPlayer = "X"
            } else {
                userMakesAMove()
                currentPlayer = "0"
            }
            // Task 6 Step 5: call the printBoard() function to show the current game board to the user
            // put your answer here
            printBoard()
            // Task 6 Step 6: call the checkWinner() function to see if anyone wins, save the result var "winner"
            // put your answer here
            winner = checkWinner()
        }


        // Task 7 Step 4: call the announceWinner function to let the user know who won
        announceWinner(winner)

        // Task 8 Step 3: uncomment these 2 lines to ask the user if another game should be played
        println()
        println("Play another game? (Y/N)?")
        // Task 8 Step 4: call readLine() and store the result in the variable to declared above
        playAgain = readLine() ?: "N"
        // Task 8 Step 5: what if the result is null?
        //  if(playAgain == null)
        //     playAgain = "N"

        // Task 8 Step 2:
    } while(playAgain == "y" || playAgain == "Y" )

    // Task 8 Step 6: what is the test condition that decides if the loop will have another round?
}

fun announceWinner(winner: String) {

    // this function simply announces who the winner is
    // if the argument winner is "X", the computer won
    // if the argument winner is "O", the user won
    // if the argument winner is "D", it is a draw


    when(winner){
        "X" -> println("*** You Won ***")
        "0" -> println("*** I won ***")
        "D" -> println("*** It's a draw ***")
    }

    println("****** GAME OVER ******")
}

fun checkWinner(): String? {

    // this function checks the game board array to see if there are any 3 X's or O's in a line
    // returns "X" if there are 3 X's in a line
    // returns "O" if there are 3 O's in a line
    // returns "D" if all positions are taken (not "_") and there are no 3 X's or O's in a line
    // returns null if there are still empty positions and there are no 3 X's or O's in a line

    // check all 3 rows to see if they are all them same
    // we only need to make sure one of them is not empty
    if (ownerOf("A1") == ownerOf("A2") && ownerOf("A2") == ownerOf("A3") && isTaken("A1")) {
        return ownerOf("A1")
    }
    if (ownerOf("B1") == ownerOf("B2") && ownerOf("B2") == ownerOf("B3") && isTaken("B1")) {
        return ownerOf("B1")
    }
    if (ownerOf("C1") == ownerOf("C2") && ownerOf("C2") == ownerOf("C3") && isTaken("C1")) {
        return ownerOf("C1")
    }

    // check all 3 column to see if they are all them same
    // we only need to make sure one of them is not empty
    if (ownerOf("A1") == ownerOf("B1") && ownerOf("B1") == ownerOf("C1") && isTaken("C1")) {
        return ownerOf("A1")
    }
    if (ownerOf("A2") == ownerOf("B2") && ownerOf("B2") == ownerOf("C2") && isTaken("C2")) {
        return ownerOf("A2")
    }
    if (ownerOf("A3") == ownerOf("B3") && ownerOf("B3") == ownerOf("C3") && isTaken("C3")) {
        return ownerOf("A3")
    }

    // check 2 diagonals
    // we only need to make sure one of them is not empty
    if (ownerOf("A1") == ownerOf("B2") && ownerOf("B2") == ownerOf("C3") && isTaken("C3")) {
        return ownerOf("A1")
    }
    if (ownerOf("A3") == ownerOf("B2") && ownerOf("B2") == ownerOf("C1") && isTaken("C1")) {
        return ownerOf("A3")
    }

    // we need to check to see if it is a draw (all positions full)

    for (r in 'A'..'C') {
        for (c in '1'..'3') {

            val testPosition = r.toString() + c.toString()
            if (!isTaken(testPosition)) {
                return null      // found at least one empty cell
            }
        }
    }

    return "D"    // it's a draw
}

fun computerMakesAMove() {

    // this function "thinks" of the best position for the computer to put down an O

    println("My turn. Let me think...")

    // see if we need to block a win, if we did, we are done.
    if (needToBlock()) {
        return
    }

    // is the center taken? If not, take it

    if (!isTaken("B2")) {
        recordMove("O", "B2")
        return
    }

    // if we own the center, check surrounding for a possible winning move

    if (ownerOf("B2") == "O") {

        if (ownerOf("A1") == "O" && !isTaken("C3")) {
            recordMove("O", "C3")
            return
        }

        if (ownerOf("A2") == "O" && !isTaken("C2")) {
            recordMove("O", "C2")
            return
        }

        if (ownerOf("A3") == "O" && !isTaken("C1")) {
            recordMove("O", "C1")
            return
        }

        if (ownerOf("B1") == "O" && !isTaken("B3")) {
            recordMove("O", "B3")
            return
        }

        if (ownerOf("B3") == "O" && !isTaken("B1")) {
            recordMove("O", "B1")
            return
        }

        if (ownerOf("C1") == "O" && !isTaken("A3")) {
            recordMove("O", "A3")
            return
        }

        if (ownerOf("C2") == "O" && !isTaken("A2")) {
            recordMove("O", "A2")
            return
        }

        if (ownerOf("C3") == "O" && !isTaken("A1")) {
            recordMove("O", "A1")
            return
        }
    }

    // if we don't own the center or no diagonal move, the only ways to win are the four edges

    // top row
    if (ownerOf("A1") == "O" && ownerOf("A2") == "O" && !isTaken("A3")) {
        recordMove("O", "A3")
        return
    }

    if (ownerOf("A1") == "O" && ownerOf("A3") == "O" && !isTaken("A2")) {
        recordMove("O", "A2")
        return
    }

    if (ownerOf("A2") == "O" && ownerOf("A3") == "O" && !isTaken("A1")) {
        recordMove("O", "A1")
        return
    }

    // left column
    if (ownerOf("A1") == "O" && ownerOf("B1") == "O" && !isTaken("C1")) {
        recordMove("O", "C1")
        return
    }

    if (ownerOf("A1") == "O" && ownerOf("C1") == "O" && !isTaken("B1")) {
        recordMove("O", "B1")
        return
    }

    if (ownerOf("B1") == "O" && ownerOf("C1") == "O" && !isTaken("A1")) {
        recordMove("O", "A1")
        return
    }

    // bottom row
    if (ownerOf("C1") == "O" && ownerOf("C2") == "O" && !isTaken("C3")) {
        recordMove("O", "C3")
        return
    }

    if (ownerOf("C1") == "O" && ownerOf("C3") == "O" && !isTaken("C2")) {
        recordMove("O", "C2")
        return
    }

    if (ownerOf("C2") == "O" && ownerOf("C3") == "O" && !isTaken("C1")) {
        recordMove("O", "C1")
        return
    }

    // right column
    if (ownerOf("A3") == "O" && ownerOf("B3") == "O" && !isTaken("C3")) {
        recordMove("O", "C3")
        return
    }

    if (ownerOf("A3") == "O" && ownerOf("C3") == "O" && !isTaken("B3")) {
        recordMove("O", "B3")
        return
    }

    if (ownerOf("B3") == "O" && ownerOf("C3") == "O" && !isTaken("A3")) {
        recordMove("O", "A3")
        return
    }

    // No winning move, try to put 2 together on lines that has a chance to win, starting with center

    if (ownerOf("B2") == "O") {

        if (!isTaken("A1") && !isTaken("C3")) {
            recordMove("O", "A1")
            return
        }

        if (!isTaken("A2") && !isTaken("C2")) {
            recordMove("O", "A2")
            return
        }

        if (!isTaken("A3") && !isTaken("C1")) {
            recordMove("O", "A3")
            return
        }

        if (!isTaken("B1") && !isTaken("B3")) {
            recordMove("O", "B1")
            return
        }
    }
    else {

        // try to find a line with just one circle (no need to check the middle row and column)

        // try to find an empty line (no need to check the middle row and column)

        // top row

        if (!isTaken("A1") && !isTaken("A2") && !isTaken("A3")) {
            recordMove("O", "A1")
            return
        }

        // bottom row
        if (!isTaken("C1") && !isTaken("C2") && !isTaken("C3")) {
            recordMove("O", "C1")
            return
        }

        // left column
        if (!isTaken("A1") && !isTaken("B1") && !isTaken("C1")) {
            recordMove("O", "A1")
            return
        }

        // right column
        if (!isTaken("A3") && !isTaken("B3") && !isTaken("C3")) {
            recordMove("O", "A3")
            return
        }
    }

    // No best moves, just find an empty spot...

    for (r in 'A'..'C') {
        for (c in '1'..'3') {

            val testPosition = r.toString() + c.toString()
            if (!isTaken(testPosition)) {
                recordMove("O", testPosition)
                return
            }
        }
    }

}

fun isTaken(position : String) : Boolean {
    // Task 5 Step 3: Write a function isTaken that accepts a position as a String and returns a Boolean

    // check to see if the position argument, e.g. "A3", is not "_"

    // Task 5 Step 4: directly return the results of the function call ownerOf() with the position argument
    //                  check to see if it is "_", it should return true if it is not "_"
    return ownerOf(position) != "_"
}

fun isValidPosition(position: String): Boolean {

    // check to see if the position argument is valid
    // it must be 2 characters long
    // the first character must be either A, a, B, b, C or c
    // the second character must be either 1, 2 or 3
    // returns true if valid
    // returns false if invalid

    if (position.length != 2) { return false }

    val row = position.substring(0, 1).toLowerCase()
    val col = position.substring(1, 2).toLowerCase()

    return (row in "a".."c") && (col in "1".."3")
}

fun getColIndex(position: String) : Int? {

    // this function translates the column in a position (e.g. A3) into a zero-based array index (Int)
    // for this game this will be 0, 1 or 2
    // returns null if the position argument is not a valid game position

    if (!isValidPosition(position)) { return null }

    val col = position.substring(1, 2).toLowerCase()

    return col.toInt() - 1      // arrays are zero based!
}

fun getRowIndex(position: String) : Int? {

    // this function translate the row in a position (e.g. A3) into a zero-based array index (Int)
    // for this game, A will be 0, B will be 1, and C will be 2
    // returns null if the position argument is not a valid game position

    if (!isValidPosition(position)) { return null }

    val row = position.substring(0, 1).toLowerCase()

    when (row) {
        "a" -> return 0
        "b" -> return 1
        "c" -> return 2
        else -> return null
    }
}

fun needToBlock() : Boolean {

    // this function checks to see if there are any two X's on a winnable line and one empty spot
    // if so, the computer will block it with an O
    // these are priorities because if we don't block them the user can win in the next turn

    // top row

    if (!isTaken("A1") && ownerOf("A2") == "X" && ownerOf("A3") == "X") {
        recordMove("O", "A1")
        return true
    }

    if (!isTaken("A2") && ownerOf("A1") == "X" && ownerOf("A3") == "X") {
        recordMove("O", "A2")
        return true
    }

    if (!isTaken("A3") && ownerOf("A1") == "X" && ownerOf("A2") == "X") {
        recordMove("O", "A3")
        return true
    }

    // bottom row

    if (!isTaken("C1") && ownerOf("C2") == "X" && ownerOf("C3") == "X") {
        recordMove("O", "C1")
        return true
    }

    if (!isTaken("C2") && ownerOf("C1") == "X" && ownerOf("C3") == "X") {
        recordMove("O", "C2")
        return true
    }

    if (!isTaken("C3") && ownerOf("C1") == "X" && ownerOf("C2") == "X") {
        recordMove("O", "C3")
        return true
    }

    // left column

    if (!isTaken("A1") && ownerOf("B1") == "X" && ownerOf("C1") == "X") {
        recordMove("O", "A1")
        return true
    }

    if (!isTaken("B1") && ownerOf("A1") == "X" && ownerOf("C1") == "X") {
        recordMove("O", "B1")
        return true
    }

    if (!isTaken("C1") && ownerOf("A1") == "X" && ownerOf("B1") == "X") {
        recordMove("O", "C1")
        return true
    }

    // right column

    if (!isTaken("A3") && ownerOf("B3") == "X" && ownerOf("C3") == "X") {
        recordMove("O", "A3")
        return true
    }

    if (!isTaken("B3") && ownerOf("A3") == "X" && ownerOf("C3") == "X") {
        recordMove("O", "B3")
        return true
    }

    if (!isTaken("C3") && ownerOf("A3") == "X" && ownerOf("B3") == "X") {
        recordMove("O", "C3")
        return true
    }

    // if we own the center, we are done here, if not, continue

    if (ownerOf("B2") == "O") {
        return false
    }

    // middle column

    if (!isTaken("A2") && ownerOf("B2") == "X" && ownerOf("C2") == "X") {
        recordMove("O", "A2")
        return true
    }

    if (!isTaken("B2") && ownerOf("A2") == "X" && ownerOf("C2") == "X") {
        recordMove("O", "B2")
        return true
    }

    if (!isTaken("C2") && ownerOf("A2") == "X" && ownerOf("B2") == "X") {
        recordMove("O", "C2")
        return true
    }

    // middle row

    if (!isTaken("B1") && ownerOf("B2") == "X" && ownerOf("B3") == "X") {
        recordMove("O", "B1")
        return true
    }

    if (!isTaken("B2") && ownerOf("B1") == "X" && ownerOf("B3") == "X") {
        recordMove("O", "B2")
        return true
    }

    if (!isTaken("B3") && ownerOf("B1") == "X" && ownerOf("B2") == "X") {
        recordMove("O", "B3")
        return true
    }

    return false    // no block needed
}

fun ownerOf(position: String): String? {

    // this function returns the value of a game board position, X, O or _
    // returns null if the position argument is invalid

    val rowIndex = getRowIndex(position)
    val colIndex = getColIndex(position)

    if (rowIndex == null || colIndex == null) { return null }

    // Task 6 Step 2: return the element in the gameboard array with rowIndex and colIndex
    return gameboard[rowIndex][colIndex]
}

fun printBoard() {

    // this functions prints out the game board array in a user readable format

    // Task 6 Step 3: print the elements of the gameboard

//    println("printBoard() emulated output")
//    val line1 = "   1 2 3"
//    val line2 = "A  X O X"
//    val line3 = "B  _ X _"
//    val line4 = "C  O _ O"

    val line1 = "   1 2 3"
    val line2 = "A  " + gameboard[0][0] + " " + gameboard[0][1] + " " + gameboard[0][2]
    val line3 = "B  " + gameboard[1][0] + " " + gameboard[1][1] + " " + gameboard[1][2] // complete this line
    val line4 = "C  " + gameboard[2][0] + " " + gameboard[2][1] + " " + gameboard[2][2]// complete this line

    println()
    println(line1)
    println(line2)
    println(line3)
    println(line4)
    println()
}

fun recordMove(currentPlayer: String, position: String) {

    // this function marks the position in the game board with the currentPlayer (X or O)
    // it won't do anything if the position argument is invalid

    val rowIndex = getRowIndex(position)
    val colIndex = getColIndex(position)

    // Task 4 Step 1:  if you look at the logics of getRowIndex(position) and getColIndex(position)
    //            what inferred types are the variables rowIndex and colIndex?

    // Task 4 Step 2:  How do we make sure we exit this function if either is null?

    if (rowIndex == null || colIndex == null) { return }

    // Task 6 Step 4: set the element rowIndex and colIndex in the gameboard array to the currentPlayer
    //          string passed as the argument
    gameboard[rowIndex][colIndex] = currentPlayer
}

// Task 5 Step 1: declare an empty function resetGameBoard that does not take any argument or return anything
//                  don't worry about the body we will write it later
fun resetGameBoard() {
    var i = 0;
    var j = 0;
    // this function simply sets all the positions in the game board array to _

    // Task 6 Step 7: write two nested for loops and set all the gameboard elements to "_"

    for (i in 0..2) {
        for (j in 0..2) {
            gameboard[i][j] = "_"
        }
    }
}


fun userMakesAMove() {

    // this function prompts the user for a position
    // it loops if the user entered an invalid position or a position that is already taken

    // Task 3 Step 2: declare a variable called moveCompleted and initialize it to false
    //          do you need to specify its type in this case?

    var moveCompleted = false

    // Task 7 Step 0.1: we will do this one together
    //          lets's wrap this in a while loop so that it keeps running until the user enters a valid move

    while (!moveCompleted) {

        println("It's your turn. Please enter a position (e.g. A3)")

        // user enters a move
        var move = readLine()

        // is it valid?
        if (move != null && isValidPosition(move)) {

            // Task 5 Step 5: We need to check to see if the move is already taken
            if (isTaken(move)) {
                println("That position is already taken!!")
            }

            // Task 5 Step 6: call record move to make the position with an X
            else {
                // record move
                recordMove("X", move)

                // Task 7 Step 0.2
                moveCompleted = true    // this will end the while loop
            }

        } else {
            println("You entered an invalid position!!")
        }
    }

}