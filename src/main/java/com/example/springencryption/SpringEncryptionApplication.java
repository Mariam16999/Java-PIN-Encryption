package com.example.springencryption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Arrays;


public class SpringEncryptionApplication {

    public static void main(String[] args) {

        String pin = "4256";
        String cardNumber = "5259370015781523";
        String pinEncryptionKey = "3D7368268AE61CB502ECD0C29EBF3EFB";
        String pinBlockFiller = "F";

        // Step 1: Create the PIN Block
        int pinLength = pin.length();
        String pinBlock = String.format("0%d%s%s", pinLength, pin, pinBlockFiller.repeat(16 - pinLength - 2));

        // Step 2: Extract the PAN Block (rightmost 12 digits excluding the last digit)
        String panBlock = "0000" + cardNumber.substring(cardNumber.length() - 13, cardNumber.length() - 1);

        // Step 3: XOR the PIN Block with the PAN Block
        long pinBlockBytes = Long.parseUnsignedLong(pinBlock, 16);
        long panBlockBytes = Long.parseUnsignedLong(panBlock, 16);
        long xoredResult = pinBlockBytes ^ panBlockBytes;

        // Convert to hex and pad to 16 hex digits if necessary
        String xoredResultHex = String.format("%016X", xoredResult);

        try {
            // Step 4: Encrypt the XORed Result using 3DES (single DES mode)
            // Step 4: Adjust the key to be 24 bytes (192 bits) by appending the first 8 bytes to the existing key
            byte[] keyBytes = DatatypeConverter.parseHexBinary(pinEncryptionKey);
            if (keyBytes.length == 16) { // If the key is 16 bytes (128 bits), extend it to 24 bytes
                keyBytes = Arrays.copyOf(keyBytes, 24);
                System.arraycopy(keyBytes, 0, keyBytes, 16, 8); // Copy first 8 bytes to last 8 bytes
            }
            Key key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedPinBlock = cipher.doFinal(DatatypeConverter.parseHexBinary(xoredResultHex));

            // Result
            String encryptedPinBlockHex = DatatypeConverter.printHexBinary(encryptedPinBlock).toUpperCase();
            System.out.println("Encrypted PIN Block: " + encryptedPinBlockHex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
