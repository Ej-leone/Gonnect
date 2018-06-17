package com.op.bt.beneficiarypayments.Data;

public enum DatabaseContents {

    DATABASE("bt.beneficiary.db1"),
    TABLE_PAYMENTS("payments"),

    LANGUAGE("language");


    private String name;


    /**
     * Constructs DatabaseContents.
     *
     * @param name name of this content for using in database.
     */
    DatabaseContents(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
