const crypto = require('crypto');
const fs = require('fs');

function encrypt(s, keyFileName_public) {
    var encs = crypto.publicEncrypt(
        {
            key: readKeyFromPEMFile(keyFileName_public),
            padding: crypto.constants.RSA_PKCS1_OAEP_PADDING,
            oaepHash: "sha256"
        }, Buffer.from(s));
    var encs = encs.toString("base64");
    return encs;
}

function decrypt(es, keyFileName_private) {
    var options = {
        key: readKeyFromPEMFile(keyFileName_private),
        padding: crypto.constants.RSA_PKCS1_OAEP_PADDING,
        oaepHash: "sha256"
    };
    var dcs = crypto.privateDecrypt(options, Buffer.from(es, "base64"));
    var dcs = dcs.toString("utf8");
    return dcs;
}

function readKeyFromPEMFile(fn) {
    var s = fs.readFileSync(fn, 'utf8');
    console.log(s);
    return s;
}

module.exports = { encrypt, decrypt };