package app.wakayama.harusame.karaoke

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_join_room.*

class joinRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        //パスワード入力欄を隠しておく。
        passWordLayout.isVisible = false
        passwordJoinTextView.isVisible = false


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
        // OnItemSelectedListenerの実装
        var selectedRoom:String = ""
        selectRoom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            // 項目が選択された時に呼ばれる
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedRoom = parent?.selectedItem as String//選ばれたルーム名を取得
                Log.d("TAG","select changed ${selectedRoom}")
//                Toast.makeText(this@joinRoomActivity, selectedRoom, Toast.LENGTH_SHORT).show()
                db.collection("karaoke").document(selectedRoom)
                    .get()
                    .addOnSuccessListener { document ->
                        if(document.data?.get("pass") != ""){//もしパスワードが存在すれば、入力欄を表示
                            passWordLayout.isVisible = true
                            passwordJoinTextView.isVisible = true
                        }else{
                            passWordLayout.isVisible = false
                            passwordJoinTextView.isVisible = false
                        }
                    }
            }

            // 基本的には呼ばれないが、何らかの理由で選択されることなく項目が閉じられたら呼ばれる
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        goToPlayFromJoinButton.setOnClickListener {
//            Log.d("TAG","selected => ${selectedRoom}")
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