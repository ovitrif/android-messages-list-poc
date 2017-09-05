package poc.bringme.groupie.utils

import com.xwray.groupie.Section
import poc.bringme.groupie.core.MyExpandableGroup
import poc.bringme.groupie.item.MessageGroupHeader
import poc.bringme.groupie.item.MessageItem

private var stampIndex = 1
private fun timestamp() = "${stampIndex++} days ago"

fun dummyMessage() = MessageItem(createRandomTitle(), timestamp(), createRandomText())

fun dummyMessageGroupHeader() = MessageGroupHeader(createRandomTitle(), timestamp(), createRandomText())

fun dummyMessageSection() = Section(MessageItem(createRandomTitle(), timestamp(), createRandomText()))

fun dummyMessageGroup() = MyExpandableGroup(dummyMessageGroupHeader()).apply {
    add(dummyMessage())
    add(dummyMessage())
}

fun createRandomMessages(count: Int) = Section().apply {
    for (i in 1..count) {
        add(dummyMessage())
    }
}

