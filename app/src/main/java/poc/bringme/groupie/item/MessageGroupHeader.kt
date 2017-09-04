package poc.bringme.groupie.item

import android.view.View
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_message_group.view.*
import poc.bringme.groupie.R

class MessageGroupHeader @JvmOverloads constructor(
        title: CharSequence? = "",
        timestamp: CharSequence? = "",
        body: CharSequence? = "") : MessageItem(title, timestamp, body), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun getLayout(): Int {
        return R.layout.item_message_group
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        super.bind(viewHolder, position)

        viewHolder.itemView.expandButton.setOnClickListener {
            expandableGroup.onToggleExpanded()
            viewHolder.itemView.expandButton.visibility = View.GONE
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}
