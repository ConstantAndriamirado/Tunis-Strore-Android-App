package tunisStore.app.ui.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteAppData(
    val id: String,
    @SerializedName("package_name") val packageName: String,
    val name: String,
    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("full_description") val fullDescription: String,
    @SerializedName("developer_id") val developerId: String,
    @SerializedName("developer_name") val developerName: String,
    @SerializedName("category_id") val categoryId: String,
    val tags: List<String>,
    val status: String,
    @SerializedName("moderation_notes") val moderationNotes: String?,
    @SerializedName("content_rating") val contentRating: String,
    @SerializedName("privacy_policy_url") val privacyPolicyUrl: String?,
    @SerializedName("download_count") val downloadCount: Int,
    val rating: String,
    @SerializedName("review_count") val reviewCount: Int,
    @SerializedName("icon_url") val iconUrl: String?,
    @SerializedName("feature_graphic_url") val featureGraphicUrl: String?,
    val screenshots: List<String>?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("published_at") val publishedAt: String?,
    @SerializedName("last_modified_at") val lastModifiedAt: String,
    val versions: List<Version>
)

data class Version(
    val id: String,
    @SerializedName("app_id") val appId: String,
    @SerializedName("version_name") val versionName: String,
    @SerializedName("version_code") val versionCode: Int,
    @SerializedName("file_name") val fileName: String,
    @SerializedName("file_path") val filePath: String,
    @SerializedName("file_size") val fileSize: Long,
    @SerializedName("file_hash") val fileHash: String,
    @SerializedName("mime_type") val mimeType: String,
    @SerializedName("min_sdk_version") val minSdkVersion: Int?,
    @SerializedName("target_sdk_version") val targetSdkVersion: Int?,
    @SerializedName("supported_abis") val supportedAbis: List<String>?,
    val permissions: List<String>?,
    @SerializedName("release_notes") val releaseNotes: String?,
    @SerializedName("release_notes_language") val releaseNotesLanguage: String,
    val status: String,
    @SerializedName("rollout_percentage") val rolloutPercentage: Int,
    @SerializedName("download_count") val downloadCount: Int,
    @SerializedName("crash_rate") val crashRate: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("published_at") val publishedAt: String?,
    @SerializedName("archived_at") val archivedAt: String?
)