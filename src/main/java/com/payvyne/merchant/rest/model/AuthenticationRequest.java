package com.payvyne.merchant.rest.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {

  @NonNull private final String username;
  @NonNull private final String password;
}
