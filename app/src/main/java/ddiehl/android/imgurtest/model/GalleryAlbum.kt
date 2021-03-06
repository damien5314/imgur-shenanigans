package ddiehl.android.imgurtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GalleryAlbum(
    val id: String,
    val title: String,
    val description: String,
    val datetime: Long,
    val cover: String,
    @SerializedName("cover_width") val coverWidth: Int,
    @SerializedName("cover_height") val coverHeight: Int,
    @SerializedName("account_url") val accountUrl: String,
    @SerializedName("account_id") val accountId: Int,
    val privacy: String,
    val layout: String,
    val views: Int,
    @Expose val link: String,
    val ups: Int,
    val downs: Int,
    val points: Int,
    val score: Int,
    val vote: String?,
    val favorite: Boolean,
    val nsfw: Boolean?,
    @SerializedName("comment_count") val commentCount: Int,
    @SerializedName("comment_preview") val commentPreview: List<Comment>,
    val topic: String,
    @SerializedName("topic_id") val topicId: Int,
    @SerializedName("images_count") val imagesCount: Int,
    val images: List<GalleryImage>
) : AbsGalleryItem() {
  class Response (
      @Expose val data: GalleryAlbum,
      @Expose val success: Boolean,
      @Expose val status: Int
  )

  fun getRawCover() = getCover("")
  fun getSmallCover() = getCover("t")
  fun getMediumCover() = getCover("m")
  fun getLargeCover() = getCover("l")
  fun getHugeCover() = getCover("h")
  private fun getCover(s: String) = "http://i.imgur.com/%s%s.jpg".format(cover, s)
}