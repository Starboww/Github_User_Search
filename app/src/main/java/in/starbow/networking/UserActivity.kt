package `in`.starbow.networking

import `in`.starbow.networking.databinding.ActivityUserBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserActivity : AppCompatActivity() {
    private lateinit var bind:ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityUserBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val id=intent.getStringExtra("ID")
        GlobalScope.launch(Dispatchers.Main){
            val response = withContext(Dispatchers.IO){

                Client.api.getUserbyId(id.toString())
            }
            if(response.isSuccessful){
                response.body()?.let {
                    bind.inc.textView.text = it.login
                    bind.inc.textView2.text = it.name
                    Picasso.get().load(it.avatarUrl).into(bind.inc.imageView);
                }
            }
        }
    }
}