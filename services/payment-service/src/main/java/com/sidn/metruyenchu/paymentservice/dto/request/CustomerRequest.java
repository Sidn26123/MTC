package com.sidn.metruyenchu.paymentservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class CustomerRequest {
    @NotNull
    String id;
    @NotNull
    @Email
    String email;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
}