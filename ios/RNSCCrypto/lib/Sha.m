#import <CommonCrypto/CommonDigest.h>

#import "Shared.h"
#import "Sha.h"

@implementation Sha

+ (NSData *) sha: (NSData *)input :(NSString *)type {
    NSDictionary *algMap = @{
        @"SHA-1" : [NSNumber numberWithInt:CC_SHA1_DIGEST_LENGTH],
        @"SHA-256" : [NSNumber numberWithInt:CC_SHA256_DIGEST_LENGTH],
        @"SHA-512" : [NSNumber numberWithInt:CC_SHA512_DIGEST_LENGTH],
    };

    int digestLength = [[algMap valueForKey:type] intValue];
    if (digestLength == 0) {
        [NSException raise:@"Invalid hash algorithm" format:@"%@ is not a valid hash algorithm", type];
    }

    if (digestLength == CC_SHA1_DIGEST_LENGTH) {
        NSMutableData *result = [[NSMutableData alloc] initWithLength:CC_SHA1_DIGEST_LENGTH];
        CC_SHA1([input bytes], (CC_LONG)[input length], result.mutableBytes);
        return result;
    } else {
        unsigned char* buffer = malloc(digestLength);
        if (digestLength == CC_SHA256_DIGEST_LENGTH) {
            CC_SHA256([input bytes], (CC_LONG)[input length], buffer);
        } else {
            CC_SHA512([input bytes], (CC_LONG)[input length], buffer);
        }
        return [NSData dataWithBytesNoCopy:buffer length:digestLength freeWhenDone:YES];
    }
}

+ (NSString *) shaUtf8: (NSString *)input :(NSString *)type {
    NSData* inputData = [input dataUsingEncoding:NSUTF8StringEncoding];
    NSData* result = [self sha:inputData :type];
    return [Shared toHex:result];
}

+ (NSString *) shaBase64: (NSString *)input :(NSString *)type {
    NSData* inputData = [[NSData alloc] initWithBase64EncodedString:input options:0];
    NSData* result = [self sha:inputData :type];
    return [result base64EncodedStringWithOptions:0];
}

@end
