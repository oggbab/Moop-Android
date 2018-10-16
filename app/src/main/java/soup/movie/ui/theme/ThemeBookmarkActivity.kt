package soup.movie.ui.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_theme_bookmark.*
import soup.movie.R
import soup.movie.theme.ThemeBook
import soup.movie.util.recreateWithAnimation

class ThemeBookmarkActivity : AppCompatActivity() {

    private val listAdapter = ThemePageListAdapter {
        ThemeBook.turnOver(it) {
            recreateWithAnimation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeBook.open(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_bookmark)
        listView.adapter = listAdapter
        listAdapter.submitList(ThemeBook.getAvailablePages())
    }

    companion object {

        fun execute(ctx: Context) {
            ctx.startActivity(Intent(ctx, ThemeBookmarkActivity::class.java))
        }
    }
}