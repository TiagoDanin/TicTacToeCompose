package com.tiagodanin.mobile.tictactoe

class BoxModel (
    var status: Status = Status.Empty,
    var indexColumn: Int = 0,
    var indexRow: Int = 0,
) {
    fun showText(): String {
        return when (status) {
            Status.Empty -> ""
            Status.PlayerX -> "X"
            Status.PlayerO -> "O"
        }
    }
}