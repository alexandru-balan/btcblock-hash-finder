package bitcoin.difficulty.finder.constants

object TestConstants {
    // Block Header values
    const val VERSION : Int = 0x20400000
    const val PREVIOUS_BLOCK_HASH : String = "00000000000000000006a4a234288a44e715275f1775b77b2fddb6c02eb6b72f"
    const val MERKLE_ROOT_HASH : String = "2dc60c563da5368e0668b81bc4d8dd369639a1134f68e425a9a74e428801e5b8"
    const val TIMESTAMP : Int = 0x5DB8AB5E
    const val DIFFICULTY_BITS : Int = 0x17148EDF
    const val START_NONCE : Long = 3_000_000_000

    // Other values
    const val MAX_NONCE_STEP : Long = 100_000_000
}