package `in`.divu.wishlistapp2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addAWish (wishEntity : Wish)

    //Loads all the wishes from the table
    @Query("SELECT * FROM `wishTable`")
    abstract fun getAllWishes (): Flow<List<Wish>>


    @Update
    abstract  suspend fun updateWish (wishEntity : Wish)

    @Delete
    abstract  suspend fun deleteWish (wishEntity : Wish)

    @Query("SELECT * FROM `wishTable` WHERE id = :id")
    abstract fun getAWishByID (id :Long): Flow<Wish>


}