package poc.bringme.groupie.core

import com.xwray.groupie.Group
import com.xwray.groupie.NestedGroup
import java.util.*

class MyExpandableGroup(private val parent: MyExpandableItem) : NestedGroup() {

    private var isExpanded = false
    private val children = ArrayList<Group>()

    init {
        parent.setExpandableGroup(this)
    }

    override fun add(group: Group) {
        super.add(group)
        if (isExpanded) {
            children.add(0, group)
            notifyItemRangeInserted(1, group.itemCount)
        } else {
            children.add(0, group)
        }
    }

    fun isExpanded(): Boolean {
        return isExpanded
    }

    override fun getGroup(position: Int): Group {
        return if (position == 0) {
            parent
        } else {
            children[position - 1]
        }
    }

    override fun getPosition(group: Group): Int {
        return if (group === parent) {
            0
        } else {
            1 + children.indexOf(group)
        }
    }

    override fun getGroupCount(): Int {
        return 1 + if (isExpanded) children.size else 0
    }

    fun onExpand() {
        val oldSize = itemCount
        isExpanded = !isExpanded
        val newSize = itemCount
        if (oldSize > newSize) {
            notifyItemRangeRemoved(newSize, oldSize - newSize)
        } else {
            notifyItemRangeInserted(oldSize, newSize - oldSize)
        }
    }

    private fun dispatchChildChanges(group: Group): Boolean {
        return isExpanded || group === parent
    }

    override fun onChanged(group: Group) {
        if (dispatchChildChanges(group)) {
            super.onChanged(group)
        }
    }

    override fun onItemInserted(group: Group, position: Int) {
        if (dispatchChildChanges(group)) {
            super.onItemInserted(group, position)
        }
    }

    override fun onItemChanged(group: Group, position: Int) {
        if (dispatchChildChanges(group)) {
            super.onItemChanged(group, position)
        }
    }

    override fun onItemChanged(group: Group, position: Int, payload: Any) {
        if (dispatchChildChanges(group)) {
            super.onItemChanged(group, position, payload)
        }
    }

    override fun onItemRemoved(group: Group, position: Int) {
        if (dispatchChildChanges(group)) {
            super.onItemRemoved(group, position)
        }
    }

    override fun onItemRangeChanged(group: Group, positionStart: Int, itemCount: Int) {
        if (dispatchChildChanges(group)) {
            super.onItemRangeChanged(group, positionStart, itemCount)
        }
    }

    override fun onItemRangeInserted(group: Group, positionStart: Int, itemCount: Int) {
        if (dispatchChildChanges(group)) {
            super.onItemRangeInserted(group, positionStart, itemCount)
        }
    }

    override fun onItemRangeRemoved(group: Group, positionStart: Int, itemCount: Int) {
        if (dispatchChildChanges(group)) {
            super.onItemRangeRemoved(group, positionStart, itemCount)
        }
    }

    override fun onItemMoved(group: Group, fromPosition: Int, toPosition: Int) {
        if (dispatchChildChanges(group)) {
            super.onItemMoved(group, fromPosition, toPosition)
        }
    }

    override fun remove(group: Group) {
        super.remove(group)
        val position = getItemCountBeforeGroup(group)
        children.remove(group)
        notifyItemRangeRemoved(position, group.itemCount)
    }
}
