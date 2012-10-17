//
//  GameADay.h
//
//  Created by Peter Hsu on 10/16/12.
//  Copyright (c) 2012 Game A Day, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

#define GAMEADAY_GAMEID          @"REPLACE ME!"

@interface GameADay : NSObject<NSURLConnectionDelegate> {
    NSURLConnection *connection;
}

@property (nonatomic, strong) NSURLConnection *connection;

+ (void)trackInstall;

@end

