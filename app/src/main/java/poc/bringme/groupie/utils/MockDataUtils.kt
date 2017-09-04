package poc.bringme.groupie.utils

import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Section
import poc.bringme.groupie.item.MessageGroupHeader
import poc.bringme.groupie.item.MessageItem

private val timestamp = "1 hour ago"

fun dummyMessage() = MessageItem(createRandomTitle(), timestamp, createRandomText())

fun dummyMessageGroupHeader() = MessageGroupHeader(createRandomTitle(), timestamp, createRandomText())

fun dummyMessageSection() = Section(MessageItem(createRandomTitle(), timestamp, createRandomText()))

fun dummyMessageGroup() = Section().apply {
    add(ExpandableGroup(dummyMessageGroupHeader()).apply {
        add(dummyMessage())
        add(dummyMessage())
    })
}
