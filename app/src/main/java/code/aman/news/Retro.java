package code.aman.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Retro {

  @GET
    Call<NewsModal> getAllNews(@Url String url);

  @GET
    Call<NewsModal>getNewsByCatogory(@Url String url);


}
