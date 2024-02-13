function encryptText(plaintext, key) {
    var enc = new JSEncrypt();
    enc.setPublicKey(key);
    return enc.encrypt(plaintext);
}
async function importPublicKey(base64PublicKey) {
    try {
        const publicKeyData = Uint8Array.from(atob(base64PublicKey), c => c.charCodeAt(0));
        return await window.crypto.subtle.importKey(
            "spki",
            publicKeyData,
            {
                name: "RSA",
                hash: {name: "SHA-256"},
            },
            true,
            ["encrypt"]
        );
    } catch (error) {
        throw new Error("Failed to import public key: " + error.message);
    }
}