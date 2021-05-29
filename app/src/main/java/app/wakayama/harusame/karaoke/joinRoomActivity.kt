package app.wakayama.harusame.karaoke

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_join_room.*

class joinRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        backToMainFromJoinButton.setOnClickListener {
            val toMainIntent: Intent = Intent(this,MainActivity::class.java)
            startActivity(toMainIntent)
            finish()
        }
        //ここは部屋人数に応じて取ってくる仕組みに後で変更する。
        val roomLists = arrayOf(
            "room1",
            "room2",
            "room3",
            "room4",
            "room5"
        )
        val selectRoom = findViewById<Spinner>(R.id.selectRoomSpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roomLists)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectRoom.adapter = adapter

        goToPlayFromJoinButton.setOnClickListener {
            //選ばれたルーム名を取得
            val selectedRoom = selectRoom.selectedItem.toString()
            Log.d("TAG","selected => ${selectedRoom}")
            //入力されたパスワードを取得
            val inputedPassWord = passwordJoinTextView.text.toString()
            //パスワード照合
            val collectionName = "karaoke"
            db.collection(collectionName).document(selectedRoom)
                .get()
                .addOnSuccessListener { document ->
                    if(inputedPassWord == document.data?.get("pass")){//パスワードが一致すれば画面遷移
                        val toPlayRoomIntent:Intent = Intent(this,playRoomActivity::class.java)
                        toPlayRoomIntent.putExtra("selectedRoom",selectedRoom)//選ばれたルーム名を渡す
                        startActivity(toPlayRoomIntent)
                        finish()
                    }else{
                        joinMessageTextView.setTextColor(Color.parseColor("#ff0000"))
                        joinMessageTextView.text = "パスワードが一致しません。"
                    }
                }
                .addOnFailureListener {
                    Log.d("TAG","データの取得に失敗しました。部屋が存在しないかも？")
                    joinMessageTextView.setTextColor(Color.parseColor("#ff0000"))
                    joinMessageTextView.text = "データの取得に失敗しました。部屋が存在しないかも？"
                }
        }

    }
}