package cc.vivp.bankrupt;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
  tags = {
    @Tag(name = "accounts", description = "Account operations"),
    @Tag(name = "transfers", description = "Transfer operations")
  },
  info = @Info(
    title = "Bankrupt Bank API",
    version = "latest",
    contact = @Contact(
      name = "Bankrupt Bank API Support",
      url = "http://bankrupt-bank.com/support",
      email = "support@bankrupt-bank.com"
    ),
    license = @License(
      name = "The Unlicense",
      url = "https://unlicense.org"
    )
  )
)
public class BankruptBankApplication extends Application {}
