package `in`.divu.wishlistapp2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishTable")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo(name = "wishTitle")
    val title :String ="",
    @ColumnInfo(name = "wishDesc")
    val description :String =""

)


