package com.glekhub.chi_sqlite.models


data class Category(
    val categoryId: Int,
    val categoryTitle: String   //hot drink, cold drink, meal, snack
) {
    companion object {
        const val TABLE = "Category"
        const val CATEGORY_ID = "_id"
        const val CATEGORY_TITLE = "category_title"

        const val SQL_CREATE_CATEGORY =
            "CREATE TABLE $TABLE (" +
                    "$CATEGORY_ID INTEGER PRIMARY KEY," +
                    "$CATEGORY_TITLE TEXT NOT NULL);"

        const val SQL_DELETE_CATEGORY = "DROP TABLE IF EXISTS $TABLE"
    }
}