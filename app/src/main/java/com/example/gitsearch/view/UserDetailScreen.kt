package com.example.gitsearch.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.gitsearch.viewmodel.UsersViewModel

@Composable
fun UserDetailScreen(viewModel: UsersViewModel = remember { UsersViewModel() }, login: String, navController: NavHostController) {
    val userInfo by viewModel.userInfo.collectAsState()

    LaunchedEffect(login) {
        viewModel.loadUserInfo(login)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {


        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "",
            modifier = Modifier
                .clickable { navController.popBackStack() }
                .padding(top = 30.dp)
        )

        Text(
            "Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp, bottom = 15.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        userInfo?.avatar_url?.let { avatar ->
            AsyncImage(
                model = avatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(userInfo?.name ?: "", fontSize = 40.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(userInfo?.email ?: "", color = Color(0xE0D3D3D3))
        Spacer(modifier = Modifier.height(15.dp))

        Text(userInfo?.bio ?: "", fontSize = 30.sp)

        Spacer(Modifier.height(15.dp))

        Row {
            Icon(imageVector = Icons.Outlined.Person, contentDescription = "Person Icon")
            Spacer(Modifier.width(5.dp))
            Text(text = "${userInfo?.followers ?: 0} followers.${userInfo?.following ?: 0} following", fontSize = 20.sp)

        }

        Spacer(Modifier.height(15.dp))

        if (!userInfo?.location.isNullOrBlank()) {
            Row {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Location Icon"
                )
                Spacer(Modifier.width(5.dp))
                Text(userInfo?.location ?: "", fontSize = 20.sp)
            }
        }

        Spacer(Modifier.height(15.dp))



    }
}