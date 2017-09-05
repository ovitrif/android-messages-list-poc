package poc.bringme.groupie.core

import com.xwray.groupie.Group

interface MyExpandableItem : Group {
    fun setExpandableGroup(group: MyExpandableGroup)
}
