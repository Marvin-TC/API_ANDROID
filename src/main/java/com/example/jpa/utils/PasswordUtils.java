package com.example.jpa.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public final class PasswordUtils {

    private PasswordUtils() {
    }

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATIONS = 100_000;
    private static final int KEY_LENGTH = 256;

    // Generar un salt aleatorio (16 bytes)
    public static byte[] generarSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    // Generar el hash PBKDF2 del password
    public static byte[] hash(char[] password, byte[] salt) throws GeneralSecurityException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();

        // Limpiar el arreglo de contraseña por seguridad
        Arrays.fill(password, '\u0000');
        return hash;
    }

    // Verificar si una contraseña coincide con su hash almacenado
    public static boolean verify(char[] password, byte[] salt, byte[] expectedHash) throws GeneralSecurityException {
        byte[] candidato = hash(password, salt);
        boolean ok = MessageDigest.isEqual(candidato, expectedHash);

        Arrays.fill(candidato, (byte) 0);
        Arrays.fill(password, '\u0000');
        return ok;
    }

    // Codificar a Base64
    public static String toBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    // Decodificar desde Base64
    public static byte[] fromBase64(String s) {
        return Base64.getDecoder().decode(s);
    }
}