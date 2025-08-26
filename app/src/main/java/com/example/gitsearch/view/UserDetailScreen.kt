package com.example.gitsearch.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun UserDetailScreen(
    viewModel: UsersViewModel = remember { UsersViewModel() },
    login: String,
    navController: NavHostController
) {
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


        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2a5286))
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                userInfo?.avatar_url?.let { avatar ->
                    AsyncImage(
                        model = avatar,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(userInfo?.name ?: "", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFFe5e0da))
                if (!userInfo?.location.isNullOrBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Location Icon",
                            tint = Color(0xFFe5e0da)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(userInfo?.location ?: "", fontSize = 16.sp, color = Color(0xFFe5e0da))
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoCard(
                        title = "Followers",
                        value = userInfo?.followers ?: 0,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "Following",
                        value = userInfo?.following ?: 0,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "Repos",
                        value = userInfo?.public_repos ?: 0,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                if (!userInfo?.bio.isNullOrBlank()) {
                    Text(userInfo?.bio ?: "", fontSize = 23.sp, color = Color(0xFFe5e0da))
                    Spacer(Modifier.height(15.dp))
                }


                Spacer(Modifier.height(15.dp))


            }
        }
    }
}


@Composable
fun InfoCard(title: String, value: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .wrapContentHeight(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFe5e0da))
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2a5286)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFF2a5286)
            )
        }
    }
}