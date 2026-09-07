package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Ilustraciones vectoriales temáticas para las tarjetas de Módulos del Mes:
 * - Estrella dorada 3D & Constelaciones (General / Atributos)
 * - Pergamino & Reloj de arena (Historia / Contexto)
 * - Festividades dinámicas según el mes (Januquiá en Kislev, Shofar en Tishrei, Pésaj en Nisán, etc.)
 * - Cosechas y agricultura dinámicas (Lluvias en Kislev/Jeshván, Granadas en Tishrei, Cebada en Nisán, etc.)
 * - Libro Rabínico abierto (Sabios)
 * - Pergamino de tradición (Midrash)
 * - Corona y Trompeta Mesiánica (Escatología)
 * - Tablas de la Ley y Rollo de Torá (Citas Bíblicas)
 * - Destello de Sabiduría IA (Modo Estudio)
 */

@Composable
fun Golden3DStarIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldLight = Color(0xFFFFE082)
    val goldMid = Color(0xFFDDA638)
    val goldDark = Color(0xFF9E6B15)

    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val outerR = size.width * 0.46f
        val innerR = outerR * 0.42f

        // Dibujar estrella con caras 3D iluminadas
        for (i in 0 until 5) {
            val outerAngle = (i * 72f - 90f) * (Math.PI.toFloat() / 180f)
            val innerAngle1 = (i * 72f - 90f + 36f) * (Math.PI.toFloat() / 180f)
            val innerAngle0 = (i * 72f - 90f - 36f) * (Math.PI.toFloat() / 180f)

            val pOuter = Offset(cx + outerR * Math.cos(outerAngle.toDouble()).toFloat(), cy + outerR * Math.sin(outerAngle.toDouble()).toFloat())
            val pInner1 = Offset(cx + innerR * Math.cos(innerAngle1.toDouble()).toFloat(), cy + innerR * Math.sin(innerAngle1.toDouble()).toFloat())
            val pInner0 = Offset(cx + innerR * Math.cos(innerAngle0.toDouble()).toFloat(), cy + innerR * Math.sin(innerAngle0.toDouble()).toFloat())

            // Cara izquierda (Luz)
            val pathLight = Path().apply {
                moveTo(cx, cy)
                lineTo(pInner0.x, pInner0.y)
                lineTo(pOuter.x, pOuter.y)
                close()
            }
            drawPath(pathLight, goldLight)

            // Cara derecha (Sombra)
            val pathDark = Path().apply {
                moveTo(cx, cy)
                lineTo(pOuter.x, pOuter.y)
                lineTo(pInner1.x, pInner1.y)
                close()
            }
            drawPath(pathDark, goldMid)
        }

        // Borde exterior en oro oscuro
        val outline = Path().apply {
            for (i in 0 until 5) {
                val outerAngle = (i * 72f - 90f) * (Math.PI.toFloat() / 180f)
                val innerAngle = (i * 72f - 90f + 36f) * (Math.PI.toFloat() / 180f)
                val ox = cx + outerR * Math.cos(outerAngle.toDouble()).toFloat()
                val oy = cy + outerR * Math.sin(outerAngle.toDouble()).toFloat()
                val ix = cx + innerR * Math.cos(innerAngle.toDouble()).toFloat()
                val iy = cy + innerR * Math.sin(innerAngle.toDouble()).toFloat()
                if (i == 0) moveTo(ox, oy) else lineTo(ox, oy)
                lineTo(ix, iy)
            }
            close()
        }
        drawPath(outline, goldDark, style = Stroke(width = 1.5f))
    }
}

