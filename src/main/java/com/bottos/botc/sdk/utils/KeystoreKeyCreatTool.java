package com.bottos.botc.sdk.utils;


import android.util.Log;

import com.bottos.botc.sdk.entity.ETHWallet;
import com.bottos.botc.sdk.entity.WalletKeyPair;
import com.bottos.botc.sdk.exceptions.UnreadableWalletException;
import com.bottos.botc.sdk.utils.crypto.CipherException;
import com.bottos.botc.sdk.utils.crypto.Credentials;
import com.bottos.botc.sdk.utils.crypto.DeterministicSeed;
import com.bottos.botc.sdk.utils.crypto.ECKeyPair;
import com.bottos.botc.sdk.utils.crypto.Keys;
import com.bottos.botc.sdk.utils.crypto.Numeric;
import com.bottos.botc.sdk.utils.crypto.SecureRandomUtils;
import com.bottos.botc.sdk.utils.crypto.Sign;
import com.bottos.botc.sdk.utils.crypto.Wallet;
import com.bottos.botc.sdk.utils.crypto.WalletFile;
import com.bottos.botc.sdk.utils.protocol.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.annotation.Nullable;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;

import static com.bottos.botc.sdk.utils.crypto.Hash.sha256;

/**
 * Created by xionglh on 2018/8/20
 */
public class KeystoreKeyCreatTool {

    private static final SecureRandom secureRandom = SecureRandomUtils.secureRandom();
    private static final int PUBLIC_KEY_SIZE = 64;
    private static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
    private static ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

//    private static WalletKeyPair createWalletKeyPair() {
//        WalletKeyPair walletKeyPair = new WalletKeyPair();
//        long creationTimeSeconds = System.currentTimeMillis() / 1000;
//        DeterministicSeed ds = new DeterministicSeed(secureRandom, 128, "", creationTimeSeconds);
//        ECKeyPair ecKeyPair = getECKeyPair(ds);
//        BigInteger publicKey = ecKeyPair.getPublicKey();
//        String publicKeyStr = Numeric.toHexStringWithPrefixZeroPadded(publicKey, PUBLIC_KEY_LENGTH_IN_HEX);
//        BigInteger privateKey = ecKeyPair.getPrivateKey();
//        String privateKeyStr = Numeric.toHexStringNoPrefixZeroPadded(privateKey, Keys.PRIVATE_KEY_LENGTH_IN_HEX);
//
//        walletKeyPair.setPrivateKey(privateKeyStr);
//        walletKeyPair.setPublicKey(publicKeyStr);
//        return walletKeyPair;
//    }

    public static WalletKeyPair createWalletKeyPair( ) {
        return createWalletKeyPair(getMnemonic());
    }

    public static WalletKeyPair createWalletKeyPair( String seend) {
        WalletKeyPair walletKeyPair = new WalletKeyPair();
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed deterministicSeed = null;
//        List mnemonicList = Arrays.asList(mnemonic.split(" "));
//        byte[] seedByte = new SeedCalculator()
//                .withWordsFromWordList(English.INSTANCE)
//                .calculateSeed(mnemonicList, "");
        try {
            deterministicSeed = new DeterministicSeed(seend, null, "", creationTimeSeconds);
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }
        ECKeyPair ecKeyPair = getECKeyPair(deterministicSeed);
        BigInteger publicKey = ecKeyPair.getPublicKey();
        String publicKeyStr = Numeric.toHexStringWithPrefixZeroPadded(publicKey, PUBLIC_KEY_LENGTH_IN_HEX);
        BigInteger privateKey = ecKeyPair.getPrivateKey();

        String privateKeyStr = Numeric.toHexStringNoPrefixZeroPadded(privateKey, Keys.PRIVATE_KEY_LENGTH_IN_HEX);

        walletKeyPair.setPrivateKey(privateKeyStr);
        walletKeyPair.setPublicKey(publicKeyStr);
        return walletKeyPair;
    }



