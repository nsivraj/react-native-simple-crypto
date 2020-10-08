#import "RNSCPbkdf2.h"
#import "lib/Pbkdf2.h"

@implementation RNSCPbkdf2

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(hash:(NSString *)password
                  salt:(NSString *)salt
                  iterations:(int)iterations
                  keyLen:(int)keyLen
                  algorithm:(NSString *)algorithm
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    NSError *error = nil;
    NSString *data = [Pbkdf2 hash:password salt:salt iterations:iterations keyLen:keyLen algorithm:algorithm];
    if (data == nil) {
        reject(@"keygen_fail", @"Key generation failed", error);
    } else {
        resolve(data);
    }
}
@end
