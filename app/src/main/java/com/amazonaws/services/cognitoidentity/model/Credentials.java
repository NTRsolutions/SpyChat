/*
 * Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 * 
 *  http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;

/**
 * <p>
 * Credentials for the provided identity ID.
 * </p>
 */
public class Credentials implements Serializable {

    /**
     * The Access Key portion of the credentials.
     */
    private String accessKeyId;

    /**
     * The Secret Access Key portion of the credentials
     */
    private String secretKey;

    /**
     * The Session Token portion of the credentials
     */
    private String sessionToken;

    /**
     * The date at which these credentials will expire.
     */
    private java.util.Date expiration;

    /**
     * The Access Key portion of the credentials.
     *
     * @return The Access Key portion of the credentials.
     */
    public String getAccessKeyId() {
        return accessKeyId;
    }
    
    /**
     * The Access Key portion of the credentials.
     *
     * @param accessKeyId The Access Key portion of the credentials.
     */
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    
    /**
     * The Access Key portion of the credentials.
     * <p>
     * Returns a reference to this object so that method calls can be chained together.
     *
     * @param accessKeyId The Access Key portion of the credentials.
     *
     * @return A reference to this updated object so that method calls can be chained
     *         together.
     */
    public Credentials withAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    /**
     * The Secret Access Key portion of the credentials
     *
     * @return The Secret Access Key portion of the credentials
     */
    public String getSecretKey() {
        return secretKey;
    }
    
    /**
     * The Secret Access Key portion of the credentials
     *
     * @param secretKey The Secret Access Key portion of the credentials
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    
    /**
     * The Secret Access Key portion of the credentials
     * <p>
     * Returns a reference to this object so that method calls can be chained together.
     *
     * @param secretKey The Secret Access Key portion of the credentials
     *
     * @return A reference to this updated object so that method calls can be chained
     *         together.
     */
    public Credentials withSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    /**
     * The Session Token portion of the credentials
     *
     * @return The Session Token portion of the credentials
     */
    public String getSessionToken() {
        return sessionToken;
    }
    
    /**
     * The Session Token portion of the credentials
     *
     * @param sessionToken The Session Token portion of the credentials
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    
    /**
     * The Session Token portion of the credentials
     * <p>
     * Returns a reference to this object so that method calls can be chained together.
     *
     * @param sessionToken The Session Token portion of the credentials
     *
     * @return A reference to this updated object so that method calls can be chained
     *         together.
     */
    public Credentials withSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
        return this;
    }

    /**
     * The date at which these credentials will expire.
     *
     * @return The date at which these credentials will expire.
     */
    public java.util.Date getExpiration() {
        return expiration;
    }
    
    /**
     * The date at which these credentials will expire.
     *
     * @param expiration The date at which these credentials will expire.
     */
    public void setExpiration(java.util.Date expiration) {
        this.expiration = expiration;
    }
    
    /**
     * The date at which these credentials will expire.
     * <p>
     * Returns a reference to this object so that method calls can be chained together.
     *
     * @param expiration The date at which these credentials will expire.
     *
     * @return A reference to this updated object so that method calls can be chained
     *         together.
     */
    public Credentials withExpiration(java.util.Date expiration) {
        this.expiration = expiration;
        return this;
    }

    /**
     * Returns a string representation of this object; useful for testing and
     * debugging.
     *
     * @return A string representation of this object.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAccessKeyId() != null) sb.append("AccessKeyId: " + getAccessKeyId() + ",");
        if (getSecretKey() != null) sb.append("SecretKey: " + getSecretKey() + ",");
        if (getSessionToken() != null) sb.append("SessionToken: " + getSessionToken() + ",");
        if (getExpiration() != null) sb.append("Expiration: " + getExpiration() );
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;
        
        hashCode = prime * hashCode + ((getAccessKeyId() == null) ? 0 : getAccessKeyId().hashCode()); 
        hashCode = prime * hashCode + ((getSecretKey() == null) ? 0 : getSecretKey().hashCode()); 
        hashCode = prime * hashCode + ((getSessionToken() == null) ? 0 : getSessionToken().hashCode()); 
        hashCode = prime * hashCode + ((getExpiration() == null) ? 0 : getExpiration().hashCode()); 
        return hashCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (obj instanceof Credentials == false) return false;
        Credentials other = (Credentials)obj;
        
        if (other.getAccessKeyId() == null ^ this.getAccessKeyId() == null) return false;
        if (other.getAccessKeyId() != null && other.getAccessKeyId().equals(this.getAccessKeyId()) == false) return false; 
        if (other.getSecretKey() == null ^ this.getSecretKey() == null) return false;
        if (other.getSecretKey() != null && other.getSecretKey().equals(this.getSecretKey()) == false) return false; 
        if (other.getSessionToken() == null ^ this.getSessionToken() == null) return false;
        if (other.getSessionToken() != null && other.getSessionToken().equals(this.getSessionToken()) == false) return false; 
        if (other.getExpiration() == null ^ this.getExpiration() == null) return false;
        if (other.getExpiration() != null && other.getExpiration().equals(this.getExpiration()) == false) return false; 
        return true;
    }
    
}
    