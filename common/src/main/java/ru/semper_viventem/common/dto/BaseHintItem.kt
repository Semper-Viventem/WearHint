package ru.semper_viventem.common.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import ru.semper_viventem.common.dto.BaseHintItem.Deserializer
import java.io.Serializable

/**
 * Класс для представления элементов списка подсказок.
 * Все возможные реализации должны иметь companion object, унаследованный от
 * [Deserializer] и [Serializer] для получения их конечных реализаций.
 *
 * @param id идентификатор
 * @param type тип конечной реализации данного объекта. Необходим для десериализации
 */
abstract class BaseHintItem(
    @SerializedName(KEY_ID) val id: Long,
    @SerializedName(KEY_TYPE) val type: Int
) : Serializable {

    companion object {
        protected const val KEY_TYPE = "type"
        protected const val KEY_ID = "id"

        private const val TYPE_TITLE = 0
        private const val TYPE_TEXT = 1
        private const val TYPE_IMAGE = 2

        /**
         * Получить конечную реализацию этого элемента из [JSONObject]
         * @param json объект для десериализации
         */
        fun fromJson(json: JSONObject): BaseHintItem = when(json.getInt(KEY_TYPE)) {
            TYPE_TITLE -> TitleHintItem.fromJsonByType(json)
            TYPE_IMAGE -> ImageHintItem.fromJsonByType(json)
            TYPE_TEXT -> TextHintItem.fromJsonByType(json)
            else -> throw IllegalArgumentException("Unknown object type")
        }
    }

    /**
     * Получить json из конечной реализации этого объекта
     * @param item сам объект
     */
    fun toJson(): String = when(this) {
        is TitleHintItem -> toJsonByType()
        is TextHintItem -> toJsonByType()
        is ImageHintItem -> toJsonByType()
        else -> throw IllegalArgumentException("Unknown object type")
    }

    /**
     * Интерфейс для companion object,
     * позволяющий десериализовывать этот объект десериализации.
     */
    private interface Deserializer {
        fun fromJsonByType(json: JSONObject): BaseHintItem
    }

    /**
     * Интерфейс для companion object,
     * позволяющий сериализовывать конкретный тип объекта подсказки.
     */
    abstract fun toJsonByType(): String

    /**
     * Реализация элемента заголовка
     * @param id идентификатор суперкласса
     * @param title заголовок
     */
    class TitleHintItem(
        id: Long,
        @SerializedName(KEY_TITLE) val title: String
    ): BaseHintItem(id, TYPE_TITLE) {

        companion object : Deserializer {

            private const val KEY_TITLE = "title"

            override fun fromJsonByType(json: JSONObject) = TitleHintItem(
                    json.getLong(KEY_ID),
                    json.getString(KEY_TITLE)
            )
        }

        override fun toJsonByType(): String = Gson().toJson(this)
    }

    /**
     * Реализация элемента заголовка
     * @param id идентификатор суперкласса
     * @param text текст блока
     */
    class TextHintItem(
        id: Long,
        @SerializedName(KEY_TEXT) val text: String
    ): BaseHintItem(id, TYPE_TEXT) {

        companion object : Deserializer {

            private const val KEY_TEXT = "text"

            override fun fromJsonByType(json: JSONObject) = TextHintItem(
                    json.getLong(KEY_ID),
                    json.getString(KEY_TEXT)
            )
        }

        override fun toJsonByType(): String = Gson().toJson(this)
    }

    /**
     * Реализация элемента заголовка
     * @param id идентификатор суперкласса
     * @param uri ссылка на изображение
     */
    class ImageHintItem(
        id: Long,
        @SerializedName(KEY_URI) var uri: String
    ): BaseHintItem(id, TYPE_IMAGE) {

        companion object : Deserializer {

            private const val KEY_URI = "uri"

            override fun fromJsonByType(json: JSONObject) = ImageHintItem(
                    json.getLong(KEY_ID),
                    json.getString(KEY_URI)
            )
        }
        override fun toJsonByType(): String = Gson().toJson(this)
    }
}

