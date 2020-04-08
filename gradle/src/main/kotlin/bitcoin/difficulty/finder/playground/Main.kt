package bitcoin.difficulty.finder.playground

import bitcoin.difficulty.finder.BlockHeader
import bitcoin.difficulty.finder.Finder
import bitcoin.difficulty.finder.constants.TestConstants
import bitcoin.difficulty.finder.extensions.swipeEndianity
import bitcoin.difficulty.finder.extensions.toDouble
import bitcoin.difficulty.finder.interfaces.Hashable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun main (arguments : Array<String>) {

    // 0. Get an instance of the testConstants
    val testObject = TestConstants

    // 1. Get an instance of a BlockHeader
    val blockHeader : BlockHeader = BlockHeader.newInstance(
        testObject.VERSION,
        testObject.PREVIOUS_BLOCK_HASH,
        testObject.MERKLE_ROOT_HASH,
        testObject.TIMESTAMP,
        testObject.DIFFICULTY_BITS,
        testObject.START_NONCE
    )

    // 2. Get a finder object
    val finder = Finder(blockHeader)

    // 3. Use the new finder object to try and find the hash
    GlobalScope.launch { finder.find() }.join()

    println(finder.foundNonce)
    println(finder.foundHash.swipeEndianity())

    /*println("0000000000000000000d7612d743325d8e47cb9e506d547694478f35f736188e".toDouble(16))
    println(finder.difficulty)

    println("0000000000000000000d7612d743325d8e47cb9e506d547694478f35f736188e".toDouble(16) <= finder.difficulty)*/
}
