package one.devs.blank.locker

import android.content.Context
import android.gesture.Gesture
import android.os.Parcel
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

private const val KEY_ALIAS = "gestureKey"
private const val FILE_NAME = "gesture.enc"

fun generateSecretKey() {
    val keyGenerator = javax.crypto.KeyGenerator.getInstance(
        "AES",
        "AndroidKeyStore"
    )
    val keyGenParameterSpec = android.security.keystore.KeyGenParameterSpec.Builder(
        KEY_ALIAS,
        android.security.keystore.KeyProperties.PURPOSE_ENCRYPT or android.security.keystore.KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)
        .build()
    keyGenerator.init(keyGenParameterSpec)
    keyGenerator.generateKey()
    Log.d("SecurityUtils", "Secret key generated")
}

fun getSecretKey(): SecretKey? {
    val keyStore = KeyStore.getInstance("AndroidKeyStore")
    keyStore.load(null)
    return keyStore.getKey(KEY_ALIAS, null) as? SecretKey
}

fun gestureToBytes(gesture: Gesture): ByteArray {
    val parcel = Parcel.obtain()
    gesture.writeToParcel(parcel, 0)
    val bytes = parcel.marshall()
    parcel.recycle()
    return bytes
}

fun bytesToGesture(bytes: ByteArray): Gesture {
    val parcel = Parcel.obtain()
    parcel.unmarshall(bytes, 0, bytes.size)
    parcel.setDataPosition(0)
    val gesture = Gesture.CREATOR.createFromParcel(parcel)
    parcel.recycle()
    return gesture
}

fun saveEncryptedGesture(context: Context, gesture: Gesture) {
    var secretKey = getSecretKey()
    if (secretKey == null) {
        generateSecretKey()
        secretKey = getSecretKey()
    }

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    val iv = cipher.iv

    val gestureBytes = gestureToBytes(gesture)
    val encryptedBytes = cipher.doFinal(gestureBytes)

    val outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
    outputStream.use { fos ->
        fos.write(iv.size)
        fos.write(iv)
        fos.write(encryptedBytes)
    }
    Log.d("SecurityUtils", "Gesture encrypted and saved")
}

fun loadEncryptedGesture(context: Context): Gesture? {
    val secretKey = getSecretKey() ?: return null

    val file = File(context.filesDir, FILE_NAME)
    if (!file.exists()) {
        Log.e("SecurityUtils", "Encrypted gesture file does not exist")
        return null
    }

    val inputStream = FileInputStream(file)
    inputStream.use { fis ->
        val ivSize = fis.read()
        val iv = ByteArray(ivSize)
        fis.read(iv)
        val encryptedBytes = fis.readBytes()

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return bytesToGesture(decryptedBytes)
    }
}