@Composable
fun ScrollAndHourglassIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFC59B27)
    val darkBronze = Color(0xFF6B4815)
    val parchmentBg = Color(0xFFFAF2DD)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Pergamino enrollado a la izquierda
        val scroll = Path().apply {
            moveTo(w * 0.15f, h * 0.20f)
            lineTo(w * 0.55f, h * 0.20f)
            lineTo(w * 0.55f, h * 0.75f)
            lineTo(w * 0.15f, h * 0.75f)
            close()
        }
        drawPath(scroll, parchmentBg)
        drawPath(scroll, darkBronze, style = Stroke(width = 1.5f))

        // Rodillos del pergamino
        drawRoundRect(goldColor, topLeft = Offset(w * 0.10f, h * 0.15f), size = Size(w * 0.10f, h * 0.70f), cornerRadius = CornerRadius(3f))
        drawRoundRect(darkBronze, topLeft = Offset(w * 0.10f, h * 0.15f), size = Size(w * 0.10f, h * 0.70f), cornerRadius = CornerRadius(3f), style = Stroke(1.2f))
        drawRoundRect(goldColor, topLeft = Offset(w * 0.50f, h * 0.15f), size = Size(w * 0.10f, h * 0.70f), cornerRadius = CornerRadius(3f))
        drawRoundRect(darkBronze, topLeft = Offset(w * 0.50f, h * 0.15f), size = Size(w * 0.10f, h * 0.70f), cornerRadius = CornerRadius(3f), style = Stroke(1.2f))

        // Líneas de texto en pergamino
        drawLine(darkBronze.copy(alpha = 0.5f), Offset(w * 0.23f, h * 0.35f), Offset(w * 0.47f, h * 0.35f), strokeWidth = 1f)
        drawLine(darkBronze.copy(alpha = 0.5f), Offset(w * 0.23f, h * 0.48f), Offset(w * 0.47f, h * 0.48f), strokeWidth = 1f)
        drawLine(darkBronze.copy(alpha = 0.5f), Offset(w * 0.23f, h * 0.61f), Offset(w * 0.47f, h * 0.61f), strokeWidth = 1f)

        // Reloj de arena a la derecha
        val hourglass = Path().apply {
            moveTo(w * 0.65f, h * 0.30f)
            lineTo(w * 0.90f, h * 0.30f)
            lineTo(w * 0.78f, h * 0.55f)
            lineTo(w * 0.90f, h * 0.80f)
            lineTo(w * 0.65f, h * 0.80f)
            lineTo(w * 0.77f, h * 0.55f)
            close()
        }
        drawPath(hourglass, parchmentBg)
        drawPath(hourglass, darkBronze, style = Stroke(width = 1.5f))
        drawLine(goldColor, Offset(w * 0.62f, h * 0.30f), Offset(w * 0.93f, h * 0.30f), strokeWidth = 2.5f)
        drawLine(goldColor, Offset(w * 0.62f, h * 0.80f), Offset(w * 0.93f, h * 0.80f), strokeWidth = 2.5f)
    }
}

/**
 * Icono dinámico de festividades adaptado al mes específico:
 * - Kislev: Januquiá de 9 brazos
 * - Tishrei / Elul: Shofar
 * - Nisán / Iyar: Pésaj y Copa de Kidush
 * - Shevat: Árbol de Tu Bishvat
 * - Adar: Corona y Purim
 * - Siván: Tablas de la Ley
 * - Otros: Menorá dorada
 */
@Composable
fun MonthFestivalDynamicIcon(monthId: String, modifier: Modifier = Modifier.size(34.dp)) {
    val m = monthId.lowercase()
    when {
        m.contains("kislev") -> Chanukiah9BranchesIcon(modifier)
        m.contains("tishrei") || m.contains("elul") -> ShofarIcon(modifier)
        m.contains("nisan") || m.contains("iyar") -> PassoverCupAndMatzahIcon(modifier)
        m.contains("shevat") -> TuBishvatTreeIcon(modifier)
        m.contains("adar") -> PurimCrownIcon(modifier)
        m.contains("sivan") -> TabletsOfTorahIcon(modifier)
        else -> GoldenMenorahIcon(modifier)
    }
}

/**
 * Icono dinámico de cosechas adaptado al ciclo agrícola de Israel:
 * - Jeshván / Kislev / Tevet: Nubes de lluvia invernal (Geshem) y brotes
 * - Tishrei: Granadas y aceitunas
 * - Shevat: Floración de almendros
 * - Nisán / Iyar: Gavilla de cebada (Ómer)
 * - Siván: Cesta de primicias (Bikkurim) y espigas de trigo
 * - Tamuz / Av: Uvas e higos de verano
 * - Otros: Tractor y espigas doradas
 */
