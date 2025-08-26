import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.gitsearch.R
import com.example.gitsearch.model.UserData
import com.example.gitsearch.viewmodel.UsersViewModel


@Composable
fun UsersScreen(viewModel: UsersViewModel = remember { UsersViewModel() }, onUserClick: (String) -> Unit) {
    val users by viewModel.users.collectAsState()
    val textState = remember { mutableStateOf(TextFieldValue("")) }

   Box(
       modifier = Modifier.fillMaxWidth()
   ){


       Column (
           modifier = Modifier.padding(5.dp, top = 35.dp)
       ){

           Row(
               modifier = Modifier
                   .align(Alignment.CenterHorizontally)
                   .padding(top = 15.dp, bottom = 15.dp, end = 11.dp)
           ) {
               Image(
                   painter = painterResource(
                       id = R.drawable.light_logo),
                   contentDescription = "Logo",

               )

           }

           Text(
               "GitHub User Search",
               fontSize = 30.sp,
               fontWeight = FontWeight.Bold,
               modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 5.dp)
           )

           Row(
               modifier = Modifier.padding(top = 10.dp)
           ) {
               SearchView(
                   state = textState,
                   placeHolder = "Search",
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(8.dp)
               ) { query ->
                   if (query.isNotEmpty()) {
                       viewModel.searchUsers(query, reset = true)
                   }
               }
           }

           LazyColumn {
               itemsIndexed(users) { index, user ->
                   UserItem(user = user, onClick = { onUserClick(user.login) })

                   if (index == users.lastIndex && users.isNotEmpty()) {
                       viewModel.searchUsers(viewModel.currentQuery, reset = false)
                   }
               }
           }
       }
   }
}


@Composable
fun UserItem(user: UserData, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.avatar_url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(user.login, fontSize = 20.sp)
        }
    }
}


@Composable
fun SearchView(
    state: MutableState<TextFieldValue>,
    placeHolder: String,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
){
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = state.value,
        onValueChange = { value -> state.value = value },
        placeholder = { Text(placeHolder) },
        modifier = modifier,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onSearch(state.value.text)
            }
        ),
        shape = RoundedCornerShape(16.dp)
    )


}
