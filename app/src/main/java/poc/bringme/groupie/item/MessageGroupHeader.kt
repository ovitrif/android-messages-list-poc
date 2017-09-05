package poc.bringme.groupie.item

import android.view.View
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_message_group.view.*
import poc.bringme.groupie.R
import poc.bringme.groupie.core.MyExpandableGroup
import poc.bringme.groupie.core.MyExpandableItem

class MessageGroupHeader @JvmOverloads constructor(
        title: CharSequence? = "",
        timestamp: CharSequence? = "",
        body: CharSequence? = "") : MessageItem(title, timestamp, body), MyExpandableItem {

    private lateinit var expandableGroup: MyExpandableGroup

    override fun getLayout(): Int {
        return R.layout.item_message_group
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        super.bind(viewHolder, position)

        viewHolder.itemView.expandButton.setOnClickListener {
            expandableGroup.onExpand()
            viewHolder.itemView.expandButton.visibility = View.GONE
        }
    }

    override fun setExpandableGroup(group: MyExpandableGroup) {
        this.expandableGroup = group
    }

    fun getExpandableGroup(): MyExpandableGroup {
        return this.expandableGroup
    }
}
