package com.example.data.repository

import com.example.data.model.BiblicalQuote
import com.example.data.model.HebrewMonth
import com.example.data.model.QuoteComment

object HebrewMonthData {

    val months: List<HebrewMonth> = listOf(
        HebrewMonth(
            id = "tishrei",
            nameSpanish = "Tishrei",
            nameHebrew = "תִּשְׁרֵי",
            monthNumberCivil = 1,
            monthNumberReligious = 7,
            gregorianApprox = "Septiembre - Octubre",
            associatedTribe = "Efraín (Abundancia / Fruto)",
            associatedLetter = "Lamed (ל) - Ascenso",
            season = "Otoño (Stav)",
            agriculturalHarvest = "Cosecha de higos, granadas, dátiles y aceitunas. Prensado del aceite nuevo.",
            agriculturalIcons = listOf("🍇", "🍎", "🍯", "🫒"),
            festivalsInMonth = listOf("Rosh Hashaná (1-2 Tishrei)", "Yom Kipur (10 Tishrei)", "Sukkot (15-21 Tishrei)", "Sheminí Atzeret / Simjat Torá (22 Tishrei)"),
            historyMeaning = "El mes más repleto de fiestas sagradas. Su nombre proviene de la raíz acadia 'Tashritu' que significa 'Comienzo'. Dios juzga al mundo y derrama el perdón completo.",
            biblicalQuotes = listOf(
                BiblicalQuote("Levítico 23:27", "אַךְ בֶּעָשׂוֹר לַחֹדֶשׁ הַשְּׁבִיעִי הַזֶּה יוֹם הַכִּפֻּרִים הוּא", "A los diez días de este mes séptimo será el día de expiación...", "El día de la expiación."),
                BiblicalQuote("Levítico 23:34", "בַּחֲמִשָּׁה עָשָׂר יוֹם לַחֹדֶשׁ הַשְּׁבִיעִי הַזֶּה חַג הַסֻּכּוֹת", "A los quince días de este mes séptimo será la fiesta de las cabañas...", "Fiesta de Sukkot.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Sefer Yetzirah", "Capítulo 5", "Gobernó la letra Lamed en el mes de Tishrei y formó el sentido de la Acción."),
                QuoteComment("Zohar", "Volumen 3:231b", "En Tishrei se abren las compuertas del juicio y la misericordia celestial.")
            ),
            midrashText = "El Midrash enseña que Tishrei es el mes de la plenitud (Sova), donde la tierra entrega sus mejores frutos y el cielo sus mayores perdones.",
            eschatologySignificance = "Anticipa las Bodas del Cordero y el reinado mesiánico de mil años en Sukkot."
        ),
        HebrewMonth(
            id = "cheshvan",
            nameSpanish = "Jeshván (Marjeshván)",
            nameHebrew = "מַרְחֶשְׁוָן",
            monthNumberCivil = 2,
            monthNumberReligious = 8,
            gregorianApprox = "Octubre - Noviembre",
            associatedTribe = "Manasés (Olvido del dolor / Crecimiento)",
            associatedLetter = "Nun (נ) - Pez / Fidelidad",
            season = "Otoño (Lluvias tempranas)",
            agriculturalHarvest = "Arado de la tierra y siembra del grano de trigo y cebada esperando la lluvia (Yoreh).",
            agriculturalIcons = listOf("🌧️", "🌾", "🌱"),
            festivalsInMonth = listOf("Sin fiestas festivas públicas (Mes reservado para la oración interior y la memoria de la matriarca Raquel)."),
            historyMeaning = "Llamado popularmente 'Mar-Jeshván' (Jeshván Amargo) por no contener ninguna festividad. La tradición enseña que este mes está reservado para la inauguración del Tercer Templo en la era mesiánica. En este mes comenzó el Gran Diluvio en días de Noé.",
            biblicalQuotes = listOf(
                BiblicalQuote("1 Reyes 6:38", "וּבַשָּׁנָה הָאַחַת עֶשְׂרֵה בְּיֶרַח בּוּל הוּא הַחֹדֶשׁ הַשְּׁמִינִי", "Y en el mes de Bul, que es el mes octavo, fue acabada la Casa...", "Construcción del Templo de Salomón."),
                BiblicalQuote("Génesis 7:11", "בַּחֹדֶשׁ הַשֵּׁנִי... נִבְקְעוּ כָּל-מַעְיְנֹת תְּהוֹם רַבָּה", "En el mes segundo... fueron rotas todas las fuentes del gran abismo...", "Comienzo del Diluvio.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "1 Reyes 6:38", "El mes de Bul es Jeshván, llamado así porque la hierba se marchita y el campo requiere agua."),
                QuoteComment("Radak", "Comentario Bíblico", "La muerte de la matriarca Raquel ocurrió el 11 de Jeshván en la ruta a Efrata.")
            ),
            midrashText = "El Midrash relata que Jeshván se quejó ante Dios por no tener festividades, y Dios le prometió que en el futuro el Rey Mesías inaugurará el Templo en su tiempo.",
            eschatologySignificance = "Mes de la consagración del Templo Mesiánico y la revelación de la santidad oculta."
        ),
        HebrewMonth(
            id = "kislev",
            nameSpanish = "Kislev",
            nameHebrew = "כִּסְלֵו",
            monthNumberCivil = 3,
            monthNumberReligious = 9,
            gregorianApprox = "Noviembre - Diciembre",
            associatedTribe = "Benjamín (El hijo de la diestra)",
            associatedLetter = "Samekh (ס) - Apoyo / Escudo",
            season = "Invierno (Lluvias intensas)",
            agriculturalHarvest = "Germinación de las semillas bajo tierra; tiempo de refugio e iluminación interior.",
            agriculturalIcons = listOf("🕯️", "🌧️", "🫒"),
            festivalsInMonth = listOf("Janucá (25 Kislev al 2 u 3 Tevet) - Fiesta de la Dedicación y de las Luces"),
            historyMeaning = "Mes de los milagros y de la victoria de los Macabeos sobre la tiranía seléucida. Re-dedicación del Altar del Templo y milagro de la vasija de aceite que ardió 8 días.",
            biblicalQuotes = listOf(
                BiblicalQuote("Zacarías 7:1", "בְּאַרְבָּעָה לַחֹדֶשׁ הַתְּשִׁיעִי בְּכִסְלֵו", "...a los cuatro días del mes noveno, que es Kislev...", "Profecía de Zacarías."),
                BiblicalQuote("Hageo 2:18", "מִיּוֹם עֶשְׂרִים וְאַרְבָּעָה לַתְּשִׁיעִי", "Desde el día veinticuatro del noveno mes...", "Promesa de bendición de Hageo.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Maharal de Praga", "Ner Mitzvah", "Janucá en Kislev enseña que la luz espiritual prevalece sobre la oscuridad más profunda."),
                QuoteComment("Kedushat Levi", "Janucá", "El aceite puro representa la sabiduría de la Torá que nunca se contamina.")
            ),
            midrashText = "Dice el Midrash: La luz que Dios creó en el primer día permaneció oculta hasta que los Macabeos la encendieron en la Menorá.",
            eschatologySignificance = "Victoria de la luz divina sobre las tinieblas y preparación para la manifestación del Mesías."
        ),
        HebrewMonth(
            id = "tevet",
            nameSpanish = "Tevet",
            nameHebrew = "טֵבֵת",
            monthNumberCivil = 4,
            monthNumberReligious = 10,
            gregorianApprox = "Diciembre - Enero",
            associatedTribe = "Dan (Juicio / Rectitud)",
            associatedLetter = "Ayin (ע) - Ojo / Visión",
            season = "Invierno (Frío y heladas)",
            agriculturalHarvest = "Maduración de los cereales de invierno bajo la lluvia constante.",
            agriculturalIcons = listOf("❄️", "🌧️", "📜"),
            festivalsInMonth = listOf("Conclusión de Janucá (1-2 Tevet)", "Ayuno del 10 de Tevet (Asarah B'Tevet - Asedio de Jerusalén)"),
            historyMeaning = "Mes de prueba y fortalecimiento moral. El 10 de Tevet comenzó el trágico asedio a Jerusalén por el rey Nabucodonosor de Babilonia.",
            biblicalQuotes = listOf(
                BiblicalQuote("Esther 2:16", "בַּחֹדֶשׁ הָעֲשִׂירִי הוּא-חֹדֶשׁ טֵבֵת", "...en el mes décimo, que es el mes de Tevet...", "Mención bíblica de Tevet."),
                BiblicalQuote("Ezequiel 24:1-2", "בַּחֹדֶשׁ הָעֲשִׂירִי בְּעָשׂוֹר לַחֹדֶשׁ... סָמַךְ מֶלֶךְ-בָּבֶל אֶל-יְרוּשָׁלִַם", "En el mes décimo, a los diez días del mes... el rey de Babilonia sitio a Jerusalén...", "Inicio del asedio.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Ezequiel 24:2", "El 10 de Tevet fue el primer eslabón de la cadena de sufrimientos que llevó a la destrucción del Templo."),
                QuoteComment("Chafetz Chaim", "Ahavat Chesed", "El ayuno de Tevet nos llama a reparar las brechas de la unidad entre hermanos.")
            ),
            midrashText = "El Midrash enseña que Tevet exige transformar la mirada severa (Ayin) en una mirada de compasión y rectificación moral.",
            eschatologySignificance = "Transformación futura de los ayunos invernales en días de regocijo e iluminación divina."
        ),
        HebrewMonth(
            id = "shevat",
            nameSpanish = "Shevat",
            nameHebrew = "שְׁבָט",
            monthNumberCivil = 5,
            monthNumberReligious = 11,
            gregorianApprox = "Enero - Febrero",
            associatedTribe = "Aser (Deleite / Abundancia)",
            associatedLetter = "Tzadi (צ) - El Justo",
            season = "Invierno tardío (Despertar de la savia)",
            agriculturalHarvest = "Floración del almendro (Shaked), primer árbol en despertar en Israel.",
            agriculturalIcons = listOf("🌸", "🌳", "🍃"),
            festivalsInMonth = listOf("Tu BiShvat (15 de Shevat) - Año Nuevo de los Árboles"),
            historyMeaning = "Mes del despertar espiritual y de la renovación de la savia divina. Moisés comenzó a recitar y explicar el libro de Deuteronomio (Mishneh Torah) a Israel el 1 de Shevat.",
            biblicalQuotes = listOf(
                BiblicalQuote("Deuteronomio 1:3", "בְּעַשְׁתֵּי-עָשָׂר חֹדֶשׁ בְּאֶחָד לַחֹדֶשׁ... דִּבֶּר מֹשֶׁה אֶל-בְּנֵי יִשְׂרָאֵל", "En el mes undécimo, el primero del mes... habló Moisés a los hijos de Israel...", "Discurso final de Moisés."),
                BiblicalQuote("Zacarías 1:7", "בְּיוֹם עֶשְׂרִים וְאַרְבָּעָה לְעַשְׁתֵּי-עָשָׂר חֹדֶשׁ הוּא-חֹדֶשׁ שְׁבָט", "A los veinticuatro días del mes undécimo, que es el mes de Shevat...", "Visión profética de los caballos.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Ramban", "Deuteronomio 1:1", "En Shevat Moisés explicó la Torá en 70 idiomas para que fuera accesible a toda la humanidad."),
                QuoteComment("Sefer Yetzirah", "Capítulo 5", "Shevat está asociado con el sentido del Gusto y la rectificación de la nutrición pura.")
            ),
            midrashText = "El Midrash compara a Israel en Shevat con el almendro: parece dormido en invierno, pero se apresura a dar las primeras flores bellas de la primavera.",
            eschatologySignificance = "Restauración de la fertilidad del Edén y efusión de la sabiduría de la Torá a las naciones."
        ),
        HebrewMonth(
            id = "adar_i",
            nameSpanish = "Adar (Adar I)",
            nameHebrew = "אֲדָר",
            monthNumberCivil = 6,
            monthNumberReligious = 12,
            gregorianApprox = "Febrero - Marzo",
            associatedTribe = "Neftalí (Cierva suelta / Palabras hermosas)",
            associatedLetter = "Kof (ק) - Santidad / Risa rectificada",
            season = "Transición de Invierno a Primavera",
            agriculturalHarvest = "Desarrollo pleno de la vegetación e intensificación de la floración silvestre.",
            agriculturalIcons = listOf("🎭", "🌾", "🐑"),
            festivalsInMonth = listOf("7 de Adar (Nacimiento y fallecimiento de Moisés)", "14-15 de Adar (Purim en año regular / Purim Katan en año bisiesto)"),
            historyMeaning = "El mes de la máxima alegría según el Talmud: 'Cuando entra Adar, se incrementa la alegría' (Mishenichnas Adar Marbin B'Simcha). Mes de la salvación de Purim.",
            biblicalQuotes = listOf(
                BiblicalQuote("Esther 3:7", "עַד-חֹדֶשׁ שְׁנֵים-עָשָׂר הוּא-חֹדֶשׁ אֲדָר", "...hasta el mes duodécimo, que es el mes de Adar...", "El mes decretado para la salvación."),
                BiblicalQuote("Esdras 6:15", "וְשֵׁיצִיא בַּיְתָה דְנָה עַד יוֹם תְּלָתָה לַחֹדֶשׁ אֲדָר", "Y fue acabada esta Casa el tercer día del mes de Adar...", "Terminación del Segundo Templo.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Talmud Ta'anit 29a", "Rav Yehuda", "Igual que cuando entra Av disminuye la alegría, cuando entra Adar la alegría aumenta sin medida."),
                QuoteComment("Rashi", "Ta'anit 29a", "Adar es un mes afortunado para Israel porque en él ocurrieron los milagros de Purim y Pésaj le sigue.")
            ),
            midrashText = "El Midrash enseña que Amán echó suertes (Pur) y se alegró cuando cayó Adar porque en Adar murió Moisés, ignorando que en ese mismo mes Moisés también había nacido.",
            eschatologySignificance = "Revelación de la victoria completa de la fe sobre las maquinaciones del enemigo."
        ),
        HebrewMonth(
            id = "adar_ii",
            nameSpanish = "Adar II (Adar Shení - Mes Bisiesto)",
            nameHebrew = "אֲדָר שֵׁנִי",
            monthNumberCivil = 6,
            monthNumberReligious = 13,
            gregorianApprox = "Marzo",
            associatedTribe = "Efraín / Manasés (Doble Porción)",
            associatedLetter = "Kof (ק) - Transformación Suprema",
            season = "Primavera temprana (Equinoccio)",
            agriculturalHarvest = "Ajuste del ciclo solar con el lunar para asegurar que Pésaj caiga en la primavera (Aviv).",
            agriculturalIcons = listOf("🌾", "☀️", "🌙"),
            festivalsInMonth = listOf("Ayuno de Ester (13 Adar II)", "Purim (14 Adar II)", "Shushan Purim (15 Adar II)"),
            historyMeaning = "El mes intercalar añadido 7 veces en un ciclo de 19 años (ciclo metónico) para sincronizar el calendario lunar con las estaciones solares agrícolas ordenadas por la Torá.",
            biblicalQuotes = listOf(
                BiblicalQuote("Deuteronomio 16:1", "שָׁמוֹר אֶת-חֹדֶשׁ הָאָבִיב", "Guardarás el mes de Aviv (Primavera)...", "El mandato de sincronizar el año."),
                BiblicalQuote("Ester 9:21", "לְקַיֵּם עֲלֵיהֶם לִהְיוֹת עֹשִׂים אֵת יוֹם אַרְבָּעָה עָשָׂר לַחֹדֶשׁ אֲדָר", "Estableciendo que celebrasen el día catorce del mes de Adar...", "Celebración de Purim en Adar II.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Mishnah Megillah 1:4", "Rabbis", "No hay diferencia entre el primer Adar y el segundo Adar salvo la lectura de la Megilá y los regalos a los pobres."),
                QuoteComment("Chasam Sofer", "Responsa", "Adar Shení representa la rectificación doble y la plenitud de la alegría dividida en dos recipientes.")
            ),
            midrashText = "El Midrash enseña que el mes bisiesto es el regalo de la misericordia divina para ajustar el sol (justicia) con la luna (fe).",
            eschatologySignificance = "Armonía perfecta entre la revelación cósmica y la redención histórica humana."
        ),
        HebrewMonth(
            id = "nisan",
            nameSpanish = "Nisán (Aviv)",
            nameHebrew = "נִיסָן",
            monthNumberCivil = 7,
            monthNumberReligious = 1,
            gregorianApprox = "Marzo - Abril",
            associatedTribe = "Judá (Alabanza / Realeza)",
            associatedLetter = "He (ה) - Aliento de Vida",
            season = "Primavera (Aviv)",
            agriculturalHarvest = "Maduración de la cebada y florecimiento de los árboles frutales.",
            agriculturalIcons = listOf("🌾", "🍷", "🐑", "🌸"),
            festivalsInMonth = listOf("Pésaj (15-22 Nisán)", "Cuenta del Omer (comienza el 16 de Nisán)"),
            historyMeaning = "El primer mes del calendario religioso hebreo, denominado 'El Mes de la Redención'. Dios declaró a Nisán como la cabeza de todos los meses tras la salida victoriosa de Egipto.",
            biblicalQuotes = listOf(
                BiblicalQuote("Éxodo 12:2", "הַחֹדֶשׁ הַזֶּה לָכֶם רֹאשׁ חֳדָשִׁים", "Este mes os será principio de los meses; para vosotros será el primero...", "Institución del calendario sagrado."),
                BiblicalQuote("Esther 3:7", "בַּחֹדֶשׁ הָרִאשׁוֹן הוּא-חֹדֶשׁ נִיסָן", "En el mes primero, que es el mes de Nisán...", "Mención bíblica de Nisán.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Éxodo 12:2", "Nisán se llama Aviv (Primavera) porque es el mes en que los frutos brotan suaves y frescos."),
                QuoteComment("Talmud Rosh Hashanah 11a", "Rabbi Joshua", "En Nisán el mundo fue redimido y en Nisán volverá a ser redimido en el futuro.")
            ),
            midrashText = "Midrash Rabbah: Nisán es el mes real donde nació Judá y donde el cetro del Mesías se manifiesta con poder libertador.",
            eschatologySignificance = "Mes de la gran redención futura donde todas las naciones reconocerán la soberanía de Dios."
        ),
        HebrewMonth(
            id = "iyar",
            nameSpanish = "Iyar (Ziv)",
            nameHebrew = "אִיָּר",
            monthNumberCivil = 8,
            monthNumberReligious = 2,
            gregorianApprox = "Abril - Mayo",
            associatedTribe = "Isacar (Sabiduría / Estudio de Torá)",
            associatedLetter = "Vav (ו) - Conexión",
            season = "Primavera plena",
            agriculturalHarvest = "Cosecha floreciente del trigo temprano y recolección de flores medicinales.",
            agriculturalIcons = listOf("🌱", "🌿", "📖"),
            festivalsInMonth = listOf("Pésaj Shení (14 Iyar - Segunda Pascua)", "Lag BaOmer (18 Iyar - Día 33 del Omer)", "Yom HaAtzmaut (Día de la Independencia de Israel)"),
            historyMeaning = "Conocido en la Biblia como el mes de 'Ziv' (Brillo / Esplendor). Su nombre acadio 'Iyar' se interpreta acrónimamente como 'Ani YHVH Rofecha' (Yo soy el Señor tu Sanador). Mes de sanidad y estudio intensivo.",
            biblicalQuotes = listOf(
                BiblicalQuote("1 Reyes 6:1", "בְּחֹדֶשׁ זִו הוּא הַחֹדֶשׁ הַשֵּׁנִי", "...en el mes de Ziv, que es el mes segundo...", "Comienzo del Templo de Salomón."),
                BiblicalQuote("Números 9:11", "בַּחֹדֶשׁ הַשֵּׁנִי בְּאַרְבָּעָה עָשָׂר יוֹם... יַעֲשׂוּ אֹתוֹ", "En el mes segundo, a los catorce días... la celebrarán (Pésaj Shení)...", "La segunda oportunidad pascual.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "1 Reyes 6:1", "El mes se llama Ziv porque en él el mundo brilla con el esplendor de las flores y árboles en flor."),
                QuoteComment("Rabbi Yitzchak Luria", "Gate of Meditations", "Cada día de Iyar posee un mandamiento especial: el conteo diario del Omer.")
            ),
            midrashText = "El Midrash enseña que durante el mes de Iyar cayó por primera vez el Maná del cielo en el desierto, probando la fidelidad de Dios.",
            eschatologySignificance = "Sanidad completa de las naciones y preparación espiritual para la recepción de la luz divina."
        ),
        HebrewMonth(
            id = "sivan",
            nameSpanish = "Siván",
            nameHebrew = "סִיוָן",
            monthNumberCivil = 9,
            monthNumberReligious = 3,
            gregorianApprox = "Mayo - Junio",
            associatedTribe = "Zabulón (Comercio honesto / Sostén de la Torá)",
            associatedLetter = "Zayin (ז) - Espada / Corona",
            season = "Final de Primavera / Inicio de Verano",
            agriculturalHarvest = "Cosecha del trigo maduro y primeras frutas de la estación calurosa.",
            agriculturalIcons = listOf("🌾", "🍞", "📜"),
            festivalsInMonth = listOf("Shavuot (6 de Siván) - Entrega de la Torá"),
            historyMeaning = "El mes de la Revelación en el Sinaí. En Siván Dios descendió sobre el Monte Sinaí entre truenos y fuego para entregar la Torá a Israel unificado.",
            biblicalQuotes = listOf(
                BiblicalQuote("Éxodo 19:1", "בַּחֹדֶשׁ הַשְּׁלִישִׁי לְצֵאת בְּנֵי-יִשְׂרָאֵל מֵאֶרֶץ מִצְרָיִם... בָּאוּ מִדְבַּר סִינָי", "En el mes tercero de la salida de los hijos de Israel de Egipto... vinieron al desierto de Sinaí...", "Llegada al Sinaí."),
                BiblicalQuote("Esther 8:9", "בַּחֹדֶשׁ הַשְּׁלִישִׁי הוּא-חֹדֶשׁ סִיוָן", "...en el mes tercero, que es el mes de Siván...", "Decreto de liberación en Persia.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Talmud Shabbat 88a", "Rabbis", "Bendito sea el Dios que dio una Torá triple (Torá, Profetas, Escritos) a un pueblo triple en el mes tercero."),
                QuoteComment("Zohar", "Siván", "En Siván la luz de la verdad rompió los límites entre los cielos y la tierra.")
            ),
            midrashText = "El Midrash enseña que las montañas altivas competían por ser el lugar de la entrega de la Torá, pero Dios escogió al humilde Monte Sinaí en Siván.",
            eschatologySignificance = "Unión eterna entre Dios y la humanidad mediante la palabra viva que llena la tierra."
        ),
        HebrewMonth(
            id = "tammuz",
            nameSpanish = "Tamuz",
            nameHebrew = "תַּמּוּז",
            monthNumberCivil = 10,
            monthNumberReligious = 4,
            gregorianApprox = "Junio - Julio",
            associatedTribe = "Rubén (Primogénito / Ver)",
            associatedLetter = "Chet (ח) - Vida / Cercado",
            season = "Verano (Kayitz / Calor intenso)",
            agriculturalHarvest = "Cosecha de los primeros higos de verano y recogida de uvas tempranas.",
            agriculturalIcons = listOf("☀️", "🍇", "🧱"),
            festivalsInMonth = listOf("Ayuno del 17 de Tamuz (Shiva Asar B'Tammuz - Brecha en las murallas de Jerusalén)"),
            historyMeaning = "Inicio de las Tres Semanas de duelo (Bein HaMetzarim). El 17 de Tamuz se rompieron las murallas de Jerusalén antes de la destrucción del Templo y Moisés rompió las primeras tablas tras el becerro de oro.",
            biblicalQuotes = listOf(
                BiblicalQuote("Jeremías 39:2", "בַּחֹדֶשׁ הָרְבִיעִי בְּתִשְׁעָה לַחֹדֶשׁ הָבְקְעָה הָעִיר", "En el mes cuarto, a los nueve días del mes, fue abierta brecha en la ciudad...", "Brecha en las murallas."),
                BiblicalQuote("Zacarías 8:19", "צוֹם הָרְבִיעִי... יִהְיוּ לְבֵית-יְהוּדָה לְשָׂשׂוֹן", "El ayuno del mes cuarto... se convertirá en gozo y alegría...", "Promesa futura.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Mishnah Ta'anit 4:6", "Rabbis", "Cinco tragedias ocurrieron a nuestros padres el 17 de Tamuz: se rompieron las tablas, cesó el sacrificio diario, se abrió brecha en la ciudad, Apostomus quemó la Torá y se colocó un ídolo en el Templo."),
                QuoteComment("Bnei Yissaschar", "Tamuz", "El trabajo espiritual de Tamuz es reparar la visión del ojo para no caer en el extravío del becerro de oro.")
            ),
            midrashText = "El Midrash enseña que cuando Moisés descendió del monte en Tamuz y vio el becerro de oro, las letras sagradas volaron de las tablas de piedra de regreso al cielo.",
            eschatologySignificance = "Transformación futura de los días de duelo estival en grandes festivales de luz divina."
        ),
        HebrewMonth(
            id = "av",
            nameSpanish = "Av (Menachem Av)",
            nameHebrew = "אָב / מְנַחֵם אָב",
            monthNumberCivil = 11,
            monthNumberReligious = 5,
            gregorianApprox = "Julio - Agosto",
            associatedTribe = "Simeón (Escuchar / Arrepentimiento)",
            associatedLetter = "Tet (ט) - Bondad Oculta",
            season = "Verano pleno (Calor sofocante)",
            agriculturalHarvest = "Vendimia de la uva y secado de los higos al sol.",
            agriculturalIcons = listOf("☀️", "🍷", "🌾"),
            festivalsInMonth = listOf("Ayuno del 9 de Av (Tisha B'Av - Destrucción del Templo)", "Tu B'Av (15 de Av - Fiesta del Amor y la Reconciliación)"),
            historyMeaning = "Denominado con consuelo 'Menachem Av' (El Padre que Consuela). Contiene el día de mayor lamento (9 de Av) y uno de los más alegres (15 de Av, el día del amor y la unión matrimonial en Israel).",
            biblicalQuotes = listOf(
                BiblicalQuote("Números 33:38", "וַיַּעַל אַהֲרֹן הַכֹּהֵן... בַּחֹדֶשׁ הַחֲמִישִׁי בְּאֶחָד לַחֹדֶשׁ", "Y subió el sacerdote Aarón... en el mes quinto, el primero del mes...", "Fallecimiento de Aarón."),
                BiblicalQuote("Jeremías 1:3", "עַד-גְּלוֹת יְרוּשָׁלִַם בַּחֹדֶשׁ הַחֲמִישִׁי", "...hasta la cautividad de Jerusalén en el mes quinto...", "El exilio en Av.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Mishnah Ta'anit 4:8", "Rabban Shimon ben Gamliel", "No había días tan festivos para Israel como el 15 de Av y Yom Kipur, cuando las jóvenes de Jerusalén salían vestidas de blanco a bailar en los viñedos."),
                QuoteComment("Kedushat Levi", "Av", "En Av recibimos el consuelo del Padre Celestial que limpia nuestras lágrimas y promete el Tercer Templo.")
            ),
            midrashText = "El Midrash enseña que el León de Babilonia vino en el mes del signo del León (Av) a destruir el León de Dios (el Templo), para que el León Divino venga en Av a reconstruirlo.",
            eschatologySignificance = "Nacimiento y revelación del Rey Mesías que transforma las cenizas en coronas de gloria."
        ),
        HebrewMonth(
            id = "elul",
            nameSpanish = "Elul",
            nameHebrew = "אֱלוּל",
            monthNumberCivil = 12,
            monthNumberReligious = 6,
            gregorianApprox = "Agosto - Septiembre",
            associatedTribe = "Gad (Tropa de bendición / Valentía)",
            associatedLetter = "Yod (י) - Mano / Punto de Humildad",
            season = "Final de Verano / Inicio de Otoño",
            agriculturalHarvest = "Cosecha de dátiles, higos tardíos y preparación de los campos para el nuevo año.",
            agriculturalIcons = listOf("🎺", "📜", "🌾"),
            festivalsInMonth = listOf("Inicio del toque diario del Shofar al amanecer", "Recitación de Selichot (Peticiones de Perdón)", "40 días de Teshuvá hacia Yom Kipur"),
            historyMeaning = "El mes de la preparación del corazón y del amor recíproco. Su nombre E-L-U-L es acrónimo de 'Ani LeDodi VeDodi Li' ('Yo soy de mi amado y mi amado es mío' - Cantar de los Cantares 6:3). Moisés ascendió al Sinaí durante 40 días en Elul para recibir las segundas Tablas.",
            biblicalQuotes = listOf(
                BiblicalQuote("Cantar de los Cantares 6:3", "אֲנִי לְדוֹדִי וְדוֹדִי לִי", "Yo soy de mi amado, y mi amado es mío...", "El acrónimo de Elul."),
                BiblicalQuote("Nehemías 6:15", "וַתִּשְׁלַם הַחוֹמָה בְּעֶשְׂרִים וַחֲמִשָּׁה לֶאֱלוּל", "Fue acabada la muralla el veinticinco del mes de Elul...", "Reconstrucción de Jerusalén.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rabbi Shneur Zalman de Liadi", "Likkutei Torah", "En Elul 'El Rey está en el campo'. Cualquier persona puede acercarse a Él en su vestimenta cotidiana y Él recibe a todos con rostro sonriente."),
                QuoteComment("Rambam", "Hilchot Teshuvá", "Aunque el arrepentimiento es bueno todo el año, en el mes de Elul es aún más propicio y aceptado de inmediato.")
            ),
            midrashText = "El Midrash enseña que durante todo el mes de Elul se toca el Shofar para advertir al pueblo que se acerque el día del Juicio con amor y arrepentimiento sincero.",
            eschatologySignificance = "El llamado final a la humanidad para reconciliarse con el Creador antes de la manifestación de la gloria de Dios."
        )
    )
}
