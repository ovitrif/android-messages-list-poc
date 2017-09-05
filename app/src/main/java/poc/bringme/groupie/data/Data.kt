package poc.bringme.groupie.data

import poc.bringme.groupie.item.LoaderItem
import poc.bringme.groupie.utils.createRandomMessages

class Data {

    val messageSection = createRandomMessages(4)
    val loader = LoaderItem()
}
