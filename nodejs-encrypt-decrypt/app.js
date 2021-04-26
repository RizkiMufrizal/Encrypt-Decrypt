var ch = require('./crypto-helper');
var uuid = require('uuid');

var secretKey = uuid.v4().replace(/-/g, "");
console.log("Secret Key : " + secretKey);

var encrypted = ch.encrypt(secretKey, "./public_key.pem");
console.log(encrypted);

var decrypted = ch.decrypt(encrypted, "./private_pkcs8_key.pem");
console.log("Result Decrypt : " + decrypted);
