package `in`.divu.wishlistapp2

import android.content.Context
import androidx.room.Room
import `in`.divu.wishlistapp2.data.WishDataBase
import `in`.divu.wishlistapp2.data.WishRepository

object Graph {
    lateinit var dataBase: WishDataBase

    val wishRepository by lazy {
        WishRepository(wishDAO = dataBase.wishdao())
    }

    fun provide(context: Context){
        dataBase= Room.databaseBuilder(context
            , WishDataBase::class.java
            ,"wishlist.db")
            .build()
    }
}