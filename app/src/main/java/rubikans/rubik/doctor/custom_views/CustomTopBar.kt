package rubikans.rubik.doctor.custom_views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.withStyledAttributes
import kotlinx.android.synthetic.main.custon_top_bar.view.*
import rubikans.rubik.doctor.R
import rubikans.rubik.doctor.util.extensions.hide
import rubikans.rubik.doctor.util.extensions.visible


@SuppressLint("ResourceAsColor")
class CustomTopBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var rightCardView: CardView
    var leftCardView: CardView
    var notificationCardView: CardView
    var notificationImageView: ImageView
    var rightImageView: ImageView
    var leftImageView: ImageView
    var title: TextView
    var notificationCountTv: TextView


    fun hideLeftIcon() {
         leftCardView.hide()
    }
    fun visibleLeftIcon() {
        leftCardView.visible()
    }
    fun visibleNotificationIcon() {
        notificationCardView.visible()
    }


    fun visibleAddIcon() {
        addCardView.visible()
    }

    fun visableNotificationCount() {
        notificationCountTv.visible()
    }

    fun hideNotificationCount() {
        notificationCountTv.hide()
    }

    fun hideRightIcon() {
        rightCardView.hide()
    }
    fun visibleRightIcon() {
        rightCardView.visible()
    }

    fun setNotificationCount(notificationCount:String) {
        notificationCountTv.text = notificationCount
    }

    var rightImage: Int? = null
        set(value) {
            field = value
            rightImageView.setImageResource(value!!)
        }
    var leftImage: Int? = null
        set(value) {
            field = value
            leftImageView.setImageResource(value!!)
        }
    var appBarTitle: String? = null
        set(value) {
            field = value
            title.text = value
        }

    init {
        inflate(context, R.layout.custon_top_bar, this)
        rightCardView = findViewById(R.id.rightCardView)
        leftCardView = findViewById(R.id.leftCardView)
        rightImageView = findViewById(R.id.rightImageView)
        notificationCardView = findViewById(R.id.notificationCardView)
        notificationImageView = findViewById(R.id.notificationImageView)
        leftImageView = findViewById(R.id.leftImageView)
        title = findViewById(R.id.appBarTitle)
        notificationCountTv  = findViewById(R.id.notificationCount)
        context.withStyledAttributes(attrs, R.styleable.CustomTopBar) {
            rightImage = getResourceId(R.styleable.CustomTopBar_rightIcon, 0)
            leftImage = getResourceId(R.styleable.CustomTopBar_leftIcon, 0)
        }

    }





}