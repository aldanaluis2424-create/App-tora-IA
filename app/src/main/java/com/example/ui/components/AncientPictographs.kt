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
 * Ilustraciones pictográficas ancestrales para las 22 letras del Alefato Hebreo.
 * Estilo bronce / oro antiguo con detalles nítidos (Buey, Casa, Camello, Puerta, etc.)
 */
@Composable
fun AncientPictograph(
    letterId: String,
    modifier: Modifier = Modifier.size(38.dp)
) {
    val bronzeGold = Color(0xFFB58742)
    val darkBronze = Color(0xFF7A5420)
    val lightGold = Color(0xFFE8CA82)

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            when (letterId.lowercase()) {
                "alef" -> drawOx(bronzeGold, darkBronze, lightGold)
                "bet" -> drawTemple(bronzeGold, darkBronze, lightGold)
                "gimel" -> drawCamel(bronzeGold, darkBronze, lightGold)
                "dalet" -> drawAncientDoor(bronzeGold, darkBronze, lightGold)
                "he" -> drawPraisingMan(bronzeGold, darkBronze, lightGold)
                "vav" -> drawTentPeg(bronzeGold, darkBronze, lightGold)
                "zayin" -> drawSword(bronzeGold, darkBronze, lightGold)
                "chet" -> drawFence(bronzeGold, darkBronze, lightGold)
                "tet" -> drawBasket(bronzeGold, darkBronze, lightGold)
                "yod" -> drawArmHand(bronzeGold, darkBronze, lightGold)
                "kaf" -> drawPalm(bronzeGold, darkBronze, lightGold)
                "lamed" -> drawShepherdStaff(bronzeGold, darkBronze, lightGold)
                "mem" -> drawWaterWaves(bronzeGold, darkBronze, lightGold)
                "nun" -> drawFish(bronzeGold, darkBronze, lightGold)
                "samej" -> drawPillar(bronzeGold, darkBronze, lightGold)
                "ayin" -> drawEye(bronzeGold, darkBronze, lightGold)
                "pe" -> drawMouth(bronzeGold, darkBronze, lightGold)
                "tsadi" -> drawKneelingMan(bronzeGold, darkBronze, lightGold)
                "qof" -> drawHorizonNeedle(bronzeGold, darkBronze, lightGold)
                "resh" -> drawHeadProfile(bronzeGold, darkBronze, lightGold)
                "shin" -> drawFlames(bronzeGold, darkBronze, lightGold)
                "tav" -> drawCrossSeal(bronzeGold, darkBronze, lightGold)
                else -> drawOx(bronzeGold, darkBronze, lightGold)
            }
        }
    }
}

// 1. Alef: Buey / Toro dorado
private fun DrawScope.drawOx(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Cuerpo del buey
    val bodyPath = Path().apply {
        moveTo(w * 0.2f, h * 0.5f)
        cubicTo(w * 0.3f, h * 0.35f, w * 0.6f, h * 0.35f, w * 0.75f, h * 0.45f)
        lineTo(w * 0.85f, h * 0.38f) // Cuello/cabeza
        lineTo(w * 0.95f, h * 0.42f) // Hocico
        lineTo(w * 0.88f, h * 0.58f) // Mandíbula
        lineTo(w * 0.78f, h * 0.62f) // Pecho
        lineTo(w * 0.75f, h * 0.88f) // Pata delantera
        lineTo(w * 0.68f, h * 0.88f)
        lineTo(w * 0.66f, h * 0.68f)
        lineTo(w * 0.38f, h * 0.68f) // Vientre
        lineTo(w * 0.35f, h * 0.88f) // Pata trasera
        lineTo(w * 0.28f, h * 0.88f)
        lineTo(w * 0.22f, h * 0.65f)
        close()
    }
    drawPath(bodyPath, primary, style = Fill)
    drawPath(bodyPath, dark, style = Stroke(width = 1.5f, join = StrokeJoin.Round))

    // Cuernos hacia arriba
    val hornPath = Path().apply {
        moveTo(w * 0.82f, h * 0.38f)
        cubicTo(w * 0.80f, h * 0.20f, w * 0.72f, h * 0.15f, w * 0.65f, h * 0.18f)
        moveTo(w * 0.85f, h * 0.36f)
        cubicTo(w * 0.88f, h * 0.18f, w * 0.94f, h * 0.14f, w * 0.98f, h * 0.20f)
    }
    drawPath(hornPath, dark, style = Stroke(width = 2.2f, cap = StrokeCap.Round))

    // Cola
    val tailPath = Path().apply {
        moveTo(w * 0.20f, h * 0.52f)
        cubicTo(w * 0.12f, h * 0.58f, w * 0.14f, h * 0.72f, w * 0.16f, h * 0.80f)
    }
    drawPath(tailPath, dark, style = Stroke(width = 1.8f, cap = StrokeCap.Round))
}

