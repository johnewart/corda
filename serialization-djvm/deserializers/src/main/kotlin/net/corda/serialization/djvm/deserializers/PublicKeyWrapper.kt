package net.corda.serialization.djvm.deserializers

import java.security.PublicKey
import java.util.Arrays
import java.util.function.Function

class PublicKeyWrapper : Function<Array<out Any>, PublicKey> {
    override fun apply(data: Array<out Any>): PublicKey {
        return WrappedPublicKey(
            algorithm = data[0] as? String,
            format = data[1] as? String,
            encoded = data[2] as ByteArray
        )
    }
}

private class WrappedPublicKey(
    private val algorithm: String?,
    private val format: String?,
    private val encoded: ByteArray
) : PublicKey {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        } else if (other !is PublicKey) {
            return false
        } else {
            return Arrays.equals(encoded, other.encoded)
        }
    }

    override fun hashCode(): Int {
        return encoded.hashCode()
    }

    override fun getEncoded(): ByteArray {
        return encoded.clone()
    }

    override fun getFormat(): String? {
        return format
    }

    override fun getAlgorithm(): String? {
        return algorithm
    }
}