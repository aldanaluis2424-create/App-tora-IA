package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AlefatoList : Screen("alefato_list")
    object LetterDetail : Screen("letter_detail/{letterId}") {
        fun createRoute(letterId: String) = "letter_detail/$letterId"
    }
    object FeastsList : Screen("feasts_list")
    object FeastDetail : Screen("feast_detail/{feastId}") {
        fun createRoute(feastId: String) = "feast_detail/$feastId"
    }
    object CalendarList : Screen("calendar_list")
    object MonthDetail : Screen("month_detail/{monthId}") {
        fun createRoute(monthId: String) = "month_detail/$monthId"
    }
    object BibleBooks : Screen("bible_books")
    object BibleChapters : Screen("bible_chapters/{bookId}") {
        fun createRoute(bookId: String) = "bible_chapters/$bookId"
    }
    object BibleChapterDetail : Screen("bible_chapter_detail/{bookId}/{chapterNumber}") {
        fun createRoute(bookId: String, chapterNumber: Int) = "bible_chapter_detail/$bookId/$chapterNumber"
    }
    object Translator : Screen("translator")
    object Favorites : Screen("favorites")
}
