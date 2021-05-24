package app.wakayama.harusame.karaoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_join_room.*

class joinRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_room)

        backToMainFromJoinButton.setOnClickListener {
            val toMainIntent: Intent = Intent(this,MainActivity::class.java)
            startActivity(toMainIntent)
        }
        goToPlayFromJoinButton.setOnClickListener {
            val toPlayRoomIntent:Intent = Intent(this,playRoomActivity::class.java)
            startActivity(toPlayRoomIntent)
        }

    }
}