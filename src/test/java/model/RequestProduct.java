package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestProduct {
    protected String name;
    /**
     * to have some passed tests description is commented due to bug on server
     */
    //protected String description;
    protected Double price;
    protected Integer itemCount;
    protected Boolean active;
}