@Composable
fun MonthHarvestDynamicIcon(monthId: String, modifier: Modifier = Modifier.size(34.dp)) {
    val m = monthId.lowercase()
    when {
        m.contains("jeshv") || m.contains("cheshvan") || m.contains("kislev") || m.contains("tevet") -> WinterRainAndSproutsIcon(modifier)
        m.contains("tishrei") -> PomegranateAndOlivesIcon(modifier)
        m.contains("shevat") -> TuBishvatTreeIcon(modifier)
        m.contains("nisan") || m.contains("iyar") -> BarleySheafIcon(modifier)
        m.contains("sivan") -> BikkurimBasketIcon(modifier)
        m.contains("tamuz") || m.contains("av") -> GrapesAndFigsIcon(modifier)
        else -> TractorAndWheatIcon(modifier)
    }
}

@Composable
fun Chanukiah9BranchesIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFD4AF37)
    val flameColor = Color(0xFFFF9800)
    val darkGold = Color(0xFF9E6B15)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Base y eje central (Shamash)
        drawLine(goldColor, Offset(w * 0.5f, h * 0.20f), Offset(w * 0.5f, h * 0.85f), strokeWidth = 2.5f, cap = StrokeCap.Round)
        drawLine(darkGold, Offset(w * 0.25f, h * 0.88f), Offset(w * 0.75f, h * 0.88f), strokeWidth = 2.5f, cap = StrokeCap.Round)

        // 4 pares de brazos simétricos (8 brazos + shamash central = 9)
        listOf(0.12f, 0.22f, 0.32f, 0.40f).forEach { r ->
            val branch = Path().apply {
                moveTo(w * (0.5f - r), h * 0.28f)
                cubicTo(
                    w * (0.5f - r), h * (0.28f + r * 1.3f),
                    w * (0.5f + r), h * (0.28f + r * 1.3f),
                    w * (0.5f + r), h * 0.28f
                )
            }
            drawPath(branch, goldColor, style = Stroke(width = 1.8f, cap = StrokeCap.Round))
        }

        // Llamas
        val xOffsets = listOf(
            0.5f - 0.40f, 0.5f - 0.32f, 0.5f - 0.22f, 0.5f - 0.12f,
            0.5f, // Shamash
            0.5f + 0.12f, 0.5f + 0.22f, 0.5f + 0.32f, 0.5f + 0.40f
        )
        xOffsets.forEach { xPos ->
            val flameY = if (xPos == 0.5f) h * 0.16f else h * 0.24f
            drawCircle(flameColor, radius = 2.2f, center = Offset(w * xPos, flameY))
            drawCircle(Color(0xFFFFEB3B), radius = 1.2f, center = Offset(w * xPos, flameY))
        }
    }
}

@Composable
fun ShofarIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldMid = Color(0xFFC79A46)
    val darkHorn = Color(0xFF634110)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val shofarPath = Path().apply {
            moveTo(w * 0.12f, h * 0.68f) // Boquilla
            cubicTo(w * 0.25f, h * 0.70f, w * 0.45f, h * 0.75f, w * 0.65f, h * 0.60f)
            cubicTo(w * 0.80f, h * 0.48f, w * 0.90f, h * 0.32f, w * 0.88f, h * 0.20f) // Campana
            lineTo(w * 0.78f, h * 0.24f)
            cubicTo(w * 0.75f, h * 0.38f, w * 0.65f, h * 0.50f, w * 0.45f, h * 0.58f)
            cubicTo(w * 0.30f, h * 0.62f, w * 0.15f, h * 0.62f, w * 0.12f, h * 0.68f)
            close()
        }
        drawPath(shofarPath, goldMid)
        drawPath(shofarPath, darkHorn, style = Stroke(width = 1.5f, join = StrokeJoin.Round))

        // Boquilla dorada
        drawCircle(darkHorn, radius = 2f, center = Offset(w * 0.12f, h * 0.68f))

        // Rayas de cuerno
        drawLine(darkHorn, Offset(w * 0.42f, h * 0.60f), Offset(w * 0.45f, h * 0.72f), strokeWidth = 1.2f)
        drawLine(darkHorn, Offset(w * 0.60f, h * 0.52f), Offset(w * 0.65f, h * 0.64f), strokeWidth = 1.2f)
    }
}

