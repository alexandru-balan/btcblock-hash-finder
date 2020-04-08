package bitcoin.difficulty.finder.extensions

import kotlin.math.pow

private const val HEX_CHARS = "0123456789abcdef"

/**
 * @author Alexandru Balan -- last modified: 2020/04/07
 * @since 2020.alpha.1
 *
 * @sample {val bytes : ByteArray = hexString.hexStringToByteArray}
 *
 * @see [bitcoin.difficulty.finder.extensions] in the ByteArray.kt file for the inverse of this function
 *
 * This function extends the functionality of the [String] class by allowing to transform a string representing a hexadecimal
 * number into an array of bytes
 */
fun String.hexStringToByteArray () : ByteArray {
    val str = this.decapitalize()

    val result = ByteArray(str.length / 2)

    for (i in str.indices step 2) {
        val firstIndex = HEX_CHARS.indexOf(str[i])
        val secondIndex = HEX_CHARS.indexOf(str[i+1])

        val byte = (firstIndex shl 4) or secondIndex
        result[i.shr(1)] = byte.toByte()
    }

    return result
}

/**
 * @author Alexandru Balan -- last modified: 2020/04/07
 * @since 2020.alpha.1
 *
 * The method allows you to swipe between little-endian notation and big-endian notation of a string
 */
fun String.swipeEndianity() : String {
    var result = ""
    for (i in this.indices step 2) {
        val str : String = this.slice(i until i+2)
        result += str.reversed()
    }

    return result.reversed()
}

/**
 * @author Alexandru Balan -- last modified: 2020/04/08
 * @since 2020.alpha.2
 *
 * This method can transform a string in hexadecimal notation into a [Double]
 *
 * @param [radix] in the future this method might support more than hex strings, right now just use 16
 *
 */
fun String.toDouble(radix : Int) : Double {
    val str = this.reversed()

    var result : Double = 0.0

    for (i in str.indices) {
        result += radix.toDouble().pow(i) * HEX_CHARS.indexOf(str[i])
    }

    return result
}