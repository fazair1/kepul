package com.juaracoding.kepul.security;

import java.util.function.Function;

public class BcryptImpl {

    private static final BcryptCustom bcrypt = new BcryptCustom(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyAndUpdateHash(String password,
                                              String hash,
                                              Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    public static boolean verifyHash(String password , String hash)
    {
        return bcrypt.verifyHash(password,hash);
    }

    public static void main(String[] args) {
//        String strUserName = "paul123Bagas@123";
        System.out.println(hash("142564"));
        System.out.println(hash("divisi.123divisi1234"));
        System.out.println(hash("fauzan.123fauzan@1234").length());
        System.out.println(verifyHash("fauzan.123fauzan@1234","$2a$11$Tk0IxRpKlrOKKjZ1ZcY/6eBBa.xShNmJRjcM6E7zJ2BZ8dmm4c8BW"));
        System.out.println(verifyHash("123456","$2a$11$8L6epKou2z5TQ9u8iFsiW./dhP.yJK1dRk71ugHkY.lQ77DVR4EVe"));
    }
}