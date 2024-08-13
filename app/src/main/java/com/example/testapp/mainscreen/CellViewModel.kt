package com.example.testapp.mainscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.R
import com.example.testapp.database.AppDatabase
import com.example.testapp.database.Cell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class CellViewModel(
    context: Context,
) : ViewModel() {
    private val _cellList = MutableLiveData<List<Cell>>()
    val cellList: LiveData<List<Cell>> get() = _cellList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _cellList.postValue(
                AppDatabase.getDatabase(context).cellDao().getAllCell()
            )
        }
    }

    fun createCell(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val newCell = generateRandomCell()
            AppDatabase.getDatabase(context).cellDao().insertCell(newCell)

            val updatedCellList = AppDatabase.getDatabase(context).cellDao().getAllCell()

            if (updatedCellList.size >= 3) {
                checkForSpecialConditions(context, updatedCellList)
            }

            _cellList.postValue(AppDatabase.getDatabase(context).cellDao().getAllCell())
        }
    }

    private fun generateRandomCell(): Cell {
        return if (Random.nextBoolean()) {
            Cell(
                stateCell = "Живая",
                emoji = "\uD83D\uDCA5",
                description = "и шевелится!",
                background = R.drawable.alive_cell
            )
        } else {
            Cell(
                stateCell = "Мертвая",
                emoji = "\uD83D\uDC80",
                description = "или прикидывается",
                background = R.drawable.death_cell,
                alreadyTaken = false
            )
        }
    }

    private fun checkForSpecialConditions(context: Context, cellList: List<Cell>) {
        val lastThreeCells = cellList.takeLast(3)

        when {
            lastThreeCells.all { it.stateCell == "Живая" } -> {
                AppDatabase.getDatabase(context).cellDao().insertCell(
                    Cell(
                        stateCell = "Жизнь",
                        description = "Ку-ку!",
                        emoji = "\uD83D\uDC23",
                        background = R.drawable.life
                    )
                )
            }

            lastThreeCells.all { it.stateCell == "Мертвая" && it.alreadyTaken == false } -> {
                for (cell in cellList.reversed()) {
                    if (cell.stateCell == "Жизнь") {
                        AppDatabase.getDatabase(context).cellDao().updateCell(
                            cell.copy(
                                stateCell = "Смерть",
                                description = "Бай-бай!",
                                emoji = "\uD83E\uDEA6",
                                background = R.drawable.death
                            )
                        )
                        lastThreeCells.forEach {
                            AppDatabase.getDatabase(context).cellDao()
                                .updateCell(it.copy(alreadyTaken = true))
                        }
                        break
                    }
                }
            }
        }
    }
}
