//
//  Tic Tac Toe
//  main.kt
//

var gameboard = arrayOf(arrayOf("_","_","_"),arrayOf("_","_","_"),arrayOf("_","_","_"))

fun main(args: Array<String>) {


    var playAgain : String? = "n"

    println("Hello my name is Computer!")



    do {
        resetGameBoard()

        println("Would you like to go first? (Y/N)")
        var goFirst = readLine()

        if (goFirst != "Y" && goFirst != "y") {
            println("Okay I will go first.")
            computerMakesAMove()
        }

        printBoard()
        println("I have circles. You have noughts.")



        var currentPlayer = "X"


        var winner: String? = null




        while (winner == null) {
            if (currentPlayer == "0") {
                computerMakesAMove()
                currentPlayer = "X"
            } else {
                userMakesAMove()
                currentPlayer = "0"
            }

            printBoard()

            winner = checkWinner()
        }



        announceWinner(winner)


        println()
        println("Play another game? (Y/N)?")

        playAgain = readLine() ?: "N"


        // Task 8 Step 2:
    } while(playAgain == "y" || playAgain == "Y" )

}

fun announceWinner(winner: String) {




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


    println("My turn. Let me think...")

    if (needToBlock()) {
        return
    }

    // is the center taken? If not, take it

    if (!isTaken("B2")) {
        recordMove("O", "B2")
        return
    }


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

    return ownerOf(position) != "_"
}

fun isValidPosition(position: String): Boolean {

    if (position.length != 2) { return false }

    val row = position.substring(0, 1).toLowerCase()
    val col = position.substring(1, 2).toLowerCase()

    return (row in "a".."c") && (col in "1".."3")
}

fun getColIndex(position: String) : Int? {


    if (!isValidPosition(position)) { return null }

    val col = position.substring(1, 2).toLowerCase()

    return col.toInt() - 1      // arrays are zero based!
}

fun getRowIndex(position: String) : Int? {


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



    if (ownerOf("B2") == "O") {
        return false
    }


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

    return false
}

fun ownerOf(position: String): String? {


    val rowIndex = getRowIndex(position)
    val colIndex = getColIndex(position)

    if (rowIndex == null || colIndex == null) { return null }

    return gameboard[rowIndex][colIndex]
}

fun printBoard() {


//    println("printBoard() emulated output")
//    val line1 = "   1 2 3"
//    val line2 = "A  X O X"
//    val line3 = "B  _ X _"
//    val line4 = "C  O _ O"

    val line1 = "   1 2 3"
    val line2 = "A  " + gameboard[0][0] + " " + gameboard[0][1] + " " + gameboard[0][2]
    val line3 = "B  " + gameboard[1][0] + " " + gameboard[1][1] + " " + gameboard[1][2]
    val line4 = "C  " + gameboard[2][0] + " " + gameboard[2][1] + " " + gameboard[2][2]

    println()
    println(line1)
    println(line2)
    println(line3)
    println(line4)
    println()
}

fun recordMove(currentPlayer: String, position: String) {


    val rowIndex = getRowIndex(position)
    val colIndex = getColIndex(position)


    if (rowIndex == null || colIndex == null) { return }

    gameboard[rowIndex][colIndex] = currentPlayer
}

fun resetGameBoard() {
    var i = 0;
    var j = 0;

    for (i in 0..2) {
        for (j in 0..2) {
            gameboard[i][j] = "_"
        }
    }
}


fun userMakesAMove() {

    var moveCompleted = false


    while (!moveCompleted) {

        println("It's your turn. Please enter a position (e.g. A3)")

        var move = readLine()

        if (move != null && isValidPosition(move)) {

            if (isTaken(move)) {
                println("That position is already taken!!")
            }

            else {
                recordMove("X", move)

                moveCompleted = true
            }

        } else {
            println("You entered an invalid position!!")
        }
    }

}