@Composable
fun WinterRainAndSproutsIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val cloudBlue = Color(0xFF78909C)
    val rainBlue = Color(0xFF29B6F6)
    val sproutGreen = Color(0xFF66BB6A)
    val soilBrown = Color(0xFF8D6E63)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Nube superior
        val cloud = Path().apply {
            moveTo(w * 0.25f, h * 0.35f)
            cubicTo(w * 0.25f, h * 0.20f, w * 0.45f, h * 0.20f, w * 0.50f, h * 0.25f)
            cubicTo(w * 0.55f, h * 0.15f, w * 0.80f, h * 0.18f, w * 0.80f, h * 0.35f)
            cubicTo(w * 0.90f, h * 0.35f, w * 0.90f, h * 0.50f, w * 0.80f, h * 0.50f)
            lineTo(w * 0.25f, h * 0.50f)
            close()
        }
        drawPath(cloud, cloudBlue)

        // Gotas de lluvia (Geshem / Yoreh)
        listOf(0.35f, 0.50f, 0.68f).forEach { xP ->
            drawLine(rainBlue, Offset(w * xP, h * 0.56f), Offset(w * (xP - 0.05f), h * 0.68f), strokeWidth = 1.8f, cap = StrokeCap.Round)
        }

        // Suelo y brote verde
        drawLine(soilBrown, Offset(w * 0.15f, h * 0.85f), Offset(w * 0.85f, h * 0.85f), strokeWidth = 2f, cap = StrokeCap.Round)
        drawLine(sproutGreen, Offset(w * 0.50f, h * 0.85f), Offset(w * 0.50f, h * 0.72f), strokeWidth = 2f, cap = StrokeCap.Round)
        drawCircle(sproutGreen, radius = 2.5f, center = Offset(w * 0.44f, h * 0.71f))
        drawCircle(sproutGreen, radius = 2.5f, center = Offset(w * 0.56f, h * 0.71f))
    }
}

@Composable
fun PomegranateAndOlivesIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val pomegranateRed = Color(0xFFC2185B)
    val goldAccent = Color(0xFFD4AF37)
    val oliveGreen = Color(0xFF558B2F)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Granada
        drawCircle(pomegranateRed, radius = w * 0.26f, center = Offset(w * 0.42f, h * 0.55f))
        // Corona de la granada
        val crown = Path().apply {
            moveTo(w * 0.34f, h * 0.30f)
            lineTo(w * 0.42f, h * 0.22f)
            lineTo(w * 0.50f, h * 0.30f)
            close()
        }
        drawPath(crown, goldAccent)

        // Aceituna verde a la derecha
        drawCircle(oliveGreen, radius = w * 0.16f, center = Offset(w * 0.72f, h * 0.65f))
        drawLine(Color(0xFF33691E), Offset(w * 0.72f, h * 0.50f), Offset(w * 0.82f, h * 0.42f), strokeWidth = 1.8f)
    }
}

@Composable
fun TuBishvatTreeIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val trunkBrown = Color(0xFF6D4C41)
    val leavesPink = Color(0xFFF48FB1)
    val leavesGreen = Color(0xFF81C784)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Tronco
        drawLine(trunkBrown, Offset(w * 0.5f, h * 0.88f), Offset(w * 0.5f, h * 0.50f), strokeWidth = 3f, cap = StrokeCap.Round)
        drawLine(trunkBrown, Offset(w * 0.5f, h * 0.60f), Offset(w * 0.35f, h * 0.48f), strokeWidth = 2f, cap = StrokeCap.Round)
        drawLine(trunkBrown, Offset(w * 0.5f, h * 0.58f), Offset(w * 0.65f, h * 0.46f), strokeWidth = 2f, cap = StrokeCap.Round)

        // Copa en flor de almendro
        drawCircle(leavesPink, radius = w * 0.22f, center = Offset(w * 0.50f, h * 0.35f))
        drawCircle(leavesGreen, radius = w * 0.16f, center = Offset(w * 0.35f, h * 0.42f))
        drawCircle(leavesPink, radius = w * 0.16f, center = Offset(w * 0.65f, h * 0.40f))
    }
}

