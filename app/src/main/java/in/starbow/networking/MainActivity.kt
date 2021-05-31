package `in`.starbow.networking

import `in`.starbow.networking.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var bind : ActivityMainBinding
    val adapter=UserAdapter()
    val originalList= arrayListOf<User>()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
//        val okHttpClient = OkHttpClient()
//        val request = Request.Builder()
//            .url("https://api.github.com/users/Starboww")
//            .build()
//        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

//     GlobalScope.launch(Dispatchers.Main) {
//      val response =  withContext(Dispatchers.IO) {
//          okHttpClient.newCall(request).execute().body?.string()}
//
//          // Log.i("Coroutine","${response.body}")
//          val user = gson.fromJson<User>(response,User::class.java)
//
//          Log.i("NetworkingX", user.login.toString())
//
////          val image = obj.getString("avatar_url")
////          val login = obj.getString("login")
////          val name = obj.getString("name")
//
//          bind.textView.text = user.login
//          bind.textView2.text = user.name
//          Log.i("Networking", "$image  ,$login,   $name")
//         Picasso.get().load(user.avatarUrl).into(bind.imageView);
//
//     }
            adapter.onItemClick={
                val intent = Intent(this,UserActivity::class.java)
                intent.putExtra("ID",it)
                startActivity(intent)

            }
            bind.userRV.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
                adapter=this@MainActivity.adapter
                Log.d("RV","$layoutManager")

            }
            bind.searchView.isSubmitButtonEnabled=true
            bind.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { searchUsers(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        searchUsers(newText)
                    }
                    return true
                }

            })
           bind.searchView.setOnCloseListener {
               adapter.swapData(originalList)
               true
           }
           GlobalScope.launch(Dispatchers.Main){
               val response = withContext(Dispatchers.IO){
                   Client.api.getUser()
               }
               if(response.isSuccessful){
                   response.body()?.let {
                    originalList.addAll(it)
                       adapter.swapData(it)
                   }
               }
           }


    }
fun searchUsers(query:String)
{
    GlobalScope.launch(Dispatchers.Main){
        val response = withContext(Dispatchers.IO){
         Client.api.searchUsers(query)

        }
        if(response.isSuccessful){
            response.body()?.let {

               it.items?.let{it1-> adapter.swapData(it1)}
            }
        }
    }
}

}