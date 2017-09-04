package poc.bringme.groupie.utils

import com.thedeanda.lorem.LoremIpsum

private val loremIpsum = LoremIpsum.getInstance()

fun createRandomText() = loremIpsum.getWords(10, 12).capitalize() + '.'
fun createRandomTitle() = loremIpsum.getTitle(3)
