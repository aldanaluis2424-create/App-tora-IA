package com.example.data.repository

import com.example.data.model.BibleBook
import com.example.data.model.BibleChapterDetail
import com.example.data.model.BibleVerse
import com.example.data.model.QuoteComment

object BibleData {

    val books: List<BibleBook> = listOf(
        // Torá
        BibleBook(
            id = "genesis",
            nameSpanish = "Génesis (Bereshit)",
            nameHebrew = "בְּרֵאשִׁית",
            category = "Torá (Pentateuco)",
            chapterCount = 50,
            summary = "El libro de los comienzos: la creación del universo, la caída, el pacto abrahámico y los patriarcas de Israel.",
            keyVerse = "Génesis 1:1 - En el principio creó Dios los cielos y la tierra.",
            icon = "🌌"
        ),
        BibleBook(
            id = "exodus",
            nameSpanish = "Éxodo (Shemot)",
            nameHebrew = "שְׁמוֹת",
            category = "Torá (Pentateuco)",
            chapterCount = 40,
            summary = "La salida heroica de la esclavitud en Egipto, la entrega de los Diez Mandamientos en el Sinaí y la edificación del Tabernáculo.",
            keyVerse = "Éxodo 3:14 - Y respondió Dios a Moisés: YO SOY EL QUE SOY.",
            icon = "🔥"
        ),
        BibleBook(
            id = "leviticus",
            nameSpanish = "Levítico (Vayikra)",
            nameHebrew = "וַיִּקְרָא",
            category = "Torá (Pentateuco)",
            chapterCount = 27,
            summary = "Manual sacerdotal de pureza, sacrificios sagrados, el Gran Día de la Expiación (Yom Kipur) y las siete Fiestas de Adonai.",
            keyVerse = "Levítico 19:2 - Santos seréis, porque santo soy yo el Señor vuestro Dios.",
            icon = "🕎"
        ),
        BibleBook(
            id = "numbers",
            nameSpanish = "Números (Bamidbar)",
            nameHebrew = "בְּמִדְבַּר",
            category = "Torá (Pentateuco)",
            chapterCount = 36,
            summary = "La travesía de 40 años en el desierto, censos de las tribus, la bendición sacerdotal y la fe probada.",
            keyVerse = "Números 6:24-26 - El Señor te bendiga y te guarde...",
            icon = "⛺"
        ),
        BibleBook(
            id = "deuteronomy",
            nameSpanish = "Deuteronomio (Devarim)",
            nameHebrew = "דְּבָרִים",
            category = "Torá (Pentateuco)",
            chapterCount = 34,
            summary = "Discurso final de Moisés antes de entrar a la Tierra Prometida, la oración del Shemá Israel y la renovación del pacto.",
            keyVerse = "Deuteronomio 6:4 - Oye, Israel: el Señor nuestro Dios, el Señor uno es.",
            icon = "📜"
        ),
        // Nevi'im
        BibleBook(
            id = "isiah",
            nameSpanish = "Isaías (Yeshayahu)",
            nameHebrew = "יְשַׁעְיָהוּ",
            category = "Nevi'im (Profetas)",
            chapterCount = 66,
            summary = "El príncipe de los profetas: visiones gloriosas del Mesías sufriente y reinante, consuelo e Israel restaurado.",
            keyVerse = "Isaías 9:6 - Porque un niño nos es nacido, hijo nos es dado...",
            icon = "👑"
        ),
        BibleBook(
            id = "jeremiah",
            nameSpanish = "Jeremías (Yirmeyahu)",
            nameHebrew = "יִרְמְיָהוּ",
            category = "Nevi'im (Profetas)",
            chapterCount = 52,
            summary = "El profeta llorón y el anuncio del Nuevo Pacto grabado directamente en los corazones.",
            keyVerse = "Jeremías 31:33 - Pondré mi ley en su mente, y la escribiré en su corazón...",
            icon = "💔"
        ),
        // Ketuvim
        BibleBook(
            id = "psalms",
            nameSpanish = "Salmos (Tehilim)",
            nameHebrew = "תְּהִלִּים",
            category = "Ketuvim (Escritos)",
            chapterCount = 150,
            summary = "El libro de alabanzas y oraciones del Rey David: cánticos de fe, profecías mesánicas y refugio espiritual.",
            keyVerse = "Salmo 23:1 - El Señor es mi pastor; nada me faltará.",
            icon = "🎵"
        ),
        BibleBook(
            id = "proverbs",
            nameSpanish = "Proverbios (Mishlei)",
            nameHebrew = "מִשְׁלֵי",
            category = "Ketuvim (Escritos)",
            chapterCount = 31,
            summary = "Sabiduría práctica del Rey Salomón para la vida diaria, el temor del Señor y la rectitud moral.",
            keyVerse = "Proverbios 1:7 - El principio de la sabiduría es el temor del Señor.",
            icon = "💡"
        ),
        // Brit Hadashah
        BibleBook(
            id = "matthew",
            nameSpanish = "Mateo (Matityahu)",
            nameHebrew = "מַתִּתְיָהוּ",
            category = "Brit Hadashah (Nuevo Pacto)",
            chapterCount = 28,
            summary = "El evangelio del Rey Mesías presentado a Israel, demostrando el cumplimiento de las profecías del Tanaj.",
            keyVerse = "Mateo 5:17 - No penséis que he venido para abrogar la ley o los profetas...",
            icon = "🦁"
        ),
        BibleBook(
            id = "john",
            nameSpanish = "Juan (Yochanan)",
            nameHebrew = "יוֹחָנָן",
            category = "Brit Hadashah (Nuevo Pacto)",
            chapterCount = 21,
            summary = "El Verbo encarnado, la Luz del Mundo, el Cordero de Dios y la vida eterna manifestada.",
            keyVerse = "Juan 1:1 - En el principio era el Verbo, y el Verbo era con Dios...",
            icon = "🦅"
        )
    )

