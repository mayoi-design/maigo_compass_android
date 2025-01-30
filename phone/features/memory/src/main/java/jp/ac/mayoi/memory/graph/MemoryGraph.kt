package jp.ac.mayoi.memory.graph

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.phone.model.PieChartModel

@Composable
fun MemoryGraph(
    model: PieChartModel,
) {
    var selectedEntry: Int? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    Box {
        MemoryPieChart(
            model = model,
            selectedEntry = selectedEntry,
            onEntrySelected = {
                Toast.makeText(
                    context,
                    "Entry $it has selected",
                    Toast.LENGTH_SHORT
                ).show()
                selectedEntry = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )
    }
}
