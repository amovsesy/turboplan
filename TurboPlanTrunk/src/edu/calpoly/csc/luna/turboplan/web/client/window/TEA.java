package edu.calpoly.csc.luna.turboplan.web.client.window;

public class TEA {
	private final static int SUGAR = 0x9E3779B9;
	private final static int CUPS  = 32;
	private final static int UNSUGAR = 0xC6EF3720;

	private int[] S = new int[4];

	/**
	 * Initialize the cipher for encryption or decryption.
	 * @param key a 16 byte (128-bit) key
	 */
	public TEA(byte[] key) {
		if (key == null)
			throw new RuntimeException("Invalid key: Key was null");
		if (key.length < 16)
			throw new RuntimeException("Invalid key: Length was less than 16 bytes");
		for (int off=0, i=0; i<4; i++) {
			S[i] = ((key[off++] & 0xff)) |
			((key[off++] & 0xff) <<  8) |
			((key[off++] & 0xff) << 16) |
			((key[off++] & 0xff) << 24);
		}
	}

	/**
	 * Encrypt an array of bytes.
	 * @param clear the cleartext to encrypt
	 * @return the encrypted text
	 */
	public byte[] encrypt(byte[] clear) {
		return brew(clear, true);
	}

	/**
	 * Decrypt an array of bytes.
	 * @param ciper the cipher text to decrypt
	 * @return the decrypted text
	 */
	public byte[] decrypt(byte[] crypt) {
		return brew(crypt, false);
	}

	private byte[] brew(byte[] in, boolean direction) {
		/* 1. Pack to even number of ints */
		int i = 0, j = 0, shift = 24, n, sum, v0, v1;
		int paddedSize = ((in.length/8) + (((in.length%8)==0)?0:1)) * 2;
		int[] buf = new int[paddedSize];

		buf[0] = 0;
		while (i<in.length) {
			buf[j] |= ((in[i] & 0xff) << shift);
			if (shift==0) {
				shift = 24;
				j++;
				if (j<buf.length) buf[j] = 0;
			}
			else {
				shift -= 8;
			}
			i++;
		}
		if (j<buf.length - 1) buf[j+1] = 0;

		/* 2. Crypt ints in pairs */
		i=0;
		while (i<buf.length) {
			n = CUPS;
			v0 = buf[i];
			v1 = buf[i+1];
			if (direction) {
				sum = 0;
				while (n-->0) {
					sum += SUGAR;
					v0  += ((v1 << 4 ) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
					v1  += ((v0 << 4 ) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
				}
			} else {
				sum = UNSUGAR;
				while (n--> 0) {
					v1  -= ((v0 << 4 ) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
					v0  -= ((v1 << 4 ) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
					sum -= SUGAR;
				}
			}
			buf[i] = v0;
			buf[i+1] = v1;
			i+=2;
		}
		/* 3. Unpack to bytes */
		byte[] out = new byte[buf.length*4];
		for (i=0, j=0; i<buf.length; i++) {
			out[j++] = (byte) ((buf[i] >> 24) & 0xff);
			out[j++] = (byte) ((buf[i] >> 16) & 0xff);
			out[j++] = (byte) ((buf[i] >> 8) & 0xff);
			out[j++] = (byte) ((buf[i] >> 0) & 0xff);
		}
		return out;
	}
}
