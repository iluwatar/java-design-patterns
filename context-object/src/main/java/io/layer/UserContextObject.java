package io.layer;

/**
 * Define accessor and mutator operations to get/set layer's context information
 */
public class UserContextObject implements ContextObject{
    private String _endpoint;
    private String _userName;

    public void setEnvironmentUserName(String userName){
        this._userName = userName;
    }

    @Override
    public String getEnvironmentUserName(){
        return this._userName;
    }

    public void setEndpoint(String endpoint){
        this._endpoint = endpoint;
    }

    @Override
    public String getEndpointInfo() {
        return this._endpoint;
    }
}
