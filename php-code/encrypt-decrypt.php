<?php

require __DIR__ . '/vendor/autoload.php';

use phpseclib3\Crypt\Random;
use phpseclib3\Crypt\AES;

function encrypt_payload($payload, $key) {
    $nonce = Random::string(12);

    $cipher = new AES('gcm');
    $cipher->setKey($key);
    $cipher->setNonce($nonce);

    $ciphertext = $cipher->encrypt($payload) . $cipher->getTag() . $nonce;
    return base64_encode($ciphertext);
}

function decrypt_payload($payload, $key) {
    $decoded = base64_decode($payload);

    $nonce = substr($decoded, -12);
    $decoded = substr($decoded, 0, -12);

    $tag = substr($decoded, -16);
    $decoded = substr($decoded, 0, -16);

    $cipher = new AES('gcm');
    $cipher->setKey($key);
    $cipher->setNonce($nonce);
    $cipher->setTag($tag);

    return $cipher->decrypt($decoded);
}

$secret = "079423bc31698dbaf2d6f49973301b97";

$contentEncryption = encrypt_payload("hello world", $secret);
echo "encryption : ", $contentEncryption, PHP_EOL;

$contentDecryption = decrypt_payload($contentEncryption, $secret);
echo "decryption : ", $contentDecryption, PHP_EOL;

?>