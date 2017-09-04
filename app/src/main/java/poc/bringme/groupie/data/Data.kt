package poc.bringme.groupie.data

import com.xwray.groupie.Section
import poc.bringme.groupie.item.LoaderItem
import poc.bringme.groupie.utils.dummyMessage
import poc.bringme.groupie.utils.dummyMessageGroup

class Data {

    val messageSection = createRandomMessages(3)
    val messageGroup = dummyMessageGroup()
    val loader = LoaderItem()

    fun createRandomMessages(count:Int) = Section().apply {
        for (i in 1..count) {
            add(dummyMessage())
        }
    }
}
