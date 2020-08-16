package cc.vivp.bankrupt.model.api;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferCommand {

  @Schema(example = "12345678901234")
  @NotBlank
  String source;
  @Schema(example = "98765432109876")
  @NotBlank
  String target;
  @Schema(example = "1.23")
  @NotNull
  @DecimalMin(value = "0.00", inclusive = false)
  BigDecimal amount;
  @Schema(example = "Transfer description")
  String description;

}