@Composable
fun BarleySheafIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFE0A93B)
    val darkGold = Color(0xFF8C5C15)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Gavilla de cebada (Omer)
        drawLine(darkGold, Offset(w * 0.5f, h * 0.85f), Offset(w * 0.35f, h * 0.30f), strokeWidth = 2f, cap = StrokeCap.Round)
        drawLine(darkGold, Offset(w * 0.5f, h * 0.85f), Offset(w * 0.50f, h * 0.25f), strokeWidth = 2f, cap = StrokeCap.Round)
        drawLine(darkGold, Offset(w * 0.5f, h * 0.85f), Offset(w * 0.65f, h * 0.30f), strokeWidth = 2f, cap = StrokeCap.Round)

        // Lazo central
        drawRoundRect(Color(0xFFC2185B), topLeft = Offset(w * 0.40f, h * 0.58f), size = Size(w * 0.20f, h * 0.08f), cornerRadius = CornerRadius(2f))

        // Espigas doradas
        listOf(0.35f to 0.30f, 0.50f to 0.25f, 0.65f to 0.30f).forEach { (x, y) ->
            drawCircle(goldColor, radius = 3.5f, center = Offset(w * x, h * y))
        }
    }
}

@Composable
fun BikkurimBasketIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val basketBrown = Color(0xFF8D6E63)
    val fruitGold = Color(0xFFFFB300)
    val fruitRed = Color(0xFFE53935)
    val fruitPurple = Color(0xFF8E24AA)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Cesta de primicias
        val basket = Path().apply {
            moveTo(w * 0.20f, h * 0.55f)
            lineTo(w * 0.80f, h * 0.55f)
            lineTo(w * 0.70f, h * 0.85f)
            lineTo(w * 0.30f, h * 0.85f)
            close()
        }
        drawPath(basket, basketBrown)

        // Asa
        drawArc(basketBrown, 180f, 180f, useCenter = false, topLeft = Offset(w * 0.30f, h * 0.35f), size = Size(w * 0.40f, h * 0.40f), style = Stroke(2f))

        // Frutos de primicias sobresaliendo
        drawCircle(fruitGold, radius = 3.5f, center = Offset(w * 0.38f, h * 0.50f))
        drawCircle(fruitRed, radius = 3.5f, center = Offset(w * 0.50f, h * 0.48f))
        drawCircle(fruitPurple, radius = 3.5f, center = Offset(w * 0.62f, h * 0.50f))
    }
}

@Composable
fun GrapesAndFigsIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val grapePurple = Color(0xFF6A1B9A)
    val leafGreen = Color(0xFF43A047)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Hoja de parra
        drawCircle(leafGreen, radius = w * 0.12f, center = Offset(w * 0.50f, h * 0.30f))

        // Racimo de uvas
        val grapePositions = listOf(
            0.40f to 0.45f, 0.50f to 0.45f, 0.60f to 0.45f,
            0.45f to 0.58f, 0.55f to 0.58f,
            0.50f to 0.70f
        )
        grapePositions.forEach { (x, y) ->
            drawCircle(grapePurple, radius = 3.5f, center = Offset(w * x, h * y))
        }
    }
}

@Composable
fun PassoverCupAndMatzahIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFD4AF37)
    val matzahColor = Color(0xFFE8D3A2)
    val darkBronze = Color(0xFF6B4815)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Matzá cuadrada a la izquierda
        drawRoundRect(matzahColor, topLeft = Offset(w * 0.12f, h * 0.35f), size = Size(w * 0.40f, h * 0.45f), cornerRadius = CornerRadius(3f))
        drawRoundRect(darkBronze, topLeft = Offset(w * 0.12f, h * 0.35f), size = Size(w * 0.40f, h * 0.45f), cornerRadius = CornerRadius(3f), style = Stroke(1.2f))
        // Puntos de matzá
        for (i in 1..3) {
            for (j in 1..3) {
                drawCircle(darkBronze, radius = 0.8f, center = Offset(w * (0.15f + i * 0.08f), h * (0.38f + j * 0.09f)))
            }
        }

        // Copa de Kidush dorada a la derecha
        val cupPath = Path().apply {
            moveTo(w * 0.58f, h * 0.35f)
            lineTo(w * 0.86f, h * 0.35f)
            lineTo(w * 0.80f, h * 0.60f)
            lineTo(w * 0.64f, h * 0.60f)
            close()
        }
        drawPath(cupPath, goldColor)
        drawLine(goldColor, Offset(w * 0.72f, h * 0.60f), Offset(w * 0.72f, h * 0.80f), strokeWidth = 2.5f)
        drawLine(goldColor, Offset(w * 0.60f, h * 0.80f), Offset(w * 0.84f, h * 0.80f), strokeWidth = 2.5f)
    }
}

