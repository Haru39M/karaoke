package app.wakayama.harusame.karaoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_play_room.*

class playRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_room)

        backToMainFromPlayButton.setOnClickListener {
            val toMainIntent:Intent = Intent(this,MainActivity::class.java)
            startActivity(toMainIntent)
        }

        playSound1Button.setOnClickListener {

        }

    }
}