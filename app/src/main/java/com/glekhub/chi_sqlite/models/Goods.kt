package com.glekhub.chi_sqlite.models

data class Goods(
    val productTitle: String,   //coffee, lemonade, cake, cookie
    val productSize: String,    //S, M, L
    val productPrice: Int
) {
    companion object {
        const val TABLE = "Goods"
        const val GOODS_TITLE = "goods_title"
        const val GOODS_SIZE = "goods_size"
        const val GOODS_PRICE = "goods_price"

        const val SQL_CREATE_GOODS =
            "CREATE TABLE $TABLE (" +
                    "$GOODS_TITLE TEXT NOT NULL," +
                    "$GOODS_SIZE TEXT NOT NULL," +
                    "$GOODS_PRICE INTEGER NOT NULL);"

        const val SQL_DELETE_GOODS = "DROP TABLE IF EXISTS $TABLE"
    }
}