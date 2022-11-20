package com.glekhub.chi_sqlite.models


data class User(
    val userId: Int,
    val userNumber: Int,
    val userName: String
) {
    companion object {
        const val TABLE = "User"
        const val USER_ID = "_id"
        const val USER_NUMBER = "user_number"
        const val USER_NAME = "user_name"
        const val DEFAULT_PASS = "default_pass"

        const val SQL_CREATE_USER =
            "CREATE TABLE $TABLE (" +
                    "$USER_ID INTEGER PRIMARY KEY," +
                    "$USER_NUMBER INTEGER NOT NULL," +
                    "$USER_NAME TEXT NOT NULL);"

        const val SQL_DELETE_USER = "DROP TABLE IF EXISTS $TABLE"
    }
}