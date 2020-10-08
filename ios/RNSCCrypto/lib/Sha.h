#import <Foundation/Foundation.h>

@interface Sha : NSObject
+ (NSData *) sha: (NSData *)input :(NSString *)type;
+ (NSString *) shaUtf8: (NSString *)input :(NSString *)type;;
+ (NSString *) shaBase64: (NSString *)input :(NSString *)type;;
@end
