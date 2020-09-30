#import <CommonCrypto/CommonCryptor.h>
#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonKeyDerivation.h>

#import "Shared.h"
#import "Aes.h"

@implementation Aes

+ (NSData *) AES128CBC: (NSString *)operation data: (NSData *)data key: (NSString *)key iv: (NSString *)iv {
    // Convert hex string to hex data.
    NSData *keyData = [Shared fromHex:key];
    NSData *ivData = [Shared fromHex:iv];
    size_t numBytes = 0;
    NSMutableData *buffer = [[NSMutableData alloc] initWithLength:[data length] + kCCBlockSizeAES128];

    CCCryptorStatus cryptStatus = CCCrypt(
                                          [operation isEqualToString:@"encrypt"] ? kCCEncrypt : kCCDecrypt,
                                          kCCAlgorithmAES128,
                                          kCCOptionPKCS7Padding,
                                          keyData.bytes,
                                          keyData.length,
                                          ivData.bytes,
                                          data.bytes, data.length,
                                          buffer.mutableBytes,
                                          buffer.length,
                                          &numBytes);

    if (cryptStatus == kCCSuccess) {
        [buffer setLength:numBytes];
        return buffer;
    }
    NSLog(@"AES error, %d", cryptStatus);
    return nil;
}

+ (NSString *) encrypt: (NSString *)clearText64 key: (NSString *)key iv: (NSString *)iv {
    NSData* clearData = [[NSData alloc] initWithBase64EncodedString:clearText64 options:0];
    NSData *result = [self AES128CBC:@"encrypt" data:clearData key:key iv:iv];
    return [result base64EncodedStringWithOptions:0];
}

+ (NSString *) decrypt: (NSString *)cipherText key: (NSString *)key iv: (NSString *)iv {
    NSData *result = [self AES128CBC:@"decrypt" data:[[NSData alloc] initWithBase64EncodedString:cipherText options:0] key:key iv:iv];
    return [result base64EncodedStringWithOptions:0];
}

@end