// 2. Bet: Casa / Templo con columnas y frontón
private fun DrawScope.drawTemple(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Techo triangular (frontón)
    val roofPath = Path().apply {
        moveTo(w * 0.5f, h * 0.15f)
        lineTo(w * 0.9f, h * 0.38f)
        lineTo(w * 0.1f, h * 0.38f)
        close()
    }
    drawPath(roofPath, primary, style = Fill)
    drawPath(roofPath, dark, style = Stroke(width = 1.5f))

    // Cornisa
    drawRect(light, topLeft = Offset(w * 0.08f, h * 0.38f), size = Size(w * 0.84f, h * 0.07f))
    drawRect(dark, topLeft = Offset(w * 0.08f, h * 0.38f), size = Size(w * 0.84f, h * 0.07f), style = Stroke(1.2f))

    // Columnas (4 columnas)
    val colW = w * 0.08f
    val colH = h * 0.35f
    val startY = h * 0.45f
    listOf(0.14f, 0.36f, 0.56f, 0.78f).forEach { xPos ->
        drawRect(primary, topLeft = Offset(w * xPos, startY), size = Size(colW, colH))
        drawRect(dark, topLeft = Offset(w * xPos, startY), size = Size(colW, colH), style = Stroke(1.2f))
    }

    // Base con escalones
    drawRect(light, topLeft = Offset(w * 0.05f, h * 0.80f), size = Size(w * 0.90f, h * 0.06f))
    drawRect(dark, topLeft = Offset(w * 0.05f, h * 0.80f), size = Size(w * 0.90f, h * 0.06f), style = Stroke(1.2f))
    drawRect(primary, topLeft = Offset(w * 0.02f, h * 0.86f), size = Size(w * 0.96f, h * 0.07f))
    drawRect(dark, topLeft = Offset(w * 0.02f, h * 0.86f), size = Size(w * 0.96f, h * 0.07f), style = Stroke(1.2f))
}

// 3. Gimel: Camello del desierto
private fun DrawScope.drawCamel(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val camelPath = Path().apply {
        moveTo(w * 0.85f, h * 0.22f) // Cabeza
        lineTo(w * 0.92f, h * 0.24f) // Hocico
        lineTo(w * 0.86f, h * 0.30f)
        cubicTo(w * 0.80f, h * 0.42f, w * 0.75f, h * 0.48f, w * 0.70f, h * 0.50f) // Cuello largo curvado
        // Joroba
        cubicTo(w * 0.65f, h * 0.32f, w * 0.45f, h * 0.32f, w * 0.40f, h * 0.52f)
        // Grupa
        cubicTo(w * 0.35f, h * 0.48f, w * 0.26f, h * 0.48f, w * 0.22f, h * 0.56f)
        lineTo(w * 0.20f, h * 0.88f) // Pata trasera
        lineTo(w * 0.26f, h * 0.88f)
        lineTo(w * 0.29f, h * 0.68f)
        lineTo(w * 0.58f, h * 0.68f) // Barriga
        lineTo(w * 0.62f, h * 0.88f) // Pata delantera
        lineTo(w * 0.68f, h * 0.88f)
        lineTo(w * 0.72f, h * 0.60f)
        close()
    }
    drawPath(camelPath, primary, style = Fill)
    drawPath(camelPath, dark, style = Stroke(width = 1.5f, join = StrokeJoin.Round))

    // Oreja
    drawCircle(dark, radius = 1.5f, center = Offset(w * 0.84f, h * 0.20f))
}

