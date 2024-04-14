package `in`.divu.wishlistapp2.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDAO: WishDAO) {

    suspend fun addAWish(wish: Wish){
        wishDAO.addAWish(wish)
    }

    fun getWishes(): Flow<List<Wish>> = wishDAO.getAllWishes()

    fun getAWishByID(id :Long): Flow<Wish> {
        return wishDAO.getAWishByID(id)
    }

    suspend fun updateAWish(wish: Wish){
        wishDAO.updateWish(wish)
    }

    suspend fun deleteAWish(wish: Wish){
        wishDAO.deleteWish(wish)
    }


}