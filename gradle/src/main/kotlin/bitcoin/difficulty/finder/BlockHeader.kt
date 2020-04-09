package bitcoin.difficulty.finder

import bitcoin.difficulty.finder.extensions.hexStringToByteArray
import bitcoin.difficulty.finder.extensions.swipeEndianity
import bitcoin.difficulty.finder.extensions.toHexString
import bitcoin.difficulty.finder.interfaces.Hashable
import java.security.MessageDigest

/**
 * @author Alexandru Balan -- 2020/04/06
 * @since 2020.alpha.1
 *
 * @constructor User the newInstance method to get a new instance of the BlockHeader as it allows you pass arguments
 * in normal fashion using Ints and Longs etc. and it will automatically translate them to their hex-value in little-endian format
 *
 * @param arguments[version] : [String] A little-endian hex value of the version
 * @param arguments[previousBlockHash] : [String] A string representing the big-endian sha256 hash value of a valid bitcoin block
 * @param arguments[merkleRootHash] : [String] A string representing the big-endian sha256 hash value of the MerkleRoot
 * @param arguments[timestamp] : [String] A little-endian hex value of the timestamp
 * @param arguments[difficultyBits] : [String] A little-endian hex value of the difficulty bits
 * @param arguments[nonce] : [String] An integer value representing the nonce
 */
class BlockHeader private constructor(private val arguments : Map<String, Any>) : Hashable {

    // Attributes of the class
    private val version: String = arguments["version"] as String
    private val previousBlockHash : String = arguments["previousBlockHash"] as String
    private val merkleRootHash : String = arguments["merkleRootHash"] as String
    private val timestamp : String = arguments["timeStamp"] as String
    val difficultyBits : String = arguments["difficultyBits"] as String
    private var nonceHex : String = arguments["nonce"] as String
    var nonce : Long = arguments["nonceInteger"] as Long
        set(value) {
            field = value
            nonceHex = nonce.toString(16).swipeEndianity()
        }
    var hash : String = "NOT COMPUTED"

    override fun toString (): String {
        val separator = "\t:\t"
        val hexPrefix = "0x"
        var descriptor = ""

        descriptor += "Version$separator$hexPrefix${version.swipeEndianity()} (${version.swipeEndianity()
            .toLong(16)})\n"
        descriptor += "Previous Block Hash$separator${previousBlockHash.swipeEndianity()}\n"
        descriptor += "Merkle Root Hash$separator${merkleRootHash.swipeEndianity()}\n"
        descriptor += "Timestamp$separator$hexPrefix${timestamp.swipeEndianity()} (${timestamp.swipeEndianity()
            .toLong(16)})\n"
        descriptor += "Difficulty$separator$hexPrefix${difficultyBits.swipeEndianity()} (${difficultyBits.swipeEndianity()
            .toLong(16)})\n"
        descriptor += "Nonce$separator$hexPrefix${nonceHex.swipeEndianity()} ($nonce)\n"
        descriptor += if (hash == "NOT COMPUTED") "Block Hash$separator$hash" else "Block Hash$separator${hash.swipeEndianity()}"

        return descriptor
    }

    fun copy() : BlockHeader {
        return newInstance(
            version.swipeEndianity().toInt(16),
            previousBlockHash.swipeEndianity(),
            merkleRootHash.swipeEndianity(),
            timestamp.swipeEndianity().toInt(16),
            difficultyBits.swipeEndianity().toInt(16),
            nonce
        )
    }

    /**
     * Gets a concatenated version of the attributes in little-endian format
     */
    private fun getContent () : String {
        return version+previousBlockHash+merkleRootHash+timestamp+difficultyBits+nonceHex
    }

    override fun hash(hashAlgorithm: Hashable.Algorithm): String {
        val messageDigest : MessageDigest = MessageDigest.getInstance(hashAlgorithm.algorithm)

        val bytes : ByteArray = messageDigest.digest(this.getContent().hexStringToByteArray())
        messageDigest.reset()
        val finalHashBytes : ByteArray = messageDigest.digest(bytes)

        hash = finalHashBytes.toHexString()

        return hash
    }

    companion object {
        // Get a new block header instance
        fun newInstance (version: Int, previousBlockHash: String, merkleRootHash: String,
            timestamp: Int, difficultyBits: Int, nonce: Long) : BlockHeader {

            val arguments : Map <String, Any> = mapOf(
                "version" to version.toString(16).swipeEndianity(),
                "previousBlockHash" to previousBlockHash.swipeEndianity(),
                "merkleRootHash" to merkleRootHash.swipeEndianity(),
                "timeStamp" to timestamp.toString(16).swipeEndianity(),
                "difficultyBits" to difficultyBits.toString(16).swipeEndianity(),
                "nonce" to nonce.toString(16).swipeEndianity(),
                "nonceInteger" to nonce
            )

            return BlockHeader(arguments)
        }
    }
}