// 4. Dalet: Puerta antigua de arco tallada
private fun DrawScope.drawAncientDoor(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Marco exterior con arco
    val archPath = Path().apply {
        moveTo(w * 0.18f, h * 0.90f)
        lineTo(w * 0.18f, h * 0.40f)
        cubicTo(w * 0.18f, h * 0.12f, w * 0.82f, h * 0.12f, w * 0.82f, h * 0.40f)
        lineTo(w * 0.82f, h * 0.90f)
        close()
    }
    drawPath(archPath, light, style = Fill)
    drawPath(archPath, dark, style = Stroke(width = 2f))

    // Puerta interior
    val innerDoor = Path().apply {
        moveTo(w * 0.26f, h * 0.88f)
        lineTo(w * 0.26f, h * 0.42f)
        cubicTo(w * 0.26f, h * 0.22f, w * 0.74f, h * 0.22f, w * 0.74f, h * 0.42f)
        lineTo(w * 0.74f, h * 0.88f)
        close()
    }
    drawPath(innerDoor, primary, style = Fill)
    drawPath(innerDoor, dark, style = Stroke(width = 1.5f))

    // División central vertical
    drawLine(dark, Offset(w * 0.5f, h * 0.25f), Offset(w * 0.5f, h * 0.88f), strokeWidth = 1.8f)

    // Paneles decorativos de madera
    listOf(0.42f, 0.62f).forEach { yP ->
        drawRoundRect(light, topLeft = Offset(w * 0.30f, h * yP), size = Size(w * 0.16f, h * 0.14f), cornerRadius = CornerRadius(2f))
        drawRoundRect(dark, topLeft = Offset(w * 0.30f, h * yP), size = Size(w * 0.16f, h * 0.14f), cornerRadius = CornerRadius(2f), style = Stroke(1f))
        drawRoundRect(light, topLeft = Offset(w * 0.54f, h * yP), size = Size(w * 0.16f, h * 0.14f), cornerRadius = CornerRadius(2f))
        drawRoundRect(dark, topLeft = Offset(w * 0.54f, h * yP), size = Size(w * 0.16f, h * 0.14f), cornerRadius = CornerRadius(2f), style = Stroke(1f))
    }

    // Aldaba / Picaporte
    drawCircle(dark, radius = 2f, center = Offset(w * 0.45f, h * 0.58f))
    drawCircle(dark, radius = 2f, center = Offset(w * 0.55f, h * 0.58f))
}

