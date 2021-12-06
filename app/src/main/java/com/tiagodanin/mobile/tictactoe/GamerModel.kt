package com.tiagodanin.mobile.tictactoe

class GamerModel(
    var currentPlayer: Status = Status.PlayerX,
    var winingPlayer: Status = Status.Empty,
    var isGamerEnding: Boolean = false,
) {}