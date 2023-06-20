package code.aman.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.security.AllPermission;
import java.util.ArrayList;

import kotlin.reflect.KCallable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements categoryRVAdaptor.CategoryClickInterface {

    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<categoryRVModel> categoryRVModels;
    private categoryRVAdaptor categoryRVAdaptor;
    private NewsRVAdaptor newsRVAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV= findViewById(R.id.idRVNews);
        categoryRV=findViewById(R.id.idRVCategories);
        loadingPB=findViewById(R.id.PBLoading);
        articlesArrayList= new ArrayList<>();
        categoryRVModels = new ArrayList<>();
        newsRVAdaptor= new NewsRVAdaptor(articlesArrayList,this);
        categoryRVAdaptor= new categoryRVAdaptor(categoryRVModels,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdaptor);
        categoryRV.setAdapter(categoryRVAdaptor);

        getCategories();
    }

    private Void getCategories(){
        categoryRVModels.add(new categoryRVModel("All","https://unsplash.com/photos/yktK2qaiVHI"));
        categoryRVModels.add(new categoryRVModel("Technology","https://unsplash.com/photos/FO7JIlwjOtU"));
        categoryRVModels.add(new categoryRVModel("Science","https://unsplash.com/photos/G66K_ERZRhM"));
        categoryRVModels.add(new categoryRVModel("Sports","https://unsplash.com/photos/WUehAgqO5hE"));
        categoryRVModels.add(new categoryRVModel("General","https://unsplash.com/photos/shr_Xn8S8QU"));
        categoryRVModels.add(new categoryRVModel("Business","https://unsplash.com/photos/hpjSkU2UYSU"));
        categoryRVModels.add(new categoryRVModel("Entertainment","https://unsplash.com/photos/Qnlp3FCO2vc"));
        categoryRVModels.add(new categoryRVModel("Health","https://unsplash.com/photos/mNGaaLeWEp0"));

          categoryRVAdaptor.notifyDataSetChanged();

        return null;
    };

     private void getNews(String category){
         loadingPB.setVisibility(View.VISIBLE);
         articlesArrayList.clear();
         String categoryURL= "https://newsapi.org/v2/top-headlines/sources?category="+category+"&apiKey=bb7ae82f4d16448184e5bac85355fda0";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=bb7ae82f4d16448184e5bac85355fda0";
        String Base_URL ="https://newsapi.org/";
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(Base_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
         Class<NewsModal> call;
         if (category.equals(All)){
             call = retrofitApi.getAllNews(url);
         }else{
             call = retrofitApi.getNewsByCategory(categoryURL);

         }

         call.enqueue(new Callback<NewsModal>(){

             @Override
             public void onResponse(Call<NewsModal> call1 , Response<NewsModal> response){
              NewsModal newsModal = response.body();
              loadingPB.setVisibility(View.GONE);
              ArrayList<Articles> articles = newsModal.getArticles();
              for (int i=0, i<articles.size();i++){
                  articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDiscription(),articles.get(i).getUrlToImage(),
                          articles.get(i).getUrl(),articles.get(i).getContent()));
                 }
              NewsRVAdaptor.notifyDataSetChanged();
             }

             @Override
             public void onFailure(Call<NewsModal> call1 , Throwable ) {
                 Toast.makeText(MainActivity.this , "Fail to get news",Toast.LENGTH_SHORT).show();
             }
         });


     }


    @Override
    public void onCategoryClick(int position) {

    }
}