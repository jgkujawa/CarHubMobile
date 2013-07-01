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
 * (build: 2013-06-26 16:27:34 UTC)
 * on 2013-07-01 at 03:01:25 UTC 
 * Modify at your own risk.
 */

package com.google.api.services.carhub.model;

/**
 * Model definition for ExpenseCategory.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ExpenseCategory extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String category;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("server_id")
  private java.lang.String serverId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String subcategory;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCategory() {
    return category;
  }

  /**
   * @param category category or {@code null} for none
   */
  public ExpenseCategory setCategory(java.lang.String category) {
    this.category = category;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getServerId() {
    return serverId;
  }

  /**
   * @param serverId serverId or {@code null} for none
   */
  public ExpenseCategory setServerId(java.lang.String serverId) {
    this.serverId = serverId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSubcategory() {
    return subcategory;
  }

  /**
   * @param subcategory subcategory or {@code null} for none
   */
  public ExpenseCategory setSubcategory(java.lang.String subcategory) {
    this.subcategory = subcategory;
    return this;
  }

  @Override
  public ExpenseCategory set(String fieldName, Object value) {
    return (ExpenseCategory) super.set(fieldName, value);
  }

  @Override
  public ExpenseCategory clone() {
    return (ExpenseCategory) super.clone();
  }

}
