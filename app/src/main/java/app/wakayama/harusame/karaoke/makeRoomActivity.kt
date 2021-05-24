package app.wakayama.harusame.karaoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_make_room.*

class makeRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_room)

        backButton.setOnClickListener {
            val toMainIntent:Intent = Intent(this,MainActivity::class.java)
            startActivity(toMainIntent)
        }
        okButton.setOnClickListener {
            val toPlayRoomIntent:Intent = Intent(this,playRoomActivity::class.java)
            startActivity(toPlayRoomIntent)
        }
    }
}