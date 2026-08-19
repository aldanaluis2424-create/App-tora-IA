package com.example.data.repository

import com.example.data.model.BiblicalQuote
import com.example.data.model.JewishFeast
import com.example.data.model.QuoteComment
import com.example.data.model.TimelineEvent

object JewishFeastData {

    val feasts: List<JewishFeast> = listOf(
        JewishFeast(
            id = "shabbat",
            nameSpanish = "Shabāt (El Reposo Semanal)",
            nameHebrew = "שַׁבָּת",
            hebrewDate = "Cada 7º Día (Semanal)",
            gregorianApprox = "Semanal",
            themeColorHex = "#1E2A38",
            category = "Día Sagrado Semanal",
            history = "Instituido en la creación del mundo cuando Dios reposó al séptimo día y consagrado en el Sinaí como señal eterna del pacto.",
            biblicalBasis = "Génesis 2:1-3, Éxodo 20:8-11, Deuteronomio 5:12-15",
            mitzvot = listOf("Encendido de velas de Shabat", "Kiddush (Santificación sobre el vino)", "Tres comidas festivas con Challah", "Cese total de Melajó (39 trabajos creativos)"),
            customs = listOf("Cantar Shalom Aleichem para recibir a los ángeles", "Bendición de los hijos", "Eshet Chayil (Canto a la mujer virtuosa)", "Havdalá al finalizar el Shabat"),
            traditionalFoods = listOf("Challah (Pan trenzado)", "Pescado Gefilte", "Sopa de pollo con Matzah Balls", "Cholent / Jamin"),
            clothingAttire = "Vestimentas blancas o elegantes de fiesta reservadas exclusivamente para el Shabat.",
            ritualObjects = listOf("Candelabros de Shabat", "Copa de Kiddush de plata", "Cubierta de pan Challah bordada", "Vela trenzada de Havdalá y especias (Besamim)"),
            scriptureQuotes = listOf(
                BiblicalQuote("Éxodo 20:8", "זָכוֹר אֶת-יוֹם הַשַּׁבָּת לְקַדְּשׁוֹ", "Acuérdate del día de reposo para santificarlo.", "Mandamiento del Decálogo."),
                BiblicalQuote("Isaías 58:13", "וְקָרָאתָ לַשַּׁבָּת עֹנֶג", "...y llamarás al Shabat delicia, santo, glorioso del Señor...", "La delicia del reposo.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Achad Ha'am", "Ensayos Judíos", "Más de lo que Israel ha guardado el Shabat, el Shabat ha guardado a Israel."),
                QuoteComment("Rashi", "Génesis 2:2", "¿Qué le faltaba al mundo al sexto día? El reposo; vino el Shabat y el mundo se completó.")
            ),
            midrashText = "Dice el Midrash: El Shabat se presentó ante el Creador y dijo: 'Todos los días tienen su pareja, pero yo estoy solo'. Respondió el Santo: 'El pueblo de Israel será tu pareja'.",
            kabbalahText = "En la Kabbalah, el Shabat representa la unión de la Novia (Malchut) con el Novio Divino (Zeir Anpin), un anticipo del Mundo Venidero.",
            pardesPeshat = "Peshat: Cese del trabajo físico al séptimo día de la semana.",
            pardesRemez = "Remez: Insinúa el Milenio de Paz y la redención final.",
            pardesDerash = "Derash: Santificar el tiempo en lugar del espacio; el Shabat es un santuario construido en el tiempo.",
            pardesSod = "Sod: Adquisición de la Neshamah Yeterah (Alma Adicional) durante el día sagrado.",
            eschatology = "Representa el 'Yom Shekulo Shabat' (El Día que será enteramente Shabat), la era mesiánica de paz universal.",
            modernApplication = "Desconectarse de la tecnología y el estrés material para reconectarse con la familia, la comunidad y Dios.",
            timelineEvents = listOf(
                TimelineEvent("Viernes 18:00", "Encendido de velas y entrada de la santidad del Shabat", "Recepción de la Reina Shabat"),
                TimelineEvent("Viernes 20:00", "Cena festiva de Shabat con cánticos y bendiciones", "Unión familiar en paz"),
                TimelineEvent("Sábado 10:00", "Lectura de la Parashá de la semana en la Sinagoga", "Alimentación del alma"),
                TimelineEvent("Sábado 19:15", "Ceremonia de Havdalá con vino, fuego y especias", "Separación entre lo sagrado y lo cotidiano")
            )
        ),
        JewishFeast(
            id = "pesach",
            nameSpanish = "Pésaj (La Pascua)",
            nameHebrew = "פֶּסַח",
            hebrewDate = "15-22 de Nisán",
            gregorianApprox = "Marzo - Abril",
            themeColorHex = "#9B2226",
            category = "Fiesta de Peregrinaje (Shalosh Regalim)",
            history = "Celebración de la liberación gloriosa del pueblo de Israel de la esclavitud en Egipto bajo la mano poderosa de Dios.",
            biblicalBasis = "Éxodo 12:1-28, Levítico 23:4-8, Deuteronomio 16:1-8",
            mitzvot = listOf("Eliminación total del Chametz (levadura)", "Comer Matzah (Pan ázimo)", "Relatar la Haggadah a los niños en el Seder", "Beber las 4 copas de vino"),
            customs = listOf("Bedikat Chametz (Búsqueda de levadura)", "Seder de Pésaj con la bandeja pascual", "Afikoman (Ocultamiento de la media matzah)", "Kiddush y canto del Hallel"),
            traditionalFoods = listOf("Matzah", "Maror (Hierbas amargas)", "Charoset", "Zeroah (Hueso de cordero asado)"),
            clothingAttire = "Kittel blanco o ropas de fiesta blancas simbolizando libertad y pureza.",
            ritualObjects = listOf("Kearah (Plato del Seder)", "Copa de Elías el Profeta", "Haggadah de Pésaj", "Copa de Kiddush"),
            scriptureQuotes = listOf(
                BiblicalQuote("Éxodo 12:13", "וְרָאִיתִי אֶת-הַדָּם וּפָסַחְתִּי עֲלֵכֶם", "Y veré la sangre y pasaré de largo sobre vosotros...", "El milagro de la protección pascual."),
                BiblicalQuote("Deuteronomio 16:3", "לְמַעַן תִּזְכֹּר אֶת-יוֹם צֵאתְךָ מֵאֶרֶץ מִצְרַיִם", "...para que recuerdes el día de tu salida de Egipto...", "Memoria eterna de la liberación.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Hillel el Viejo", "Mishnah Pesachim", "En cada generación el ser humano debe considerarse como si él mismo hubiese salido de Egipto."),
                QuoteComment("Rambam", "Hilchot Chametz U'Matzah", "Contar el éxodo a los niños en la noche del Seder es un mandamiento bíblico supremo.")
            ),
            midrashText = "El Midrash enseña: Cuando los ángeles quisieron cantar alabanzas mientras los egipcios se ahogaban en el Mar Rojo, el Santo dijo: 'Mis creaciones se están ahogando en el mar, ¿y ustedes cantan alabanzas?'.",
            kabbalahText = "En la mística del Zohar, la Matzah es llamada 'Meichla deMehemanuta' (El Pan de la Fe) y 'Meichla d'Asvuta' (El Pan de la Sanidad).",
            pardesPeshat = "Peshat: Fiesta del pan ázimo el 15 de Nisán y recuerdo del cordero pascual.",
            pardesRemez = "Remez: Limpieza del Chametz (orgullo) para recibir la gracia de Dios.",
            pardesDerash = "Derash: Salir del Egipto espiritual (Mitzrayim ➔ Angostura) personal.",
            pardesSod = "Sod: Nacimiento cósmico del pueblo de Israel y rectificación de la fe primordial.",
            eschatology = "Prefiguración del Mesías como el Cordero de Dios y la Gran Redención Final.",
            modernApplication = "Examinar nuestro corazón para eliminar el orgullo y vivir en verdadera libertad espiritual.",
            timelineEvents = listOf(
                TimelineEvent("14 Nisán", "Búsqueda y quema del Chametz al mediodía", "Purificación de la casa"),
                TimelineEvent("15 Nisán (Noche)", "Gran Seder de Pésaj con las 4 copas y la Haggadah", "Celebración de la libertad"),
                TimelineEvent("15-21 Nisán", "Siete días comiendo pan sin levadura (Matzah)", "Días festivos de santidad"),
                TimelineEvent("21 Nisán", "Séptimo día de Pésaj: Paso del Mar Rojo", "Cántico de victoria (Shirat HaYam)")
            )
        ),
        JewishFeast(
            id = "chag_hamatzot",
            nameSpanish = "Chag HaMatzot (Fiesta de los Panes sin Levadura)",
            nameHebrew = "חַג הַמַּצּוֹת",
            hebrewDate = "15-21 de Nisán",
            gregorianApprox = "Marzo - Abril",
            themeColorHex = "#7F4F24",
            category = "Fiesta Sagrada de Siete Días",
            history = "Los siete días ordenados por Dios para abstenerse de todo alimento leudado, recordando la prisa con la que Israel salió de Egipto sin tiempo para que la masa leudara.",
            biblicalBasis = "Éxodo 12:15-20, Levítico 23:6-8, Números 28:17-25",
            mitzvot = listOf("No comer ni poseer nada con leudante (Chametz)", "Comer Matzah durante los 7 días", "Santificar el primer y el séptimo día como convocaciones santas"),
            customs = listOf("Preparación de recetas basadas exclusivamente en harina de Matzah", "Estudio de las leyes de pureza e integridad"),
            traditionalFoods = listOf("Matzah dura", "Matzah Brei", "Galletas de Matzah", "Sopas purificadas"),
            clothingAttire = "Ropas limpias de fiesta sin polvo ni leudante.",
            ritualObjects = listOf("Bandejas de Matzah", "Cajas de almacenamiento kasher lePésaj"),
            scriptureQuotes = listOf(
                BiblicalQuote("Éxodo 12:15", "שִׁבְעַת יָמִים מַצּוֹת תֹּאכֵלוּ", "Siete días comeréis panes sin levadura...", "Mandato de la semana sin levadura."),
                BiblicalQuote("1 Corintios 5:7", "Purificaos de la vieja levadura", "Para que seáis nueva masa, sin levadura como sois...", "Significado espiritual.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Éxodo 12:15", "El Chametz simboliza el mal impulso (Yetzer HaRa) que infla al hombre con orgullo."),
                QuoteComment("Sforno", "Comentario a la Torá", "La Matzah es el pan de la humildad y la verdad pura.")
            ),
            midrashText = "El Midrash compara el Chametz y la Matzah: ambos están hechos de harina y agua, pero el Chametz se infla con aire, mientras la Matzah permanece humilde y plana.",
            kabbalahText = "La Matzah conecta al alma con la luz pura de Chochmah sin interferencia del ego.",
            pardesPeshat = "Peshat: Abstenerse de masa leudada durante siete días.",
            pardesRemez = "Remez: Insinúa la purificación de la doctrina y los pensamientos corruptos.",
            pardesDerash = "Derash: Quitar la levadura del orgullo para caminar en la sencillez del pacto.",
            pardesSod = "Sod: Rectificación de la caída del hombre mediante el pan incorruptible.",
            eschatology = "Anticipa la era en que el pecado y la corrupción serán borrados de la tierra.",
            modernApplication = "Examinar la vida cotidiana para retirar hábitos tóxicos y actitudes soberbias.",
            timelineEvents = listOf(
                TimelineEvent("15 Nisán", "Primer día santo: Cese de trabajo servil", "Convocación Santa"),
                TimelineEvent("16-20 Nisán", "Chol HaMoed: Días intermedios festivos", "Alegría y santidad"),
                TimelineEvent("21 Nisán", "Séptimo día santo: Gran victoria en el Mar Rojo", "Cántico de salvación")
            )
        ),
        JewishFeast(
            id = "bikkurim",
            nameSpanish = "Bikkurim (Día de las Primicias)",
            nameHebrew = "בִּכּוּרִים",
            hebrewDate = "16 de Nisán (Día siguiente al Shabat pascual)",
            gregorianApprox = "Marzo - Abril",
            themeColorHex = "#2D6A4F",
            category = "Fiesta Agrícola y Profética",
            history = "El día en que se mecía la primera gavilla (Omer) de la cosecha de cebada ante el Señor en el Templo, dando inicio a la cuenta de 49 días hacia Shavuot.",
            biblicalBasis = "Levítico 23:9-14, Deuteronomio 26:1-11",
            mitzvot = listOf("Mecer la gavilla de cebada (Omer Reshit) ante Dios", "Presentar ofrendas de grano y cordero sin defecto", "Iniciar la Cuenta del Omer (Sefirat HaOmer)"),
            customs = listOf("Traer las primeras frutas de los 7 frutos de la Tierra de Israel a la casa de Dios", "Recitar la declaración de gratitud histórica"),
            traditionalFoods = listOf("Grano nuevo tostado (Kali)", "Cebada fresca", "Frutos tempranos de la tierra"),
            clothingAttire = "Túnicas agrícolas blancas y guirnaldas de primicias.",
            ritualObjects = listOf("Gavilla de cebada fresca", "Cestas decoradas de primicias", "Instrumentos de alabanza"),
            scriptureQuotes = listOf(
                BiblicalQuote("Levítico 23:11", "וְהֵנִיף אֶת-הָעֹמֶר לִפְנֵי יְהוָה", "Y mecerá la gavilla delante del Señor para que seáis aceptos...", "El mecido profético."),
                BiblicalQuote("1 Corintios 15:20", "Cristo, primicias de los que durmieron", "Mas ahora Cristo ha resucitado de los muertos; primicias de los que durmieron...", "Cumplimiento profético.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Philo de Alejandría", "De Specialibus Legibus", "El mecido de las primicias reconoce que toda la fertilidad de la tierra proviene de la mano de Dios."),
                QuoteComment("Mishnah Bikkurim", "Capítulo 3", "Las flautas tocaban ante los peregrinos que subían a Jerusalén con sus cestas de primicias.")
            ),
            midrashText = "El Midrash relata cómo los habitantes de Jerusalén salían a recibir con cánticos a los agricultores que traían sus cestas adornadas con cintas de oro y plata.",
            kabbalahText = "El Omer representa la elevación de las chispas divinas atrapadas en el mundo material desde Nisán hasta Siván.",
            pardesPeshat = "Peshat: Ofrenda de la primera cosecha de cebada en la tierra prometida.",
            pardesRemez = "Remez: Insinúa la resurrección y el nuevo nacimiento tras la muerte del grano.",
            pardesDerash = "Derash: Dar a Dios lo primero y lo mejor de nuestra vida antes de disfrutar del resto.",
            pardesSod = "Sod: Revelación de Zeir Anpin como las Primicias de la Creación.",
            eschatology = "Garantía de la gran cosecha de almas y de la resurrección de los justos.",
            modernApplication = "Honrar a Dios consagrando el primer fruto de nuestros ingresos y talentos a su servicio.",
            timelineEvents = listOf(
                TimelineEvent("16 Nisán (Mañana)", "Corte y mecido de la gavilla de cebada en el Templo", "Consagración de la cosecha"),
                TimelineEvent("16 Nisán (Noche)", "Inicio del Conteo del Omer (Día 1 de 49)", "Camino de preparación hacia el Sinaí")
            )
        ),
        JewishFeast(
            id = "shavuot",
            nameSpanish = "Shavuot (Pentecostés / Entrega de la Torá)",
            nameHebrew = "שָׁבוּעוֹת",
            hebrewDate = "6 de Siván",
            gregorianApprox = "Mayo - Junio",
            themeColorHex = "#2D6A4F",
            category = "Fiesta de Peregrinaje (Shalosh Regalim)",
            history = "Celebración del final de los 49 días de la Cuenta del Omer y la revelación gloriosa de la Torá en el Monte Sinaí.",
            biblicalBasis = "Éxodo 19-20, Levítico 23:15-21, Números 28:26-31",
            mitzvot = listOf("Ofrenda de los dos panes de trigo (Shtei HaLechem)", "Tikkun Leil Shavuot (Vigilia nocturna de estudio)", "Escuchar los Diez Mandamientos en la Sinagoga"),
            customs = listOf("Decorar casas y sinagogas con flores y ramas", "Comer alimentos lácteos y miel", "Lectura del Libro de Rut"),
            traditionalFoods = listOf("Cheesecake (Pastel de queso)", "Blintzes de queso", "Challah con miel"),
            clothingAttire = "Ropas blancas de fiesta celebrando la entrega de la ley.",
            ritualObjects = listOf("Rollos de la Torá", "Libro de Rut", "Adornos florales sagrados"),
            scriptureQuotes = listOf(
                BiblicalQuote("Éxodo 19:6", "וְאַתֶּם תִּהְיוּ-לִי מַמְלֶכֶת כֹּהֲנִים", "Y vosotros me seréis un reino de sacerdotes y gente santa.", "Propósito del pacto."),
                BiblicalQuote("Levítico 23:16", "עַד מִחֳרַת הַשַּׁבָּת הַשְּׁבִיעִת תִּסְפְּרוּ חֲמִשִּׁים יוֹם", "Hasta el día siguiente al séptimo sábado contaréis cincuenta días...", "La cuenta del Omer.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Éxodo 19:2", "Acamparon frente al monte 'como un solo hombre con un solo corazón'."),
                QuoteComment("Baal Shem Tov", "Imrei Kodesh", "En Shavuot volvemos a recibir la Torá con la misma fuerza que en el Sinaí.")
            ),
            midrashText = "Midrash Shemot Rabbah: Cuando Dios entregó la Torá, las aves no cantaron, los ángeles no volaron y el universo guardó silencio completo para escuchar la voz divina.",
            kabbalahText = "En la Kabbalah, Shavuot es la unión nupcial entre Tiferet (el Novio Divino) y Malchut (la Novia / Israel).",
            pardesPeshat = "Peshat: Fiesta agrícola de las primicias del trigo a los 50 días de Pésaj.",
            pardesRemez = "Remez: Los 50 días representan las 50 Puertas de la Sabiduría espiritual.",
            pardesDerash = "Derash: 'Naaseh VeNishma' (Haremos y escucharemos) aceptando la voluntad divina.",
            pardesSod = "Sod: Efusión del Espíritu Santo (Ruach HaKodesh) sobre los corazones limpios.",
            eschatology = "Promesa del nuevo pacto donde la Ley es escrita directamente en las tablas de carne del corazón.",
            modernApplication = "Dedicarse con fervor renovado al estudio diario de las Escrituras y la vida recta.",
            timelineEvents = listOf(
                TimelineEvent("5 Siván", "Preparación y purificación personal", "Vigilia previa"),
                TimelineEvent("6 Siván (Noche)", "Tikkun Leil Shavuot: Estudio de Torá toda la noche", "Vigilia espiritual"),
                TimelineEvent("6 Siván (Mañana)", "Lectura de los 10 Mandamientos y el Libro de Rut", "Recepción de la Ley"),
                TimelineEvent("6 Siván (Mediodía)", "Banquete lácteo festivo", "Alegría de la palabra")
            )
        ),
        JewishFeast(
            id = "rosh_hashanah",
            nameSpanish = "Rosh Hashaná (Año Nuevo Judío / Día de las Trompetas)",
            nameHebrew = "רֹאשׁ הַשָּׁנָה",
            hebrewDate = "1-2 de Tishrei",
            gregorianApprox = "Septiembre - Octubre",
            themeColorHex = "#9E721D",
            category = "Altas Fiestas (Yamim Noraim)",
            history = "Aniversario de la creación del primer ser humano (Adán) y día en que Dios juzga a todas las criaturas del mundo.",
            biblicalBasis = "Levítico 23:23-25, Números 29:1-6",
            mitzvot = listOf("Escuchar los 100 toques del Shofar", "Hacer Teshuvá (Arrepentimiento sincero)", "Tashlich (Arrojar los pecados al agua)"),
            customs = listOf("Mojar manzana en miel deseando un año dulce", "Comer cabeza de pescado (ser cabeza y no cola)", "Comer granada"),
            traditionalFoods = listOf("Manzana con miel", "Granada fresca", "Challah redonda", "Cabeza de pescado u oveja"),
            clothingAttire = "Vestimentas blancas impecables expresando pureza ante el juicio divino.",
            ritualObjects = listOf("Shofar de cuerno de carnero", "Machzor (Libro de Altas Fiestas)", "Plato de simanim"),
            scriptureQuotes = listOf(
                BiblicalQuote("Levítico 23:24", "זִכְרוֹן תְּרוּעָה מִקְרָא-קֹדֶשׁ", "...tendréis día de reposo, una conmemoración al son de trompetas...", "Institución de Yom Teruáh."),
                BiblicalQuote("Salmo 81:3", "תִּקְעוּ בַחֹדֶשׁ שׁוֹפָר", "Tocad el shofar en la nueva luna, en el día señalado...", "Canto jubiloso.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rambam", "Hilchot Teshuvá 3:4", "¡Despertad de vuestro sueño, durmientes, y examinad vuestras acciones!"),
                QuoteComment("Zohar", "Vayikra 98b", "El sonido del Shofar terrenal despierta la misericordia celestial.")
            ),
            midrashText = "Midrash Tanchuma: En Rosh Hashaná, Dios se levanta del Trono de Juicio y se sienta en el Trono de Misericordia cuando escucha el clamor del Shofar.",
            kabbalahText = "En la Kabbalah, Rosh Hashaná es el día de la coronación (Hamlachah) donde renovamos la soberanía de Dios.",
            pardesPeshat = "Peshat: Día del soplo y sonido del Shofar el 1º de Tishrei.",
            pardesRemez = "Remez: El cuerno del carnero evoca el sacrificio de Isaac (Akedat Yitzchak).",
            pardesDerash = "Derash: Despertar la conciencia moral dormida.",
            pardesSod = "Sod: Reconstrucción del edificio del Reino Divino (Binyan HaMalchut).",
            eschatology = "Anticipa el Gran Día del Señor y la resurrección al sonido del Gran Shofar.",
            modernApplication = "Examinar nuestra vida, buscar el perdón y comenzar el año renovados en Dios.",
            timelineEvents = listOf(
                TimelineEvent("1 Tishrei (Noche)", "Encendido de velas, Kiddush y cena con alimentos simbólicos", "Bendición del año dulce"),
                TimelineEvent("1 Tishrei (Mañana)", "Servicio en la sinagoga y 100 toques del Shofar", "Toque sagrado"),
                TimelineEvent("1 Tishrei (Tarde)", "Ceremonia de Tashlich junto al río o mar", "Petición de perdón"),
                TimelineEvent("2 Tishrei", "Segundo día festivo reafirmando la santidad", "Conclusión de Rosh Hashaná")
            )
        ),
        JewishFeast(
            id = "yom_kippur",
            nameSpanish = "Yom Kipur (Día de la Expiación)",
            nameHebrew = "יוֹם כִּפּוּר",
            hebrewDate = "10 de Tishrei",
            gregorianApprox = "Septiembre - Octubre",
            themeColorHex = "#1E2A38",
            category = "La Fiestas Más Sagrada (Shabat Shabaton)",
            history = "El día en que el Gran Sacerdote ingresaba al Lugar Santísimo (Kodesh HaKodashim) con la sangre de los sacrificios para expiar los pecados de todo el pueblo de Israel.",
            biblicalBasis = "Levítico 16:1-34, Levítico 23:26-32, Números 29:7-11",
            mitzvot = listOf("Ayuno estricto de 25 horas (sin comida ni agua)", "Cinco prohibiciones: no comer, no beber, no lavarse, no ungirse, no usar calzado de cuero, abstinencia matrimonial", "Oración de Kol Nidre y Ne'ilah"),
            customs = listOf("Kaparot (expiación simbólica previa)", "Pedir perdón a todas las personas dañadas durante el año", "Vestir Kittel blanco"),
            traditionalFoods = listOf("Seudah Mafseket (Cena abundante previa al ayuno)", "Comida de ruptura de ayuno suave con té y pastel"),
            clothingAttire = "Kittel blanco y medias sin cuero, representando la semejanza a los ángeles.",
            ritualObjects = listOf("Machzor de Yom Kipur", "Talit envuelto durante la oración de la noche", "Shofar de clausura (Ne'ilah)"),
            scriptureQuotes = listOf(
                BiblicalQuote("Levítico 16:30", "כִּי-בַיּוֹם הַזֶּה יְכַפֵּר עֲלֵיכֶם לְטַהֵר אֶתְכֶם", "Porque en este día se hará expiación por vosotros para purificaros...", "El perdón supremo."),
                BiblicalQuote("Isaías 1:18", "אִם-יִהְיוּ חֲטָאֵיכֶם כַּשָּׁנִים כַּשֶּׁלֶג יַלְבִּינוּ", "Si vuestros pecados fueren como la grana, como la nieve serán emblanquecidos...", "Promesa de purificación.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Mishnah Yoma 8:9", "Rabbi Akiva", "¡Dichosos vosotros, Israel! ¿Ante quién os purificáis y quién os purifica? Vuestro Padre celestial."),
                QuoteComment("Rambam", "Hilchot Teshuvá 2:7", "Yom Kipur es el tiempo del arrepentimiento para todos, tanto individuales como para la colectividad.")
            ),
            midrashText = "El Midrash relata que en Yom Kipur el Acusador (Satanás) no tiene permiso para acusar a Israel, pues sus letras numéricas suman 364 días, dejando a Yom Kipur libre de acusación.",
            kabbalahText = "En Yom Kipur se revela la dimensión de Yechidah, la chispa más elevada del alma conectada directamente con Keter.",
            pardesPeshat = "Peshat: Ayuno y purificación anual de los pecados del pueblo.",
            pardesRemez = "Remez: Insinúa la transformación total del alma y el perdón de Dios.",
            pardesDerash = "Derash: Los pecados entre el hombre y Dios son perdonados en Yom Kipur, pero los pecados entre el hombre y su prójimo requieren pedir perdón primero.",
            pardesSod = "Sod: Elevación del mundo al nivel de Bina (El Mundo Venidero).",
            eschatology = "Prefiguración del Juicio Final y la reconciliación universal con el Creador.",
            modernApplication = "Buscar el perdón de nuestros semejantes, restaurar relaciones rotas y consagrar la vida.",
            timelineEvents = listOf(
                TimelineEvent("9 Tishrei (Tarde)", "Cena previa (Seudah Mafseket) e inicio del ayuno de 25 horas", "Entrada en la santidad"),
                TimelineEvent("10 Tishrei (Noche)", "Oración de Kol Nidre e inicio de los servicios", "Clamor de perdón"),
                TimelineEvent("10 Tishrei (Todo el día)", "Cinco servicios de oración: Shacharit, Musaf, Minchá, Avodá", "Purificación angelical"),
                TimelineEvent("10 Tishrei (Atardecer)", "Oración final de Ne'ilah y toque final del Shofar", "Sellado del Libro de la Vida")
            )
        ),
        JewishFeast(
            id = "sukkot",
            nameSpanish = "Sukkot (Fiesta de las Cabañas / Tabernáculos)",
            nameHebrew = "סֻכּוֹת",
            hebrewDate = "15-21 de Tishrei",
            gregorianApprox = "Septiembre - Octubre",
            themeColorHex = "#2D6A4F",
            category = "Fiesta de Peregrinaje (Shalosh Regalim / Z'man Simchatenu)",
            history = "Recordatorio de las cabañas temporales en las que habitó Israel durante los 40 años en el desierto y de la protección de las Nubes de Gloria divinas.",
            biblicalBasis = "Levítico 23:33-44, Deuteronomio 16:13-17, Números 29:12-39",
            mitzvot = listOf("Morar y comer en la Sukkah (cabaña) durante 7 días", "Tomar las Cuatro Especies (Arbaat HaMinim: Lulav, Etrog, Hadas, Aravah) y mecerlas", "Regocijarse completamente"),
            customs = listOf("Construir y decorar la Sukkah con frutas y ramas", "Invitar Ushpizin (huéspedes patriarcales y necesitados)", "Nisuch HaMayim (Libación del agua en el Templo)"),
            traditionalFoods = listOf("Comidas servidas dentro de la Sukkah", "Estofados de verduras", "Frutas frescas de otoño", "Platos rellenos (Holishkes)"),
            clothingAttire = "Ropas festivas de otoño llenas de alegría y color.",
            ritualObjects = listOf("Sukkah con techo de ramas (Schach)", "Lulav (Rama de palma)", "Etrog (Cidra)", "Hadasim (Mirto)", "Aravot (Sauce)"),
            scriptureQuotes = listOf(
                BiblicalQuote("Levítico 23:42", "בַּסֻּכֹּת תֵּשְׁבוּ שִׁבְעַת יָמִים", "En cabañas habitaréis siete días...", "El mandamiento de morar en la Sukkah."),
                BiblicalQuote("Deuteronomio 16:15", "וְהָיִיתָ אַךְ שָׂמֵחַ", "...y estarás verdaderamente alegre.", "La fiesta de nuestra alegría.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Levítico 23:43", "Las cabañas eran las Nubes de Gloria con las que Dios rodeó a Israel para protegerlo del calor del desierto."),
                QuoteComment("Mishnah Sukkah 5:1", "Rabbis", "Aquel que no ha visto la alegría de la Libación del Agua no ha visto alegría en su vida.")
            ),
            midrashText = "El Midrash enseña que las 4 Especies representan 4 tipos de seres humanos: el Etrog (sabor y olor) representa a quien tiene Torá y buenas obras; la Palma (sabor sin olor) a quien tiene Torá; el Mirto (olor sin sabor) a quien tiene buenas obras; y el Sauce (ni sabor ni olor) representa al humilde. Todos se unen en un solo mazo.",
            kabbalahText = "La Sukkah representa el Abrazo Divino (Chibuk) donde la Shejiná rodea al fiel por todos lados.",
            pardesPeshat = "Peshat: Vivir en cabañas temporales tras la cosecha de otoño.",
            pardesRemez = "Remez: Insinúa la fragilidad de la vida terrenal y nuestra morada temporal.",
            pardesDerash = "Derash: Confiar en la protección de Dios en lugar de la solidez de las paredes de piedra.",
            pardesSod = "Sod: Entrada en la sombra de la fe (Tzila D'Mehemanuta).",
            eschatology = "Anticipa el Reinado Mesiánico donde todas las naciones subirán a Jerusalén a celebrar Sukkot (Zacarías 14:16).",
            modernApplication = "Aprender la verdadera gratitud, practicar la hospitalidad y confiar en el cuidado de Dios.",
            timelineEvents = listOf(
                TimelineEvent("15 Tishrei (Noche)", "Entrada en la Sukkah, Kiddush y primera cena festiva", "Abrazo de la Sukkah"),
                TimelineEvent("15-21 Tishrei", "Comer y morar en la Sukkah y bendición diaria de las 4 Especies", "Setenta sacrificios por las naciones"),
                TimelineEvent("21 Tishrei", "Hoshaná Rabbah: Séptimo día de Sukkot con 7 vueltas con el Lulav", "Gran Salvación y sellado del agua")
            )
        ),
        JewishFeast(
            id = "shemini_atzeret",
            nameSpanish = "Sheminí Atzeret / Simjat Torá (Octavo Día / Regocijo de la Torá)",
            nameHebrew = "שְׁמִינִי עֲצֶרֶת / שִׂמְחַת תּוֹרָה",
            hebrewDate = "22-23 de Tishrei",
            gregorianApprox = "Octubre",
            themeColorHex = "#9E721D",
            category = "Conclusión Festiva Suprema",
            history = "El octavo día de detención sagrada donde Dios pide a Israel quedarse un día más a solas con Él tras los 7 días de Sukkot, culminando con la finalización y reinicio del ciclo anual de lectura de la Torá.",
            biblicalBasis = "Levítico 23:36, Números 29:35-38",
            mitzvot = listOf("Detención sagrada (Atzeret)", "Oración por la lluvia (Tefillat Geshem)", "Bailar con los rollos de la Torá (Hakafot)"),
            customs = listOf("Siete vueltas (Hakafot) cantando y bailando con los rollos de la Torá", "Llamar a todos los niños a la Torá (Kol HaNe'arim)", "Lectura de los últimos versos de Deuteronomio y los primeros de Génesis"),
            traditionalFoods = listOf("Comidas festivas ricas", "Dulces y golosinas repartidas a los niños", "Vino de celebración"),
            clothingAttire = "Trajes elegantes de gala y vestidos de máxima alegría.",
            ritualObjects = listOf("Rollos de la Torá adornados con coronas de plata", "Banderas de Simjat Torá para niños", "Velas de fiesta"),
            scriptureQuotes = listOf(
                BiblicalQuote("Números 29:35", "בַּיּוֹם הַשְּׁמִינִי עֲצֶרֶת תִּהְיֶה לָכֶם", "El octavo día tendréis asamblea solemne...", "Institución del octavo día."),
                BiblicalQuote("Salmo 119:162", "שָׂשׂ אָנֹכִי עַל-אִמְרָתֶךָ", "Me regocijo en tu palabra como el que halla muchos despojos...", "La alegría de la Torá.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Levítico 23:36", "Es como un rey que invitó a sus hijos a un banquete. Cuando llegó el momento de irse, dijo: 'Por favor, quédense un día más, me cuesta separarme de ustedes'."),
                QuoteComment("Baal Shem Tov", "Keter Shem Tov", "En Simjat Torá, los pies que bailan con la Torá alcanzan un nivel más alto que el intelecto.")
            ),
            midrashText = "El Midrash enseña que en Sukkot se ofrecían 70 toros por las 70 naciones, pero en Sheminí Atzeret se ofrece un solo toro por el amor íntimo entre Dios e Israel.",
            kabbalahText = "Representa el nivel de Yichud (Unión Suprema) por encima del tiempo y del espacio.",
            pardesPeshat = "Peshat: Octavo día de asamblea de cierre del ciclo festivo.",
            pardesRemez = "Remez: El número 8 simboliza lo que está más allá del ciclo natural de 7 días.",
            pardesDerash = "Derash: Regocijarse en la entrega completa del texto sagrado que renueva la vida.",
            pardesSod = "Sod: Absorción de la luz trascendente del Infinito (Ein Sof).",
            eschatology = "Anticipa la alegría eterna del Olam Haba donde la sabiduría divina será el deleite perpetuo.",
            modernApplication = "Renovar la pasión por la lectura y vivencia diaria de las Escrituras sagradas.",
            timelineEvents = listOf(
                TimelineEvent("22 Tishrei (Noche)", "Hakafot nocturnas: Baile con la Torá en la sinagoga", "Cánticos de júbilo"),
                TimelineEvent("22 Tishrei (Mañana)", "Oración por la lluvia (Tefillat Geshem) y Hakafot matutinas", "Petición de bendición agrícola"),
                TimelineEvent("23 Tishrei", "Simjat Torá: Lectura de Chatan Torá (Final) y Chatan Bereshit (Inicio)", "Reinicio del ciclo sagrado")
            )
        ),
        JewishFeast(
            id = "chanukah",
            nameSpanish = "Janucá (Fiesta de las Luces / Dedicación)",
            nameHebrew = "חֲנֻכָּה",
            hebrewDate = "25 de Kislev al 2-3 de Tevet",
            gregorianApprox = "Diciembre",
            themeColorHex = "#1E2A38",
            category = "Fiesta de Victoria e Iluminación Histórica",
            history = "La victoria milagrosa de los Macabeos sobre el imperio seléucida que intentó proscribir la Torá, y el milagro de la vasija de aceite puro que ardió 8 días en el Templo.",
            biblicalBasis = "Libros de los Macabeos, Profecía de Hageo 2:18-20, Juan 10:22",
            mitzvot = listOf("Encendido progresivo de la Hanukkiah (1 vela la 1ª noche hasta 8 velas la 8ª noche)", "Publicar el milagro (Pirsumei Nisa)", "Recitar el Hallel completo diario"),
            customs = listOf("Jugar al Sevivon / Dreidel (Trompo de 4 letras: Nun, Gimel, He, Shin)", "Dar dinero de Janucá (Janucá Gelt) a los niños", "Cantar Maoz Tzur"),
            traditionalFoods = listOf("Sufganiyot (Buñuelos rellenos de mermelada)", "Latkes (Tortitas de patata fritas en aceite)", "Alimentos fritos en aceite de oliva"),
            clothingAttire = "Ropas festivas de invierno.",
            ritualObjects = listOf("Hanukkiah (Candelabro de 9 brazos)", "Aceite de oliva puro o velas de cera", "Sevivon / Dreidel"),
            scriptureQuotes = listOf(
                BiblicalQuote("Zacarías 4:6", "לֹא בְחַיִל וְלֹא בְכֹחַ כִּי אִם-בְּרוּחִי", "No con ejército ni con fuerza, sino con mi Espíritu, ha dicho el Señor...", "El lema de Janucá."),
                BiblicalQuote("Juan 10:22", "Celebrábase en Jerusalén la fiesta de la Dedicación", "Y era invierno, y Jesús andaba en el templo por el pórtico de Salomón...", "Mención del Nuevo Testamento.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Talmud Shabbat 21b", "Rabbis", "¿Qué es Janucá? Cuando los griegos entraron al Templo contaminaron los aceites. Los Macabeos hallaron una sola vasija con el sello del Gran Sacerdote para un solo día, pero ardió ocho días."),
                QuoteComment("Maharal de Praga", "Ner Mitzvah", "Janucá demuestra que la luz de la verdad no puede ser extinguida por la fuerza de las armas.")
            ),
            midrashText = "El Midrash relata cómo las mujeres judías, lideradas por Yehudit (Judith), mostraron valentía heroica al defender la pureza de la fe contra los decretos paganos.",
            kabbalahText = "Las 36 velas encendidas en total representan la Luz Primordial (Or HaGanuz) escondida desde la creación.",
            pardesPeshat = "Peshat: Conmemoración de la victoria militar y re-dedicación del Templo.",
            pardesRemez = "Remez: Insinúa que una pequeña cantidad de luz pura disipa mucha oscuridad.",
            pardesDerash = "Derash: Mantener la llama del alma ardiendo frente a la asimilación cultural.",
            pardesSod = "Sod: Iluminación de los 8 niveles superiores del intelecto divino.",
            eschatology = "Anticipa la victoria final de la luz del Mesías sobre todos los imperios del mundo.",
            modernApplication = "Defender la libertad de culto, llevar la luz a lugares oscuros y no avergonzarse de la fe.",
            timelineEvents = listOf(
                TimelineEvent("25 Kislev (Noche)", "Encendido de la 1ª vela de Janucá y bendición", "Inicio de la iluminación"),
                TimelineEvent("25-2 Kislev/Tevet", "Encendido diario añadiendo una vela cada noche", "Crecimiento progresivo de la luz"),
                TimelineEvent("2 Tevet", "Zot Janucá: Octava noche con las 8 velas encendidas completas", "Plenitud de la luz milagrosa")
            )
        ),
        JewishFeast(
            id = "tu_bishvat",
            nameSpanish = "Tu BiShvat (Año Nuevo de los Árboles)",
            nameHebrew = "טִ״ו בִּשְׁבָט",
            hebrewDate = "15 de Shevat",
            gregorianApprox = "Enero - Febrero",
            themeColorHex = "#2D6A4F",
            category = "Fiesta Ecológica y Agrícola",
            history = "La fecha que marca el fin de la estación de lluvias invernales en Israel y el renacer de la savia en los árboles, usada históricamente para el cálculo de diezmos de los frutos.",
            biblicalBasis = "Deuteronomio 20:19, Levítico 19:23-25",
            mitzvot = listOf("Cálculo del diezmo de los frutos (Ma'aser)", "Bendecir al Creador por la abundancia de los árboles", "Plantar árboles en la Tierra de Israel"),
            customs = listOf("Seder de Tu BiShvat (comer 15 frutos distintos con 4 copas de vino blanco y tinto)", "Comer de las 7 Especies de la Tierra de Israel (Trigo, Cebada, Uva, Higo, Granada, Olivo, Dátil)", "Oración por un Etrog hermoso"),
            traditionalFoods = listOf("Frutos secos (Dátiles, Higos, Pasas, Almendras)", "Granadas", "Aceitunas", "Pan de trigo entero"),
            clothingAttire = "Ropas cómodas en tonos verdes y de naturaleza.",
            ritualObjects = listOf("Bandeja de frutos de las 7 Especies", "Vino blanco y tinto para el Seder cabalístico", "Herramientas de plantación"),
            scriptureQuotes = listOf(
                BiblicalQuote("Deuteronomio 20:19", "כִּי הָאָדָם עֵץ הַשָּׂדֶה", "...porque ¿es acaso el árbol del campo un hombre...?", "Comparación del hombre con el árbol."),
                BiblicalQuote("Salmo 1:3", "וְהָיָה כְּעֵץ שָׁתוּל עַל-פַּלְגֵי מָיִם", "Será como árbol plantado junto a corrientes de aguas...", "El justo da fruto.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Mishnah Rosh Hashanah 1:1", "Beit Hillel", "El 15 de Shevat es el Año Nuevo de los árboles."),
                QuoteComment("Rabbi Yitzchak Luria (Ari)", "Gate of Fruits", "Comer los frutos en Tu BiShvat con santa intención eleva las chispas divinas de la creación.")
            ),
            midrashText = "El Midrash enseña que cuando los árboles florecen en Shevat, la tierra de Israel despierta de su sueño invernal y canta alabanzas al Creador.",
            kabbalahText = "El Seder de Tu BiShvat conecta los cuatro mundos (Asiyah, Yetzirah, Beriah, Atzilut) comiendo frutos con cáscara, frutos con semilla y frutos totalmente comestibles.",
            pardesPeshat = "Peshat: Fecha agrícola para el cómputo de diezmos de árboles frutales.",
            pardesRemez = "Remez: Insinúa el florecimiento interior del ser humano comparado a un árbol.",
            pardesDerash = "Derash: Echar raíces profundas en la Torá para resistir los vientos de las pruebas.",
            pardesSod = "Sod: Sustento del Árbol de la Vida (Etz HaChayim) celestial.",
            eschatology = "Anticipa la restauración del Edén donde los árboles darán fruto doce meses al año.",
            modernApplication = "Proteger la naturaleza, cuidar el medio ambiente y nutrir nuestras raíces espirituales.",
            timelineEvents = listOf(
                TimelineEvent("15 Shevat (Noche)", "Seder de Tu BiShvat con frutos y 4 copas de vino", "Celebración de la creación"),
                TimelineEvent("15 Shevat (Día)", "Plantación de árboles en la tierra de Israel", "Renovación de la tierra")
            )
        ),
        JewishFeast(
            id = "purim",
            nameSpanish = "Purim (Fiesta de las Suertes / Salvación de Ester)",
            nameHebrew = "פּוּרִים",
            hebrewDate = "14-15 de Adar",
            gregorianApprox = "Febrero - Marzo",
            themeColorHex = "#9B2226",
            category = "Fiesta de Alegría y Rescate Histórico",
            history = "La milagrosa salvación del pueblo judío del decreto de exterminio promovido por el malvado Amán en el Imperio Persa, gracias a la fe de Mardoqueo y el valor de la Reina Ester.",
            biblicalBasis = "Libro de Ester 1-10",
            mitzvot = listOf("Lectura pública de la Megilát Ester (el rollo de Ester)", "Mishloach Manot (Enviar porciones de comida a los amigos)", "Matanot LaEvyonim (Dar regalos y caridad a los pobres)", "Seudát Purim (Banquete festivo de alegría)"),
            customs = listOf("Disfrazarse para recordar que la mano de Dios estuvo oculta tras los eventos", "Hacer ruido con carracas (Ra'ashan) al escuchar el nombre de Amán", "Comer Orejas de Amán (Oznei Haman)"),
            traditionalFoods = listOf("Oznei Haman / Hamantaschen (Pasteles triangulares rellenos de amapola, chocolate o fruta)", "Pavo asado", "Vino festivo"),
            clothingAttire = "Disfraces festivos y coloridos llenos de humor y alegría.",
            ritualObjects = listOf("Pergamino manuscrito de la Megilát Ester", "Carracas (Ra'ashan)", "Cestas de regales (Mishloach Manot)"),
            scriptureQuotes = listOf(
                BiblicalQuote("Ester 9:22", "לַעֲשׂוֹת אוֹתָם יְמֵי מִשְׁתֶּה וְשִׂמְחָה", "...para que los hiciesen días de banquete y de gozo, y de enviar porciones...", "Mandato de Purim."),
                BiblicalQuote("Ester 4:14", "וּמִי יוֹדֵעַ אִם-לְעֵת כָּזֹאת הִגַּעְתְּ לַמַּלְכוּת", "...¿y quién sabe si para esta hora has llegado al reino...?", "Llamado al valor de Ester.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Talmud Megillah 7b", "Rava", "Una persona debe alegrarse en Purim hasta no distinguir entre 'Maldito sea Amán' y 'Bendito sea Mardoqueo'."),
                QuoteComment("Rambam", "Hilchot Megillah 2:18", "Todas las escrituras proféticas cesarán en la era mesiánica excepto el Libro de Ester y las leyes de Purim.")
            ),
            midrashText = "El Midrash enseña que aunque el Nombre de Dios no aparece explícitamente en todo el Libro de Ester, cada vez que dice 'El Rey' insinúa al Rey del Universo orquestando la salvación desde las sombras.",
            kabbalahText = "Purim revela el nivel de Hester Panim (Rostro Oculto) donde Dios opera mediante la providencia natural.",
            pardesPeshat = "Peshat: Anulación del decreto de Amán y victoria de los judíos en Persia.",
            pardesRemez = "Remez: Insinúa que no hay coincidencias en la vida; todo es Providencia Divina (Hashgachá Pratit).",
            pardesDerash = "Derash: Los disfraces enseñan que no debemos juzgar a las personas por su apariencia externa.",
            pardesSod = "Sod: Transformación del mal en bien supremo en la raíz del pensamiento divino.",
            eschatology = "Anticipa la victoria final sobre Amalec y la revelación completa de Dios en la historia humana.",
            modernApplication = "Tener el valor de defender nuestra identidad espiritual y cuidar de los necesitados con alegría.",
            timelineEvents = listOf(
                TimelineEvent("13 Adar", "Ta'anit Ester: Ayuno de Ester recordando la intercesión", "Preparación en oración"),
                TimelineEvent("14 Adar (Noche)", "Lectura de la Megilá en la sinagoga y ruidos contra Amán", "Escuchar la salvación"),
                TimelineEvent("14 Adar (Día)", "Envío de regalos (Mishloach Manot), caridad y gran banquete", "Día de fiesta y gozo"),
                TimelineEvent("15 Adar", "Shushan Purim: Celebración en ciudades amuralladas como Jerusalén", "Alegría extendida")
            )
        ),
        JewishFeast(
            id = "tisha_bav",
            nameSpanish = "Tisha B'Av (El Noveno día de Av - Ayuno de la Memoria)",
            nameHebrew = "תִּשְׁעָה בְּאָב",
            hebrewDate = "9 de Av",
            gregorianApprox = "Julio - Agosto",
            themeColorHex = "#9B2226",
            category = "Día de Duelo Solemnísimo",
            history = "El día trágico en que fueron destruidos tanto el Primer Templo de Salomón (586 a.C.) por los babilonios como el Segundo Templo (70 d.C.) por los romanos, así como el decreto del desierto tras el informe de los espías.",
            biblicalBasis = "Números 14:1-35, Lamentaciones de Jeremías, Zacarías 8:19",
            mitzvot = listOf("Ayuno estricto de 25 horas", "Cinco aflicciones (sin comida, bebida, lavado, unción, ni calzado de cuero)", "Sentarse en el suelo o en sillas bajas hasta el mediodía", "Lectura del Libro de Lamentaciones (Eichah)"),
            customs = listOf("Apagar las luces de la sinagoga y leer a la luz de las velas", "No estudiar Torá alegre (solo pasajes tristes y de duelo)", "Visitar el Muro de las Lamentaciones (Kotel)"),
            traditionalFoods = listOf("Seudah Hamafseket (Cena final previa con huevo duro mojado en ceniza, comido en el suelo)"),
            clothingAttire = "Ropas sencillas de duelo, sin calzado de cuero.",
            ritualObjects = listOf("Libro de Lamentaciones (Eichah)", "Kinot (Elegías poéticas de duelo)", "Cojines para sentarse en el suelo"),
            scriptureQuotes = listOf(
                BiblicalQuote("Lamentaciones 1:1", "אֵיכָה יָשְׁבָה בָדָד הָעִיר רַבָּתִי עָם", "¡Cómo ha quedado sola la ciudad populosa!...", "Apertura de Lamentaciones."),
                BiblicalQuote("Zacarías 8:19", "צּוֹם הָרְבִיעִי וְצוֹם הַחֲמִישִׁי... יִהְיֶה לְבֵית-יְהוּדָה לְשָׂשׂוֹן וּלְשִׂמְחָה", "El ayuno del quinto mes se convertirá para la casa de Judá en gozo y alegría...", "Promesa profética.")
            ),
            rabbinicComments = listOf(
                QuoteComment("Talmud Yoma 9b", "Rabbis", "¿Por qué fue destruido el Segundo Templo? Por el odio gratuito (Sin'at Chinam) entre hermanos."),
                QuoteComment("Napoleon Bonaparte", "Francia", "Un pueblo que llora por su Templo destruido hace 2000 años es un pueblo que vivirá para ver su reconstrucción.")
            ),
            midrashText = "El Midrash enseña que el Mesías (Mashiach) nace simbólicamente en la tarde de Tisha B'Av, enseñando que desde el corazón del dolor florece la redención futura.",
            kabbalahText = "En Tisha B'Av la Shejiná se inclina en el polvo llorando con sus hijos en el exilio.",
            pardesPeshat = "Peshat: Ayuno nacional recordando la destrucción del Templo y los exilios.",
            pardesRemez = "Remez: Insinúa la necesidad de reparar el odio infundado con amor incondicional (Ahavat Chinam).",
            pardesDerash = "Derash: Quien se aflige con Jerusalén en su duelo merecerá ver su alegría consolada.",
            pardesSod = "Sod: Ocultamiento profundo de las luces para la preparación del Tercer Templo celestial.",
            eschatology = "Promesa profética de que Tisha B'Av se transformará en el día de mayor fiesta en la era mesiánica.",
            modernApplication = "Erradicar la división, amar a nuestro hermano incondicionalmente y anhelar la paz de Jerusalén.",
            timelineEvents = listOf(
                TimelineEvent("8 Av (Atardecer)", "Cena de duelo (Seudah Hamafseket) con huevo y ceniza en el suelo", "Inicio de las 25 horas de duelo"),
                TimelineEvent("9 Av (Noche)", "Lectura de Lamentaciones (Eichah) a la luz de las velas en el suelo", "Lamento por la destrucción"),
                TimelineEvent("9 Av (Mañana)", "Recitación de elegías (Kinot) sin vestir Tefilín ni Talit", "Profundidad del ayuno"),
                TimelineEvent("9 Av (Tarde)", "Colocación de Tefilín en Minchá y consuelo del nacimiento del Mesías", "Esperanza de consuelo")
            )
        )
    )
}
