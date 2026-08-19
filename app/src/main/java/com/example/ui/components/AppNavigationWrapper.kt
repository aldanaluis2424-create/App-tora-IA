package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ui.navigation.Screen
import com.example.ui.viewmodel.TorahViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationWrapper(
    navController: NavHostController,
    viewModel: TorahViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val showReaderSettings by viewModel.showReaderSettings.collectAsState()
    var isTaskbarVisible by remember { mutableStateOf(true) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    // Compute Breadcrumb / Title & IsTopLevel
    val isTopLevelScreen = remember(currentRoute) {
        currentRoute == Screen.Home.route ||
                currentRoute == Screen.AlefatoList.route ||
                currentRoute == Screen.FeastsList.route ||
                currentRoute == Screen.CalendarList.route ||
                currentRoute == Screen.Translator.route ||
                currentRoute == Screen.Favorites.route
    }

    val screenTitle = remember(currentRoute) {
        when {
            currentRoute == Screen.Home.route -> "Torah IA — Estudio Bíblico"
            currentRoute == Screen.AlefatoList.route -> "Alefato Hebreo (22 Letras)"
            currentRoute.startsWith("letter_detail") -> "Detalle de Letra"
            currentRoute == Screen.FeastsList.route -> "Fiestas de Adonai (Moedim)"
            currentRoute.startsWith("feast_detail") -> "Detalle de la Festividad"
            currentRoute == Screen.CalendarList.route -> "Calendario Hebreo"
            currentRoute.startsWith("month_detail") -> "Detalle del Mes"
            currentRoute == Screen.Translator.route -> "Traductor & Gematría IA"
            currentRoute == Screen.Favorites.route -> "Mis Favoritos & Notas"
            else -> "Torah IA"
        }
    }

    val breadcrumbText = remember(currentRoute) {
        when {
            currentRoute == Screen.Home.route -> "Inicio"
            currentRoute == Screen.AlefatoList.route -> "Inicio > Alefato"
            currentRoute.startsWith("letter_detail") -> "Inicio > Alefato > Detalle"
            currentRoute == Screen.FeastsList.route -> "Inicio > Fiestas"
            currentRoute.startsWith("feast_detail") -> "Inicio > Fiestas > Detalle"
            currentRoute == Screen.CalendarList.route -> "Inicio > Calendario"
            currentRoute.startsWith("month_detail") -> "Inicio > Calendario > Detalle"
            currentRoute == Screen.Translator.route -> "Inicio > Traductor IA"
            currentRoute == Screen.Favorites.route -> "Inicio > Mis Favoritos"
            else -> "Inicio"
        }
    }

    val navigateToTab: (String) -> Unit = { targetRoute ->
        if (targetRoute == Screen.Home.route) {
            if (currentRoute != Screen.Home.route) {
                val popped = navController.popBackStack(Screen.Home.route, inclusive = false)
                if (!popped) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        } else {
            val detailPrefix = when (targetRoute) {
                Screen.AlefatoList.route -> "letter_detail"
                Screen.FeastsList.route -> "feast_detail"
                Screen.CalendarList.route -> "month_detail"
                else -> null
            }

            if (currentRoute == targetRoute) {
                // Already on this main screen
            } else if (detailPrefix != null && currentRoute.startsWith(detailPrefix)) {
                // On a detail screen of this module, pop back to main list
                val popped = navController.popBackStack(targetRoute, inclusive = false)
                if (!popped) {
                    navController.navigate(targetRoute) {
                        popUpTo(Screen.Home.route) { saveState = false }
                        launchSingleTop = true
                    }
                }
            } else {
                // Navigating from another module
                navController.navigate(targetRoute) {
                    popUpTo(Screen.Home.route) { saveState = false }
                    launchSingleTop = true
                }
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                // Drawer Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF1E2A38),
                                    Color(0xFF2D3E50)
                                )
                            )
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFD4AF37),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "א",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color(0xFF1E2A38)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Torah IA Studio",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Plataforma de Estudio de Raíces Hebreas",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Navigation Items inside Drawer
                val drawerItems = listOf(
                    Triple(Screen.Home.route, "Inicio", Icons.Default.Home),
                    Triple(Screen.AlefatoList.route, "Alefato Hebreo (22 Letras)", Icons.Default.Translate),
                    Triple(Screen.FeastsList.route, "Fiestas Judías (Moedim)", Icons.Default.Celebration),
                    Triple(Screen.CalendarList.route, "Calendario Hebreo", Icons.Default.CalendarMonth),
                    Triple(Screen.Translator.route, "Traductor & Gematría IA", Icons.Default.AutoAwesome),
                    Triple(Screen.Favorites.route, "Mis Favoritos & Notas", Icons.Default.Star)
                )

                drawerItems.forEach { (route, label, icon) ->
                    val isSelected = currentRoute == route
                    NavigationDrawerItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = isSelected,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navigateToTab(route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp))

                // Quick Settings item in Drawer
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.FormatSize, contentDescription = "Ajustes de Lectura") },
                    label = { Text("Ajustes de Lectura") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.openReaderSettings()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (isTopLevelScreen) {
                    CenterAlignedTopAppBar(
                        title = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = screenTitle,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = breadcrumbText,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } },
                                modifier = Modifier.testTag("open_drawer")
                            ) {
                                Icon(Icons.Default.Menu, contentDescription = "Menú")
                            }
                        },
                        actions = {},
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            },
            bottomBar = {
                val bottomNavItems = listOf(
                    Screen.Home.route to "Inicio",
                    Screen.AlefatoList.route to "Alefato",
                    Screen.FeastsList.route to "Fiestas",
                    Screen.CalendarList.route to "Calendario",
                    Screen.Translator.route to "Traductor"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Samsung-style Floating handle pill when taskbar is hidden
                    if (!isTaskbarVisible) {
                        Surface(
                            onClick = { isTaskbarVisible = true },
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            tonalElevation = 6.dp,
                            shadowElevation = 6.dp,
                            modifier = Modifier
                                .padding(bottom = 6.dp)
                                .testTag("expand_taskbar_handle")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(3.dp)
                                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Mostrar Barra",
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Barra Hebrea",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = isTaskbarVisible,
                        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                            tonalElevation = 8.dp,
                            shadowElevation = 8.dp,
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                                .fillMaxWidth(0.96f)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                // Samsung style top drag/collapse handle bar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { isTaskbarVisible = false }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(38.dp)
                                            .height(4.dp)
                                            .background(
                                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                                CircleShape
                                            )
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp, horizontal = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    bottomNavItems.forEach { (route, label) ->
                                        val isSelected = when (route) {
                                            Screen.Home.route -> currentRoute == Screen.Home.route
                                            Screen.AlefatoList.route -> currentRoute == Screen.AlefatoList.route || currentRoute.startsWith("letter_detail")
                                            Screen.FeastsList.route -> currentRoute == Screen.FeastsList.route || currentRoute.startsWith("feast_detail")
                                            Screen.CalendarList.route -> currentRoute == Screen.CalendarList.route || currentRoute.startsWith("month_detail")
                                            Screen.Translator.route -> currentRoute == Screen.Translator.route
                                            else -> currentRoute == route
                                        }

                                        val activeColor = MaterialTheme.colorScheme.primary
                                        val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)

                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(18.dp))
                                                .clickable {
                                                    navigateToTab(route)
                                                }
                                                .background(
                                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                                    else Color.Transparent
                                                )
                                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                                .testTag("nav_icon_${route}")
                                        ) {
                                            HebrewModuleIcon(route = route, isSelected = isSelected)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            content(paddingValues)

            if (showReaderSettings) {
                ReaderSettingsDialog(
                    viewModel = viewModel,
                    onDismiss = { viewModel.closeReaderSettings() }
                )
            }
        }
    }
}

@Composable
private fun HebrewModuleIcon(route: String, isSelected: Boolean) {
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    val color = if (isSelected) activeColor else inactiveColor

    when (route) {
        Screen.Home.route -> Icon(
            imageVector = Icons.Default.HistoryEdu,
            contentDescription = "Inicio / Torah",
            tint = color,
            modifier = Modifier.size(26.dp)
        )
        Screen.AlefatoList.route -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(26.dp)) {
                Text(
                    text = "א",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )
            }
        }
        Screen.FeastsList.route -> Icon(
            imageVector = Icons.Default.Flare,
            contentDescription = "Fiestas Judías",
            tint = color,
            modifier = Modifier.size(26.dp)
        )
        Screen.CalendarList.route -> Icon(
            imageVector = Icons.Default.NightsStay,
            contentDescription = "Calendario Hebreo",
            tint = color,
            modifier = Modifier.size(26.dp)
        )
        Screen.Translator.route -> Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = "Traductor / Guematría",
            tint = color,
            modifier = Modifier.size(26.dp)
        )
        else -> Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(26.dp)
        )
    }
}
