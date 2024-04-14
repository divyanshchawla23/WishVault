package `in`.divu.wishlistapp2

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import `in`.divu.wishlistapp2.data.Wish


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = { AppBarView(title = "WishVault") },

        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all =20.dp),
                contentColor = Color.Black,
                backgroundColor = colorResource (id = R.color.app_bar_color),
                onClick = {
                    navController.navigate(Screen.AddScreen.route +"/0L")

                }) {

                Icon(imageVector =Icons.Default.Add , contentDescription =null )

            }
        }




    ) {
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            items(wishlist.value,key={wish-> wish.id}){
                wish->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd ||it == DismissValue.DismissedToStart ){
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )



                SwipeToDismiss(modifier =Modifier.padding(it), state = dismissState,
                    background ={
                                val color by animateColorAsState(
                                    if(dismissState.dismissDirection==DismissDirection.EndToStart)
                                    Color.Red else Color.Transparent,
                                    label = ""
                                )


                        val alignment = Alignment.CenterEnd

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 10.dp),
                            contentAlignment = alignment){
                            Icon(Icons.Default.Delete, contentDescription = "",tint = Color.White)
                        }

                    } ,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(1.5f)},
                    dismissContent = {
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route +"/$id")

                        }
                    }
                )

            }

        }
    }
}



@Composable
fun WishItem(wish : Wish, onClick:() -> Unit){

    Spacer(modifier = Modifier.height(90.dp))
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable {
            onClick()
        },shape = RoundedCornerShape(25),
        elevation = 15.dp,
        backgroundColor = Color.White
    ){
        Column(modifier =Modifier.padding(16.dp)) {
            Text (wish.title,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif )

            Text(wish.description,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif)

        }

    }

}


