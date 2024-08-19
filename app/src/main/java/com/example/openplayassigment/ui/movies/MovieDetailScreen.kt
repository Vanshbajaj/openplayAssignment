package com.example.openplayassigment.ui.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.openplayassigment.data.local.MovieEntity
import com.example.openplayassigment.ui.movies.viewmodel.MovieDetailViewModel

@Composable
fun MovieDetailScreen(movieId: String, navController: NavHostController) {
    val viewModel: MovieDetailViewModel = hiltViewModel()
    val movie by viewModel.movie.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Trigger the loadMovieDetails only when movieId changes
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    Scaffold(modifier = Modifier.fillMaxWidth()) { innerPadding ->
        if (isLoading) {
            // Display a loading indicator or placeholder
            LoadingScreen()
        } else if (error != null) {
            // Show error message
            Text(text = "Error: $error", modifier = Modifier.padding(innerPadding))
        } else {
            if (movie != null) {
                Column(modifier = Modifier.padding(innerPadding)) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                    MovieDetailScreen1(
                        movie!!,
                        modifier = Modifier,
                        navController
                    )
                }
            } else {
                Text(text = "Movie not found", modifier = Modifier.padding(innerPadding))
            }
        }
    }
}


@Composable
fun MovieDetailScreen1(movie: MovieEntity, modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier.padding(16.dp)) {
        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        AsyncImage(
            model = imageUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 24.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Release Date: ${movie.releaseDate}",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rating: ${movie.voteAverage}/10",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Optional, to make the background color white
        contentAlignment = Alignment.Center // Center the content inside the Box
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }
}

