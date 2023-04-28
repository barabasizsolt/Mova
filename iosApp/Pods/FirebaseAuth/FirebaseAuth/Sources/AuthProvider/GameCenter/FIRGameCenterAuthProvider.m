/*
 * Copyright 2018 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#import "FirebaseAuth/Sources/Public/FirebaseAuth/FIRGameCenterAuthProvider.h"
#import <GameKit/GameKit.h>

#import "FirebaseAuth/Sources/AuthProvider/GameCenter/FIRGameCenterAuthCredential.h"
#import "FirebaseAuth/Sources/Utilities/FIRAuthErrorUtils.h"
#import "FirebaseAuth/Sources/Utilities/FIRAuthExceptionUtils.h"

NS_ASSUME_NONNULL_BEGIN

@implementation FIRGameCenterAuthProvider

- (instancetype)init {
  [FIRAuthExceptionUtils
      raiseMethodNotImplementedExceptionWithReason:@"This class is not meant to be initialized."];
  return nil;
}

+ (void)getCredentialWithCompletion:(FIRGameCenterCredentialCallback)completion {
  /**
   Linking GameKit.framework without using it on macOS results in App Store rejection.
   Thus we don't link GameKit.framework to our SDK directly. `optionalLocalPlayer` is used for
   checking whether the APP that consuming our SDK has linked GameKit.framework. If not, a
   `GameKitNotLinkedError` will be raised.
   **/
  GKLocalPlayer *_Nullable optionalLocalPlayer = [[NSClassFromString(@"GKLocalPlayer") alloc] init];

  if (!optionalLocalPlayer) {
    if (completion) {
      completion(nil, [FIRAuthErrorUtils gameKitNotLinkedError]);
    }
    return;
  }

  __weak GKLocalPlayer *localPlayer = [[optionalLocalPlayer class] localPlayer];
  if (!localPlayer.isAuthenticated) {
    if (completion) {
      completion(nil, [FIRAuthErrorUtils localPlayerNotAuthenticatedError]);
    }
    return;
  }
  if (@available(iOS 13.5, macOS 10.15.5, macCatalyst 13.5,
  // Availability of fetchItemsForIdentityVerificationSignature was extended to tvOS 13.4.8 in
  // Xcode 14 (from tvOS 13.5 in Xcode 13).
  // TODO: Remove this check when we no longer support Xcode 13.
#if defined(__TVOS_16_0)
                 tvOS 13.4.8,
#else
                 tvOS 13.5,
#endif
                     *)) {
    [localPlayer fetchItemsForIdentityVerificationSignature:^(
                     NSURL *_Nullable publicKeyURL, NSData *_Nullable signature,
                     NSData *_Nullable salt, uint64_t timestamp, NSError *_Nullable error) {
      if (error) {
        if (completion) {
          completion(nil, error);
        }
      } else {
        FIRGameCenterAuthCredential *credential =
            [[FIRGameCenterAuthCredential alloc] initWithPlayerID:localPlayer.playerID
                                                     teamPlayerID:localPlayer.teamPlayerID
                                                     gamePlayerID:localPlayer.gamePlayerID
                                                     publicKeyURL:publicKeyURL
                                                        signature:signature
                                                             salt:salt
                                                        timestamp:timestamp
                                                      displayName:localPlayer.displayName];
        completion(credential, nil);
      }
    }];
  } else {
    [localPlayer generateIdentityVerificationSignatureWithCompletionHandler:^(
                     NSURL *publicKeyURL, NSData *signature, NSData *salt, uint64_t timestamp,
                     NSError *error) {
      if (error) {
        if (completion) {
          completion(nil, error);
        }
      } else {
        if (completion) {
          /**
           @c `localPlayer.alias` is actually the displayname needed, instead of
           `localPlayer.displayname`. For more information, check
           https://developer.apple.com/documentation/gamekit/gkplayer
           **/
          NSString *displayName = localPlayer.alias;
// iOS 13 deprecation
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wdeprecated-declarations"
          FIRGameCenterAuthCredential *credential =
              [[FIRGameCenterAuthCredential alloc] initWithPlayerID:localPlayer.playerID
                                                       teamPlayerID:nil
                                                       gamePlayerID:nil
                                                       publicKeyURL:publicKeyURL
                                                          signature:signature
                                                               salt:salt
                                                          timestamp:timestamp
                                                        displayName:displayName];
#pragma clang diagnostic pop
          completion(credential, nil);
        }
      }
    }];
  }
}

@end

NS_ASSUME_NONNULL_END
