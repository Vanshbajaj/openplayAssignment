package com.example.openplayassigment.ui.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.data.response.MovieDataClass
import com.example.openplayassigment.data.response.Search
import com.example.openplayassigment.ui.movies.viewmodel.MovieListViewModel
import com.example.openplayassigment.ui.theme.OpenPlayAssigmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenPlayAssigmentTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxWidth()) { innerPadding ->
                    NavGraph(navController = navController, innerPadding)
                }
            }
        }
    }
}


@Composable
fun MovieListScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel: MovieListViewModel = hiltViewModel()
    val movies by viewModel.movies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = modifier) {
        SearchBar(
            query = viewModel.searchQuery.collectAsState().value,
            onQueryChange = { viewModel.onSearchQueryChanged(it) }
        )
        when {
            isLoading -> {
                Text(text = "Loading...", modifier = Modifier.padding(16.dp))
            }
            error != null -> {
                Text(text = "Type Three Letter At least", modifier = Modifier.padding(16.dp))
            }
            movies.isEmpty() -> {
                Text(text = "No movies found", modifier = Modifier.padding(16.dp))
            }
            else -> {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(movies) { movie ->
                        MovieItem(movie = movie) {
                            navController.navigate("movieDetail/${movie.title}")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search movies...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    )
}
@Composable
fun MovieItem(movie: Search, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .fillMaxWidth(), // Ensures the card takes the full width available
        elevation = CardDefaults.cardElevation(), // Adds shadow to give a lifted appearance
        shape = MaterialTheme.shapes.medium // Applies rounded corners
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp) // Padding inside the card
        ) {
            AsyncImage(
                model = movie.poster,
                contentDescription = movie.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium), // Applies rounded corners to the image
                contentScale = ContentScale.FillBounds // Crops the image to fill the size
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Year: ${movie.year}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Type: ${movie.type}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}



@Composable
fun NavGraph(navController: NavHostController, modifier: PaddingValues = PaddingValues()) {
    NavHost(navController = navController, startDestination = "movieList") {
        composable("movieList") {
            MovieListScreen(navController = navController, modifier = Modifier.padding(modifier))
        }
        composable("movieDetail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: return@composable
            MovieDetailScreen(movieId = movieId, navController = navController)
        }
    }
}


