package com.payvyne.merchant.domain.common;

import java.time.LocalDateTime;
import java.util.TimeZone;
import org.springframework.stereotype.Component;

@Component
public class TimeSource {

  public LocalDateTime now() {
    return LocalDateTime.now(TimeZone.getDefault().toZoneId());
  }
}