    fun getChapterDetail(bookId: String, chapterNumber: Int): BibleChapterDetail {
        return when (bookId) {
            "genesis" -> getGenesisChapter(chapterNumber)
            "psalms" -> getPsalmChapter(chapterNumber)
            "john" -> getJohnChapter(chapterNumber)
            else -> getGenericChapter(bookId, chapterNumber)
        }
    }

    private fun getGenesisChapter(chapter: Int): BibleChapterDetail {
        return BibleChapterDetail(
            bookId = "genesis",
            bookName = "Génesis (Bereshit)",
            chapterNumber = chapter,
            verses = listOf(
                BibleVerse(
                    number = 1,
                    textSpanish = "En el principio creó Dios los cielos y la tierra.",
                    textHebrew = "בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הָאָרֶץ׃",
                    textGreek = "Ἐν ἀρχῇ ἐποίησεν ὁ θεὸς τὸν οὐρανὸν καὶ τὴν γῆν.",
                    transliteration = "Bereshit bara Elohim et hashamayim ve'et ha'aretz.",
                    notes = "La primera palabra 'Bereshit' contiene 6 letras que aluden a los 6,000 años de historia antes de la era mesiánica."
                ),
                BibleVerse(
                    number = 2,
                    textSpanish = "Y la tierra estaba desordenada y vacía, y las tinieblas estaban sobre la faz del abismo, y el Espíritu de Dios se movía sobre la faz de las aguas.",
                    textHebrew = "וְהָאָרֶץ הָיְתָה תֹהוּ וָבֹהוּ וְחֹשֶׁךְ עַל-פְּנֵי תְהוֹם וְרוּחַ אֱלֹהִים מְרַחֶפֶת עַל-פְּנֵי הַמָּיִם׃",
                    textGreek = "ἡ δὲ γῆ ἦν ἀόρατος καὶ ἀκατασκεύαστος καὶ σκότος ἐπάνω τῆς ἀβύσσου.",
                    transliteration = "Veha'aretz haytah tohu vavohu vechoshech al-pney tehom veruach Elohim merachefet al-pney hamayim.",
                    notes = "El 'Ruach Elohim' (Espíritu de Dios) planeando sobre las aguas es interpretado en el Midrash Bereshit Rabbah como el espíritu del Mesías."
                ),
                BibleVerse(
                    number = 3,
                    textSpanish = "Y dijo Dios: Sea la luz; y fue la luz.",
                    textHebrew = "וַיֹּאמֶר אֱלֹהִים יְהִי אוֹר וַיְהִי-אוֹר׃",
                    textGreek = "καὶ εἶπεν ὁ θεός Γενηθήτω φῶς. καὶ ἐγένετο φῶς.",
                    transliteration = "Vayomer Elohim yehi or vayehi-or.",
                    notes = "Esta Or HaGanuz (Luz Primordial) ardió sin necesidad del sol, que no fue creado sino hasta el cuarto día."
                ),
                BibleVerse(
                    number = 4,
                    textSpanish = "Y vio Dios que la luz era buena; y separó Dios la luz de las tinieblas.",
                    textHebrew = "וַיַּרְא אֱלֹהִים אֶת-הָאוֹר כִּי-טוֹב וַיַּבְדֵּל אֱלֹהִים בֵּין הָאוֹר וּבֵין הַחֹשֶׁךְ׃",
                    textGreek = "καὶ εἶδεν ὁ θεὸς τὸ φῶς ὅτι καλόν. καὶ διεχώρισεν ὁ θεός...",
                    transliteration = "Vayar Elohim et-ha'or ki-tov vayavdel Elohim bein ha'or uvein hachoshech.",
                    notes = "División mística entre las chispas de santidad y el poder de la negatividad."
                ),
                BibleVerse(
                    number = 5,
                    textSpanish = "Y llamó Dios a la luz Día, y a las tinieblas llamó Noche. Y fue la tarde y la mañana un día.",
                    textHebrew = "וַיִּקְרָא אֱלֹהִים לָאוֹר יוֹם וְלַחֹשֶׁךְ קָרָא לָיְלָה וַיְהִי-עֶרֶב וַיְהִי-בֹקֶר יוֹם אֶחָד׃",
                    textGreek = "καὶ ἐκάλεσεν ὁ θεὸς τὸ φῶς ἡμέραν καὶ τὸ σκότος ἐκάλεσεν νύκτα...",
                    transliteration = "Vayikra Elohim la'or Yom velachoshech kara Laylah vayehi-erev vayehi-voker Yom Echad.",
                    notes = "Por esta razón en la tradición hebrea los días comienzan al atardecer (Viernes al atardecer inicia el Shabat)."
                )
            ),
            aiAnalysis = """
                Análisis Teológico Exegético de Génesis $chapter:
                1. Estructura Cósmica: El relato de Bereshit establece que el universo no surgió del caos fortuito sino de la palabra hablada (Davar) de Dios.
                2. Alef-Tav (אֵת): En el texto hebreo del verso 1 aparece la partícula 'Et' compuesta por la primera letra (Álef) y la última (Tav) del alefato, insinuando la presencia del Verbo Divino que abarca la totalidad del abecedario de la creación.
                3. La Luz Primordial: La primera orden divina es 'Yehi Or'. Esta luz no es la luz estelar del sol, sino la manifestación de la gloria divina (Shejiná) destinada a iluminar a los justos.
            """.trimIndent(),
            crossReferences = listOf(
                "Juan 1:1-3 - En el principio era el Verbo...",
                "Hebreos 11:3 - Por la fe entendemos haber sido constituido el universo por la palabra de Dios...",
                "Colosenses 1:16 - Porque en él fueron creadas todas las cosas...",
                "Salmo 33:6 - Por la palabra del Señor fueron hechos los cielos..."
            ),
            rabbinicComments = listOf(
                QuoteComment("Rashi", "Génesis 1:1", "La Torá debió comenzar con las leyes de Nisán en Éxodo 12, pero Dios comenzó con Bereshit para demostrar su soberanía absoluta sobre toda la tierra."),
                QuoteComment("Ramban (Nahmánides)", "Génesis 1:1", "La creación fue 'Yesh MiAyin' (Algo a partir de la Nada), trayendo materia primaria pura que luego formó todo el cosmos."),
                QuoteComment("Zohar I:15a", "Sefer HaZohar", "En el comienzo de la voluntad del Rey, una chispa deslumbrante talló el vacío primordial.")
            ),
            historicalContext = "Escrito por Moisés durante los 40 años en el desierto para dar al pueblo de Israel una identidad cósmica y teológica frente a las mitologías paganas de Egipto y Babilonia."
        )
    }

