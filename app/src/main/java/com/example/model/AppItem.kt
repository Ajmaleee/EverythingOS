package com.example.model

enum class AppCategory(val displayName: String) {
    ALL("ALL"),
    COMMUNICATION("CHAT"),
    SOCIAL("SOCIAL"),
    MEDIA("MEDIA"),
    PRODUCTIVITY("WORK"),
    UTILITIES("TOOLS"),
    SYSTEM("SYSTEM")
}

data class AppItem(
    val packageName: String,
    val activityName: String,
    val label: String,
    val iconName: String? = null,
    val category: AppCategory = AppCategory.ALL,
    val isPinned: Boolean = false,
    val isHidden: Boolean = false,
    val installTime: Long = 0L,
    val launchCount: Int = 0
)
