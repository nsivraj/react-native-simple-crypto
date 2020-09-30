#import "RNSCSha.h"
#import "Sha.h"

@implementation RNSCSha

RCT_EXPORT_MODULE()
 
RCT_EXPORT_METHOD(shaBase64:(NSString *)text :(NSString *)algorithm
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    NSError *error = nil;
    NSString *data = [Sha shaBase64:text :algorithm];
    if (data == nil) {
        reject(@"shaBase64_fail", @"Hash error", error);
    } else {
        resolve(data);
    }
}

RCT_EXPORT_METHOD(shaUtf8:(NSString *)text :(NSString *)algorithm
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    NSError *error = nil;
    NSString *data = [Sha shaUtf8:text :algorithm];
    if (data == nil) {
        reject(@"shaUtf8_fail", @"Hash error", error);
    } else {
        resolve(data);
    }
}

@end
