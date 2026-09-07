package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HebrewMonth
import com.example.data.remote.HebcalDateResult
import com.example.ui.components.GoldenMenorahIcon
import com.example.ui.viewmodel.TorahViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioScreen(
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit,
    onSelectMonth: (String) -> Unit
) {
    val months = viewModel.months
    val currentYearGregorian = Calendar.getInstance().get(Calendar.YEAR)
    val hebrewYear = currentYearGregorian + 3760

    val goldGradient = Brush.horizontalGradient(
        listOf(Color(0xFFD4AF37), Color(0xFFA67C1E))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F5EC)),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 90.dp, top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner Superior de Presentación
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EFE3)),
                border = BorderStroke(1.dp, Color(0xFFDAC6A2)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GoldenMenorahIcon(size = 36.dp)
                    Column {
                        Text(
                            text = "Calendario Hebreo Bíblico",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF261A07),
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Ciclo Luni-Solar • Fiestas & Estaciones Divinas",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF8A6C3A)
                        )
                    }
                }
            }
        }

        // Live Hebrew Year Calculator Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8)),
                border = BorderStroke(1.dp, Color(0xFFE4D8C4)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = Color(0xFF9E6B15)
                        )
                        Text(
                            text = "AÑO HEBREO ACTUAL",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9E6B15),
                            letterSpacing = 1.sp
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Año $hebrewYear (תשפ״ו)",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF261A07),
                                fontFamily = FontFamily.Serif
                            )
                            Text(
                                text = "Año Gregoriano $currentYearGregorian",
                                fontSize = 13.sp,
                                color = Color(0xFF6B583E)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(goldGradient, RoundedCornerShape(10.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Luni-Solar",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    HorizontalDivider(color = Color(0xFFE8DDC9))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFF9E6B15)
                        )
                        Text(
                            text = "Ajuste Metónico de 19 años: Intercala Adar II en años bisiestos (Shanah Me'uberet) para alinear las fiestas con las cosechas agrícolas.",
                            fontSize = 11.sp,
                            color = Color(0xFF5A482F),
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Conversor Interactivo Hebcal API
        item {
            HebcalInteractiveConverterCard(viewModel = viewModel)
        }

        // Título de Sección
        item {
            Text(
                text = "MÓDULOS DE LOS 13 MESES  📜",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8C6527),
                letterSpacing = 1.sp,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(top = 4.dp, start = 2.dp)
            )
        }

        // Tarjetas de los Meses
        items(months) { month ->
            MonthListCard(month = month, onClick = { onSelectMonth(month.id) })
        }
    }
}

@Composable
private fun MonthListCard(
    month: HebrewMonth,
    onClick: () -> Unit
) {
    val bronzeBadge = Brush.horizontalGradient(
        listOf(Color(0xFF8C6534), Color(0xFF5C3E1B))
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("month_card_${month.id}")
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8)),
        border = BorderStroke(1.dp, Color(0xFFE4D8C4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(bronzeBadge, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = month.monthNumberCivil.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = month.nameSpanish,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF261A07),
                        modifier = Modifier.weight(1f),
                        fontFamily = FontFamily.Serif
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = month.nameHebrew,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9E6B15)
                    )
                }

                Text(
                    text = "${month.gregorianApprox} • ${month.season}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF8A6C3A)
                )

                Text(
                    text = "Tribu: ${month.associatedTribe} | Letra: ${month.associatedLetter}",
                    fontSize = 11.sp,
                    color = Color(0xFF6B583E)
                )
            }

            Text(
                text = ">",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9E6B15)
            )
        }
    }
}