    private fun getPsalmChapter(chapter: Int): BibleChapterDetail {
        return BibleChapterDetail(
            bookId = "psalms",
            bookName = "Salmos (Tehilim)",
            chapterNumber = chapter,
            verses = listOf(
                BibleVerse(
                    number = 1,
                    textSpanish = "El Señor es mi pastor; nada me faltará.",
                    textHebrew = "יְהוָה רֹעִי לֹא אֶחְסָר׃",
                    textGreek = "Κύριος ποιμαίνει με καὶ οὐδέν με ὑστερήσει.",
                    transliteration = "Adonai ro'i lo echsar.",
                    notes = "Cántico de plena confianza davidica compuesto mientras huía o meditaba en los pastos de Judea."
                ),
                BibleVerse(
                    number = 2,
                    textSpanish = "En lugares de delicados pastos me hará descansar; junto a aguas de reposo me pastoreará.",
                    textHebrew = "בִּנְאוֹת דֶּשֶׁא יַרְבִּיצֵנִי עַל-מֵי מְנֻחוֹת יְנַהֲלֵנִי׃",
                    textGreek = "εἰς τόπον χλόης ἐκεῖ με κατεσκήνωσεν, ἐπὶ ὕδατος ἀναπαύσεως ἐξέθρεψέν με.",
                    transliteration = "Bin'ot deshe yarbitzeni al-mey menuchot yenahaleni.",
                    notes = "Las 'Aguas de Reposo' (Mey Menuchot) simbolizan la paz interior del alma alimentada por la Torá."
                ),
                BibleVerse(
                    number = 3,
                    textSpanish = "Confortará mi alma; me guiará por sendas de justicia por amor de su nombre.",
                    textHebrew = "נַפְשִׁי יְשׁוֹבֵב יַנְחֵנִי בְמַעְגְּלֵי-צֶדֶק לְמַעַן שְׁמוֹ׃",
                    textGreek = "τὴν ψυχήν μου ἐπέστρεψεν. ὡδήγησέν με ἐπὶ τρίβους δικαιοσύνης...",
                    transliteration = "Nafshi yeshovev yancheni vemaglei-tzedek lema'an shmo.",
                    notes = "Guiar por el sendero correcto no depende del mérito humano sino del 'Nombre Divino' de compasión."
                )
            ),
            aiAnalysis = "El Salmo 23 es el poema lírico místico más recitado de la historia judía y cristiana. En la mesa de Shabat se canta tradicionalmente durante la tercera comida (Seudah Shlishit) para invocar la protección divina ante el fin del día sagrado.",
            crossReferences = listOf(
                "Juan 10:11 - Yo soy el buen pastor...",
                "Ezequiel 34:12 - Como reconoce su rebaño el pastor...",
                "Apocalipsis 7:17 - Porque el Cordero los pastoreará y los guiará a fuentes de aguas de vida..."
            ),
            rabbinicComments = listOf(
                QuoteComment("Midrash Tehilim 23", "Rabbis", "David pronunció este salmo viendo por el Espíritu Santo el sustento de cada criatura en este mundo y el reposo del Mundo Venidero."),
                QuoteComment("Baal Shem Tov", "Keter Shem Tov", "Quien recita el Salmo 23 con profunda devoción disipa todos los temores de su mente.")
            ),
            historicalContext = "Compuesto por el Rey David, probablemente durante su experiencia como pastor en las colinas de Belén o durante su tiempo en el desierto."
        )
    }

