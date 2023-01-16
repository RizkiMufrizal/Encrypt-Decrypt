var crypto = require('crypto');

module.exports = {

    encrypt: function (text, masterkey) {
        const IV = crypto.randomBytes(12);
        const cipher = crypto.createCipheriv('aes-256-gcm', masterkey, IV);
        const encrypted = cipher.update(text, 'utf8');
        cipher.final();
        const tag = cipher.getAuthTag();
        return Buffer.concat([encrypted, tag, IV]).toString('base64');
    },

    decrypt: function (chiperText, masterkey) {
        const chiperTextBase64 = Buffer.from(chiperText, 'base64');
        const IV = chiperTextBase64.subarray(chiperTextBase64.length - 12);
        const tag = chiperTextBase64.subarray(chiperTextBase64.length - 28, chiperTextBase64.length - 12);
        const encrypted = chiperTextBase64.subarray(0, chiperTextBase64.length - 28);
        const decipher = crypto.createDecipheriv('aes-256-gcm', masterkey, IV);
        decipher.setAuthTag(tag);
        let stringDecrypted = decipher.update(encrypted, null, 'utf8');
        stringDecrypted += decipher.final('utf8');
        return stringDecrypted;
    }
};