@Composable
fun TuBishvatTreeIconAlternative(modifier: Modifier = Modifier.size(34.dp)) {
    TuBishvatTreeIcon(modifier)
}

@Composable
fun PurimCrownIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFD4AF37)
    val jewelRed = Color(0xFFD32F2F)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val crownPath = Path().apply {
            moveTo(w * 0.15f, h * 0.70f)
            lineTo(w * 0.85f, h * 0.70f)
            lineTo(w * 0.85f, h * 0.40f)
            lineTo(w * 0.68f, h * 0.55f)
            lineTo(w * 0.50f, h * 0.30f)
            lineTo(w * 0.32f, h * 0.55f)
            lineTo(w * 0.15f, h * 0.40f)
            close()
        }
        drawPath(crownPath, goldColor)
        drawCircle(jewelRed, radius = 2.5f, center = Offset(w * 0.50f, h * 0.32f))
        drawCircle(jewelRed, radius = 2f, center = Offset(w * 0.18f, h * 0.42f))
        drawCircle(jewelRed, radius = 2f, center = Offset(w * 0.82f, h * 0.42f))
    }
}

@Composable
fun TabletsOfTorahIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val parchmentBg = Color(0xFFFAF2DD)
    val darkBronze = Color(0xFF6B4815)
    val goldColor = Color(0xFFC59B27)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Tabla izquierda
        val leftTablet = Path().apply {
            moveTo(w * 0.15f, h * 0.80f)
            lineTo(w * 0.48f, h * 0.80f)
            lineTo(w * 0.48f, h * 0.35f)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(w * 0.15f, h * 0.20f, w * 0.48f, h * 0.50f),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            close()
        }
        drawPath(leftTablet, parchmentBg)
        drawPath(leftTablet, darkBronze, style = Stroke(1.5f))

        // Tabla derecha
        val rightTablet = Path().apply {
            moveTo(w * 0.52f, h * 0.80f)
            lineTo(w * 0.85f, h * 0.80f)
            lineTo(w * 0.85f, h * 0.35f)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(w * 0.52f, h * 0.20f, w * 0.85f, h * 0.50f),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            close()
        }
        drawPath(rightTablet, parchmentBg)
        drawPath(rightTablet, darkBronze, style = Stroke(1.5f))

        // Líneas de mandamientos
        for (i in 1..4) {
            drawLine(goldColor, Offset(w * 0.22f, h * (0.35f + i * 0.09f)), Offset(w * 0.42f, h * (0.35f + i * 0.09f)), strokeWidth = 1.2f)
            drawLine(goldColor, Offset(w * 0.58f, h * (0.35f + i * 0.09f)), Offset(w * 0.78f, h * (0.35f + i * 0.09f)), strokeWidth = 1.2f)
        }
    }
}

@Composable
fun TractorAndWheatIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFC59B27)
    val darkBronze = Color(0xFF6B4B18)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Tractor
        drawRoundRect(goldColor, topLeft = Offset(w * 0.10f, h * 0.40f), size = Size(w * 0.20f, h * 0.25f), cornerRadius = CornerRadius(2f))
        drawRoundRect(darkBronze, topLeft = Offset(w * 0.10f, h * 0.40f), size = Size(w * 0.20f, h * 0.25f), cornerRadius = CornerRadius(2f), style = Stroke(1.2f))
        drawRect(goldColor, topLeft = Offset(w * 0.26f, h * 0.48f), size = Size(w * 0.22f, h * 0.17f))
        drawRect(darkBronze, topLeft = Offset(w * 0.26f, h * 0.48f), size = Size(w * 0.22f, h * 0.17f), style = Stroke(1.2f))

        // Ruedas
        drawCircle(darkBronze, radius = w * 0.14f, center = Offset(w * 0.20f, h * 0.72f), style = Stroke(2f))
        drawCircle(goldColor, radius = w * 0.10f, center = Offset(w * 0.20f, h * 0.72f))
        drawCircle(darkBronze, radius = w * 0.08f, center = Offset(w * 0.42f, h * 0.74f), style = Stroke(1.8f))
        drawCircle(goldColor, radius = w * 0.05f, center = Offset(w * 0.42f, h * 0.74f))

        // Espigas de trigo a la derecha
        val wheatStem = Path().apply {
            moveTo(w * 0.65f, h * 0.85f)
            cubicTo(w * 0.70f, h * 0.60f, w * 0.75f, h * 0.40f, w * 0.82f, h * 0.18f)
        }
        drawPath(wheatStem, darkBronze, style = Stroke(width = 1.8f, cap = StrokeCap.Round))

        // Granos de trigo
        listOf(0.25f, 0.38f, 0.52f, 0.65f).forEach { yP ->
            drawCircle(goldColor, radius = 2.5f, center = Offset(w * 0.78f, h * yP))
            drawCircle(goldColor, radius = 2.5f, center = Offset(w * 0.86f, h * (yP - 0.04f)))
        }
    }
}

