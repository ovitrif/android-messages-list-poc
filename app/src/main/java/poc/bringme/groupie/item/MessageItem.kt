package poc.bringme.groupie.item

import android.view.View
import com.xwray.groupie.GroupDataObserver
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_message_simple.view.*
import poc.bringme.groupie.ItemDecorations
import poc.bringme.groupie.R

open class MessageItem @JvmOverloads constructor(
        var title: CharSequence? = "",
        var timestamp: CharSequence? = "",
        var body: CharSequence? = "",
        var hasImage: Boolean = false) : RemovableItem() {

    init {
        extras.put(ItemDecorations.INSET_TYPE_KEY, ItemDecorations.INSET)
    }

    fun getParent(): GroupDataObserver? {
        return this.parentDataObserver
    }

    override fun getLayout(): Int {
        return R.layout.item_message_simple
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.title.text = title
        viewHolder.itemView.timestamp.text = timestamp
        viewHolder.itemView.body.text = body
        viewHolder.itemView.image.visibility = if (hasImage) View.VISIBLE else View.GONE
    }
}
