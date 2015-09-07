package io.layer;

/**
 * Assure that context objects keep socket related info
 */
public interface ContextObject {
    void setEnvironmentUserName(String userName);
    String getEnvironmentUserName();
    void setEndpoint(String endpoint);
    String getEndpointInfo();
}
