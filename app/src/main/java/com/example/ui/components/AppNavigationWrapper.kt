package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.R
import com.example.ui.navigation.Screen
import com.example.ui.screens.GoldenMenorahIcon
import com.example.ui.viewmodel.TorahViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationWrapper(
    navController: NavHostController,
    viewModel: TorahViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current
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
            currentRoute == Screen.Home.route -> "Torah IA - Estudio Bíblico"
            currentRoute == Screen.AlefatoList.route -> "Alefato Hebreo (22 Letras)"
            currentRoute.startsWith("letter_detail") -> "Detalle de Letra"
            currentRoute == Screen.FeastsList.route -> "Fiestas Judías (Moedim)"
            currentRoute.startsWith("feast_detail") -> "Detalle de Festividad"
            currentRoute == Screen.CalendarList.route -> "Calendario Hebreo"
            currentRoute.startsWith("month_detail") -> "Detalle del Mes"
            currentRoute == Screen.Translator.route -> "Rabí-IA (Estudio & Traducción)"
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
            currentRoute == Screen.Translator.route -> "Inicio > Rabí-IA"
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
                val popped = navController.popBackStack(targetRoute, inclusive = false)
                if (!popped) {
                    navController.navigate(targetRoute) {
                        popUpTo(Screen.Home.route) { saveState = false }
                        launchSingleTop = true
                    }
                }
            } else {
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
                modifier = Modifier.width(300.dp),
                drawerContainerColor = Color(0xFFFAF7F0)
            ) {
                // Drawer Header with Hebrew Letters Imagery
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hebrew_letters_1787603329352),
                        contentDescription = "Letras Hebreas",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0x661E2A38),
                                        Color(0xE60D1B2A),
                                        Color(0xF50D1B2A)
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(18.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFD4AF37),
                            modifier = Modifier.size(44.dp),
                            shadowElevation = 4.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "א",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E2A38)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Torah IA Studio",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Estudio y Raíces Hebreas con IA",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.85f)
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
                    Triple(Screen.Translator.route, "Rabí-IA (Chat & Traductor)", Icons.Default.AutoAwesome),
                    Triple(Screen.Favorites.route, "Mis Notas & Marcadores", Icons.Default.EditNote)
                )

                drawerItems.forEach { (route, label, icon) ->
                    val isSelected = currentRoute == route
                    NavigationDrawerItem(
                        icon = { Icon(icon, contentDescription = label, tint = Color(0xFF8B672B)) },
                        label = { Text(label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
                        selected = isSelected,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navigateToTab(route)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFFEAD8B2),
                            selectedTextColor = Color(0xFF382705),
                            unselectedTextColor = Color(0xFF4A4235)
                        )
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE0D2BC))

                // Quick Settings item in Drawer
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.FormatSize, contentDescription = "Ajustes de Lectura", tint = Color(0xFF8B672B)) },
                    label = { Text("Ajustes de Lectura", fontWeight = FontWeight.Medium) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.openReaderSettings()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color(0xFF4A4235)
                    )
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (isTopLevelScreen) {
                    // Header estilo Mármol / Pergamino con Menorá Dorada (como en imagen de referencia)
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFF5EFE3),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                        border = BorderStroke(1.dp, Color(0xFFE2D5BE)),
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Botón de Menú con icono de hamburguesa claro y Menorá
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFEDE2CE),
                                border = BorderStroke(1.dp, Color(0xFFD6C2A0)),
                                modifier = Modifier
                                    .clickable {
                                        scope.launch {
                                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                        }
                                    }
                                    .testTag("open_drawer")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Abrir Menú Principal",
                                        tint = Color(0xFF5A3E12),
                                        modifier = Modifier.size(22.dp)
                                    )
                                    GoldenMenorahIcon(size = 22.dp)
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = screenTitle,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF261A07),
                                    fontFamily = FontFamily.Serif
                                )
                                Text(
                                    text = breadcrumbText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF7A684C)
                                )
                            }

                            // Botón de Ajustes de Lectura en Header
                            IconButton(
                                onClick = { viewModel.openReaderSettings() },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("open_reader_settings")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatSize,
                                    contentDescription = "Ajustes de Lectura",
                                    tint = Color(0xFF8B672B),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                // Barra Inferior Flotante Metálica Dorada estilo Lujo Bíblico
                val navTabs = listOf(
                    NavigationTabItem(
                        id = "inicio",
                        label = "Inicio",
                        icon = Icons.Default.Home,
                        route = Screen.Home.route
                    ),
                    NavigationTabItem(
                        id = "texto",
                        label = "Alefato",
                        route = Screen.AlefatoList.route,
                        isHebrewText = true
                    ),
                    NavigationTabItem(
                        id = "estrella",
                        label = "Fiestas",
                        icon = Icons.Default.Flare,
                        route = Screen.FeastsList.route
                    ),
                    NavigationTabItem(
                        id = "sol_luna",
                        label = "Calendario",
                        icon = Icons.Default.NightsStay,
                        route = Screen.CalendarList.route
                    ),
                    NavigationTabItem(
                        id = "ajustes",
                        label = "Rabí",
                        icon = Icons.Default.AutoAwesome,
                        route = Screen.Translator.route
                    ),
                    NavigationTabItem(
                        id = "comentario",
                        label = "Notas",
                        icon = Icons.Default.EditNote,
                        route = Screen.Favorites.route
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!isTaskbarVisible) {
                        Surface(
                            onClick = { isTaskbarVisible = true },
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFF8B672B),
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
                                        .background(Color(0xFFFFDF7A), CircleShape)
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Mostrar Barra",
                                    modifier = Modifier.size(16.dp),
                                    tint = Color(0xFFFFDF7A)
                                )
                                Text(
                                    text = "Barra Hebrea",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
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
                            shape = RoundedCornerShape(26.dp),
                            color = Color.Transparent,
                            border = BorderStroke(1.2.dp, Color(0xFFC79E3E)),
                            shadowElevation = 10.dp,
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .fillMaxWidth(0.98f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Color(0xFF8D6827),
                                                Color(0xFF5A3E12)
                                            )
                                        )
                                    )
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    // Handle superior para colapsar
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { isTaskbarVisible = false }
                                            .padding(top = 6.dp, bottom = 2.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(36.dp)
                                                .height(3.5.dp)
                                                .background(
                                                    Color(0xFFE8D3A0).copy(alpha = 0.5f),
                                                    CircleShape
                                                )
                                        )
                                    }

                                    // Fila de 6 botones de navegación dorados (distribución equitativa para evitar apilamiento de texto)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp, vertical = 3.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        navTabs.forEach { tab ->
                                            val isSelected = when (tab.route) {
                                                null -> drawerState.isOpen
                                                Screen.Home.route -> currentRoute == Screen.Home.route && drawerState.isClosed
                                                Screen.AlefatoList.route -> (currentRoute == Screen.AlefatoList.route || currentRoute.startsWith("letter_detail")) && drawerState.isClosed
                                                Screen.FeastsList.route -> (currentRoute == Screen.FeastsList.route || currentRoute.startsWith("feast_detail")) && drawerState.isClosed
                                                Screen.CalendarList.route -> (currentRoute == Screen.CalendarList.route || currentRoute.startsWith("month_detail")) && drawerState.isClosed
                                                Screen.Translator.route -> currentRoute == Screen.Translator.route && drawerState.isClosed
                                                Screen.Favorites.route -> currentRoute == Screen.Favorites.route && drawerState.isClosed
                                                else -> currentRoute == tab.route && drawerState.isClosed
                                            }

                                            // Cápsula activa dorada brillante o botón limpio
                                            val itemModifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(14.dp))
                                                .then(
                                                    if (isSelected) {
                                                        Modifier.background(
                                                            Brush.verticalGradient(
                                                                listOf(
                                                                    Color(0xFFF1DEAE),
                                                                    Color(0xFFCBA03E)
                                                                )
                                                            )
                                                        )
                                                    } else {
                                                        Modifier
                                                    }
                                                )
                                                .clickable {
                                                    if (tab.route != null) {
                                                        if (drawerState.isOpen) {
                                                            scope.launch { drawerState.close() }
                                                        }
                                                        navigateToTab(tab.route)
                                                    } else {
                                                        scope.launch {
                                                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                                        }
                                                    }
                                                }
                                                .padding(horizontal = 2.dp, vertical = 4.dp)

                                            Column(
                                                modifier = itemModifier.testTag("nav_tab_${tab.id}"),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                val iconColor = if (isSelected) Color(0xFF332306) else Color(0xFFE5CE9F)
                                                val textColor = if (isSelected) Color(0xFF332306) else Color(0xFFE5CE9F)

                                                if (tab.isHebrewText) {
                                                    Text(
                                                        text = "א",
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Black,
                                                        color = iconColor
                                                    )
                                                } else if (tab.icon != null) {
                                                    Icon(
                                                        imageVector = tab.icon,
                                                        contentDescription = tab.label,
                                                        tint = iconColor,
                                                        modifier = Modifier.size(19.dp)
                                                    )
                                                }

                                                Text(
                                                    text = tab.label,
                                                    fontSize = 9.5.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                    color = textColor,
                                                    maxLines = 1,
                                                    softWrap = false,
                                                    overflow = TextOverflow.Ellipsis,
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
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
}

private data class NavigationTabItem(
    val id: String,
    val label: String,
    val icon: ImageVector? = null,
    val route: String? = null,
    val isHebrewText: Boolean = false
)