@Composable
fun HebcalInteractiveConverterCard(viewModel: TorahViewModel) {
    val cal = remember { Calendar.getInstance() }
    var selectedYear by remember { mutableStateOf(cal.get(Calendar.YEAR)) }
    var selectedMonth by remember { mutableStateOf(cal.get(Calendar.MONTH) + 1) }
    var selectedDay by remember { mutableStateOf(cal.get(Calendar.DAY_OF_MONTH)) }

    val convertedDate by viewModel.convertedHebcalDate.collectAsState()
    val isConverting by viewModel.isConvertingHebcal.collectAsState()

    val goldGradient = Brush.horizontalGradient(
        listOf(Color(0xFFD4AF37), Color(0xFFA67C1E))
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hebcal_converter_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8)),
        border = BorderStroke(1.dp, Color(0xFFE4D8C4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        tint = Color(0xFF9E6B15)
                    )
                    Text(
                        text = "CONVERSOR HEBCAL (API INTERACTIVA)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9E6B15),
                        letterSpacing = 1.sp
                    )
                }

                IconButton(
                    onClick = {
                        val now = Calendar.getInstance()
                        selectedYear = now.get(Calendar.YEAR)
                        selectedMonth = now.get(Calendar.MONTH) + 1
                        selectedDay = now.get(Calendar.DAY_OF_MONTH)
                        viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Fecha de Hoy",
                        tint = Color(0xFF9E6B15)
                    )
                }
            }

            Text(
                text = "Convierte cualquier fecha del calendario gregoriano al calendario hebreo en tiempo real consultando la API oficial de Hebcal.com:",
                fontSize = 12.sp,
                color = Color(0xFF6B583E),
                lineHeight = 16.sp
            )

            // Selectores de Día, Mes y Año
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Selector de Día
                Column(modifier = Modifier.weight(1f)) {
                    Text("Día", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF8A6C3A))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF7F2E7), RoundedCornerShape(10.dp))
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (selectedDay > 1) {
                                    selectedDay--
                                    viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                                }
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Text("-", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF8A6C3A))
                        }
                        Text("$selectedDay", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF261A07))
                        IconButton(
                            onClick = {
                                if (selectedDay < 31) {
                                    selectedDay++
                                    viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                                }
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Text("+", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF8A6C3A))
                        }
                    }
                }

                // Selector de Mes
                Column(modifier = Modifier.weight(1.2f)) {
                    Text("Mes", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF8A6C3A))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF7F2E7), RoundedCornerShape(10.dp))
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (selectedMonth > 1) {
                                    selectedMonth--
                                    viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                                }
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Text("-", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF8A6C3A))
                        }
                        val monthNamesEs = listOf("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")
                        Text(monthNamesEs.getOrElse(selectedMonth - 1) { "$selectedMonth" }, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF261A07))
                        IconButton(
                            onClick = {
                                if (selectedMonth < 12) {
                                    selectedMonth++
                                    viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                                }
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Text("+", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF8A6C3A))
                        }
                    }
                }

                // Selector de Año
                Column(modifier = Modifier.weight(1.3f)) {
                    Text("Año", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF8A6C3A))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF7F2E7), RoundedCornerShape(10.dp))
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        IconButton(
                            onClick = {
                                selectedYear--
                                viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Text("-", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF8A6C3A))
                        }
                        Text("$selectedYear", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF261A07))
                        IconButton(
                            onClick = {
                                selectedYear++
                                viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Text("+", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF8A6C3A))
                        }
                    }
                }
            }

            // Presets Rápidos de Fiestas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "Hoy" to {
                        val now = Calendar.getInstance()
                        selectedYear = now.get(Calendar.YEAR)
                        selectedMonth = now.get(Calendar.MONTH) + 1
                        selectedDay = now.get(Calendar.DAY_OF_MONTH)
                        viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                    },
                    "Rosh Hashaná" to {
                        selectedMonth = 9; selectedDay = 23
                        viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                    },
                    "Pesaj" to {
                        selectedMonth = 4; selectedDay = 15
                        viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                    },
                    "Shavuot" to {
                        selectedMonth = 5; selectedDay = 25
                        viewModel.convertGregorianDate(selectedYear, selectedMonth, selectedDay)
                    }
                ).forEach { (label, action) ->
                    AssistChip(
                        onClick = action,
                        label = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0xFFF5EFE3),
                            labelColor = Color(0xFF5A3E1B)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFDAC6A2))
                    )
                }
            }

            // Resultado de Hebcal API
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF261A07), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                if (isConverting) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color(0xFFD4AF37))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Consultando Hebcal API...", color = Color(0xFFE4D8C4), fontSize = 13.sp)
                    }
                } else if (convertedDate != null) {
                    val date = convertedDate!!
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "FECHA HEBREA RESULTANTE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD4AF37),
                                letterSpacing = 1.sp
                            )
                            if (date.isRoshChodesh) {
                                Box(
                                    modifier = Modifier
                                        .background(goldGradient, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text("Rosh Jódesh", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Text(
                            text = date.hebrewFormatted,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF9F5EC),
                            fontFamily = FontFamily.Serif
                        )

                        Text(
                            text = "Día ${date.hebrewDay} del mes de ${date.hebrewMonthName} • Año ${date.hebrewYear}",
                            fontSize = 13.sp,
                            color = Color(0xFFD8C8AF)
                        )

                        if (date.events.isNotEmpty()) {
                            HorizontalDivider(color = Color(0xFF4A3820))
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                date.events.forEach { ev ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = null,
                                            modifier = Modifier.size(14.dp),
                                            tint = Color(0xFFD4AF37)
                                        )
                                        Text(
                                            text = ev,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFFFFF2D6)
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
}

