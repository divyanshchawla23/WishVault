package `in`.divu.wishlistapp2

sealed class Screen (val route :String ){

    object HomeScreen : Screen("homescreen")
    object AddScreen : Screen("addscreen")
}