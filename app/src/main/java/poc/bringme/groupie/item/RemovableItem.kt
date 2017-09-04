package poc.bringme.groupie.item

import android.support.v7.widget.helper.ItemTouchHelper
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

abstract class RemovableItem : Item<ViewHolder>() {

    override fun getSwipeDirs(): Int {
        return ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }
}
