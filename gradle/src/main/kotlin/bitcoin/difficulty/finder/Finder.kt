package bitcoin.difficulty.finder

import bitcoin.difficulty.finder.constants.TestConstants
import bitcoin.difficulty.finder.extensions.swipeEndianity
import bitcoin.difficulty.finder.extensions.toDouble
import bitcoin.difficulty.finder.interfaces.Hashable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.pow

/**
 * @author Alexandru Balan -- last modified: 2020/04/08
 * @since 2020.alpha.2
 *
 * @param blockHeader is the block header of the block we want to find the hash for
 */
class Finder (private var blockHeader: BlockHeader) {
    private var found : Boolean = false
    // We first instantiate a collection of jobs
    private val jobs : MutableList<Job> = MutableList(NUMBER_OF_THREADS) { Job()}
    private var difficulty : Double
    private var difficultyString : String = blockHeader.difficultyBits.swipeEndianity()
    private val mutex : Mutex = Mutex()
    var foundNonce : Long = -1
    var foundHash : String = "No Hash Found"

    /**
     * In the constructor we compute the target difficulty of the hash
     */
    init {
        val exp = difficultyString.slice(0 until 2).toLong(16)
        val coef = difficultyString.slice(2 until difficultyString.length).toLong(16)

        difficulty = coef * 2.0.pow((8*(exp - 3)).toDouble())
    }

    /**
     * This is the function that is tasked with launching and stopping the jobs that search for the hash
     *
     * @return null : if the hash could not be found
     * @return nonce : [Long] : The nonce that gives the found hash
     */
    suspend fun find () : Long? {
        // Launching the coroutines
        for (i in 0 until NUMBER_OF_THREADS) {
            jobs[i] = GlobalScope.launch { search(blockHeader.nonce + i * STEP, blockHeader.nonce + (i+1) * STEP) }
        }

        while (!found) {
            // We check each job to see if there is still one active
            if (jobs.all { it.isCompleted or it.isCancelled }) {
                break
            }
        }

        if (!found) return null

        // If we reach this part it means we found a hash
        return foundNonce
    }

    private suspend fun search (lowerBound : Long, upperBound : Long)  {
        val blockHeaderCopy = blockHeader.copy()

        for (i in lowerBound .. upperBound) {
            // We increment the nonce and hash the block header
            blockHeaderCopy.nonce = i
            blockHeaderCopy.hash(Hashable.Algorithm.SHA256)

            // We check to see if we found the right hash
            if (blockHeaderCopy.hash.swipeEndianity().toDouble(16) <= difficulty) { // We found the right hash

                mutex.withLock {
                    found = true
                    foundNonce = i
                    foundHash = blockHeaderCopy.hash
                    jobs.forEach {
                        it.cancelAndJoin()
                    }
                }
            }
        }
    }

    /**
     * Setting a new block header will automatically reset your [Finder] object to be ready for a new finding round
     */
    fun setBlockHeader (blockHeader: BlockHeader) {
        this.blockHeader = blockHeader
        this.reset()
    }

    fun found() : Boolean {
        return found
    }

    private fun reset() {
        difficultyString = blockHeader.difficultyBits.swipeEndianity()

        val exp = difficultyString.slice(0 until 2).toLong(16)
        val coef = difficultyString.slice(2 until difficultyString.length).toLong(16)

        difficulty = coef * 2.0.pow((8*(exp - 3)).toDouble())

        foundNonce = -1
        foundHash = "No Hash Found"
        found = false
    }

    companion object {
        private const val NUMBER_OF_THREADS : Int = 10
        private const val STEP : Long = TestConstants.MAX_NONCE_STEP / NUMBER_OF_THREADS
    }

}