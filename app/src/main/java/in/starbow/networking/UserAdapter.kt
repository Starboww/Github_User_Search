package `in`.starbow.networking

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
    private var data:List<User> = ArrayList()
   var onItemClick:((login:String)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user,parent,false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
   return holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun swapData(data:List<User>){
        this.data=data
        notifyDataSetChanged()
    }
    inner class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
             fun bind(item:User)=with(itemView){
                   val textView = itemView.findViewById<TextView>(R.id.textView)
                   val textView2 = itemView.findViewById<TextView>(R.id.textView2)
                 val imageView=findViewById<ImageView>(R.id.imageView)
                 textView.text = item.login
                textView2.text = item.name
             //   Log.i("Networking", "$image  ,$login,   $name")
                Picasso.get().load(item.avatarUrl).into(imageView);
                 setOnClickListener{
                     onItemClick?.invoke(item.login!!)
                 }
             }
    }
}