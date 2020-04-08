package bitcoin.difficulty.finder.playground

import bitcoin.difficulty.finder.BlockHeader
import bitcoin.difficulty.finder.Finder
import bitcoin.difficulty.finder.constants.TestConstants
import bitcoin.difficulty.finder.extensions.swipeEndianity
import kotlinx.coroutines.GlobalScope
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
    // Average time to find this particular hash : 5-6 seconds (Intel i7 3.2GHz with 8 cores)
    GlobalScope.launch { finder.find() }.join()

    println("Nonce 1: " + finder.foundNonce)
    println("Block hash: " + finder.foundHash.swipeEndianity())

    // The next part is very CPU-intensive and probably won't yield a valid hash and nonce
    // You must uncomment this yourself if you want to run it.
    // Estimated runtime on an Intel i7 3.2GHz with 8 cores : 5-7 minutes

    /*

    // 4. Instantiating a new block header with the same data but with a changed nonce; also printing it
    val blockHeader2 : BlockHeader = blockHeader.copy()
        .apply { nonce = Random.nextLong(finder.foundNonce, finder.foundNonce + TestConstants.MAX_NONCE_STEP) }

    // 5. We try to find another good hash and nonce with this header
    finder.setBlockHeader(blockHeader2)
    GlobalScope.launch { finder.find() }.join()

    println("Nonce 2 start: " + blockHeader2.nonce)
    println("Nonce 2: " + finder.foundNonce)
    if (finder.found()) println("Block hash: " + finder.foundHash.swipeEndianity()) else println("Hash not found")

     */
}
