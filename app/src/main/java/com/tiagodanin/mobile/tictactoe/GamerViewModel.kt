package com.tiagodanin.mobile.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GamerViewModel : ViewModel() {
    private val gamerStatus: MutableLiveData<GamerModel> by lazy {
        MutableLiveData<GamerModel>()
    }

    private val boxes: MutableLiveData<MutableList<MutableList<BoxModel>>> by lazy {
        MutableLiveData<MutableList<MutableList<BoxModel>>>()
    }

    fun getGamerStatus(): LiveData<GamerModel> {
        return gamerStatus
    }

    fun getBoxes(): LiveData<MutableList<MutableList<BoxModel>>> {
        return boxes
    }

    fun loadGamer() {
        var indexColumn: Int = 0
        var indexRow: Int = 0

        boxes.value = MutableList(3) {
            indexRow = 0

            MutableList(3) {
                BoxModel(
                    indexColumn = indexColumn++ / 3,
                    indexRow = indexRow++
                )
            }
        }

        gamerStatus.value = GamerModel()
    }

    fun selectBox(box: BoxModel) {
        var currantPlayer: Status = gamerStatus.value?.currentPlayer!!
        var hasModification: Boolean = false

        var list: MutableList<MutableList<BoxModel>> = boxes.value?.map { columns ->
            var newColumns = columns.map { row ->
                if (box.indexColumn == row.indexColumn && box.indexRow == row.indexRow) {
                    if (row.status == Status.Empty) {
                        hasModification = true
                        row.status = currantPlayer
                    }
                }

                row
            }

            newColumns
        } as MutableList<MutableList<BoxModel>>

        if (hasModification && gamerStatus.value?.isGamerEnding == false) {
            gamerStatus.value?.currentPlayer = gamerStatus.value?.currentPlayer!!.next()
            boxes.value?.removeAll { true }
            boxes.value = list
        }

        checkEndingGamer()
    }

    private fun checkEndingGamer() {
        // Columns
        (0..2).forEach() { index ->
            if (
                boxes.value?.get(index = index)?.get(0)!!.status == boxes.value?.get(index = index)?.get(1)!!.status &&
                boxes.value?.get(index = index)?.get(1)!!.status == boxes.value?.get(index = index)?.get(2)!!.status &&
                boxes.value?.get(index = index)?.get(2)!!.status != Status.Empty
            ) {
                gamerStatus.value?.isGamerEnding = true
                gamerStatus.value?.winingPlayer = boxes.value?.get(index = index)?.get(2)!!.status
            }
        }

        // Row
        (0..2).forEach() { index ->
            if (
                boxes.value?.get(0)?.get(index = index)!!.status == boxes.value?.get(1)?.get(index = index)!!.status &&
                boxes.value?.get(1)?.get(index = index)!!.status == boxes.value?.get(2)?.get(index = index)!!.status &&
                boxes.value?.get(2)?.get(index = index)!!.status != Status.Empty
            ) {
                gamerStatus.value?.isGamerEnding = true
                gamerStatus.value?.winingPlayer = boxes.value?.get(2)?.get(index = index)!!.status
            }
        }

        // Diagonals
        if (
            boxes.value?.get(0)?.get(0)!!.status == boxes.value?.get(1)?.get(1)!!.status &&
            boxes.value?.get(1)?.get(1)!!.status == boxes.value?.get(2)?.get(2)!!.status &&
            boxes.value?.get(2)?.get(2)!!.status != Status.Empty
        ) {
            gamerStatus.value?.isGamerEnding = true
            gamerStatus.value?.winingPlayer = boxes.value?.get(1)?.get(1)!!.status
        }

        if (
            boxes.value?.get(0)?.get(2)!!.status == boxes.value?.get(1)?.get(1)!!.status &&
            boxes.value?.get(1)?.get(1)!!.status == boxes.value?.get(2)?.get(0)!!.status &&
            boxes.value?.get(2)?.get(0)!!.status != Status.Empty
        ) {
            gamerStatus.value?.isGamerEnding = true
            gamerStatus.value?.winingPlayer = boxes.value?.get(1)?.get(1)!!.status
        }
    }
}