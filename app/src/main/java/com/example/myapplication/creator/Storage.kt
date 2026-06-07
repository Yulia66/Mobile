package com.example.myapplication.creator

import com.example.myapplication.data.dto.TrackDto

class MusicStorage {

    private val tracksCatalog = listOf(
        TrackDto("Кукушка", "Виктор Цой", 245000),
        TrackDto("Спокойная ночь", "Кино", 312000),
        TrackDto("Пластмассовая жизнь", "Ночные Снайперы", 198000),
        TrackDto("Моя оборона", "Гражданская Оборона", 178000),
        TrackDto("Русское поле экспериментов", "Егор Летов", 267000),
        TrackDto("Белая ночь", "Форум", 224000),
        TrackDto("Я хочу быть с тобой", "Nautilus Pompilius", 198000),
        TrackDto("Прогулка по воде", "Nautilus Pompilius", 212000),
        TrackDto("Выхода нет", "Сплин", 191000),
        TrackDto("Весна", "ДДТ", 205000)
    )

    fun findTracksByQuery(searchQuery: String): List<TrackDto> {
        val cleanedQuery = searchQuery.lowercase().trim()
        
        return if (cleanedQuery.isEmpty()) {
            emptyList()
        } else {
            tracksCatalog.filter { musicItem ->
                musicItem.trackName.lowercase().contains(cleanedQuery)
            }
        }
    }
}