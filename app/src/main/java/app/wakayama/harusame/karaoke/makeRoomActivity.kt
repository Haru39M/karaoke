package app.wakayama.harusame.karaoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_make_room.*

class makeRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_room)

        val collectionName:String = "karaoke"

        //ここはまだ作られていないもののみ表示するように後から変更する。でないと既に作られた部屋も誰でも初期化できてしまう。
        val roomLists = arrayOf(
            "room1",
            "room2",
            "room3",
            "room4",
            "room5"
        )
        val setRoom = findViewById<Spinner>(R.id.setRoomSpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roomLists)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        setRoom.adapter = adapter

        backButton.setOnClickListener {
            val toMainIntent:Intent = Intent(this,MainActivity::class.java)
            startActivity(toMainIntent)
            finish()
        }
        okButton.setOnClickListener {
            val setedRoom = setRoom.selectedItem.toString()//選択されたルーム名を取得
            val roomId:String = setedRoom
            val PassWord:String = passwordMakeTextView.text.toString()
            Log.d("TAG","password is setted! => ${PassWord}")

            //初期化用データ。roomIdとPassWordはmakeRoomからもらう
            val defaultData = hashMapOf(//初期化する時のデータ
                "pass" to PassWord,
                "people" to 0,//部屋に居る人数
                "sound1" to 0,
                "sound2" to 0,
                "sound3" to 0,
                "sound4" to 0
            )
            //初期化していくぅ！
            db.collection(collectionName).document(roomId)
                .set(defaultData)
                .addOnSuccessListener {
                    Log.d("TAG", "successed to initializing")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "failed to initializing", e)
                }


            //画面遷移
            val toPlayRoomIntent:Intent = Intent(this,playRoomActivity::class.java)
            toPlayRoomIntent.putExtra("PassWord",PassWord)
            toPlayRoomIntent.putExtra("selectedRoom",roomId)
            startActivity(toPlayRoomIntent)
            finish()
        }
    }
}