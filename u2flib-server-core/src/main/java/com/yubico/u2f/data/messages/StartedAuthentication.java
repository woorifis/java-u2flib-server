/*
 * Copyright 2014 Yubico.
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://developers.google.com/open-source/licenses/bsd
 */

package com.yubico.u2f.data.messages;

import com.google.common.base.Objects;
import com.yubico.u2f.U2F;
import com.yubico.u2f.data.DataObject;

import static com.google.common.base.Preconditions.checkNotNull;

public class StartedAuthentication extends DataObject {
  /**
   * Version of the protocol that the to-be-registered U2F token must speak. For
   * the version of the protocol described herein, must be "U2F_V2"
   */
  private final String version;

  /** The websafe-base64-encoded challenge. */
  private final String challenge;

  /**
   * The application id that the RP would like to assert. The U2F token will
   * enforce that the key handle provided above is associated with this
   * application id. The browser enforces that the calling origin belongs to the
   * application identified by the application id.
   */
  private final String appId;

  /**
   * websafe-base64 encoding of the key handle obtained from the U2F token
   * during registration.
   */
  private final String keyHandle;

  public StartedAuthentication(String challenge, String appId, String keyHandle) {
    this.version = U2F.U2F_VERSION;
    this.challenge = checkNotNull(challenge);
    this.appId = checkNotNull(appId);
    this.keyHandle = checkNotNull(keyHandle);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(version, challenge, appId, keyHandle);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof StartedAuthentication))
      return false;
    StartedAuthentication other = (StartedAuthentication) obj;
    return Objects.equal(appId, other.appId)
            && Objects.equal(challenge, other.challenge)
            && Objects.equal(keyHandle, other.keyHandle)
            && Objects.equal(version, other.version);
  }

  public String getKeyHandle() {
    return keyHandle;
  }

  public String getChallenge() {
    return challenge;
  }

  public String getAppId() {
    return appId;
  }

  public static StartedAuthentication fromJson(String json) {
    return GSON.fromJson(json, StartedAuthentication.class);
  }
}
