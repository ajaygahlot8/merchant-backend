package com.payvyne.merchant.domain.common;

import java.time.OffsetDateTime;
import java.util.TimeZone;
import org.springframework.stereotype.Component;

@Component
public class TimeSource {

  public OffsetDateTime now() {
    return OffsetDateTime.now(TimeZone.getDefault().toZoneId());
  }
}
