package com.iluwatar.ambassador;

/**
 * Holds information regarding the status of the Remote Service.
 *
 * !Attention - This Enum replaces the integer value previously stored in {@link RemoteServiceInterface}
 * as SonarCloud was identifying it as an issue. All test cases have been checked after changes, without failures.
 */

public enum RemoteServiceStatus {
    FAILURE(-1)
    ;

    private final long remoteServiceStatusValue;

    RemoteServiceStatus(long remoteServiceStatusValue) {
        this.remoteServiceStatusValue = remoteServiceStatusValue;
    }

    public long getRemoteServiceStatusValue() {
        return remoteServiceStatusValue;
    }
}
