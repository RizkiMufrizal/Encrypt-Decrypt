var crypto = require('crypto');

module.exports = {

    encrypt: function (text, masterkey) {
        try {
            var iv = crypto.randomBytes(16);

            var salt = crypto.randomBytes(64);

            var key = crypto.pbkdf2Sync(masterkey, salt, 2145, 32, 'sha512');

            var cipher = crypto.createCipheriv('aes-256-gcm', key, iv);

            var encrypted = Buffer.concat([cipher.update(text, 'utf8'), cipher.final()]);

            var tag = cipher.getAuthTag();

            return Buffer.concat([salt, iv, tag, encrypted]).toString('base64');

        } catch (e) {
            console.log(e);
        }

        return null;
    },

    decrypt: function (data, masterkey) {
        try {
            var bData = Buffer.from(data, 'base64');

            let salt = bData.slice(0, 64);
            let iv = bData.slice(64, 80);
            let tag = bData.slice(80, 96);
            let text = bData.slice(96);

            //var key = crypto.pbkdf2Sync(masterkey, salt, 2145, 32, 'sha512');

            var decipher = crypto.createDecipheriv('aes-256-gcm', masterkey, iv);
            decipher.setAuthTag(tag);

            // var decrypted = decipher.update(text, 'binary', 'utf8');
            // decrypted += decipher.final('utf8');

            let dec = decipher.update(text, null, 'utf8')
            dec += decipher.final('utf8');          

            return dec;

        } catch (e) {
            console.log(e);
        }

        return null;
    }
};