@Composable
fun RabbinicBookIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFC79A46)
    val darkBronze = Color(0xFF6B4815)
    val parchmentBg = Color(0xFFFBF4E4)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Libro abierto
        val leftPage = Path().apply {
            moveTo(w * 0.50f, h * 0.35f)
            cubicTo(w * 0.35f, h * 0.30f, w * 0.20f, h * 0.32f, w * 0.12f, h * 0.36f)
            lineTo(w * 0.12f, h * 0.78f)
            cubicTo(w * 0.20f, h * 0.74f, w * 0.35f, h * 0.72f, w * 0.50f, h * 0.78f)
            close()
        }
        drawPath(leftPage, parchmentBg)
        drawPath(leftPage, darkBronze, style = Stroke(width = 1.5f))

        val rightPage = Path().apply {
            moveTo(w * 0.50f, h * 0.35f)
            cubicTo(w * 0.65f, h * 0.30f, w * 0.80f, h * 0.32f, w * 0.88f, h * 0.36f)
            lineTo(w * 0.88f, h * 0.78f)
            cubicTo(w * 0.80f, h * 0.74f, w * 0.65f, h * 0.72f, w * 0.50f, h * 0.78f)
            close()
        }
        drawPath(rightPage, parchmentBg)
        drawPath(rightPage, darkBronze, style = Stroke(width = 1.5f))

        // Lomo
        drawLine(goldColor, Offset(w * 0.50f, h * 0.35f), Offset(w * 0.50f, h * 0.78f), strokeWidth = 2.5f)

        // Líneas de texto hebreo simuladas
        listOf(0.44f, 0.54f, 0.64f).forEach { yP ->
            drawLine(darkBronze.copy(alpha = 0.5f), Offset(w * 0.20f, h * yP), Offset(w * 0.44f, h * yP), strokeWidth = 1f)
            drawLine(darkBronze.copy(alpha = 0.5f), Offset(w * 0.56f, h * yP), Offset(w * 0.80f, h * yP), strokeWidth = 1f)
        }
    }
}

@Composable
fun MidrashScrollIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFC59B27)
    val darkBronze = Color(0xFF6B4815)
    val parchmentBg = Color(0xFFFAF2DD)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Rollo de Midrash desplegado
        val scroll = Path().apply {
            moveTo(w * 0.20f, h * 0.20f)
            lineTo(w * 0.80f, h * 0.20f)
            lineTo(w * 0.80f, h * 0.78f)
            lineTo(w * 0.20f, h * 0.78f)
            close()
        }
        drawPath(scroll, parchmentBg)
        drawPath(scroll, darkBronze, style = Stroke(1.5f))

        // Cabezales del rollo (Etz Jayim)
        drawRoundRect(goldColor, topLeft = Offset(w * 0.14f, h * 0.12f), size = Size(w * 0.08f, h * 0.76f), cornerRadius = CornerRadius(2f))
        drawRoundRect(goldColor, topLeft = Offset(w * 0.78f, h * 0.12f), size = Size(w * 0.08f, h * 0.76f), cornerRadius = CornerRadius(2f))

        // Líneas
        listOf(0.32f, 0.44f, 0.56f, 0.68f).forEach { yP ->
            drawLine(darkBronze.copy(alpha = 0.6f), Offset(w * 0.28f, h * yP), Offset(w * 0.72f, h * yP), strokeWidth = 1.2f)
        }
    }
}

