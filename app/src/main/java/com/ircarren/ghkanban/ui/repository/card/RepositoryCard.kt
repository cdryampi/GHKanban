package com.ircarren.ghkanban.ui.repository.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.ui.viewModel.RepoLocalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardRepo(
    repo: Repository,
    navController: NavController,
    onClick: (String) -> Unit,
    isFavorite: Boolean = true
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            navController.navigate("issues/${"cdryampi"}/${repo.name}")
            //navController.navigate("issues")
        }
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            var formatedName = repo.name?.replace("[", " ") ?: ""
            formatedName = formatedName?.replace("]", " ").toString()



            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(text = formatedName, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "yampi", style = MaterialTheme.typography.bodyLarge)
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Box(modifier = Modifier.size(64.dp)) {
                    if (isFavorite) {
                        Icon(imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .clickable {
                                    onClick(repo.name)
                                })
                    } else {
                        Icon(imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .clickable {
                                    onClick(repo.name)
                                })
                    }
                }
            }
        }
    }


}
