package cc.vivp.bankrupt.model.api;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferCommand {

  @NotBlank
  String source;
  @NotBlank
  String target;
  @NotNull
  @DecimalMin(value = "0.00", inclusive = false)
  BigDecimal amount;
  String description;

}
