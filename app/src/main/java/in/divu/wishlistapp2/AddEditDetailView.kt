package `in`.divu.wishlistapp2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import `in`.divu.wishlistapp2.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(id : Long ,
                      viewModel: WishViewModel,
                      navController: NavController)
{

    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    if(id !=0L){
        val wish =viewModel.getAWishByID(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.wishTitleState=wish.value.title
        viewModel.wishDescriptionState=wish.value.description
    }
    else{
        viewModel.wishTitleState= ""
        viewModel.wishDescriptionState= ""
    }

    Scaffold(
        topBar = { AppBarView(title =
        if (id !=0L) "Update Wish"
        else "Add Wish" ){navController.navigateUp()}
        },
        scaffoldState = scaffoldState
    )

    {
        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center


        ) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Title", value = viewModel.wishTitleState,
                onValueChanged = { viewModel.onWishTitleChanged(it) } )

            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Description", value = viewModel.wishDescriptionState,
                onValueChanged = { viewModel.onWishDescriptionChanged(it) } )

            Spacer(modifier = Modifier.height(10.dp))

            Button(modifier = Modifier.width(200.dp), shape = RoundedCornerShape(100),
                onClick = {
                    if (viewModel.wishTitleState.isNotEmpty() &&
                        viewModel.wishDescriptionState.isNotEmpty()){

                        if(id != 0L){
                            viewModel.updateWish(Wish(id , viewModel.wishTitleState.trim(),viewModel.wishDescriptionState))
                            snackMessage.value="Wish has been updated"
                        }else{
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()

                                )
                            )
                            snackMessage.value="Wish has been created"
                        }

                    }else{
                        snackMessage.value = "Enter fields to create a wish"
                    }

                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                        navController.navigateUp()
                    }
                }){

                Text(if (id !=0L) "Update Wish" else "Add Wish" )

            }


        }
    }

}


@Composable
fun WishTextField (
    label :String,
    value : String,
    onValueChanged: (String) -> Unit
){

    OutlinedTextField(value = value, onValueChange = onValueChanged,shape = RoundedCornerShape(25),
        label= { Text(text=label, color=Color.Black) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = colorResource(id = R.color.app_bar_color),
            unfocusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor  = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),

            )
    )


}