@Composable
fun PropheticCrownIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFD4AF37)
    val darkBronze = Color(0xFF8C5C15)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Shofar profético y corona
        val horn = Path().apply {
            moveTo(w * 0.20f, h * 0.75f)
            cubicTo(w * 0.40f, h * 0.75f, w * 0.70f, h * 0.65f, w * 0.80f, h * 0.35f)
            lineTo(w * 0.70f, h * 0.35f)
            cubicTo(w * 0.60f, h * 0.55f, w * 0.35f, h * 0.65f, w * 0.20f, h * 0.68f)
            close()
        }
        drawPath(horn, goldColor)
        drawPath(horn, darkBronze, style = Stroke(1.5f))

        // Rayos de luz mesiánicos
        listOf(0.30f, 0.50f, 0.70f).forEach { xP ->
            drawLine(goldColor, Offset(w * xP, h * 0.25f), Offset(w * xP, h * 0.12f), strokeWidth = 1.8f, cap = StrokeCap.Round)
        }
    }
}

@Composable
fun BiblicalScrollIcon(modifier: Modifier = Modifier.size(34.dp)) {
    TabletsOfTorahIcon(modifier)
}

@Composable
fun StudyAiSparkleIcon(modifier: Modifier = Modifier.size(34.dp)) {
    val goldColor = Color(0xFFC59B27)
    val starColor = Color(0xFFFFD54F)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Destello grande
        val cx = w * 0.5f
        val cy = h * 0.5f
        val r = w * 0.38f

        val sparkle = Path().apply {
            moveTo(cx, cy - r)
            cubicTo(cx, cy, cx, cy, cx + r, cy)
            cubicTo(cx, cy, cx, cy, cx, cy + r)
            cubicTo(cx, cy, cx, cy, cx - r, cy)
            cubicTo(cx, cy, cx, cy, cx, cy - r)
            close()
        }
        drawPath(sparkle, starColor)
        drawPath(sparkle, goldColor, style = Stroke(1.5f))

        // Destello pequeño satélite
        drawCircle(goldColor, radius = 2.5f, center = Offset(w * 0.80f, h * 0.25f))
        drawCircle(goldColor, radius = 2.0f, center = Offset(w * 0.22f, h * 0.75f))
    }
}

@Composable
fun GoldenMenorahIcon(
    modifier: Modifier = Modifier.size(34.dp)
) {
    val goldColor = Color(0xFFD4AF37)
    val darkGold = Color(0xFF9E6B15)
    val flameColor = Color(0xFFFF9800)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Base y eje central
        drawLine(
            color = goldColor,
            start = Offset(w * 0.5f, h * 0.25f),
            end = Offset(w * 0.5f, h * 0.85f),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )

        // Base
        val basePath = Path().apply {
            moveTo(w * 0.25f, h * 0.90f)
            lineTo(w * 0.75f, h * 0.90f)
            lineTo(w * 0.5f, h * 0.85f)
            close()
        }
        drawPath(basePath, darkGold)

        // 3 pares de brazos curvos (Menorá de 7 brazos)
        listOf(0.18f, 0.28f, 0.38f).forEach { r ->
            val branchPath = Path().apply {
                moveTo(w * (0.5f - r), h * 0.25f)
                cubicTo(
                    w * (0.5f - r), h * (0.25f + r * 1.5f),
                    w * (0.5f + r), h * (0.25f + r * 1.5f),
                    w * (0.5f + r), h * 0.25f
                )
            }
            drawPath(branchPath, goldColor, style = Stroke(width = 2f, cap = StrokeCap.Round))
        }

        // 7 llamas de fuego doradas/naranjas
        val candleXPositions = listOf(
            0.5f - 0.38f, 0.5f - 0.28f, 0.5f - 0.18f,
            0.5f,
            0.5f + 0.18f, 0.5f + 0.28f, 0.5f + 0.38f
        )

        candleXPositions.forEach { xPos ->
            drawCircle(flameColor, radius = 2.2f, center = Offset(w * xPos, h * 0.20f))
            drawCircle(Color(0xFFFFEB3B), radius = 1.2f, center = Offset(w * xPos, h * 0.20f))
        }
    }
}
