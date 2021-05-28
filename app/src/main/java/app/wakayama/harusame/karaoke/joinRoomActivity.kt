package app.wakayama.harusame.karaoke

import android.content.Intent
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
            val selectedRoom = selectRoom.selectedItem.toString()//選ばれたルーム名を取得
            Log.d("TAG","selected => ${selectedRoom}")
            val toPlayRoomIntent:Intent = Intent(this,playRoomActivity::class.java)
            toPlayRoomIntent.putExtra("selectedRoom",selectedRoom)//選ばれたルーム名を渡す
            startActivity(toPlayRoomIntent)
            finish()
        }

    }
}