    public static String getMnemonic(){
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE).createMnemonic(entropy, sb::append);
        return sb.toString();
    }
    private static ECKeyPair getECKeyPair(DeterministicSeed ds) {
        byte[] seedBytes = ds.getSeedBytes();
        if (seedBytes == null)
            return null;
        return ECKeyPair.create(sha256(seedBytes));
    }

    public static String createKeys() {
        WalletKeyPair walletKeyPair = createWalletKeyPair();
        return GJsonUtil.toJson(walletKeyPair, WalletKeyPair.class);
    }

    public static String createKeystore(String password, String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        WalletFile walletFile;
        try {
            walletFile = Wallet.create(password, ecKeyPair, 1024, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return GJsonUtil.toJson(walletFile, WalletFile.class);
    }

    public static String recoverKeystore(String pwd, String keystore) {
        Credentials credentials;
        ECKeyPair keypair;
        String privateKey = null;
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            credentials = Credentials.create(Wallet.decrypt(pwd, walletFile));
            keypair = credentials.getEcKeyPair();
            privateKey = Numeric.toHexStringNoPrefixZeroPadded(keypair.getPrivateKey(), Keys.PRIVATE_KEY_LENGTH_IN_HEX);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static String createBtoKeystore(String accountName, String password, String privateKey) {
        String keyStoreKey = createKeystore(password, privateKey);
        try {
            JSONObject jsonObject = new JSONObject(keyStoreKey);
            JSONObject crypto = jsonObject.getJSONObject("crypto");
            String id = jsonObject.getString("id");
            String version = jsonObject.getString("version");
            JSONObject result = new JSONObject();
            result.put("account", accountName);
            result.put("crypto", crypto);
            result.put("id", id);
            result.put("version", version);
            keyStoreKey = result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return keyStoreKey;
    }

    public static String privateKeyToPublicKey(String privateKey) {
        return Numeric.toHexStringWithPrefixZeroPadded(Sign.publicKeyFromPrivate(Numeric.toBigIntNoPrefix(privateKey)), PUBLIC_KEY_LENGTH_IN_HEX);
    }

    /***************** 以下代码均为为了web插件做测试而写 ******************/
    /**
     * 通用的以太坊基于bip44协议的助记词路径 （imtoken jaxx Metamask myetherwallet）
     */
    public static String ETH_JAXX_TYPE = "m/44'/60'/0'/0/0";

    /**
     * 创建助记词，并通过助记词创建钱包
     *
     * @param walletName
     * @param pwd
     * @return
     */
    public static ETHWallet generateMnemonic(String walletName, String pwd) {
        String[] pathArray = ETH_JAXX_TYPE.split("/");
        String passphrase = "";
        long creationTimeSeconds = System.currentTimeMillis() / 1000;

        org.bitcoinj.wallet.DeterministicSeed ds = new org.bitcoinj.wallet.DeterministicSeed(secureRandom, 128, passphrase, creationTimeSeconds);
        return generateWalletByMnemonic(walletName, ds, pathArray, pwd);
    }

    /**
     * @param walletName 钱包名称
     * @param ds         助记词加密种子
     * @param pathArray  助记词标准
     * @param pwd        密码
     * @return
     */
    @Nullable
    public static ETHWallet generateWalletByMnemonic(String walletName, org.bitcoinj.wallet.DeterministicSeed ds,
                                                     String[] pathArray, String pwd) {
        //种子
        byte[] seedBytes = ds.getSeedBytes();
//        System.out.println(Arrays.toString(seedBytes));
        //助记词
        List<String> mnemonic = ds.getMnemonicCode();
//        System.out.println(Arrays.toString(mnemonic.toArray()));
        if (seedBytes == null)
            return null;
        DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
        for (int i = 1; i < pathArray.length; i++) {
            ChildNumber childNumber;
            if (pathArray[i].endsWith("'")) {
                int number = Integer.parseInt(pathArray[i].substring(0,
                        pathArray[i].length() - 1));
                childNumber = new ChildNumber(number, true);
            } else {
                int number = Integer.parseInt(pathArray[i]);
                childNumber = new ChildNumber(number, false);
            }
            dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
        }
        ECKeyPair keyPair = ECKeyPair.create(dkKey.getPrivKeyBytes());
        ETHWallet ethWallet = generateWallet(walletName, pwd, keyPair);
        if (ethWallet != null) {
            ethWallet.setMnemonic(convertMnemonicList(mnemonic));
        }
        return ethWallet;
    }

    private static String convertMnemonicList(List<String> mnemonics) {
        StringBuilder sb = new StringBuilder();
        for (String mnemonic : mnemonics) {
            sb.append(mnemonic);
            sb.append(" ");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    @Nullable
    private static ETHWallet generateWallet(String walletName, String pwd, ECKeyPair ecKeyPair) {
        WalletFile walletFile;
        try {
            walletFile = Wallet.create(pwd, ecKeyPair, 1024, 1); // WalletUtils. .generateNewWalletFile();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        BigInteger publicKey = ecKeyPair.getPublicKey();
        String publicKeyStr = Numeric.toHexStringWithPrefixZeroPadded(publicKey, PUBLIC_KEY_LENGTH_IN_HEX);
        BigInteger privateKey = ecKeyPair.getPrivateKey();
        String privateKeyStr = Numeric.toHexStringNoPrefixZeroPadded(privateKey, Keys.PRIVATE_KEY_LENGTH_IN_HEX);
        String keyStoreStr = GJsonUtil.toJson(walletFile, WalletFile.class);


        Log.i("publicKeyStr ", publicKeyStr);
        Log.i("privateKeyStr", privateKeyStr);
        Log.i("keyStore", keyStoreStr);


        Log.i("ETHWalletUtils", "publicKey = " + publicKeyStr);
        ETHWallet ethWallet = new ETHWallet();
        ethWallet.setName(walletName);
        ethWallet.setAddress(Keys.toChecksumAddress(walletFile.getAddress()));
        ethWallet.setPrivateKeyStr(privateKeyStr);
        ethWallet.setPublicKeyStr(publicKeyStr);
        return ethWallet;
    }

    /**
     * 通过导入助记词，导入钱包
     *
     * @param walletName 钱包名
     * @param list 助记词
     * @param pwd  密码
     * @return
     */
    public static ETHWallet importMnemonic(String walletName, List<String> list, String pwd) {
        String path = ETH_JAXX_TYPE;
        if (!path.startsWith("m") && !path.startsWith("M")) {
            //参数非法
            return null;
        }
        String[] pathArray = path.split("/");
        if (pathArray.length <= 1) {
            //内容不对
            return null;
        }
        String passphrase = "";
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        org.bitcoinj.wallet.DeterministicSeed ds = new org.bitcoinj.wallet.DeterministicSeed(list, null, passphrase, creationTimeSeconds);
        return generateWalletByMnemonic(walletName, ds, pathArray, pwd);
    }
}
