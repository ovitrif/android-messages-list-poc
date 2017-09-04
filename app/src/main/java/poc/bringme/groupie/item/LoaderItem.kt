package poc.bringme.groupie.item

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import poc.bringme.groupie.R

open class LoaderItem : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_loader
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }
}
