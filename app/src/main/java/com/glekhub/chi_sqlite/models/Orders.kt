package com.glekhub.chi_sqlite.models


data class Orders(
    val orderDate: String,
    val userId: Int,
    val productId: Int
) {
    companion object {
        const val TABLE = "Orders"
        private const val ORDER_ID = "_id"
        const val ORDER_DATE = "order_date"
        const val USER_ID = "user_id"
        const val PRODUCT_ID = "product_id"

        const val SQL_CREATE_ORDER =
            "CREATE TABLE $TABLE (" +
                    "$ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$ORDER_DATE TEXT NOT NULL," +
                    "$USER_ID INTEGER," +
                    "$PRODUCT_ID INTEGER," +
                    "FOREIGN KEY ($USER_ID) REFERENCES ${User.TABLE} (${User.USER_ID})," +
                    "FOREIGN KEY ($PRODUCT_ID) REFERENCES ${Product.TABLE} (${Product.PRODUCT_ID}));"

        const val SQL_DELETE_ORDER = "DROP TABLE IF EXISTS $TABLE"
    }
}