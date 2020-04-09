package bitcoin.difficulty.finder.playground

import bitcoin.difficulty.finder.BlockHeader
import bitcoin.difficulty.finder.Finder
import bitcoin.difficulty.finder.constants.TestConstants
import bitcoin.difficulty.finder.extensions.swipeEndianity
import bitcoin.difficulty.finder.interfaces.Hashable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

suspend fun main (arguments : Array<String>) {

    // 0. Get an instance of the testConstants
    val testObject = TestConstants

    // 1. Get an instance of a BlockHeader
    val blockHeader: BlockHeader = BlockHeader.newInstance(
        testObject.VERSION,
        testObject.PREVIOUS_BLOCK_HASH,
        testObject.MERKLE_ROOT_HASH,
        testObject.TIMESTAMP,
        testObject.DIFFICULTY_BITS,
        testObject.START_NONCE
    )

    println("The starting block header:\n")
    println(blockHeader.toString() + "\n")

    println("The first 5 hashes:\n")
    // 1.1 Printing the first five hashes
    repeat(5) {
        blockHeader.hash(Hashable.Algorithm.SHA256)
        println("Nonce " + blockHeader.nonce.toString() + "\t:\t" + blockHeader.hash)
        blockHeader.nonce++
    }

    // 1.2 Resetting the nonce
    blockHeader.nonce = testObject.START_NONCE

    // 2. Get a finder object
    val finder = Finder(blockHeader)

    // 3. Use the new finder object to try and find the hash
    // Average time to find this particular hash : 5-6 seconds (Intel i7 3.2GHz with 8 cores)
    val startTime = System.currentTimeMillis()
    GlobalScope.launch { finder.find() }.join()
    val endTime = System.currentTimeMillis()

    println("\nFound nonce and hash of the block: (found in ${(endTime - startTime) / 1000} seconds)\n")
    println("Nonce 1: " + finder.foundNonce)
    println("Block hash: " + finder.foundHash.swipeEndianity())

    println("\nThe next part is very CPU-intensive and probably won't yield a valid hash and nonce")
    println("Estimated runtime on an Intel i7 3.2GHz with 8 cores : 5-7 minutes")
    println("Do you still want to run it? [y/n]")

    var response = readLine()
    while (response != "y" && response != "n") {
        println("Unknown answer detected. Please input one of the following [y/n]")
        response = readLine()
    }

    if (response == "y") {
        // 4. Instantiating a new block header with the same data but with a changed nonce; also printing it
        val blockHeader2: BlockHeader = blockHeader.copy()
            .apply { nonce = Random.nextLong(finder.foundNonce, finder.foundNonce + TestConstants.MAX_NONCE_STEP) }
        println("Trying to find another lucky nonce and hash. Starting with nonce ${blockHeader2.nonce}")

        // 5. We try to find another good hash and nonce with this header
        finder.setBlockHeader(blockHeader2)
        GlobalScope.launch { finder.find() }.join()

        println("Nonce 2 start: " + blockHeader2.nonce)
        println("Nonce 2: " + finder.foundNonce)
        if (finder.found()) println("Block hash: " + finder.foundHash.swipeEndianity()) else println("Hash not found")
    } else {
        println("Program terminated")
    }

}
