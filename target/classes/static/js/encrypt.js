async function encryptText(plaintext, key) {
    var enc = new JSEncrypt({
        default_key_size: 2048
    });
    enc.setPublicKey(key);
    return btoa(enc.encrypt(plaintext));
}