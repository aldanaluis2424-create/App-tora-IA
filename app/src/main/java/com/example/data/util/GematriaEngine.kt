package com.example.data.util

import com.example.data.model.LetterBreakdown

/**
 * Motor de cálculo de Gematría Estándar (Mispar Hejrají).
 * Implementa la función JavaScript estándar solicitada para cálculo local en tiempo real:
 *
 * ```javascript
 * function calcularGematriaEstandar(textoHebreo) {
 *   const valores = {
 *     'א': 1, 'ב': 2, 'ג': 3, 'ד': 4, 'ה': 5, 'ו': 6, 'ז': 7, 'ח': 8, 'ט': 9,
 *     'י': 10, 'כ': 20, 'ך': 20, 'ל': 30, 'מ': 40, 'ם': 40, 'נ': 50, 'ן': 50,
 *     'ס': 60, 'ע': 70, 'פ': 80, 'ף': 80, 'צ': 90, 'ץ': 90, 'ק': 100, 'ר': 200,
 *     'ש': 300, 'ת': 400
 *   };
 *   let total = 0;
 *   for (let char of textoHebreo) {
 *     if (valores[char]) {
 *       total += valores[char];
 *     }
 *   }
 *   return total;
 * }
 * ```
 */
object GematriaEngine {

    const val JAVASCRIPT_SOURCE_CODE: String = """
function calcularGematriaEstandar(textoHebreo) {
  const valores = {
    'א': 1, 'ב': 2, 'ג': 3, 'ד': 4, 'ה': 5, 'ו': 6, 'ז': 7, 'ח': 8, 'ט': 9,
    'י': 10, 'כ': 20, 'ך': 20, 'ל': 30, 'מ': 40, 'ם': 40, 'נ': 50, 'ן': 50,
    'ס': 60, 'ע': 70, 'פ': 80, 'ף': 80, 'צ': 90, 'ץ': 90, 'ק': 100, 'ר': 200,
    'ש': 300, 'ת': 400
  };

  let total = 0;
  for (let char of textoHebreo) {
    if (valores[char]) {
      total += valores[char];
    }
  }
  return total;
}
"""

    val VALORES_GEMATRIA: Map<Char, Int> = mapOf(
        'א' to 1, 'ב' to 2, 'ג' to 3, 'ד' to 4, 'ה' to 5, 'ו' to 6, 'ז' to 7, 'ח' to 8, 'ט' to 9,
        'י' to 10, 'כ' to 20, 'ך' to 20, 'ל' to 30, 'מ' to 40, 'ם' to 40, 'נ' to 50, 'ן' to 50,
        'ס' to 60, 'ע' to 70, 'פ' to 80, 'ף' to 80, 'צ' to 90, 'ץ' to 90, 'ק' to 100, 'ר' to 200,
        'ש' to 300, 'ת' to 400
    )

    private val NOMBRES_LETRAS: Map<Char, String> = mapOf(
        'א' to "Álef", 'ב' to "Bet", 'ג' to "Guímel", 'ד' to "Dálet", 'ה' to "He",
        'ו' to "Vav", 'ז' to "Zayin", 'ח' to "Jet", 'ט' to "Tet", 'י' to "Yod",
        'כ' to "Kaf", 'ך' to "Kaf Sofit", 'ל' to "Lámed", 'מ' to "Mem", 'ם' to "Mem Sofit",
        'נ' to "Nun", 'ן' to "Nun Sofit", 'ס' to "Sámej", 'ע' to "Ayin", 'פ' to "Pe",
        'ף' to "Pe Sofit", 'צ' to "Tzadi", 'ץ' to "Tzadi Sofit", 'ק' to "Qof",
        'ר' to "Resh", 'ש' to "Shin", 'ת' to "Tav"
    )

    private val SIGNIFICADOS_LETRAS: Map<Char, String> = mapOf(
        'א' to "Buey, líder, fuerza primordial, unidad de Dios",
        'ב' to "Casa, morada, dualidad santa, creación",
        'ג' to "Camello, generosidad, recompensa al necesitado",
        'ד' to "Puerta, humildad, conciencia de dependencia",
        'ה' to "Ventana, aliento divino de vida, revelación",
        'ו' to "Gancho, conexión entre cielo y tierra",
        'ז' to "Espada, corona, sustento divino, Shabbat",
        'ח' to "Muro, valla, vida eterna, trascendencia",
        'ט' to "Recipiente, bondad oculta, gestación",
        'י' to "Mano divina, punto primordial infinito",
        'כ' to "Palma de la mano, corona, recepción de bendición",
        'ך' to "Palma extendida hacia lo profundo (Final)",
        'ל' to "Aguijón de pastor, corazón sabio, elevación",
        'מ' to "Aguas vivas, sabiduría oculta, purificación",
        'ם' to "Aguas cerradas, misterio de la redención final",
        'נ' to "Pez, fidelidad, alma que brilla en la oscuridad",
        'ן' to "Fidelidad erguida hacia la eternidad (Final)",
        'ס' to "Sostén, círculo protector divino, apoyo incondicional",
        'ע' to "Ojo, visión espiritual, discernimiento divino",
        'פ' to "Boca, palabra creadora, expresión del alma",
        'ף' to "Boca abierta en alabanza eterna (Final)",
        'צ' to "Anzuelo, el justo (Tzadik), equilibrio cósmico",
        'ץ' to "Justicia eterna consumada (Final)",
        'ק' to "Ojo de aguja, santidad suprema, transformación",
        'ר' to "Cabeza, elección entre grandeza o vanidad",
        'ש' to "Dientes, fuego divino, triple manifestación",
        'ת' to "Cruz de señal, pacto, sello de la verdad (Emet)"
    )

    /**
     * Calcula la Gematría estándar de un texto hebreo (equivalente a la función JS)
     */
    fun calcularGematriaEstandar(textoHebreo: String): Int {
        var total = 0
        for (char in textoHebreo) {
            val valor = VALORES_GEMATRIA[char]
            if (valor != null) {
                total += valor
            }
        }
        return total
    }

    /**
     * Obtiene el desglose letra por letra
     */
    fun obtenerDesglose(textoHebreo: String): List<LetterBreakdown> {
        val list = mutableListOf<LetterBreakdown>()
        for (char in textoHebreo) {
            val valor = VALORES_GEMATRIA[char]
            if (valor != null) {
                list.add(
                    LetterBreakdown(
                        letterSymbol = char.toString(),
                        letterName = NOMBRES_LETRAS[char] ?: "Letra $char",
                        value = valor,
                        meaningSummary = SIGNIFICADOS_LETRAS[char] ?: "Fuerza espiritual de la letra $char"
                    )
                )
            }
        }
        return list
    }
}
