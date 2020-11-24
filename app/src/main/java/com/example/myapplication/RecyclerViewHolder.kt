
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    val text: TextView = view.text
}