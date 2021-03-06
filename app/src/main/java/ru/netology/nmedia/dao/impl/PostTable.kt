package ru.netology.nmedia.dao.impl

object PostTable {
    const val NAME = "posts"
    val DDL = """
        CREATE TABLE $NAME (
            ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.AUTHOR.columnName} TEXT NOT NULL,
            ${Column.AUTHOR_AVATAR.columnName} TEXT NOT NULL,
            ${Column.CONTENT.columnName} TEXT NOT NULL,
            ${Column.PUBLISHED.columnName} TEXT NOT NULL,
            ${Column.LIKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT false,
	        ${Column.LIKED_COUNT.columnName} INTEGER NOT NULL DEFAULT 0,
	        ${Column.SHARED_COUNT.columnName} INTEGER NOT NULL DEFAULT 0,	
	        ${Column.VIEWED_COUNT.columnName} INTEGER NOT NULL DEFAULT 0,	
            ${Column.VIDEO_URL.columnName} TEXT
        );
        """.trimIndent()

    val ALL_COLUMNS = Column.values()
        .map(Column::columnName)
        .toTypedArray()

    enum class Column(val columnName : String){
        ID("id"),
        AUTHOR("author"),
        AUTHOR_AVATAR("authorAvatar"),
        CONTENT("content"),
        PUBLISHED("published"),
        LIKED_BY_ME("likedByMe"),
        LIKED_COUNT("likedCount"),
        SHARED_COUNT("sharedCount"),
        VIEWED_COUNT("viewedCount"),
        VIDEO_URL("videoURL"),
    }
}