package `in`.divu.wishlistapp2

import android.app.Application

class WishListApp: Application() {
    override fun onCreate(){
        super.onCreate()
        Graph.provide(this)
    }
}