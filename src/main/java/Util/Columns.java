package Util;

public enum Columns {
    //Primary keys
    PRODUCT_CODE("product_code"), WORKSHOP_ID("workshop_id"), CONTRACT_NUMBER("contract_code"), ADDRESS_ID("id"), ORDER_ID("order_id"),
    //contracts
    CONTRACT_NAME("name"), CONTRACT_DATE("regDate"), COMPLETION_DATE("doneDate"), ADDRESS_CITY("city"), ADDRESS_STREET("street"),
    //products
    PRODUCT_NAME("name"), PRODUCT_UNIT_PRICE("unit_price"), PRODUCTS_WORKSHOPS_ID("workshops_id"),
    //workshops
    WORKSHOPS_NAME("name"), WORKSHOPS_MANAGER("manager"),WORKSHOPS_PHONE("phoneNum"),
    //orders
    ORDERS_QUANTITY("quantity"), ORDERS_CONTRACT_NUMBER("contractNum"), ORDERS_PRODUCT_CODE("productCode");
    Columns(String col) {
        this.column = col;
    }
    private final String column;

    public String getColumnName() {
        return column;
    }
}
