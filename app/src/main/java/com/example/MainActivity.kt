package com.example

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
            val readerTheme by viewModel.readerTheme.collectAsState()
            val fontFamilyName by viewModel.fontFamily.collectAsState()
            val fontSizeSp by viewModel.fontSizeSp.collectAsState()

            TorahIaTheme(
                readerTheme = readerTheme,
                fontFamilyName = fontFamilyName,
                fontSizeSp = fontSizeSp
            ) {
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
    val currentUser by viewModel.currentUser.collectAsState()

    // Si el usuario no tiene sesión activa, redirigir inmediatamente a Login para proteger todos los módulos
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            val currentRoute = navController.currentDestination?.route
            if (currentRoute != null && currentRoute != Screen.Login.route && currentRoute != Screen.Register.route) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    AppNavigationWrapper(
        navController = navController,
        viewModel = viewModel
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = if (viewModel.isUserAuthenticated()) Screen.Home.route else Screen.Login.route,
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
                    onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                    onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(Screen.Login.route) {
                LoginScreen(
                    viewModel = viewModel,
                    canNavigateBack = navController.previousBackStackEntry != null && currentUser != null,
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    },
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateBack = {
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        }
                    }
                )
            }

            composable(Screen.Register.route) {
                RegisterScreen(
                    viewModel = viewModel,
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    },
                    onRegisterSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    },
                    onNavigateBack = {
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        } else {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
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
