package com.payvyne.merchant.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<D> {

  private boolean success;
  private D data;
  private ApiError error;

  public static ApiResponse<Void> createErrorResponse(final ApiError error) {
    return new ApiResponse<>(false, null, error);
  }

  public static <D> ApiResponse<D> createSuccessResponse(final D data) {
    return new ApiResponse<>(true, data, null);
  }

  public static <D> ApiResponse<D> createErrorResponseWithData(final D data) {
    return new ApiResponse<>(false, data, null);
  }
}
