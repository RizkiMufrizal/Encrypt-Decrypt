var ch = require('./rsaoaepwithsha256');
var aes256gcm = require('./aes256gcm');
var uuid = require('uuid');

var secretKey = uuid.v4().replace(/-/g, "");
console.log("Secret Key : " + secretKey);

var encrypted = ch.encrypt(secretKey, "./public_key.pem");
console.log(encrypted);

var decrypted = ch.decrypt(encrypted, "./private_pkcs8_key.pem");
console.log("Result Decrypt : " + decrypted);