package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 426 Upgrade Required exception.
 */
public class UpgradeRequiredException extends HttpException {

    public UpgradeRequiredException() {
        super(ExtendedStatus.UPGRADE_REQUIRED);
    }

    public UpgradeRequiredException(String message) {
        super(ExtendedStatus.UPGRADE_REQUIRED, message);
    }

    public UpgradeRequiredException(String message, Throwable cause) {
        super(ExtendedStatus.UPGRADE_REQUIRED, message, cause);
    }

    public UpgradeRequiredException(Throwable cause) {
        super(ExtendedStatus.UPGRADE_REQUIRED, cause);
    }
}
