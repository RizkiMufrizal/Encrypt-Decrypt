var ch = require('./rsaoaepwithsha256');
var aes256gcm = require('./aes256gcm');
var uuid = require('uuid');

var secretKey = uuid.v4().replace(/-/g, "");
console.log("Result Secret Key : " + secretKey);

var encrypted = ch.encrypt(secretKey, "./public_key.pem");
console.log("Result Encrypted : " +encrypted);

var decrypted = ch.decrypt(encrypted, "./private_pkcs8_key.pem");
console.log("Result Decrypt : " + decrypted);

var message = "Hello World";

var encryptedMessage = aes256gcm.encrypt(message, secretKey);
console.log("Result Encrypt Message : " + encryptedMessage);

var decryptedMessage = aes256gcm.decrypt(encryptedMessage, decrypted);
console.log("Result Decrypt Message : " + decryptedMessage);