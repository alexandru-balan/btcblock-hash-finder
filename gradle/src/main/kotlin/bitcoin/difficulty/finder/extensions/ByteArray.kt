package bitcoin.difficulty.finder.extensions

private const val HEX_CHARS = "0123456789abcdef"

/**
 * @author Alexandru Balan -- last modified: 2020/04/07
 * @since 2020.alpha.1
 *
 * @sample {val hexString : String = bytes.toHexString}
 *
 * @see [bitcoin.difficulty.finder.extensions] in the String.kt file for the inverse of this function
 *
 * This function extends the functionality of the [ByteArray] class by allowing you to transform an array of bytes into a
 * string
 */
fun ByteArray.toHexString() : String {
    val result : StringBuilder = StringBuilder(this.size * 2)
    this.forEach {
        val i = it.toInt()
        result.append(HEX_CHARS[i shr 4 and 0x0f])
        result.append(HEX_CHARS[i and 0x0f])
    }

    return result.toString()
}