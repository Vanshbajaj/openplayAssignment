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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    val movies by viewModel.filteredMovies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column {
        SearchBar(
            query = viewModel.searchQuery.collectAsState().value,
            onQueryChange = { viewModel.onSearchQueryChanged(it) }
        )
        if (isLoading) {
            // Show loading indicator
            Text(text = "Loading...", modifier = modifier.padding(16.dp))
        } else if (error != null) {
            // Show error message
            Text(text = "Error: $error", modifier = modifier.padding(16.dp))
        } else {
            // Show the list of movies
            LazyColumn(modifier = modifier.padding(16.dp)) {
                items(movies) { movie ->
                    MovieItem(movie = movie) {
                        navController.navigate("movieDetail/${movie.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    androidx.compose.material3.TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search movies...") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}


@Composable
fun MovieItem(movie: MovieEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick.invoke() } // Add clickable modifier
    ) {
        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

        AsyncImage(
            model = imageUrl,
            contentDescription = movie.title,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rating: ${movie.voteAverage} | Release Date: ${movie.releaseDate}",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp
            )
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


