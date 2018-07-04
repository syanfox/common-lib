package com.guo.common.util;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtil {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * RSA签名
	 * 
	 * @param待签名数据
	 * @param privateKey 商户私钥
	 * @param
	 * @return 签名值
	 */
	public static String sign(String src, String privateKey,
                              String inputCharset) {
		if (src == null) {
			return null;
		}
		String dest = null;
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey, Base64.DEFAULT));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(src.getBytes(inputCharset));

			byte[] signed = signature.sign();

			return Base64.encodeToString(signed, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			dest = src;
		}

		return dest;
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            支付宝公钥
	 * @param inputCharset
	 *            编码格式
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign, String publicKey,
                                 String inputCharset) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey, Base64.DEFAULT);
			PublicKey pubKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(inputCharset));

			boolean bverify = signature.verify(Base64.decode(sign, Base64.DEFAULT));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	


	/**
	 * 解密
	 * 
	 * @param content
	 *            密文
	 * @param private_key
	 *            商户私钥
	 * @param input_charset
	 *            编码格式
	 * @return 解密后的字符串
	 */
	public static String decrypt(String content, String private_key,
                                 String input_charset) throws Exception {
		PrivateKey prikey = getPrivateKey(private_key);

		//Cipher cipher = Cipher.getInstance("RSA");
		Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");  //如果写成 RSA 解析不处理java后台加密的数据

		cipher.init(Cipher.DECRYPT_MODE, prikey);

		InputStream ins = new ByteArrayInputStream(Base64.decode(content, Base64.DEFAULT));
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
		byte[] buf = new byte[128];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}

		return new String(writer.toByteArray(), input_charset);
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;

		keyBytes = Base64.decode(key, Base64.DEFAULT);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;
	}
	
	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {

		byte[] keyBytes;

		keyBytes = Base64.decode(key, Base64.DEFAULT);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		return publicKey;
	}
	


	/**
	 * 加密
	 * @param content 需要加密的内容
	 * @param publicKey 公钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content, String publicKey, String charset)  throws Exception {
		PublicKey publickey = getPublicKey(publicKey);

		//Cipher cipher = Cipher.getInstance("RSA");
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  //如果写成RSA  Java后台解析不出来
		cipher.init(Cipher.ENCRYPT_MODE, publickey);

		byte[] cipherText = cipher.doFinal(content.getBytes(charset));

		return Base64.encodeToString(cipherText, Base64.DEFAULT);
	}
}
