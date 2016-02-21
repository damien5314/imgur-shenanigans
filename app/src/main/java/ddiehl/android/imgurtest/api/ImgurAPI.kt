package ddiehl.android.imgurtest.api

import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryAlbum
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface ImgurAPI {

  @GET("/3/{section}/{sort}/{page}")
  fun getGallery(
      @Path("section") section: String,
      @Path("sort") sort: String,
      @Path("page") page: Int): Observable<Response<AbsGalleryItem.Response>>

  @GET("/3/gallery/album/{id}")
  fun getAlbum(
      @Path("id") id: String
  ): Observable<Response<GalleryAlbum.Response>>

}