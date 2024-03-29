package com.bottos.botc.sdk.exceptions;

public class UnreadableWalletException extends Exception {


    public UnreadableWalletException(String s) {
        super(s);
    }

    public UnreadableWalletException(String s, Throwable t) {
        super(s, t);
    }

    public static class BadPassword extends UnreadableWalletException {
        public BadPassword() {
            super("Password incorrect");
        }
    }

    public static class FutureVersion extends UnreadableWalletException {
        public FutureVersion() { super("Unknown wallet version from the future."); }
    }

    public static class WrongNetwork extends UnreadableWalletException {
        public WrongNetwork() {
            super("Mismatched network ID");
        }
    }
}