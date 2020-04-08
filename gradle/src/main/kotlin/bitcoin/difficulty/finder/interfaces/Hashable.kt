package bitcoin.difficulty.finder.interfaces

/**
 * The [Hashable] interface must be implemented by all elements that need to generate a hash value
 */
interface Hashable {
    /**
     * @param [hashAlgorithm] Is one of the instances of [Hashable.Algorithm]
     */
    fun hash (hashAlgorithm : Algorithm) : String

    enum class Algorithm(val algorithm : String) {
        SHA256("SHA-256"),
        SHA1("SHA-1"),
        SHA512("SHA-512"),
        MD("MD")
    }


}