package com.nemo.cineman.entity

object TMDBSortOptions {

    enum class SortField {
        POPULARITY,
        RELEASE_DATE,
        REVENUE,
        PRIMARY_RELEASE_DATE,
        ORIGINAL_TITLE,
        VOTE_AVERAGE,
        VOTE_COUNT,
        CREATE_AT
    }

    enum class SortDirection {
        ASC, DESC
    }

    data class SortOption(val field: SortField, val direction: SortDirection) {

        fun toParameterString(): String {
            val fieldString = when (field) {
                SortField.POPULARITY -> "popularity"
                SortField.RELEASE_DATE -> "release_date"
                SortField.REVENUE -> "revenue"
                SortField.PRIMARY_RELEASE_DATE -> "primary_release_date"
                SortField.ORIGINAL_TITLE -> "original_title"
                SortField.VOTE_AVERAGE -> "vote_average"
                SortField.VOTE_COUNT -> "vote_count"
                SortField.CREATE_AT -> "created_at"
            }

            val directionString = direction.name.lowercase()

            return "$fieldString.$directionString"
        }
    }

    val POPULARITY_ASC = SortOption(SortField.POPULARITY, SortDirection.ASC)
    val POPULARITY_DESC = SortOption(SortField.POPULARITY, SortDirection.DESC)
    val RELEASE_DATE_ASC = SortOption(SortField.RELEASE_DATE, SortDirection.ASC)
    val RELEASE_DATE_DESC = SortOption(SortField.RELEASE_DATE, SortDirection.DESC)
    val VOTE_AVERAGE_ASC = SortOption(SortField.VOTE_AVERAGE, SortDirection.ASC)
    val VOTE_AVERAGE_DESC = SortOption(SortField.VOTE_AVERAGE, SortDirection.DESC)
    val ORIGINAL_TITLE_ASC = SortOption(SortField.ORIGINAL_TITLE, SortDirection.ASC)
    val ORIGINAL_TITLE_DESC = SortOption(SortField.ORIGINAL_TITLE, SortDirection.DESC)
    val CREATE_AT_ASC = SortOption(SortField.CREATE_AT, SortDirection.ASC)
    val CREATE_AT_DESC = SortOption(SortField.CREATE_AT, SortDirection.DESC)

    val ALL_OPTIONS = listOf(
        POPULARITY_ASC,
        POPULARITY_DESC,
        RELEASE_DATE_ASC,
        RELEASE_DATE_DESC,
        VOTE_AVERAGE_ASC,
        VOTE_AVERAGE_DESC,
        ORIGINAL_TITLE_ASC,
        ORIGINAL_TITLE_DESC
    )

    val CREATE_OPTION = listOf(
        CREATE_AT_ASC,
        CREATE_AT_DESC
    )



    fun getDisplayName(sortOption: SortOption): String {
        val fieldName = when (sortOption.field) {
            SortField.POPULARITY -> "Độ phổ biến"
            SortField.RELEASE_DATE -> "Ngày phát hành"
            SortField.REVENUE -> "Doanh thu"
            SortField.PRIMARY_RELEASE_DATE -> "Ngày phát hành chính"
            SortField.ORIGINAL_TITLE -> "Tiêu đề gốc"
            SortField.VOTE_AVERAGE -> "Điểm đánh giá"
            SortField.VOTE_COUNT -> "Số lượt đánh giá"
            SortField.CREATE_AT -> "Ngày tạo"
        }

        val directionName = when (sortOption.direction) {
            SortDirection.ASC -> "tăng dần"
            SortDirection.DESC -> "giảm dần"
        }

        return "$fieldName ($directionName)"
    }
}