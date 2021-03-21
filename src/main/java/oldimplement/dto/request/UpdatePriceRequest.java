package oldimplement.dto.request;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class UpdatePriceRequest {

    @NotNull
    private long productId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal newPrice;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }
}
