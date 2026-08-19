package com.example.data.repository

import com.example.data.model.BiblicalQuote
import com.example.data.model.HebrewLetter
import com.example.data.model.ImportantWord
import com.example.data.model.QuoteComment

object HebrewLetterData {

    val letters: List<HebrewLetter> = listOf(
        HebrewLetter(
            id = "alef",
            symbol = "א",
            name = "Alef",
            nameHebrew = "אָלֶף",
            transliteration = "' / Silenciosa (Toma el sonido de la vocal)",
            numericValue = 1,
            colorHex = "#9E721D",
            pictographSymbol = "𓃾",
            pictographMeaning = "Cabeza de buey, Fuerza, Líder, Padre, Unicidad Divina",
            originEvolution = "Evolucionó desde la cabeza de buey proto-sinaítica (1500 a.C.), pasando por el fenicio, hasta la grafía asiria cuadrada usada en los rollos de la Torá.",
            visualComparison = "Pictograma (Buey 𓃾) ➔ Paleo-Hebreo (𐤀) ➔ Hebreo Cuadrado (א) ➔ Cursiva (א)",
            gematriaExplanation = "El valor 1 representa el Dios Único (Echad). Su nombre 'Alef' (א-ל-פ) suma 111, conectando con 'Elef' (Mil), enseñando que el Uno abarca la infinidad.",
            importantWords = listOf(
                ImportantWord("אֱלֹהִים", "Elohim", "Dios Creador", 86, "Primer nombre de Dios en la Biblia"),
                ImportantWord("אָב", "Av", "Padre", 3, "Principio de la familia y el pacto"),
                ImportantWord("אֱמֶת", "Emet", "Verdad", 441, "Compuesto por la primera, media y última letra del alefato")
            ),
            letterRelations = "Alef está compuesta por una Yod superior (Cielo), una Yod inferior (Tierra) y una Vav diagonal que las conecta (la Torá/el Mediador).",
            pardesPeshat = "Peshat: Primera letra del alfabeto hebreo, consonante gutural silenciosa.",
            pardesRemez = "Remez: Insinúa la unidad divina y la corona primordial antes de la creación.",
            pardesDerash = "Derash: Aunque la Torá comienza con Bet (Bereshit), Dios dio los Diez Mandamientos comenzando con Alef (Anochi).",
            pardesSod = "Sod: Representa Keter (la Corona), la voluntad pura e incomprensible del Creador.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Bereshit Rabbah 1:10", "La letra Alef se quejó por no abrir la creación, pero Dios la recompensó otorgándole la apertura de los Mandamientos."),
                QuoteComment("Baal Shem Tov", "Sefer Baal Shem Tov", "En cada Alef habita el aliento de vida primordial con el que el mundo fue formado.")
            ),
            talmudReferences = "Talmud Babilónico, Shabbat 104a: 'Alef-Bet significa Alaf Binah (Aprende la Sabiduría)'.",
            midrashReferences = "Midrash Otiyot de Rabbi Akiva: Explicación mística sobre la humildad de la Alef.",
            kabbalahMeaning = "Sefer Yetzirah enseña que Alef es una de las tres letras madres, representando el Aire (Ruach) que equilibra Agua y Fuego.",
            spiritualApplication = "Cultivar la humildad profunda para convertirse en un canal limpio de la luz divina.",
            practicalApplication = "Iniciar cada día reconociendo la presencia unificadora del Creador.",
            biblicalQuotes = listOf(
                BiblicalQuote("Éxodo 20:2", "אָנֹכִי יְהוָה אֱלֹהֶיךָ", "Yo soy el Señor tu Dios...", "Comienzo del Decálogo con Alef."),
                BiblicalQuote("Isaías 44:6", "אֲנִי רִאשׁוֹן וַאֲנִי אַחֲרוֹן", "Yo soy el primero y el último...", "Declaración de eternidad.")
            ),
            bibliography = listOf("Sefer Yetzirah", "Zohar Hakadosh", "Talmud Shabbat 104a")
        ),
        HebrewLetter(
            id = "bet",
            symbol = "ב",
            name = "Bet",
            nameHebrew = "בֵּית",
            transliteration = "B / V",
            numericValue = 2,
            colorHex = "#1E2A38",
            pictographSymbol = "𓉐",
            pictographMeaning = "Casa, Hogar, Tienda, Morada interior, Familia",
            originEvolution = "Deriva del plano de una tienda o casa nómada semítica. Abierta por un lado para indicar hospitalidad.",
            visualComparison = "Pictograma (Casa 𓉐) ➔ Paleo-Hebreo (𐤁) ➔ Hebreo Cuadrado (ב) ➔ Cursiva (ב)",
            gematriaExplanation = "El valor 2 simboliza la dualidad de la creación (Cielo/Tierra) y la bendición (Berajá).",
            importantWords = listOf(
                ImportantWord("בַּיִת", "Bayit", "Casa/Hogar", 412, "Lugar de habitación divina"),
                ImportantWord("בְּרָכָה", "Berajah", "Bendición", 227, "Flujo de abundancia celestial"),
                ImportantWord("בֵּן", "Ben", "Hijo", 52, "Continuidad del pacto")
            ),
            letterRelations = "Sigue a Alef como la vasija que recibe la luz del Creador y la manifiesta en la creación.",
            pardesPeshat = "Peshat: Segunda letra del alefato, consonante oclusiva labial.",
            pardesRemez = "Remez: Insinúa los dos mundos: Olam Hazeh y Olam Haba.",
            pardesDerash = "Derash: La Torá comienza con Bet (Bereshit) para enseñar que el mundo fue creado con bendición.",
            pardesSod = "Sod: Representa Binah (Entendimiento) que construye la casa del universo.",
            rabbinicComments = listOf(
                QuoteComment("Rambam", "Moreh Nevuchim", "La casa física es la sombra del santuario espiritual."),
                QuoteComment("Zohar", "Bereshit", "Bet es la letra de la Creación porque su forma está abierta hacia adelante.")
            ),
            talmudReferences = "Talmud Chagigah 160a: '¿Por qué la Torá comienza con Bet? Porque está cerrada arriba y abierta al frente'.",
            midrashReferences = "Midrash Rabbah Bereshit 1:5: La elección de Bet para abrir las escrituras.",
            kabbalahMeaning = "Asociada con la Sefirá de Binah, el útero cósmico donde se forman las almas.",
            spiritualApplication = "Transformar el hogar en un santuario en miniatura lleno de paz.",
            practicalApplication = "Bendecir activamente la vida de los demás mediante palabras edificantes.",
            biblicalQuotes = listOf(
                BiblicalQuote("Génesis 1:1", "בְּרֵאשִׁית בָּרָא אֱלֹהִים", "En el principio creó Dios...", "Apertura de la Torá con Bet."),
                BiblicalQuote("Josué 24:15", "וַאֲנִי וּבֵיתִי נַעֲבֹד אֶת-יְהוָה", "Yo y mi casa serviremos al Señor.", "Consagración del hogar.")
            ),
            bibliography = listOf("Sefer HaBahir", "Zohar Bereshit", "Talmud Chagigah")
        ),
        HebrewLetter(
            id = "gimel",
            symbol = "ג",
            name = "Gimel",
            nameHebrew = "גִּימֶל",
            transliteration = "G (Gato)",
            numericValue = 3,
            colorHex = "#2D6A4F",
            pictographSymbol = "𓃘",
            pictographMeaning = "Camello, Generosidad, Recompensa, Elección",
            originEvolution = "Inspirada en el cuello y cabeza de un camello que atraviesa el desierto llevando riqueza.",
            visualComparison = "Pictograma (Camello 𓃘) ➔ Paleo-Hebreo (𐤂) ➔ Hebreo Cuadrado (ג) ➔ Cursiva (ג)",
            gematriaExplanation = "El 3 representa la estabilidad (trípode), la reconciliación y el flujo de la gracia (Gemilut Chasadim).",
            importantWords = listOf(
                ImportantWord("גָּמָל", "Gamal", "Camello", 73, "Portador de provisiones"),
                ImportantWord("גֶּבֶר", "Gever", "Hombre fuerte / Valiente", 205, "Dominio propio e integridad"),
                ImportantWord("גְּאוּלָה", "Geulah", "Redención", 49, "Liberación y rescate divino")
            ),
            letterRelations = "La Gimel parece una persona corriendo hacia la Dalet (el pobre) para darle caridad.",
            pardesPeshat = "Peshat: Tercera letra, sonido velar sonoro.",
            pardesRemez = "Remez: Insinúa los tres Patriarcas (Abraham, Isaac y Jacob) y las tres partes del Tanaj.",
            pardesDerash = "Derash: 'Gimel-Dalet: Gemol Dalim (Sé generoso con los necesitados)'.",
            pardesSod = "Sod: Representa Hesed (Misericordia activa) que fluye sin cesar.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Shabbat 104a", "La patita de la Gimel se extiende hacia la Dalet porque el benefactor corre tras el pobre."),
                QuoteComment("Rabbi Akiva", "Otiyot deRabbi Akiva", "Gimel es el camello de la Providencia.")
            ),
            talmudReferences = "Talmud Shabbat 104a: Disertación sobre la caridad y la forma de Gimel.",
            midrashReferences = "Midrash Tanchuma: La generosidad de Rebeca al dar de beber a los camellos.",
            kabbalahMeaning = "En Sefer Yetzirah, Gimel es una de las 7 letras dobles y rige sobre Riqueza y Pobreza.",
            spiritualApplication = "Ser un dador alegre y proactivo que busca ayudar al necesitado.",
            practicalApplication = "Apartar con fidelidad el diezmo y la tzedaká para causas justas.",
            biblicalQuotes = listOf(
                BiblicalQuote("Génesis 24:19", "גַּם לִגְמַלֶּיךָ אֶשְׁאָב", "También para tus camellos sacaré agua...", "La prueba de Rebeca."),
                BiblicalQuote("Salmo 116:7", "כִּי-יְהוָה גָּמַל עָלָיְכִי", "...porque el Señor te ha colmado de bien.", "Recompensa divina.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Pirkei Avot")
        ),
        HebrewLetter(
            id = "dalet",
            symbol = "ד",
            name = "Dalet",
            nameHebrew = "דָּלֶת",
            transliteration = "D",
            numericValue = 4,
            colorHex = "#7F4F24",
            pictographSymbol = "🚪",
            pictographMeaning = "Puerta, Humildad, Apertura, Pobreza (Dal)",
            originEvolution = "Evolucionó de la puerta de una tienda de campaña semítica o una hoja de madera colgada.",
            visualComparison = "Pictograma (Puerta 🚪) ➔ Paleo-Hebreo (𐤃) ➔ Hebreo Cuadrado (ד) ➔ Cursiva (ד)",
            gematriaExplanation = "El 4 representa los cuatro puntos cardinales, las cuatro matronas y la estructura material del mundo.",
            importantWords = listOf(
                ImportantWord("דֶּלֶת", "Delet", "Puerta", 434, "Punto de acceso y transición sagrada"),
                ImportantWord("דָּוִד", "David", "Amado / Rey David", 14, "Dinastía mesiánica"),
                ImportantWord("דַּעַת", "Daat", "Conocimiento espiritual", 474, "Conexión entre mente y corazón")
            ),
            letterRelations = "La Dalet le vuelve la espalda a la Gimel por vergüenza de su pobreza, pero recibe su caridad.",
            pardesPeshat = "Peshat: Cuarta letra del alfabeto.",
            pardesRemez = "Remez: Insinúa la condición del ser humano humilde (Dal) que reconoce su dependencia de Dios.",
            pardesDerash = "Derash: La diferencia entre Dalet (ד) y Resh (ר) es un pequeño punto (Yotzer).",
            pardesSod = "Sod: Representa Malchut (el Reino), la vasija receptora.",
            rabbinicComments = listOf(
                QuoteComment("Baal Shem Tov", "Tzavaat HaRibash", "La persona humilde baha su dintel para dejar entrar la gloria divina."),
                QuoteComment("Rambam", "Hilchot Tzedakah", "La mayor caridad preserva la dignidad del pobre.")
            ),
            talmudReferences = "Talmud Shabbat 104a y Sanhedrin 98a sobre la venida del Mesías a las puertas.",
            midrashReferences = "Midrash Tehillim sobre el Salmo 24: 'Alzad, oh puertas, vuestras cabezas'.",
            kabbalahMeaning = "Sefer Yetzirah: rige sobre la Sombra y el Dominio.",
            spiritualApplication = "Reconocer la necesidad espiritual ante Dios con corazón humilde.",
            practicalApplication = "Mantener las puertas del corazón y la casa abiertas para el prójimo.",
            biblicalQuotes = listOf(
                BiblicalQuote("Salmo 24:7", "שְׂאוּ שְׁעָרִים רָאשֵׁיכֶם", "Alzad, oh puertas, vuestras cabezas...", "Entrada del Rey de Gloria."),
                BiblicalQuote("Deuteronomio 6:9", "וּכְתַבְתָּם עַל-מְזֻזוֹת בֵּיתֶךָ וּבִשְׁעָרֶיךָ", "Y las escribirás en las puertas...", "La Mezuzá.")
            ),
            bibliography = listOf("Sefer Yetzirah", "Talmud Shabbat 104a", "Zohar Tetzaveh")
        ),
        HebrewLetter(
            id = "he",
            symbol = "ה",
            name = "He",
            nameHebrew = "הֵא",
            transliteration = "H (Suave)",
            numericValue = 5,
            colorHex = "#9B2226",
            pictographSymbol = "𓀠",
            pictographMeaning = "Ventana, Aliento Divino, Revelación, Contemplación",
            originEvolution = "Derivada de la figura humana alzando los brazos en adoración.",
            visualComparison = "Pictograma (Hombre adorando 𓀠) ➔ Paleo-Hebreo (𐤄) ➔ Hebreo Cuadrado (ה) ➔ Cursiva (ה)",
            gematriaExplanation = "El 5 simboliza los 5 libros de la Torá y los 5 niveles del alma (Nefesh, Ruach, Neshamah, Chayah, Yechidah).",
            importantWords = listOf(
                ImportantWord("הֵיכָל", "Heichal", "Palacio / Templo", 65, "Morada gloriosa divina"),
                ImportantWord("הוֹד", "Hod", "Esplendor / Majestad", 15, "Sefirá del agradecimiento"),
                ImportantWord("הֲלָכָה", "Halajá", "El Camino", 65, "Caminar diario en los mandamientos")
            ),
            letterRelations = "Aparece dos veces en el Nombre Inefable YHVH (י-ה-ו-ה), representando el aliento de vida.",
            pardesPeshat = "Peshat: Quinta letra, consonante laríngea suave.",
            pardesRemez = "Remez: Representa el aliento con el que Dios creó el mundo presente.",
            pardesDerash = "Derash: La apertura en la pata izquierda de la He enseña que siempre hay espacio para el arrepentimiento.",
            pardesSod = "Sod: Representa Binah y Malchut en el árbol místico.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Génesis 2:4", "Con la letra He fueron creados los cielos y la tierra."),
                QuoteComment("Zohar", "Volumen 1:28a", "La He es la madre de la creación.")
            ),
            talmudReferences = "Talmud Menachot 29b: '¿Por qué este mundo fue creado con la He? Porque parece un pórtico'.",
            midrashReferences = "Midrash Bereshit Rabbah 39: La adición de He al nombre de Abraham.",
            kabbalahMeaning = "Representa el elemento Aire sutil y la dimensión del habla articulada.",
            spiritualApplication = "Inspirar el aliento de la oración continua y mantener abierto el arrepentimiento.",
            practicalApplication = "Expresarse con palabras puras y de edificación.",
            biblicalQuotes = listOf(
                BiblicalQuote("Génesis 17:5", "וְהָיָה שִׁמְךָ אַבְרָהָם", "...y tu nombre será Abraham.", "Inserción de He en el nombre."),
                BiblicalQuote("Salmo 150:6", "כֹּל הַנְּשָׁמָה תְּהַלֵּל יָהּ", "Todo lo que respira alabe al Señor.", "Aliento de alabanza.")
            ),
            bibliography = listOf("Talmud Menachot 29b", "Sefer Yetzirah", "Zohar Bereshit")
        ),
        HebrewLetter(
            id = "vav",
            symbol = "ו",
            name = "Vav",
            nameHebrew = "וָו",
            transliteration = "V / W / O / U",
            numericValue = 6,
            colorHex = "#9E721D",
            pictographSymbol = "𓍇",
            pictographMeaning = "Clavo, Estaca de tienda, Conexión, Unión",
            originEvolution = "Evolucionó de una estaca o clavija de tienda utilizada para fijar la morada.",
            visualComparison = "Pictograma (Clavo 𓍇) ➔ Paleo-Hebreo (𐤅) ➔ Hebreo Cuadrado (ו) ➔ Cursiva (ו)",
            gematriaExplanation = "El 6 representa los 6 días de la creación y las 6 direcciones del espacio físico.",
            importantWords = listOf(
                ImportantWord("וָו", "Vav", "Clavo / Conector", 12, "Unión en el pergamino de la Torá"),
                ImportantWord("וַיֹּאמֶר", "Vayomer", "Y dijo...", 257, "Conjugación consecutiva profética"),
                ImportantWord("וֶאֱמֶת", "Ve-Emet", "Y Verdad", 447, "Conjunción copulativa divina")
            ),
            letterRelations = "Actúa como la conjunción 'Y' en hebreo, conectando conceptos distantes.",
            pardesPeshat = "Peshat: Sexta letra, consonante y vocal.",
            pardesRemez = "Remez: Insinúa el ser humano creado en el sexto día.",
            pardesDerash = "Derash: En los rollos de la Torá, cada columna comienza con Vav (Tikkun HaVavim).",
            pardesSod = "Sod: Representa Zeir Anpin (las 6 emociones divinas) en Kabbalah.",
            rabbinicComments = listOf(
                QuoteComment("Ramban", "Éxodo", "Vav es el pilar de luz que une la causa con el efecto."),
                QuoteComment("Zohar", "Pinchas", "La Vav es la verdad que no vacila.")
            ),
            talmudReferences = "Talmud Sanhedrin 22a sobre la escritura del pergamino sagrado.",
            midrashReferences = "Midrash Tanchuma Bereshit: La creación del hombre en el sexto día.",
            kabbalahMeaning = "En Sefer Yetzirah gobierna el mes de Iyar y la facultad del Pensamiento.",
            spiritualApplication = "Ser un puente de reconciliación y unidad entre las personas.",
            practicalApplication = "Fijar firmemente nuestras convicciones éticas como estacas.",
            biblicalQuotes = listOf(
                BiblicalQuote("Éxodo 27:10", "וָוֵי הָעַמֻּדִים", "...los clavos de las columnas...", "Uso de Vav en el Tabernáculo."),
                BiblicalQuote("Génesis 1:31", "וַיַּרְא אֱלֹהִים כָּל-אֲשֶׁר עָשָׂה", "Y vio Dios todo lo que había hecho...", "Sexto día.")
            ),
            bibliography = listOf("Zohar Pinchas", "Sefer Yetzirah", "Tikunei Zohar")
        ),
        HebrewLetter(
            id = "zayin",
            symbol = "ז",
            name = "Zayin",
            nameHebrew = "זַיִן",
            transliteration = "Z",
            numericValue = 7,
            colorHex = "#1E2A38",
            pictographSymbol = "🗡️",
            pictographMeaning = "Espada, Cetro, Sustento, Corona, Reposo Sagrado",
            originEvolution = "Proviene de un arma corta o hoz semítica usada para segar y defender.",
            visualComparison = "Pictograma (Espada 🗡️) ➔ Paleo-Hebreo (𐤆) ➔ Hebreo Cuadrado (ז) ➔ Cursiva (ז)",
            gematriaExplanation = "El 7 representa la plenitud del Shabat (7º día) y las 7 semanas de la Cuenta del Omer.",
            importantWords = listOf(
                ImportantWord("זַיִת", "Zayit", "Olivo", 417, "Símbolo de unción y luz pura"),
                ImportantWord("זָכָר", "Zachar", "Recordar / Varón", 227, "Memoria viva del pacto"),
                ImportantWord("זָקֵן", "Zaqen", "Anciano sabio", 157, "Sabiduría adquirida")
            ),
            letterRelations = "Coronada con un Tag (corona) de tres picos, indicando majestad espiritual.",
            pardesPeshat = "Peshat: Séptima letra del alefato.",
            pardesRemez = "Remez: Insinúa el reposo sagrado del Shabat que protege la vida.",
            pardesDerash = "Derash: 'Zayin-Zanah: Nutre (Zan) a todas las criaturas'.",
            pardesSod = "Sod: Representa la Esposa Coronada (Eshet Chayil).",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Shabbat 104a", "Zayin es el sostén (Zan) con el que Dios alimenta al mundo."),
                QuoteComment("Baal Shem Tov", "Keter Shem Tov", "La espada de Zayin corta las ilusiones.")
            ),
            talmudReferences = "Talmud Menachot 29b: Sobre las coronas (Tagim) sobre la letra Zayin.",
            midrashReferences = "Midrash Rabbah Vayikra 24: La santidad del número siete.",
            kabbalahMeaning = "Gobernada por la facultad del Movimiento y la Sefirá de Netzach.",
            spiritualApplication = "Defender la verdad bíblica con la palabra de Dios como espada.",
            practicalApplication = "Guardar el Shabat con alegría y gratitud por la provisión.",
            biblicalQuotes = listOf(
                BiblicalQuote("Éxodo 20:8", "זָכוֹר אֶת-יוֹם הַשַּׁבָּת", "Acuérdate del día del sábado...", "Mandato de recordar."),
                BiblicalQuote("Zacarías 4:6", "לֹא בְחַיִל וְלֹא בְכֹחַ", "No con ejército ni con fuerza...", "Victoria espiritual.")
            ),
            bibliography = listOf("Talmud Menachot 29b", "Sefer Yetzirah", "Zohar Vaera")
        ),
        HebrewLetter(
            id = "chet",
            symbol = "ח",
            name = "Chet",
            nameHebrew = "חֵית",
            transliteration = "Ch (J guttural)",
            numericValue = 8,
            colorHex = "#2D6A4F",
            pictographSymbol = "🧱",
            pictographMeaning = "Cercado, Pared, Refugio, Vida (Chai), Gracia (Chen)",
            originEvolution = "Derivada del dibujo de una valla o pared de caña usada para proteger el ganado.",
            visualComparison = "Pictograma (Valla 🧱) ➔ Paleo-Hebreo (𐤇) ➔ Hebreo Cuadrado (ח) ➔ Cursiva (ח)",
            gematriaExplanation = "El 8 representa trascendencia por encima del orden natural (7 días), resurrección y la vida (Chai = 18).",
            importantWords = listOf(
                ImportantWord("חַיִּים", "Chayim", "Vida", 68, "Principio vital concedido por el Creador"),
                ImportantWord("חֵן", "Chen", "Gracia", 58, "Favor inmerecido ante Dios"),
                ImportantWord("חָכְמָה", "Chochmah", "Sabiduría", 73, "Luz primordial de la mente")
            ),
            letterRelations = "Formada por la unión de Vav y Zayin conectadas por un puente superior (Chatap).",
            pardesPeshat = "Peshat: Octava letra del alfabeto.",
            pardesRemez = "Remez: Insinúa el octavo día de la circuncisión (Brit Milá) y la trascendencia.",
            pardesDerash = "Derash: Chet representa el pecado (Chet) si la persona derriba el cercado divino.",
            pardesSod = "Sod: Representa Chochmah (Sabiduría) en el mundo de Atzilut.",
            rabbinicComments = listOf(
                QuoteComment("Rambam", "Hilchot Teshuvá", "La vida verdadera es la adhesión espiritual a la Verdad Divina."),
                QuoteComment("Zohar", "Vayikra", "Chet es la puerta por donde ingresa el aliento vital.")
            ),
            talmudReferences = "Talmud Shabbat 104a sobre la diferencia entre Chet y He.",
            midrashReferences = "Midrash Rabbah Shir HaShirim sobre el significado del número 8.",
            kabbalahMeaning = "Rige el mes de Tamuz y el sentido de la Vista en Sefer Yetzirah.",
            spiritualApplication = "Establecer límites saludables y buscar la santidad de vida.",
            practicalApplication = "Promover la vida y la salud en todas nuestras acciones cotidianas.",
            biblicalQuotes = listOf(
                BiblicalQuote("Deuteronomio 30:19", "וּבָחַרְתָּ בַּחַיִּים", "...escoge, pues, la vida...", "Elección del pacto."),
                BiblicalQuote("Proverbios 3:18", "עֵץ-חַיִּים הִיא לַמַּחֲזִיקִים בָּהּ", "Árbol de vida es a los que de ella echan mano...", "La Torá como Vida.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Zohar Vayikra")
        ),
        HebrewLetter(
            id = "tet",
            symbol = "ט",
            name = "Tet",
            nameHebrew = "טֵית",
            transliteration = "T (Enfática)",
            numericValue = 9,
            colorHex = "#9E721D",
            pictographSymbol = "🏺",
            pictographMeaning = "Vasija, Serpiente enrollada, Escudo, Bondad oculta (Tov)",
            originEvolution = "Proviene del pictograma de una cesta tejida o rueda sagrada de alfarero.",
            visualComparison = "Pictograma (Cesta 🏺) ➔ Paleo-Hebreo (𐤈) ➔ Hebreo Cuadrado (ט) ➔ Cursiva (ט)",
            gematriaExplanation = "El 9 simboliza la verdad (cuyo valor se mantiene al multiplicar), los 9 meses de gestación y la bondad (Tov = 17).",
            importantWords = listOf(
                ImportantWord("טוֹב", "Tov", "Bueno / Bien", 17, "Evaluación divina de la creación"),
                ImportantWord("טַהוֹר", "Tahor", "Puro / Limpio", 225, "Estado de pureza ritual y del corazón"),
                ImportantWord("טַל", "Tal", "Rocío", 39, "Rocío de resurrección y bendición")
            ),
            letterRelations = "La Tet se inclina hacia adentro como una vasija que guarda un tesoro escondido.",
            pardesPeshat = "Peshat: Novena letra del alefato.",
            pardesRemez = "Remez: Insinúa que el bien divino a menudo está oculto tras las pruebas de la vida.",
            pardesDerash = "Derash: Primera vez que Tet aparece en la Torá es en la palabra 'Tov' (Bueno - Génesis 1:4).",
            pardesSod = "Sod: Representa Yesod (El Fundamento) que guarda el pacto sagrado.",
            rabbinicComments = listOf(
                QuoteComment("Baal Shem Tov", "Sefer Baal Shem Tov", "La luz oculta del primer día fue guardada dentro de la Tet para los justos."),
                QuoteComment("Rashi", "Génesis 1:4", "Vio Dios que la luz era buena y la apartó para el futuro.")
            ),
            talmudReferences = "Talmud Baba Kama 55a: 'Aquel que ve la letra Tet en un sueño es un buen augurio'.",
            midrashReferences = "Midrash Otiyot deRabbi Akiva sobre la corona de la Tet.",
            kabbalahMeaning = "En Sefer Yetzirah rige sobre el mes de Av y el sentido de la Audición.",
            spiritualApplication = "Buscar el bien supremo en medio de las dificultades con fe inquebrantable.",
            practicalApplication = "Purificar nuestros pensamientos e intenciones para ofrecer una vasija limpia.",
            biblicalQuotes = listOf(
                BiblicalQuote("Génesis 1:4", "וַיַּרְא אֱלֹהִים אֶת-הָאוֹר כִּי-טוֹב", "Y vio Dios que la luz era buena...", "Primera aparición de Tet."),
                BiblicalQuote("Salmo 34:9", "טַעֲמוּ וּרְאוּ כִּי-טוֹב יְהוָה", "Gustad y ved que es bueno el Señor...", "La bondad divina.")
            ),
            bibliography = listOf("Talmud Baba Kama 55a", "Sefer Yetzirah", "Zohar Bereshit")
        ),
        HebrewLetter(
            id = "yod",
            symbol = "י",
            name = "Yod",
            nameHebrew = "יוֹד",
            transliteration = "Y / I",
            numericValue = 10,
            colorHex = "#9B2226",
            pictographSymbol = "✋",
            pictographMeaning = "Mano abierta, Trabajo, Semilla, Humildad, Punto Inicial",
            originEvolution = "Representada originalmente por un brazo y mano extendida listos para actuar.",
            visualComparison = "Pictograma (Mano ✋) ➔ Paleo-Hebreo (𐤉) ➔ Hebreo Cuadrado (י) ➔ Cursiva (י)",
            gematriaExplanation = "El 10 representa los 10 Mandamientos, las 10 Enunciaciones de la Creación y la plenitud del diezmo.",
            importantWords = listOf(
                ImportantWord("יָד", "Yad", "Mano / Poder", 14, "Instrumento de acción y bendición"),
                ImportantWord("יְהוָה", "YHVH", "Nombre Inefable", 26, "El Creador Eterno"),
                ImportantWord("יֵשׁוּעָה", "Yeshua", "Salvación", 391, "Rescate y victoria divina")
            ),
            letterRelations = "Es la letra más pequeña físicamente, pero está presente en la construcción de todas las demás letras del alefato.",
            pardesPeshat = "Peshat: Décima letra del alfabeto hebreo.",
            pardesRemez = "Remez: Insinúa el punto primordial de luz concentrada (Tzimtzum).",
            pardesDerash = "Derash: 'Ni una Yod ni una tilde pasará de la ley hasta que todo se cumpla'.",
            pardesSod = "Sod: Representa Chochmah (Sabiduría pura), la chispa Divina inalterable.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Menachot 29a", "El mundo venidero fue creado con la Yod por su pequeñez y pureza."),
                QuoteComment("Zohar", "Bereshit 6a", "La Yod es el punto donde el Infinito se concentra para tocar la materia.")
            ),
            talmudReferences = "Talmud Menachot 29a y Sanhedrin 107a sobre la santidad de la Yod.",
            midrashReferences = "Midrash Tanchuma: La Yod que fue tomada del nombre de Sarai.",
            kabbalahMeaning = "Es la semilla de todas las letras y representa el mundo primordial de Atzilut.",
            spiritualApplication = "Permanecer pequeños y humildes para que la grandeza de Dios brille.",
            practicalApplication = "Extender la mano con generosidad para ayudar y trabajar con diligencia.",
            biblicalQuotes = listOf(
                BiblicalQuote("Éxodo 15:6", "יְמִינְךָ יְהוָה נֶאְדָּרִי בַּכֹּחַ", "Tu diestra, oh Señor, ha sido magnificada en poder...", "El poder de la mano divina."),
                BiblicalQuote("Salmo 145:16", "פּוֹתֵחַ אֶת-יָדֶךָ", "Abres tu mano y colmas de bendición...", "Provisión divina.")
            ),
            bibliography = listOf("Talmud Menachot 29a", "Sefer Yetzirah", "Zohar Hakadosh")
        ),
        HebrewLetter(
            id = "kaf",
            symbol = "כ",
            name = "Kaf",
            nameHebrew = "כַּף",
            transliteration = "K / Ch",
            numericValue = 20,
            colorHex = "#1E2A38",
            pictographSymbol = "🤲",
            pictographMeaning = "Palma de la mano, Corona (Keter), Vasija amoldable, Cobertura",
            originEvolution = "Deriva de la curva de la palma de la mano abierta lista para recibir o coronar.",
            visualComparison = "Pictograma (Palma 🤲) ➔ Paleo-Hebreo (𐤋) ➔ Hebreo Cuadrado (כ) ➔ Cursiva (כ)",
            gematriaExplanation = "El 20 representa el valor de las dos manos humanas unidas y la capacidad de contener la bendición.",
            importantWords = listOf(
                ImportantWord("כֶּתֶר", "Keter", "Corona", 620, "La sefirá más elevada del intelecto divino"),
                ImportantWord("כָּבוֹד", "Kavod", "Gloria / Honor", 32, "Peso de la presencia divina"),
                ImportantWord("כֹּהֵן", "Kohen", "Sacerdote", 75, "Servidor sagrado en el Santuario")
            ),
            letterRelations = "Tiene una forma final (Kaf Sofit ך) que se extiende hacia abajo bendiciendo la tierra.",
            pardesPeshat = "Peshat: Undécima letra del alefato.",
            pardesRemez = "Remez: Insinúa la vasija receptora que se moldea a la voluntad del Alfarero Celestial.",
            pardesDerash = "Derash: 'Kaf-Keter: Si doblegas tu orgullo, serás coronado con gloria divina'.",
            pardesSod = "Sod: Representa Keter Elyon (La Corona Suprema) descendiendo hacia Malchut.",
            rabbinicComments = listOf(
                QuoteComment("Rambam", "Hilchot De'ot", "La gloria divina reposa sobre aquellos que someten su voluntad con humildad."),
                QuoteComment("Baal Shem Tov", "Sefer Imrei Kodesh", "La Kaf es la mano que moldea el barro del alma.")
            ),
            talmudReferences = "Talmud Shabbat 104a sobre la Kaf doblada y la Kaf recta final.",
            midrashReferences = "Midrash Rabbah Bamidbar 14 sobre las palmas llenas de incienso.",
            kabbalahMeaning = "Sefer Yetzirah: Rige sobre la Vida y la Muerte, asociada con la Sefirá de Keter.",
            spiritualApplication = "Dejarse amoldar por Dios como barro en manos del alfarero.",
            practicalApplication = "Usar las manos para edificar, sanar y servir a la comunidad.",
            biblicalQuotes = listOf(
                BiblicalQuote("Salmo 24:4", "נְקִי כַפַּיִם וּבַר-לֵבָב", "El limpio de manos y puro de corazón...", "Requisito para subir al monte."),
                BiblicalQuote("Isaías 49:16", "הֵן עַל-כַּפַּיִם חַקֹּתִיךְ", "He aquí que en las palmas de mis manos te tengo esculpida...", "Amor eterno.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Zohar Tetzaveh")
        ),
        HebrewLetter(
            id = "lamed",
            symbol = "ל",
            name = "Lamed",
            nameHebrew = "לָמֶד",
            transliteration = "L",
            numericValue = 30,
            colorHex = "#2D6A4F",
            pictographSymbol = "𓌃",
            pictographMeaning = "Aguijón de buey, Vara de pastor, Enseñanza, Ascenso del corazón",
            originEvolution = "Evolucionó de una vara de pastor o aguijón usado para guiar al ganado en el camino correcto.",
            visualComparison = "Pictograma (Vara 𓌃) ➔ Paleo-Hebreo (𐤌) ➔ Hebreo Cuadrado (ל) ➔ Cursiva (ל)",
            gematriaExplanation = "El 30 es el valor del corazón de la enseñanza. Es la letra más alta del alefato, sobresaliendo por encima de todas.",
            importantWords = listOf(
                ImportantWord("לֵב", "Lev", "Corazón", 32, "Centro de las emociones y la voluntad"),
                ImportantWord("לִמּוּד", "Limmud", "Estudio / Aprendizaje", 84, "Dedicación constante a la Torá"),
                ImportantWord("לֶחֶם", "Lechem", "Pan / Sustento", 78, "Alimento físico y espiritual")
            ),
            letterRelations = "Ubicada en el centro del alefato. Su torre se eleva hacia los cielos buscando la luz superior.",
            pardesPeshat = "Peshat: Duodécima letra del alfabeto hebreo.",
            pardesRemez = "Remez: Insinúa el deseo del alma (Lev Mevin) de ascender y aprender la sabiduría divina.",
            pardesDerash = "Derash: 'Lamed: Lomed (Aprender) y Melammed (Enseñar) son las dos alas del sabio'.",
            pardesSod = "Sod: Representa Binah elevándose hacia Keter para atraer el flujo profético.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Shabbat 104a", "Lamed es como una torre que vuela en el aire buscando la verdad."),
                QuoteComment("Pirkei Avot 4:1", "Ben Zoma", "¿Quién es sabio? Aquel que aprende de cada ser humano.")
            ),
            talmudReferences = "Talmud Shabbat 104a: 'Lamed-Medabber: El corazón que comprende la sabiduría'.",
            midrashReferences = "Midrash Otiyot deRabbi Akiva sobre la altura majestuosa de Lamed.",
            kabbalahMeaning = "En Sefer Yetzirah rige el mes de Tishrei y la facultad del Deseo / Acción.",
            spiritualApplication = "Mantener una mentalidad de estudiante constante frente a las lecciones de la vida.",
            practicalApplication = "Enseñar con paciencia y ejemplo moral a las futuras generaciones.",
            biblicalQuotes = listOf(
                BiblicalQuote("Proverbios 4:23", "מִכָּל-מִשְׁמָר נְצֹר לִבֶּךָ", "Sobre toda cosa guardada, guarda tu corazón...", "Cuidado del Lev."),
                BiblicalQuote("Deuteronomio 6:7", "וְשִׁנַּנְתָּם לְבָנֶיךָ", "Y las repetirás a tus hijos...", "El deber de enseñar (Lamed).")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Pirkei Avot", "Sefer Yetzirah")
        ),
        HebrewLetter(
            id = "mem",
            symbol = "מ",
            name = "Mem",
            nameHebrew = "מֵם",
            transliteration = "M",
            numericValue = 40,
            colorHex = "#7F4F24",
            pictographSymbol = "🌊",
            pictographMeaning = "Agua (Mayim), Océano, Revelación, Matriz primordial, 40 días de purificación",
            originEvolution = "Derivada de las olas del mar o corrientes de agua proto-sinaíticas.",
            visualComparison = "Pictograma (Olas 🌊) ➔ Paleo-Hebreo (𐤍) ➔ Hebreo Cuadrado (מ) ➔ Cursiva (מ)",
            gematriaExplanation = "El 40 representa los 40 días de Moisés en el Sinaí, los 40 años en el desierto y los 40 seah del Mikvé.",
            importantWords = listOf(
                ImportantWord("מַיִם", "Mayim", "Agua", 90, "Fuente de vida y purificación"),
                ImportantWord("מֶלֶךְ", "Melech", "Rey", 90, "Soberanía y gobierno justo"),
                ImportantWord("מָשִׁיחַ", "Mashiach", "Mesías / Ungido", 358, "Redentor prometido")
            ),
            letterRelations = "Tiene dos formas: Mem abierta (מ) que representa la revelación presente, y Mem cerrada final (ם) que guarda los secretos mesiánicos.",
            pardesPeshat = "Peshat: Decimotercera letra del alefato.",
            pardesRemez = "Remez: Insinúa las aguas de la Torá que sacian la sed del alma.",
            pardesDerash = "Derash: Mem abierta es la sabiduría revelada; Mem cerrada es el secreto reservado para el Olam Haba.",
            pardesSod = "Sod: Representa la Sefirá de Chesed (Misericordia) como un torrente de agua viva.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Isaías 9:6", "La Mem cerrada en medio de la palabra insinúa el misterio de la redención final."),
                QuoteComment("Zohar", "Volumen 3:255a", "La Torá es comparada al agua porque desciende de un lugar alto a uno bajo.")
            ),
            talmudReferences = "Talmud Shabbat 104a y Chagigah 13a sobre Mem abierta y Mem cerrada.",
            midrashReferences = "Midrash Rabbah Bamidbar 19 sobre la pozo de Miriam en el desierto.",
            kabbalahMeaning = "Es una de las tres letras madres, representando el elemento Agua primordial.",
            spiritualApplication = "Sumergirse diariamente en las aguas purificadoras de la verdad espiritual.",
            practicalApplication = "Mantener una conducta transparente y refrescar a quienes sufren cansancio.",
            biblicalQuotes = listOf(
                BiblicalQuote("Jeremías 2:13", "מְקוֹר מַיִם חַיִּים", "...fuente de agua viva...", "Dios como fuente inagotable."),
                BiblicalQuote("Isaías 11:9", "כִּי-מָלְאָה הָאָרֶץ דֵּעָה אֶת-יְהוָה כַּמַּיִם לַיָּם מְכַסִּים", "Porque la tierra será llena del conocimiento del Señor como las aguas cubren el mar...", "Profecía mesíanica.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Zohar Hakadosh")
        ),
        HebrewLetter(
            id = "nun",
            symbol = "נ",
            name = "Nun",
            nameHebrew = "נוּן",
            transliteration = "N",
            numericValue = 50,
            colorHex = "#9E721D",
            pictographSymbol = "🐟",
            pictographMeaning = "Pez, Vida en abundancia, Fidelidad (Neeman), Caída y Levantamiento",
            originEvolution = "Inspirada en un pez nadando en las aguas o una serpiente marina en movimiento.",
            visualComparison = "Pictograma (Pez 🐟) ➔ Paleo-Hebreo (𐤏) ➔ Hebreo Cuadrado (נ) ➔ Cursiva (נ)",
            gematriaExplanation = "El 50 simboliza el año del Jubileo (Yovel), las 50 Puertas de la Sabiduría y los 49 días de la Cuenta del Omer hacia el 50.",
            importantWords = listOf(
                ImportantWord("נֵר", "Ner", "Lámpara / Vela", 250, "La luz del alma humana (Proverbios 20:27)"),
                ImportantWord("נֶפֶשׁ", "Nefesh", "Alma / Vida", 430, "La dimensión vital del ser"),
                ImportantWord("נֶאֱמָן", "Neeman", "Fiel / Confiable", 141, "Integridad probada en el pacto")
            ),
            letterRelations = "Posee una forma final recta (Nun Sofit ן) que penetra las profundidades para rescatar a los caídos.",
            pardesPeshat = "Peshat: Decimocuarta letra del alefato.",
            pardesRemez = "Remez: Insinúa la capacidad del pez de multiplicarse en medio del agua sin ser visto por el mal ojo.",
            pardesDerash = "Derash: Nun representa al caído (Nofel), pero Dios lo sostiene inmediatamente con la letra Samekh que le sigue.",
            pardesSod = "Sod: Representa Malkhut en estado de entrega humilde esperando la luz divina.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Berachot 4b", "Por qué no hay Nun en el Salmo 145? Porque insinúa la caída (Nefilah) de Israel, sostenida por la Samekh."),
                QuoteComment("Zohar", "Pinchas 218a", "La Nun Sofit es el justo que se yergue firme en la fe.")
            ),
            talmudReferences = "Talmud Berachot 4b y Shabbat 104a sobre la Nun encorvada y la Nun recta.",
            midrashReferences = "Midrash Rabbah Shir HaShirim sobre Josué hijo de Nun.",
            kabbalahMeaning = "En Sefer Yetzirah rige el mes de Jeshván y el sentido del Olfato.",
            spiritualApplication = "Levantarse con fe tras cada tropiezo sabiendo que la mano divina sostiene al justo.",
            practicalApplication = "Ser un faro de fidelidad e integridad en las relaciones personales y laborales.",
            biblicalQuotes = listOf(
                BiblicalQuote("Salmo 119:105", "נֵר-לְרַגְלִי דְבָרֶךָ", "Lámpara es a mis pies tu palabra...", "Luz divina."),
                BiblicalQuote("Proverbios 20:27", "נֵר יְהוָה נִשְׁמַת אָדָם", "Lámpara del Señor es el espíritu del hombre...", "El alma como llama.")
            ),
            bibliography = listOf("Talmud Berachot 4b", "Sefer Yetzirah", "Zohar Hakadosh")
        ),
        HebrewLetter(
            id = "samekh",
            symbol = "ס",
            name = "Samekh",
            nameHebrew = "סָמֶךְ",
            transliteration = "S",
            numericValue = 60,
            colorHex = "#9B2226",
            pictographSymbol = "🛡️",
            pictographMeaning = "Escudo, Soporte, Apoyo, Círculo de Protección, Milagro ininterrumpido",
            originEvolution = "Deriva de un pilar o estaca de soporte semítica que sostiene una estructura pesada.",
            visualComparison = "Pictograma (Escudo 🛡️) ➔ Paleo-Hebreo (𐤐) ➔ Hebreo Cuadrado (ס) ➔ Cursiva (ס)",
            gematriaExplanation = "El 60 representa la Bendición Sacerdotal compuestapor 60 letras y los 60 valientes que rodean la cama de Salomón.",
            importantWords = listOf(
                ImportantWord("סוֹד", "Sod", "Misterio / Secreto divino", 70, "El nivel místico de la Torá"),
                ImportantWord("סוּכָּה", "Sukkah", "Cabaña protectora", 91, "Refugio bajo la sombra del Altísimo"),
                ImportantWord("סְלִיחָה", "Selichah", "Perdón", 103, "Absolución de la culpa")
            ),
            letterRelations = "Forma un círculo perfecto cerrado sin principio ni fin, simbolizando la protección divina que abarca todo.",
            pardesPeshat = "Peshat: Decimoquinta letra del alfabeto.",
            pardesRemez = "Remez: Insinúa el apoyo divino constante (Somech Nofelim) que sostiene a los necesitados.",
            pardesDerash = "Derash: En las Tablas de la Ley grabadas en piedra, el centro de la letra Samekh flotaba milagrosamente.",
            pardesSod = "Sod: Representa el Keter envolvente (Or Makif) que protege la creación.",
            rabbinicComments = listOf(
                QuoteComment("Talmud Shabbat 104a", "Rabbis", "La Mem final y la Samekh que estaban en las Tablas se sostenían por un milagro."),
                QuoteComment("Baal Shem Tov", "Sefer Baal Shem Tov", "Dios es el escudo que rodea al alma con amor incondicional.")
            ),
            talmudReferences = "Talmud Shabbat 104a y Megillah 3a sobre el milagro de la Samekh.",
            midrashReferences = "Midrash Tehillim sobre el Salmo 3: 'Tú, Señor, eres escudo alrededor de mí'.",
            kabbalahMeaning = "En Sefer Yetzirah gobierna el mes de Kislev y la facultad del Sueño.",
            spiritualApplication = "Descansar en la seguridad del amparo divino frente a la ansiedad.",
            practicalApplication = "Ofrecer apoyo emocional y auxilio a quienes se encuentran desalentados.",
            biblicalQuotes = listOf(
                BiblicalQuote("Salmo 145:14", "סוֹמֵךְ יְהוָה לְכָל-הַנֹּפְלִים", "Sostiene el Señor a todos los que caen...", "El apoyo divino (Samekh)."),
                BiblicalQuote("Salmo 3:4", "וְאַתָּה יְהוָה מָגֵן בַּעֲדִי", "Mas tú, Señor, eres escudo alrededor de mí...", "Protección del Creador.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Zohar Tetzaveh")
        ),
        HebrewLetter(
            id = "ayin",
            symbol = "ע",
            name = "Ayin",
            nameHebrew = "עַיִן",
            transliteration = "' / Gutural sorda",
            numericValue = 70,
            colorHex = "#1E2A38",
            pictographSymbol = "👁️",
            pictographMeaning = "Ojo, Visión espiritual, Comprensión profunda, Fuente de agua (Ein), 70 Naciones",
            originEvolution = "Evolucionó directamente de la forma humana de un ojo con su pupila prominente.",
            visualComparison = "Pictograma (Ojo 👁️) ➔ Paleo-Hebreo (𐤑) ➔ Hebreo Cuadrado (ע) ➔ Cursiva (ע)",
            gematriaExplanation = "El 70 representa los 70 ancianos del Sanedrín, las 70 naciones del mundo y las 70 facetas de la Torá (70 Panim LaTorah).",
            importantWords = listOf(
                ImportantWord("עַיִן", "Ayin", "Ojo / Fuente", 130, "Órgano de la percepción profunda"),
                ImportantWord("עוֹלָם", "Olam", "Mundo / Eternidad", 146, "La dimensión espacio-temporal"),
                ImportantWord("עֹז", "Oz", "Fuerza / Fortaleza", 77, "Poder conferido por la fe")
            ),
            letterRelations = "Compuesta por dos ramas (dos ojos) que convergen en una sola raíz, buscando la visión unificada.",
            pardesPeshat = "Peshat: Decimosexta letra del alefato.",
            pardesRemez = "Remez: Insinúa la vigilancia divina constante sobre el mundo (Provincia divina).",
            pardesDerash = "Derash: 'Ayin: Ayin Tovah (Ojo bueno y generoso) atrae bendición; Ayin Hara (Ojo malo) atrae escasez'.",
            pardesSod = "Sod: Representa Chochmah (Visión intuitiva) que penetra los velos del mundo físico.",
            rabbinicComments = listOf(
                QuoteComment("Pirkei Avot 2:9", "Rabbi Yochanan ben Zakkai", "¿Cuál es el buen camino? El ojo bueno (Ayin Tovah)."),
                QuoteComment("Zohar", "Volumen 3:204a", "Los ojos del Señor están fijos sobre la tierra de Israel desde el principio del año hasta el fin.")
            ),
            talmudReferences = "Talmud Shabbat 104a y Berachot 55b sobre el resguardo del mal ojo.",
            midrashReferences = "Midrash Rabbah Bamidbar 14 sobre las 70 facetas de la Torá.",
            kabbalahMeaning = "En Sefer Yetzirah rige sobre el mes de Tevet y la facultad de la Ira rectificada.",
            spiritualApplication = "Purificar la mirada para ver lo bueno y sagrado en cada ser humano.",
            practicalApplication = "Juzgar favorablemente a nuestro prójimo y cultivar la benevolencia visual.",
            biblicalQuotes = listOf(
                BiblicalQuote("Salmo 33:18", "הִנֵּה עֵין יְהוָה אֶל-יְרֵאָיו", "He aquí el ojo del Señor sobre los que le temen...", "La providencia divina."),
                BiblicalQuote("Proverbios 22:9", "טוֹב-עַיִן הוּא יְבֹרָךְ", "El de ojo misericordioso será bendito...", "Generosidad visual.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Pirkei Avot", "Sefer Yetzirah")
        ),
        HebrewLetter(
            id = "pe",
            symbol = "פ",
            name = "Pe",
            nameHebrew = "פֵּא",
            transliteration = "P / F",
            numericValue = 80,
            colorHex = "#2D6A4F",
            pictographSymbol = "🗣️",
            pictographMeaning = "Boca, Palabra viva, Expresión del alma, Oración, Revelación profética",
            originEvolution = "Deriva del dibujo de unos labios o una boca abierta lista para hablar o proclamar.",
            visualComparison = "Pictograma (Boca 🗣️) ➔ Paleo-Hebreo (𐤒) ➔ Hebreo Cuadrado (פ) ➔ Cursiva (פ)",
            gematriaExplanation = "El 80 es la edad de Moisés cuando habló ante el Faraón para liberar a Israel.",
            importantWords = listOf(
                ImportantWord("פֶּה", "Peh", "Boca", 85, "Instrumento del habla y la oración"),
                ImportantWord("פָּנִים", "Panim", "Rostro / Presencia", 180, "Expresión de la identidad profunda"),
                ImportantWord("פֶּדּוּת", "Pedut", "Redención", 494, "Liberación proclamada")
            ),
            letterRelations = "Contiene un espacio en blanco interior que dibuja secretamente la letra Bet (la casa de la palabra). Tiene forma final recta (Pe Sofit ף).",
            pardesPeshat = "Peshat: Decimoséptima letra del alefato.",
            pardesRemez = "Remez: Insinúa el poder de la palabra hablada (Peh-Sach = Pésaj, la boca que habla de la libertad).",
            pardesDerash = "Derash: 'Guardar la boca del chisme (Lashon Hara) preserva la vida del alma'.",
            pardesSod = "Sod: Representa Malchut expresando la luz divina en palabras santas.",
            rabbinicComments = listOf(
                QuoteComment("Chafetz Chaim", "Shmirat HaLashon", "La boca es el santuario del habla; contaminarla distorsiona el alma."),
                QuoteComment("Zohar", "Bereshit 24b", "Con la boca se proclama la unicidad del Creador en el Shemá.")
            ),
            talmudReferences = "Talmud Shabbat 104a y Arachin 15b sobre las leyes del habla guardada.",
            midrashReferences = "Midrash Rabbah Devarim sobre las palabras proféticas de Moisés.",
            kabbalahMeaning = "Sefer Yetzirah: Rige sobre el mes de Shevat y la facultad de la Comida / Sabor.",
            spiritualApplication = "Consagrar nuestras palabras para bendecir, sanar y proclamar la verdad.",
            practicalApplication = "Evitar la crítica, el chisme y el juicio apresurado con la boca.",
            biblicalQuotes = listOf(
                BiblicalQuote("Salmo 19:15", "יִהְיוּ לְרָצוֹן אִמְרֵי-פִי", "Sean gratos los dichos de mi boca...", "Oración de pureza hablada."),
                BiblicalQuote("Proverbios 18:21", "מָוֶת וְחַיִּים בְּיַד-לָשׁוֹן", "La muerte y la vida están en poder de la lengua...", "Poder de la palabra.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Chafetz Chaim", "Zohar Hakadosh")
        ),
        HebrewLetter(
            id = "tzadi",
            symbol = "צ",
            name = "Tzadi",
            nameHebrew = "צָדִי",
            transliteration = "Tz (TS)",
            numericValue = 90,
            colorHex = "#7F4F24",
            pictographSymbol = "⚓",
            pictographMeaning = "Anzuelo, Cosecha, El Justo (Tzadik), Justicia social (Tzedaká)",
            originEvolution = "Proviene del anzuelo de un pescador o de una persona inclinada en oración fervorosa.",
            visualComparison = "Pictograma (Anzuelo ⚓) ➔ Paleo-Hebreo (𐤓) ➔ Hebreo Cuadrado (צ) ➔ Cursiva (צ)",
            gematriaExplanation = "El 90 es la edad de Sara cuando concibió a Isaac, demostrando que la fe recta florece milagrosamente.",
            importantWords = listOf(
                ImportantWord("צַדִּיק", "Tzadik", "El Justo", 204, "Pilar del mundo que vive por la fe"),
                ImportantWord("צְדָקָה", "Tzedaká", "Justicia / Caridad", 199, "Restitución del equilibrio social"),
                ImportantWord("צִיּוֹן", "Tzion", "Sión", 156, "Morada escogida de la santidad divina")
            ),
            letterRelations = "Formada por una Nun inclinada con una Yod posada sobre su espalda. Posee forma final recta (Tzadi Sofit ץ).",
            pardesPeshat = "Peshat: Decimooctava letra del alefato.",
            pardesRemez = "Remez: Insinúa el equilibrio moral impecable del Tzadik que sostiene el universo.",
            pardesDerash = "Derash: 'Tzadi-Tzadik: El justo cae siete veces y vuelve a levantarse'.",
            pardesSod = "Sod: Representa Yesod uniendo el Cielo con la Tierra.",
            rabbinicComments = listOf(
                QuoteComment("Talmud Sanhedrin 92b", "Rabbis", "El mundo no existe sino por el mérito de los 36 Justos ocultos (Lamed-Vav Tzaddikim)."),
                QuoteComment("Zohar", "Volumen 1:59b", "Tzadi es el sello de la verdad rectificada.")
            ),
            talmudReferences = "Talmud Shabbat 104a y Sanhedrin 92b sobre la naturaleza del Tzadik.",
            midrashReferences = "Midrash Rabbah Bereshit 30 sobre la justicia de Noé en su generación.",
            kabbalahMeaning = "En Sefer Yetzirah rige sobre el mes de Adar y la facultad de la Risa / Alegría.",
            spiritualApplication = "Actuar con equidad, ética impecable y caridad activa en todo momento.",
            practicalApplication = "Dar Tzedaká diariamente y defender a los desamparados.",
            biblicalQuotes = listOf(
                BiblicalQuote("Proverbios 10:25", "וְצַדִּיק יְסוֹד עוֹלָם", "...mas el justo es fundamento eterno del mundo.", "El pilar del Tzadik."),
                BiblicalQuote("Habacuc 2:4", "וְצַדִּיק בֶּאֱמוּנָתוֹ יִחְיֶה", "...mas el justo por su fe vivirá.", "La fe del Tzadik.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Zohar Hakadosh")
        ),
        HebrewLetter(
            id = "kof",
            symbol = "ק",
            name = "Kof",
            nameHebrew = "קוֹף",
            transliteration = "K (Enfática / Guttural)",
            numericValue = 100,
            colorHex = "#9E721D",
            pictographSymbol = "🪡",
            pictographMeaning = "Ojo de aguja, El Santo (Kadosh), Retorno desde las profundidades, Santidad",
            originEvolution = "Deriva del ojo de una aguja de coser o del nudo de la cinta del filacterio posterior.",
            visualComparison = "Pictograma (Aguja 🪡) ➔ Paleo-Hebreo (𐤔) ➔ Hebreo Cuadrado (ק) ➔ Cursiva (ק)",
            gematriaExplanation = "El 100 representa los 100 toques del Shofar en Rosh Hashaná y las 100 bendiciones diarias (Me'ah Berachot).",
            importantWords = listOf(
                ImportantWord("קָדוֹשׁ", "Kadosh", "Santo / Apartado", 410, "Trascendencia y pureza divina"),
                ImportantWord("קוֹל", "Kol", "Voz / Clamor", 136, "La voz profética divina"),
                ImportantWord("קַו", "Kav", "Línea / Rayo de Luz", 106, "Rayo de luz primordial")
            ),
            letterRelations = "Su pata desciende por debajo de la línea de escritura, simbolizando que la misericordia divina desciende hasta lo más profundo para rescatar al perdido.",
            pardesPeshat = "Peshat: Decimonovena letra del alefato.",
            pardesRemez = "Remez: Insinúa que Dios es Kadosh (Santo), trascendente e inalcanzable, pero cercano al corazón contrito.",
            pardesDerash = "Derash: 'Kof: Kadosh es el Santo que perdona y transforma los errores en méritos'.",
            pardesSod = "Sod: Representa Malkhut en proceso de elevación hacia Keter.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Shabbat 104a", "La pata de la Kof desciende para mostrar que la Teshuvá alcanza incluso los lugares más oscuros."),
                QuoteComment("Zohar", "Volumen 3:225a", "Kadosh, Kadosh, Kadosh es el clamor continuo de los seres celestiales.")
            ),
            talmudReferences = "Talmud Shabbat 104a y Menachot 29b sobre la pata suspendida de Kof.",
            midrashReferences = "Midrash Rabbah Vayikra 24 sobre la santidad de Israel.",
            kabbalahMeaning = "En Sefer Yetzirah rige sobre el mes de Nisán y la facultad del Habla profética.",
            spiritualApplication = "Separarse de la impureza moral y vivir en una dimensión de santidad cotidiana.",
            practicalApplication = "Recitar las bendiciones diarias con intención y devoción de corazón.",
            biblicalQuotes = listOf(
                BiblicalQuote("Isaías 6:3", "קָדוֹשׁ קָדוֹשׁ קָדוֹשׁ יְהוָה צְבָאוֹת", "Santo, Santo, Santo, Señor de los ejércitos...", "La trisagios angelical."),
                BiblicalQuote("Levítico 19:2", "קְדֹשִׁים תִּהְיוּ כִּי קָדוֹשׁ אֲנִי", "Santos seréis, porque santo soy yo el Señor vuestro Dios...", "Llamado a la santidad.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Zohar Hakadosh")
        ),
        HebrewLetter(
            id = "resh",
            symbol = "ר",
            name = "Resh",
            nameHebrew = "רֵישׁ",
            transliteration = "R",
            numericValue = 200,
            colorHex = "#9B2226",
            pictographSymbol = "👤",
            pictographMeaning = "Cabeza, Perfil humano, Pobreza de espíritu (Rash), Elección moral",
            originEvolution = "Evolucionó de la cabeza o perfil humano semítico en movimiento.",
            visualComparison = "Pictograma (Cabeza 👤) ➔ Paleo-Hebreo (𐤕) ➔ Hebreo Cuadrado (ר) ➔ Cursiva (ר)",
            gematriaExplanation = "El 200 simboliza la capacidad intelectual del ser humano de dirigir sus pensamientos hacia el bien o hacia el mal.",
            importantWords = listOf(
                ImportantWord("רֹאשׁ", "Rosh", "Cabeza / Principio", 501, "Comienzo del camino o del año"),
                ImportantWord("רוּחַ", "Ruach", "Espíritu / Viento", 214, "El hálito divino que da vida"),
                ImportantWord("רַחֲמִים", "Rachamim", "Misericordia profunda", 298, "Comprensión compasiva divina")
            ),
            letterRelations = "Muy parecida a Dalet, pero con la esquina posterior redondeada. Cambiar Resh por Dalet en ciertas oraciones destruye el sentido profético.",
            pardesPeshat = "Peshat: Vigésima letra del alefato.",
            pardesRemez = "Remez: Insinúa al ser humano pobre (Rash) que no posee nada propio salvo lo otorgado por el Creador.",
            pardesDerash = "Derash: 'Resh-Rasha: El malvado le da la espalda a Dios, pero si hace arrepentimiento se convierte en Rosh (Cabeza)'.",
            pardesSod = "Sod: Representa Zeir Anpin en el nivel de las emociones superiores.",
            rabbinicComments = listOf(
                QuoteComment("Rambam", "Hilchot Teshuvá", "El libre albedrío reside en la cabeza (Resh) del ser humano para elegir su destino."),
                QuoteComment("Zohar", "Volumen 1:137b", "Resh es la cabeza de la sabiduría cuando se inclina ante el Rey.")
            ),
            talmudReferences = "Talmud Shabbat 104a sobre la diferencia gráfica entre Resh y Dalet.",
            midrashReferences = "Midrash Rabbah Bereshit 1:1 sobre la cabeza de la creación.",
            kabbalahMeaning = "Sefer Yetzirah: Una de las 7 letras dobles, rigiendo sobre la Paz y la Guerra.",
            spiritualApplication = "Renovar la mente diariamente librándola de pensamientos destructivos.",
            practicalApplication = "Liderar con el ejemplo y cultivar sentimientos de misericordia activa.",
            biblicalQuotes = listOf(
                BiblicalQuote("Salmo 111:10", "רֵאשִׁית חָכְמָה יִרְאַת יְהוָה", "El principio de la sabiduría es el temor del Señor...", "La verdadera cabeza."),
                BiblicalQuote("Génesis 1:2", "וְרוּחַ אֱלֹהִים מְרַחֶפֶת", "...y el Espíritu de Dios se movía sobre la faz de las aguas.", "El Ruach primordial.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Zohar Bereshit")
        ),
        HebrewLetter(
            id = "shin",
            symbol = "ש",
            name = "Shin",
            nameHebrew = "שִׁין",
            transliteration = "Sh / S",
            numericValue = 300,
            colorHex = "#1E2A38",
            pictographSymbol = "🔥",
            pictographMeaning = "Diente, Fuego consumidor (Esh), Nombres divinos (Shaddai), Transformación",
            originEvolution = "Inspirada en dos dientes incisivos o tres llamas de fuego alzándose hacia el cielo.",
            visualComparison = "Pictograma (Dientes/Llamas 🔥) ➔ Paleo-Hebreo (𐤖) ➔ Hebreo Cuadrado (ש) ➔ Cursiva (ש)",
            gematriaExplanation = "El 300 representa el fuego espiritual abrasador y las tres ramas de los Patriarcas unidas en Dios.",
            importantWords = listOf(
                ImportantWord("שַׁדַּי", "Shaddai", "Dios Todopoderoso", 314, "El Guardián de las puertas de Israel"),
                ImportantWord("שָׁלוֹם", "Shalom", "Paz / Plenitud", 376, "Estado de integridad divina"),
                ImportantWord("שְׁמַע", "Shemá", "Escucha / Oye", 410, "La proclamación central de la fe")
            ),
            letterRelations = "Inscripta milagrosamente sobre los filacterios (Tefilín) de la cabeza con 3 picos a la derecha y 4 picos a la izquierda.",
            pardesPeshat = "Peshat: Vigésimo primera letra del alefato.",
            pardesRemez = "Remez: Insinúa el fuego consumidor de la presencia divina (Esh Ochelah).",
            pardesDerash = "Derash: 'Shin-Shalom: Dios une los opuestos así como la Shin une tres brasas en una sola llama'.",
            pardesSod = "Sod: Representa el nivel místico de la Sefer Yetzirah como la Madre del Fuego.",
            rabbinicComments = listOf(
                QuoteComment("Zohar", "Volumen 3:252a", "La letra Shin de los Tefilín es el sello sagrado de la gloria divina sobre la frente."),
                QuoteComment("Rashi", "Shabbat 104a", "Shin es la letra de la verdad (Sheker es falsedad si carece de apoyo).")
            ),
            talmudReferences = "Talmud Shabbat 104a y Menachot 35b sobre la Shin de los Tefilín.",
            midrashReferences = "Midrash Otiyot deRabbi Akiva sobre el fuego de la Shin.",
            kabbalahMeaning = "Una de las tres letras madres, representando el elemento Fuego (Esh).",
            spiritualApplication = "Encender el corazón con fuego sagrado de amor y celo por Dios.",
            practicalApplication = "Proclamar la verdad y buscar la paz armónica en todas las relaciones.",
            biblicalQuotes = listOf(
                BiblicalQuote("Deuteronomio 6:4", "שְׁמַע יִשְׂרָאֵל יְהוָה אֱלֹהֵינוּ יְהוָה אֶחָד", "Oye, Israel: el Señor nuestro Dios, el Señor uno es.", "El Shemá Yisrael."),
                BiblicalQuote("Cantar de los Cantares 8:6", "רְשָׁפֶיהָ רִשְׁפֵּי אֵשׁ", "...sus brasas son brasas de fuego, fuerte llama.", "Fuego de amor.")
            ),
            bibliography = listOf("Talmud Shabbat 104a", "Sefer Yetzirah", "Zohar Hakadosh")
        ),
        HebrewLetter(
            id = "tav_end",
            symbol = "ת",
            name = "Tav",
            nameHebrew = "תָּו",
            transliteration = "T",
            numericValue = 400,
            colorHex = "#9B2226",
            pictographSymbol = "𓏴",
            pictographMeaning = "Sello, Marca, Cruz, Pacto Eterno, Verdad Completa (Emet)",
            originEvolution = "Representada originalmente por dos palos cruzados (marca o firma de propiedad).",
            visualComparison = "Pictograma (Marca 𓏴) ➔ Paleo-Hebreo (𐤏) ➔ Hebreo Cuadrado (ת) ➔ Cursiva (ת)",
            gematriaExplanation = "El 400 es el valor de la última letra del alefato, simbolizando la totalidad de la creación desde Alef a Tav.",
            importantWords = listOf(
                ImportantWord("תּוֹרָה", "Torah", "Instrucción / Ley", 611, "La guía completa de Dios para la humanidad"),
                ImportantWord("תְּפִלָּה", "Tefillah", "Oración", 515, "Conexión directa del alma con el Creador"),
                ImportantWord("תְּשּׁוּבָה", "Teshuvá", "Arrepentimiento / Retorno", 718, "Regreso a la fuente divina")
            ),
            letterRelations = "Completa el alefato conectando de regreso con la Alef primera.",
            pardesPeshat = "Peshat: Letra final del alfabeto hebreo.",
            pardesRemez = "Remez: Insinúa el sello de la Verdad (Emet) compuesto por Alef, Mem y Tav.",
            pardesDerash = "Derash: Tav es el sello del Rey Santo colocado en los justos.",
            pardesSod = "Sod: Representa Malchut perfeccionada en el final de los tiempos.",
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Ezequiel 9:4", "La letra Tav de tinta fue marcada en la frente de los justos para preservarlos."),
                QuoteComment("Zohar", "Pardes Rimonim", "Tav es la consumación de todas las cosas y la garantía del pacto.")
            ),
            talmudReferences = "Talmud Shabbat 55a sobre la marca de la Tav en las frentes del pueblo.",
            midrashReferences = "Midrash Otiyot deRabbi Akiva sobre la majestad de la Tav.",
            kabbalahMeaning = "En Sefer Yetzirah gobierna el Portal del Universo y la belleza del Pacto.",
            spiritualApplication = "Vivir con integridad impecable siendo portadores del sello divino.",
            practicalApplication = "Perseverar hasta el final en todo buen propósito encomendado.",
            biblicalQuotes = listOf(
                BiblicalQuote("Ezequiel 9:4", "וְהִתְוִיתָ תָּו עַל-מִצְחוֹת הָאֲנָשִׁים", "...y pon una marca (Tav) en la frente de los hombres...", "El sello protector divino."),
                BiblicalQuote("Salmo 119:176", "תָּעִיתִי כְּשֶׂה אֹבֵד בַּקֵּשׁ עַבְדֶּךָ", "Yo anduve errante como oveja perdida; busca a tu siervo...", "Cierre del Salmo del Alefato.")
            ),
            bibliography = listOf("Talmud Shabbat 55a", "Sefer Yetzirah", "Zohar Hakadosh")
        )
    )
}
