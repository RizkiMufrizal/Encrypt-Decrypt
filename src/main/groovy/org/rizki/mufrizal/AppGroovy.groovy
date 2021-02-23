package org.rizki.mufrizal

class AppGroovy {
    static void main(String[] args) {
        def publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzVNBIAdt3iUZppRmuLfz\n" +
                "hobW10zcL/AoAwdFgGxBD7Lh3JWIAHAVsP+Yt0V521z3lQkULJQ6VzgOKL6HS/aa\n" +
                "v+Aa770FaOBhJbycE93T7noe9chhKhA3wvUVboc8QzSxuHZsfmcOMtsCK59F9NJ1\n" +
                "mzWCNUkwMcTxBssq5q21GTyynssQkiFBr1G7OH6JLWuFQLt14Zm3Wfrjnglj+O+H\n" +
                "IOBevRjYixknW+z3wHoEqi+cE7k6fS3KIXFGX3PsWlPthjMLbepx3JiKTNFey69t\n" +
                "a6KDeuY5gcJy3Ftr3nDCG5VmPIE1QZ13Z4KkHZ4mZcKTRiRsYmXlrZgBFPEEOpgV\n" +
                "owIDAQAB\n" +
                "-----END PUBLIC KEY-----"

        def privateKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDNU0EgB23eJRmm\n" +
                "lGa4t/OGhtbXTNwv8CgDB0WAbEEPsuHclYgAcBWw/5i3RXnbXPeVCRQslDpXOA4o\n" +
                "vodL9pq/4BrvvQVo4GElvJwT3dPueh71yGEqEDfC9RVuhzxDNLG4dmx+Zw4y2wIr\n" +
                "n0X00nWbNYI1STAxxPEGyyrmrbUZPLKeyxCSIUGvUbs4fokta4VAu3XhmbdZ+uOe\n" +
                "CWP474cg4F69GNiLGSdb7PfAegSqL5wTuTp9LcohcUZfc+xaU+2GMwtt6nHcmIpM\n" +
                "0V7Lr21rooN65jmBwnLcW2vecMIblWY8gTVBnXdngqQdniZlwpNGJGxiZeWtmAEU\n" +
                "8QQ6mBWjAgMBAAECggEBAK9cjMGzNqXFH/xCwNzA1y+tWC54CZKz1SiI/FYrnwGu\n" +
                "cPL5jzd4gz4xfpgAsYumAhp6r41HZ/B4ArfPyjQZwZ9g4wCges9Q3Afj55WcHtaN\n" +
                "3IVkh3/qbAWJVq2YuOJZTfRSyGTI1bqfjGH/XTs0yJcwAy5JfOz03DpGKTTtZT/Q\n" +
                "LKWMqGtD439w6a6E/wfbwQUTcjGokWgX6zhOJn5ox1Bxzwtke9QvzkKClIuAmNbP\n" +
                "dbshzNFMFM04blEIwHS/Jm+Br58TTo7uFPtGw2a3Pmj9eKeOhEkTWVd1rQLkQ98g\n" +
                "rZcdX4VDwpEWemyCnNsaph67G9dfWD9Rz9FXByWj1fECgYEA64+ZrIZUdQpl67Zi\n" +
                "AVzRovNVw3Dovxa8Nay/deTwDeeQW02PfYBFH3uzSLhl+k5FVPh9tCZdYoTlRLwD\n" +
                "KmFLtSTE1xACPUQ8lBumU9M/FTyursWflLoNDDOkpLF0RjAYT6QDVMICAVUZIGvv\n" +
                "R57GyzAsWDZjH2te8wd6aL+/z3kCgYEA3yQK8L7Bd44JkOqFE6LVcBX0GuNGjy9b\n" +
                "cQ1/TPb48GcRDhwQuG/5z78uemJTF0zxvCEVl13Gjqe9+bgndVpdi4ySsUKX+Mxi\n" +
                "zeM3XsoM04F79k961F22ND18LIUxLZAFyCB09Ji/S+F/N/M+9yfyYuWiRAKKdSWW\n" +
                "Stg6F2PoevsCgYEA3dsVhSPPD5yHeYUAsP/WgX5k2/nPe4nSIUtd14+Td8UMdLGL\n" +
                "30ubzpcWt/rUMPbe3bRbz/wCH/PCawYYhSW1xBmpOlRdh45o76VK9dATrdDFRN3j\n" +
                "+pNwDnnlKyfmtuQ9QWTbrkw6zz5yt9JwPigQWvY4DazLlp/tgT8dzuIpqSECgYBq\n" +
                "u97P0S7RdQt2WfdVsSnO93FP+y6hBtICfaZKtkfVFje+PAZzcnxXtucQez+rgY6P\n" +
                "onOld6GmUu44KLIXHCZqvc7dIzF2PK12Nh0iJhuEgAc/hj0Gn9yrmE1xLjSbyqw5\n" +
                "Ue0fooC+Vxp3NM8FggIa9CRty5lW96ewHUWMMqndSQKBgQCROfob3N4qAI8CFXJK\n" +
                "G+IEkNSYrdDj6Uu9WjM4A5otVDvzR2mqKg93JAsX1qRkwm2bWAg+mTPYq657VS2u\n" +
                "XU3sraSATPp0Mqav/MtA7PhnLhOEYMDSRnz2wO0ZLRJafuMiFcYl9q73Zyd2Z/Ja\n" +
                "QEikiQBuOsBXYRFQzOvkrzeriA==\n" +
                "-----END PRIVATE KEY-----"

        def secretKey = UUID.randomUUID().toString().replaceAll("-", "")
        println("Secret Key : ${secretKey}")

        def rSAOAEPWithSHA256Groovy = new RSAOAEPWithSHA256Groovy()

        def encryptedKey = rSAOAEPWithSHA256Groovy.encrypt(secretKey, publicKey)
        println("Encrypted Key ${encryptedKey}")

        def decryptedKey = rSAOAEPWithSHA256Groovy.decrypt(encryptedKey, privateKey)
        println("Decrypted Key ${decryptedKey}")

        String message = "Hello World"

        def aES256GCMGroovy = new AES256GCMGroovy()

        def encryptedMessage = aES256GCMGroovy.encrypt(message, decryptedKey)
        println("Encrypted Message ${encryptedMessage}")

        def decryptedMessage = aES256GCMGroovy.decrypt(encryptedMessage, decryptedKey)
        println("Decrypted Message ${decryptedMessage}")
    }
}