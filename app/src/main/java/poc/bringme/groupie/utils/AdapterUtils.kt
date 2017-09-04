package poc.bringme.groupie.utils

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import poc.bringme.groupie.data.Data

class AdapterUtils(
        private val adapter: GroupAdapter<ViewHolder>,
        private val data: Data) {

    fun addFirstPageOfMessages() {
        adapter.add(data.messageGroup)
        adapter.add(data.messageSection)
        adapter.add(data.loader)
    }

    fun clear() {
        adapter.clear()
    }
}
