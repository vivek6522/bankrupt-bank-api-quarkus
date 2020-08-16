package cc.vivp.bankrupt.model.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferReceipt {

  @Schema(example = "838ced71-7968-40e9-97ea-341a02590a2")
  String paymentReference;
  @Schema(example = "12345678901234")
  String source;
  @Schema(example = "1.23")
  String amount;
  @Schema(example = "98765432109876")
  String target;
  @Schema(example = "Transfer description")
  String description;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  LocalDateTime timestamp;
}