// 5. He: Hombre orando con brazos arriba
private fun DrawScope.drawPraisingMan(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Cabeza
    drawCircle(primary, radius = w * 0.12f, center = Offset(w * 0.5f, h * 0.22f))
    drawCircle(dark, radius = w * 0.12f, center = Offset(w * 0.5f, h * 0.22f), style = Stroke(1.5f))

    // Tronco
    drawLine(dark, Offset(w * 0.5f, h * 0.34f), Offset(w * 0.5f, h * 0.66f), strokeWidth = 3.5f, cap = StrokeCap.Round)

    // Brazos levantados en alabanza
    val arms = Path().apply {
        moveTo(w * 0.18f, h * 0.24f)
        lineTo(w * 0.30f, h * 0.40f)
        lineTo(w * 0.50f, h * 0.44f)
        lineTo(w * 0.70f, h * 0.40f)
        lineTo(w * 0.82f, h * 0.24f)
    }
    drawPath(arms, dark, style = Stroke(width = 3.2f, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // Piernas
    drawLine(dark, Offset(w * 0.5f, h * 0.66f), Offset(w * 0.32f, h * 0.90f), strokeWidth = 3.2f, cap = StrokeCap.Round)
    drawLine(dark, Offset(w * 0.5f, h * 0.66f), Offset(w * 0.68f, h * 0.90f), strokeWidth = 3.2f, cap = StrokeCap.Round)
}

// 6. Vav: Clavo / Estaca de tienda
private fun DrawScope.drawTentPeg(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val peg = Path().apply {
        moveTo(w * 0.35f, h * 0.15f)
        lineTo(w * 0.65f, h * 0.15f)
        lineTo(w * 0.58f, h * 0.28f)
        lineTo(w * 0.54f, h * 0.88f) // Punta
        lineTo(w * 0.46f, h * 0.88f)
        lineTo(w * 0.42f, h * 0.28f)
        close()
    }
    drawPath(peg, primary, style = Fill)
    drawPath(peg, dark, style = Stroke(width = 2f, join = StrokeJoin.Round))

    // Gancho superior
    val hook = Path().apply {
        moveTo(w * 0.35f, h * 0.15f)
        cubicTo(w * 0.18f, h * 0.15f, w * 0.18f, h * 0.32f, w * 0.32f, h * 0.34f)
    }
    drawPath(hook, dark, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
}

// 7. Zayin: Espada / Cetro coronado
private fun DrawScope.drawSword(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Hoja de la espada vertical
    val blade = Path().apply {
        moveTo(w * 0.5f, h * 0.10f) // Punta
        lineTo(w * 0.58f, h * 0.62f)
        lineTo(w * 0.42f, h * 0.62f)
        close()
    }
    drawPath(blade, light, style = Fill)
    drawPath(blade, dark, style = Stroke(width = 1.8f))

    // Guarda
    drawRoundRect(primary, topLeft = Offset(w * 0.25f, h * 0.62f), size = Size(w * 0.50f, h * 0.08f), cornerRadius = CornerRadius(2f))
    drawRoundRect(dark, topLeft = Offset(w * 0.25f, h * 0.62f), size = Size(w * 0.50f, h * 0.08f), cornerRadius = CornerRadius(2f), style = Stroke(1.2f))

    // Empuñadura
    drawRect(dark, topLeft = Offset(w * 0.46f, h * 0.70f), size = Size(w * 0.08f, h * 0.16f))

    // Pomo
    drawCircle(primary, radius = w * 0.07f, center = Offset(w * 0.5f, h * 0.88f))
    drawCircle(dark, radius = w * 0.07f, center = Offset(w * 0.5f, h * 0.88f), style = Stroke(1.2f))
}

// 8. Chet: Cerca / Valla de estacas
private fun DrawScope.drawFence(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Estacas verticales
    listOf(0.18f, 0.38f, 0.58f, 0.78f).forEach { xPos ->
        val stake = Path().apply {
            moveTo(w * xPos + w * 0.05f, h * 0.15f) // Punta triangular
            lineTo(w * xPos + w * 0.10f, h * 0.25f)
            lineTo(w * xPos + w * 0.10f, h * 0.88f)
            lineTo(w * xPos, h * 0.88f)
            lineTo(w * xPos, h * 0.25f)
            close()
        }
        drawPath(stake, primary, style = Fill)
        drawPath(stake, dark, style = Stroke(width = 1.3f))
    }

    // Tablones horizontales
    drawRect(light, topLeft = Offset(w * 0.12f, h * 0.38f), size = Size(w * 0.76f, h * 0.08f))
    drawRect(dark, topLeft = Offset(w * 0.12f, h * 0.38f), size = Size(w * 0.76f, h * 0.08f), style = Stroke(1.2f))

    drawRect(light, topLeft = Offset(w * 0.12f, h * 0.68f), size = Size(w * 0.76f, h * 0.08f))
    drawRect(dark, topLeft = Offset(w * 0.12f, h * 0.68f), size = Size(w * 0.76f, h * 0.08f), style = Stroke(1.2f))
}

// 9. Tet: Cesta tejida con asa / Vasija
private fun DrawScope.drawBasket(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Asa circular
    drawArc(
        color = dark,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(w * 0.25f, h * 0.12f),
        size = Size(w * 0.50f, h * 0.40f),
        style = Stroke(width = 2.5f, cap = StrokeCap.Round)
    )

    // Cuerpo de la cesta
    val basket = Path().apply {
        moveTo(w * 0.15f, h * 0.45f)
        lineTo(w * 0.85f, h * 0.45f)
        cubicTo(w * 0.82f, h * 0.85f, w * 0.68f, h * 0.88f, w * 0.50f, h * 0.88f)
        cubicTo(w * 0.32f, h * 0.88f, w * 0.18f, h * 0.85f, w * 0.15f, h * 0.45f)
        close()
    }
    drawPath(basket, primary, style = Fill)
    drawPath(basket, dark, style = Stroke(width = 1.5f))

    // Líneas entrecruzadas de tejido
    drawLine(dark, Offset(w * 0.22f, h * 0.52f), Offset(w * 0.78f, h * 0.78f), strokeWidth = 1.2f)
    drawLine(dark, Offset(w * 0.78f, h * 0.52f), Offset(w * 0.22f, h * 0.78f), strokeWidth = 1.2f)
    drawLine(dark, Offset(w * 0.50f, h * 0.45f), Offset(w * 0.50f, h * 0.88f), strokeWidth = 1.2f)
}

// 10. Yod: Brazo y mano abierta
private fun DrawScope.drawArmHand(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val hand = Path().apply {
        moveTo(w * 0.15f, h * 0.75f) // Antebrazo
        lineTo(w * 0.45f, h * 0.55f) // Muñeca
        lineTo(w * 0.55f, h * 0.35f) // Pulgar
        lineTo(w * 0.62f, h * 0.38f)
        lineTo(w * 0.58f, h * 0.52f)
        lineTo(w * 0.85f, h * 0.42f) // Dedos extendidos
        lineTo(w * 0.82f, h * 0.62f)
        lineTo(w * 0.48f, h * 0.70f)
        lineTo(w * 0.22f, h * 0.88f)
        close()
    }
    drawPath(hand, primary, style = Fill)
    drawPath(hand, dark, style = Stroke(width = 1.8f, join = StrokeJoin.Round))
}

// 11. Kaf: Palma de la mano
private fun DrawScope.drawPalm(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val palm = Path().apply {
        moveTo(w * 0.38f, h * 0.85f) // Muñeca
        lineTo(w * 0.62f, h * 0.85f)
        lineTo(w * 0.72f, h * 0.55f)
        lineTo(w * 0.82f, h * 0.38f) // Meñique
        lineTo(w * 0.70f, h * 0.20f) // Anular
        lineTo(w * 0.50f, h * 0.15f) // Medio
        lineTo(w * 0.32f, h * 0.22f) // Índice
        lineTo(w * 0.18f, h * 0.48f) // Pulgar
        lineTo(w * 0.30f, h * 0.62f)
        close()
    }
    drawPath(palm, primary, style = Fill)
    drawPath(palm, dark, style = Stroke(width = 1.8f, join = StrokeJoin.Round))
}

// 12. Lamed: Cayado / Vara de pastor
private fun DrawScope.drawShepherdStaff(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val crook = Path().apply {
        moveTo(w * 0.30f, h * 0.35f)
        cubicTo(w * 0.30f, h * 0.12f, w * 0.75f, h * 0.12f, w * 0.75f, h * 0.30f)
        cubicTo(w * 0.75f, h * 0.42f, w * 0.55f, h * 0.45f, w * 0.55f, h * 0.90f)
    }
    drawPath(crook, dark, style = Stroke(width = 3.5f, cap = StrokeCap.Round))
}

// 13. Mem: Olas de agua
private fun DrawScope.drawWaterWaves(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    listOf(0.28f, 0.50f, 0.72f).forEach { yP ->
        val wave = Path().apply {
            moveTo(w * 0.10f, h * yP)
            cubicTo(w * 0.25f, h * (yP - 0.10f), w * 0.35f, h * (yP + 0.10f), w * 0.50f, h * yP)
            cubicTo(w * 0.65f, h * (yP - 0.10f), w * 0.75f, h * (yP + 0.10f), w * 0.90f, h * yP)
        }
        drawPath(wave, primary, style = Stroke(width = 3.5f, cap = StrokeCap.Round))
        drawPath(wave, dark, style = Stroke(width = 1.5f, cap = StrokeCap.Round))
    }
}

// 14. Nun: Pez nadando / Semilla germinando
private fun DrawScope.drawFish(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val fish = Path().apply {
        moveTo(w * 0.85f, h * 0.50f) // Boca
        cubicTo(w * 0.65f, h * 0.25f, w * 0.35f, h * 0.30f, w * 0.15f, h * 0.35f) // Cola superior
        lineTo(w * 0.25f, h * 0.50f)
        lineTo(w * 0.15f, h * 0.65f) // Cola inferior
        cubicTo(w * 0.35f, h * 0.70f, w * 0.65f, h * 0.75f, w * 0.85f, h * 0.50f)
        close()
    }
    drawPath(fish, primary, style = Fill)
    drawPath(fish, dark, style = Stroke(width = 1.8f, join = StrokeJoin.Round))

    // Ojo
    drawCircle(dark, radius = 2f, center = Offset(w * 0.72f, h * 0.46f))
}

// 15. Samej: Pilar con capitel / Soporte
private fun DrawScope.drawPillar(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Capitel
    drawRoundRect(light, topLeft = Offset(w * 0.18f, h * 0.18f), size = Size(w * 0.64f, h * 0.10f), cornerRadius = CornerRadius(2f))
    drawRoundRect(dark, topLeft = Offset(w * 0.18f, h * 0.18f), size = Size(w * 0.64f, h * 0.10f), cornerRadius = CornerRadius(2f), style = Stroke(1.2f))

    // Fuste (columna central)
    drawRect(primary, topLeft = Offset(w * 0.35f, h * 0.28f), size = Size(w * 0.30f, h * 0.50f))
    drawRect(dark, topLeft = Offset(w * 0.35f, h * 0.28f), size = Size(w * 0.30f, h * 0.50f), style = Stroke(1.5f))

    // Estrías
    drawLine(dark, Offset(w * 0.45f, h * 0.28f), Offset(w * 0.45f, h * 0.78f), strokeWidth = 1.2f)
    drawLine(dark, Offset(w * 0.55f, h * 0.28f), Offset(w * 0.55f, h * 0.78f), strokeWidth = 1.2f)

    // Base
    drawRoundRect(light, topLeft = Offset(w * 0.18f, h * 0.78f), size = Size(w * 0.64f, h * 0.10f), cornerRadius = CornerRadius(2f))
    drawRoundRect(dark, topLeft = Offset(w * 0.18f, h * 0.78f), size = Size(w * 0.64f, h * 0.10f), cornerRadius = CornerRadius(2f), style = Stroke(1.2f))
}

// 16. Ayin: Ojo almendrado
private fun DrawScope.drawEye(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val eyeOutline = Path().apply {
        moveTo(w * 0.10f, h * 0.50f)
        cubicTo(w * 0.30f, h * 0.20f, w * 0.70f, h * 0.20f, w * 0.90f, h * 0.50f)
        cubicTo(w * 0.70f, h * 0.80f, w * 0.30f, h * 0.80f, w * 0.10f, h * 0.50f)
        close()
    }
    drawPath(eyeOutline, light, style = Fill)
    drawPath(eyeOutline, dark, style = Stroke(width = 2f))

    // Iris & Pupila
    drawCircle(primary, radius = w * 0.18f, center = Offset(w * 0.50f, h * 0.50f))
    drawCircle(dark, radius = w * 0.10f, center = Offset(w * 0.50f, h * 0.50f))
}

// 17. Pe: Boca / Labios
private fun DrawScope.drawMouth(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val lips = Path().apply {
        moveTo(w * 0.12f, h * 0.50f)
        cubicTo(w * 0.32f, h * 0.30f, w * 0.68f, h * 0.30f, w * 0.88f, h * 0.50f)
        cubicTo(w * 0.68f, h * 0.75f, w * 0.32f, h * 0.75f, w * 0.12f, h * 0.50f)
        close()
    }
    drawPath(lips, primary, style = Fill)
    drawPath(lips, dark, style = Stroke(width = 1.8f))

    // Línea de división de labios
    val line = Path().apply {
        moveTo(w * 0.12f, h * 0.50f)
        cubicTo(w * 0.35f, h * 0.56f, w * 0.65f, h * 0.44f, w * 0.88f, h * 0.50f)
    }
    drawPath(line, dark, style = Stroke(width = 1.5f))
}

// 18. Tsadi: Hombre justo arrodillado / Anzuelo
private fun DrawScope.drawKneelingMan(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Cabeza inclinada
    drawCircle(primary, radius = w * 0.12f, center = Offset(w * 0.42f, h * 0.25f))
    drawCircle(dark, radius = w * 0.12f, center = Offset(w * 0.42f, h * 0.25f), style = Stroke(1.5f))

    // Espalda doblada en oración
    val body = Path().apply {
        moveTo(w * 0.42f, h * 0.37f)
        cubicTo(w * 0.25f, h * 0.50f, w * 0.28f, h * 0.70f, w * 0.40f, h * 0.82f)
        lineTo(w * 0.75f, h * 0.82f) // Piernas plegadas
        lineTo(w * 0.65f, h * 0.72f)
    }
    drawPath(body, dark, style = Stroke(width = 3.2f, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // Manos juntas
    val hands = Path().apply {
        moveTo(w * 0.35f, h * 0.50f)
        lineTo(w * 0.60f, h * 0.48f)
        lineTo(w * 0.68f, h * 0.38f)
    }
    drawPath(hands, dark, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
}

// 19. Qof: Sol en el horizonte / Ojo de aguja
private fun DrawScope.drawHorizonNeedle(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Sol sobre el horizonte
    drawArc(
        color = light,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = true,
        topLeft = Offset(w * 0.25f, h * 0.20f),
        size = Size(w * 0.50f, h * 0.50f)
    )
    drawArc(
        color = dark,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(w * 0.25f, h * 0.20f),
        size = Size(w * 0.50f, h * 0.50f),
        style = Stroke(width = 2f)
    )

    // Línea de horizonte
    drawLine(dark, Offset(w * 0.12f, h * 0.45f), Offset(w * 0.88f, h * 0.45f), strokeWidth = 2.2f)

    // Aguja / Mango vertical
    drawLine(primary, Offset(w * 0.50f, h * 0.45f), Offset(w * 0.50f, h * 0.88f), strokeWidth = 3f, cap = StrokeCap.Round)
    drawLine(dark, Offset(w * 0.50f, h * 0.45f), Offset(w * 0.50f, h * 0.88f), strokeWidth = 1.5f, cap = StrokeCap.Round)
}

// 20. Resh: Perfil de cabeza humana
private fun DrawScope.drawHeadProfile(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    val head = Path().apply {
        moveTo(w * 0.30f, h * 0.88f) // Cuello trasero
        lineTo(w * 0.30f, h * 0.50f)
        cubicTo(w * 0.30f, h * 0.15f, w * 0.70f, h * 0.15f, w * 0.72f, h * 0.35f) // Frente
        lineTo(w * 0.82f, h * 0.45f) // Nariz
        lineTo(w * 0.70f, h * 0.52f)
        lineTo(w * 0.75f, h * 0.60f) // Labios
        lineTo(w * 0.68f, h * 0.68f) // Mentón
        lineTo(w * 0.55f, h * 0.88f) // Cuello delantero
        close()
    }
    drawPath(head, primary, style = Fill)
    drawPath(head, dark, style = Stroke(width = 1.8f, join = StrokeJoin.Round))
}

// 21. Shin: Tres llamas de fuego / Dientes
private fun DrawScope.drawFlames(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Tres lenguas de fuego unidas en la base
    val flame1 = Path().apply {
        moveTo(w * 0.20f, h * 0.80f)
        cubicTo(w * 0.12f, h * 0.50f, w * 0.28f, h * 0.20f, w * 0.25f, h * 0.18f)
        cubicTo(w * 0.38f, h * 0.35f, w * 0.35f, h * 0.65f, w * 0.38f, h * 0.80f)
        close()
    }
    val flame2 = Path().apply {
        moveTo(w * 0.38f, h * 0.80f)
        cubicTo(w * 0.40f, h * 0.45f, w * 0.50f, h * 0.12f, w * 0.50f, h * 0.12f)
        cubicTo(w * 0.60f, h * 0.45f, w * 0.62f, h * 0.65f, w * 0.62f, h * 0.80f)
        close()
    }
    val flame3 = Path().apply {
        moveTo(w * 0.62f, h * 0.80f)
        cubicTo(w * 0.65f, h * 0.65f, w * 0.62f, h * 0.35f, w * 0.75f, h * 0.18f)
        cubicTo(w * 0.72f, h * 0.20f, w * 0.88f, h * 0.50f, w * 0.80f, h * 0.80f)
        close()
    }
    drawPath(flame1, primary, style = Fill)
    drawPath(flame1, dark, style = Stroke(1.5f))
    drawPath(flame2, light, style = Fill)
    drawPath(flame2, dark, style = Stroke(1.5f))
    drawPath(flame3, primary, style = Fill)
    drawPath(flame3, dark, style = Stroke(1.5f))

    // Base unificada
    drawRoundRect(dark, topLeft = Offset(w * 0.18f, h * 0.78f), size = Size(w * 0.64f, h * 0.08f), cornerRadius = CornerRadius(2f))
}

// 22. Tav: Dos maderos cruzados / Marca de pacto
private fun DrawScope.drawCrossSeal(primary: Color, dark: Color, light: Color) {
    val w = size.width
    val h = size.height

    // Madero diagonal 1
    val bar1 = Path().apply {
        moveTo(w * 0.20f, h * 0.25f)
        lineTo(w * 0.30f, h * 0.18f)
        lineTo(w * 0.80f, h * 0.75f)
        lineTo(w * 0.70f, h * 0.82f)
        close()
    }
    // Madero diagonal 2
    val bar2 = Path().apply {
        moveTo(w * 0.80f, h * 0.25f)
        lineTo(w * 0.70f, h * 0.18f)
        lineTo(w * 0.20f, h * 0.75f)
        lineTo(w * 0.30f, h * 0.82f)
        close()
    }
    drawPath(bar1, primary, style = Fill)
    drawPath(bar1, dark, style = Stroke(1.5f))
    drawPath(bar2, light, style = Fill)
    drawPath(bar2, dark, style = Stroke(1.5f))
}
