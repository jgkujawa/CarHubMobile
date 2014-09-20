/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-07-22 21:53:01 UTC)
 * on 2014-09-21 at 00:22:38 UTC 
 * Modify at your own risk.
 */

package com.appspot.car_hub.carhub.model;

/**
 * Model definition for ModelsStringRequest.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the carhub. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ModelsStringRequest extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("string")
  private java.lang.String string__;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getString() {
    return string__;
  }

  /**
   * @param string__ string__ or {@code null} for none
   */
  public ModelsStringRequest setString(java.lang.String string__) {
    this.string__ = string__;
    return this;
  }

  @Override
  public ModelsStringRequest set(String fieldName, Object value) {
    return (ModelsStringRequest) super.set(fieldName, value);
  }

  @Override
  public ModelsStringRequest clone() {
    return (ModelsStringRequest) super.clone();
  }

}
