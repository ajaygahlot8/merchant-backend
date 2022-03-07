package com.payvyne.merchant.rest.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

  private final String jwt;
}