    private fun getJohnChapter(chapter: Int): BibleChapterDetail {
        return BibleChapterDetail(
            bookId = "john",
            bookName = "Juan (Yochanan)",
            chapterNumber = chapter,
            verses = listOf(
                BibleVerse(
                    number = 1,
                    textSpanish = "En el principio era el Verbo, y el Verbo era con Dios, y el Verbo era Dios.",
                    textHebrew = "בְּרֵאשִׁית הָיָה הַדָּבָר וְהַדָּבָר הָיָה אֵצֶל הָאֱלֹהִים וֵאלֹהִים הָיָה הַדָּבָר׃",
                    textGreek = "Ἐν ἀρχῇ ἦν ὁ λόγος, καὶ ὁ λόγος ἦν πρὸς τὸν θεόν, καὶ θεὸς ἦν ὁ λόγος.",
                    transliteration = "Bereshit hayah HaDavar veHaDavar hayah etzel HaElohim veElohim hayah HaDavar.",
                    notes = "El Logos en griego y Memra / Davar en arameo/hebreo representan el instrumento de creación y revelación divina."
                ),
                BibleVerse(
                    number = 2,
                    textSpanish = "Este era en el principio con Dios.",
                    textHebrew = "הוּא הָיָה בְרֵאשִׁית אֵצֶל הָאֱלֹהִים׃",
                    textGreek = "οὗτος ἦν ἐν ἀρχῇ πρὸς τὸν θεόν.",
                    transliteration = "Hu hayah vereshit etzel HaElohim.",
                    notes = "Preexistencia eterna del Hijo antes de la fundación del mundo."
                ),
                BibleVerse(
                    number = 14,
                    textSpanish = "Y aquel Verbo fue hecho carne, y habitó entre nosotros (y vimos su gloria, gloria como del unigénito del Padre), lleno de gracia y de verdad.",
                    textHebrew = "וְהַדָּבָר נִהְיָה בָשָׂר וַיִּשְׁכֹּן בְּתוֹכֵנוּ וַנֶּחֱזֶה אֶת-כְּבֹדוֹ...",
                    textGreek = "καὶ ὁ λόγος σὰρξ ἐγένετο καὶ ἐσκήνωσεν ἐν ἡμῖν...",
                    transliteration = "VeHaDavar nihyah vasar vayishkon betochenu vanechezeh et-kevodo...",
                    notes = "'Vayishkon' comparte la raíz con Shejiná (La presencia tabernaculizante de Dios entre los hombres)."
                )
            ),
            aiAnalysis = "El Prólogo de Juan conecta directamente con Génesis 1:1. El apóstol Yochanan explica cómo la Sabiduría y Palabra de Dios (Memra / Logos) que creó el universo descendió a tabernaculizar entre la humanidad durante las festividades de Israel.",
            crossReferences = listOf(
                "Génesis 1:1 - En el principio creó Dios...",
                "Proverbios 8:22-30 - El Señor me poseía en el principio de su camino...",
                "Hebreos 1:1-3 - Dios, habiendo hablado muchas veces... nos ha hablado por el Hijo..."
            ),
            rabbinicComments = listOf(
                QuoteComment("Targum Yerushalmi", "Génesis 1", "Por la Memra (Palabra) de Adonai fueron creados los cielos y la tierra."),
                QuoteComment("Philo de Alejandría", "De Opificio Mundi", "El Logos es el modelo arquetípico sobre el cual fue estampada la creación divina.")
            ),
            historicalContext = "Escrito por el apóstol Juan en Éfeso a finales del primer siglo para confirmar la divinidad e identidad mesiánica de Yeshua a comunidades judeo-helenísticas."
        )
    }

