package ddiehl.android.imgurtest.searchresults

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import ddiehl.android.imgurtest.ImgurApplication
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.squareImageView
import org.jetbrains.anko.*
import timber.log.Timber

class SearchResultsAdapter(val mPresenter: SearchResultsPresenter)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  companion object {
    private val TYPE_IMAGE = 0
    private val TYPE_ALBUM = 1
  }

  override fun getItemViewType(position: Int): Int {
    val item = mPresenter.getItemAt(position)
    if (item is GalleryImage) return TYPE_IMAGE
    else if (item is GalleryAlbum) return TYPE_ALBUM
    else return Int.MIN_VALUE
  }

  override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
    val item = mPresenter.getItemAt(position)
    if (vh is GalleryImageVH) vh.bind(item as GalleryImage)
    else if (vh is GalleryAlbumVH) vh.bind(item as GalleryAlbum)
    else Timber.w("Need a bind function for this type: " + vh.javaClass.simpleName)
  }

  override fun getItemCount(): Int = mPresenter.getNumImages()

  override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder? {
    when (type) {
      TYPE_IMAGE -> return GalleryImageVH(VH_UI().createView(
          AnkoContext.create(parent.context, this)), mPresenter)
      TYPE_ALBUM -> return GalleryAlbumVH(VH_UI().createView(
          AnkoContext.create(parent.context, this)), mPresenter)
    }
    return null
  }

  private class GalleryImageVH(val view: View, val mPresenter: SearchResultsPresenter) :
      RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.find<ImageView>(R.id.image_view)

    fun bind(image: GalleryImage) {
      imageView.setOnClickListener { mPresenter.onImageClicked(image) }
      Glide.with(ImgurApplication.context)
          .load(image.getMediumThumbnailLink())
          .centerCrop()
//          .listener(LoggingListener<String, GlideDrawable>())
          .into(imageView)
    }
  }

  private class GalleryAlbumVH(val mView: View, val mPresenter: SearchResultsPresenter) :
      RecyclerView.ViewHolder(mView) {
    val imageView: ImageView = mView.find<ImageView>(R.id.image_view)

    fun bind(album: GalleryAlbum) {
      imageView.setOnClickListener { mPresenter.onAlbumClicked(album) }
      Glide.with(ImgurApplication.context)
          .load(album.getLargeCover())
          .centerCrop()
//          .listener(LoggingListener<String, GlideDrawable>())
          .into(imageView)
    }
  }

  private class VH_UI : AnkoComponent<SearchResultsAdapter> {
    override fun createView(ui: AnkoContext<SearchResultsAdapter>): View =
        ui.apply {
          frameLayout {
            lparams {
              width = matchParent
              height = wrapContent
            }
            squareImageView {
              id = R.id.image_view
              lparams {
                width = matchParent
                height = dip(0)
              }
            }
          }
        }.view
  }
}