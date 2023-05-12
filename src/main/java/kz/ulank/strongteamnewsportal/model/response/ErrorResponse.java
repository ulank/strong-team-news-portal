package kz.ulank.strongteamnewsportal.model.response;

import java.time.Instant;

/**
 * Created by Ulan on 5/12/2023
 */
public record ErrorResponse(
        String code,
        String message,
        Instant timestamp
) {
}
