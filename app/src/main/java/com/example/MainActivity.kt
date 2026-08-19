package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.components.AppNavigationWrapper
import com.example.ui.navigation.Screen
import com.example.ui.screens.*
import com.example.ui.theme.TorahIaTheme
import com.example.ui.viewmodel.TorahViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TorahViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TorahIaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TorahAppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun TorahAppNavigation(viewModel: TorahViewModel) {
    val navController = rememberNavController()

    AppNavigationWrapper(
        navController = navController,
        viewModel = viewModel
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigate = { route -> navController.navigate(route) }
                )
            }

            composable(Screen.AlefatoList.route) {
                AlefatoScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onSelectLetter = { letterId ->
                        navController.navigate(Screen.LetterDetail.createRoute(letterId))
                    }
                )
            }

            composable(
                route = Screen.LetterDetail.route,
                arguments = listOf(navArgument("letterId") { type = NavType.StringType })
            ) { backStackEntry ->
                val letterId = backStackEntry.arguments?.getString("letterId") ?: "alef"
                LetterDetailScreen(
                    letterId = letterId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.FeastsList.route) {
                FiestasScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onSelectFeast = { feastId ->
                        navController.navigate(Screen.FeastDetail.createRoute(feastId))
                    }
                )
            }

            composable(
                route = Screen.FeastDetail.route,
                arguments = listOf(navArgument("feastId") { type = NavType.StringType })
            ) { backStackEntry ->
                val feastId = backStackEntry.arguments?.getString("feastId") ?: "shabbat"
                FiestaDetailScreen(
                    feastId = feastId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Translator.route) {
                TraductorScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.CalendarList.route) {
                CalendarioScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onSelectMonth = { monthId ->
                        navController.navigate(Screen.MonthDetail.createRoute(monthId))
                    }
                )
            }

            composable(
                route = Screen.MonthDetail.route,
                arguments = listOf(navArgument("monthId") { type = NavType.StringType })
            ) { backStackEntry ->
                val monthId = backStackEntry.arguments?.getString("monthId") ?: "tishrei"
                MonthDetailScreen(
                    monthId = monthId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToRoute = { route -> navController.navigate(route) }
                )
            }
        }
    }
}
