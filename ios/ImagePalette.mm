#import "ImagePalette.h"

#import <react_native_image_palette/react_native_image_palette-Swift.h>

@implementation ImagePalette
RCT_EXPORT_MODULE()


- (instancetype)init
{
    self = [super init];
    if (self) {
        self.manager = [[ImagePaletteModule alloc] init];
    }
    return self;
}


RCT_EXPORT_METHOD(getAverageColor:(NSString*)uri
                  config:(NSDictionary *)config
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
    ImagePaletteModule *imagePalette = (ImagePaletteModule *)self.manager;
    
    NSDictionary* headers = [config objectForKey:@"headers"];

    [imagePalette getAverageColorWithUri:uri headers:headers onResolve:resolve onReject:reject];
}

RCT_EXPORT_METHOD(getPalette:(NSString*)uri
                  config:(NSDictionary *)config
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
    ImagePaletteModule *imagePalette = (ImagePaletteModule *)self.manager;
    
    NSDictionary* headers = [config objectForKey:@"headers"];
    NSString* fallback = [config objectForKey:@"fallbackColor"];
    
    if (fallback == nil) {
        fallback = @"#fff";
    }

    [imagePalette getPaletteWithUri:uri fallback:fallback headers:headers onResolve:resolve onReject:reject];
}

RCT_EXPORT_METHOD(getSegmentsAverageColor:(NSString*)uri
                  segments:(NSArray*)segments
                  config:(NSDictionary *)config
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
    ImagePaletteModule *imagePalette = (ImagePaletteModule *)self.manager;

    
    NSDictionary* headers = [config objectForKey:@"headers"];
    
    [imagePalette getSegmentsAverageColorWithUri:uri segments:segments headers:headers onResolve:resolve onReject:reject];
}
RCT_EXPORT_METHOD(getSegmentsPalette:(NSString*)uri
                  segments:(NSArray*)segments
                  config:(NSDictionary *)config
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
    ImagePaletteModule *imagePalette = (ImagePaletteModule *)self.manager;
    
    NSDictionary* headers = [config objectForKey:@"headers"];
    
    NSString* fallback = [config objectForKey:@"fallbackColor"];
    
    if (fallback == nil) {
        fallback = @"#fff";
    }
    
    [imagePalette getSegmentsPaletteWithUri:uri segments:segments fallback:fallback headers:headers onResolve:resolve onReject:reject];
}



// Don't compile this code when we build for the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeImagePaletteSpecJSI>(params);
}
#endif

@end
