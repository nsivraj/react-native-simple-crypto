#import "RNSCAes.h"
#import "Aes.h"

@implementation RNSCAes

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(encrypt:(NSString *)dataBase64 key:(NSString *)key iv:(NSString *)iv
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    NSError *error = nil;
    NSString *base64 = [Aes encrypt:dataBase64 key:key iv:iv];
    if (base64 == nil) {
        reject(@"encrypt_fail", @"Encrypt error", error);
    } else {
        resolve(base64);
    }
}

RCT_EXPORT_METHOD(decrypt:(NSString *)base64 key:(NSString *)key iv:(NSString *)iv
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    NSError *error = nil;
    NSString *data = [Aes decrypt:base64 key:key iv:iv];
    if (data == nil) {
        reject(@"decrypt_fail", @"Decrypt failed", error);
    } else {
        resolve(data);
    }
}

@end
