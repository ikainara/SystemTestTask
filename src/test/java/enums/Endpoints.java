package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoints {
    ADD_PRODUCT("/product"),
    CLEAR_DB("/clearDb");
    private final String path;
}
