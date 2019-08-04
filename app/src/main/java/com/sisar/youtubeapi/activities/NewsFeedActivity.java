package com.sisar.youtubeapi.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sisar.youtubeapi.Pojo.MediaList;
import com.sisar.youtubeapi.Pojo.NewsFeed;
import com.sisar.youtubeapi.R;
import com.sisar.youtubeapi.adapter.NewsFeedAdapter;
import com.sisar.youtubeapi.apiclient.ApiClient;
import com.sisar.youtubeapi.databinding.ActivityNewsFeedBinding;
import com.sisar.youtubeapi.interfaces.APIService;
import com.sisar.youtubeapi.interfaces.RefreshTokenCallbacks;
import com.sisar.youtubeapi.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFeedActivity extends AppCompatActivity {
    private final String TAG="NewsFeedActivity";
    private ActivityNewsFeedBinding binding;
    private boolean isLoading;
    private ArrayList <NewsFeed> mList;
    private NewsFeedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_feed);
        binding.setNewsfeeddata(this);

        binding.rvRecylerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastCompletelyVisibleItemPosition = 0;

                lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                try {
                    //if (mList.size() > 9) {
                    if (!isLoading && lastCompletelyVisibleItemPosition == mList.size() - 1) {


                                   /* mList.add(null);
                                    mAdapter.notifyItemInserted(mList.size() - 1);

                                    isLoading = true;
                                    loadmorescrolltype = "1";

                                    detailsBindFromService(type, date);*/
                        Log.d(TAG, "Last item Visibile");
                    }
                    // }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });
        preparingData();
    }

    private void preparingData() {
        mList=new ArrayList <>();
        for(int i=0;i<=10;i++){
            NewsFeed mitem=new NewsFeed();



            if(i==2||i==4||i==6){
                ArrayList<MediaList> medialist1=new ArrayList <>();
                for(int k=0;k<2;k++)
                {
                    MediaList mediaList=new MediaList();
                    if(k==0){
                        mediaList.setImageid("1");
                        mediaList.setFiletype("image");
                        mediaList.setPath("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjP1qSitOPjAhUBNI8KHYLrDOAQjRx6BAgBEAU&url=https%3A%2F%2Fonlinejpgtools.com%2Fconvert-jpg-to-png&psig=AOvVaw11q6P9MCImYmTe6SHVSCli&ust=1564808508928865");
                         }
                    if(k==1){
                        mediaList.setImageid("2");
                        mediaList.setFiletype("url");
                        mediaList.setPath("https://www.youtube.com/watch?v=mAotyuaOAb0");
                    }
                    medialist1.add(mediaList);
                    mitem.setMediaList(medialist1);
                }

            }else {
                ArrayList<MediaList> list1=new ArrayList <>();
                for(int j=0;j<4;j++)
                {
                    MediaList mediaList=new MediaList();
                    if(j==0){
                        mediaList.setImageid("1");
                        mediaList.setFiletype("image");
                        mediaList.setPath("https://www.google.com/search?tbm=isch&sa=1&ei=VcNDXZvhAdLhz7sPtrypoAU&q=images+png&oq=images+png&gs_l=img.3..0l10.97854.100984..101807...0.0..0.107.385.3j1......0....1..gws-wiz-img.......0i67.rnE56h1MOBg&ved=0ahUKEwjb36Das-PjAhXS8HMBHTZeClQQ4dUDCAY&uact=5#imgrc=ZgGsr4qs6PWp9M:");
                    }
                    if(j==1){
                        mediaList.setImageid("2");
                        mediaList.setFiletype("url");
                        mediaList.setPath("https:\\/\\/youtu.be\\/dceBQE8EZCs");
                    }
                    if(j==2){
                        mediaList.setImageid("3");
                        mediaList.setFiletype("url");
                        mediaList.setPath("https:\\/\\/youtu.be\\/dceBQE8EZCs");
                    }
                    if(j==3){
                    mediaList.setImageid("4");
                    mediaList.setFiletype("image");
                    mediaList.setPath("https://www.google.com/search?tbm=isch&sa=1&ei=VcNDXZvhAdLhz7sPtrypoAU&q=images+png&oq=images+png&gs_l=img.3..0l10.97854.100984..101807...0.0..0.107.385.3j1......0....1..gws-wiz-img.......0i67.rnE56h1MOBg&ved=0ahUKEwjb36Das-PjAhXS8HMBHTZeClQQ4dUDCAY&uact=5#imgrc=ZgGsr4qs6PWp9M:");
                }
                    list1.add(mediaList);
                    mitem.setMediaList(list1);
                }
                //mitem.setMediaList(medialist1);
            }
            //mitem.setMediaList(medialist1);
            mList.add(mitem);

        }

        if (mAdapter == null) {
            mAdapter = new NewsFeedAdapter(this, mList);
            binding.rvRecylerview.setLayoutManager(new LinearLayoutManager(NewsFeedActivity.this));
            binding.rvRecylerview.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }else {
            mAdapter.setList(mList);
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnClickListener(new NewsFeedAdapter.OnClickListener() {
            @Override
            public void onMediaClick(int position, int mediaPosition) {
                loadViewPageData(position,mediaPosition);
            }
        });
    }

    private void loadViewPageData(int position, int mediaPosition) {
        ArrayList<MediaList> mediaLists=mList.get(position).getMediaList();

        if(mediaLists.get(mediaPosition).getFiletype().equals("url")){
            Intent intent = new Intent(NewsFeedActivity.this, MainActivity.class);
            intent.putExtra("mediatype", mediaLists.get(mediaPosition).getPath());
            startActivity(intent);
        }else {
            Log.d(TAG,"image");
        }
    }

    /*void GetUserRecordApi() {

            Log.d("isCheckRefreshedToken","progreshshown");
           // cf.progressdialog.show("");



        APIService service = ApiClient.getClient(getApplicationContext())
                .create(APIService.class);

        Call<JsonObject> call = service.getUserRecords(
                "","");

        //calling the api
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> responseObject)
            {
                //hiding progress dialog


                if (responseObject.isSuccessful() && responseObject.body() != null)
                {

                    Log.d("isCheckRefreshedToken","true");
                    //cf.progressdialog.dismiss("");
                    Log.i("Retrofit Response", responseObject.toString());
                    Log.i("Retrofit Response", responseObject.body().toString());

                    try {
                        JSONObject response = new JSONObject(responseObject.body().toString());



                        if (response.has("user"))

                        {
                            JSONObject jsonObj = new JSONObject();
                            jsonObj = response.getJSONObject("user");



                        }

                        if (response.has("organization_data")) {
                            JSONObject organizationData = new JSONObject();
                            organizationData = response.getJSONObject("organization_data");

                            sharedPreference.setorganization_title(organizationData.getString("org_title"));
                            sharedPreference.setorg_id(String.valueOf(organizationData.getInt("org_id")));
                            sharedPreference.setapp_name(organizationData.getString("app_name"));

                        }

                        if(response.has("timezone")){
                            sharedPreference.settime_zone(response.getString("timezone"));
                        }
                        if(response.has("timezone_abbr")){
                            sharedPreference.settime_zoneabbr(response.getString("timezone_abbr"));
                        }

                        sharedPreference.setuser_role(response.getString("role"));
                        sharedPreference.setShow_role(response.getString("show_role"));


                        startActivity(new Intent(SplashScreen.this,MainActivity.class));
                        finish();


                    } catch (JSONException e) {
                        cf.show_toast(getResources().getString(R.string.lbl_jsonerror));
                        e.printStackTrace();
                    }

                } else {
                    Log.i("Message", responseObject.message().toString());
                    Log.i("Retrofit Response", "Error in getGenericJson:" + responseObject.code() + " " + responseObject.message());
//                    cf.show_toast(getResources().getString(R.string.lbl_serverdown));
                    // cf.showErrorHangling(SplashScreen.this,responseObject);



                    if (responseObject.code() == 401) {

                        cf.callRefreshToken(SplashScreen.this, new RefreshTokenCallbacks() {
                            @Override
                            public void onSuccess(@NonNull boolean value)
                            {
                                if(value){
                                    Log.d("isCheckRefreshedToken","false");
                                    isCheckRefreshedToken=false;
                                    Log.i("RefreshToken Response","TRUE");
                                    GetUserRecordApi();
                                }else {
                                    Log.d("isCheckRefreshedToken","failure");

                                    cf.show_toast(getString(R.string.tockenfailederror));
                                    cf.progressdialog.dismiss("");
                                    Intent intent = new Intent(SplashScreen.this, LoginAcitivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    sharedPreference.ClearPreferences();
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            @Override
                            public void onError(@NonNull Throwable throwable) {
                                cf.progressdialog.dismiss("");
                                try
                                {

                                    String smJSON = throwable.getMessage().toString();
                                    JSONObject jObj = new JSONObject(smJSON);
                                    if (jObj.has("message"));
                                    cf.show_toast(jObj.getString("message"));
                                    Intent intent = new Intent(SplashScreen.this, LoginAcitivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    sharedPreference.ClearPreferences();
                                    startActivity(intent);
                                    finish();
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }else {
                        cf.progressdialog.dismiss("");
                        cf.showErrorHangling( SplashScreen.this,responseObject);
                    }

                }



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                cf.progressdialog.dismiss("");
                t.printStackTrace();

                cf.show_toast(getResources().getString(R.string.lbl_serverdown));

                try {
                    String smJSON = t.getMessage().toString();
                    JSONObject jObj = new JSONObject(smJSON);
                    if (jObj.has("message"))
                        cf.show_toast(jObj.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }*/
   /* void detailsBindFromService(final String type1, final String date1) {

        if ((type1.equals("1") || type1.equals("2")) && date1.isEmpty()) {
            cf.progressdialog.show("");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cf.initOkHttp())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call <JsonObject> call = service.announcementslist(
                userTokenStr, userSchoolId, userYearId, userRoleStr, date1, searchStr, sectionId, classId, "", changeLang);

        Log.d("testingserach", searchStr);
        //calling the api
        call.enqueue(new Callback <JsonObject>() {
            @Override
            public void onResponse(Call <JsonObject> call, Response <JsonObject> responseObject) {
                //hiding progress dialog

                if (type1.equals("1") || type1.equals("2") && date1.equals("")) {
                    cf.progressdialog.dismiss("");

                }


                if (responseObject.isSuccessful() && responseObject.body() != null) {
                    Log.i("Retrofit Response", responseObject.toString());
                    Log.i("Retrofit Response", responseObject.body().toString());

                    try {
                        JSONObject response = new JSONObject(responseObject.body().toString());

                        if (response.has("posts")) {
                            JSONArray jsonArray = response.getJSONArray("posts");
                            // mList = new ArrayList<>();

                            if (searchTypeStr.equals("1")) {
                                if (mSearchList.size() > 0) {
                                    mSearchList.remove(mSearchList.size() - 1);
                                    mAdapter.notifyItemRemoved(mSearchList.size());
                                    //mAdapter.notifyDataSetChanged();
                                }
                            } else {
                                if (mList.size() > 0) {
                                    mList.remove(mList.size() - 1);
                                    mAdapter.notifyItemRemoved(mList.size());
                                    //mAdapter.notifyDataSetChanged();
                                }
                            }

                            // mSearchList=new ArrayList <>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                ArrayList <String> mDisplayList = new ArrayList <>();
                                ArrayList <String> mDisplayClassList = new ArrayList <>();
                                ArrayList <String> mDisplayTagsList = new ArrayList <>();

                                Annoucement item = new Annoucement();
                                JSONObject jObj = jsonArray.getJSONObject(i);

                                item.setFirst_name(jObj.getString("first_name"));
                                item.setLast_name(jObj.getString("last_name"));
                                item.setProfileImage(jObj.getString("picture"));
                                item.setGrpEdited(jObj.getInt("grpEdited"));
                                item.setGrpId(jObj.getInt("grpId"));
                                //item.setCalId(jObj.get("calId")==null?0:jObj.getInt("calId"));
                                item.setCalId(jObj.getInt("calId"));
                                item.setCalStatus(jObj.getInt("calStatus"));
                                //item.setCalStatus(0);
                                //item.setCalId(6);
                                item.setCalFromDate(jObj.getString("updated_at"));
                                item.setCalToDate(jObj.getString("created_at"));
                                date = item.getCalFromDate();
                                item.setGrpUserId(jObj.getInt("grpUserId"));
                                item.setGrpListId(jObj.getString("grpListId"));
                                item.setGrpSectionId(jObj.getString("grpSectionId"));
                                item.setGrpSubjectId(jObj.getInt("grpSubjectId"));

                                item.setSpace(jObj.getString("space"));

                                item.setGrpSubject(jObj.getString("grpSubject"));
                                item.setGrpContent(jObj.getString("grpContent"));
                                item.setGrpFromDate(jObj.getString("grpFromDate"));
                                item.setGrpToDate(jObj.getString("grpToDate"));
                                //item.setGrpSpecific(jObj.getString("grpSpecific"));
                                item.setViewsCount(jObj.getInt("viewsCount"));
                                item.setLikesCount(jObj.getInt("likesCount"));
                                item.setGoingCount(jObj.getInt("goingCount"));
                                item.setMaybeCount(jObj.getInt("maybeCount"));
                                item.setNotgoingCount(jObj.getInt("notgoingCount"));
                                item.setIsliked(jObj.getInt("liked"));
                                item.setConformStatus(jObj.getInt("confirmation_status"));
                                item.setIschecked(0);


                                //text added by list

                                String grpclass = "";
                                grpclass = jObj.get("class") == null ? "" : jObj.getString("class");
                                if (!grpclass.equals("") && !grpclass.equals("null")) {
                                    item.setGrpclass("Added for:" + grpclass + " ");
                                    //mDisplayList.add(item.getGrpclass());
                                } else {
                                    item.setGrpclass("");

                                }

                                String grptags = "";
                                grptags = jObj.get("grpTags") == null ? "" : jObj.getString("grpTags");
                                //item.setGrpTags();
                                if (!grptags.equals("") && !grptags.equals("null")) {
                                    item.setGrpTags("#" + grptags);
                                    //mDisplayTagsList.add(" "+item.getGrpTags());
                                    //mDisplayClassList.add(" "+item.getGrpTags());
                                } else {
                                    item.setGrpTags("");
                                }

                                ArrayList <String> classTextlist = new ArrayList <>();
                                String classlistStr1 = "";
                                classlistStr1 = jObj.get("class") == null ? "" : jObj.getString("class");
                                String[] strclassspe1 = classlistStr1.split(",");
                                classTextlist = new ArrayList <>(Arrays.asList(strclassspe1));
                                item.setClassTextList(classTextlist);

                                if (!grpclass.equals("") && !grpclass.equals("null")) {
                                    for (int i1 = 0; i1 < classTextlist.size(); i1++) {

                                        //mDisplayClassList.add(classTextlist.get(i1)+",");
                                        mDisplayList.add(classTextlist.get(i1));
                                    }
                                }


                                String grptitle = "";
                                grptitle = jObj.get("subject_title") == null ? "" : jObj.getString("subject_title");
                                item.setStrSubjTitle(grptitle);
                                if (!grptitle.equals("") && !grptitle.equals("null")) {
                                    item.setGrptitle("(" + grptitle + ")" + " ");
                                    // mDisplayClassList.add(item.getGrptitle());
                                    mDisplayList.add(item.getGrptitle());
                                } else {
                                    item.setGrptitle("");

                                }

                                //item.setGrptitle(jObj.getString());


                                ArrayList <String> classIdlist = new ArrayList <>();
                                String classlistStr = "";
                                classlistStr = jObj.get("class_section") == null ? "" : jObj.getString("class_section");
                                String[] strclassspe = classlistStr.split(",");
                                classIdlist = new ArrayList <>(Arrays.asList(strclassspe));
                                item.setClassIDList(classIdlist);


                                String grpspecifStr = jObj.getString("grpSpecific");
                                String[] strgrpspe = grpspecifStr.split(",");

                                String s = "";
                                ArrayList <String> specif = new ArrayList <>();
                                specif = new ArrayList <>(Arrays.asList(strgrpspe));

                                item.setSpecIDList(specif);

                                for (int k = 0; k < strgrpspe.length; k++) {
                                    if (strgrpspe[k].equals("6")) {
                                        s = s + "#AllTeacher " + " ";
                                    }
                                    if (strgrpspe[k].equals("7")) {
                                        s = s + "#AllStudent " + " ";
                                    }
                                    if (strgrpspe[k].equals("8")) {
                                        s = s + "#AllParents " + " ";
                                    }

                                    // mDisplayList.add(strgrpspe[k]);

                                }

                                if (s.equals("")) {
                                    item.setGrpSpecific(s);

                                } else {
                                    item.setGrpSpecific(s + " ");
                                    //mDisplayTagsList.add(item.getGrpSpecific());
                                    mDisplayList.add(item.getGrpSpecific());
                                }






                                *//*if(item.getSpecIDList().size()>0)
                                    mDisplayList.addAll(item.getSpecIDList());*//*

                                *//*if(!item.getGrpclass().isEmpty())
                                    mDisplayList.add(item.getGrpclass() +" ");*//*


                                item.setDisplayList(mDisplayList);
                                item.setDisplayClassList(mDisplayClassList);
                                item.setDisplayTagsList(mDisplayTagsList);


                                if (!jObj.getString("grpFiles").isEmpty()) {
                                    String file_name = "";

                                    ArrayList <String> splitlist = new ArrayList <>();
                                    String[] strlist = jObj.getString("grpFiles").split(",");
                                    splitlist = new ArrayList <>(Arrays.asList(strlist));

                                    ArrayList <MediaAnnoucement> mediaList = new ArrayList <>();
                                    ArrayList <MediaAnnoucement> otherList = new ArrayList <>();

                                    for (int j = 0; j < splitlist.size(); j++) {
                                        MediaAnnoucement annoucement = new MediaAnnoucement();
                                        annoucement.setId(j + "");
                                        annoucement.setName(splitlist.get(j));
                                        String mimeType = splitlist.get(j).substring(splitlist.get(j).lastIndexOf(".") + 1);
                                        annoucement.setMIMEType(mimeType);
                                        annoucement.setVideoBitmap(null);

                                        if (mimeType.equalsIgnoreCase("jpg") ||
                                                mimeType.equalsIgnoreCase("png") ||
                                                mimeType.equalsIgnoreCase("gif") || mimeType.equalsIgnoreCase("svg")
                                                || mimeType.equalsIgnoreCase("jpeg") || mimeType.equalsIgnoreCase("arw")) {
                                            annoucement.setMediaType(MediaAnnoucement.MEDIA_IMAGE);
                                        } else if (mimeType.equalsIgnoreCase("mp4") || mimeType.equalsIgnoreCase("mkv")
                                                || mimeType.equalsIgnoreCase("avi")) {
                                            annoucement.setMediaType(MediaAnnoucement.MEDIA_VIDEO);
                                        } else if (mimeType.equalsIgnoreCase("mp3") || mimeType.equalsIgnoreCase("m4a")) {
                                            annoucement.setMediaType(MediaAnnoucement.MEDIA_AUDIO);
                                        } else {
                                            annoucement.setMediaType(MediaAnnoucement.MEDIA_OTHER);
                                        }

                                        if (annoucement.getMediaType() == MediaAnnoucement.MEDIA_OTHER) {
                                            String[] arrfile = splitlist.get(j).split("~");
                                            file_name = arrfile[1];
                                            annoucement.setSplitfiles(file_name);
                                            annoucement.setFilePath(item.getSpace() + splitlist.get(j));
                                            otherList.add(annoucement);
                                        } else {
                                            String[] arrfile = splitlist.get(j).split("~");
                                            file_name = arrfile[1];
                                            annoucement.setSplitfiles(file_name);
                                            annoucement.setFilePath(jObj.getString("space") + splitlist.get(j));
                                            mediaList.add(annoucement);

                                        }

                                    }

                                    item.setMediaList(mediaList);
                                    item.setOthersList(otherList);

                                } else {
                                    item.setMediaList(new ArrayList <MediaAnnoucement>());
                                    item.setOthersList(new ArrayList <MediaAnnoucement>());
                                }


                                if (!searchTypeStr.equals("1")) {
                                    mList.add(item);

                                } else {
                                    mSearchList.add(item);
                                }


                            }

                            detailstype = "1";
                            if (!searchTypeStr.equals("1")) {
                                searchStr = "";
                                searchTypeStr = "";
                                prepareDetails(mList);

                            } else {

                                prepareDetails(mSearchList);

                            }

                            // Log.d("detailscount", detailstype + "start");
                            //entryScreenTyp = "3";


                            if (type1.equals("2")) {
                                entryScreenTyp = "1";
                                initToolbar();
                            }

                        }

                    } catch (JSONException e) {
                        cf.show_toast(getResources().getString(R.string.lbl_jsonerror));
                        e.printStackTrace();
                    }

                } else {
                    Log.i("Message", responseObject.message().toString());
                    Log.i("Retrofit Response", "Error in getGenericJson:" + responseObject.code() + " " + responseObject.message());

                    try {
                        String smJSON = responseObject.errorBody().string();
                        JSONObject jObj = new JSONObject(smJSON);

                        if (jObj.has("status_code")) {
                            if (jObj.getString("status_code").equals("401")) {

                                tokentype = "listApi";
                                ArrayList <Annoucement> stype = new ArrayList <>();


                                callRefreshToken(tokentype, type1, "", "", 0, 0, date1, stype, 0);

                            }

                            if (jObj.getString("status_code").equals("500")) {
                                Intent intent = new Intent(AnnouncementActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mGlobalValues.clear();
                                startActivity(intent);
                                cf.progressdialog.dismiss("");
                                cf.show_toast(getResources().getString(R.string.error500));
                            }
                        }

                        if (jObj.has("message"))
                            cf.show_toast(jObj.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call <JsonObject> call, Throwable t) {
                cf.progressdialog.dismiss("");
                t.printStackTrace();

                cf.show_toast(getResources().getString(R.string.lbl_serverdown));

                try {
                    String smJSON = t.getMessage().toString();
                    JSONObject jObj = new JSONObject(smJSON);
                    if (jObj.has("message"))
                        Toast.makeText(AnnouncementActivity.this, jObj.getString("message"),
                                Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }*/

    public void setLoaded() {
        isLoading = false;
    }
}