    private fun getGenericChapter(bookId: String, chapter: Int): BibleChapterDetail {
        val book = books.find { it.id == bookId } ?: books.first()
        return BibleChapterDetail(
            bookId = book.id,
            bookName = book.nameSpanish,
            chapterNumber = chapter,
            verses = listOf(
                BibleVerse(
                    number = 1,
                    textSpanish = "Palabras sagradas registradas en el libro de ${book.nameSpanish}, capítulo $chapter.",
                    textHebrew = "דִּבְרֵי קֹדֶשׁ מִסֵּפֶר ${book.nameHebrew} פֶּרֶק $chapter",
                    transliteration = "Divrey kodesh mi-sefer ${book.nameSpanish} pereq $chapter.",
                    notes = "Versículo inicial del capítulo $chapter de ${book.nameSpanish}."
                ),
                BibleVerse(
                    number = 2,
                    textSpanish = "Escucha, Israel, la instrucción eterna y camina en los mandamientos de santidad.",
                    textHebrew = "שְׁמַע יִשְׂרָאֵל אֶת-הַמִּצְוָה וְהַתּוֹרָה אֲשֶׁר צִוָּה יְהוָה",
                    transliteration = "Shema Yisrael et-hamitzvah vehaTorah asher tzivah Adonai.",
                    notes = "Enseñanza sobre la perseverancia y la fe."
                )
            ),
            aiAnalysis = "Capítulo $chapter de ${book.nameSpanish}. Este pasaje proporciona valiosas lecciones de edificación espiritual, contexto histórico del pacto y profecías orientadas a la rectificación moral y la fe en Dios.",
            crossReferences = listOf(
                "${book.nameSpanish} 1:1",
                "Salmos 119:105 - Lámpara es a mis pies tu palabra...",
                "Deuteronomio 30:14 - Porque muy cerca de ti está la palabra..."
            ),
            rabbinicComments = listOf(
                QuoteComment("Comentario General", book.nameSpanish, "Este capítulo resalta la importancia de guardar el testimonio divino en el corazón.")
            ),
            historicalContext = "Contexto histórico sagrado conservado en la tradición del Tanaj y el Nuevo Pacto."
        )
    }
}
