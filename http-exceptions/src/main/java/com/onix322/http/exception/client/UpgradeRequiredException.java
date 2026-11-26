package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Upgrade Required exception.
 */
public class UpgradeRequiredException extends HttpException {

    public UpgradeRequiredException() {
        super(HttpStatusCode.UPGRADE_REQUIRED);
    }

    public UpgradeRequiredException(String message) {
        super(HttpStatusCode.UPGRADE_REQUIRED, message);
    }

    public UpgradeRequiredException(String message, Throwable cause) {
        super(HttpStatusCode.UPGRADE_REQUIRED, message, cause);
    }

    public UpgradeRequiredException(Throwable cause) {
        super(HttpStatusCode.UPGRADE_REQUIRED, cause);
    }
}
