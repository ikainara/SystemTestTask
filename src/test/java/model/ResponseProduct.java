package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseProduct extends RequestProduct {
    @Getter
    private String id;
    private String created;
    private String modified;

    public boolean isSameAsRequest(RequestProduct requestProduct) {
        return name.equals(requestProduct.getName()) && price.equals(requestProduct.getPrice())
                && itemCount.equals(requestProduct.getItemCount()) && active == requestProduct.getActive();
    }
}
